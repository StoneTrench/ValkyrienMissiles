package net.valkyrienmissiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.valkyrienmissiles.Main;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;

public class RocketFinBlock extends RocketBlock {
    public static final double SCALAR = 100.0;

    public RocketFinBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        rocketControl.addFin(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(SCALAR)
        );
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        rocketControl.addFin(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(SCALAR)
        );
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        RocketControl rocketControl = GetControl(blockPos, level, false);
        if (rocketControl == null) return;

        rocketControl.removeFin(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(SCALAR)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }
}
