/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;
import java.util.*;
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
    public void checkEqualityNum() {
        final Expression Num = new Num(9);
        assertEquals(Num , new Num(9));
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
        assertEquals(expression.getRight(), new SumExpression(new Var("y"), new Var("z")));
        assertEquals(expression.getLeft(), new Var("x"));
    }

    @Test
    public void multiproductTest() {
        final BinaryOp expression = (BinaryOp) Expression.parse("x*y*z");
        assertEquals(expression.getLeft(),new Var("x"));
        assertEquals(expression.getRight(),new MultiplyExpression(new Var("y"), new Var("z")));
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
        final Expression left = new MultiplyExpression(new Num(2), new Var("x"));
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
        final Expression subProd1 = new MultiplyExpression(new Num(3), new Var("x"));
        final Expression subProd2 = new MultiplyExpression(new Num(2), new MultiplyExpression(new Var("x"), new Var("x")));
        final Expression subProd3 = new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")));
        final Expression sumexpr = new SumExpression(new Num(4), new SumExpression(subProd1, new SumExpression(subProd2, subProd3)));
        assertEquals(sumexpr, expression.simplify(new HashMap<>()));
    }
    
    
    /* Simple tests for checking derivatives */
    
    @Test
    public void NumDerivativeCheck() {
        final Expression exp = new Num(2);
        assertEquals(new Num(0), exp.differentiate(new Var("x")));
    }
    
    @Test
    public void VariableDerivativeCheck() {
        final Expression exp = new Var("x");
        assertEquals(new Num(1), exp.differentiate(new Var("x")));
        assertEquals(new Num(0), exp.differentiate(new Var("y")));

    }
    
    @Test
    public void ProductDerivativeCheck() {
        final Expression exp = new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")));
        final Expression coeff = new MultiplyExpression(new Var("x"), new Var("x"));
        assertEquals(new MultiplyExpression(new Num(3), coeff), exp.differentiate(new Var("x")).simplify(new HashMap<>()));
    }
    
    @Test
    public void ProductDerivativeCheck2() {
        final Expression exp = new MultiplyExpression(new Var("x"), new Var("y"));
        final Expression ans = new Var("y");
        assertEquals(ans, exp.differentiate(new Var("x")).simplify(new HashMap<>()));
    }
   
    @Test
    public void ProductDerivativeCheck3() {
        final Expression exp = new MultiplyExpression(new Var("y"), new Var("x"));
        final Expression ans = new Var("y");
        assertEquals(ans, exp.differentiate(new Var("x")).simplify(new HashMap<>()));
    }
    
    
    @Test
    public void ProductDerivativeCheck4() {
        final Expression exp = new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")));
        assertEquals(exp.differentiate(new Var("y")).simplify(new HashMap<>()), new Num(0));
    }
    
    
    @Test
    public void SumDerivativeCheck() {
        final Expression exp = new SumExpression(new Var("x"), new Num(1));
        assertEquals(exp.differentiate(new Var("x")),  new SumExpression(new Num(1), new Num(0)));
    }
    
    
    @Test
    public void ProductSumDerivativeCheck1() {
        final Expression exp = new MultiplyExpression(new SumExpression(new Var("x"), new Var("y")), new SumExpression(new Var("x"), new Var("y")));
        final Expression leftOp = new SumExpression(new Var("x"), new Var("y"));
        assertEquals(exp.differentiate(new Var("x")).simplify(new HashMap<>()), new MultiplyExpression(new Num(2), leftOp));
    }
    
    /* Simple tests for checking Simplify() */
    
    /* check case-sensitivity */
    @Test
    public void VarSimplify() {
        final Expression exp = new Var("x");
        Map<String, Double> env = new HashMap<>();
        env.put("X", 4.0);
        assertEquals(exp.simplify((env)), exp);
    }
    
    @Test
    public void SumSimplify() {
        final Expression exp = new SumExpression(new Var("x"), new MultiplyExpression(new Var("y"), new Var("y")));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 4.0); 
        env.put("y", 3.0);
        assertEquals(exp.simplify(env), new Num(13));
    }
    
    @Test
    public void SumSimplify2() {
        final Expression exp = new SumExpression(new Var("x"), new MultiplyExpression(new Var("y"), new Var("z")));
        Map<String, Double> env = new HashMap<>();
        env.put("y", 2.0); 
        env.put("z", 5.0);
        assertEquals(exp.simplify(env), new SumExpression(new Var("x"), new Num(10)));
    }
    
    @Test
    public void Simplify3() {
        final Expression exp = new SumExpression(new Num(3), new Num(4));
        assertEquals(exp.simplify(new HashMap<String, Double>()), new Num(7));
    }
    
    
    @Test
    public void Simplify4() {
        final Expression exp = new MultiplyExpression(new Num(3), new Num(4));
        assertEquals(exp.simplify(new HashMap<String,Double>()), new Num(12));
    }
    
    @Test
    public void ProductSimplify() {
        final Expression exp = new MultiplyExpression(new Var("x"),new MultiplyExpression(new Var("y"), new Var("z")));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 4.0); 
        env.put("y", 3.0);
        env.put("z", 2.0);
        assertEquals(exp.simplify(env), new Num(24));
    }
    
    @Test
    public void Simplify() {
        final Expression exp = new SumExpression(new MultiplyExpression(new Num(4), new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")))), new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x"))));
        assertEquals(exp.simplify(new HashMap<>()), new MultiplyExpression(new Num(5), new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("x")))));
    }
    
    @Test
    public void Simplify2() {
        final Expression exp = new SumExpression(new MultiplyExpression(new Var("x"), new Var("x")), new MultiplyExpression(new Var("y"), new MultiplyExpression(new Var("x"), new Var("x"))));
        Map<String, Double> env = new HashMap<>();
        env.put("y", 4.0);
        assertEquals(exp.simplify(env), new MultiplyExpression(new Num(5), new MultiplyExpression(new Var("x"), new Var("x"))));
    }

    @Test
    public void ComplexSimplify1() {
        final Expression exp = new SumExpression(new MultiplyExpression(new Var("x"), new MultiplyExpression(new Var("x"), new Var("y"))), new MultiplyExpression(new Var("y"), new SumExpression(new Num(1), new Var("x"))));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 2.0);
        assertEquals(exp.simplify(env), new MultiplyExpression(new Num(7), new Var("y")));
    }
}
