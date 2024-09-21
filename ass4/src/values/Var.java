package values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to represent a variable for logic operations.
 * @author Yuval Anteby
 */
public class Var implements Expression {
    private final String variable;

    /**
     * Default constructor.
     *
     * @param variable the variable's name.
     */
    public Var(String variable) {
        this.variable = variable.toLowerCase();
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (assignment.containsKey(this.variable)) {
            return assignment.get(this.variable);
        }
        throw new Exception(this.variable + "isn't set in the assigment");
    }

    @Override
    public Boolean evaluate() throws Exception {
        return evaluate(new HashMap<>());
    }

    @Override
    public List<String> getVariables() {
        List<String> variableList = new ArrayList<>();
        variableList.add(this.variable);
        return variableList;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (var.equalsIgnoreCase(variable)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return this.variable;
    }


    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Var.class) {
            return false;
        }
        return ((Var) obj).variable.equalsIgnoreCase(this.variable);
    }
}
