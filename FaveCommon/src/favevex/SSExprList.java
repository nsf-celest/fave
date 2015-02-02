/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

import java.util.ArrayList;

/**
 *
 * @author ebrisson
 */

public class SSExprList extends ArrayList<SSExpr> {
    
    protected String indentUnit = "  ";
    public void stringDump() {
        stringDumpIndented(indentUnit);
    }
    
    public void stringDumpIndented(String indent) {
        System.out.println();
        System.out.print(indent+"[");
        for (SSExpr expr : this) {
            // param.stringDumpIndented(indent+indentUnit);
            // System.out.print("<");
            expr.stringDumpIndented(indent+indentUnit);
            // System.out.print(">");
        }
        System.out.println();
        System.out.print(indent+"]");
    }
    
    public String getString(int i) {
        return get(i).stringVal();
    }
    public Integer getInteger(int i) {
        return get(i).integerVal();
    }
    public Float getFloat(int i) {
        return get(i).floatVal();
    }
    public Double getDouble(int i) {
        return get(i).doubleVal();
    }

}
