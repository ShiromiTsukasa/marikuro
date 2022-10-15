package physics2d.forces;

import physics2d.rigidbody.RigidBody2D;

public class ForceRegistration {
    public ForceGenerator fg;
    public RigidBody2D rb;

    public ForceRegistration(ForceGenerator fg, RigidBody2D rb) {
        this.fg = fg;
        this.rb = rb;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != ForceRegistration.class) return false;

        ForceRegistration fr = (ForceRegistration)obj;

        return fr.rb == this.rb && fr.fg == this.fg;
    }
}
