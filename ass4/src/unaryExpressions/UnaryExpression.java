package unaryExpressions;

import values.BaseExpression;
import values.Expression;
import values.Val;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;


/**
 * Abstract class, operands needing one expression will inherit from it.
 * @author Yuval Anteby
 */
public abstract class UnaryExpression extends BaseExpression {
    private final Expression expression;

    /**
     * Default constructor.
     * @param expression logic operand.
     */
    protected UnaryExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * @return the operand.
     */
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * Applying the operator on the operand.
     *
     * @param first operand.
     * @return the applied value according to operand.
     */
    public abstract boolean apply(boolean first);

    @Override
    public List<String> getVariables() {
        Set<String> variables = new HashSet<>(this.expression.getVariables());
        return new ArrayList<>(variables);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        try {
            return this.getClass().getConstructor(Expression.class)
                    .newInstance(this.expression.assign(var, expression));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return apply(this.expression.evaluate(assignment));
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
        return expression.hashCode();
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
        UnaryExpression other = (UnaryExpression) obj;
        return expression.equals(other.expression);
    }
}
