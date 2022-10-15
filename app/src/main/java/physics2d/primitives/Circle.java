package physics2d.primitives;

import org.joml.Vector2f;

import lombok.Getter;
import lombok.Setter;
import physics2d.rigidbody.RigidBody2D;

public class Circle {
    @Getter @Setter private float radius = 1f;
    @Setter private RigidBody2D rigidBody = null;

    public Vector2f getCenter() {
        return rigidBody.getPosition();
    }
}
