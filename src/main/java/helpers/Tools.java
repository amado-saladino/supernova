package helpers;

import java.util.Random;

public class Tools {
    private static Random random = new Random();

    /**
     * Takes an entry and generate random keyword
     * from this entry
     * @param entry
     * @return keyword
     */
    public static String generateKeyword(String entry) {
        int length = entry.length();
        int startIndex, endIndex;
        String keyword;

        while (true) {
            startIndex = random.nextInt(length -1) + 0;
            endIndex = random.nextInt(length -1) + 0;

            if (endIndex - startIndex >= 5) {
                keyword = entry.substring(startIndex, endIndex);
                break;
            }
        }
        return keyword;
    }
}
