/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.app;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

/**
 *
 * @author E12
 */
public class AppGlob extends SimpleApplication {
    
    AppSettings globSettings;
    
    AppSettings initGlobSettings() {
        AppSettings s = new AppSettings(true);
        s.setFrameRate(60);
        setSettings(s);
        // app.setShowSettings(GS.displaySettings.showSettingsScreen);
        // settings.setResolution(GS.displaySettings.displayWidth, GS.displaySettings.displayHeight);
        // settings.setFullscreen(GS.displaySettings.fullscreen);
        return s;
    }
    
    void init() {
        globSettings = initGlobSettings();   
    }
    
    @Override
    public void simpleInitApp() {
    }
    
}
