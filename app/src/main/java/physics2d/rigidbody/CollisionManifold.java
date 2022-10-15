package physics2d.rigidbody;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CollisionManifold {
    private boolean isColliding;
    @Getter private Vector2f normal;
    @Getter private List<Vector2f> contactPoints;
    @Getter private float depth;

    public CollisionManifold() {
        this.normal = new Vector2f();
        this.contactPoints = new ArrayList<>(); // maybe fatal
        this.depth = 0f;
        isColliding = false;
    }

    public CollisionManifold(Vector2f normal, float depth) {
        this.normal = normal;
        this.contactPoints = new ArrayList<>();
        this.depth = depth;
        isColliding = true;
    }

    public void addContactPoint(Vector2f contact) {
        this.contactPoints.add(contact);
    }
}
