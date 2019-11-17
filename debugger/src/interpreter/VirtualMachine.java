/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Stack;

/**
 *
 * @author ken
 */
public class VirtualMachine {

    RunTimeStack runStack;
    private int pc;                  //the program counter
    private Stack returnAddrs;       //push/pop when call/return functions
    private boolean isRunning;       //true while the VM is running
    private Program program;         //bytecoe program
    int dumpSignal =0;
    int numArgs;
    public VirtualMachine() {
    }

    public VirtualMachine(Program p) {
        program = p;
        returnAddrs = new Stack();
        numArgs = 0;
       //isRunning = true;
    }
    /*pass the call to the runStack for ByteCode to execute
    public void dumpRunStack(){  
    runStack.dump();
    }*/

    public int peekRunStack() {
        return runStack.peek();
    }

    public int popRunStack() {
        return runStack.pop();
    }

    public int pushRunStack(int pc) {
        return runStack.push(pc);
    }

    public void newFrameAtRunStack(int offset) {
        runStack.newFrameAt(offset);
    }

    public void popFrameRunStack() {
        runStack.popFrame();
    }

    public int storeRunStack(int offset) {
        return runStack.store(offset);
    }

    public int loadRunstack(int offset) {
        return runStack.load(offset);
    }

    public Integer push(Integer i) {
        return runStack.push(i);
    }//end of runStack calls

    public void executeProgram() {
        pc = 0;
        runStack = new RunTimeStack();
        isRunning = true;
        
        while (isRunning) {
            
            ByteCode code = program.getCode(pc);
            //if(code.toString().equals("call")|| code.toString().equals("return"))
            code.execute(this);
            
            /* check if the dump signal is on, if yes, 
             * printing bytecodes and dump the runStack follow by that*/
            if (code.toString().contentEquals("DUMP on") || code.toString().contentEquals("DUMP ON")) {
                //System.out.print("DUMP is WORKING");
                dumpSignal =1 ;//check that the operation is correct
            }else if (code.toString().contentEquals("DUMP OFF")||code.toString().contentEquals("DUMP OFF")){
                dumpSignal =0;
            }
            
            if (code.toString().regionMatches(0, "ARGS",0, 3)){
                String Line = code.toString();
                int count = 0;
                String temp = "";
                char ch = Line.charAt(5);
                //scan through the ARGS to get the number of args for the function call
                while ( count < Line.length()){
                    count++;
                    if (count < Line.length())
                        ch = Line.charAt(count);
                    
                    if (Character.isDigit(ch)&& count < Line.length()){
                        temp += ch;
                        count++; 
                        if (count < Line.length())
                            ch = Line.charAt(count);
                    }
                     
                }
                //temp += ch;
                numArgs =Integer.parseInt(temp);
            }
            
            if ( !code.toString().contentEquals("DUMP ON")||code.toString().contentEquals("DUMP on")){
                if (dumpSignal == 1 )
                    runStack.dump(); 
            }
            /**/
            //System.out.print(code.);
            pc++;
        }
    }

    
    /*methods to change the variables of the VM, so
     * bytecode is able to request the VM to change the varibles
     */
    public void stopRunning(){
            isRunning = false ;
    }
    public void callFrame(String d){
            if(dumpSignal== 1){
                int i = numArgs;
                System.out.print(d);
                int[] funcArgs = new int[50];
                while (i>0){                  
                    funcArgs[i] = runStack.pop();  //pop to get the args on top of runStack 
                    runStack.push(funcArgs[i]);        //then push it back
                    i--;
                }
                i =1; 
                while(i<=numArgs){
                    
                    System.out.print(funcArgs[i]);
                    if(i != numArgs)
                         System.out.print(",");
                    
                    i++;
                }
                System.out.println(")");
            }
            //System.out.print("pc "+pc);           
            returnAddrs.push(pc);
            
    }
    
    public void returnFrame(int addrs, String d){
            pc = (Integer)returnAddrs.pop();
            d += ": " + runStack.peek();
            if(dumpSignal== 1)
                System.out.println(d);
            
    }
    public void storeVal(String d){
       
            d += runStack.peek();
            if(dumpSignal== 1)
                System.out.println(d);
            
    }
    //change the pc responsed to the call function symbol address
    public void jal(int symAddr){
            
            pc = symAddr;
            //returnAddrs.push(pc);
    }
    public void printBytecode(String displaycode){
            if(dumpSignal== 1)
                System.out.println(displaycode);
    }
}
