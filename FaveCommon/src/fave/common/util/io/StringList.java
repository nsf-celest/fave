/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class StringList {
    private static final String thisName = StringList.class.getName();
    private static final Logger logger = Logger.getLogger(thisName);
    BufferedReader reader = null;
    List<String> lines = new ArrayList<String>();
    public StringList(String fname) {
        try { 
            reader = new BufferedReader(new FileReader(fname)); 
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Could not open {0}", new Object[]{fname});
        } 
        
        if (reader != null) {
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    lines.add(line);
            }
            catch (Exception e) {
                logger.log(Level.SEVERE, "Problem reading {0}", new Object[]{fname});
            }

            try {
                reader.close();
            }
            catch (Exception e){
                logger.log(Level.SEVERE, "Could not close {0}", new Object[]{fname});
           }
        }
    }
    public void append(StringList slAppend) {
        for (String lineAppend : slAppend.lines)
            lines.add(lineAppend);
    }
    public void appendFromFile(String fname) {
        append(new StringList(fname));
    }
    public ListIterator<String> getIterator() {
        ListIterator<String> iter = lines.listIterator();
        return iter;
    }
    
    /* Not tested, not used, but may be useful
    public void getMatches(String firstToken, List<String> linesMatch) {
        for (int i=0; i<lines.size(); i++) {
            if (lines.get(i).matches(firstToken+"(.*)")) {
                linesMatch.add(lines.get(i));
            }
        }
    }
    */
}
