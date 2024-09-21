package binaryExpressions;

import values.BaseExpression;
import values.Expression;
import values.Val;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * Abstract class, operands needing two expressions will inherit from it.
 * @author Yuval Anteby
 */
public abstract class BinaryExpression extends BaseExpression {
    private final Expression expression1;
    private final Expression expression2;

    /**
     * Default constructor.
     * @param expression1 first logic operand.
     * @param expression2 second logic operand.
     */
    public BinaryExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    /**
     * @return the first operand.
     */
    public Expression getFirstExpression() {
        return this.expression1;
    }

    /**
     * @return the second operand.
     */
    public Expression getSecondExpression() {
        return this.expression2;
    }

    /**
     * @return the first operand simplified.
     */
    protected Expression getFirstSimplified() {
        return this.expression1.simplify();
    }

    /**
     * @return the second operand simplified.
     */
    protected Expression getSecondSimplified() {
        return this.expression2.simplify();
    }

    /**
     * Applying the operator on the operands.
     *
     * @param first operand.
     * @param second operand.
     * @return the applied value according to operand.
     */
    public abstract boolean apply(boolean first, boolean second);

    @Override
    public List<String> getVariables() {
        Set<String> variables = new HashSet<>();
        variables.addAll(this.expression1.getVariables());
        variables.addAll(this.expression2.getVariables());
        return new ArrayList<>(variables);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        try {
            return this.getClass().getConstructor(Expression.class, Expression.class)
                    .newInstance(this.expression1.assign(var, expression), this.expression2.assign(var, expression));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return apply(this.expression1.evaluate(assignment), this.expression2.evaluate(assignment));
    }

    @Override
    public Expression simplify() {
        try {
            return new Val(evaluate());
        } catch (Exception e) {
            return this;
        }
    }

    @Override
    public int hashCode() {
        int result = expression1.hashCode();
        result = 31 * result + expression2.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!this.getClass().isAssignableFrom(obj.getClass()) && !obj.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }
        BinaryExpression other = (BinaryExpression) obj;
        return (expression1.equals(other.expression1) && expression2.equals(other.expression2))
                || (expression2.equals(other.expression1) && expression1.equals(other.expression2));
    }
}
