package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

public enum IOType {
    INPUT(0), OUTPUT(1),
    // Power is a special case input expected to be drawn from every tick
    POWER(-1);

    int networkID;

    IOType(int networkID) {
        this.networkID = networkID;
    }
}
