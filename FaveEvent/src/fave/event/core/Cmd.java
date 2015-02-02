/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.event.core;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
abstract public class Cmd {
    abstract public CmdType getCmdType();
    abstract public String[] asTokens();
    abstract public void fromTokens(String[] tokens);
}
