/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.examples.intro;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.renderer.RenderManager;
import fave.common.jme.util.GraphicsUtility;
import fave.common.nav.INav;
import fave.common.sg.ISceneGraph;
import fave.common.sg.SceneGraphDoit;
import fave.common.nav.NavSystem;
import gameplay.GamePlaySettings;
import java.util.logging.Logger;
/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

public class FaveSimpleAppIntro extends SimpleApplication { 
    private static final Logger logger = Logger.getLogger(FaveSimpleAppIntro.class.getName());

    public ISceneGraph sg;
    protected SceneGraphDoit sgImp;
    GamePlaySettings playsettings = new GamePlaySettings();
    public GraphicsUtility graphicsUtility;
    INav nav;
    NavSystem navsys = null;
       
    boolean done = false;
 
    public void localSceneCreate() {}
    public void agentSceneCreate() {}
    public void agentsInit() {} 
    public void agentsUpdate(boolean done) {}
    public void agentsNavUpdate(boolean done) {}

    @Override
    public void simpleInitApp() {
        sgImp = new SceneGraphDoit(assetManager, rootNode);
        sg = sgImp;
        graphicsUtility = new GraphicsUtility(rootNode); 
        navsys = new NavSystem(inputManager, flyCam, cam, sg, sgImp);
        navsys.init("FREEFLY");

        localSceneCreate();
        agentSceneCreate();
        agentsInit();
        
        ScreenshotAppState screenShotState;
        screenShotState = new ScreenshotAppState();
        stateManager.attach(screenShotState);

        setDisplayFps(false);
        setDisplayStatView(false);
    }

    @Override
    public void simpleUpdate(float tpf) {       
        agentsUpdate(done);    
        if (!done)
            graphicsUtility.updateLights(cam.getDirection());

        if (navsys.getMode().equals("AGENT"))
            agentsNavUpdate(done);
        else {
            Boolean dummyDone = navsys.update();
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {}
}
