package expressivo;

public class Number implements Expression{
    private final double number;
    /**
     * 
     * @param num: The number this contains
     */
    public Number(double num) {
        this.number = num;
    }
    
    /**
     * 
     * @return the number that this contains
     */
    public double getNum(){
        return number;
    }
    
    /**
     * @return a string representation for this enclosed in parenthesis
     */
    public String toString() {
        return '(' + Double.toString(number) + ')';
    }
    
    /**
     * 
     * @param that: Object to be check equality against
     * @return boolean indicating whether this == that
     */
    public boolean isEqual(Object that) {
        if (that instanceof Number) {
            Number that_number = (Number) that;
            return that_number.number == number;
        }
        
        else return false;
    }
}