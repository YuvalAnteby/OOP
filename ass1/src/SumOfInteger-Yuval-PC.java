/**
 * First task.
 */
public class SumOfInteger {
    /**
     * Main function of the task.
     *
     * @param args - Command line arguments.
     */
    public static void main(String[] args) {
        int minLen = 1, numIndex = 0, minusOne = -1;
        if (args.length < minLen) {
            System.out.print("Invalid input");
        } else {
            //Try to convert the string to integer, if failed print invalid input.
            try {
                int num = Integer.parseInt(args[numIndex]);
                //If the number is negative calculate the sum of digits as if they are positive
                if (num >= 0) {
                    System.out.print(sumOfIntegers(num));
                } else {
                    System.out.print(sumOfIntegers(minusOne * num));
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input");
            }
        }
    }

    /**
     * Function to calculate recursively a sum of digits of an integer.
     * Takes the rightmost digit then divide by 10 to get the next digit to be the rightmost.
     *
     * @param n - number we would like to check its sum of digits.
     * @return - sum of digits of an integer.
     */
    private static int sumOfIntegers(int n) {
        //Initialize constant variables
        int minNum = 0, nextDigit = 10, currentDigit = 10;
        //Check if we reached zero end the recursive function
        if (n == minNum) {
            return 0;
        }
        //If n is greater than zero than keep calculating the next digits
        return sumOfIntegers(n / nextDigit) + n % currentDigit;
    }
}