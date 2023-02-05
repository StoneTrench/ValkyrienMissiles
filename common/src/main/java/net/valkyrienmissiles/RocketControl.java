package net.valkyrienmissiles;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.api.ServerShipUser;
import org.valkyrienskies.core.impl.api.ShipForcesInducer;

import java.util.ArrayList;


@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class RocketControl implements ShipForcesInducer, ServerShipUser {

    @JsonIgnore
    private ServerShip ship;

    private final ArrayList<Pair<Vector3dc, Vector3dc>> Thrusters = new ArrayList<>();

    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        System.out.println("ApplyForces");

        if (ship == null) return;

        Thrusters.forEach(e -> {
            physShip.applyInvariantForceToPos(e.component1().add(0.5, 0.5, 0.5, new Vector3d()), e.component2());
        });
    }

    public void addThruster(Vector3d position, Vector3d force){
        Thrusters.add(new Pair<Vector3dc, Vector3dc>(position, force));
    }
    public void removeThruster(Vector3d position, Vector3d force){
        Thrusters.remove(new Pair<Vector3dc, Vector3dc>(position, force));
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

    @Nullable
    @Override
    public ServerShip getShip() {
        return ship;
    }

    @Override
    public void setShip(@Nullable ServerShip serverShip) {
        ship = serverShip;
    }
}
