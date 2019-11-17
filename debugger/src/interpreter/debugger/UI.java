package interpreter.debugger;

import interpreter.Entries;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * UI class that prompt the user for input and display instructions
 * @author ken
 */
public class UI {

    private String debugAction;
    private ArrayList<Entries> sourceCode;
    private debugVirtualMachine dvm;
    private boolean keepRunning = true;
    private int pc = 0;
    private int startLine;
    private int currentLine;
    private int endLine;

    //constructor
    public UI(debugVirtualMachine t, ArrayList<Entries> sourceCode) {

        this.sourceCode = new ArrayList<Entries>();
        this.sourceCode = (ArrayList<Entries>) sourceCode.clone();
        dvm = t;
        init();
        while (true) {

            debugAction = newInput();      //prompt the user for command
            if (debugAction.equals("?")) {
                helpOut();
            }
            if (debugAction.regionMatches(0, "clrbt", 0, 5)) {
                try {
                    System.out.println("||clearing the breakpoint(s)||");
                    Scanner in = new Scanner(System.in);
                    String breakLine;
                    breakLine = debugAction.substring(5);
                    System.out.println(breakLine);
                    int[] inputs = new int[50];
                    char ch = breakLine.charAt(0);
                    String aString = "";
                    int count = 0;
                    int i = 0;
                    while (Character.isWhitespace(ch) && count < breakLine.length()) {  // scan past whitespace
                        count++;
                        ch = breakLine.charAt(count);
                        aString = "";
                        while (Character.isDigit(ch) && count < breakLine.length()) {    //if next ch is a char 
                            aString += ch;                               //we add it to the string

                            count++;
                            if (count == breakLine.length()) {
                                break;
                            }
                            ch = breakLine.charAt(count);


                        }
                        inputs[i] = Integer.parseInt(aString);
                        i++;
                    }
                    i = 0;
                    if (inputs[i] > 0 && inputs[i] < this.sourceCode.size()) {
                        while (inputs[i] < this.sourceCode.size() && inputs[i] > 0) {
                            inputs[i]--;
                            this.sourceCode.get(inputs[i]).setIsBreakptSet(false);
                            dvm.clearBreakpoint(inputs[i]);
                            inputs[i]++;
                            System.out.println("the breakpoint at " + inputs[i] + " is cleared");
                            i++;
                        }

                    } else {
                        // setbreaks = false;
                        System.out.println("no breakpoint was set at " + inputs[i]);
                    }
                    //  }

                } catch (Exception e) {
                    System.out.println("bad input... p.s. try not to enter space after the last number ");
                }

            }
            if (debugAction.regionMatches(0, "setbt", 0, 5)) {
                boolean setbreaks = true;

                try {
                    System.out.println("||initializing the breakpoin(s)||");
                    //while(setbreaks) {
                    Scanner in = new Scanner(System.in);
                    String breakLine;
                    breakLine = debugAction.substring(5);
                    System.out.println(breakLine);
                    int[] inputs = new int[50];
                    char ch = breakLine.charAt(0);
                    String aString = "";
                    int count = 0;
                    int i = 0;
                    while (Character.isWhitespace(ch) && count < breakLine.length()) {  // scan past whitespace
                        //ch= numbers.charAt(0);
                        count++;
                        ch = breakLine.charAt(count);
                        aString = "";
                        while (Character.isDigit(ch) && count < breakLine.length()) {    //if next ch is a char 
                            aString += ch;                               //we add it to the string


                            count++;
                            if (count == breakLine.length()) {
                                break;
                            }
                            ch = breakLine.charAt(count);


                        }
                        inputs[i] = Integer.parseInt(aString);
                        i++;
                    }
                    i = 0;
                    if (inputs[i] < this.sourceCode.size()) {
                        while (inputs[i] < this.sourceCode.size() && inputs[i] > 0) {
                            inputs[i]--;

                            sourceCode.get(inputs[i]).setIsBreakptSet(true);
                            dvm.setBreakpoint(inputs[i]);
                            inputs[i]++;
                            System.out.println("the breakpoint is set at " + inputs[i]);

                            i++;
                        }

                    } else {
                        System.out.println("the breakpoint cannot be set at " + inputs[i]);
                    }
                } catch (Exception e) {
                    System.out.println("bad input... p.s. try not to enter space after the last number");
                }
                // }
            }

            if (debugAction.equals("contd")) {
                //pc = dvm.executeProgram(pc);
                break;
            }
            if (debugAction.equals("qq")) {
                System.out.println("sorry, the program has not been running yet");
            }
            if (debugAction.equals("func")) {
                System.out.println("sorry, the program has not been running yet");
            }
            if (debugAction.equals("var")) {
                System.out.println("sorry, the program has not been running yet");
            }
            if (debugAction.equals("stpo")) {
                dvm.setOutFlag();
                System.out.println("Step Out!!! ");
                break;
            }
            if (debugAction.equals("stin")) {
                dvm.setInFlag();
                System.out.println("Step In!!! ");
                break;
            }
            if (debugAction.equals("stor")) {
                dvm.setOverFlag();
                System.out.println("Step Over!!! ");
                //dvm = dvm.execute();
                break;
            }
            if (debugAction.equals("rollback")) {
                System.out.println("rollback!!! rollback has no effect at the beginning");
                //break;
            }
            if (debugAction.contains("change")) {
                System.out.println("sorry, the program has not been running yet");
            }
        }
        System.out.println("Initializing the debugger...");
        System.out.println("********************************************");
    } //end of initialize of the UI

