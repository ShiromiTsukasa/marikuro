package physics2d.forces;

import physics2d.rigidbody.RigidBody2D;

public interface ForceGenerator {
    void updateForce(RigidBody2D body, float dt);
}
