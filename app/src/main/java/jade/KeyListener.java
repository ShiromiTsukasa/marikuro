package jade;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class KeyListener {
    private static KeyListener __instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {

    }

    public static KeyListener get() {
        if (__instance == null) {
            __instance = new KeyListener();
        }

        return __instance;
    }

    public static void keyCallback(long lpGlfwWindow, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
