package physics2d.primitives;

import org.joml.Vector2f;

import lombok.Setter;
import physics2d.rigidbody.RigidBody2D;

public class AABB {
    private Vector2f center = new Vector2f();
    private Vector2f size = new Vector2f();
    private Vector2f halfSize = new Vector2f();
    @Setter private RigidBody2D rigidBody;

    public AABB() {
        this.halfSize = new Vector2f(size.div(2));
    }

    public AABB(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
    }

    public Vector2f getMin() {
        return new Vector2f(this.rigidBody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getMax() {
        return new Vector2f(this.rigidBody.getPosition()).add(this.halfSize);
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
        this.halfSize.set(size.x / 2f, size.y / 2f); 
    }
}
