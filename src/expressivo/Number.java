package expressivo;

public class Number implements Expression{
    private final int number;
    /**
     * 
     * @param num: The number this contains
     */
    public Number(int num) {
        this.number = num;
    }
    
    /**
     * 
     * @return the number that this contains
     */
    public int getNum(){
        return number;
    }
    
    /**
     * @return a string representation for this enclosed in parenthesis
     */
    public String toString() {
        return Integer.toString(number);
    }
    
    /**
     * 
     * @param that: Object to be check equality against
     * @return boolean indicating whether this == that
     */
    public boolean equals(Object that) {
        if (that instanceof Number) {
            Number that_number = (Number) that;
            return that_number.number == number;
        }
        
        else return false;
    }
}