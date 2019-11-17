/*
 * Call <funcname> -fransfer control to the indicated function
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**

 * @author ken
 */
public class CallCode extends ByteCode{
        protected String CodeName = "CALL";
        protected String args;
        protected int addrs;
        public CallCode(){
                //int i=0 ;
               args = "";
        }
        public void init(String aArgs,int symAddrs ){
                int count = 0;
                    char ch = aArgs.charAt(count);
                  try{
                    while (Character.isDefined(ch) && count < aArgs.length() ) {  // scan past whitespace
                        if(Character.isJavaIdentifierPart(ch)||Character.isDigit(ch)) {  
                            args += ch;                              
                            
                             
                            
                             
                       }
                       count++;
                       ch = aArgs.charAt(count); 
                    }
                    
                  }catch(Exception e){
                      //System.out.println("Finish getting arguments");
                  }
               // symbol address 
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
                int p = (Integer)t.peekRunStack();
                String displayCode = CodeName +" " +args +"  "+args +"(";
                t.callFrame(displayCode);
                //int newFrameAt = t.peekRunStack();
                //System.out.println("call last element" +  newFrameAt);
                //t.newFrameAtRunStack(newFrameAt);
                if (addrs != 0){
                    //int a = Integer.parseInt(args);
                    t.jal(addrs);
                }
                t.jal(addrs);
                
               // t.printBytecode(displayCode);
        }    
}
