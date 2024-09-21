package values;

import java.util.HashMap;

/**
 * Abstract class, Binary and Unary expressions classes inherit from it.
 * @author Yuval Anteby
 */
public abstract class BaseExpression implements Expression {

    @Override
    public Boolean evaluate() throws Exception {
        return evaluate(new HashMap<>());
    }

}
