
import binaryExpressions.And;
import binaryExpressions.Nand;
import binaryExpressions.Or;
import binaryExpressions.Xor;
import binaryExpressions.Xnor;
import unaryExpressions.Not;
import values.Expression;
import values.Val;
import values.Var;

import java.util.HashMap;

/**
 * Class containing the main function, meant for testing expressions.
 */
public class ExpressionsTest {

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

        System.out.println(ex.toString().equals("((x A F) # ~(((x | y) & (T ^ z))))"));

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("y", false);
        map.put("z", false);

        try {
            System.out.println(!ex.evaluate(map));
        } catch (Exception ignored) {
        }
        //distribute the string to prevent line being longer than 120 chars.
        System.out.println(ex.nandify().toString().equals("((((x A F) A (x A F)) A ((((((x A x) A (y A y)) A "
                + "((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z))))) A "
                + "((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) "
                + "A (z A (T A z)))))) A (((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A "
                + "(y A y)) A ((T A (T A z)) A (z A (T A z))))) A ((((x A x) A (y A y)) A ((T A (T A z)) A "
                + "(z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))))))) A ((x A F) A "
                + "(((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) A ((T A (T A z)) A "
                + "(z A (T A z))))) A ((((x A x) A (y A y)) A ((T A (T A z)) A (z A (T A z)))) A (((x A x) A (y A y)) "
                + "A ((T A (T A z)) A (z A (T A z))))))))"));
        System.out.println(ex.norify().toString().equals("(((((x V x) V (F V F)) V ((x V x) V (F V F))) V ((((x V x) "
                + "V (F V F)) V ((x V x) V (F V F))) V (((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((((T V T) "
                + "V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z)))) V ((((x V y) V (x V y)) V ((x V y) "
                + "V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V (T V z))))))) V ((((((x V y)"
                + " V (x V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V "
                + "(T V z)))) V ((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V "
                + "(((T V T) V (z V z)) V (T V z))))) V ((((x V x) V (F V F)) V ((x V x) V (F V F))) V (((((x V y) V "
                + "(x V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V (((T V T) V (z V z)) V "
                + "(T V z)))) V ((((x V y) V (x V y)) V ((x V y) V (x V y))) V ((((T V T) V (z V z)) V (T V z)) V "
                + "(((T V T) V (z V z)) V (T V z))))))))"));
        System.out.println(ex.simplify().toString().equals("(T # ~(((x | y) & ~(z))))"));
    }
}
