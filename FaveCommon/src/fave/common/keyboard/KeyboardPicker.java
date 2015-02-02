/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.keyboard;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.system.AppSettings;
import fave.common.jme.util.FaveHud;
import fave.common.nav.INav;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class KeyboardPicker implements ActionListener {
    private static final Logger logger = Logger.getLogger(fave.common.keyboard.KeyboardPicker.class.getName());
    InputManager inputManager;
    AppSettings settings;
    AssetManager assetManager;
    INav navSender;
    public KeyboardPicker(FaveHud hud, InputManager inputManager) {
        this.inputManager = inputManager;
        initKeys();
        hud.useCrossHairs();
    }
    public void setNavSender(INav navSender) {
        this.navSender = navSender;
    }
    private void initKeys() {
        inputManager.addMapping("Pick",
           new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
           new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
        inputManager.addListener(this, "Pick");
    }
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pick") && !keyPressed) {
            if (navSender != null)
                navSender.genericNav(INav.NavCommandType.PICK, 0f);
        }
    }
}
