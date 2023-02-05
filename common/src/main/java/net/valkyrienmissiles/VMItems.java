package net.valkyrienmissiles;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.valkyrienmissiles.block.RocketBlock;
import net.valkyrienmissiles.block.RocketThrusterBlock;
import net.valkyrienmissiles.item.ShipAssemblerItem;

public class VMItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Main.MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Main.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<Item> ITEM_GROUP_ICON = ITEMS.register("item_group_icon", () ->
            new Item(new Item.Properties()));
    public static final CreativeModeTab ITEM_GROUP = CreativeTabRegistry.create(new ResourceLocation(Main.MOD_ID, "item_group"), () ->
            new ItemStack(VMItems.ITEM_GROUP_ICON.get()));


    public static final RegistrySupplier<Item> SHIP_ASSEMBLER = ITEMS.register("ship_assembler", () ->
            new ShipAssemblerItem(new Item.Properties().tab(ITEM_GROUP)));

    //#region parts
    public static final RegistrySupplier<Block> ROCKET_BODY_THRUSTER_BLOCK = BLOCKS.register("rocket_body_thruster", () ->
            new RocketThrusterBlock(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistrySupplier<Item> ROCKET_BODY_THRUSTER_ITEM = ITEMS.register("rocket_body_thruster", () ->
            new BlockItem(ROCKET_BODY_THRUSTER_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistrySupplier<Block> ROCKET_BODY_FINS_BLOCK = BLOCKS.register("rocket_body_fins", () ->
            new RocketBlock(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistrySupplier<Item> ROCKET_BODY_FINS_ITEM = ITEMS.register("rocket_body_fins", () ->
            new BlockItem(ROCKET_BODY_FINS_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistrySupplier<Block> ROCKET_BODY_BLOCK = BLOCKS.register("rocket_body", () ->
            new RocketBlock(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistrySupplier<Item> ROCKET_BODY_ITEM = ITEMS.register("rocket_body", () ->
            new BlockItem(ROCKET_BODY_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistrySupplier<Block> ROCKET_BODY_TIP_BLOCK = BLOCKS.register("rocket_body_tip", () ->
            new RocketBlock(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistrySupplier<Item> ROCKET_BODY_TIP_ITEM = ITEMS.register("rocket_body_tip", () ->
            new BlockItem(ROCKET_BODY_TIP_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));

    //#endregion parts

    public static void RegisterAll(){
        ITEMS.register();
        BLOCKS.register();
    }
}
