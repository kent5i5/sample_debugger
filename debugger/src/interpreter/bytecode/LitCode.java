/*
 * LIT n - load the literal value n
 * LIT 0 i - this form of the Lit was generated to load 0 on 
 * the stack in order to initialize the variable i to 0
 * reserve space on the runtime stack for i
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class LitCode extends ByteCode{
        protected String CodeName = "LIT";
        protected String arg;
        protected String arg1;
        //private long addrs;
        public LitCode(){
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
                return CodeName + " " +arg +" " +arg1;
        }
      /* public String getArgs(){
                return args;
        }
        public void storeAddrs(int symbolAddrs){
                addrs = symbolAddrs;
        } */
        public void execute(VirtualMachine t){
            
            //System.out.println(CodeName +" " + arg +" "+  arg1);
            String i =arg;
            
           // if (arg == 1){
                int n = Integer.parseInt(i);
                t.pushRunStack(n);
           // }
          /*  } else if(args.size() > 2){
                    int n = 0;
                   // n = Integer.parseInt(args.get(0));
                    t.pushRunStack(n);
                    
            } */ 
            String displayCode;
            if (arg1 != ""){
                displayCode = CodeName +" "+arg + " "+ arg1+"   int" +arg1;
               
            }else{
                displayCode = CodeName  +" "+arg + " "+ arg1;
            }
            t.printBytecode(displayCode);
       }
}
