/* ParseCoreNav.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.common.parsecore;

import fave.common.nav.NavSystem;
import static fave.common.parsecore.ParseCoreBase.defaultName;
import favevex.SSExpr;
import favevex.SSExprList;

public class ParseCoreNav extends ParseCoreBase {
    String mode = "FREEFLY";
    public ParseCoreNav(SSExprList plist) {
        name = defaultName("ParseCoreNav");
        for (SSExpr param : plist) {
            if (param.isAtom())
                System.out.println("ParseCoreNav got an atom, expected a list.");
            else {
                String fieldName = param.fieldName();
                SSExprList fieldPlist = param.paramList();
                if (fieldName.equals("navmode"))
                    mode = fieldPlist.getString(0);
            }
        }     
    }
    public void sgCall(NavSystem navsys) {
        navsys.init(mode);
    }

}
