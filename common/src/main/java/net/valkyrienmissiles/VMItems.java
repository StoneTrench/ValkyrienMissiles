package net.valkyrienmissiles;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.valkyrienmissiles.block.RocketBlock;
import net.valkyrienmissiles.block.RocketThrusterBlock;
import net.valkyrienmissiles.item.ShipAssemblerItem;

public class VMItems {

    //#region Registry
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Main.MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Main.MOD_ID, Registry.BLOCK_REGISTRY);
    //#endregion Registry

    //#region Item Group
    public static final RegistrySupplier<Item> ITEM_GROUP_ICON = ITEMS.register("item_group_icon", () ->
            new Item(new Item.Properties()));
    public static final CreativeModeTab ITEM_GROUP = CreativeTabRegistry.create(new ResourceLocation(Main.MOD_ID, "item_group"), () ->
            new ItemStack(VMItems.ITEM_GROUP_ICON.get()));
    //#endregion Item Group

    public static final RegistrySupplier<Item> SHIP_ASSEMBLER = ITEMS.register("ship_assembler", () ->
            new ShipAssemblerItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));

    public static final BlockBehaviour.Properties PROPERTY = BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD);

    //#region Parts
    public static final RegistrySupplier<Block> ROCKET_BODY_THRUSTER_BLOCK = BLOCKS.register("rocket_body_thruster", () ->
            new RocketThrusterBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_FINS_BLOCK = BLOCKS.register("rocket_body_fins", () ->
            new RocketBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_BLOCK = BLOCKS.register("rocket_body", () ->
            new RocketBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_TIP_BLOCK = BLOCKS.register("rocket_body_tip", () ->
            new RocketBlock(PROPERTY));

    //#endregion Parts

    public static void RegisterAll(){
        BLOCKS.register();

        BLOCKS.forEach((e) -> {
            ITEMS.register(e.getId(), () -> new BlockItem(e.get(), new Item.Properties().tab(ITEM_GROUP)));
        });

        ITEMS.register();
    }
}
