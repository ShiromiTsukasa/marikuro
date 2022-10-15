package scenes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import components.Component;
import components.ComponentDeserializer;
import imgui.ImGui;
import jade.Camera;
import jade.GameObject;
import jade.GameObjectDeserializer;
import renderer.Renderer;

public abstract class Scene {
    protected Renderer renderer = new Renderer();

    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject = null;
    protected boolean levelLoaded = false;

    public Scene() {
        
    }

    public void init() {
        
    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        gameObjects.add(go);

        if (isRunning) {
            go.start();
            this.renderer.add(go);
        }
    }

    public abstract void update(float dt);
    public abstract void render();

    public Camera getCamera() {
        return this.camera;
    }

    public void sceneImgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui() {

    }

    public void saveExit() {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Component.class, new ComponentDeserializer())
            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
            .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Component.class, new ComponentDeserializer())
            .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
            .create();

        String infile = "";
        try {
            infile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            File file = new File("level.txt");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (!infile.equals("")) {
            long maxGoID = -1;
            long maxCompID = -1;

            GameObject[] objs = gson.fromJson(infile, GameObject[].class);
            for (int i = 0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);

                for (Component c : objs[i].getComponents()) {
                    if (c.getUid() > maxCompID) {
                        maxCompID = c.getUid();
                    }
                }

                if (objs[i].getUid() > maxGoID) {
                    maxGoID = objs[i].getUid();
                }
            }

            maxGoID++;
            maxCompID++;

            // System.out.println(maxGoID);
            // System.out.println(maxCompID);

            GameObject.init(maxGoID);
            Component.init(maxCompID);

            this.levelLoaded = true;
        }
    }
}
