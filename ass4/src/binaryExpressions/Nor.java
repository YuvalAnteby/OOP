package binaryExpressions;

import unaryExpressions.Not;
import values.Expression;
import values.Val;

/**
 * Class to represent the 'nor' operand of logic.
 * string representations: Nor(x, y) = (x V y).
 * @author Yuval Anteby
 */
public class Nor extends BinaryExpression {

    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public Nor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return !a && !b;
    }

    @Override
    public String toString() {
        return "(" + getFirstExpression() + " V " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression firstInnerExpression = new Nand(getFirstExpression().nandify(), getFirstExpression().nandify());
        Expression secondInnerExpression = new Nand(getSecondExpression().nandify(), getSecondExpression().nandify());
        return new Nand(
                new Nand(firstInnerExpression, secondInnerExpression),
                new Nand(firstInnerExpression, secondInnerExpression));
    }

    @Override
    public Expression norify() {
        return new Nor(getFirstExpression().norify(), getSecondExpression().norify());
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
        //Check each expression for xV 1 = 0.
        if (firstSimplified.equals(Val.TRUE) || secondSimplified.equals(Val.TRUE)) {
            return Val.FALSE;
        }
        //Check each expression for xV 0 =∼ (x).
        if (firstSimplified.equals(Val.FALSE)) {
            return new Not(secondSimplified).simplify();
        }
        if (secondSimplified.equals(Val.FALSE)) {
            return new Not(firstSimplified).simplify();
        }
        //Check if the expressions are equal xV x =∼ (x).
        if (firstSimplified.equals(secondSimplified)) {
            return new Not(firstSimplified).simplify();
        }
        //Default return value is simplified expressions.
        return new Nor(firstSimplified, secondSimplified);
    }
}
