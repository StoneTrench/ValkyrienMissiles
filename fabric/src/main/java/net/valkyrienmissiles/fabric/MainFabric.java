package net.valkyrienmissiles.fabric;

import net.valkyrienmissiles.Main;
import net.fabricmc.api.ModInitializer;

public class MainFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Main.init();
    }
}
