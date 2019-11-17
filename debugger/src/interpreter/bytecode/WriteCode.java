/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class WriteCode extends ByteCode{
        private String CodeName = "WRITE";
        //private Vector<String> args;
        //private int addrs;
        public WriteCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        public void init(String aArgs,int symAddrs ){
               //int i=0 ;
               //args = v; 
        }
        
        public String toString(){
                return CodeName;
        }
       /* public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }*/
        public void execute(VirtualMachine t){
                //System.out.println(CodeName );
                
                //t.pushRunStack(in.nextInt());
                int topValue = t.peekRunStack();
                System.out.println(topValue);
                String displayCode = CodeName;
                t.printBytecode(displayCode);
        }    
}
