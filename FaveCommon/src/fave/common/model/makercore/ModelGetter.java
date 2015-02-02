/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 *
 * This class provides a way to get a model (i.e., a JME Node)
 * given a string.  That string may be an asset in the JME Asset
 * space (path in that form), the "name" of a callback, or an alias
 * to either of those.
 * 
 * It is allowable to set an alias to point to another alias.
 *  
 * When passed a String that repr
 * Order of looking for modelName:
 *     alias
 *     callback
 *     modelpath
 */
package fave.common.model.makercore;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class ModelGetter {
    static final Logger logger = Logger.getLogger(ModelGetter.class.getName());
    AssetManager assetManager;
    Map<String, String> aliasMap = new HashMap<String, String>();
    Map<String, SpatialMaker> makerMap = new HashMap<String, SpatialMaker>();
    Set<String> spriteSet = new LinkedHashSet<String>();
    
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void addModelUseName(String alias, String modelAsset) {
         aliasMap.put(alias, modelAsset);
    }
    
    public void addSpatialMaker(String modelName, SpatialMaker spatialMaker) {
         makerMap.put(modelName, spatialMaker);
    }
    
    public void setSprite(String modelName) {
        spriteSet.add(modelName);
    }
    // getModel first follows whatever path of aliases,
    // then looks for a maker, and finally tries to load a
    // model by its path.
    public Spatial getModel(String modelName) {
        Spatial model = null;
        String resolvedName = modelName;
        while (resolvedName!=null && aliasMap.containsKey(resolvedName))
            resolvedName = aliasMap.get(resolvedName);
        if (resolvedName == null)
            logger.log(Level.SEVERE, "{0} null or eventually aliased to null", new Object[]{modelName});
        else {
            if (makerMap.containsKey(resolvedName)) {
                SpatialMaker maker = makerMap.get(resolvedName);
                if (maker == null)
                    logger.log(Level.SEVERE, "{0} gives null maker", new Object[]{modelName});
                else
                    model = maker.cb();
            }
            else
                model = assetManager.loadModel(resolvedName);

        }
        return model;
    }

    public Spatial useModel(String modelName, String name) {
        Spatial model = getModelS(modelName);
        if (model != null)
            model.setName(name); 
        return model;
    }

    public Boolean isSprite(String modelName) {
        Boolean sprite = false;
        String resolvedName = modelName;
        while (resolvedName!=null && aliasMap.containsKey(resolvedName)) {
            if (spriteSet.contains(resolvedName))
                sprite = true;
            resolvedName = aliasMap.get(resolvedName);
        } 
        if (resolvedName != null && spriteSet.contains(resolvedName))
             sprite = true;
        return sprite;
    }
    
    public Spatial getModelS(String modelName) {
        Spatial model = getModel(modelName);
        if (isSprite(modelName)) {
            BillboardControl bb = new BillboardControl();
            bb.setAlignment(BillboardControl.Alignment.Camera);
            model.addControl(bb); 
        }
        return model;
    }
    
    public Spatial useModelS(String modelName, String name) {
        Spatial model = getModelS(modelName);
        if (model != null)
            model.setName(name); 
        return model;
    }
/*    
    public Node getModelS(String modelName) {
        Spatial model = getModel(modelName);
        Node n = new Node();
        if (!isSprite(modelName))
            n.attachChild(model);
        else {
            Node nb = new Node();
            BillboardControl bb = new BillboardControl();
            bb.setAlignment(BillboardControl.Alignment.Camera);
            nb.addControl(bb);
            nb.attachChild(model);
            n.attachChild(nb); 
        }
        return n;
    }
    
    public Node useModelS(String modelName, String name) {
        Node n = getModelS(modelName);
        if (n != null)
            n.setName(name); 
        return n;
    }
*/
}
