package expressivo;

public class SumExpression extends BinaryOp implements Expression{

    public SumExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
        // TODO Auto-generated constructor stub
    }

    /* factory method for generating expressions */
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
    	else return new SumExpression(leftOp, rightOp);
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