package jade;

import java.util.ArrayList;
import java.util.List;

import components.Component;
import lombok.Getter;

public class GameObject {
    private static long ID_COUNTER = 0;
    @Getter private long uid = -1;

    private String name;
    @Getter private List<Component> components;
    public Transform transform;
    @Getter private int zIndex;

    public GameObject(String name, Transform transform, int zIndex) {
        //! Potential problem when deserializing, uid is assigned before static ID_COUNTER is set
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;

        this.uid = ID_COUNTER++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c: components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componenClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componenClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateId();
        components.add(c);
        c.gameObject = this;
    }

    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }    

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for (Component c: components) {
            c.imgui();
        }
    }

    public static void init(long maxID) {
        ID_COUNTER = maxID;
    }
}
