/*
 * RETURN <funcname> ; return from the current function<function>
 * is used as a comment to indicate the current function
 * RETURN is generated for intrinsic functions
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class ReturnCode extends ByteCode{
        protected String CodeName = "RETURN";
        protected String args;
        protected int addrs;
        public ReturnCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        public void init(String aArgs,int symAddrs ){
               args = aArgs; 
               //symbol address
                addrs = symAddrs;
        }
        
        public String toString(){
                return CodeName + " " +args;
        }
       /* public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }*/
        public void execute(VirtualMachine t){
                //System.out.println(CodeName +" " + args);
                //t.popRunStack();
              if (args == null){
                
                t.popFrameRunStack();
                String displayCode = CodeName +" " +args + "  exit "+args;
                t.returnFrame(0,displayCode); 
              }else {
                
                t.popFrameRunStack();
                String displayCode = CodeName +" " +args + "  exit "+args;
                t.returnFrame(addrs,displayCode);   
              }
                //t.printBytecode(displayCode);
           
        }    
}
