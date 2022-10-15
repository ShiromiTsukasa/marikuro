package physics2d.primitives;

import org.joml.Vector2f;

import components.Component;

public class Collider2D extends Component {
    protected Vector2f offset = new Vector2f();

    // TODO: Implement inertia tensors
    // public abstract float getInertiaTensor(float mass);
}
