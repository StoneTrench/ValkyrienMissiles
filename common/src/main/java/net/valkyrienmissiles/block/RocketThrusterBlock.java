package net.valkyrienmissiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;
import org.joml.Vector3d;
import org.joml.Vector3i;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class RocketThrusterBlock extends RocketBlock {
    public static final BooleanProperty POWERED;
    public static final Vector3d FORCE = new Vector3d(0, 2, 0);

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

        System.out.println("Control_p");

        RocketControl rocketControl = RocketControl.getOrCreate(serverShip);
        rocketControl.addThruster(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), FORCE);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        ServerLevel serverLevel = ShipAssembler.getServerLevel(level);
        if (serverLevel == null) return;

        ServerShip serverShip = VSGameUtilsKt.getShipManagingPos(serverLevel, blockPos);
        if (serverShip == null) return;

        System.out.println("Control_r");

        RocketControl rocketControl = RocketControl.getOrCreate(serverShip);
        rocketControl.removeThruster(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), FORCE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
