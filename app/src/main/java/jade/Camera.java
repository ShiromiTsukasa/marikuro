package jade;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import lombok.Getter;

public class Camera {
    private Matrix4f viewMatrix;
    @Getter private Matrix4f projectionMatrix, inverseProjection, inverseView;
    public Vector2f position;
    @Getter private Vector2f projectionSize = new Vector2f(32f * 40f, 32f * 21f);

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0, projectionSize.x, 0, projectionSize.y, 0, 100);
        projectionMatrix.invert(inverseProjection);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0, 0, -1);
        Vector3f cameraUp = new Vector3f(0, 1, 0);
        this.viewMatrix.identity();

        viewMatrix.lookAt(
            new Vector3f(position.x, position.y, 20),
            cameraFront.add(position.x, position.y, 0),
            cameraUp
        );

        this.viewMatrix.invert(inverseView);

        return this.viewMatrix;
    }
}
