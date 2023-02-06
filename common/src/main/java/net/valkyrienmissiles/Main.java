package net.valkyrienmissiles;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Main {
    public static final String MOD_ID = "valkyrienmissiles";

    public static void init() {
        VMItems.RegisterAll();
    }

    public static void Log(String text){
        if (true) return;
        System.out.println(text);
    }
}
