package expressivo;

public class Var implements Expression{
    private final String var;
    /**
     * 
     * @param var: The variable this contains (requires var to consist of uppercase and lowercase alphabets only)
     */
    public Var(String var) {
        this.var = var;
    }
    
    /**
     * 
     * @return the variable that this contains
     */
    public String getVar(){
        return var;
    }
    
    /**
     * @return a string representation for this enclosed in parenthesis
     */
    public String toString() {
        return var;
    }
    
    
    /**
     * @param that: Object to be compared against
     * @return Boolean indicating whether this is equal to that
     */
    
    public boolean equals(Object that) {
        if (that instanceof Var) {
            Var that_var = (Var) that;
            return (that_var.var).equals(var);
        }
        
        else return false;
    }
}