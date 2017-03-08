package expressivo;


public class MultiplyExpression extends BinaryOp implements Expression{

    public MultiplyExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '*');
    }
    

    /* factory method for generating expressions 
     * @returns: An Expression formed using the following rules
     * 1: a*b = 0 if either a or b is 0
     * 2: a*b = a if b = 1 || a*b = b if a = 1
     * 3: the Number always comes first.
     * */
    public static Expression createProduct(Expression leftOp, Expression rightOp) {
    	boolean isNumLeft = leftOp instanceof Number;
    	boolean isNumRight = rightOp instanceof Number;
    	final Expression one = new Number(1);
    	final Expression zero = new Number(0);
    	
    	if (isNumLeft) {
            int leftNum  = ((Number) leftOp).getNum();
            if (isNumRight) {
                int rightNum = ((Number) rightOp).getNum();
                return new Number(leftNum*rightNum);
            }
            else if (rightOp instanceof MultiplyExpression) {
    	        final Expression leftOfRightOp = ((MultiplyExpression) rightOp).getLeft();
    	        final Expression rigtOfRightOp = ((MultiplyExpression) rightOp).getRight();
    	        if (leftOfRightOp instanceof Number) {
    	            int leftOfRightNum = ((Number) leftOfRightOp).getNum();
    	            return createProduct(new Number(leftNum*leftOfRightNum), rigtOfRightOp);
    	        }
    	    }
    	   
    	}
    	

    	if (leftOp.equals(zero) || rightOp.equals(zero)) return zero;
    	else if (leftOp.equals(one)) return rightOp;
    	else if (rightOp.equals(one)) return leftOp;
    
    	else if (isNumRight) return new MultiplyExpression(rightOp, leftOp);
    	else return new MultiplyExpression(leftOp, rightOp);
    }



  

    /* duv/dx = udv/dx + vdu/dx */
    public Expression differentiate(Var x) {
        final Expression left = getLeft();
        
        final Expression right = getRight();
        final Expression leftDeriv = left.differentiate(x);
        final Expression rightDeriv = right.differentiate(x);
               
        return SumExpression.createSum(createProduct(left, rightDeriv), createProduct(right, leftDeriv));	
	} 
}