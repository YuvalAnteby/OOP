package values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to represent values of logic operations (true and false).
 * @author Yuval Anteby
 */
public class Val implements Expression {
    //Value constant variables.
    public static final Val TRUE = new Val(true);
    public static final Val FALSE = new Val(false);

    private boolean value;

    /**
     * Default constructor.
     * @param value boolean value.
     */
    public Val(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.value;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return evaluate(new HashMap<>());
    }

    @Override
    public List<String> getVariables() {
        return new ArrayList<>();
    }

    @Override
    public Expression assign(String var, Expression expression) {
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
        return value ? "T" : "F";
    }

    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Val.class) {
            return false;
        }
        return ((Val) obj).value == this.value;
    }
}
