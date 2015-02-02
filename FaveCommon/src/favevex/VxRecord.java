/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

/**
 *
 * @author ebrisson
 */
public class VxRecord {
    String fileDummy = null;
    public VxRecord(SSExprList plist) {
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("target"))
                fileDummy = fieldPlist.get(0).stringVal();
        }  
    }

}
