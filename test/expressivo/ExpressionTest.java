/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // Check all equality functions
    // for checking parsing, divide into multiSum, multiProd, sumofprods, prodOfsums and a complex parse
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // simple tests for checking isEquals
    @Test
    public void checkEqualityVar() {
        final Expression varOp = new Var("x");
        assertEquals(varOp , new Var("x"));
    }

    @Test 
    public void checkEqualityNumber() {
        final Expression number = new Number(9);
        assertEquals(number , new Number(9));
    }

    @Test
    public void checkEqualitySum() {
        final Expression left = new Var("x");
        final Expression right = new Var("y");
        final SumExpression sumexpr = new SumExpression(left, right);
        assertEquals(sumexpr, new SumExpression(new Var("x"), new Var("y")));
    }


    @Test
    public void checkEqualityMult() {
        final Expression left = new Var("x");
        final Expression right = new Var("y");
        final MultiplyExpression multexpr = new MultiplyExpression(left, right);
        assertEquals(multexpr, new MultiplyExpression(new Var("x"), new Var("y")));
    }

    // simple tests for parsing
    @Test
    public void multisumTest() {
        final BinaryOp expression = (BinaryOp) Expression.parse("x+y+z");
        assertEquals(expression.getLeft(), new SumExpression(new Var("x"), new Var("y")));
        assertEquals(expression.getRight(), new Var("z"));
    }

    @Test
    public void multiproductTest() {
        final BinaryOp expression = (BinaryOp) Expression.parse("x*y*z");
        assertEquals(expression.getRight(),new Var("z"));
        assertEquals(expression.getLeft(),new MultiplyExpression(new Var("x"), new Var("y")));
    }


    @Test
    public void sumProductTest() {
    	final Expression expression = Expression.parse("x*y + z*w");
    	final Expression left = new MultiplyExpression(new Var("x"), new Var("y"));
    	final Expression right = new MultiplyExpression(new Var("z"), new Var("w"));
        assertEquals(expression , new SumExpression(left, right));
    }
    
    @Test
    public void sumProductTest2() {
        final Expression expression = Expression.parse("(2*x    )+    (    y*x    )");
        final Expression left = new MultiplyExpression(new Number(2), new Var("x"));
        final Expression right = new MultiplyExpression(new Var("y"), new Var("x"));
        assertEquals(expression , new SumExpression(left, right));
    }

    @Test
    public void productTest() {
    	final Expression expression = Expression.parse("(x+y)*(w+z)");
    	final Expression leftOp  = new SumExpression(new Var("x"), new Var("y"));
    	final Expression rightOp = new SumExpression(new Var("w"), new Var("z"));
    	assertEquals(expression , new MultiplyExpression(leftOp, rightOp));
    }
    
    @Test
    public void complexParseTest(){
        final Expression expression = Expression.parse("4 + 3 * x + 2 * x * x + 1 * x * x * (((x)))");
        final Expression subProd1 = new MultiplyExpression(new Number(3), new Var("x"));
        final Expression subProd2 = new MultiplyExpression(new MultiplyExpression(new Number(2), new Var("x")), new Var("x"));
        final Expression subProd3 = new MultiplyExpression(new MultiplyExpression(new MultiplyExpression(new Number(1), new Var("x")), new Var("x")), new Var("x"));
        final Expression sumexpr = new SumExpression(new SumExpression(new SumExpression(new Number(4), subProd1), subProd2), subProd3);
        assertEquals(sumexpr, expression);
    }
    
    
    /* Simple tests for checking derivatives */
    
    @Test
    public void NumberDerivativeCheck() {
        final Expression exp = new Number(2);
        assertEquals(new Number(0), exp.differentiate(new Var("x")));
    }
    
    @Test
    public void VariableDerivativeCheck() {
        final Expression exp = new Var("x");
        assertEquals(new Number(1), exp.differentiate(new Var("x")));
        assertEquals(new Number(0), exp.differentiate(new Var("y")));

    }
    
    @Test
    public void ProductDerivativeCheck() {
        final Expression exp = new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")));
        final Expression left = new MultiplyExpression(new Var("x"), new SumExpression(new Var("x"), new Var("x")) );
        final Expression right = new MultiplyExpression(new Var("x"), new Var("x"));
        assertEquals(new SumExpression(left, right), exp.differentiate(new Var("x")));
    }
    
    @Test
    public void ProductDerivativeCheck2() {
        final Expression exp = new MultiplyExpression(new Var("x"), new Var("y"));
        final Expression ans = new Var("y");
        assertEquals(ans, exp.differentiate(new Var("x")));
    }
   
    @Test
    public void ProductDerivativeCheck3() {
        final Expression exp = new MultiplyExpression(new Var("y"), new Var("x"));
        final Expression ans = new Var("y");
        assertEquals(ans, exp.differentiate(new Var("x")));
    }
    
    
    @Test
    public void ProductDerivativeCheck4() {
        final Expression exp = new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")));
        assertEquals(exp.differentiate(new Var("y")), new Number(0));
    }
    
    
    @Test
    public void SumDerivativeCheck() {
        final Expression exp = new SumExpression(new Var("x"), new Number(1));
        assertEquals(exp.differentiate(new Var("x")), new Number(1));
    }
    
    
    @Test
    public void ProductSumDerivativeCheck1() {
        final Expression exp = new MultiplyExpression(new SumExpression(new Var("x"), new Var("y")), new SumExpression(new Var("x"), new Var("y")));
        final Expression leftOp = new SumExpression(new Var("x"), new Var("y"));
        assertEquals(exp.differentiate(new Var("x")), new SumExpression(leftOp, leftOp));
    }
    

}
