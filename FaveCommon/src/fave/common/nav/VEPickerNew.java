/*
 * Developed as part of the Celeste FLAME project, 2013
 */

package fave.common.nav;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import fave.common.jme.util.SGCompanionApply;
import fave.common.model.simple.AudioManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

public class VEPickerNew {
    private static final Logger logger = Logger.getLogger(fave.common.nav.VEPickerNew.class.getName());
    Camera cam;
    Node worldNode;
    Node pickables;
    Geometry mark = null;
    AppSettings settings;
    AssetManager assetManager;
    boolean useMark = false;
    AudioManager audioManager = null;
    String audioPickMissName = "unset";
    String audioPickHitName = "unset";
    AudioNode audioPickMiss = null;
    AudioNode audioPickHit = null;
    float maxPickDistance = 1000000f;
    
    public VEPickerNew(AssetManager assetManager, Camera cam,
                     Node worldNode, Node pickables, float maxPickDistance) {
        this.assetManager = assetManager;
        this.cam = cam;
        // this.navControl = navControl;
        this.worldNode = worldNode;
        this.pickables = pickables;
        // this.recorder = recorder;
        this.maxPickDistance = maxPickDistance;
        if (useMark) {
            initMark();
        }
    }
    
    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;   
    }
    
    public void setAudioPickMiss(AudioNode audioPickMiss) {
        this.audioPickMiss = audioPickMiss;   
    }
    
    public void setAudioPickMissName(String audioPickMissName) {
        this.audioPickMissName = audioPickMissName;   
    }
    
    public void setAudioPickHit(AudioNode audioPickHit) {
        this.audioPickHit = audioPickHit;   
    }
    
    public void setAudioPickHitName(String audioPickHitName) {
        this.audioPickHitName = audioPickHitName;   
    }
    
    private void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.1f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }
   
    /*
    public void putNavCom(NavCom navCom) {
        if (navCom.getCommandType() == NavCom.CommandType.PICK) {
            pickAttempt();
        }
    }
    */
    
    public void pickAttempt() {
            // navControl.addNavRecord(CommandType.PICK, 0f);
            boolean pickablePicked = false;
            CollisionResults results = new CollisionResults();
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            pickables.collideWith(ray, results);
/* To look at all collisions   
            for (int i = 0; i < results.size(); i++) {
                CollisionResult cr = results.getCollision(i);
            }      
*/
            if (results.size() > 0) {
                CollisionResult closest = results.getClosestCollision();
                float dist = closest.getDistance();
                if (dist <= maxPickDistance) {
                    Vector3f pt = closest.getContactPoint();
                    Spatial geom = closest.getGeometry();
                    logger.log(Level.INFO, "picked geom name is {0}", new Object[]{geom.getName()});
                    while (geom != null) {
                        logger.log(Level.INFO, "try geom name is {0}.", new Object[]{geom.getName()});
                        SGCompanionApply sgpick = SGCompanionApply.fromSpatial(geom);
                        if (sgpick != null) {
                            sgpick.applyPick(pt, dist);
                            pickablePicked = true;
                            break;
                        }
                        geom = geom.getParent();
                    }
                    // String hit = closest.getGeometry().getName();
                    // System.out.println("  You picked " + hit + " at " + pt + ", " + dist + " feet away.");
                    if (mark != null) {
                        mark.setLocalTranslation(closest.getContactPoint());
                        worldNode.attachChild(mark);
                    }
                }

            } else {
                if (mark != null)
                    worldNode.detachChild(mark);  
            }
            if (pickablePicked) {
                if (audioPickHit != null)
                    audioPickHit.playInstance();
                else if (audioManager!=null && !audioPickHitName.equals("unset"))
                    audioManager.triggerByName(audioPickHitName);
            } else {
                if (audioPickMiss != null)
                    audioPickMiss.playInstance();
                else if (audioManager!=null && !audioPickMissName.equals("unset"))
                    audioManager.triggerByName(audioPickMissName);
            }
        }
    
}
