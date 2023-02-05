package net.valkyrienmissiles;

import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.impl.api.ShipForcesInducer;

import java.util.ArrayList;

public class RocketControl implements ShipForcesInducer {

    private final ArrayList<Pair<Vector3ic, Vector3dc>> Thrusters = new ArrayList<>();

    @Override
    public void applyForces(@NotNull PhysShip physShip) {

        Thrusters.forEach(e -> {
            physShip.applyInvariantForceToPos((Vector3dc)e.component1(), e.component2());
        });
    }

    public void addThruster(Vector3i position, Vector3d force){
        Thrusters.add(new Pair<Vector3ic, Vector3dc>(position, force));
    }
    public void removeThruster(Vector3i position, Vector3d force){
        Thrusters.remove(new Pair<Vector3ic, Vector3dc>(position, force));
    }

    @NotNull
    public static RocketControl getOrCreate(@NotNull ServerShip serverShip){
        RocketControl rocketControl = serverShip.getAttachment(RocketControl.class);

        if (rocketControl == null) {
            rocketControl = new RocketControl();
            serverShip.saveAttachment(RocketControl.class, rocketControl);
        }

        return rocketControl;
    }
}
