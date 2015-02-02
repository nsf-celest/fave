/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.common.util.math;

import com.jme3.math.Vector3f;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class View {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    public Vector3f eyePosn;
    public Vector3f eyeDir;
    public Vector3f eyeUp;
    public View() {
        setView(new Vector3f(Vector3f.ZERO), new Vector3f(Vector3f.UNIT_Z.negate()), new Vector3f(Vector3f.UNIT_Y));
    };
    public View(Vector3f eyePosn, Vector3f eyeDir, Vector3f eyeUp) {
        setView(eyePosn, eyeDir, eyeUp);
    };
    public View(View view) {
        setView(view.eyePosn.clone(), view.eyeDir.clone(), view.eyeUp.clone());
    };
    final public void setView(Vector3f eyePosn, Vector3f eyeDir, Vector3f eyeUp) {
        this.eyePosn = eyePosn;
        this.eyeDir  = eyeDir;
        this.eyeUp   = eyeUp;    
    }
};
