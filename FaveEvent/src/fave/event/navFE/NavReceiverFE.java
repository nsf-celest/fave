/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.event.navFE;

import fave.common.nav.INav;
import fave.event.core.Cmd;
import fave.event.core.CmdType;
import fave.event.core.ICmdProcessor;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class NavReceiverFE implements ICmdProcessor {
    INav target;
    public NavReceiverFE(INav target) {
        this.target = target;
    }
    public void processCmd(Cmd cmd) {
        CmdType cmdType = cmd.getCmdType();
        if (cmdType == CmdNavGenericComFE.classGetCmdType()) {
            CmdNavGenericComFE cmdNav = (CmdNavGenericComFE)cmd;
            CmdNavGenericComFE.GenericCom data = cmdNav.data;
            target.genericNav(data.cmd, data.val);
        }
        else if (cmdType == CmdNavFrameTickFE.classGetCmdType()) {
            CmdNavFrameTickFE cmdNav = (CmdNavFrameTickFE)cmd;
            CmdNavFrameTickFE.FrameTickData data = cmdNav.data;
            target.frameTick(data.tframe, data.iframe);
        }
    }
}
