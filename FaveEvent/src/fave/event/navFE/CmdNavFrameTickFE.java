/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.event.navFE;

import fave.event.core.Cmd;
import fave.event.core.CmdType;

/**
 *
 * @author ebrisson
 */
public class CmdNavFrameTickFE extends Cmd {
    static CmdType myType = new CmdType("NavFrameTickFE", CmdNavFrameTickFE.class);
    static public CmdType classGetCmdType() { return myType; };
    public CmdType getCmdType() { return myType; }

    public class FrameTickData {
        public float tframe;
        public int iframe;
    }
    public FrameTickData data = new FrameTickData();

    public CmdNavFrameTickFE() {
    }
    
    public CmdNavFrameTickFE(float tframe, int iframe) {
        setData(tframe, iframe);
    }
       
    final public void setData(float tframe, int iframe) {
        data.tframe = tframe;
        data.iframe = iframe;
    }

    public String[] asTokens() {
        String[] tokens = {
            classGetCmdType().getSid(),
            Float.toString(data.tframe),
            Integer.toString(data.iframe)
        };
        return tokens;
    }
    public void fromTokens(String[] tokens) {
        int itoken = 1;
        data.tframe = Float.parseFloat(tokens[itoken++]);
        data.iframe = Integer.parseInt(tokens[itoken++]);
    }
}