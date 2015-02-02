/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

/**
 *
 * @author ebrisson
 */
public class VxRoot {

    VxFaveSetup setup = null;
    public VxRoot(SSExpr expr) {
        String fieldName = expr.fieldName();
        if (fieldName.equals("favesetup"))
            setup = new VxFaveSetup(expr.paramList());
    }
}
