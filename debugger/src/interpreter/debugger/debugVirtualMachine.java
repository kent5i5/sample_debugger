package interpreter.debugger;

import interpreter.Entries;
import interpreter.ByteCode;
import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import java.util.ArrayList;
import java.util.Stack;

/* The new virtual machine executes the following commands
 * help, set/clear breakpoints,  display the current function
 * continue execution, quit execution, display variable(s)
 *
 * @author ken
 */
public class debugVirtualMachine extends VirtualMachine {

    private RunTimeStack runStack;
    private ArrayList<Entries> sourceCode;
    private FunctionEnvironmentRecord envRecord;
    private Stack<FunctionEnvironmentRecord> EnvStack;
    private int pc;                  //the program counter
    private Stack returnAddrs;       //push/pop when call/return functions
    private boolean isRunning = true;       //true while the VM is running
    private Program program;         //bytecoe program
    
    //indicators of when to break 
    private int dumpSignal = 0;
    private int numArgs;
    private boolean StpoFlag = false;           //step out flag
    private boolean StinFlag = false;           // step in flag
    private boolean StorFlag = false;           //step over flag
    private int envSize = 1;                     //remember the size of the EnvStack before a function call
    private boolean isTraceOn = false;
    private int continueSignal;              //for skipping the code with the same line after a breakptr
    private boolean storFlag2 = false;
    private int skipcount = 0;                    //Hit a call , I read the sequences of codes
    //back up boxes
    private boolean RollbackFlag = false;
    private Stack<RunTimeStack> variableStack;    //A stack to remember the runStack
    private RunTimeStack runStack_B;               //a copy of the runStack for each function

    public debugVirtualMachine(ArrayList<Entries> sourceEntries, Program p) {
        
        sourceCode = new ArrayList<Entries>();
        sourceCode = (ArrayList<Entries>) sourceEntries.clone();

        envRecord = new FunctionEnvironmentRecord();
        envRecord.beginScope();   //initial the environment stack

        EnvStack = new Stack<FunctionEnvironmentRecord>();
        EnvStack.add(envRecord);
        program = p;
        returnAddrs = new Stack();
        numArgs = 0;
        continueSignal = 0;
        runStack = new RunTimeStack();

        variableStack = new Stack();
        
    }

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

