package expressivo;

import org.hamcrest.core.IsInstanceOf;

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
    	else if (leftOp.equals(rightOp)) return new MultiplyExpression(new Number(2), leftOp);
    	else{
    	    if (leftOp instanceof MultiplyExpression && rightOp instanceof MultiplyExpression) {
    	        final Expression RightOfLeft = ((MultiplyExpression) leftOp).getRight();
    	        final Expression RightofRight =  ((MultiplyExpression) rightOp).getRight();
    	        if (RightOfLeft.equals(RightofRight)) {
    	            final Expression leftOfLeft =  ((MultiplyExpression) leftOp).getLeft();
    	            final Expression leftOfRight =  ((MultiplyExpression) rightOp).getLeft();
    	            if (leftOfLeft instanceof Number && leftOfRight instanceof Number)
    	                return MultiplyExpression.createProduct(createSum(leftOfLeft, leftOfRight), RightOfLeft);
    	            else return new SumExpression(leftOp, rightOp);
    	        }
    	        
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