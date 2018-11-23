/*
giving a expressive word: hellloooo
when a letter repeated >= three times it's considered a word extension. eg:lll and oooo
return the start and end index of the word extension
input string: hellloooo return (2,4) (5,8)
return 一个arrayList
*/

import java.util.*;

public class WordExtension {
    private static List<int[]> method1(String s) {
        List<int[]> result = new ArrayList<>();
        int left = 0;
        for (int i = 1; i < s.length(); i++) {
            while (i < s.length() && s.charAt(i) == s.charAt(left)) {
                i++;
            }
            if (i - left > 2) {
                result.add(new int[]{left, i - 1});
                i--;
            }
            left = i;
        }
        return result;
    }
    private static void printIntList(List<int[]> list) {
        for (int[] nums : list) {
            System.out.print("(" + nums[0] + ", " + nums[1] + ") ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String s1 = "hellloooo";
        String s2 = "aaaaaa";
        String s3 = "aabbbccdddeeeffff";
        printIntList(method1(s1)); // (2,4) (5,8)
        printIntList(method1(s2)); // (0, 5)
        printIntList(method1(s2)); // (2, 4) (7, 9) (10, 12) (13, 16)
    }
}