    //Run the UI, UI use the dvm 
    public void run() {
        //init();
        int show = 1;
        System.out.println("executing bytecodes...");
        dvm.execute();
        while (keepRunning == true) {
            if (dvm.getIsRunning() == false) {
                break;    //when HALT cod is reached
            }

            if (show == 1) {
                displayProgram();
                show = 0;
            }
            debugAction = newInput();      //prompt the user for command
            if (debugAction.equals("?")) {
                helpOut();
            }
            if (debugAction.regionMatches(0, "clrbt", 0, 5)) {
                try {
                    //System.out.println("********************************************");
                    System.out.println("||clearing the breakpoint(s)||");
                    //while(setbreaks) {
                    Scanner in = new Scanner(System.in);
                    String breakLine;
                    breakLine = debugAction.substring(5);
                    System.out.println(breakLine);
                    int[] inputs = new int[50];
                    char ch = breakLine.charAt(0);
                    String aString = "";
                    int count = 0;
                    int i = 0;
                    while (Character.isWhitespace(ch) && count < breakLine.length()) {  // scan past whitespace
                        //ch= numbers.charAt(0);
                        count++;
                        ch = breakLine.charAt(count);
                        aString = "";
                        while (Character.isDigit(ch) && count < breakLine.length()) {    //if next ch is a char 
                            aString += ch;                               //we add it to the string


                            count++;
                            if (count == breakLine.length()) {
                                break;
                            }
                            ch = breakLine.charAt(count);


                        }
                        inputs[i] = Integer.parseInt(aString);
                        i++;
                    }
                    i = 0;
                    if (inputs[i] > 0 && inputs[i] < sourceCode.size()) {
                        while (inputs[i] < sourceCode.size() && inputs[i] > 0) {
                            inputs[i]--;
                            sourceCode.get(inputs[i]).setIsBreakptSet(false);
                            dvm.clearBreakpoint(inputs[i]);
                            inputs[i]++;
                            System.out.println("the breakpoint at " + breakLine + " is cleared");
                            i++;
                        }

                    } else {
                        // setbreaks = false;
                        System.out.println("no breakpoint was set at " + inputs[i]);
                    }


                } catch (Exception e) {
                    System.out.println("bad input... p.s. try not to enter space after the last number");
                }
                System.out.println("********************************************");
            }
            if (debugAction.regionMatches(0, "setbt", 0, 5)) {
                boolean setbreaks = true;
                //System.out.println("********************************************");
                try {
                    System.out.println("||initializing the breakpoin(s)||");

                    Scanner in = new Scanner(System.in);
                    String breakLine;
                    breakLine = debugAction.substring(5);
                    System.out.println(breakLine);
                    int[] inputs = new int[50];
                    char ch = breakLine.charAt(0);
                    String aString = "";
                    int count = 0;
                    int i = 0;
                    while (Character.isWhitespace(ch) && count < breakLine.length()) {  // scan past whitespace

                        count++;
                        
                        ch = breakLine.charAt(count);
                        aString = "";
                        if (Character.isDigit(ch)&& count < breakLine.length()) {
                            while (Character.isDigit(ch) && count < breakLine.length()) {    //if next ch is a char 
                                aString += ch;                               //we add it to the string

                                count++;
                                if (count == breakLine.length()) {
                                    break;
                                }
                                ch = breakLine.charAt(count);


                            }
                            inputs[i] = Integer.parseInt(aString);
                            i++;
                        }
                        
                    }
                    i = 0;
                    if (inputs[i] < sourceCode.size()) {
                        while (inputs[i] < sourceCode.size() && inputs[i] > 0) {
                            inputs[i]--;
                            sourceCode.get(inputs[i]).setIsBreakptSet(true);
                            dvm.setBreakpoint(inputs[i]);
                            inputs[i]++;
                            System.out.println("the breakpoint is set at " + inputs[i]);

                            i++;
                        }

                    } else {
                        // setbreaks = false;
                        System.out.println("the breakpoint cannot be set at " + inputs[i]);
                    }

                } catch (Exception e) {
                    System.out.println("bad input... p.s. put 6 9 and not to enter space after the last number");
                }
                System.out.println("********************************************");
            }
            if (debugAction.equals("func")) {

                String functionName = dvm.getFunctionName();
                System.out.println("current function: " + functionName);

            }
            if (debugAction.equals("contd")) {


                System.out.println("continue executing");
                dvm.execute();
                show = 1;
            }
            if (debugAction.equals("qq")) {
                keepRunning = false;

            }
            if (debugAction.equals("var")) {

                dvm.getValue();


            }
            if (debugAction.equals("stpo")) {
                // isSet = StepOut(envSize, EnvStack.size());
                dvm.setOutFlag();
                System.out.println("Step Out!!! ");
                System.out.println("********************************************");
                dvm.execute();
                show = 1;
            }
            if (debugAction.equals("stin")) {
                dvm.setInFlag();
                System.out.println("Step In!!! ");
                System.out.println("********************************************");
                dvm.execute();
                show = 1;
            }
            if (debugAction.equals("stor")) {
                dvm.setOverFlag();
                System.out.println("Step Over!!! ");
                System.out.println("********************************************");
                dvm.execute();
                show = 1;
            }
            if (debugAction.equals("rollback")) {
                dvm.rollBack();
                System.out.println("rollback!!! ");
                System.out.println("********************************************");
                dvm.execute();
                show = 1;
            }
            if (debugAction.regionMatches(0, "change", 0, 6)) {
                System.out.println("********************************************");
                System.out.println("changing the local variable...");
                //while(setbreaks) {

                Scanner in = new Scanner(System.in);
                String breakLine;
                breakLine = debugAction.substring(7);
                System.out.println(breakLine);

                char ch = breakLine.charAt(0);
                String arg = "";
                String arg1 = "";
                int count = 0;
                int isArg = 0;
                try {
                    if (Character.isJavaIdentifierPart(ch) && count < breakLine.length() && isArg == 0) {    //if next ch is a char 
                        arg += ch;                               //we add it to the string


                        count++;
                        ch = breakLine.charAt(count);
                        if (Character.isWhitespace(ch)) {
                            isArg = 1;
                        }

                    }
                    while (Character.isWhitespace(ch)) {
                        count++;
                        ch = breakLine.charAt(count);
                    }
                    isArg = 0;
                    while (Character.isDigit(ch) && count < breakLine.length() && isArg == 0) {    //if next ch is a char 
                        arg1 += ch;                               //we add it to the string


                        count++;
                        ch = breakLine.charAt(count);
                        if (Character.isWhitespace(ch)) {
                            isArg = 1;
                        }
                    }
                } catch (Exception e) {
                    //System.out.println("Finish getting arguments");
                }

                int newVal = Integer.parseInt(arg1);
                int oldVal = dvm.changeVariables(arg, newVal);
                System.out.println(arg + " change from " + oldVal + "  to " + newVal);
                System.out.println("********************************************");
            }

        }
    }

