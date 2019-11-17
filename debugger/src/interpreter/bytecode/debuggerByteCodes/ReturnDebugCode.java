/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.ReturnCode;
import interpreter.debugger.debugVirtualMachine;

/**
 *
 * @author ken
 */
public class ReturnDebugCode extends ReturnCode {

    //private String CodeName = "RETURNDEBUG";
    //private String args;
    //private int addrs;

    public ReturnDebugCode() {
        //int i=0 ;
        //args = v.get(i);
    }
/*
    public void init(String aArgs, int symAddrs) {
        args = aArgs;
        //symbol address
        addrs = symAddrs;
    }
*/
    public String toString() {
        return CodeName + " " + args;
    }
    
    

    public void execute(VirtualMachine t) {
        debugVirtualMachine debug_t = (debugVirtualMachine)t;
        debug_t.returnStates();
        debug_t.EndScope();
        
        //System.out.println(CodeName +" " + args);
        //t.popRunStack();
        if (args == null) {

            t.popFrameRunStack();
            String displayCode = CodeName + " " + args + "  exit " + args;
            t.returnFrame(0, displayCode);
        } else {

            t.popFrameRunStack();
            String displayCode = CodeName + " " + args + "  exit " + args;
            t.returnFrame(addrs, displayCode);
        }
        //t.printBytecode(displayCode);

    }
}
