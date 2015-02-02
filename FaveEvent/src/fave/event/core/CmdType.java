/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.event.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class CmdType {
    static final Logger logger = Logger.getLogger(CmdType.class.getName());
    String sid;
    Class cmdClass;
    public CmdType(String sid, Class cmdClass) {
// System.out.println("CmdType.CmdType: sid, cmdClass = "+sid+" "+cmdClass.toString());
        this.sid = sid;
        this.cmdClass = cmdClass;
    }
    public String getSid() {
        return sid;
    }
    public Class getCmdClass() {
        return cmdClass;
    }
    public Object getCmdInstance() {
        return instanceFromClass(cmdClass);
    }
    // This is independent of any class - just here to be ... somewhere
    public static Object instanceFromClass(Class c) {  
// System.out.println("CmdType.instanceFromClass: c = "+c.toString());
        Object obj = null;
        try {
            obj = (Object)(c.newInstance());
        } catch (InstantiationException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return obj;
    } 
}
