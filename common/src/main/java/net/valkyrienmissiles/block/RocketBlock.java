package net.valkyrienmissiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class RocketBlock extends Block {
    public static final DirectionProperty FACING;

    public RocketBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState) blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Nullable
    protected RocketControl GetControl(BlockPos blockPos, Level level, boolean create){
        ServerLevel serverLevel = ShipAssembler.getServerLevel(level);
        if (serverLevel == null) return null;

        LoadedServerShip serverShip = VSGameUtilsKt.getShipObjectManagingPos(serverLevel, blockPos);
        if (serverShip == null) {
            return null;
        }

        if (create)
            return RocketControl.getOrCreate(serverShip);
        else
            return serverShip.getAttachment(RocketControl.class);
    }

    static {
        FACING = DirectionalBlock.FACING;
    }
}
