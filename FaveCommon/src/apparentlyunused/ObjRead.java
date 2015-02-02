/*
 * Developed as part of the Celeste FLAME project, 2013
 */
// package fave.common.util.io;
package apparentlyunused;
 
/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjRead implements ReaderInterface {
    FileInputStream istream;
    ObjectInputStream ois;

    public void open(String fname) {
        try {
            istream = new FileInputStream(fname);
            ois = new ObjectInputStream(istream);
        }
        catch (IOException ex) {
            Logger.getLogger(ObjRead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object read()  {
        Object obj = null;
        try {
	    obj = ois.readObject();
        }
        catch (IOException ex) {
            Logger.getLogger(ObjRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ObjRead.class.getName()).log(Level.SEVERE, null, ex);
        }
	return obj;
    }

    public void close() {
        try {
            ois.close();
        }
        catch (IOException ex) {
            Logger.getLogger(ObjRead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean eof() {
        boolean result = false;
        try {
            result = (istream.available() <= 0);
        }
        catch (IOException ex) {
            Logger.getLogger(ObjRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}


