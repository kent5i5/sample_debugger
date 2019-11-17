/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class FalseBranchCode extends ByteCode {
        private String CodeName = "FALSEBRANCH";
        static String args;
        private int addrs;
        public FalseBranchCode(){
                //int i=0 ;
                args = "";
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
                if(t.popRunStack()== 0){
                    t.jal(addrs);
                }
                String displayCode = CodeName +" " +args;
                t.printBytecode(displayCode);
                
        }
}
