/*
package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class CrafttweakerModule extends ModuleBase {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        CraftTweakerAPI.registerClass(AlloyForgeTweaker.class);
        CraftTweakerAPI.registerClass(CastingBlockTweaker.class);
        CraftTweakerAPI.registerClass(CrucibleTweaker.class);
        CraftTweakerAPI.registerClass(DistillerTweaker.class);
        CraftTweakerAPI.registerClass(SteamFurnaceTweaker.class);
        CraftTweakerAPI.registerClass(SteamHammerTweaker.class);
        CraftTweakerAPI.registerClass(VatTweaker.class);
        CraftTweakerAPI.registerClass(GrinderTweaker.class);
        CraftTweakerAPI.registerClass(SteelworksTweaker.class);
        super.preInit(event);
    }

    @Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
        dependencies.add(new ModDependency("crafttweaker"));
        return dependencies;
    }

    @Override
    public String getName() {
        return "Crafttweaker Support";
    }
}
*/
