/*
 * STORE n <id> - pop the top of the stack ; store value into the offset n from 
 * the start of the frame; <id> is used as a comment , it's the variable name where 
 * the daa is stored
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class StoreCode extends ByteCode{
        protected String CodeName = "STORE";
        protected String arg;
        protected String arg1;
        //private int addrs;
        public StoreCode(){
                arg = "";
                arg1 = "";
        }
        public void init(String aArgs,int symAddrs ){
                    int isArg = 0; 
                    int count = 0;
                    char ch = aArgs.charAt(count);
                  try{
                    while (Character.isDefined(ch) && count < aArgs.length() && isArg == 0) {  // scan past whitespace
                        
                        //count++;
                       // ch = aArgs.charAt(count);
                        
                        //while (!Character.isDefined(ch)&& count < aArgs.length()&& isArg == 0){    //if next ch is a char 
                            arg += ch;                               //we add it to the string
                            count++;
                            ch = aArgs.charAt(count);  
                            if(Character.isWhitespace(ch)){
                                isArg = 1;
                                }
                       
                    }
                    isArg = 0;
                    while (Character.isWhitespace(ch) && count < aArgs.length() && isArg == 0) {  // scan past whitespace
                        
                        count++;
                        ch = aArgs.charAt(count);
                        
                        while (Character.isDefined(ch)&& count < aArgs.length()&& isArg == 0){    //if next ch is a char 
                            arg1 += ch;                               //we add it to the string
                            count++;
                            ch = aArgs.charAt(count);  
                           // if(Character.isWhitespace(ch))
                               // isArg = 1;
                        }
                       
                    }
                  }catch(Exception e){
                      //System.out.println("Finish getting arguments");
                  }

               //no symbol address
        }
        
        public String toString(){
                return CodeName + " " +arg + " "+arg1;
        }
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        } */
        public void execute(VirtualMachine t){
                //System.out.println(CodeName +" " + arg+" "+arg1);
                int i = Integer.parseInt(arg);
               // i = t.popRunStack();
            
                t.storeRunStack(i);
                String displayCode = CodeName +" " +arg +" "+arg1+"  "+arg1+" = ";
                t.storeVal(displayCode);
        }    
}
