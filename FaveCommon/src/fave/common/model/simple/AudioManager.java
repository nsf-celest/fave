/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.model.simple;

import com.jme3.audio.AudioNode;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class AudioManager implements IAudio {
    private static final Logger logger = Logger.getLogger(AudioManager.class.getName());
    Map<String, AudioNode> aliasMap = new HashMap<String, AudioNode>();
    Map<AudioNode, Boolean> useMap = new HashMap<AudioNode, Boolean>();
    boolean audioOn = true;
    public void setAudioOn() {
        audioOn = true;
    }
    public void setAudioOff() {
        audioOn = false;
    }
    // By default, when register a sound, will set use to be true by default
    // (so don't have to check the use map for null all the time
    public void registerClip(String alias, AudioNode anode) { 
        aliasMap.put(alias, anode);
        useMap.put(anode, true);
    }  
    
    public void setUseClip(AudioNode anode, boolean use) {
        if (anode == null)
            logger.log(Level.SEVERE, "Null audio node");
        else
            useMap.put(anode, use);
    }
    public void setUseClip(String alias, boolean use) { 
        AudioNode anode = aliasMap.get(alias);
        if (anode == null)
            logger.log(Level.SEVERE, "No audio node registered for {0}", new Object[]{alias});
        else
            useMap.put(anode, use);
    }
    
    public void triggerClip(AudioNode anode) {
        if (anode!=null && audioOn && useMap.get(anode))
            anode.playInstance();
    }
    public void triggerByName(String alias) {
        triggerClip(aliasMap.get(alias));
    }
}
