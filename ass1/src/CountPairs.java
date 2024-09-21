/**
 * Second task.
 */
public class CountPairs {
    /**
     * Main function.
     * @param args - Command line arguments.
     */
    public static void main(String[] args) {
        final int MIN_LIST_SIZE = 2;
        if (args.length < MIN_LIST_SIZE) {
            System.out.println("Invalid input");
        } else {
            //Convert the string elements in args to int and put them in a new array, then start counting
            int []arr = new int[args.length];
            for (int i = 0; i < args.length; i++){
                arr[i] = Integer.parseInt(args[i]);
            }
            printPairs(arr, arr[arr.length - 1]);
        }

    }

    /**
     * Function to count the pairs of numbers that are smaller than the last number.
     * The function will loop for each element in the array and check its sum with the other elements, increment the
     * counter if the pair's sum is less than the last element.
     * @param arr - the array of integers for checking pairs.
     * @param lastNum - the last element in the array.
     */
    private static void printPairs(int[]arr, int lastNum){
        final int ONE = 1;
        int count = 0;
        //The outer loop will end at the element before the last one since it's used for checking the pairs
        for (int i = 0; i < arr.length - ONE; i++){
            //The inner for starts at i+1 since we already checked the elements before that
            for (int j = i+1; j < arr.length - ONE; j++){
                if ((arr[i] + arr[j] < lastNum) && (i!=j))
                    count++;
            }
        }
        //Print the final value of the counter (the amount of pairs)
        System.out.print(count);
    }
}
