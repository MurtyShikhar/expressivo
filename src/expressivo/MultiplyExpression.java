package expressivo;

public class MultiplyExpression extends BinaryOp implements Expression{

    public MultiplyExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '*');
    }
    

    /* factory method for generating expressions */
    public static Expression createProduct(Expression leftOp, Expression rightOp) {
    	boolean isNumLeft = leftOp instanceof Number;
    	boolean isNumRight = rightOp instanceof Number;
    	final Expression one = new Number(1);
    	final Expression zero = new Number(0);
    	if (isNumLeft && isNumRight) {
    		int leftNum  = ((Number) leftOp).getNum();
    		int rightNum = ((Number) rightOp).getNum();
    		return new Number(leftNum*rightNum);

    	}

    	if (leftOp.equals(zero) || rightOp.equals(zero)) return zero;
    	else if (leftOp.equals(one)) return rightOp;
    	else if (rightOp.equals(one)) return leftOp;
    	else return new MultiplyExpression(leftOp, rightOp);
    }




    /* duv/dx = udv/dx + vdu/dx */
    public Expression differentiate(Var x) {
        final Expression left = getLeft();
        
        final Expression right = getRight();
        final Expression leftDeriv = left.differentiate(x);
        final Expression rightDeriv = right.differentiate(x);
        
        // /* duv/dx = 0 if du/dx = dv/dx = 1 */
        // if (leftDeriv.equals(zero) && rightDeriv.equals(zero)) return zero;
        // /* duv/dx = udv/dx if du/dx = 0 and similarly if dv/dx = 0, duv/dx = vdu/dx */
        // else if (leftDeriv.equals(zero))  return rightDeriv.equals(one) ? left : new MultiplyExpression(left, rightDeriv);
        // else if (rightDeriv.equals(zero)) return leftDeriv.equals(one) ? right : new MultiplyExpression(right, leftDeriv);
        
        // duv/dx = u+v if du/dx = dv/dx = 1 
        // else if (leftDeriv.equals(one) && rightDeriv.equals(one)) return new SumExpression(left, right);
        // /* duv/dx = udv/dx + v if du/dx = 1 and = u + vdu/dx if dv/dx = 1 */
        // else if (leftDeriv.equals(one)) return new SumExpression(new MultiplyExpression(left, rightDeriv), right);         
        // else if (rightDeriv.equals(one)) return new SumExpression(left, new MultiplyExpression(right, leftDeriv));           
        
        return SumExpression.createSum(createProduct(left, rightDeriv), createProduct(right, leftDeriv));	
	} 
}