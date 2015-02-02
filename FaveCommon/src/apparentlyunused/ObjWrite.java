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

public class ObjWrite {
    ObjectOutputStream oos;
int n = 0;
    public void open(String fname) {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fname));
        }
        catch (IOException ex) {
            Logger.getLogger(ObjWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(Object obj) {
        try {
// System.out.println("ObjWrite.write: class of obj" + obj.getClass());
	    // oos.reset();
            oos.writeObject(obj);
            // oos.flush();
        }
        catch (IOException ex) {
            Logger.getLogger(ObjWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try { 
            // oos.flush();
            oos.close();
        }
        catch (IOException ex) {
            Logger.getLogger(ObjWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


