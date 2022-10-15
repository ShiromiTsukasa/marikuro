package physics2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import physics2d.forces.ForceRegistry;
import physics2d.forces.Gravity2D;
import physics2d.rigidbody.RigidBody2D;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private List<RigidBody2D> rigidBodies;
    private Gravity2D gravity;
    private float fixedUpdateDt;

    public PhysicsSystem2D(float fixedUpdateDt, Vector2f gravity) {
        this.forceRegistry = new ForceRegistry();
        this.rigidBodies = new ArrayList<>();
        this.gravity = new Gravity2D(gravity);
        this.fixedUpdateDt = fixedUpdateDt;
    }

    public void update(float dt) {
        fixedUpdate();
    }

    public void fixedUpdate() {
        forceRegistry.updateForces(fixedUpdateDt);

        for (int i = 0; i < rigidBodies.size(); i++) {
            rigidBodies.get(i).physicsUpdate(fixedUpdateDt);
        }
    }

    public void addRigidBody(RigidBody2D body) {
        this.rigidBodies.add(body);
        this.forceRegistry.add(body, gravity);
    }
}
