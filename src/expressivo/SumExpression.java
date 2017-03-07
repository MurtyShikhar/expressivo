package expressivo;

public class SumExpression extends BinaryOp implements Expression{

    public SumExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
        // TODO Auto-generated constructor stub
    }

	/* d(u+v)/dx = du/dx + dv/dx */	
	public Expression differentiate(Var x) {
	    final Expression left = getLeft();
	    final Expression right = getRight();
	    final Expression leftDeriv = left.differentiate(x);
	    final Expression rightDeriv = right.differentiate(x);
	    final Expression zero = new Number(0);
	    
	    if (leftDeriv.equals(zero) && rightDeriv.equals(zero)) return zero;
	    else if (leftDeriv.equals(zero)) return rightDeriv;
	    else if (rightDeriv.equals(zero)) return leftDeriv;
	    else return new SumExpression(left.differentiate(x), right.differentiate(x));	
	} 
}