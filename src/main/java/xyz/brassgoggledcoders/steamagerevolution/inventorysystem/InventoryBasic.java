package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.MultiFluidHandler;

//TODO Convert as much of InventorySystem to flyweight as possible. Two seperate classes, InventoryShape and InventoryContents?
@SuppressWarnings("rawtypes")
public class InventoryBasic implements ICapabilitySerializable<NBTTagCompound> {

	@Nonnull
	public final IHasInventory enclosingMachine;

	// Masterlist.
	public HashMap<String, InventoryPiece> inventoryPieces = Maps.newHashMap();

	public HashMap<Class<? extends InventoryPiece>, HashMap<String, InventoryPiece>> pieceLists = Maps.newHashMap();

	// You MUST use the builder...
	public InventoryBasic(IHasInventory parent) {
		enclosingMachine = parent;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if (!inventoryPieces.isEmpty()) {
			NBTTagList list = new NBTTagList();
			inventoryPieces.values().stream().filter(piece -> piece instanceof INBTSerializable)
					.forEach(piece -> list.appendTag(((INBTSerializable) piece).serializeNBT()));
			tag.setTag("pieces", list);
		}
		return tag;
	}

	// TODO Bloody mess...
	@SuppressWarnings("unchecked")
	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for(NBTBase base : tag.getTagList("pieces", 10)) {		
			NBTTagCompound ctag = (NBTTagCompound) base;
			String name = ctag.getString("name");
			if(this.inventoryPieces.get(name) != null) {
				((INBTSerializable) this.inventoryPieces.get(name)).deserializeNBT(ctag);
			}
		}
	}

	// TODO
	public List<ItemStackHandler> getItemHandlers() {
		return this.getInventoryPiecesOfType(InventoryPieceItemHandler.class).stream().map(p -> p.getHandler())
				.collect(Collectors.toList());
	}

	// TODO
	public List<FluidTankSync> getFluidHandlers() {
		return this.getInventoryPiecesOfType(InventoryPieceFluidTank.class).stream().map(p -> p.getHandler())
				.collect(Collectors.toList());
	}

	// Allows direct interaction with a specific, known, handler of that machine,
	// without having to rely on guessing their positions in an array
	// TODO these lists should include things that extend the passed in class
	@SuppressWarnings("unchecked")
	public <H extends INBTSerializable<NBTTagCompound>> H getHandler(String name, Class<H> handlerType) {
		if (inventoryPieces.containsKey(name)) {
			return ((InventoryPieceHandler<H>) inventoryPieces.get(name)).getHandler();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void updateFluid(PacketFluidUpdate message) {
		if (inventoryPieces.containsKey(message.name)) {
			FluidTankSync tank = null;
			try {
				MultiFluidHandler handler = ((InventoryPieceHandler<MultiFluidHandler>) inventoryPieces.get(message.name)).getHandler();
				for(FluidTankSync fillTank : handler.containedTanks) {
					if(fillTank.getFluid() != null && fillTank.getFluid().getFluid().equals(message.fluid.getFluid())) {
						tank = fillTank;
					}
				}
				if(tank == null) {
					tank = handler.containedTanks[0];
				}
			}
			catch(ClassCastException e) {
				tank = ((InventoryPieceHandler<FluidTankSync>) inventoryPieces.get(message.name)).getHandler();
			}
			tank.setFluid(message.fluid);
		}
	}

	public Collection<InventoryPiece> getInventoryPieces() {
		return inventoryPieces.values();
	}

	public void createSublists() {
		for (InventoryPiece piece : inventoryPieces.values()) {
			if (!pieceLists.containsKey(piece.getClass())) {
				pieceLists.put(piece.getClass(), new HashMap<>());
			}
			pieceLists.get(piece.getClass()).put(piece.getName(), piece);
		}
	}

	public <P extends InventoryPiece> ArrayList<P> getInventoryPiecesOfType(Class<P> type) {
		if (!pieceLists.containsKey(type)) {
			return new ArrayList<>();
		}
		return new ArrayList<P>((Collection<? extends P>) pieceLists.get(type).values());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return null;
	}
}
