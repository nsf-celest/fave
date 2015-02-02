/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.jme.util;

import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.ScreenshotAppState;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class ScreenGrabber {
    private static final Logger logger = Logger.getLogger(fave.common.jme.util.ScreenGrabber.class.getName());
    boolean doGrab = false;
    int grabStart = -1;
    int grabEnd;
    int grabStep;
    ArrayList<Integer> grabIndex = null;
    ArrayList<Integer[]> grabSequence = null;  
    String savePath;
    ScreenshotAppState screenshotAppState;
    AppStateManager appStateManager;
    ArrayList<Boolean> grabMe;
    
    public ScreenGrabber() {
    }
    public ScreenGrabber(AppStateManager appStateManager) {
        setAppStateManager(appStateManager);
    }  
    public ScreenGrabber(AppStateManager appStateManager, String savePath) {
        setAppStateManager(appStateManager);
        setSavePath(savePath);
    }

    final public void setAppStateManager(AppStateManager appStateManager) {
        if (appStateManager != null) {
            this.appStateManager = appStateManager;
            if (screenshotAppState == null)
                screenshotAppState = new ScreenshotAppState();
            setScreenshotAppState(screenshotAppState);
        }
    }
    final public void setScreenshotAppState(ScreenshotAppState screenShotState) {
        if (screenShotState != null) {
            this.screenshotAppState = screenShotState;
            if (appStateManager != null)
                appStateManager.attach(screenshotAppState);
            if (savePath != null)
                this.screenshotAppState.setFilePath(savePath);
        }
    }
    
    public void addGrabFrame(int index) {
        if (grabMe == null)
            grabMe = new ArrayList<Boolean>();
// System.out.println("addGrabFrame: index, grabMe.size = "+index+" "+grabMe.size());
        if (index < grabMe.size())
            grabMe.set(index, true);
        else {
            int nfill = index - grabMe.size();
            for (int i=0; i<nfill; i++)
                grabMe.add(false);
            grabMe.add(true);
        }
    }
    public void addGrabList(ArrayList<Integer> indexList) {
        for (int index : indexList)
            addGrabFrame(indexList.get(index));      
    } 
    public void addGrabSequence(int indexBeg, int indexEnd, int step) {
        for (int index=indexBeg; index<=indexEnd; index+=step)
            addGrabFrame(index);
    }
  
    final public void setSavePath(String path) {   
        savePath = path; 
        if (screenshotAppState != null)
            screenshotAppState.setFilePath(savePath);          
    }

    public void grabCheck(int frameCounter) {
// System.out.println("enter grabCheck: frame "+frameCounter);
        if (grabMe==null || screenshotAppState==null)
            logger.log(Level.SEVERE, "not initialized");
        else if (frameCounter<0 || frameCounter>=grabMe.size()) {
        }
        else if (grabMe.get(frameCounter)) {
            screenshotAppState.takeScreenshot();
            System.out.println("grabCheck grabbed frame "+frameCounter);
        }
//        else
// System.out.println("grabCheck not grabbing frame "+frameCounter);
    }
    
    /**
     *
     * @param mc
     */
    
    public void sMethEval(MethSCaller mc) {
        if (mc==null)
            logger.log(Level.SEVERE, "null mc");
        else if (mc.fname().equals("addGrabSequence")) {
            if (mc.nargs() == 3) {
                try {
                    addGrabSequence(Integer.parseInt(mc.arg(0)), Integer.parseInt(mc.arg(1)), Integer.parseInt(mc.arg(2)));
                } catch (NumberFormatException e) {}
            } else
                logger.log(Level.SEVERE, "addGrabSequence requires 3 args, but got {0}", new Object[]{mc.nargs()});
        }
    }
    
}
