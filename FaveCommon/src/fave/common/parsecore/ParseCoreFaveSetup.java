/* ParseCoreFaveSetup.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 * 
 * Notes:
 *    FaveSetup is treated as a singleton, i.e., any block in the heirarchy headed by favesetup
 *    will contribute members to one FaveSetup instance
 */

package fave.common.parsecore;

import fave.common.nav.NavSystem;
import fave.common.sg.ISceneGraph;
import favevex.SSExpr;
import favevex.SSExprList;

public class ParseCoreFaveSetup extends ParseCoreBase {
    public ParseCoreScene scene = null;
    public ParseCoreNav nav = null;
    /*
    public VxRecord record = null;
    */
    public ParseCoreFaveSetup(SSExprList plist) {
        parse(plist);
    }
 
    final public void parse(SSExprList plist) {
        for (SSExpr param : plist) {       
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("scene"))
                scene = new ParseCoreScene(fieldPlist);
            else if (fieldName.equals("nav"))
                nav = new ParseCoreNav(fieldPlist);
            /*
            if (fieldName.equals("record"))
                record = new VxRecord(fieldPlist);
            */
        }
    }
    public void sgCall(ISceneGraph sg, NavSystem navsys) {
        if (scene == null)
            System.out.println("WARNING in ParseCoreFaveSetup: scene == null");
        else if (nav == null)
            System.out.println("WARNING in ParseCoreFaveSetup: nav == null");
        else {
            nav.sgCall(navsys);
            scene.sgCall(sg, "topNode");
        }
    }
}
