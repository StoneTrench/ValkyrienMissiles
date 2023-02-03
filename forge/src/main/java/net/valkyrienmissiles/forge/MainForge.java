package net.valkyrienmissiles.forge;

import dev.architectury.platform.forge.EventBuses;
import net.valkyrienmissiles.Main;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
public class MainForge {
    public MainForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Main.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Main.init();
    }
}
