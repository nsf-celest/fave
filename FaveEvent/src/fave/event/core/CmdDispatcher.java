/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class CmdDispatcher implements ICmdProcessor {
    ArrayList<ICmdProcessor> toAll = new ArrayList<ICmdProcessor>();
    Map<CmdType, ArrayList<ICmdProcessor>> cmdMap = new HashMap<CmdType, ArrayList<ICmdProcessor>>();

    // To specify that commands of a specific type be passed on
    public void addCmdTarget(CmdType t, ICmdProcessor i) {
        ArrayList<ICmdProcessor> ilist = cmdMap.get(t);
        if (ilist == null) {
            ilist = new ArrayList<ICmdProcessor>();
            cmdMap.put(t, ilist);
        }
        ilist.add(i);
    }

    // To specify that commands of a specific type be passed on
    public void addCmdTargetList(CmdType[] tlist, ICmdProcessor i) {
        for (CmdType t : tlist)
            addCmdTarget(t, i);
    }

    // To dispatch (pass on) all commands that come to this processor
    public void addAllTarget(ICmdProcessor i) {
        if (i != null)
            toAll.add(i);
    }

    public void processCmd(Cmd cmd) {
        for (ICmdProcessor i : toAll)
            i.processCmd(cmd);
        /*
        for (Iterator<ICmdProcessor> it = toAll.iterator(); it.hasNext();) {
            ICmdProcessor i = it.next();
            i.processCmd(cmd);
        }
        */

        CmdType t = cmd.getCmdType();
        ArrayList<ICmdProcessor> ilist = cmdMap.get(t);
        if (ilist != null) {
// System.out.println("CmdDispatcher.processCmd sending cmdType "+t);
            for (ICmdProcessor i : ilist)  
                i.processCmd(cmd);
        }
    }
}

