/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.core;

import fave.common.util.io.SimpleFileWrite;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */

// Either supply an open SimpleFileWriter
// or use the open and close commands

public class CmdWriter implements ICmdProcessor {
    SimpleFileWrite writer;
    public CmdWriter(SimpleFileWrite writer) {
        this.writer = writer;
    }
    public CmdWriter() {
    }
    public CmdWriter(String fname) {
        openInternal(fname);
    }
    final void openInternal(String fname) {
        writer = new SimpleFileWrite(fname);
        writer.open();
// System.out.println("CmdWriter opened "+fname);
    }
    public void open(String fname) {
        openInternal(fname);
    }
    public void close() {
        writer.close();
    }
    public void processCmd(Cmd cmd) {
        String[] tokens = cmd.asTokens();
// System.out.print("CmdWriter.processCmd: tokens =");
// for (String s : tokens)
// System.out.print(" "+s);
// System.out.println();
        String delimiter = " ";
        for (int itoke=0; itoke<tokens.length; itoke++) {
            if (itoke < tokens.length-1)
                writer.putString(tokens[itoke]+delimiter);
            else
                writer.putLine(tokens[itoke]);
        }
    }
}
