package xyz.brassgoggledcoders.steamagerevolution.worldgen;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARConfig;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.worldgen.OreGenerator.OreEntry;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
public class ModuleWorldgen extends ModuleBase {

    @Override
    public void init(FMLInitializationEvent event) {
        if(SARConfig.generation.enableWorldGen) {
            GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
            SubBlockSystem system = SteamAgeRevolution.instance.getSubBlockSystem();
            if(SARConfig.generation.enableCopperGen) {
                OreGenerator.oresToGenerate
                        .add(new OreEntry("Copper", 16, 64, 8, system.getSubBlock("copper_ore").getBlockState()));
            }
            if(SARConfig.generation.enableZincGen) {
                OreGenerator.oresToGenerate
                        .add(new OreEntry("Zinc", 10, 40, 6, system.getSubBlock("zinc_ore").getBlockState()));
            }
            if(SARConfig.generation.enableSulphurGen) {
                OreGenerator.oresToGenerate
                        .add(new OreEntry("Sulphur", 6, 55, 4, system.getSubBlock("sulphur_ore").getBlockState()));
            }
        }
        super.init(event);
    }

    @Override
    public String getName() {
        return "Worldgen";
    }
}