    public void execute() {
        //pc = 0; 
        /*display the source code*/

        String debugAction;
       
        while (isRunning) {

            //get the bytecode from the program and run its execute
            ByteCode code = program.getCode(pc);           
            code.execute(this);
            
            /* check if the dump signal is on, if yes, 
             * printing bytecodes and dump the runStack follow by that*/
            if (code.toString().contentEquals("DUMP on") || code.toString().contentEquals("DUMP ON")) {
                
                dumpSignal = 1;//check that the operation is correct
            } else if (code.toString().contentEquals("DUMP OFF") || code.toString().contentEquals("DUMP OFF")) {
                dumpSignal = 0;
            }

            if (code.toString().regionMatches(0, "ARGS", 0, 3)) {
                String Line = code.toString();
                int count = 0;
                String temp = "";
                char ch = Line.charAt(5);
                //scan through the ARGS to get the number of args for the function call
                while (count < Line.length()) {
                    count++;
                    if (count < Line.length()) {
                        ch = Line.charAt(count);
                    }

                    if (Character.isDigit(ch) && count < Line.length()) {
                        temp += ch;
                        count++;
                        if (count < Line.length()) {
                            ch = Line.charAt(count);
                        }
                    }

                }
                numArgs = Integer.parseInt(temp);
            }

            if (!code.toString().contentEquals("DUMP ON") || code.toString().contentEquals("DUMP on")) {
                if (dumpSignal == 1) {
                    runStack.dump();
                    
                }
            }
            try{
                if (code.toString().contains("FUNCTION")
                    && EnvStack.peek().getCurrentLine() == -1
                    && StinFlag == true) {
                    StpoFlag = false;           //indicate when to break
                    StinFlag = false;
                    StorFlag = false;
                    System.out.println("\n\n\t*********READ*********\n\n");
                    System.out.println("********************************************");
                    pc++;
                    break;
                }
            }catch(Exception e){
                //skip the null function name}
            }
            if (code.toString().contentEquals("WRITE")) {
                System.out.println("!!!THIS VALUE IS WRITEN INTO MEMORY!!!");
            }

            if (code.toString().contains("CALL")) {
                skipcount = 3;
            } else if (code.toString().contains("FUNCTION")) {
                skipcount = 1;
            }
            /*reset the signal when it sees a LINE bytecode*/
            
            if (continueSignal == -1) {
                continueSignal = -1;
            } else {
                continueSignal = 1;
            }

            if (EnvStack.peek().getCurrentLine() > 0 && sourceCode.get(EnvStack.peek().getCurrentLine()-1).getIsBreakptSet() == false) {
                continueSignal = 0;
            }

            //int k;
            //for (k=0; k < EnvStack.size(); k++)
              //System.out.println(EnvStack.get(k).keys()+" "+EnvStack.get(k).toString());
             
            pc++;
            
            if (continueSignal != -1 && EnvStack.peek().getCurrentLine() > 0
                    && sourceCode.get(EnvStack.peek().getCurrentLine()-1).getIsBreakptSet() == true) {


                StpoFlag = false;           //indicate when to break
                StinFlag = false;
                StorFlag = false;
                if (code.toString().contains("LINE") || code.toString().contains("FUNCTION")) {
                    System.out.println("");
                } else {
                    //System.out.println("BREAKPOINT!!!");
                    continueSignal = -1;
                    break;
                }

            }

            /*check the flags*/
            if (StpoFlag == true) {

                //System.out.println("envSize is "+envSize + " " +", EnvStack is "+ EnvStack.size());
                if (EnvStack.size() < envSize) {
                    StpoFlag = false;
                    //System.out.println("size of EnvStack < envSize , step out of curretn function");
                    break;
                }
            }
            if (StinFlag == true && !code.toString().contains("LINE -1")) {
                            
                if (skipcount > 0) {
                    skipcount--; //do nothing   || code.toString().contains("FUNCTION")
                } else if (code.toString().contains("LINE")     
                        || code.toString().contains("RETURN")) {
                    StinFlag = false;
                    break;
                }
            }
            if (StorFlag == true) {

                if (EnvStack.size() < envSize) {
                    
                    StorFlag = false;
                    
                    System.out.println("size of EnvStack < envSize , step out of curretn function ");
                    break;
                }

                if (EnvStack.size() == envSize && code.toString().contains("LINE")) {

                    StorFlag = false;
                    break;
                }
            }
            if (RollbackFlag == true){
                
                if (skipcount > 0 ){
                    skipcount--;
                }else {
                    RollbackFlag = false;
                    System.out.println("reset current function");
                    break;
                }
            }

            if (isTraceOn = true) {
            }
        }
        System.out.println("BREAK!");
        //return this;
    }

    /* UI methods */
    
    public void recordStates() {
        int j = 0;
        runStack_B = new RunTimeStack();
        for (int i = runStack.size()-1; i >= 0; i--) {

            runStack_B.push(runStack.getValue(i));
            
            j++;
        }

        
        variableStack.push(runStack_B);
        
        
    }
    public int changeVariables(String s, int newVal){
        int offset;
        int retVal;
        if(EnvStack.peek().getCurrentLine() > 0) 
            offset = (Integer)EnvStack.peek().get(s); //get the offset
        else 
            offset = (Integer)EnvStack.get(EnvStack.size()-2).get(s);
        
        return runStack.set(offset,newVal);
    }
    public void returnStates(){
        variableStack.pop();
    }
    public void rollBack() {
        try{
       
            if (returnAddrs.size() != 0) {
                pc = (Integer) returnAddrs.pop(); //return the function call
            } else {
                pc = 0;
            }
            //clear the runStack
            for (int i = runStack.size()-1; i >= 0; i--) {

                runStack.pop();
            }
           
            //variableStack.peek() is variable_B which is the record of runStack for each function
            for (int i = variableStack.peek().size()-1; i >= 0; i--) {

                runStack.push(variableStack.peek().pop());
            }

            //pop the current environment record
            if (EnvStack.size()==1){
                int cl = EnvStack.peek().getRecordSize();
                EnvStack.peek().endScope(cl);
            }else 
                EndScope();
            
            RollbackFlag = true;
            skipcount = 2;
            //reset the flags
            //StpoFlag = false;           
            //StinFlag = false;
            //StorFlag = false;
            //envSize = 1;                     
            //isTraceOn = false;
            //continueSignal = 0;            
            //storFlag2 = false;
            //skipcount = 0;
        
        }catch(Exception e){;}
        //bcak to UI

    }

    public void setOutFlag() {

        envSize = EnvStack.size();                                        //save the size of EnvStack
        StpoFlag = true;
    }

    public void setInFlag() {
        envSize = EnvStack.size();
        StinFlag = true;
    }

