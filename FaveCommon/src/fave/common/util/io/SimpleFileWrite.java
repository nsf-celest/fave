/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.util.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author E12
 */
public class SimpleFileWrite {
    private static final String thisName = fave.common.util.io.SimpleFileWrite.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    BufferedWriter writer = null;
    String fname;
    public SimpleFileWrite(String fname) {
        this.fname = fname;
    }
    public void open() { // throws IOException {
// System.out.println("SimpleFileWrite.open fname = "+fname);
        // try { 
        try {
            writer = new BufferedWriter(new FileWriter(fname));
        } 
        // catch (Exception e) {
        //     logger.log(Level.SEVERE, "Could not open {0}", new Object[]{fname});
        // }
        catch(IOException ex) { 
            logger.log(Level.SEVERE, "Could not find {0}", new Object[]{fname});
        }

    }
    public void putLine(String line) {
        putStringInternal(line, true);
    }
    public void putString(String s) {
        putStringInternal(s, false);
    }
    void putStringInternal(String token, boolean addNewline) {
// System.out.println("SimpleFileWrite.putStringInternal token = "+token);
        if (writer != null) {
            try {
                writer.write(token);
                if (addNewline)
                    writer.newLine();
                    // writer.write("\n");
            }
            catch (Exception e) {
                logger.log(Level.SEVERE, "Problem writing {0}; exception {1}", new Object[]{fname, e});
            }
        }
    }
    public void close() {
// System.out.println("SimpleFileWrite.close fname = "+fname);
        try {
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Could not close {0}", new Object[]{fname});
       }   
    }
}
