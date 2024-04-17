package xyz.brassgoggledcoders.steamagerevolution.machinesystem;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Convenience interface for allowing access to world and position of TE/Multiblock/Entity machines.
public interface IMachine {

    public MachineType getMachineType();

    public World getMachineWorld();

    public BlockPos getMachinePos();
}
