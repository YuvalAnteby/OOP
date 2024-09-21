package binaryExpressions;

import values.Expression;
import values.Val;

/**
 * Class to represent the 'or' operand of logic.
 * string representations: Or(x, y) = (x ∨ y).
 * @author Yuval Anteby
 */
public class Or extends BinaryExpression {

    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public Or(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a || b;
    }

    @Override
    public String toString() {
        //TODO used this ascii char for automatic testing of BIU, might need to change before submitting.
        return "(" + getFirstExpression() + " | " + getSecondExpression() + ")";
        //return "(" + getFirstExpression() + " ∨ " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression firstExpression = new Nand(getFirstExpression().nandify(), getFirstExpression().nandify());
        Expression secondExpression = new Nand(getSecondExpression().nandify(), getSecondExpression().nandify());
        return new Nand(firstExpression, secondExpression);
    }

    @Override
    public Expression norify() {
        Expression firstExpression = new Nor(getFirstExpression().norify(), getSecondExpression().norify());
        Expression secondExpression = new Nor(getFirstExpression().norify(), getSecondExpression().norify());
        return new Nor(firstExpression, secondExpression);
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
        //Check x ∨ 1 = 1.
        if (firstSimplified.equals(Val.TRUE) || secondSimplified.equals(Val.TRUE)) {
            return Val.TRUE;
        }
        //Check each expression for x ∨ 0 = x.
        if (firstSimplified.equals(Val.FALSE)) {
            return secondSimplified;
        }
        if (secondSimplified.equals(Val.FALSE)) {
            return firstSimplified;
        }
        //Check if the expressions are equal x ∨ x = x.
        if (firstSimplified.equals(secondSimplified)) {
            return firstSimplified;
        }
        //Default return value is simplified expressions.
        return new Or(firstSimplified, secondSimplified);
    }
}
