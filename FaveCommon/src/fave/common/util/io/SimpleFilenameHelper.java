/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.util.io;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

public class SimpleFilenameHelper {
    String sdateFixed = null;
    final public void initSdate() {
        if (sdateFixed == null) {
            DateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
            Date date = new Date();
            sdateFixed = dateFormat.format(date);
        }
    }
    String getSdate() {
        if (sdateFixed == null) {
            initSdate();
        }
        return sdateFixed;
    }
    public SimpleFilenameHelper() {
    }
    public SimpleFilenameHelper(boolean useDate) {
        if (useDate) {
            initSdate();
        }
    }
    public String makeFullname(String dir, String namebase, String trialId,
                               String extension, boolean addDate) {   
        String sdir = "";
        String strial = "";
        String sdate = "";
        String sext = "";         
        
        if (dir!=null && !dir.equals("")) {
            sdir = dir + "/";
        }
        if (trialId!=null && !trialId.equals("")) {
            strial = trialId + "_";
        }
        if (addDate) {
            sdate = getSdate();
        }
        if (extension!=null && !extension.equals("")) {
            // sext = "." + extension;
            sext = extension;
        }        
        
        return sdir + strial + namebase + sdate + sext;
    }
}
