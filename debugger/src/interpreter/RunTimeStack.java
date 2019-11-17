/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author ken
 */
public class RunTimeStack {

    private Stack framePointers; //used to record prior frame pointer when
                                 //when calling functions
    private Vector runStack ;     //the runtime stack; it's Vector rather than a 
                                 //Stack

    public RunTimeStack() {      
        
        //runStack = null;
        runStack = new Vector(200);
        framePointers = new Stack();
        framePointers.push(0);
    }
    
    /*
     * dump the RunTimeStack information for debugging 
     */
    public void dump() {
       int i = 0;
       int j = framePointers.size();
       int[] singleFrameSize = new int[100];
        /*print the run time Stack*/
       int frameSize = framePointers.size(); 
       int max = runStack.size();     
       while (j>0){
           singleFrameSize[j] = (Integer)framePointers.get(j-1);
           j--;
       }
       int k = 1;
       while (k <=frameSize){
            
            System.out.print("[");
            while (i < runStack.size()){
 
               if(i == singleFrameSize[k+1]&& i != 0){
                   break;
               }
               if (i != singleFrameSize[k])
                      System.out.print(",");
               
               System.out.print(runStack.get(i));
               
               i++;
            }
            System.out.print("] ");
            k++;
       }
       System.out.println("");
        
    }
    /*return index of last element*/
    public int peek() {
        
        return (Integer)runStack.lastElement();
        //return runStack.indexOf(runStack.lastElement());
    }
    
    //change the local variable 
    public int set(int offset,int var){
        int oldvalue = (Integer)runStack.set(offset, var);
        return oldvalue;
    }
    //remove last element in runStack and return it
    public int pop() {
        //framePointers.pop();
        int i = runStack.size() - 1;
        int retval =(Integer)runStack.remove(i);
        return retval;
    }

    public int push(int i) {
        //System.out.println("reserved space");
        runStack.addElement(i);
        //framePointers.push(i);
        return i;
    }
    

    public void newFrameAt(int offset) {
            int numArgsToPush = runStack.size()-offset;
            framePointers.push(numArgsToPush);
            //for (int i = runStack.size(); i<offset;i++){
            //    runStack.add(i);
            //}
    }

    //pop the current frame
    public void popFrame() {
        //Vector temp;
       // runStack.add(runStack.lastElement());
        //int currentFrame = (Integer)lastElement
        int index = (Integer)framePointers.pop();
        int topEle = 0;
        if(!runStack.isEmpty() ) {
             topEle = (Integer)runStack.get(runStack.size()-1); //save the last element
        }
        for (int i=(runStack.size()-1) ; i>=index; i--)
            runStack.remove(i);
        
        runStack.add(topEle); //push the saved value
    }

    /*get the last element in runStack and pop the current frame */
    public int store(int offset) {
        int retval = (Integer)runStack.remove(runStack.size()-1);
        if (offset == 0){
            runStack.setElementAt(retval, 0);
        }else{
            runStack.setElementAt(retval, offset);
        }
        //runStack.add(offset,runStack.lastElement());   
        return retval;
    }
    /*load value of the location(count from start to the offset) onto to top of stack*/
    public int load(int offset) {
        try{
            /*calculate the location start from the begining the current frame to offset
             * and add it to the  end of runStack/end of current frame
             */
            int locat =  (Integer)framePointers.peek() + offset;    
            runStack.add(runStack.get(locat));
            
            return (Integer)runStack.get(locat);
        }catch(Exception e){return -1;}
    }

    /*used to load Literals onto the stack. e.g lit 5 , call push with 5*/
    public Integer push(Integer i) {
        //framePointers.p;
        runStack.addElement(i);
        return i;
    }
    public int size(){
        return runStack.size();
    }
    public int getValue(int offset){
        return (Integer)runStack.get(offset);
    }
    
}
