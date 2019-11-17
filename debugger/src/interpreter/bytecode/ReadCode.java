/*
 * READ; read an integer ;prompt the user for input ;
 * put the value just read on top of the stack
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
public class ReadCode extends ByteCode{
        private String CodeName = "READ";
        //private String args;
        //private int addrs;
        public ReadCode(){
                //int i=0 ;
                //args = v.get(i);
        }
        public void init(String aArgs,int symAddrs ){
               //no args  
        }
        
        public String toString(){
                return CodeName ;
        }
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }*/
        public void execute(VirtualMachine t){
                //System.out.println(CodeName );
                System.out.println("Enter a integer value ");
                
                try{
                    Scanner in = new Scanner(System.in);
                    int aInt = in.nextInt();
                    t.pushRunStack(aInt);
                }catch(Exception e){
                    t.stopRunning();
                    System.out.println("bad input");
                }
                System.out.println("********************************************");
               // t.jal(addrs);
                String displayCode = CodeName;
                t.printBytecode(displayCode);
        }    
}
