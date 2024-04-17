package xyz.brassgoggledcoders.steamagerevolution;

import net.minecraftforge.common.config.Config;

@Config(modid = SteamAgeRevolution.MODID, name = "acronym/" + SteamAgeRevolution.MODID)
public class SARConfig {
    public static BalanceConfig balance = new BalanceConfig();
    public static GenerationConfig generation = new GenerationConfig();

    public static class GenerationConfig {
        public boolean enableWorldGen = true;
        public boolean enableCopperGen = true;
        public boolean enableZincGen = true;
        public boolean enableSulphurGen = true;
    }

    public static class BalanceConfig {
        public int plateCount = 1;
        public int dustCount = 2;
    }
}
