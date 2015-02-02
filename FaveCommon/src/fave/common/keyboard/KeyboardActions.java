/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.keyboard;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import fave.common.nav.INav;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class KeyboardActions {
    private static final String thisName = fave.common.keyboard.KeyboardActions.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    boolean execute = false;
    
    INav navSender;
    
    public KeyboardActions(InputManager inputManager) {
        // logger.log(Level.INFO, "Enter");
        setupKeys(inputManager);
        // logger.log(Level.INFO, "Leave");
    }
    public void setNavSender(INav navSender) {
        this.navSender = navSender;
    }
    public void setExecute(boolean on) {
        execute = on;
    }

    private void setupKeys(InputManager inputManager) {
        inputManager.addMapping("SetLookmark", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("GotoLookmark",new KeyTrigger(KeyInput.KEY_V));
        inputManager.addListener(actionListener, "SetLookmark");
        inputManager.addListener(actionListener, "GotoLookmark");
    }
   
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String binding, boolean keyPressed, float tpf) {
            if (execute) {
                if (navSender != null) {
                    if (binding.equals("SetLookmark"))
                        navSender.genericNav(INav.NavCommandType.SETLOOKMARK, 0f);
                    else if (binding.equals("GotoLookmark"))
                        navSender.genericNav(INav.NavCommandType.GOTOLOOKMARK, 0f);
                }
            }
        }
    };
}
