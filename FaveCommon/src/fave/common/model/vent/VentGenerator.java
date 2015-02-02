/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.model.vent;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author E12
 */
public class VentGenerator {
    static final Logger logger = Logger.getLogger(VentGenerator.class.getName());
    public class UserData {
    }
    
    public interface Maker {
        public Spatial make(VentGenerator.UserData data);
    }
    
    static AssetManager assetManager = null;
    static Map<String, Maker> regMaker = null;
    static Map<String, Spatial> regVent = null;
        
    static void init(AssetManager assetManager) {
        VentGenerator.assetManager = assetManager;
    }
    static AssetManager getAssetManager() {
        return assetManager;
    }

    public static void registerMaker(Maker maker, String name) {
        if (regMaker == null) {
            regMaker = new HashMap<String, Maker>();
        }
        regMaker.put(name, maker);
    }
    static public Maker getMaker(String name) {
        return regMaker.get(name);
    }
    
    public static void registerVent(Spatial s, String name) {
        if (regVent == null) {
            regVent = new HashMap<String, Spatial>();
        }
        regVent.put(name, s);
    }
    static public Spatial getVent(String name) {
        return regVent.get(name);
    }
 
 }
