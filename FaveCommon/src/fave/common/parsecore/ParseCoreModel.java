/* ParseCoreScene.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.common.parsecore;

import com.jme3.math.Vector3f;
import fave.common.sg.ISceneGraph;
import favevex.SSExpr;
import favevex.SSExprList;

public class ParseCoreModel extends ParseCoreBase {
    String modelFile = null;
    public ParseCoreModel(SSExprList plist) {
        name = defaultName("ParseCoreModel");
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("modelFile"))
                modelFile = fieldPlist.getString(0);
        }     
    }
    public void sgCall(ISceneGraph sg, String parent) {
        if (modelFile == null)
            System.out.println("WARNING in ParseCoreModel: modelFile == null.  No node added.");
        else {
            String modelNodeName = name+"_model";
            sg.createModelNode(modelNodeName);
            sg.loadModel(modelNodeName, modelFile);
            sg.addChild(parent, modelNodeName);      
        }
    }
}
