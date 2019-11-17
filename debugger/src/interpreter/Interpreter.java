package interpreter;

import interpreter.debugger.UI;
import interpreter.debugger.debugVirtualMachine;
import java.io.*;
import java.util.ArrayList;


/**
 * <pre>
 * 
 *  
 *   
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 *     
 *   
 *  
 * </pre>
 */
public class Interpreter {
    private BufferedReader source;
    private String newLine;
    private ArrayList<Entries> entries;          //contains the source program line i and a breakpoint set for this line
   //private FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord(); //contain informatin for each frame
    private ByteCodeLoader bcl;

    public Interpreter(String codeFile) {
        try {
            CodeTable.init();
            bcl = new ByteCodeLoader(codeFile);

        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }
    
    public Interpreter(String d, String codeFile)throws IOException {
        entries = new ArrayList<Entries>();
        String programFile = codeFile + ".x.cod";
        //System.out.println(programFile);
        source = new BufferedReader(new FileReader(codeFile));
        /*store the source program*/
        try {

            char ch;
            newLine = source.readLine();
            while (newLine != null) {
                //System.out.println(newLine  );
                
                if (newLine == null) {  // hit eof 
                    
                    break;
                }
                if (newLine.length() == 0) {
                    
                     break;
                }
                Entries e = new Entries(newLine, false);   
                entries.add(e);
                newLine = source.readLine();
                //System.out.println(entries.size());
            }
        }catch (Exception e) {
            System.out.println("bad");
        }
    
        try {
            CodeTable.init();
            bcl = new ByteCodeLoader(d, programFile);

        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }
    
    void run() {
        Program program = bcl.loadCodes();
        
        //System.out.println("inter " + program.getCode(0).toString());
        VirtualMachine vm = new VirtualMachine(program);
        vm.executeProgram();
    }
    
    //Declare the debugger vm and Run the debug UI
    void runDebug(){
        Program program = bcl.loadCodes();
        
        //System.out.println("inter " + program.getCode(0).toString());
        debugVirtualMachine vm = new debugVirtualMachine(entries,program);
        //vm.executeProgram();
        UI ui= new UI(vm, entries);
        ui.run();
    }

    public static void main(String args[]) {
        
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
        //System.out.println(args[0] + " "+ args[1]);
        if (args[0].equals("-d")){
            try {
                (new Interpreter("-d",args[1])).runDebug();
            }catch(IOException e){}
        }else
            (new Interpreter(args[0])).run();
    }
}