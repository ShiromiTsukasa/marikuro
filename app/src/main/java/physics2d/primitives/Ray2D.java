package physics2d.primitives;

import org.joml.Vector2f;

import lombok.Getter;


public class Ray2D {
    @Getter private Vector2f origin;
    @Getter private Vector2f direction;

    public Ray2D(Vector2f origin, Vector2f direction) {
        this.origin = origin;
        this.direction = direction;
        this.direction.normalize();
    }
}