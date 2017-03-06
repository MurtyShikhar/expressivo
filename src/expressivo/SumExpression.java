package expressivo;

public class SumExpression extends BinaryOp implements Expression{

    public SumExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
        // TODO Auto-generated constructor stub
    }
 
}