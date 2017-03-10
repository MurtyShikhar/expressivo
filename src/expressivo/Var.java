package expressivo;

import java.util.Map;

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

    /* du/dx = 1 if u == x else 0 */
    public Expression differentiate(Var x) {
        if (this.equals(x)) return Num.one();
        else return Num.zero();
    }
    
    public Expression simplify(Map<String, Double> env) {
      if (env.containsKey(var)) {
          double val = env.get(var);
          return new Num(val);
      }
      
      else return this;
      
    }

}