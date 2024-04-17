/*
package xyz.brassgoggledcoders.steamagerevolution.compat.theoneprobe;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class OneProbeModule extends ModuleBase {

    @Override
    public String getName() {
        return "TheOneProbe Support";
    }

    @Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
        dependencies.add(new ModDependency("theoneprobe"));
        return dependencies;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe",
                "xyz.brassgoggledcoders.steamagerevolution.compat.theoneprobe.GetTheOneProbe");
    }

}
*/
