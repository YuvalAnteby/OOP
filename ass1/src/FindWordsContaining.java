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
        int minElements = 3, firstChar = 0, maxLen = 1;
        String lastString = args[args.length - 1];
        char lastChar = lastString.charAt(firstChar);
        //if the string isn't alphabetical or the amount of elements is less than 3 the input is invalid.
        if (args.length <= minElements || lastString.length() > maxLen || !isAlpha(lastChar)) {
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
     * Function to check if a given char is alphabetical or not.
     *
     * @param c - char to be checked.
     * @return - return true if the char is alphabetical, false if the char is a symbol or number.
     */
    private static boolean isAlpha(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        }
        return false;
    }

    /**
     * Function to print all the strings containing a string of length 1 (char).
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
