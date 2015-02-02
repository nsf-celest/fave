/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class GenericStruct {
    private static final Logger logger = Logger.getLogger(GenericStruct.class.getName());
    ArrayList<String>   listString  = null;
    ArrayList<Integer>  listInteger = null;
    ArrayList<Float>    listFloat   = null;
    int iString;
    int iInteger;
    int iFloat;
    
    public void getReset() {
        iString = 0;
        iInteger = 0;
        iFloat = 0;
    }
    
    public void putString(String s) {
        if (listString == null)
            listString = new ArrayList<String>();
        listString.add(s);
    }
    public boolean hasNextString() {
        if (listString==null || iString>=listString.size())
            return false;
        else 
            return true;
    }
    public String getNextString() {
        return listString.get(iString++);
    }
    public String getString(int i) {
        if (i<listString.size())
            return listString.get(i);
        else {
            logger.log(Level.SEVERE, "index out of range");
            return null;
        }
    }
    
    public void putInt(int i) {
        if (listInteger == null)
            listInteger = new ArrayList<Integer>();
        listInteger.add(i);
    }
    public  boolean hasNextInt() {
        if (listInteger==null || iInteger>=listInteger.size())
            return false;
        else
            return true;
    }
    public int getNextInt() {
        return listInteger.get(iInteger++);
    }
    public int getInt(int i) {
        if (i<listInteger.size())
            return listInteger.get(i);
        else {
            logger.log(Level.SEVERE, "index out of range");
            return -1;
        }
    }
    
    public void putFloat(float f) {
        if (listFloat == null)
            listFloat = new ArrayList<Float>();
        listFloat.add(f);
    }
    public boolean hasNextFloat() {
        if (listFloat==null || iFloat>=listFloat.size())
            return false;
        else
            return true;
    }
    public float getNextFloat() {
        return listFloat.get(iFloat++);
    }
    public float getFloat(int i) {
        if (i<listFloat.size())
            return listFloat.get(i);
        else {
            logger.log(Level.SEVERE, "index out of range");
            return 0f;
        }
    }
    
    public String[] asTokens(String cmdSid, String subType) {
        int ns = 0;
        if (listString != null)
            ns = listString.size();
        int ni = 0;
        if (listInteger != null)
            ni = listInteger.size();
        int nf = 0;
        if (listFloat != null)
            nf = listFloat.size();
        int n = ns + ni + nf + 3;
        if (cmdSid != null)
            n++;
        if (subType != null)
            n++;
        
        String[] tokens = new String[n];
        int itoke = 0;

        if (cmdSid != null)
            tokens[itoke++] = cmdSid;
        if (subType != null)
            tokens[itoke++] = subType;
        tokens[itoke++] = Integer.toString(ns);
        for (int i=0; i<ns; i++)
            tokens[itoke++] = listString.get(i);
       
        tokens[itoke++] = Integer.toString(ni);
        for (int i=0; i<ni; i++)
            tokens[itoke++] = Integer.toString(listInteger.get(i));
        
        tokens[itoke++] = Integer.toString(nf);
        for (int i=0; i<nf; i++)
            tokens[itoke++] = Float.toString(listFloat.get(i));
        
        return tokens;
    }
    
    public String[] asTokens(String cmdSid) {
        return asTokens(cmdSid, null);
    }
    
     public String[] asTokens() {
        return asTokens(null);
    }
 
    public void fromTokens(String[] tokens, int istart) {
        listString = new ArrayList<String>();
        listInteger = new ArrayList<Integer>();
        listFloat = new ArrayList<Float>();
   
        int itoke = istart;
        
        int nlist = Integer.parseInt(tokens[itoke++]);
        for (int ilist=0; ilist<nlist; ilist++)
            listString.add(tokens[itoke++]);

        nlist = Integer.parseInt(tokens[itoke++]);
        for (int ilist=0; ilist<nlist; ilist++)
            listInteger.add(Integer.parseInt(tokens[itoke++]));
        
        nlist = Integer.parseInt(tokens[itoke++]);
        for (int ilist=0; ilist<nlist; ilist++)
            listFloat.add(Float.parseFloat(tokens[itoke++]));
    }

}
