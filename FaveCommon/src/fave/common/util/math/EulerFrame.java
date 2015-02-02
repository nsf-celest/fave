package fave.common.util.math;

// import javax.vecmath.Vector3f;
import com.jme3.math.Vector3f;
import java.util.logging.Logger;

public class EulerFrame {
    private static final Logger logger = Logger.getLogger(EulerFrame.class.getName());
    private Vector3f porigin;
    private Vector3f vrot;
    public EulerFrame() {
        porigin = new Vector3f();
        vrot = new Vector3f();
    };
    public EulerFrame(Vector3f origin, Vector3f rot) {
        porigin = origin;
        vrot = rot;
    }
    public EulerFrame(float xorigin, float yorigin, float zorigin,
                      float xrot, float yrot, float zrot) {
        porigin = new Vector3f(xorigin, yorigin, zorigin);
        vrot    = new Vector3f(xrot, yrot, zrot);
    };
    public int setOrigin(float xorigin, float yorigin, float zorigin) {
        porigin.x = xorigin;
        porigin.y = yorigin;
        porigin.z = zorigin;
        return 1;
    }
    public int setRot(float xrot, float yrot, float zrot) {
        vrot.x = xrot;
        vrot.y = yrot;
        vrot.z = zrot;
        return 1;
    }
    public Vector3f origin() {
        return porigin;
    }
    public Vector3f rot() {
        return vrot;
    }
    public void dump(String mess) {
        System.out.println(mess + "origin, rot = " + porigin + " " + vrot);
    }
    public void dump() {
        dump("");
    }

};
