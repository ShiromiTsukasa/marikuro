package scenes;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import components.GridLines;
import components.MouseControls;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import imgui.ImVec2;
import jade.Camera;
import jade.GameObject;
import jade.MouseListener;
import jade.Prefabs;
import jade.Transform;
import physics2d.PhysicsSystem2D;
import physics2d.rigidbody.RigidBody2D;
import renderer.DebugDraw;
import util.AssetPool;
import util.Colors;

public class LevelEditorScene extends Scene {
    private Spritesheet sprites;

    private GameObject levelEditorsStuffs = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);
    private PhysicsSystem2D physics = new PhysicsSystem2D(1f / 60f, new Vector2f(0, -10));
    private Transform obj1, obj2;
    RigidBody2D rb1, rb2;

    public LevelEditorScene() {

    }
    
    @Override
    public void init() {
        levelEditorsStuffs.addComponent(new MouseControls());
        levelEditorsStuffs.addComponent(new GridLines());

        obj1 = new Transform(new Vector2f(100, 500));
        obj2 = new Transform(new Vector2f(200, 500));
        rb1 = new RigidBody2D();
        rb2 = new RigidBody2D();
        rb1.setRawTransform(obj1);
        rb2.setRawTransform(obj2);
        rb1.setMass(100f);
        rb2.setMass(200f);

        physics.addRigidBody(rb1);
        physics.addRigidBody(rb2);

        loadResources();
        
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("../assets/images/spritesheets/decorationsAndBlocks.png");

        if (levelLoaded) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0); 
            }
            return;
        }

    }

    private void loadResources() {
        // TODO: fix order of loading, should not matter which order they are loaded in
        AssetPool.getShader("../assets/shaders/default.glsl");
        
        AssetPool.addSpritesheet("../assets/images/spritesheets/decorationsAndBlocks.png",
        new Spritesheet(
            AssetPool.getTexture("../assets/images/spritesheets/decorationsAndBlocks.png"),
            16,
            16,
            81,
            0
            )
        );
        AssetPool.getTexture("../assets/images/blendImage2.png");

        for (GameObject g: gameObjects) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        // System.out.println("FPS: " + (1.0f/dt));
        levelEditorsStuffs.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        // DebugDraw.addBox2D(obj1.position, new Vector2f(32, 32), 0f, Colors.RED);
        // DebugDraw.addBox2D(obj2.position, new Vector2f(32, 32), 0f, new Vector3f(.2f, .8f, .1f));
        // physics.update(dt);
    }

    @Override
    public void render() {
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);

        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                // System.out.println("Button " + i + " clicked");
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);

                // Attach to mouse cursor
                levelEditorsStuffs.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
