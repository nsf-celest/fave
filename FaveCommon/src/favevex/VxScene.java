/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

import java.util.ArrayList;

/**
 *
 * @author ebrisson@bu.edu
 */
public class VxScene {
    ArrayList<VxPatch> patchList = new ArrayList<VxPatch>();
    public VxScene(SSExprList plist) {
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("patch"))
                patchList.add(new VxPatch(fieldPlist));
        }     
    }
}
