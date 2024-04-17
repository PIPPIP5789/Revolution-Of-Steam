package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.tileentities.materials;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.materialsystem.*;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.ItemPartType;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

    public static Color brassColor = new Color(251, 194, 99);
    String[] vanillaParts = new String[] { "heavy_ore", "plate", "dust", "molten", "crushed_ore", "solution", "crystal",
            "lattice", "ore_rock" };
    String[] metalParts = new String[] { "ore", "heavy_ore", "crushed_ore", "ore_rock", "solution", "crystal", "ingot",
            "nugget", "plate", "dust", "block", "molten", "lattice" };
    String[] alloyParts = Arrays.copyOfRange(metalParts, 6, metalParts.length);
    String[] compatParts = new String[] { "crushed_ore", "solution", "crystal", "lattice", "ore_rock" };
    public static List<String> knownMetalTypes = Lists.newArrayList();
    public static String[] heavyOreMaterials = { "iron", "gold", "copper", "zinc" };

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        try {
            MaterialUser SAR = SteamAgeRevolution.instance.getMaterialUser();

            PartType heavyOre = new HeavyOrePartType();
            MaterialSystem.registerPartType(heavyOre);
            registerPart("Heavy Ore", heavyOre);

            registerPart("Lattice", MaterialSystem.getPartType("block"));

            new PartBuilder().setOwnerId(SAR.getId()).setName("Solution").setOreDictName("solution")
                    .setPartType(MaterialSystem.getPartType("fluid")).build();

            com.teamacronymcoders.base.materialsystem.materials.Material iron = new MaterialBuilder().setName("Iron")
                    .setColor(Color.GRAY).setHasEffect(false).build();
            com.teamacronymcoders.base.materialsystem.materials.Material gold = new MaterialBuilder().setName("Gold")
                    .setColor(Color.YELLOW).setHasEffect(false).build();

            com.teamacronymcoders.base.materialsystem.materials.Material copper = new MaterialBuilder()
                    .setName("Copper").setColor(new Color(159, 61, 19)).setHasEffect(false).build();
            com.teamacronymcoders.base.materialsystem.materials.Material zinc = new MaterialBuilder().setName("Zinc")
                    .setColor(new Color(133, 153, 154)).setHasEffect(false).build();

            com.teamacronymcoders.base.materialsystem.materials.Material steel = new MaterialBuilder().setName("Steel")
                    .setColor(Color.DARK_GRAY).setHasEffect(false).build();
            com.teamacronymcoders.base.materialsystem.materials.Material brass = new MaterialBuilder().setName("Brass")
                    .setColor(brassColor).setHasEffect(false).build();

            com.teamacronymcoders.base.materialsystem.materials.Material sulphur = new MaterialBuilder()
                    .setName("Sulphur").setColor(new Color(200, 200, 60)).setHasEffect(false).build();

            com.teamacronymcoders.base.materialsystem.materials.Material tin = new MaterialBuilder().setName("Tin")
                    .setColor(Color.LIGHT_GRAY).setHasEffect(false).build();
            com.teamacronymcoders.base.materialsystem.materials.Material aluminum = new MaterialBuilder()
                    .setName("Aluminum").setColor(Color.WHITE).setHasEffect(false).build();
            com.teamacronymcoders.base.materialsystem.materials.Material lead = new MaterialBuilder().setName("Lead")
                    .setColor(Color.BLACK).setHasEffect(false).build();

            SAR.registerPartsForMaterial(sulphur, "ore", "dust", "crystal");
            MaterialSystem.getMaterialPart("sulphur_crystal").getData().addDataValue("burn", "1800");
            MaterialSystem.getMaterialPart("sulphur_ore").getData().addDataValue("drops", "oredict:crystalSulphur");

            SAR.registerPartsForMaterial(iron, vanillaParts);
            setDataDefaults("iron", true);

            SAR.registerPartsForMaterial(gold, vanillaParts);
            setDataDefaults("gold", true);

            SAR.registerPartsForMaterial(copper, metalParts);
            setDataDefaults("copper", true);

            SAR.registerPartsForMaterial(zinc, metalParts);
            setDataDefaults("zinc", true);

            SAR.registerPartsForMaterial(steel, alloyParts);
            setDataDefaults("steel", false);

            SAR.registerPartsForMaterial(brass, alloyParts);
            setDataDefaults("brass", false);

            // TODO Do this for all 'known metals'
            // Only register when present in oredict
            SAR.registerPartsForMaterial(tin, compatParts);
            setDataDefaults("tin", true);
            SAR.registerPartsForMaterial(aluminum, compatParts);
            setDataDefaults("aluminum", true);
            SAR.registerPartsForMaterial(lead, compatParts);
            setDataDefaults("lead", true);

        }
        catch(MaterialException e) {
            e.printStackTrace();
        }
        super.preInit(event);
    }

    @Override
    public String getName() {
        return "Materials";
    }

    public void setDataDefaults(String name, boolean isOre) {
        if(isOre) {
            MaterialSystem.getMaterialPart(name + "_ore").getData().addDataValue("resistance", "10");
            MaterialSystem.getMaterialPart(name + "_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
                    "1");
        }
        MaterialSystem.getMaterialPart(name + "lead_lattice").getData().addDataValue("transparent", "true");
    }

    private void registerPart(String name, PartType partType) {
        registerPart(
                new PartBuilder().setOwnerId(SteamAgeRevolution.instance.getID()).setName(name).setPartType(partType));
    }

    private void registerPart(PartBuilder partBuilder) {
        try {
            partBuilder.build();
        }
        catch(MaterialException e) {
            Base.instance.getLogger().getLogger().error(e);
        }
    }
}
