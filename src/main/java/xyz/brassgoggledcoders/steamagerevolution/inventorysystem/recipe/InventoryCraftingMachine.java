package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.*;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.ItemStackHandlerSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketSetRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

//TODO Drain (totalSteam/ticksToComplete) steam every tick
public class InventoryCraftingMachine extends InventoryBasic {

    public HashMap<IOType, ArrayList<ItemStackHandlerSync>> itemIOs = new HashMap<>();
    public HashMap<IOType, ArrayList<FluidTankSync>> fluidIOs = new HashMap<>();

    private int currentProgress = 0;
    protected MachineRecipe currentRecipe;
    @Nonnull
    RecipeError currrentError = RecipeError.NONE;

    public InventoryCraftingMachine(IHasInventory<? extends InventoryCraftingMachine> parent) {
        super(parent);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = super.serializeNBT();
        tag.setInteger("progress", currentProgress);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        currentProgress = tag.getInteger("progress");
        super.deserializeNBT(tag);
    }

    public MachineRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(@Nullable MachineRecipe recipe) {
        if(recipe != null) {
            currentRecipe = recipe;
        }
        else {
            currentRecipe = null;
            currentProgress = 0;
        }
        int networkID = currentRecipe != null ? currentRecipe.networkID : -1;
        if(!enclosingMachine.getMachineWorld().isRemote) {
            SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
                    new PacketSetRecipe(enclosingMachine.getMachinePos(), networkID), enclosingMachine.getMachinePos(),
                    enclosingMachine.getMachineWorld().provider.getDimension());
        }
    }

    public int getCurrentTicks() {
        return currentProgress;
    }

    public void setCurrentTicks(int ticks) {
        currentProgress = ticks;
    }

    public boolean updateServer() {
        if(canRun()) {
            setRecipeError(RecipeError.NONE);
            if(getCurrentTicks() <= currentRecipe.getTicksPerOperation()) { // TODO
                setCurrentTicks(getCurrentTicks() + 1);
            }
            if(canFinish()) {
                onFinish();
                return true;
            }
        }
        else {
            setCurrentRecipe(null);
        }
        return false;
    }

    public void setRecipeError(RecipeError error) {
        currrentError = error;
    }

    // Interpolate ticks on client
    public void updateClient() {
        if(currentRecipe != null) {
            if(currentProgress < currentRecipe.ticksToProcess) {
                currentProgress++;
            }
            else {
                currentProgress = 0;
            }
        }
        else {
            currentProgress = 0;
        }
    }

    protected void onFinish() {
        boolean extractedItems = true;
        boolean extractedFluids = true;
        boolean extractedSteam = true;
        // Try to extract required items
        if(ArrayUtils.isNotEmpty(currentRecipe.getItemInputs())) {
            // TODO Good lord loops
            int matched = 0;
            for(Ingredient input : currentRecipe.getItemInputs()) {
                for(ItemStackHandler handler : getTypedItemHandlers(IOType.INPUT)) {
                    for(int i = 0; i < handler.getSlots(); i++) {
                        if(input.apply(handler.getStackInSlot(i))) {
                            handler.extractItem(i, 1, false);
                            matched++;
                        }
                    }
                }
            }
            extractedItems = (currentRecipe.getItemInputs().length == matched);
        }
        // Try to extract required fluids
        if(ArrayUtils.isNotEmpty(currentRecipe.getFluidInputs())) {
            for(IngredientFluidStack input : currentRecipe.getFluidInputs()) {
                for(FluidTank tank : getTypedFluidHandlers(IOType.INPUT)) {
                    if(tank.drain(input.getFluid(), false) != null
                            && tank.drain(input.getFluid(), false).amount == input.getFluid().amount) {
                        tank.drain(input.getFluid(), true);
                    }
                    else {
                        extractedFluids = false;
                    }
                }
            }
        }
        // Try to extract required steam TODO See above
        if(this.getHandler("steamTank", FluidTankSync.class) != null) {
            if(this.getHandler("steamTank", FluidTankSync.class).getFluidAmount() >= currentRecipe
                    .getSteamUsePerCraft()) {
                this.getHandler("steamTank", FluidTankSync.class).drain(currentRecipe.getSteamUsePerCraft(), true);
            }
            else {
                extractedSteam = false;
            }
        }

        // If we extracted all requirements, create the outputs.
        if(extractedItems && extractedFluids && extractedSteam) {
            if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
                for(ItemStack output : currentRecipe.getItemOutputs().clone()) {
                    for(ItemStackHandler handler : getTypedItemHandlers(IOType.OUTPUT)) {
                        ItemStack stack = ItemHandlerHelper.insertItem(handler, output.copy(), false);
                        if(stack.isEmpty()) {
                            break;
                        }
                    }
                }
            }
            if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
                for(FluidStack output : currentRecipe.getFluidOutputs().clone()) {
                    for(FluidTank tank : getTypedFluidHandlers(IOType.OUTPUT)) {
                        int accepted = tank.fill(output.copy(), true);
                        if(accepted == output.amount) {
                            break;
                        }
                    }
                }
            }
            setCurrentRecipe(null);
            enclosingMachine.markMachineDirty();
        }
        else {
            SteamAgeRevolution.instance.getLogger()
                    .info("Machine encountered recipe error at final stage. This should not happen..." + extractedItems
                            + "/" + extractedFluids + "/" + extractedSteam);
        }
    }

    protected boolean canFinish() {
        if(currentRecipe != null && currentProgress >= currentRecipe.getTicksPerOperation()) {
            boolean roomForItems = true;
            boolean roomForFluids = true;
            if(ArrayUtils.isNotEmpty(currentRecipe.getItemOutputs())) {
                roomForItems = Arrays.asList(currentRecipe.getItemOutputs()).parallelStream()
                        .allMatch(output -> getTypedItemHandlers(IOType.OUTPUT).stream()
                                .anyMatch(h -> ItemHandlerHelper.insertItem(h, output, true).isEmpty()));
            }
            if(ArrayUtils.isNotEmpty(currentRecipe.getFluidOutputs())) {
                roomForFluids = Arrays.asList(currentRecipe.getFluidOutputs()).parallelStream()
                        .allMatch(output -> getTypedFluidHandlers(IOType.OUTPUT).stream()
                                .anyMatch(t -> t.fill(output, false) == output.amount));
            }
            if(!roomForItems || !roomForFluids) {
                setRecipeError(RecipeError.OUTPUT_BLOCKED);
            }
            return roomForItems && roomForFluids;
        }
        return false;
    }

    protected boolean canRun() {
        // If we already have a recipe, check we have enough steam to continue
        if(currentRecipe != null) {
            if(this.getHandler("steamTank", FluidTankSync.class) == null
                    || this.getHandler("steamTank", FluidTankSync.class)/* TODO */.getFluidAmount() >= currentRecipe
                            .getSteamUsePerCraft()) {
                return true;
            }
            else {
                setRecipeError(RecipeError.INSUFFICIENT_STEAM);
                setCurrentRecipe(null);
                return false;
            }
        }
        // Otherwise, try to find a recipe from the current inputs
        else {
            // TODO Sort recipes by size of input
            Optional<MachineRecipe> recipe = RecipeRegistry
                    .getRecipesForMachine(enclosingMachine.getMachineType().getUID()).parallelStream()
                    .filter(r -> hasRequiredFluids(r)).filter(r -> hasRequiredItems(r)).findFirst();
            if(recipe.isPresent()) {
                setCurrentRecipe(recipe.get());
                return true;
            }
        }
        return false;
    }

    public boolean hasRequiredFluids(MachineRecipe recipe) {
        // Check if the recipe has any fluid inputs required
        if(ArrayUtils.isNotEmpty(recipe.getFluidInputs())) {
            // Stream the fluid stacks required
            return Arrays.stream(recipe.getFluidInputs())
                    // Apply tanksHaveFluid to each element and output result to stream
                    .map(stack -> tanksHaveFluid(stack))
                    // Reduce list of booleans into one - so will only evaluate true if every
                    // boolean is true
                    .reduce((a, b) -> a && b).orElse(false);
        }
        // Else just return true
        return true;
    }

    private boolean tanksHaveFluid(IngredientFluidStack stack) {
        return getTypedFluidHandlers(IOType.INPUT).stream().filter(Objects::nonNull)
                .filter(tank -> tank.getFluidAmount() > 0)
                .filter(tank -> tank.getFluid().containsFluid(stack.getFluid())).findAny().isPresent();
    }

    public boolean hasRequiredItems(MachineRecipe recipe) {
        if(ArrayUtils.isNotEmpty(recipe.getItemInputs())) {
            return Arrays.stream(recipe.getItemInputs()).map(ing -> handlerHasItems(ing)).reduce((a, b) -> a && b)
                    .orElse(false);
        }
        return true;
    }

    private boolean handlerHasItems(Ingredient ingredient) {
        return getTypedItemHandlers(IOType.INPUT).stream()
                .filter(handler -> IntStream.range(0, handler.getSlots())
                        .mapToObj(slotNum -> handler.getStackInSlot(slotNum))
                        .filter(inputStack -> ingredient.apply(inputStack)).findAny().isPresent())
                .findAny().isPresent();
    }

    public ArrayList<ItemStackHandlerSync> getTypedItemHandlers(IOType type) {
        return itemIOs.get(type);
    }

    public ArrayList<FluidTankSync> getTypedFluidHandlers(IOType type) {
        return fluidIOs.get(type);
    }

    @Override
    public void createSublists() {
        super.createSublists();
        for(IOType type : IOType.values()) {
            itemIOs.put(type, new ArrayList<>());
            fluidIOs.put(type, new ArrayList<>());
        }
        this.getInventoryPiecesOfType(InventoryPieceItemHandler.class).stream().filter(piece -> piece.getType() != null)
                .forEach(piece -> itemIOs.get(piece.getType()).add(piece.getHandler()));
        this.getInventoryPiecesOfType(InventoryPieceFluidTank.class).stream().filter(piece -> piece.getType() != null)
                .forEach(piece -> fluidIOs.get(piece.getType()).add(piece.getHandler()));
    }

    @Nonnull
    public RecipeError getRecipeError() {
        return currrentError;
    }

    public void addNewPiece(InventoryPiece<InventoryCraftingMachine> piece) {
        this.inventoryPieces.put(piece.getName(), piece);
        piece.setParent(this);
        // Recreate
        this.createSublists();
    }
}
