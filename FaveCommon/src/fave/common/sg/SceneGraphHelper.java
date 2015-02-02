/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.sg;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class SceneGraphHelper {
    ISceneGraph sg;
    AssetManager assetManager;
    // Node localRootNode;
    int nModelAdd = 0;
    int nXformAdd = 0;
        
    public SceneGraphHelper(AssetManager assetManager, /* Node localRootNode, */ ISceneGraph sg) {
        this.sg = sg;
        // this.localRootNode = localRootNode;
        this.assetManager = assetManager;    
    }

    public void myModelAdd(String modelNodeName,
                    String xformNodeName, Vector3f trans, Vector3f rot, Vector3f scale,
                    String parentNodeName) {
        sg.createInternalNode(xformNodeName);
        sg.addChild(xformNodeName, modelNodeName);
        sg.xformPosRotScale(xformNodeName, trans, rot, scale);      
        sg.addChild(parentNodeName, xformNodeName);
    }
 
    public void myModelAddAsset(String modelAsset, String modelNodeName,
                    String xformNodeName, Vector3f trans, Vector3f rot, Vector3f scale,
                    String parentNodeName) {
        if (modelNodeName == null || modelNodeName.isEmpty())
            modelNodeName = "modelAdd"+Integer.toString(nModelAdd++);
        if (xformNodeName == null || xformNodeName.isEmpty())
            xformNodeName = "xformAdd"+Integer.toString(nXformAdd++);
        sg.createModelNode(modelNodeName);
        sg.loadModel(modelNodeName, modelAsset);
        myModelAdd(modelNodeName, xformNodeName, trans, rot, scale, parentNodeName);
    }
 
}
