package binaryExpressions;

import values.Expression;
import values.Val;

/**
 * Class to represent the 'xnor' operand of logic.
 * string representations: Xnor(x, y) = (x # y).
 * @author Yuval Anteby
 */
public class Xnor extends BinaryExpression {

    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public Xnor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a == b;
    }

    @Override
    public String toString() {
        return "(" + getFirstExpression() + " # " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression firstExpressionNandified = getFirstExpression().nandify();
        Expression secondExpressionNandified = getSecondExpression().nandify();
        Expression firstInnerExpression = new Nand(firstExpressionNandified, firstExpressionNandified);
        Expression secondInnerExpression = new Nand(secondExpressionNandified, secondExpressionNandified);
        return new Nand(
                new Nand(firstInnerExpression, secondInnerExpression),
                new Nand(firstExpressionNandified, secondExpressionNandified));
    }

    @Override
    public Expression norify() {
        Expression firstExpressionNorified = getFirstExpression().norify();
        Expression secondExpressionNorified = getSecondExpression().norify();

        Expression innerExpression = new Nor(firstExpressionNorified, secondExpressionNorified);
        return new Nor(
                new Nor(firstExpressionNorified, innerExpression),
                new Nor(secondExpressionNorified, innerExpression));
    }

    @Override
    public Expression simplify() {
        //Simplify and check if the result is a value of true or false.
        Expression simplified = super.simplify();
        if (simplified.equals(Val.TRUE) || simplified.equals(Val.FALSE)) {
            return this;
        }
        //Simplify the expressions.
        Expression firstSimplified = getFirstSimplified();
        Expression secondSimplified = getSecondSimplified();
        //Check if the expressions are equal x#x = 1.
        if (firstSimplified.equals(secondSimplified)) {
            return Val.TRUE;
        }
        //Default return value is simplified expressions.
        return new Xnor(firstSimplified, secondSimplified);
    }
}
