/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

import java.util.ArrayList;

/**
 *
 * @author ebrisson
 */
public class VxPatch {
    VxDressing dressing = null;
    ArrayList<VxPickable> pickableList = new ArrayList<VxPickable>();
    Integer nrandpickable = null;
    public VxPatch(SSExprList plist) {
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("dressing"))
                dressing = new VxDressing(fieldPlist);
            else if (fieldName.equals("pickable"))
                pickableList.add(new VxPickable(fieldPlist));
            if (fieldName.equals("nrandpickable"))
                nrandpickable = Integer.parseInt(fieldPlist.get(0).stringVal());
        }
    }
}
