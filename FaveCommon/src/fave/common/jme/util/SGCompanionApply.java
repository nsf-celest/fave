/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.jme.util;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class SGCompanionApply extends SGCompanion {
    public SGCompanionApply() {
        super();
    }
    public SGCompanionApply(Spatial s) {
        super(s);
    }
    static public SGCompanionApply fromSpatial(Spatial s) {
        return (SGCompanionApply)SGCompanion.fromSpatial(s);
    }
    public int apply() {
        return 0;
    }
    public int applyPick(Vector3f ptHit, float dHit) {
        return 0;
    }
    public int applyGeneric(Object x) {
        return 0;
    }
}
