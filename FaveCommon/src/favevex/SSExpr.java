/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

import fave.common.util.io.ReadTokens;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author ebrisson
 * an SSParam is a bit like an s-expression
 * an SSParam can either be a String (atom) or a SSParamList (sfield arg ... arg) (list)
 */
public class SSExpr {
    private SSExprList elist = new SSExprList();
    private String sval = null;
    
    static public SSExpr createAtom(String s) {
        SSExpr result = new SSExpr();
        result.sval = s;
        return result;
    }
    static public SSExpr createList() {
        return new SSExpr();
    }
    public void addEntry(SSExpr e) {
        if (isList())
            elist.add(e);
    }
    
    public Boolean isAtom() {
        return (sval != null);
    }
    public Boolean isList() {
        return (sval == null);
    }
    public String fieldName() {
        if (isAtom() || elist.size()==0)
            return null;
        else
            return elist.get(0).sval;
    }
    public SSExprList paramList() {
        SSExprList plist = new SSExprList();
        Boolean first = true;
        for (SSExpr entry : elist)
            if (first)
                first = false;
            else
                plist.add(entry);
        return plist;
    }
    public String stringVal() {
        return sval;
    }
    public Integer integerVal() {
        return Integer.getInteger(sval);
    }
    public Double doubleVal() {
        return Double.parseDouble(sval);
    }
    public Float floatVal() {
        return Float.parseFloat(sval);
    }

    static public SSExpr fromFile(String fname) {
        ReadTokens getter = new ReadTokens();
        ArrayList<String> sinput = getter.fromFile(fname);
        Stack<SSExpr> stack = new Stack<SSExpr>();     
        SSExpr elistCur = null;          
        SSExpr eresult = null;
        for (String token : sinput) {
            if (token.equals("(")) {
                SSExpr elistNew = SSExpr.createList();
                if (elistCur == null)
                    eresult = elistNew;
                else
                    elistCur.addEntry(elistNew);
                stack.push(elistCur);                
                elistCur = elistNew;
            }
            else if (token.equals(")"))
                elistCur = stack.pop();
            else
                elistCur.addEntry(SSExpr.createAtom(token));
        }
        return eresult;
    }
        
    private String indentUnit = "  ";
    private String spaceUnit = " ";
    public void stringDump() {
        stringDumpIndented(indentUnit);
    }
 
    public void stringDumpIndented(String indent) {
        if (isAtom())
            System.out.print(spaceUnit+sval+spaceUnit);
        else
            elist.stringDumpIndented(indent);
    }
 
}
