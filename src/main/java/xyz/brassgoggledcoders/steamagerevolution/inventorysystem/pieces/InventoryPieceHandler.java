package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;

public abstract class InventoryPieceHandler<H extends INBTSerializable<NBTTagCompound>>
        extends InventoryPiece<InventoryBasic> implements INBTSerializable<NBTTagCompound> {
    H handler;
    // Null in the case of basic inventory, only defined by recipe machines. TODO?
    @Nullable
    IOType type;

    public InventoryPieceHandler(IOType type, H handler, int xPos, int yPos, int textureX, int textureY, int xSize,
            int ySize, int offset) {
        super(xPos, yPos, textureX, textureY, xSize, ySize, offset);
        this.handler = handler;
        this.type = type;
    }

    @Nullable
    public IOType getType() {
        return type;
    }

    public H getHandler() {
        return handler;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = this.getHandler().serializeNBT();
        tag.setString("name", getName());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        name = nbt.getString("name");
        this.getHandler().deserializeNBT(nbt);
    }
}