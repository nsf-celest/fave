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
public class NavSenderFE implements INav {
    ICmdProcessor cmdTarget;
    public NavSenderFE(ICmdProcessor cmdTarget) {
        this.cmdTarget = cmdTarget;
    }
    public void genericNav(NavCommandType cmd, float val) {
        cmdTarget.processCmd(new CmdNavGenericComFE(cmd, val));
    }     
    public void frameTick(float tframe, int iframe) {
        cmdTarget.processCmd(new CmdNavFrameTickFE(tframe, iframe));
    }
}
