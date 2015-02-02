/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

/**
 *
 * @author ebrisson
 */
public class VxFaveSetup {
    public VxScene scene = null;
    public VxNav nav = null;
    public VxRecord record = null;
    public VxFaveSetup(SSExprList plist) {
        for (SSExpr param : plist) {       
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("scene"))
                scene = new VxScene(fieldPlist);
            else if (fieldName.equals("nav"))
                nav = new VxNav(fieldPlist);
            if (fieldName.equals("record"))
                record = new VxRecord(fieldPlist);
        }
    }
}
