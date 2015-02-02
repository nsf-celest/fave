/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.navFE;

import fave.common.nav.INav.NavCommandType;
import fave.event.core.Cmd;
import fave.event.core.CmdType;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Set;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class CmdNavGenericComFE extends Cmd  {
    static CmdType myType = new CmdType("CmdNavGenericComFE", CmdNavGenericComFE.class);
    static public CmdType classGetCmdType() { return myType; };
    public CmdType getCmdType() { return myType; }
    final static EnumMap<NavCommandType, String> cmdToString
           = new EnumMap<NavCommandType, String>(NavCommandType.class);
    Set<NavCommandType> keys = cmdToString.keySet();
    Collection<String> scmds = cmdToString.values();
    static {
        cmdToString.put(NavCommandType.NONE, "none");
        cmdToString.put(NavCommandType.DONE, "done");
        cmdToString.put(NavCommandType.PICK, "pick");
        cmdToString.put(NavCommandType.LEFTSPEED,      "leftspeed");
        cmdToString.put(NavCommandType.RIGHTSPEED,     "rightspeed");
        cmdToString.put(NavCommandType.FORWARDSPEED,   "forwardspeed");
        cmdToString.put(NavCommandType.BACKWARDSPEED,  "backwardspeed");
        cmdToString.put(NavCommandType.LOOKLEFTSPEED,  "lookleftspeed");
        cmdToString.put(NavCommandType.LOOKRIGHTSPEED, "lookrightspeed");
        cmdToString.put(NavCommandType.LOOKUPSPEED,    "lookupspeed");
        cmdToString.put(NavCommandType.LOOKDOWNSPEED,  "lookdownspeed");
        cmdToString.put(NavCommandType.SETLOOKMARK,    "setlookmark");
        cmdToString.put(NavCommandType.GOTOLOOKMARK,   "gotolookmark");
    }

    public class GenericCom {
        public float val;
        public NavCommandType cmd;
    }
    public CmdNavGenericComFE.GenericCom data = new CmdNavGenericComFE.GenericCom();
  
    public CmdNavGenericComFE() {
    }
    
    public CmdNavGenericComFE(NavCommandType cmd, float val) {
        setData(cmd, val);
    }

    final public void setData(NavCommandType cmd, float val) {
        data.cmd = cmd;
        data.val = val;
    }
   
    public NavCommandType getCmd() {
        return data.cmd;
    }

    public float getVal() {
        return data.val;
    }

    public String[] asTokens() {
        String[] tokens = {
            classGetCmdType().getSid(),
            cmdToString.get(data.cmd),
            Float.toString(data.val)
        };
        return tokens;
    }
    
    public void fromTokens(String[] tokens) {
        int itoken = 1;
        String scmd = tokens[itoken++];
        data.cmd = NavCommandType.NONE;
        for (NavCommandType key : keys) {
            if (cmdToString.get(key).equals(scmd))
                data.cmd = key;
        }
        data.val = Float.parseFloat(tokens[itoken++]);
    }
}
