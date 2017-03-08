package expressivo;


public class SumExpression extends BinaryOp implements Expression{

    public SumExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
        // TODO Auto-generated constructor stub
    }

    /* factory method for generating expressions 
     * @returns an Expression formed using the following rules
     * a+b = a if b = 0 || a+b = b if a = 0
     * a+b = 2*a if a = b
     * also simplifies a*x + b*x as (a+b)*x if a and b are both numbers
     * */
    public static Expression createSum(Expression leftOp, Expression rightOp) {
    	boolean isNumLeft = leftOp instanceof Number;
    	boolean isNumRight = rightOp instanceof Number;
    	final Expression zero = new Number(0);
    	if (isNumLeft && isNumRight) {
    		int leftNum  = ((Number) leftOp).getNum();
    		int rightNum = ((Number) rightOp).getNum();
    		return new Number(leftNum + rightNum);

    	}
    	else if (leftOp.equals(zero)) return rightOp;
    	else if (rightOp.equals(zero)) return leftOp;
    	else if (leftOp.equals(rightOp)) return MultiplyExpression.createProduct(new Number(2), leftOp);
    	else{
    	    
    	    boolean isLeftProduct = leftOp instanceof MultiplyExpression;
    	    boolean isRightProduct = rightOp instanceof MultiplyExpression;
    	    
    	    if (isLeftProduct || isRightProduct) {
    	        final Expression RightOfLeft = isLeftProduct ? ((MultiplyExpression) leftOp).getRight() : leftOp;
                final Expression RightofRight =  isRightProduct? ((MultiplyExpression) rightOp).getRight(): rightOp;
                final Expression leftOfLeft =  isLeftProduct? ((MultiplyExpression) leftOp).getLeft() : new Number(1);
                final Expression leftOfRight =  isRightProduct? ((MultiplyExpression) rightOp).getLeft() : new Number(1);

                
                if (RightOfLeft.equals(RightofRight)) {
                    if (leftOfLeft instanceof Number && leftOfRight instanceof Number)
                        return MultiplyExpression.createProduct(createSum(leftOfLeft, leftOfRight), RightOfLeft);
                    
                    else return new SumExpression(leftOp, rightOp);
                }
                
                else if (RightOfLeft.equals(rightOp) && leftOfLeft instanceof Number) return MultiplyExpression.createProduct(createSum(leftOfLeft, new Number(1)), rightOp);
                
                
                else if (RightofRight.equals(leftOp) && leftOfRight instanceof Number) return MultiplyExpression.createProduct(createSum(leftOfRight, new Number(1)), leftOp);
                
                else return new SumExpression(leftOp, rightOp);

    	    }
    	    
    	    
    	    
    	    else return new SumExpression(leftOp, rightOp);
    	}
    }

	/* d(u+v)/dx = du/dx + dv/dx */	
	public Expression differentiate(Var x) {
	    final Expression left = getLeft();
	    final Expression right = getRight();
	    final Expression leftDeriv = left.differentiate(x);
	    final Expression rightDeriv = right.differentiate(x);
	    
	    return createSum(leftDeriv, rightDeriv);	
	} 
}