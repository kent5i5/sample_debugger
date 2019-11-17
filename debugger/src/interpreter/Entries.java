/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author ken
 */
public class Entries {
    private String sourceLine;
    private boolean isBreakptSet;
    public Entries(String s, boolean isSet){
        sourceLine = s;
        isBreakptSet = isSet;
    }
    public String toString(){
        return sourceLine;
    }
    
    public void setSourceLine(String line){
        sourceLine = line;
    }
    public void setIsBreakptSet(boolean op){
        isBreakptSet = op;
    }
    public String getsourceLine() {
        return sourceLine;
    }
    public boolean getIsBreakptSet(){
        return isBreakptSet;
    }
}
