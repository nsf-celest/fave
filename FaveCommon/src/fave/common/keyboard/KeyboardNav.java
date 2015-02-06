/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.keyboard;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import fave.common.nav.INav;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

public class KeyboardNav /* implements ActionListener */ {
    private static final String thisName = fave.common.keyboard.KeyboardNav.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    boolean done = false;

    float xmouse, ymouse; 
    float lrspeed = 0.1f;
    float fbspeed = 0.1f; 
    float udLookSpeed = 0.1f;
    float lrLookSpeed = 0.1f;
    boolean allAction;
    boolean execute = false;
    
    INav navSender;
    
    public KeyboardNav(InputManager inputManager, String mode) {
        // System.out.println("Enter");
        allAction = true;
        setupKeys(inputManager, mode);
        // System.out.println("Leave");
    }
    public void setNavSender(INav navSender) {
        this.navSender = navSender;
    }
    public void setExecute(boolean on) {
        execute = on;
    }
    public void setMoveSpeed(float fb, float lr) {
        fbspeed = fb;
        lrspeed = lr;
    }
    public void setLookSpeed(float lrLook, float udLook) {
        udLookSpeed = udLook;
        lrLookSpeed = lrLook;
    }

    private void setupKeys(InputManager inputManager, String mode) {
        inputManager.addMapping("Done", new KeyTrigger(KeyInput.KEY_ESCAPE/*KeyInput.KEY_0*/));
        inputManager.addListener(actionListener, "Done");
 
        if (allAction) {
            inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addMapping("Right",new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("Up",   new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        
            inputManager.addListener(actionListener, "Left");
            inputManager.addListener(actionListener, "Right");
            inputManager.addListener(actionListener, "Up");
            inputManager.addListener(actionListener, "Down");
/*        
        inputManager.addMapping("Wheel", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addListener(actionListener, "Wheel");
*/
            if (mode.equals("KBN")) {
                inputManager.addMapping("MouseLeft",  new MouseAxisTrigger(MouseInput.AXIS_X, true));
                inputManager.addMapping("MouseRight", new MouseAxisTrigger(MouseInput.AXIS_X, false));
                inputManager.addMapping("MouseDown",  new MouseAxisTrigger(MouseInput.AXIS_Y, true));
                inputManager.addMapping("MouseUp",    new MouseAxisTrigger(MouseInput.AXIS_Y, false));
       
                inputManager.addListener(analogListener, "MouseLeft", "MouseRight");
                inputManager.addListener(analogListener, "MouseDown", "MouseUp");
            }
        }
    }
   
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String binding, boolean keyPressed, float tpf) {
            if (binding.equals("Done")) {
                logger.log(Level.INFO, "Done");
                done = true;
                if (navSender != null)
                    navSender.genericNav(INav.NavCommandType.DONE, 0f);
                // else if (navGate != null)
                //     navGate.sendNavCom(new NavCom(NavCom.CommandType.DONE, 0f));
            }
            else if (allAction && execute) {
                float move = 0f;
                if (keyPressed)
                    move = 1f;
                if (navSender != null) {
// System.out.println("KeyboardNav.actionListener: lrspeed, fbspeed = "+lrspeed+" "+fbspeed);
                    if (binding.equals("Left")) {
                        navSender.genericNav(INav.NavCommandType.LEFTSPEED, move*lrspeed);
                    } else if (binding.equals("Right")) {
                        navSender.genericNav(INav.NavCommandType.RIGHTSPEED, move*lrspeed);
                    } else if (binding.equals("Up")) {
                        navSender.genericNav(INav.NavCommandType.FORWARDSPEED, move*fbspeed); 
                    } else if (binding.equals("Down")) {
                        navSender.genericNav(INav.NavCommandType.BACKWARDSPEED, move*fbspeed);
                    } else if (binding.equals("Wheel")) {} 
                }
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String binding, float value, float tpf) {
            // logger.log(Level.INFO, "Enter");
            if (allAction && execute) {
                if (navSender != null) {
                    if (binding.equals("MouseLeft")) {
                        navSender.genericNav(INav.NavCommandType.LOOKLEFTSPEED, lrLookSpeed*value);
                    } else if (binding.equals("MouseRight")) {
                        navSender.genericNav(INav.NavCommandType.LOOKRIGHTSPEED, lrLookSpeed*value);
                    } else if (binding.equals("MouseDown")) {
                        navSender.genericNav(INav.NavCommandType.LOOKDOWNSPEED, udLookSpeed*value);
                    } else if (binding.equals("MouseUp")) {
                        navSender.genericNav(INav.NavCommandType.LOOKUPSPEED, udLookSpeed*value);
                    }
                }
            }
        }
    };
}
