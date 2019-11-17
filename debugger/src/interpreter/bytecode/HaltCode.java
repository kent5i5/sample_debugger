/*
 * halt execution
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class HaltCode extends ByteCode {
        private String CodeName = "HALT";
        public HaltCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        
        //private String args;
        //private long addrs;
        public void init( String arrayOfArgs,int symAddrs ){
               //no arguments 
        }
        
        public String toString(){
                return CodeName ;
        }
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        } */
        public void execute(VirtualMachine t){
                //System.out.println(CodeName);
                t.popFrameRunStack();
                t.stopRunning();
                String displayCode = CodeName;
                t.printBytecode(displayCode);
        }    
}
