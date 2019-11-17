/*
 * this instruction is immediately followed by the CALL instruction;
 * the function has n args so ARGS n instructs the interpreter
 * to set up a new frame n down from the top , so it will include the arguments
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * n=#args

 * @author ken
 */
public class ArgsCode extends ByteCode{
        private String CodeName = "ARGS";
        private String args;
        //private int addrs;
        public ArgsCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        public void init(String aArgs,int symAddrs ){               
               args = aArgs; 
               //no symbol address
        }
        
        /*oh toString return the bytecode name */
        public String toString(){
                return CodeName + " " +args;
        }
     /*   public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }
        public String getArgs(){
                return args;
        }
      */  
        public void execute(VirtualMachine t){
                //System.out.println(CodeName +" " +args);
                
                int offset = Integer.parseInt(args);
                t.newFrameAtRunStack(offset); 
                String displayCode = CodeName +" " +args;
                t.printBytecode(displayCode);
        }
}
