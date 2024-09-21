package binaryExpressions;

import unaryExpressions.Not;
import values.Expression;
import values.Val;

/**
 * Class to represent the 'nand' operand of logic.
 * string representations: Nand(x, y) = (x A y).
 * @author Yuval Anteby
 */
public class Nand extends BinaryExpression {

    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public Nand(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return !a || !b;
    }

    @Override
    public String toString() {
        return "(" + getFirstExpression() + " A " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        return new Nand(getFirstExpression().nandify(), getSecondExpression().nandify());
    }

    @Override
    public Expression norify() {
        Expression firstInnerExpression = new Nor(getFirstExpression().norify(), getFirstExpression().norify());
        Expression secondInnerExpression = new Nor(getSecondExpression().norify(), getSecondExpression().norify());
        return new Nor(
                new Nor(firstInnerExpression, secondInnerExpression),
                new Nor(firstInnerExpression, secondInnerExpression));
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
        //Check each expression for xA0 = 1.
        if (firstSimplified.equals(Val.FALSE) || secondSimplified.equals(Val.FALSE)) {
            return Val.TRUE;
        }
        //Check each expression for xA1 =∼ (x).
        if (firstSimplified.equals(Val.TRUE)) {
            return new Not(secondSimplified).simplify();
        }
        if (secondSimplified.equals(Val.TRUE)) {
            return new Not(firstSimplified).simplify();
        }
        //Check if the expressions are equal xAx =∼ (x).
        if (firstSimplified.equals(secondSimplified)) {
            return new Not(firstSimplified).simplify();
        }
        //Default return value is simplified expressions.
        return new Nand(firstSimplified, secondSimplified);
    }
}
