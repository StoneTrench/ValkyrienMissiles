package net.valkyrienmissiles;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3i;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.datastructures.DenseBlockPosSet;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShipAssembler {
    public static ServerShip createShipFromBlocks(ServerLevel level, BlockPos start){
        DenseBlockPosSet blocks = new DenseBlockPosSet();

        blocks.add(start.getX(), start.getY(), start.getZ());

        collectBlocks(level, blocks, start, (state) -> !state.isAir());

        return ShipAssemblyKt.createNewShipWithBlocks(start, blocks, level);
    }

    private static void collectBlocks(ServerLevel level, DenseBlockPosSet blocks, BlockPos start, Predicate<BlockState> predicate){

        DenseBlockPosSet closed = new DenseBlockPosSet();
        ObjectArrayList<BlockPos> open = new ObjectArrayList<>();

        directions(start, open::push);

        while (!open.isEmpty()) {
            BlockPos pos = open.pop();

            if (predicate.test(level.getBlockState(pos)) && pos.distSqr(ToVec3i(start)) < 16384) {
                blocks.add(pos.getX(), pos.getY(), pos.getZ());
                directions(pos, (e) -> {
                    if (!closed.contains(e.getX(), e.getY(), e.getZ())) {
                        closed.add(e.getX(), e.getY(), e.getZ());
                        open.push(e);
                    }
                });
            }
        }

    }

    private static void directions(BlockPos center, Consumer<BlockPos> consumer) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        consumer.accept(center.offset(x, y, z));
                    }
                }
            }
        }
    }

    @Nullable
    public static ServerLevel getServerLevel(@NotNull Level level){
        if (level.isClientSide()) return null;
        if (level.getServer() == null) return null;
        return level.getServer().getLevel(level.dimension());
    }

    @NotNull
    public static Vector3d ToDouble(Vec3i pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }
    @NotNull
    public static Vector3d ToDouble(BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }
    @NotNull
    public static Vector3i ToInteger(BlockPos pos) {
        return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
    }
    @NotNull
    public static Vec3i ToVec3i(BlockPos pos) {
        return new Vec3i(pos.getX(), pos.getY(), pos.getZ());
    }
}
