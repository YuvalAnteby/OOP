package binaryExpressions;

import unaryExpressions.Not;
import values.Expression;
import values.Val;

/**
 * Class to represent the 'xor' operand of logic.
 * string representations: Xor(x, y) = (x ⊕ y).
 * @author Yuval Anteby
 */
public class Xor extends BinaryExpression {


    /**
     * Default constructor.
     *
     * @param expression1  an operand.
     * @param expression2  an operand.
     */
    public Xor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a ^ b;
    }

    @Override
    public String toString() {
        //TODO used this ascii char for automatic testing of BIU, might need to change before submitting.
        return "(" + getFirstExpression() + " ^ " + getSecondExpression() + ")";
        //return "(" + getFirstExpression() + " ⊕ " + getSecondExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression firstExpressionNandified = getFirstExpression().nandify();
        Expression secondExpressionNandified = getSecondExpression().nandify();
        Expression innerExpression = new Nand(firstExpressionNandified, secondExpressionNandified);
        return new Nand(
                new Nand(firstExpressionNandified, innerExpression),
                new Nand(secondExpressionNandified, innerExpression));
    }

    @Override
    public Expression norify() {
        Expression firstExpressionNorified = getFirstExpression().norify();
        Expression secondExpressionNorified = getSecondExpression().norify();

        Expression firstInnerExpression = new Nor(firstExpressionNorified, firstExpressionNorified);
        Expression secondInnerExpression = new Nor(secondExpressionNorified, secondExpressionNorified);
        return new Nor(
                new Nor(firstInnerExpression, secondInnerExpression),
                new Nor(firstExpressionNorified, secondExpressionNorified));
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
        //Check each expression for x ⊕ 1 =∼ (x).
        if (firstSimplified.equals(Val.TRUE)) {
            return new Not(secondSimplified).simplify();
        }
        if (secondSimplified.equals(Val.TRUE)) {
            return new Not(firstSimplified).simplify();
        }
        //Check each expression for x ⊕ 0 = x/
        if (firstSimplified.equals(Val.FALSE)) {
            return secondSimplified;
        }
        if (secondSimplified.equals(Val.FALSE)) {
            return firstSimplified;
        }
        //Check if the expressions are equal. x ⊕ x = 0
        if (firstSimplified.equals(secondSimplified)) {
            return Val.FALSE;
        }
        //Default return value is simplified expressions.
        return new Xor(firstSimplified, secondSimplified);
    }
}
