/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// package fave.common.util.io;
package apparentlyunused;

/**
 *
 * @author E12
 */
public interface WriterInterface {
    public void open(String fname);
    public void write(Object obj);
    public void close();
}
