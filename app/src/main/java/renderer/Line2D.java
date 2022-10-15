package renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class Line2D {
    @Getter @NonNull private Vector2f from;
    @Getter @NonNull private Vector2f to;
    @Getter private Vector3f color;
    @Getter private int lifetime;

    public int beginFrame() {
        this.lifetime--;
        return this.lifetime;
    }

    public Vector2f getStart() {
        return this.from;
    }

    public Vector2f getEnd() {
        return this.to;
    }

    public float lengthSquared() {
        return new Vector2f(to).sub(from).lengthSquared();
    }
}
