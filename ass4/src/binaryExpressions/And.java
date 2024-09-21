package binaryExpressions;

import values.Expression;
import values.Val;

/**
 * Class to represent the 'and' operand of logic.
 * string representations: And(x, y) = (x ∧ y).
 * @author Yuval Anteby
 */
public class And extends BinaryExpression {

    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public And(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a && b;
    }

    @Override
    public String toString() {
        //TODO used this ascii char for automatic testing of BIU, might need to change before submitting.
        return "(" + getFirstExpression() + " & " + getSecondExpression() + ")";
        //return "(" + getFirstExpression() + " ∧ " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression firstInnerExpression =  new Nand(getFirstExpression().nandify(), getSecondExpression().nandify());
        return new Nand(firstInnerExpression, firstInnerExpression);
    }

    @Override
    public Expression norify() {
        Expression firstInnerExpression =  new Nor(getFirstExpression().norify(), getFirstExpression().norify());
        Expression secondInnerExpression =  new Nor(getSecondExpression().norify(), getSecondExpression().norify());
        return new Nor(firstInnerExpression, secondInnerExpression);
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
        //For each expression check x AND 1 = x.
        if (firstSimplified.equals(Val.TRUE)) {
            return secondSimplified;
        }
        if (secondSimplified.equals(Val.TRUE)) {
            return firstSimplified;
        }
        //For each expression check x AND 0 = 0.
        if (firstSimplified.equals(Val.FALSE) || secondSimplified.equals(Val.FALSE)) {
            return Val.FALSE;
        }
        //Check if expressions are equal x ∧ x = x.
        if (firstSimplified.equals(secondSimplified)) {
            return firstSimplified;
        }
        //Default return value is simplified expressions.
        return new And(firstSimplified, secondSimplified);
    }
}
