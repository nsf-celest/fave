/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.event.core;

import fave.common.util.io.ScannerPlus;
import java.util.Scanner;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
// Either supply an open scannerplus
// or use the open and close commands

public class CmdReader {
    ScannerPlus reader;
    Scanner scanner;
    protected CmdTypeMap keyMap;

    public CmdReader(String fname) {
        openInternal(fname);
    }
 
    public CmdReader(CmdTypeMap keyMap) {
        this.keyMap = keyMap;
    }
 
    public void setReader(ScannerPlus reader) {
        this.reader = reader;
        scanner = reader.scanner;
    }

    public void setKeyMap(CmdTypeMap keyMap) {
        this.keyMap = keyMap;
    }

    final void openInternal(String fname) {
        reader = new ScannerPlus(fname);
        scanner = reader.scanner;
    }

    public void open(String fname) {
        openInternal(fname);
    }
    
    public void close() {
        reader.close();
    }
    
    public boolean eof() {
        return (!scanner.hasNext());
    }
    
    public Cmd nextCmd() {
        if (eof())
            return null;
        else {
            String delimiter = " ";
            String sin = scanner.nextLine();
            // System.out.println("CmdReader.nextCmd sin = "+sin);
            String[] tokens = sin.split(delimiter);
            Cmd cmd = null;
            if (tokens != null) {
                cmd = (Cmd)(keyMap.instanceFromSid(tokens[0]));
                if (cmd != null)
                    cmd.fromTokens(tokens);
                // System.out.println("CmdReader.nextCmd: cmdType = "+cmd.getCmdType());
            }
            return cmd;    
        }       
    }
}