    /*display the initial state*/
    public void init() {
        /*display the source program*/
        int a, b = 1;
        System.out.println("source code:");
        for (a = 0; a < this.sourceCode.size(); a++) {
            System.out.println(b + ": " + this.sourceCode.get(a).getsourceLine());
            b++;
        }

        System.out.println("Type '?' for help/or Type 'contd' to continue");
        Scanner in = new Scanner(System.in);
        String q = in.next();

        if (q.equals("?")) {
            System.out.println("********************************************");
            System.out.println("ALL lower case letter\n"
                    + "you can use the following commands:\n" + "'?' for help\n"
                    + "  -----------------------------------------------------------\n"
                    + "'setbt' and enter the line you want to break. \n"
                    + "e.g. setbt 1 2 4 do not add spac after the last breakptr \n"
                    + "  -----------------------------------------------------------\n"
                    + "'clrbt' to clear breakpoints\n "
                    + "e.g. clrbt 1 2 4 do not add space after last breakptr\n"
                    + "  -----------------------------------------------------------\n"
                    + "'func' to display the current function\n"
                    + "  -----------------------------------------------------------\n"
                    + "'contd' tocontinue execution\n"
                    + "  -----------------------------------------------------------\n"
                    + "'qq' to quit execution \n"
                    + "  -----------------------------------------------------------\n"
                    + "'var' to display variable(s) \n"
                    + "  -----------------------------------------------------------\n"
                    + "'stpo' to step out of a function \n"
                    + "  -----------------------------------------------------------\n"
                    + "'stin' to step into a function \n"
                    + "  -----------------------------------------------------------\n"
                    + "'stor' to step over \n"
                    + "  -----------------------------------------------------------\n"
                    + "'rollback' to restart the current function \n"
                    + "  -----------------------------------------------------------\n"
                    + "'change' to the local variable , e.g change i 7\n"
                    + "  -----------------------------------------------------------\n");
            System.out.println("********************************************");
        }


    }

