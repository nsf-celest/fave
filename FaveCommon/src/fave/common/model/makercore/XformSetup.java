/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.model.makercore;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fave.common.util.math.EulerFrame;
import java.util.logging.Logger;

/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * This class provides a way to set up scaling, translation, and rotation
 * with a specified pivot point, allowing placement of the models with
 * accurate alignment
 * The pivot is specified by fractional location in the bounding box
 * of the model.
 * Rotation is about the pivot point.
 * Translation moves the pivot point to the specified destination.
 * 
 * XformNodeSetup sets transformation values in the node that it returns, so to move it
 * again either compose with current transformation or put under a new node
 */


/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class XformSetup {
    private static final Logger logger = Logger.getLogger(fave.common.model.makercore.XformSetup.class.getName());   
    Vector3f vdest = new Vector3f(Vector3f.ZERO); 
    Vector3f vpivot = new Vector3f(Vector3f.ZERO);
    boolean doEulerRot = false; 
    Vector3f verot = new Vector3f(Vector3f.ZERO);
    boolean doAngleRot = false;
    Vector3f vrotAxis = new Vector3f(Vector3f.UNIT_Y);
    float rotAngle = 0f;
    float xscale = 1f;
    float yscale = 1f;
    float zscale = 1f;
    
    // public XformSetup() {
    // }
    public void setDestination(Vector3f vdest)
    {
        this.vdest = vdest;
    }
    public void setDestination(float xdest, float ydest, float zdest)
    {
        this.vdest.set(xdest, ydest, zdest);
    }
    public void setPivot(Vector3f vpivot)
    {
        this.vpivot = vpivot;
    }
    public void setEulerRot(float xrot, float yrot, float zrot)
    {
        doEulerRot = true;
        doAngleRot = false;
        verot.set(xrot, yrot, zrot);
    }
    public void setEulerRot(Vector3f vrot) {
        setEulerRot(vrot.x, vrot.y, vrot.z);
    }
    public void setAngleAxisRot(float angle, Vector3f vaxis) {
        doEulerRot = false;
        doAngleRot = true;
        vrotAxis = vaxis;
        rotAngle = angle;
    }   
    public void setScale(float scale) {
        setScale(scale, scale, scale);
    }
    public void setScale(float xscale, float yscale, float zscale) {
        this.xscale = xscale;
        this.yscale = yscale;
        this.zscale = zscale;
    }   
    
    public void setEulerFrame(EulerFrame frame) {
        setDestination(frame.origin());
        setEulerRot(frame.rot());
    }
  
    public Vector3f getPivot() {
        return vpivot;
    }
    public Node getNodePlaced(Node nin) {
        Node nPlaced;
        Vector3f vpivotPlaced = getPivot();
        if (nin == null)
            nPlaced = new Node();
        else
            nPlaced = nin;
        nPlaced.setLocalScale(xscale, yscale, zscale);
        if (doAngleRot || doEulerRot) {
            if (doEulerRot) {
                nPlaced.move(vpivotPlaced);
                nPlaced.rotate(verot.x, verot.y, verot.z);
                nPlaced.move(vpivotPlaced.negate());
            }
            else if (doAngleRot) {
                Matrix3f Mrot = new Matrix3f();
                Mrot.fromAngleAxis(rotAngle, vrotAxis);
                nPlaced.setLocalRotation(Mrot);
                vpivotPlaced = Mrot.mult(vpivotPlaced);
            }
        }              
        nPlaced.move(vdest.x-xscale*vpivotPlaced.x, vdest.y-yscale*vpivotPlaced.y, vdest.z-zscale*vpivotPlaced.z);
        return nPlaced;
    }
}
