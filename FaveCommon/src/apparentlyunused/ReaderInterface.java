/*
 * Developed as part of the Celeste FLAME project, 2013
 */
// package fave.common.util.io;
package apparentlyunused;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public interface ReaderInterface {
    public void open(String fname);
    public Object read();
    public void close();
    public boolean eof();       
}
