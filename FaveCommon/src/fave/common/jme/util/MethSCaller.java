/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.jme.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ebrisson
 */
public class MethSCaller {
    ArrayList<String> sList = new ArrayList<String>();
    public MethSCaller() {
    }
    public MethSCaller(String ... sin) {
        sList.addAll(Arrays.asList(sin));
    }
    public void add(String s) {
        sList.add(s);
    }
    
    int n() {return sList.size();}
    public String fname() {
        if (n() > 0)
            return sList.get(0);
        else
            return null;
    }
    public int nargs() {
        return n()-1;
    }
    public String arg(int i) {
        if (i+1 < sList.size())
            return sList.get(i+1);
        else
            return null;
    }
}
