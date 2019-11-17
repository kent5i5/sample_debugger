/*
 * check the args and decide whether to Dump the information of the bytecode or not 
 * Dump on is an interpreter command to turn on runtime dumping . 
 * This will set an interpreter switch that will cause dumping After execution of each bytecode
 * Dump off will reste the switch to end dumping
 * and the Dump instructions will not be printing
 * the String variable signal ,gotten from input file, will indicate what the signal is.
 */
package interpreter.bytecode;
import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class DumpCode extends ByteCode{
        private String CodeName = "DUMP";
        private String args;
        //private String previousInstru;
        
        //switch on and off
        public DumpCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        public void init(String aArgs,int symAddrs ){
                 args = aArgs; 
               //no symbol address
        }
        public String toString(){
                return  CodeName + " " +args; 
        }
        public void execute(VirtualMachine t){
               // System.out.println("DumpCode Executing");
               //do nothing
        }
        //public void toString  no need for this I guess
        
}
