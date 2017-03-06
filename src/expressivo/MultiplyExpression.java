package expressivo;

public class MultiplyExpression extends BinaryOp implements Expression{

    public MultiplyExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '*');
    }
    
}