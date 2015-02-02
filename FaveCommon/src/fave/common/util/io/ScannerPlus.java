/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.util.io;

import com.jme3.math.Vector3f;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author E12
 * Either supply a scanner (caller manages)
 * or construct with fname, and close
 */
public class ScannerPlus {
    private static final String thisName = fave.common.util.io.ScannerPlus.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    public Scanner scanner;
    public ScannerPlus(Scanner scanner) {
        this.scanner = scanner;
    }
    public ScannerPlus(String fname) {
        try { 
            scanner = new Scanner(new File(fname));
        } 
        catch(FileNotFoundException ex) { 
            logger.log(Level.SEVERE, "Could not find {0}", new Object[]{fname});
        }
    }
    public void close() {
        scanner.close();
    }
    public Vector3f nextVector3f(Vector3f v) {
        float x = scanner.nextFloat();
        float y = scanner.nextFloat();
        float z = scanner.nextFloat();
        if (v == null)
            v = new Vector3f(x, y, z);
        else
            v.set(x, y, z);
        return v;
    }
}
