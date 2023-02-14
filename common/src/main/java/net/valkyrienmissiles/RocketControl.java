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
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.core.impl.pipelines.SegmentUtils;

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

    private Vector3dc ForwardNormal;
    private final ArrayList<Pair<Vector3dc, Vector3dc>> Thrusters = new ArrayList<>();
    private final ArrayList<Pair<Vector3dc, Vector3dc>> Fins = new ArrayList<>();

    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        if (ship == null || ForwardNormal == null) return;

        var physShipImpl = ((PhysShipImpl)physShip);
        var segment = physShipImpl.getSegments().getSegments().get(0).getSegmentDisplacement();
        var omega = SegmentUtils.INSTANCE.getOmega(physShipImpl.getPoseVel(), segment, new Vector3d());

        try {
            Thrusters.forEach(e -> {
                var pos = e.component1()
                        .add(0.5, 0.5, 0.5, new Vector3d())
                        .sub(physShip.getTransform().getPositionInShip());
                var force = physShip.getTransform().getShipToWorld().transformDirection(e.component2().get(new Vector3d()));

                if (force.isFinite() && physShipImpl.getPoseVel().getVel().lengthSquared() < 2500) {
                    physShip.applyInvariantForceToPos(force, pos);
                }
            });
        }
        catch (Exception e){
            Main.Error(e.toString());
        }

        try {
            Fins.forEach(e -> {
                var pos = e.component1()
                        .add(0.5, 0.5, 0.5, new Vector3d())
                        .sub(physShip.getTransform().getPositionInShip());

                var scalar = e.component2().length();
                var shipDir = new Vector3d(ForwardNormal.x(), ForwardNormal.y(), ForwardNormal.z());

                SegmentUtils.INSTANCE.transformDirectionWithoutScale(
                        physShipImpl.getPoseVel(),
                        segment,
                        shipDir,
                        shipDir
                );

                var angleBetween = shipDir.angle(ForwardNormal);
                var idealAngularAcceleration = new Vector3d();
                if (angleBetween > .01) {
                    var stabilizationRotationAxisNormalized = shipDir.cross(ForwardNormal, new Vector3d()).normalize();
                    idealAngularAcceleration.add(
                            stabilizationRotationAxisNormalized.mul(angleBetween, stabilizationRotationAxisNormalized)
                    );
                }

                idealAngularAcceleration.sub(
                        omega.x(),
                        omega.y(),
                        omega.z()
                );

                var stabilizationTorque = SegmentUtils.INSTANCE.transformDirectionWithScale(
                        physShipImpl.getPoseVel(),
                        segment,
                        physShipImpl.getInertia().getMomentOfInertiaTensor().transform(
                                SegmentUtils.INSTANCE.invTransformDirectionWithoutScale(
                                        physShipImpl.getPoseVel(),
                                        segment,
                                        idealAngularAcceleration,
                                        idealAngularAcceleration
                                )
                        ),
                        idealAngularAcceleration
                );

                //stabilizationTorque = stabilizationTorque.mul(scalar);
                physShipImpl.applyInvariantTorque(stabilizationTorque);
            });
        }
        catch (Exception e){
            Main.Error(e.toString());
        }
    }

    public void addThruster(Vector3d position, Vector3d force){
        Thrusters.add(new Pair<Vector3dc, Vector3dc>(position, force));
    }
    public void removeThruster(Vector3d position, Vector3d force){
        Thrusters.remove(new Pair<Vector3dc, Vector3dc>(position, force));
    }
    public void addFin(Vector3d position, Vector3d direction){
        Fins.add(new Pair<Vector3dc, Vector3dc>(position, direction));
    }
    public void removeFin(Vector3d position, Vector3d direction){
        Fins.remove(new Pair<Vector3dc, Vector3dc>(position, direction));
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
