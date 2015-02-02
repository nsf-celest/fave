/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

/**
 *
 * @author ebrisson
 */
public class VxNav {
    String method = null;
    public VxNav(SSExprList plist) {
        for (SSExpr param : plist) {
            String fieldName = param.fieldName();
            SSExprList fieldPlist = param.paramList();
            if (fieldName.equals("method"))
                method = fieldPlist.get(0).stringVal();
        }  
    }
}
