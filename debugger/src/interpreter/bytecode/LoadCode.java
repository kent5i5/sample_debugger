/*
 * LOAD n <id>;push the value in the slot which is offset n 
 * from the start of the frameonto the top of the stack; 
 * <id> is used as a comment , 
 * it's the variable name from which the dtat is loaded
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class LoadCode extends ByteCode{
        private String CodeName = "LOAD";
        private String arg;
        private String arg1;
        //private long addrs;
        public LoadCode(){
                //initialize arg and arg1 to return null
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
                return CodeName + " " +arg + " "+ arg1;
        }
        
      /*  public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        }*/
        
        public void execute(VirtualMachine t){
                //System.out.println(CodeName +" " + arg+" "+arg1  );
                String i = arg;
                int j = Integer.parseInt(i) ;
                t.loadRunstack(j);  
                
                String displayCode = CodeName  +" "+arg + " "+ arg1+"   <load "+arg1+">";
                t.printBytecode(displayCode);
        }    
}
