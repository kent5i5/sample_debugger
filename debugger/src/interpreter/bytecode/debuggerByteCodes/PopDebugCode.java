/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.PopCode;
import interpreter.debugger.debugVirtualMachine;

/**
 *
 * @author ken
 */
public class PopDebugCode extends PopCode {

    //private String CodeName = "POPDEBUG";
    //private String args;
    //private long addrs;

    public PopDebugCode() {
        //int i=0 ;
        //args = v.get(i);
    }
/*
    public void init(String aArgs, int symAddrs) {
        args = aArgs;
        //no symbol address
    }
*/
    public String toString() {
        return super.CodeName + " " + super.args;
    }
    
    

    public void execute(VirtualMachine t) {
        debugVirtualMachine debug_t = (debugVirtualMachine)t;
        debug_t.popEnv(args);

        //System.out.println("PopCode Executing");
        int n = Integer.parseInt(args);
        while (n > 0) {
            t.popRunStack();
            n -= 1;
        }
        String displayCode = super.CodeName + " " + super.args;
        t.printBytecode(displayCode);
    }
}
