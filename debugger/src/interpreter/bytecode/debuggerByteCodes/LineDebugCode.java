/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import interpreter.debugger.debugVirtualMachine;

/**
 *
 * @author ken
 */
public class LineDebugCode extends ByteCode {

    private String CodeName = "LINE";
    private String args;

    public LineDebugCode() {
        args ="";
    }

    public void init(String aArgs, int symAddrs) {
        args = aArgs;
        //no symbol address
    }

    /*oh toString return the bytecode name */
    public String toString() {
        return CodeName + " " + args;
    }
    
    
    public void execute(VirtualMachine t) {
        debugVirtualMachine debug_t = (debugVirtualMachine)t;
        debug_t.currentLine(args);
        
        String displayCode = CodeName + " " + args;
        t.printBytecode(displayCode);
    }
}
