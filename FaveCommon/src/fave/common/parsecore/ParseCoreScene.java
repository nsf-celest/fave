/* ParseCoreScene.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.common.parsecore;

import fave.common.sg.ISceneGraph;
import favevex.SSExpr;
import favevex.SSExprList;
import java.util.ArrayList;

public class ParseCoreScene extends ParseCoreBase {
    ArrayList<ParseCoreSgnode> childList = new ArrayList<ParseCoreSgnode>();
    public ParseCoreScene(SSExprList plist) {
        name = defaultName("ParseCoreScene");
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("sgnode"))
                childList.add(new ParseCoreSgnode(fieldPlist));
        }     
    }
    public void sgCall(ISceneGraph sg, String parent) {
        sg.createInternalNode(name);
        sg.addChild(parent, name);
        for (ParseCoreSgnode child : childList)
            child.sgCall(sg, name);
    }
}
