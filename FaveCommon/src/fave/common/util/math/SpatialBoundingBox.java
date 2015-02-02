/*
 * Developed as part of the Celeste FLAME project, 2013
 */

package fave.common.util.math;

/**
 * 
 * @author Erik Brisson <ebrisson@bu.edu>
 */

import com.jme3.math.Vector3f;
import java.util.logging.Logger;
// import javax.vecmath.Vector3f;

/*
 * SpatialBoundingBox will maintain its internal describing vectors.
 * These will start in a consistent state, and kept consistent on change to any of them
 * Default is a unit cube with unit intervals as sides
 */

public class SpatialBoundingBox {
    private static final Logger logger = Logger.getLogger(SpatialBoundingBox.class.getName());
    private Vector3f vmin;
    private Vector3f vmax;
    private Vector3f vcenter;
    private Vector3f vextent;
    private boolean minMaxValid;
    private boolean centerExtentValid;
    public SpatialBoundingBox() {
        vmin = new Vector3f(0f, 0f, 0f);
        vmax = new Vector3f(1f, 1f, 1f);
        vcenter = new Vector3f();
        vextent = new Vector3f();
        minMaxValid = true;
        centerExtentValid = false;
        updateInternal();
    }
    private void updateInternal() {
        if (!centerExtentValid) {     
            vcenter = (vmin.add(vmax)).mult(0.5f);
            vextent = (vmax.subtract(vmin));
            /*
            vcenter.set((vmin.x+vmax.x)/2f, (vmin.y+vmax.y)/2f, (vmin.z+vmax.z)/2f);
            vextent.set(vmax.x-vmin.x, vmax.y-vmin.y, vmax.z-vmin.z);
            */
            centerExtentValid = true;
        }
        else if (!minMaxValid) {       
            vmin.set(vcenter.subtract(vextent.mult(0.5f)));
            vmax.set(vcenter.add(vextent.mult(0.5f)));            
            /*
            Vector3f vt = new Vector3f(vextent.x/2f, vextent.y/2f, vextent.z/2f);
            vmin.set(vcenter.x-vt.x, vcenter.y-vt.y, vcenter.z-vt.z);
            vmax.set(vcenter.x+vt.x, vcenter.y+vt.y, vcenter.z+vt.z);
            */
            minMaxValid = true;              
        }
    }
    public void setMinMax(Vector3f vmin, Vector3f vmax) {
        this.vmin.set(vmin);
        this.vmax.set(vmax);
        minMaxValid = true;
        centerExtentValid = false;
        updateInternal();
    }
    public void setCenterExtent(Vector3f vcenter, Vector3f vextent) {
        this.vcenter.set(vcenter);
        this.vextent.set(vextent);
        minMaxValid = false;
        centerExtentValid = true;
        updateInternal();
    }
    public void setMinMax(float xmin, float ymin, float zmin, float xmax, float ymax, float zmax) {
        setMinMax(new Vector3f(xmin, ymin, zmin), new Vector3f(xmax, ymax, zmax));
    }
    public void setMinMaxSides(float xmin, float xmax, float ymin, float ymax, float zmin, float zmax) {
        setMinMax(new Vector3f(xmin, ymin, zmin), new Vector3f(xmax, ymax, zmax));
    }
    public void setCenterExtent(float xcenter, float ycenter, float zcenter, 
                                float xextent, float yextent, float zextent) {
        setCenterExtent(new Vector3f(xcenter, ycenter, zcenter), new Vector3f(xextent, yextent, zextent));
    }
    public Vector3f vmin() {
        return new Vector3f(vmin);
    } 
    public Vector3f vmax() {
        return new Vector3f(vmax);
    }
    public Vector3f vcenter() {
        return new Vector3f(vcenter);
    } 
    public Vector3f vextent() {
        return new Vector3f(vextent);
    }
    public float xmin() {
        return vmin.x;
    }
    public float ymin() {
        return vmin.y;
    }
    public float zmin() {
        return vmin.z;
    }
    public float xmax() {
        return vmax.x;
    }
    public float ymax() {
        return vmax.y;
    }
    public float zmax() {
        return vmax.z;
    }
    public float xcenter() {
        return vcenter.x;
    }
    public float ycenter() {
        return vcenter.y;
    }
    public float zcenter() {
        return vcenter.z;
    }
    public float xextent() {
        return vextent.x;
    }
    public float yextent() {
        return vextent.y;
    }
    public float zextent() {
        return vextent.z;
    }
    public void dump(String mess) {
        System.out.println(mess + "vmin, vextent = " + vmin + " " + vmax);
        System.out.println(mess + "vcenter, vextent = " + vcenter + " " + vextent);
        System.out.println(mess + "minMaxValid, centerExtentValid = " + minMaxValid + " " + centerExtentValid);
    }
    public void dump() {
        dump("");
    }
}
