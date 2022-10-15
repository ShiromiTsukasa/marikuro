package components;

import org.joml.Vector2f;

import lombok.Getter;
import lombok.Setter;
import renderer.Texture;

public class Sprite {
    @Getter @Setter private float width, height;
    @Getter @Setter private Texture texture = null;
    @Getter @Setter private Vector2f[] texCoords = new Vector2f[] {
        new Vector2f(1, 1),
        new Vector2f(1, 0),
        new Vector2f(0, 0),
        new Vector2f(0, 1),
    };

    public int getTexID() {
        return texture == null ? -1 : texture.getTexID();
    }
}
