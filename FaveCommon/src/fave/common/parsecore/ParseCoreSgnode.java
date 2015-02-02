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
import java.util.ArrayList;

public class ParseCoreSgnode extends ParseCoreBase {
    ArrayList<ParseCoreModel> modelNodeList = new ArrayList<ParseCoreModel>();
    ArrayList<ParseCoreSgnode> sgnodeList = new ArrayList<ParseCoreSgnode>();
    Vector3f xyz = new Vector3f(0, 0, 0);
    Vector3f hpr = new Vector3f(0, 0, 0);
    Vector3f scale = new Vector3f(1f, 1f, 1f);
    public ParseCoreSgnode(SSExprList plist) {
        name = defaultName("ParseCoreSgnode");
        float x, y, z, h, p, r;
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("sgnode"))
                sgnodeList.add(new ParseCoreSgnode(fieldPlist));
            else if (fieldName.equals("model"))
                modelNodeList.add(new ParseCoreModel(fieldPlist));
            else if (fieldName.equals("xyz")) {
                x = fieldPlist.getFloat(0);
                y = fieldPlist.getFloat(1);
                z = fieldPlist.getFloat(2); 
                xyz.set(x, y, z);
            }
            else if (fieldName.equals("hpr")) {
                h = fieldPlist.getFloat(0);
                p = fieldPlist.getFloat(1);
                r = fieldPlist.getFloat(2); 
                hpr.set(h, p, r);
            }        
            else if (fieldName.equals("xyzhpr")) {
                x = fieldPlist.getFloat(0);
                y = fieldPlist.getFloat(1);
                z = fieldPlist.getFloat(2);        
                h = fieldPlist.getFloat(3);
                p = fieldPlist.getFloat(4);
                r = fieldPlist.getFloat(5);
                xyz.set(x, y, z);
                hpr.set(h, p, r);
            }
            else if (fieldName.equals("scale")) {
                x = fieldPlist.getFloat(0);
                y = fieldPlist.getFloat(1);
                z = fieldPlist.getFloat(2); 
                scale.set(x, y, z);
            }
            else if (fieldName.equals("size")) {
                x = fieldPlist.getFloat(0);
                scale.set(x, x, x);
            }
        }     
    }
    public void sgCall(ISceneGraph sg, String parent) {
        sg.createInternalNode(name);
        sg.addChild(parent, name);
        sg.xformPosRotScale(name, xyz, hpr, scale);
        for (ParseCoreModel model : modelNodeList)
            model.sgCall(sg, name);
        for (ParseCoreSgnode sgnode : sgnodeList)
            sgnode.sgCall(sg, name);
    }
}
