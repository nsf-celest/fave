/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 * 
 * Give ModelPlacer a model, and then ask for clones
 * Extends classes that allow scaling, translation, rotation, etc
 * 
 * Modelplacer sets transformation values in the node that it returns, so to move it
 * again either compose with current transformation or put under a new node
 */

package fave.common.model.makercore;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import fave.model.vent.VentGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Erik Brisson <ebrisson@bu.edu>
 */

public class ModelPlacer extends XformBBoxSetup implements VentGenerator.Maker {
    private static final Logger logger = Logger.getLogger(fave.common.model.makercore.ModelPlacer.class.getName());
    public Spatial spatialModel;
    boolean billboard = false;
    
    public ModelPlacer() {
    }
    
    public ModelPlacer(Spatial spatialModel) {
        super();
        internalSetModel(spatialModel);
    }
    
    public void setBillboard(boolean billboard) {
        this.billboard = billboard;
    }
    
    /*
    public void setModel(Spatial spatialModel) {
        internalSetModel(spatialModel);    
    }
    */
    
    final void internalSetModel(Spatial spatialModel) {
        if (spatialModel == null)
            logger.log(Level.SEVERE, "spatialModel == null");
        else {
            this.spatialModel = spatialModel;
            spatialModel.updateModelBound();
            BoundingVolume bv = spatialModel.getWorldBound();
            if (bv.getType() != BoundingVolume.Type.AABB)
                logger.log(Level.SEVERE, "bv.getType = {0} != AABB", bv.getType());
            setBBox((BoundingBox)bv);
        }
    }
    
/*    
    public Node getClonePlacedInternal() {
        Node n = getNodePlaced(null);
        Spatial s = spatialModel.clone();
        n.attachChild(s);
        return n;
    }
    
    public Node getClonePlaced() {
        Node n = getClonePlacedInternal();  
        if (!billboard)
            return n;
        else {
            Node nb = new Node(); 
            BillboardControl bb = new BillboardControl();
            bb.setAlignment(BillboardControl.Alignment.AxialY);
            nb.addControl(bb);
            nb.attachChild(n);      
            return nb;      
        }
    }
*/ 
 
    public Node getClonePlacedInternal() {
        Node n = getNodePlaced(null);
        Spatial s = spatialModel.clone();
        if (!billboard)
            n.attachChild(s);       
        else {
            Node nb = new Node();
            BillboardControl bb = new BillboardControl();
            bb.setAlignment(BillboardControl.Alignment.Camera);
            nb.addControl(bb);
            nb.attachChild(s);
            n.attachChild(nb); 
        }
        return n;
    }
    
    public Node getClonePlaced() {
        return getClonePlacedInternal();
    }
    
    public Spatial make(VentGenerator.UserData data) {
        return getClonePlaced();
    }
}
