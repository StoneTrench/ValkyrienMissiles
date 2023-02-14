package net.valkyrienmissiles;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.valkyrienmissiles.item.ShipAssemblerItem;

public class VMItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Main.MOD_ID, Registry.ITEM_REGISTRY);

    //#region Item Group
    public static final RegistrySupplier<Item> ITEM_GROUP_ICON = ITEMS.register("item_group_icon", () ->
            new Item(new Item.Properties()));
    public static final CreativeModeTab ITEM_GROUP = CreativeTabRegistry.create(new ResourceLocation(Main.MOD_ID, "item_group"), () ->
            new ItemStack(VMItems.ITEM_GROUP_ICON.get()));
    //#endregion Item Group

    public static final RegistrySupplier<Item> SHIP_ASSEMBLER = ITEMS.register("ship_assembler", () ->
            new ShipAssemblerItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));

    public static void Register(){
        VMBlocks.BLOCKS.forEach((e) -> {
            ITEMS.register(e.getId(), () -> new BlockItem(e.get(), new Item.Properties().tab(ITEM_GROUP)));
        });

        ITEMS.register();
    }
}
