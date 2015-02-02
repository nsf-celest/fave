/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * Top level class for connecting scene graph spatials (Geom or Node)
 * with non-scene graph info.
 */
package fave.common.jme.util;

import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class SGCompanion {
    Spatial sgSpatial = null;
    int ireg;
    
    static ArrayList<SGCompanion> regSGC = null;
    static int registerSGC(SGCompanion sgc) {
        if (regSGC == null)
            regSGC = new ArrayList<SGCompanion>();
        regSGC.add(sgc);
        return(regSGC.size()-1);
    }
    static SGCompanion getSGC(int ireg) {        
        return regSGC.get(ireg);
    }
    static public SGCompanion fromSpatial(Spatial s) {
        if (s == null || s.getUserData("SGCompanion") == null)
            return null;
        else {
            int ireg = s.getUserData("SGCompanion");
            return getSGC(ireg);
        } 
    }

    public SGCompanion(Spatial s) {
        ireg = registerSGC(this);
        setSpatial(s);
    }
    public SGCompanion() {
        ireg = registerSGC(this);      
        setSpatial(null);
    }
    public Spatial getSpatial() {
        return sgSpatial; 
    }
    public Spatial s() {
        return getSpatial();
    }
    final public void setSpatial(Spatial s) {
        if (sgSpatial != null)
            sgSpatial.setUserData("SGCompanion", null);
        sgSpatial = s;
        if (sgSpatial != null)
            sgSpatial.setUserData("SGCompanion", ireg);
    }
}
