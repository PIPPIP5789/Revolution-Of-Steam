package xyz.brassgoggledcoders.steamagerevolution.utils.capability;

import java.util.concurrent.Callable;

public class NullFactory<CAP> implements Callable<CAP> {
    @Override
    public CAP call() throws Exception {
        throw new RuntimeException("Do not use the Factory");
    }

}