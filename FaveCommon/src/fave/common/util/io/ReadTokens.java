/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author E12
 */
public class ReadTokens {
    private static final Logger logger = Logger.getLogger("ReadTokens");
    public enum method {PARENS}
    method useMethod = method.PARENS;
    public void setMethod(method m) {
        useMethod = m;
    }
    public ArrayList<String> fromFile(String fname) {
        String content = null;
        try {
            content = new Scanner(new File(fname)).useDelimiter("\\Z").next();
        }
        catch(FileNotFoundException ex) { 
            logger.log(Level.SEVERE, "Could not find {0}", new Object[]{fname});
        }
        return fromString(content);
    }
    public ArrayList<String> fromString(String content) {
        ArrayList<String> result = new ArrayList<String>();
        if (content != null) {  
            switch (useMethod) {
                case PARENS:
                    content = content.replace("(", " ( ");
                    content = content.replace(")", " ) ");
                break;
            }
            Scanner scanner = new Scanner(content);
            while (scanner.hasNext())
                result.add(scanner.next());
        } 
        return result;
    }
    
}
