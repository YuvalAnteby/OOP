package unaryExpressions;

import binaryExpressions.Nand;
import binaryExpressions.Nor;
import values.Expression;
import values.Val;

/**
 * Class to represent the 'not' operand of logic.
 * string representations: Not(x) =~ (x).
 * @author Yuval Anteby
 */
public class Not extends UnaryExpression {

    /**
     * Default constructor.
     *
     * @param expression  an operand.
     */
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public boolean apply(boolean a) {
        return !a;
    }

    @Override
    public String toString() {
        return "~(" + getExpression() + ")";
    }

    @Override
    public Expression nandify() {
        Expression expressionNandified = getExpression().nandify();
        return new Nand(expressionNandified, expressionNandified);
    }

    @Override
    public Expression norify() {
        Expression expressionNorified = getExpression().norify();
        return new Nor(expressionNorified, expressionNorified);
    }

    @Override
    public Expression simplify() {
        //Simplify and check if the result is a value of true or false.
        Expression simplified = super.simplify();
        if (simplified.equals(Val.TRUE) || simplified.equals(Val.FALSE)) {
            return this;
        }
        //Simplify the expression.
        Expression simplifiedExpression = getExpression().simplify();
        //Check for the original value True becomes false and vice versa.
        if (simplifiedExpression.equals(Val.FALSE)) {
            return Val.TRUE;
        }
        if (simplifiedExpression.equals(Val.TRUE)) {
            return Val.FALSE;
        }
        //Default return value is simplified expressions.
        return new Not(simplifiedExpression);
    }
}
