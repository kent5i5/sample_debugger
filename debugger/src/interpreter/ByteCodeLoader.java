/*
 * ByteCodeLoader class will load the bytecodes from the file into the Virtual
 * Machine
 * When the bytecodes are loaded we 'll get the string for the bytecode and then
 * we'll get the bytecode class for that string from the Codetable to constrct an
 * instance of the bytecode e.g. if the bytecode loader reads the line:
 * HALT
 * we'll build an instance of the class HaltCode and store that instance in the 
 * Program object
 */
package interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Vector;

/**
 *
 * @author ken
 */
public class ByteCodeLoader {

    private boolean atEOF = false;
    private BufferedReader source;
    private String newLine;
    ///private Vector<String> args;
    int lineno;
    //private boolean isPriorEndLine = true;
    private Program program;
    private ByteCode bytecode;
    private ArrayList<ByteCode> bytecodeList = new ArrayList<ByteCode>();
    private int mode = 0; //indicate the mode normal/debugging

    public ByteCodeLoader(String programFile) throws IOException {

        //System.out.print(programFile);
        source = new BufferedReader(new FileReader(programFile));
        //args = new Vector(20);
        lineno = 0;
        //newLine = null;

        program = new Program(bytecode);
        if (source == null) {
            throw new IOException();
        }

    }

    public ByteCodeLoader(String d, String programFile) throws IOException {
        if (d.equals("-d")) {
            mode = 1;
        } else {
            //throw new IOException() ;
        }

        //System.out.print(programFile);
        source = new BufferedReader(new FileReader(programFile));
        //args = new Vector(20);
        lineno = 0;
        //newLine = null;

        program = new Program(bytecode);
        if (source == null) {
            throw new IOException();
        }

    }

    /**
     *description
     * loadcodes method read a line from the file into the Virtual Machine
     * we read the line char by char until we hit the whitespace and use the string to
     * to get the bytecode class for that string from the Codetable to construc an 
     * an instance of the bytecodes
     * @author ken
     */
    public Program loadCodes() {
        //program.(bytecode);
        String code;
        String arg1;
        //String arg2;
        String[] arrayOfName = new String[200];
        String[] arrayOfArgs = new String[200];
        String[] arrayOfLabel = new String[200];
        int[] symAddrs = new int[200];
        try {

            char ch;
            newLine = source.readLine();
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
                code = "";
                arg1 = "";
                //arg2 = "";                
                ch = newLine.charAt(count);
                try {
                    if (Character.isJavaIdentifierStart(ch)) {
                        //load the bytecodes 

                        try {
                            do {

                                count++;
                                //endPosition++;
                                code += ch;
                                ch = newLine.charAt(count);
                            } while (Character.isJavaIdentifierPart(ch) && !Character.isWhitespace(ch));
                        } catch (Exception e) {
                            atEOF = true;
                        }
                    }
                    if (mode == 1) {
                        if (code.equals("CALL") || code.equals("POP")
                                || code.equals("LIT") || code.equals("RETURN")) {
                            code = code + " d";
                        }
                    }

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
                                isArg = 0;
                            }
                        }

                    }

                } catch (Exception e) {
                    //System.out.println("end of string");
                }

                /* Record the symbolic addresses*/
                if (mode == 0) {
                        if ( !code.equals("FUNCTION")&& !code.equals("FORMAL")
                            && !code.equals("LINE")) {

                        arrayOfName[lineno] = code;
                        arrayOfArgs[lineno] = arg1;
                        if (code.equals("LABEL")) {
                            arrayOfLabel[lineno] = "LABEL" + arg1;   //e.g LABELfact
                        }
                        lineno++;
                        //System.out.println("program storecode "+ program.getCode(0).toString() 
                        //     + "arg1"+ lineno);
                    }
                    
                    newLine = source.readLine();
                } else {
                        arrayOfName[lineno] = code;
                        arrayOfArgs[lineno] = arg1;
                        if (code.equals("LABEL")) {
                            arrayOfLabel[lineno] = "LABEL" + arg1;   //e.g LABELfact
                        }
                        //System.out.println(code);

                        //program.storeCode(bytecode);
                        lineno++;
                        newLine = source.readLine();
                        //System.out.println("program storecode "+ program.getCode(0).toString() 
                        //     + "arg1"+ lineno);
                }
            }

            // System.out.println(" g "+ newLine);

        } catch (Exception e) {
            System.out.print("bad");
        }
        try {
            source.close();
        } catch (Exception e) {
        }

        //System.out.print(program);
        program.resolveAddress(arrayOfArgs, arrayOfLabel, symAddrs, lineno);

        try {
            String codeClass = null;
            for (int i = 0; i < lineno; i++) {
                codeClass = CodeTable.get(arrayOfName[i]);  //return the string-value 
                //System.out.println("codeClass "+ arrayOfName[i]);
                String s = "interpreter.bytecode." + codeClass;
                //bytecode = (ByteCode)(interpreter.bytecode.debuggerByteCodes.CallDebugCode.class.newInstance());
                bytecode = (ByteCode) (Class.forName(s).newInstance()); // new instance  of the class
                //System.out.println("bytecode "+ bytecode.toString());


                bytecode.init(arrayOfArgs[i], symAddrs[i]);    //initialize bytecodes 
                bytecodeList.add(bytecode);
                //System.out.println("code " + bytecode.toString() );

            }
        } catch (Exception e) {
            System.out.println("symbol error");
        }
        //System.out.println("end of init" + bytecodeList.size() );
        program.storeCode(bytecodeList);

        return program;
    }
}
