/* ParseCoreRoot.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 * 
 * * Notes:
 *    FaveCoreRoot is a defalt outer enclosing block.  It is not referred to explicitly in config files.
 *    FaveCoreRoot is treated as a singleton, i.e., any top level block in the heirarchy
 *    will contribute members to one FaveCoreRoot instance
 */

package fave.common.parsecore;

import fave.common.nav.NavSystem;
import fave.common.sg.ISceneGraph;
import favevex.SSExpr;

public class ParseCoreRoot extends ParseCoreBase {
    ParseCoreFaveSetup setup = null;
    public ParseCoreRoot(SSExpr expr) {
        parse(expr);
    }
    
    final public void parse(SSExpr expr) {
        String fieldName = expr.fieldName();
        if (fieldName.equals("favesetup")) {
            if (setup == null)
                setup = new ParseCoreFaveSetup(expr.paramList());
            else
                setup.parse(expr.paramList());
        }
    }
    
    public void sgCall(ISceneGraph sg, NavSystem navsys) {
        if (setup == null)
            System.out.println("WARNING in ParseCoreRoot: setup == null");
        else
            setup.sgCall(sg, navsys);
    }
}