    /*a method to get a new input*/
    public String newInput() {
        //System.out.println("********************************************");
        System.out.println("  ----------------------------------------\n");
        System.out.println("<<<Enter a command or ? for help>>> ");
        Scanner in = new Scanner(System.in);
        String c = in.nextLine();

        //System.out.println("********************************************");
        return c;
    }

    public void helpOut() {
        System.out.println("********************************************");
        System.out.println("ALL lower case letter\n"
                + "you can use the following commands:\n" + "'?' for help\n"
                + "  -----------------------------------------------------------\n"
                + "'setbt' and enter the line you want to break. \n"
                + "e.g. setbt 1 2 4 do not add spac after the last breakptr \n"
                + "  -----------------------------------------------------------\n"
                + "'clrbt' to clear breakpoints\n "
                + "e.g. clrbt 1 2 4 do not add space after last breakptr\n"
                + "  -----------------------------------------------------------\n"
                + "'func' to display the current function\n"
                + "  -----------------------------------------------------------\n"
                + "'contd' tocontinue execution\n"
                + "  -----------------------------------------------------------\n"
                + "'qq' to quit execution \n"
                + "  -----------------------------------------------------------\n"
                + "'var' to display variable(s) \n"
                + "  -----------------------------------------------------------\n"
                + "'stpo' to step out of a function \n"
                + "  -----------------------------------------------------------\n"
                + "'stin' to step into a function \n"
                + "  -----------------------------------------------------------\n"
                + "'stor' to step over \n"
                + "  -----------------------------------------------------------\n"
                + "'rollback' to restart the current function \n"
                + "  -----------------------------------------------------------\n"
                + "'change' to the local variable , e.g change i 7\n"
                + "  -----------------------------------------------------------\n");
        System.out.println("********************************************");
    }

    /*a mothed to display the current function*/
    public void displayProgram() {
        startLine = dvm.getStartLine();
        endLine = dvm.getEndLine();
        currentLine = dvm.getCurrentLine();
        if (dvm.getCurrentLine() != -1) {
            System.out.println("********************************************");
            System.out.println("'<----' is current line is,and  '*' is breakpoints");
            System.out.println("The current function:");

        }

        if (startLine <= 0) {
            int i, line = 1;

            for (i = 0; i < sourceCode.size() && dvm.getCurrentLine() != -1; i++) {
                if (line < sourceCode.size() && sourceCode.get(i).getIsBreakptSet() == true) {
                    System.out.print("*");
                }
                System.out.print("\t" + line + ": " + sourceCode.get(i).getsourceLine());
                if (line == currentLine) {
                    System.out.print(" <----");
                }
                System.out.println("");
                line++;
            }

        } else {
            int i, line;

            for (i = startLine - 1, line = i + 1; i < endLine; i++) {
                if (line < sourceCode.size() && sourceCode.get(i).getIsBreakptSet() == true) {
                    System.out.print("*");
                }
                System.out.print("\t" + line + ": " + sourceCode.get(i).getsourceLine());
                if (line == currentLine) {
                    System.out.print(" <----");
                }
                System.out.println("");
                line++;
            }
            System.out.println("********************************************");
        }
        startLine = 0;
        currentLine = 0;
        endLine = 0;
    }
}
