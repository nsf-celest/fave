/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.nav;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import fave.common.jme.util.CollisionManagement;
import fave.common.sg.ISceneGraph;
import fave.common.util.math.GeomUtility;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class NavDoitNew implements INav {
    private static final Logger logger = Logger.getLogger(fave.common.nav.NavDoitNew.class.getName());

    private Camera cam = null;
    CollisionManagement collisionMgr;
    public NavDoitNew(Camera cam, CollisionManagement collisionMgr) {
        this.cam = cam;
        this.collisionMgr = collisionMgr;
    }

    ISceneGraph sceneGraphSender;
    public void setSceneGraphSender(ISceneGraph sceneGraphSender) {
        this.sceneGraphSender = sceneGraphSender;
    }
    
    VEPickerNew vePicker;
    public void setVEPicker(VEPickerNew picker) {
        vePicker = picker;
    }
    
    boolean done = false;
    public boolean getDone() {
        return done;
    }

    boolean left = false, right = false, forward = false, backward = false;
    boolean lookLeft = false, lookRight = false, lookUp = false, lookDown = false;
    float lrspeed;
    float fbspeed;
    float lrLookSpeed;
    float udLookSpeed;
    boolean execute = true;
    boolean setLookmark = false;
    boolean gotoLookmark = false;      
    Vector3f vposn = new Vector3f();
    Vector3f vlook = new Vector3f();
    Vector3f vposnLookmark = new Vector3f();
    Vector3f vlookLookmark = new Vector3f();
    boolean lookmarkSet = false;

    public void update () {
        CharacterControl player = null;
        if (collisionMgr != null)
             player = collisionMgr.getPlayer();
// if (left || right || forward || backward)
// System.out.println("NavDoitNew.update: left, right, forward, backward = "+left+" "+right+" "+forward+" "+backward);
        // player incremental move set using current camera direction 
        Vector3f walkDirection = new Vector3f(0f, 0f, 0f);
        boolean lrmove = left^right;
        boolean fbmove = forward^backward;
        float lrm = lrspeed;
        float fbm = fbspeed;
        if (lrmove && fbmove) {   // normalize diag motion to be equal to average of requested x and z
            float fac = 0.5f * (lrm+fbm)/(float)(Math.sqrt((double)(lrm*lrm+fbm*fbm)));
            lrm *= fac;
            fbm *= fac;
        }
        if (lrmove || fbmove) {
// System.out.println("NavDoitNew.update: lrm, fbm = "+lrm+" "+fbm);
            Vector3f vahead = cam.getDirection().clone();
            vahead.y = 0f;
            Vector3f vleft = cam.getLeft().clone();
            if (lrmove) {
                if (left)
                    walkDirection.addLocal(vleft.mult(lrm));
                else
                    walkDirection.addLocal(vleft.negate().mult(lrm));
            }
            if (fbmove) {
                if (forward)
                    walkDirection.addLocal(vahead.mult(fbm));
                else
                    walkDirection.addLocal(vahead.negate().mult(fbm));
            }
            walkDirection.y = 0f;
// System.out.println("NavDoitNew.update: walkDirection = "+walkDirection);
        }
        if (player != null)
            player.setWalkDirection(walkDirection);
        else
            vposn.addLocal(walkDirection);
        
        // view direction updated
        boolean lrlook = lookLeft^lookRight;
        boolean udlook = lookUp^lookDown;
        final float upLimit = (float)Math.sin(GeomUtility.RPD*80d);  // use to restrict max up and down look dir
        Vector3f camAhead = cam.getDirection().clone();
        if (lrlook || udlook) {
            Vector3f camLeft = cam.getLeft().clone();
            Vector3f camUp = cam.getUp().clone();
            if (lrlook) {
                if (lookLeft)
                    camAhead.addLocal(camLeft.mult(lrLookSpeed));
                else
                    camAhead.addLocal(camLeft.negate().mult(lrLookSpeed));
            }
            if (udlook) {
                if (lookUp) {
                    if (camAhead.y + udLookSpeed < upLimit)
                        camAhead.addLocal(camUp.mult(udLookSpeed));
                } else {
                    if (camAhead.y - udLookSpeed > -upLimit)
                        camAhead.addLocal(camUp.negate().mult(udLookSpeed));
                }
            }
            camAhead.normalizeLocal();
        }

        if (gotoLookmark && lookmarkSet) {
            vposn.set(vposnLookmark);
            vlook.set(vlookLookmark);
        } else {
            if (player != null)
                vposn.set(player.getPhysicsLocation());
            else {
// System.out.println("NavDoitNew.update: ... ");               
            }
            vlook.set(camAhead);
            if (setLookmark) {
                vposnLookmark.set(vposn); // probably should be vposnLast, vlookLast, but being pragmatic
                vlookLookmark.set(vlook);
                lookmarkSet = true;
            }
        }
        if (sceneGraphSender != null)
            // sceneGraphSender.xformPosRotScale("defaultCamera", vposn, new Vector3f(0f,0f,0f), new Vector3f(1f,1f,1f));
            sceneGraphSender.xformPosStareDir("defaultCamera", vposn, vlook, Vector3f.UNIT_Y);
        
        if (player != null) {
            player.setViewDirection(vlook);
            player.setPhysicsLocation(vposn);
        }
        
// left = false; right = false; forward = false; backward = false;
        lookLeft = false; lookRight = false; lookDown = false; lookUp = false;
        setLookmark = false;
        gotoLookmark = false;
    }

    public void genericNav(INav.NavCommandType cmd, float val) {
// System.out.println("NavDoit.genericNav: cmd, val = "+cmd+" "+val);
        // left = false; right = false; forward = false; backward = false;
        // lookLeft = false; lookRight = false; lookUp = false; lookDown = false;
        // setLookmark = false; gotoLookmark = false;
        if (cmd == INav.NavCommandType.DONE)
            done = true;
        else if (execute) {
          switch (cmd) {
            case LEFTSPEED:
                left = (val != 0f);
                lrspeed = val;
                break;
            case RIGHTSPEED:
                right = (val != 0f);
                lrspeed = val;
                break;
            case FORWARDSPEED:
                forward = (val != 0f);
                fbspeed = val;
                break;
            case BACKWARDSPEED:
                backward = (val != 0f);
                fbspeed = val;
                break;
            case LOOKLEFTSPEED:
                lookLeft = true;
                lrLookSpeed = val;
                break;
            case LOOKRIGHTSPEED:
                lookRight = true;
                lrLookSpeed = val;
               break;
            case LOOKUPSPEED:
                lookUp = true;
                udLookSpeed = val;
                break;
            case LOOKDOWNSPEED:
                lookDown = true;
                udLookSpeed = val;
                break;
            case SETLOOKMARK:
                setLookmark = true;
                break;
            case GOTOLOOKMARK:
                gotoLookmark = true;
                break;
            case PICK:
                vePicker.pickAttempt();
                break;
            case DONE:
            default:
                break;
        }
      }
    }
    
    public void frameTick(float tframe, int iframe) {
        if (sceneGraphSender != null)
             sceneGraphSender.frameTick(tframe, iframe);
    } 
}
