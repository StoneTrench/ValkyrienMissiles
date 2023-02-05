package net.valkyrienmissiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3i;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.core.impl.game.ships.serialization.shipserver.dto.ShipDataCommon;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;

public class RocketThrusterBlock extends RocketBlock {
    public static final BooleanProperty POWERED;
    public static final Vector3d FORCE = new Vector3d(0, 8, 0);

    public RocketThrusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        ServerLevel serverLevel = ShipAssembler.getServerLevel(level);
        if (serverLevel == null) return;

        ServerShip serverShip = VSGameUtilsKt.getShipManagingPos(serverLevel, blockPos);
        if (serverShip == null) return;



        RocketControl rocketControl = RocketControl.getOrCreate(serverShip);
        rocketControl.addThruster(new Vector3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()), FORCE);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        ServerLevel serverLevel = ShipAssembler.getServerLevel(level);
        if (serverLevel == null) return;

        ServerShip serverShip = VSGameUtilsKt.getShipManagingPos(serverLevel, blockPos);
        if (serverShip == null) return;

        RocketControl rocketControl = RocketControl.getOrCreate(serverShip);
        rocketControl.removeThruster(new Vector3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()), FORCE);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
