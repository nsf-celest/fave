/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * Abstract class for classes that return a model given an index
 */
package fave.common.model.makercore;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
abstract public class VariModelMaker {
    private static final Logger logger = Logger.getLogger(VariModelMaker.class.getName());
    public AssetManager assetManager = null;
    public abstract int ntypes();
    public abstract Node getModel(int ivar);
    // public abstract Spatial getModel(int ivar, String name);
}