package xyz.brassgoggledcoders.steamagerevolution.machinesystem;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class MachineType {

    public static final Map<String, MachineType> machinesList = new HashMap<String, MachineType>();

    private String uid;
    private ItemStack catalyst;

    public MachineType(String uid) {
        this.uid = uid;
    }

    public MachineType(String uid, ItemStack catalyst) {
        this(uid);
        this.catalyst = catalyst;
    }

    public MachineType(String uid, Block catalyst) {
        this(uid, new ItemStack(catalyst));
    }

    public String getUID() {
        return uid;
    }

    public final String getLocalizedName() {
        return new TextComponentTranslation(SteamAgeRevolution.MODID + ".machine." + getUID()).getFormattedText();
    }

    // FOR JEI
    public ItemStack getCatalyst() {
        return catalyst != null ? catalyst : ItemStack.EMPTY;
    }
}
