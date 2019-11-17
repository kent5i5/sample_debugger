package interpreter.debugger;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
//import javax.xml.bind.Binder;

/** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
 */
class Binder {

    private Object value;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same symbol
    // restore this when closing scope

    Binder(Object v, String p, Binder t) {
        value = v;
        prevtop = p;
        tail = t;
    }

    Object getValue() {
        return value;
    }

    String getPrevtop() {
        return prevtop;
    }

    Binder getTail() {
        return tail;
    }
    public String toString(){
        String val = value + "";
        return val;
    }
}

/*
 * the FunctionEnvironmentRecord has a symbol table , function name , starting/ending line
 * and current line variables with getter/setter methods. The symbol table is separated
 * and work independantly, so it should be able to handle a single beginscope , enter symbols/values
 * remove a given number of symbol/value bindings
 */
/**
 * The FunctionEnvironmentRecord consist of the symbol table, function name,
 * starting/ending line number of the function and current line.
 */
public class FunctionEnvironmentRecord {
   
    private java.util.HashMap<String, Binder> symbols = new java.util.HashMap<String, Binder>();
    private String top;          // reference to last symbol added to
    // current scope; this essentially is the
    // start of a linked list of symbols in scope
    private Binder marks;       // scope mark; essentially we have a stack of
    // marks - push for new scope; pop when closing
    // scope
    private String funcName;
    private int startLine, endLine, currentLine;
    //private BufferedReader source;

    public FunctionEnvironmentRecord() {
        
    }
/*
    public static void main(String args[]) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        BufferedReader source = null;
        String Command = "";
        String newLine = null;
        String arg1 = "";
        String arg2 = "";
        String arg3 = "";
        char ch;
        System.out.println(args[0]);
        try {
            source = new BufferedReader(new FileReader(args[0]));
            newLine = source.readLine();
            //System.out.println("line" + newLine);
            //read the command name and arguments form line by line
            while (newLine != null) {
                //System.out.println(newLine  );

                if (newLine == null) {  // hit eof 
                    //throw new IOException();
                    //return program;
                    break;
                }
                if (newLine.length() == 0) {
                    //isPriorEndLine = true;
                    //return program;
                    break;
                }


                int count = 0;
                Command = "";
                arg1 = "";
                arg2 = "";
                arg3 = "";
                //arg2 = "";                
                ch = newLine.charAt(count);
                try {
                    if (Character.isJavaIdentifierStart(ch)) {

                        try {
                            do {

                                count++;
                                //endPosition++;
                                Command += ch;
                                ch = newLine.charAt(count);
                            } while (Character.isJavaIdentifierPart(ch) && !Character.isWhitespace(ch));
                        } catch (Exception e) {
                        }
                    }
                   // System.out.println(Command);
                    //record the branch code's index

                    int isArg = 0;
                    while (Character.isWhitespace(ch) && count < newLine.length() && isArg == 0) {  // scan past whitespace

                        count++;
                        ch = newLine.charAt(count);

                        while (Character.isDefined(ch) && count < newLine.length() && isArg == 0) {    //if next ch is a char 
                            arg1 += ch;                               //we add it to the string
                            count++;
                            ch = newLine.charAt(count);
                            if (Character.isWhitespace(ch)) {
                                isArg = 1;
                            }
                        }

                    }
                    //System.out.println("arg1" + arg1);
                    //argument2
                    isArg = 0;
                    while (Character.isWhitespace(ch) && count < newLine.length() && isArg == 0) {  // scan past whitespace

                        count++;
                        ch = newLine.charAt(count);

                        while (Character.isDefined(ch) && count < newLine.length() && isArg == 0) {    //if next ch is a char 
                            arg2 += ch;                               //we add it to the string
                            count++;
                            ch = newLine.charAt(count);
                            if (Character.isWhitespace(ch)) {
                                isArg = 1;
                            }
                        }

                    }
                    //System.out.println("arg2" + arg2);
                    //argument3
                    isArg = 0;
                    while (Character.isWhitespace(ch) && count < newLine.length() && isArg == 0) {  // scan past whitespace

                        count++;
                        ch = newLine.charAt(count);

                        while (Character.isDefined(ch) && count < newLine.length() && isArg == 0) {    //if next ch is a char 
                            arg3 += ch;                               //we add it to the string
                            count++;
                            ch = newLine.charAt(count);
                            if (Character.isWhitespace(ch)) {
                                isArg = 1;
                            }
                        }

                    }
                    //System.out.println("arg3" + arg3);
                } catch (Exception e) {
                    //System.out.println("end of string");
                }
                //check for commands
                if (Command.equals("BS")) {
                    //Begin scope
                    System.out.println("BS");
                    fctEnvRecord.beginScope();
                }
                if (Command.equals("Function")) {
                    System.out.println("Function" + " " + arg1 +" "+ arg2+" "+arg3);
                    //set up the fields for function name, start and end
                    fctEnvRecord.setFuncName(arg1);
                    int a2 = Integer.parseInt(arg2);
                    int a3 = Integer.parseInt(arg3);
                    fctEnvRecord.setStartLine(a2);
                    fctEnvRecord.setEndLine(a3);
                }
                if (Command.equals("Line")) {
                    System.out.println("Line" + " " + arg1);
                    //set the current line to n
                    int a1 = Integer.parseInt(arg1);
                    fctEnvRecord.setCurrentLine(a1);
                }
                if (Command.equals("Enter")) {
                    System.out.print("Enter" + " " + arg1 + " " + arg2);
                    String symbo = arg1+"/"+arg2;
                    int value = Integer.parseInt(arg2);
                    //String name = String(arg1, 0);
                    //fctEnvRecord.top.symbol(args[1],0) ;
                    //Name.symbol(args[1], 0);
                    fctEnvRecord.put(symbo, value);
                    System.out.println(fctEnvRecord.toString());
                }
                if (Command.equals("Pop")) {
                    System.out.print("Pop" + " " + arg1);
                    //Remove the last nvar/value pairs from the symbol tables\
                    int popNum = Integer.parseInt(arg1);
                    fctEnvRecord.endScope(popNum);
                    System.out.println("\t" + fctEnvRecord.toString());
                }
                newLine = source.readLine();
            }
            if (source != null) {
                source.close();
            }
        } catch (IOException e) {
            System.out.println("bad input");
        }




        /*        
        Symbol s = Symbol.symbol("a", 1),
        s1 = Symbol.symbol("b", 2),
        s2 = Symbol.symbol("c", 3);
        
        
        fctEnvRecord.beginScope();
        fctEnvRecord.put(s,"top-level a");
        fctEnvRecord.put(s1,"top-level b");
        fctEnvRecord.beginScope();
        fctEnvRecord.put(s2,"second-level c");
        fctEnvRecord.put(s,"second-level a");
        fctEnvRecord.endScope();
        fctEnvRecord.put(s2,"top-level c");
        fctEnvRecord.endScope();
    }
*/
    /**
     * Gets the object associated with the specified symbol in the Table.
     */
    public Object get(String key) {
        Binder e = symbols.get(key);
        Object retval;
        try{
            retval = e.getValue();
        }catch(Exception c){
            System.out.println("Symbol not found");
            return 0;
        }
        return e.getValue();
    }

