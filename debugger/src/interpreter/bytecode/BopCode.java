/*
 * pop top 2 levels of the stack and perform indicated operation - operation
 * are + -/* == != < =>=<|& 
 * | and & are logical operations , not bit operatiors lower level is the first
 * operand
 *  <second level> + <top level> 
 */
package interpreter.bytecode;

import interpreter.ByteCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**

 * @author ken
 */
public class BopCode extends ByteCode{
        private String CodeName = "BOP";
        private String args;
        //private int addrs;
        public BopCode(){
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
                //System.out.println(CodeName +" "+args );
                
                int rhs = t.popRunStack();
                int lhs = t.popRunStack();
               // char op = args.char(0);
                if (args.equals("+")) {lhs += rhs; t.pushRunStack(lhs); }
                if (args.equals("-")) {lhs -= rhs; t.pushRunStack(lhs); }
                if (args.equals("/")) {lhs = lhs/rhs; t.pushRunStack(lhs); } 
                if (args.equals("*")) {lhs = lhs*rhs; t.pushRunStack(lhs);}
                if (args.equals("==")){
                    if (lhs == rhs){
                        t.pushRunStack(1);
                    }else{
                        t.pushRunStack(0);
                    }
                }
                if (args.equals("!=")){
                    if (lhs != rhs){
                        t.pushRunStack(1);
                    }else{
                        t.pushRunStack(0);
                    }
                }
                if (args.equals("<=")){ int ans = (lhs<=rhs? 1:0); t.pushRunStack(ans);}
                if (args.equals(">")) { int ans = (lhs>rhs? 1:0); t.pushRunStack(ans);}
                if (args.equals(">=")){ int ans = (lhs>=rhs? 1:0); t.pushRunStack(ans);}
                if (args.equals("<")){ int ans = (lhs<rhs? 1:0); t.pushRunStack(ans);}
                //if (args.equals("|"));
                //if (args.equals("&"));
                String displayCode = CodeName +" " +args;
                t.printBytecode(displayCode);
        }  
}
