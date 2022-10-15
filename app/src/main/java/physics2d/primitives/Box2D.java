package physics2d.primitives;

import org.joml.Vector2f;

import lombok.Getter;
import lombok.Setter;
import physics2d.rigidbody.RigidBody2D;
import util.JMath;

public class Box2D {
    private Vector2f size = new Vector2f();
    @Getter private Vector2f halfSize = new Vector2f();
    @Getter @Setter private RigidBody2D rigidBody = null;

    public Box2D() {
        this.halfSize = new Vector2f(size.div(2));
    }

    public Box2D(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(size.div(2));
    }

    public Vector2f getLocalMin() {
        return new Vector2f(this.rigidBody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(this.rigidBody.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();

        Vector2f[] vertices = {
            new Vector2f(min.x, min.y),
            new Vector2f(min.x, max.y),
            new Vector2f(max.x, min.y),
            new Vector2f(max.x, max.y),
        };

        if (rigidBody.getRotation() != 0.0f) {
            for (Vector2f vert : vertices) {
                JMath.rotate(vert, rigidBody.getRotation(), rigidBody.getPosition());
            }
        }

        return vertices;
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
        this.halfSize.set(size.x / 2f, size.y / 2f); 
    }
}