    /**
     * Puts the specified value into the Table, bound to the specified Symbol.<br>
     * Maintain the list of symbols in the current scope (top);<br>
     * Add to list of symbols in prior scope with the same string identifier
     */
    public void put(String key, Object value) {
       
        symbols.put(key, new Binder(value, top, symbols.get(key)));
        top = key;
    }

    /**
     * Remembers the current state of the Table; push new mark on mark stack
     */
    public void beginScope() {
        marks = new Binder(null, top, marks);
        top = null;
    }

    /**
     * Restores the table to what it was at the most recent beginScope
     *	that has not already been ended.
     */
    public void endScope(int arg1) {
        int i = 0;
        while (top != null && i < arg1) { // I modify it so it will also stop after doing i amount of pop's
            Binder e = symbols.get(top);
            if (e.getTail() != null) {
                symbols.put(top, e.getTail());
            } else {
                symbols.remove(top);
            }
            top = e.getPrevtop();
            i++;
        }
        if (top == null && marks != null) { //end of the scope, restore previous scope
            top = marks.getPrevtop();
            marks = marks.getTail();
        }
    }

    /**
     * @return a set of the Table's symbols.
     */
    public java.util.Set<String> keys() {
        return symbols.keySet();
    }

    public void setFuncName(String Name) {
        funcName = Name;
    }

    public void setStartLine(int sl) {
        startLine = sl;
    }

    public void setEndLine(int el) {
        endLine = el;
    }

    public void setCurrentLine(int cl) {
        currentLine = cl;
    }

    public String getFuncName() {

        return funcName;
    }

    public int getSartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getRecordSize(){
        return symbols.size();
    }
    public String getValue(String key){
        // s = "(" + "<" + symbols.entrySet() + ">"+")";
        String s = ""+symbols.get(key).getValue();
        return s;
    }
    
    
    public String toString() {
        String s =  "(" + "<" + symbols.entrySet() + ">," + funcName
                + "," + startLine + "," + endLine + "," + currentLine + ")";
        
        return s;
    }
}
/* symbol table machanism , the symbol table should not be coupled to any other module
 * it should only be capable of handling a single beginscope, entering symbols/values 
 * and removing a given number of symbol/value bindings. debugger uses the symbol table
 * to record symbol/offset pairs wher offset is the offset for the symbol on the runtime
 * stack.
 */