    public void setOverFlag() {

        envSize = EnvStack.size();
        StorFlag = true;
    }

    public void setBreakpoint(int input) {

        sourceCode.get(input).setIsBreakptSet(true);
    }

    public void clearBreakpoint(int input) {

        sourceCode.get(input).setIsBreakptSet(false);
    }

    public String getFunctionName() {
        String f = EnvStack.peek().getFuncName();
        return f;
    }
    
    // a method to get the value from the runStack
    public void getValue() {
        String toKey = "" + EnvStack.peek().keys();
        if (toKey.isEmpty()){
            System.out.println("no variables yet");
        }else{
            System.out.println("^^^variables^^^");
        }
        int count = 0;
        char ch;
        ch = toKey.charAt(count);
        String key = "";
        try {
            while (Character.isWhitespace(ch) || count < toKey.length()) {  // scan past whitespace

                count++;
                ch = toKey.charAt(count);

                while (Character.isJavaIdentifierPart(ch) && count < toKey.length() && ch != ',') {    //if next ch is a char 
                    key = ch + "";                               //we add it to the string
                    System.out.println(key + " = " + runStack.getValue(Integer.parseInt(EnvStack.peek().getValue(key))));
                    count++;
                    ch = toKey.charAt(count);
                    
                }

            }
        } catch (Exception e) {
            
            //atEOF = true;
        }
     
    }

    public int getStartLine() {
        int i = EnvStack.peek().getSartLine();
        return i;
    }

    public int getCurrentLine() {
        int c = EnvStack.peek().getCurrentLine();
        return c;
    }

    public int getEndLine() {
        int e = EnvStack.peek().getEndLine();
        return e;
    }

    public boolean getIsRunning() {
        return isRunning;
    }
    /*Debug code methods changes the functionenvironment record*/

    public void BeginScope() {
        envRecord = new FunctionEnvironmentRecord();
        EnvStack.push(envRecord);
        EnvStack.peek().beginScope();
    }

    public void functionCall(String a, int a1, int a2) {
        EnvStack.peek().setFuncName(a);

        EnvStack.peek().setStartLine(a1);
        EnvStack.peek().setEndLine(a2);
    }

    public void enter(String a, int a1) {
        String symbo = a + "/" + a1;
        if (a1 < 0) {
            a1 = runStack.size() - 1;
        }  
        
        EnvStack.peek().put(a, a1);
    }

    public void popEnv(String a) {
        int popNum = Integer.parseInt(a);
        EnvStack.peek().endScope(popNum);
    }

    public void currentLine(String a) {
        int a1 = Integer.parseInt(a);
        EnvStack.peek().setCurrentLine(a1);
    }

    public void EndScope() {
        int popNum = EnvStack.peek().getRecordSize();   //get the num of keys/variables in the environment record
        EnvStack.peek().endScope(popNum);
        EnvStack.pop();
    }

    /* 
     * the methods to change the variables of the VM, so
     * bytecode is able to request the VM to change the varibles
     */
    public void stopRunning() {
        isRunning = false;
        System.out.println("****HALT is reached****");
        pc = 0;
        
    }

    public void callFrame(String d) {
        if (d.contentEquals("CALL Read")) {
            System.out.println("*********CALL READ FUNCTION*********");
        }

        if (dumpSignal == 1) {
            int i = numArgs;
            System.out.print(d);
            int[] funcArgs = new int[50];
            while (i > 0) {
                funcArgs[i] = runStack.pop();  //pop to get the args on top of runStack 
                runStack.push(funcArgs[i]);        //then push it back
                i--;
            }
            i = 1;
            while (i <= numArgs) {

                System.out.print(funcArgs[i]);
                if (i != numArgs) {
                    System.out.print(",");
                }

                i++;
            }
            System.out.println(")");
        }           
        returnAddrs.push(pc);

    }

    public void returnFrame(int addrs, String d) {
        pc = (Integer) returnAddrs.pop();
        d += ": " + runStack.peek();
        if (dumpSignal == 1) {
            System.out.println(d);
        }

    }
    
    /*old bytecodes method*/
    public void storeVal(String d) {

        d += runStack.peek();
        if (dumpSignal == 1) {
            System.out.println(d);
        }

    }
    
    //change the pc responsed to the call function symbol address
    public void jal(int symAddr) {

        pc = symAddr;
    }

    public void printBytecode(String displaycode) {
        if (dumpSignal == 1) {
            System.out.println(displaycode);
        }
    }
}
