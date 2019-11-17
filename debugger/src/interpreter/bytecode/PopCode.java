/*
 * POPn :pop top n levels of runtime stack
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class PopCode extends ByteCode {
        protected String CodeName = "POP";
        protected String args;
        //private long addrs;
        public PopCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        
        public void init(String aArgs,int symAddrs ){
               args = aArgs; 
               //no symbol address
        }
        
        public String toString(){
                return CodeName + " " +args;
        }
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        } */
        public void execute(VirtualMachine t){
            
                //System.out.println("PopCode Executing");
                int n = Integer.parseInt(args);
                while(n >0){
                    t.popRunStack();  
                    n -= 1;
                }
                String displayCode = CodeName +" " +args;
                t.printBytecode(displayCode);
        }    
}
