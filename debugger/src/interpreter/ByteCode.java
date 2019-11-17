/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Vector;

/**
 *
 * @author ken
 */
public abstract class ByteCode {
    
    //private Vector<String> args;
    /*nullary constructor*/
     public ByteCode(){
          
     }
    //public abstract void init();
    // public void storeAddrs(int a){}
    // public String getArgs(){return null;} 
    public abstract void init(String arrayOfArgs,int symAddrs );
    public abstract String toString(); 
    public abstract void execute(VirtualMachine t);
    
    
}
