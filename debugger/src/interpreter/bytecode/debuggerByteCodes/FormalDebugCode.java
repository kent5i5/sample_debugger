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
public class FormalDebugCode extends ByteCode{

    private String CodeName = "FORMAL";
    private String arg;
    private String arg1;
    public FormalDebugCode(){
        arg ="";
        arg1 = "";
    }
    
    public void init(String aArgs, int symAddrs) {
        int isArg = 0;
        int count = 0;
        char ch = aArgs.charAt(count);
        try {
            while (Character.isDefined(ch) && count < aArgs.length() && isArg == 0) {  // scan past whitespace

                 
                arg += ch;                               //we add it to the string
                count++;
                ch = aArgs.charAt(count);
                if (Character.isWhitespace(ch)) {
                    isArg = 1;
                }

            }
            isArg = 0;
            while (Character.isWhitespace(ch) && count < aArgs.length() && isArg == 0) {  // scan past whitespace

                count++;
                ch = aArgs.charAt(count);

                while (Character.isDefined(ch) && count < aArgs.length() && isArg == 0) {    //if next ch is a char 
                    arg1 += ch;                               //we add it to the string
                    count++;
                    ch = aArgs.charAt(count);
                    // if(Character.isWhitespace(ch))
                    // isArg = 1;
                }

            }
        } catch (Exception e) {
            //System.out.println("Finish getting arguments");
        }
    }

    /*oh toString return the bytecode name */
    public String toString() {
        return CodeName + " " + arg;
    }
    
    
    public void execute(VirtualMachine t) {
        debugVirtualMachine debug_t = (debugVirtualMachine)t;
        //int value = Integer.parseInt(arg1);
        int value = -1;
        debug_t.enter(arg, value);

        String displayCode = CodeName + " " + arg +" "+ arg1;
        t.printBytecode(displayCode);
    }
}
