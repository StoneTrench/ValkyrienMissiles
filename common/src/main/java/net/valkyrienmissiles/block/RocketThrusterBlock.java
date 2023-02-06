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
import net.valkyrienmissiles.Main;
import net.valkyrienmissiles.RocketControl;
import net.valkyrienmissiles.ShipAssembler;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class RocketThrusterBlock extends RocketBlock {
    public static final double THRUST = 5000.0;
    public static final BooleanProperty POWERED;

    public RocketThrusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);

        Main.Log("neighborChanged");

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        Main.Log("Control_p");

        rocketControl.addThruster(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(THRUST)
        );
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

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        Main.Log("onPlace");

        RocketControl rocketControl = GetControl(blockPos, level, true);
        if (rocketControl == null) return;

        Main.Log("Control_p");

        rocketControl.addThruster(
                ShipAssembler.ToDouble(blockPos),
                ShipAssembler.ToDouble(blockState.getValue(FACING).getNormal()).mul(THRUST)
        );
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        Main.Log("onRemove");

        RocketControl rocketControl = GetControl(blockPos, level, false);
        if (rocketControl == null) return;

        Main.Log("Control_r");

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
