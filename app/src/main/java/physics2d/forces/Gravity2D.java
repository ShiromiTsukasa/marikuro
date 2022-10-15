package physics2d.forces;

import org.joml.Vector2f;

import physics2d.rigidbody.RigidBody2D;

public class Gravity2D implements ForceGenerator {
    private Vector2f gravity;

    public Gravity2D(Vector2f force) {
        this.gravity = new Vector2f(force);
    }

    @Override
    public void updateForce(RigidBody2D body, float dt) {
        body.addForce(new Vector2f(gravity).mul(body.getMass()));
    }
}
