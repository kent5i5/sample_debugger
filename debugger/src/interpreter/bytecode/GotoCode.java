/*
 * GOTO <label>
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class GotoCode extends ByteCode{
        private String CodeName = "GOTO";
        private String args;
        private int addrs;
        public GotoCode(){
                //int i=0 ;
                //args = v.get(i);
            
        }
        public void init(String aArgs,int symAddrs ){
                args = aArgs; 
               // symbol address 
                addrs = symAddrs; 
        }
        
        public String toString(){
                return CodeName + " " +args;
        }
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }*/
        public void execute(VirtualMachine t){
                //System.out.println(CodeName +" " + args);
                t.jal(addrs);  
                String displayCode = CodeName +" " +args;
                t.printBytecode(displayCode);
        }    
}
