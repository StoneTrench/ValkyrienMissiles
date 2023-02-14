package net.valkyrienmissiles;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.valkyrienmissiles.block.RocketBlock;
import net.valkyrienmissiles.block.RocketFinBlock;
import net.valkyrienmissiles.block.RocketThrusterBlock;

public class VMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Main.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final BlockBehaviour.Properties PROPERTY = BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD);

    public static final RegistrySupplier<Block> ROCKET_BODY_THRUSTER_BLOCK = BLOCKS.register("rocket_body_thruster", () ->
            new RocketThrusterBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_FINS_BLOCK = BLOCKS.register("rocket_body_fins", () ->
            new RocketFinBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_BLOCK = BLOCKS.register("rocket_body", () ->
            new RocketBlock(PROPERTY));

    public static final RegistrySupplier<Block> ROCKET_BODY_TIP_BLOCK = BLOCKS.register("rocket_body_tip", () ->
            new RocketBlock(PROPERTY));

    public static void Register(){
        VMBlocks.BLOCKS.register();
    }
}
