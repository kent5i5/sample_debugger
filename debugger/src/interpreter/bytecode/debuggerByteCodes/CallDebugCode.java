/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.CallCode;
import interpreter.debugger.debugVirtualMachine;

/**
 *
 * @author ken
 */
public class CallDebugCode extends CallCode {

    //private String CodeName = "CALLDEBUG";
    //private String args;
    //private int addrs;

    public CallDebugCode() {
        //int i=0 ;
        args = "";
    }
/*
    public void init(String aArgs, int symAddrs) {
        int count = 0;
        char ch = aArgs.charAt(count);
        try {
            while (Character.isDefined(ch) && count < aArgs.length()) {  // scan past whitespace
                if (Character.isJavaIdentifierPart(ch) || Character.isDigit(ch)) {
                    args += ch;




                }
                count++;
                ch = aArgs.charAt(count);
            }

        } catch (Exception e) {
            //System.out.println("Finish getting arguments");
        }
        // symbol address 
        addrs = symAddrs;
    }
*/
    public String toString() {
        return CodeName + " " + args;
    }
    
    
    public void execute(VirtualMachine t) {
         debugVirtualMachine debug_t = (debugVirtualMachine)t;
         
         debug_t.BeginScope();
        
        //System.out.println(CodeName +" " + args);
        int p = (Integer) t.peekRunStack();
        String displayCode = CodeName + " " + args + "  " + args + "(";
        t.callFrame(displayCode);
        //int newFrameAt = t.peekRunStack();
        //System.out.println("call last element" +  newFrameAt);
        //t.newFrameAtRunStack(newFrameAt);
        if (addrs != 0) {
            //int a = Integer.parseInt(args);
            t.jal(addrs);
        }
        t.jal(addrs);

        // t.printBytecode(displayCode);
    }
}
