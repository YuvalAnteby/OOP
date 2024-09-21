/**
 * Third task.
 */
public class FindWordsContaining {
    /**
     * Main function.
     *
     * @param args - Command line arguments.
     */
    public static void main(String[] args) {
        int minElements = 2, maxLen = 1;
        String lastString = args[args.length - 1];
        //if the last string isn't a char or the amount of elements is less than 2 then input is invalid.
        if (args.length <= minElements || lastString.length() > maxLen) {
            System.out.print("Invalid input");
        } else {
            //Create a new array for the words from the args array
            String[] words = new String[args.length - 1];
            for (int i = 0; i < args.length - 1; i++) {
                words[i] = args[i];
            }
            printWords(words, args[args.length - 1]);
        }
    }

    /**
     * Function to print all the strings containing a string of length 1 (char).
     * FYI if there isn't a string containing the char then print the empty string.
     *
     * @param words - array of strings.
     * @param c     - the string (char) to be checked.
     */
    private static void printWords(String[] words, String c) {
        //Loop through all the strings in the array and check if each of them contains the string c.
        for (String word : words) {
            //If the string contains c then print this string.
            if (word.contains(c)) {
                System.out.print(word + "\n");
            }
        }
    }
}
