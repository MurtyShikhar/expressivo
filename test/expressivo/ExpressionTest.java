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
    //   TODO
    
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
    public void productTest() {
    	final Expression expression = Expression.parse("(x+y)*(w+z)");
    	Expression leftOp  = new SumExpression(new Var("x"), new Var("y"));
    	Expression rightOp = new SumExpression(new Var("w"), new Var("z"));
    	assertEquals(expression , new MultiplyExpression(leftOp, rightOp));
    }

}
