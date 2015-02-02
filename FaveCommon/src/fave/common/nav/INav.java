/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.common.nav;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public interface INav {
    public enum NavCommandType { NONE, DONE, PICK,
                LEFTSPEED, RIGHTSPEED, FORWARDSPEED, BACKWARDSPEED,
                LOOKLEFTSPEED, LOOKRIGHTSPEED, LOOKUPSPEED, LOOKDOWNSPEED,
                SETLOOKMARK, GOTOLOOKMARK
    }
    public void genericNav(NavCommandType cmd, float val);
    public void frameTick(float tframe, int iframe); 
}
