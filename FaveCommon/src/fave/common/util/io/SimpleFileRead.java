/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * Simple class to open a file and read lines.  Use lookahead so that
 * can test if eof before doing getLine, i.e., if you test and then
 * get, you will never receive a null line. (Though if you do just call
 * getLine, you will get a null line at end of file as you would expect)
 */
package fave.common.util.io;

import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class SimpleFileRead {
    private static final String thisName = fave.common.util.io.SimpleFileRead.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    // BufferedReader reader = null;
    Scanner reader = null;
    String fname;
    String nextLine = null;
    boolean readyToStart = false;
    boolean done = false;
    
    public SimpleFileRead(String fname) {
        this.fname = fname;
    }
    public SimpleFileRead() {
    }
    public void open(String fname) {
        this.fname = fname;
        open();
    }
    public void open() {
        try { 
            reader = new Scanner(new BufferedReader(new FileReader(fname)));
            readyToStart = true;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Could not open {0}", new Object[]{fname});
        } 
    }
int nline = 0;
    String getLineInternal() {
        String line = null;
nline++;
        if (reader != null) {
            try {
                line = reader.nextLine();
            }
            catch (Exception e) {
//                 logger.log(Level.SEVERE, "Problem reading {0}", new Object[]{fname});
// logger.log(Level.SEVERE, "Problem reading {0} nline = {1}", new Object[]{fname, nline});
// System.exit(0);
            }
        }
        return line;
    }
    public String getLine() {
        String line = null;
        if (readyToStart) {
            nextLine = getLineInternal();
            readyToStart = false;
        }
        if (nextLine != null) {
            line = nextLine;
            nextLine = getLineInternal();
        }
        if (nextLine == null) {
            done = true;
        }
        return line;
    }
    public float nextFloat() {
        return reader.nextFloat();
    }
    public Vector3f nextVector3f(Vector3f v) {
        float x = reader.nextFloat();
        float y = reader.nextFloat();
        float z = reader.nextFloat();
        if (v == null)
            v = new Vector3f(x, y, z);
        else
            v.set(x, y, z);
        return v;
    }
    public void close() {
// System.out.println("SimpleFileRead.close nlines = "+nline);
        try {
            reader.close();
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Could not close {0}", new Object[]{fname});

       }   
    }
    public boolean eof() {
        return done;  
    }
}

