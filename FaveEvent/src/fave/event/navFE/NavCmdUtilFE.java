/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.navFE;

import fave.event.core.CmdType;
import fave.event.core.CmdTypeMap;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class NavCmdUtilFE {
    static CmdTypeMap fullCmdTypeMap = null;
    static CmdType[] fullCmdTypeList = {
        CmdNavGenericComFE.classGetCmdType(),
        CmdNavFrameTickFE.classGetCmdType()
    };
    public static CmdTypeMap getFullCmdTypeMap() {
        if (fullCmdTypeMap == null) {
            fullCmdTypeMap = new CmdTypeMap();
            fullCmdTypeMap.registerList(getFullCmdTypeList());
        }
        return fullCmdTypeMap;
    }
    public static CmdType[] getFullCmdTypeList() {
        return fullCmdTypeList;
    }
}
