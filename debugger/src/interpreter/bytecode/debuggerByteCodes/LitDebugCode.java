/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecode.LitCode;
import interpreter.debugger.debugVirtualMachine;

/**
 *
 * @author ken
 */
public class LitDebugCode extends LitCode {

   // private String CodeName = "LITDEBUG";
    //private String arg;
    //private String arg1;
    //private long addrs;
/*
    public LitDebugCode() {
        arg = "";
        arg1 = "";
    }

    public void init(String aArgs, int symAddrs) {
        int isArg = 0;
        int count = 0;
        char ch = aArgs.charAt(count);
        try {
            while (Character.isDefined(ch) && count < aArgs.length() && isArg == 0) {  // scan past whitespace

                //count++;
                // ch = aArgs.charAt(count);

                //while (!Character.isDefined(ch)&& count < aArgs.length()&& isArg == 0){    //if next ch is a char 
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

        //no symbol address
    }*/

    public String toString() {
        return CodeName + " " + arg + " " + arg1;
    }
    

    public void execute(VirtualMachine t) {
        debugVirtualMachine debug_t = (debugVirtualMachine)t;
        //int value = Integer.parseInt(arg);
        int offset = -1;

        
        //System.out.println(CodeName +" " + arg +" "+  arg1);
        String i = arg;

        // if (arg == 1){
        int n = Integer.parseInt(i);
        t.pushRunStack(n);
        
        
         if (!arg1.equals("")){
            debug_t.enter(arg1, offset);
            //System.out.println("true");
         }        
        String displayCode;
        if (arg1 != "") {
            displayCode = CodeName + " " + arg + " " + arg1 + "   int" + arg1;

        } else {
            displayCode = CodeName + " " + arg + " " + arg1;
        }
        t.printBytecode(displayCode);
    }
}
