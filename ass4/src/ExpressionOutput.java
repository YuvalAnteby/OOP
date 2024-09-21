import java.util.HashMap;
import binaryExpressions.And;
import binaryExpressions.Nand;
import binaryExpressions.Or;
import binaryExpressions.Xor;
import binaryExpressions.Xnor;
import unaryExpressions.Not;
import values.Expression;
import values.Val;
import values.Var;

/**
 * Class with main to test the Expression classes.
 */
public class ExpressionOutput {

    /**
     * Main which runs code.
     * @param args relevant
     */
    public static void main(String[] args) throws Exception {
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");
        Expression ex = new Xnor(new Nand(x, new Val(false)),
                new Not(new And(new Or(x, y),
                        new Xor(new Val(true), z))));

        System.out.println(ex.toString());

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("y", false);
        map.put("z", false);

        try {
            System.out.println(ex.evaluate(map));
        } catch (Exception ignored) {
        }

        System.out.println(ex.nandify().toString());
        System.out.println(ex.norify().toString());
        System.out.println(ex.simplify().toString());
    }
}