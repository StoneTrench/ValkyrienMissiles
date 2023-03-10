package net.valkyrienmissiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.valkyrienmissiles.Main;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;

public class RocketThrusterBlock extends RocketBlock {
    public static final double THRUST = 2500.0;
    public static final BooleanProperty POWERED;

    public RocketThrusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        rocketControl.addThruster(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(THRUST)
        );
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        rocketControl.addThruster(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(THRUST)
        );
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, false);
        if (rocketControl == null) return;

        rocketControl.removeThruster(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(THRUST)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection()).setValue(POWERED, false);
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
