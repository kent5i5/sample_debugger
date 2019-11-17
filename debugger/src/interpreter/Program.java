/*
 * The Program Class will hold the bytecode program laoded from file,
 * it will also resolve symbolic addresses in the program
 */
package interpreter;

import java.util.ArrayList;

public class Program {
    
    private ArrayList<ByteCode> program = new ArrayList<ByteCode>();
    
    //public static void main (){
        //FalseBranchCode code = new FalseBranchCode();
        //program.add();
    //}
    public Program(){
        program = null;
    }
    public Program(ByteCode bytecode) {
       //for (int i= 0; i < bytecodeList.size();i++){
           //program.add(bytecodeList.get(i));
       //}
    }
    /**
     *  store the new bytecode in the program vector
     *  @param code is the bytecode to store
    */
    public void storeCode(ArrayList<ByteCode> code){
    
        for (int i= 0; i < code.size();i++){
           program.add(code.get(i));
           //System.out.println("program code "+program.get(i).toString());
       }
    }
    public ByteCode getCode(int pc) {

        return program.get(pc);
    }
    //use default constructor         
    
    /* scan through the program 
             * if bytecode has args(String label) 
             * equal to the target code args(String of label e.g continue<<6>> 
             * store the index of the target bytecode 
             * into the addrs variable*/
    public int[] resolveAddress(String[] arrayOfArgs,String[] arrayOfLabel,int[] symAddrs,int lineno) {
        //for (int i=0 ; i < lineno; i++)
        //                System.out.println(" branch "+ arrayOfArgs[i] );
        
        //for (int i=0 ; i < lineno; i++)
                        //System.out.println("program " +program.size());   //debugger code
        
        for (int i=0 ; i < lineno; i++){
            String Symbol = "LABEL"+arrayOfArgs[i];   // e.g. LABELendWhile
             //System.out.println(" symbol "+Symbol );
            for (int j=0; j<lineno ;j++){
                        //e.g symAddrs[0] is the symbolic address of 0 bytecode which get j-label address 
                if (Symbol.equals(arrayOfLabel[j]))
                        symAddrs[i] = j -1; 
                // I make it 1 less since pc++ will change the addr before execute target bytecode  
            }
        }
        //for (int i=0 ; i < lineno; i++)
                       // System.out.println(" symbolic "+symAddrs[i] );
        return symAddrs;
    }
  
}
