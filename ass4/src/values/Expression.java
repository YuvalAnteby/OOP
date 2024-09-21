package values;
import java.util.List;
import java.util.Map;

/**
 * Interface for all logical expressions functions.
 * @author Yuval Anteby
 */
public interface Expression {

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment mapping between variables and their values.
     * @return           the value of the expression.
     * @throws Exception when the expression cannot be evaluated.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method. Like the `evaluate(assignment)` method above, but uses an empty assignment.
     *
     * @return the value of the expression.
     * @throws Exception when the expression cannot be evaluated.
     */
    Boolean evaluate() throws Exception;

    /**
     * Getter for variables.
     * @return a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * @return a nice string representation of the expression.
     */
    String toString();

    /**
     * @param var        the variable to assign.
     * @param expression the expression to replace var with.
     * @return a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the current expression).
     */
    Expression assign(String var, Expression expression);

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nand operation.
     */
    Expression nandify();

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    Expression norify();

    /**
     * @return a simplified version of the current expression.
     */
    Expression simplify();
}
