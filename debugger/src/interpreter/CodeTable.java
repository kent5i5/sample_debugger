/*
 * ByteCodeLoader class will load the bytecodes from the file into the Virtual Machine
 * When the bytecodes are loaded we'll get the string for the bytecode and then we'll get 
 * [the bytecode class for that string form the 
 * Codetable to construct an instance of the bytecode;] e.g.
 * if the bytecode loader reads the line: <br>
 * HALT <br>
 * we'll build an instance of the class HaltCode and store that instance in the 
 * Program object
 */
package interpreter;

/**
 *
 * @author ken
 */
public class CodeTable {
        public static java.util.HashMap<String,String> byteCodeName = new java.util.HashMap<String,String>();
        /*init each entry with the name of the corresponding class for the code
          */
        public static void init(){
                byteCodeName.put("HALT","HaltCode");
                byteCodeName.put("POP","PopCode");  // depends on how many popped
                byteCodeName.put("FALSEBRANCH","FalseBranchCode");     // pop conditional expr
                byteCodeName.put("GOTO","GotoCode");
                byteCodeName.put("STORE","StoreCode");           // pop value
                byteCodeName.put("LOAD","LoadCode");             // load new value
                byteCodeName.put("LIT","LitCode");              // load literal value
                byteCodeName.put("ARGS","ArgsCode");             // actual args
                byteCodeName.put("CALL","CallCode");             // result of fct call is pushed
                byteCodeName.put("RETURN","ReturnCode");          // pop return value
                byteCodeName.put("BOP","BopCode");             // replace values with 
                                       // second level op top level
                byteCodeName.put("READ","ReadCode");             // read in new value
                byteCodeName.put("WRITE","WriteCode");            // write value; leave on top
                byteCodeName.put("LABEL","LabelCode");            // branch label
                byteCodeName.put("DUMP", "DumpCode");             //dump label
                /*new debugger bytecodes*/
                byteCodeName.put("CALL d", "debuggerByteCodes.CallDebugCode");
                byteCodeName.put("FORMAL", "debuggerByteCodes.FormalDebugCode");
                byteCodeName.put("FUNCTION", "debuggerByteCodes.FunctionDebugCode");
                byteCodeName.put("LINE", "debuggerByteCodes.LineDebugCode");
                byteCodeName.put("LIT d", "debuggerByteCodes.LitDebugCode");
                byteCodeName.put("POP d", "debuggerByteCodes.PopDebugCode");
                byteCodeName.put("RETURN d", "debuggerByteCodes.ReturnDebugCode");
        }
        /* */
        public static String get(String code){ 
            //System.out.print(byteCodeName.get(code));
                code = byteCodeName.get(code);
                
                return code;
        }
}
