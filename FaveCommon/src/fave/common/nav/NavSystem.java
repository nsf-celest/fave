/* NavSystem.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.common.nav;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import fave.common.jme.util.CollisionManagement;
import fave.common.keyboard.KeyboardActions;
import fave.common.keyboard.KeyboardNav;
import fave.common.sg.ISceneGraph;
import fave.common.sg.SceneGraphDoit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavSystem {
    private static final Logger logger = Logger.getLogger(NavSystem.class.getName());
    NavDoitNew navDoit = null;
    KeyboardNav userNavKB;
    KeyboardActions userActions;
    CollisionManagement collisionMgr = null;
    String mode = "FREEFLY";
    
    InputManager inputManager;
    FlyByCamera flyCam;
    Camera cam;
    ISceneGraph sg;
    SceneGraphDoit sgImp;

    public NavSystem(InputManager inputManager,
                     FlyByCamera flyCam, Camera cam,
                     ISceneGraph sg, SceneGraphDoit sgImp)
    {
        this.inputManager = inputManager;
        this.flyCam = flyCam;
        this.cam = cam;
        this.sg = sg;
        this.sgImp = sgImp;
    }
    private void setMode(String navMode) {
        if (!(navMode.equals("FREEFLY") || navMode.equals("KBN") || 
              navMode.equals("NAVREAD") || navMode.equals("AGENT"))) {
            logger.log(Level.SEVERE, "navMode {0} unrecognized.   leaving mode unchanged", new Object[]{navMode});  
        }
        else
            this.mode = navMode;
    }  
        
    public String getMode() {
        return mode;
    }
  
    // "KBN" or "FREEFLY" or "NAVREAD" or "AGENT"
    public INav init(String navMode) {
        // collisionMgr = new CollisionManagement(stateManager, rootNode, new View());

        setMode(navMode);
        if (mode.equals("FREEFLY")) {
            flyCam.setEnabled(true);
            flyCam.setMoveSpeed(10f);
            flyCam.setRotationSpeed(1f);
        }
        else {
            flyCam.setZoomSpeed(0);
            flyCam.setMoveSpeed(0); 
            flyCam.setRotationSpeed(0);
            sgImp.attachCamera("defaultCamera", cam);
            if (mode.equals("NAVREAD")) {
                System.out.println("ERROR: nav mode = "+mode+" not yet implemented");
                System.exit(0);
            }
            else if (mode.equals("KBN")) {
                navDoit = new NavDoitNew(cam, collisionMgr);
                navDoit.setSceneGraphSender(sg);
                
                userNavKB = new KeyboardNav(inputManager, "KBN");
                userNavKB.setMoveSpeed(2.0f, 2.0f);
                userNavKB.setLookSpeed(0.5f, 0.5f);
                userNavKB.setNavSender(navDoit);
                userNavKB.setExecute(true);

                userActions = new KeyboardActions(inputManager);
                userActions.setNavSender(navDoit);
                userActions.setExecute(true);
            }
            else if (mode.equals("AGENT")) {}
            else {
                System.out.println("ERROR: nav mode = "+mode+" unknown");
                System.exit(0);
            }              
        }
        return navDoit;
    }
    
    public Boolean update() {
        Boolean done = false;   // this done is not set anywhere
        if (mode.equals("KBN"))
            navDoit.update();
        // could do agent update here ?
        return done;
    }

}
