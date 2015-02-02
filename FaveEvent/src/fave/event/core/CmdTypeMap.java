/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.event.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import fave.event.core.CmdType;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class CmdTypeMap {
    private static final Logger logger = Logger.getLogger(CmdTypeMap.class.getName());
   
    Map<Class,  CmdType> classToCmdType = new HashMap<Class,  CmdType>();
    Map<String, CmdType> sidToCmdType   = new HashMap<String, CmdType>();
  
    // need to check for previous inclusion of class or sid
    /*
    public CmdType register(Class c, String sid) {
        CmdType cmdType = new CmdType(c, sid);
        sidToCmdType.put(sid, cmdType);
        classToCmdType.put(c, cmdType);
        return cmdType;
    }
    */

    public void register(CmdType cmdType) {
        if (sidToCmdType.get(cmdType.getSid())==null 
                    && classToCmdType.get(cmdType.getCmdClass())==null) {          
            sidToCmdType.put(cmdType.getSid(), cmdType);
            classToCmdType.put(cmdType.getCmdClass(), cmdType);
        }
    }
    public void registerList(CmdType[] cmdTypeList) {
        for (CmdType t : cmdTypeList) {
            register(t);
        }
    }
    
    /*
    public CmdType fromString(String sid) {
        return sidToCmdType.get(sid);
    }
    public CmdType fromClass(Class c) {
        return classToCmdType.get(c);
    }
    */
    
    public Object instanceFromSid(String sid){
        if (sid == null) {
            logger.log(Level.SEVERE, "null sid");
            return null;
        } else {
            CmdType cmdType = sidToCmdType.get(sid);
            if (cmdType == null) {
                logger.log(Level.SEVERE, "{0} not in key map", new Object[]{sid});     
                return null;
            } else
                return instanceFromCmdType(cmdType);
        }
    }

    public Object instanceFromCmdType(CmdType cmdType) {
        if (cmdType == null)
            return null;
        else 
            return cmdType.getCmdInstance();
    }

    // This is independent of any class - just here to be ... somewhere
    public static Object instanceFromClass(Class c){
        Object obj = null;
        try {
            obj = (Object)c.newInstance();
        } catch (InstantiationException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return obj;
    }  
}
