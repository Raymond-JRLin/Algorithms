// Michelle has created a word game for her students. The word game begins with Michelle writing a string and a number, K, on the board. The students have to find the substrings of size K with K distinct characters.
// Write and algorthim to help the students find the correct answer. If the given string does not have K distinct characters then output an empty list.
//
// Input
// The input to the function/method consists of two arguments -
// inputStr, representing the string wrtten by the racher
// num, an integer representing the number, K, written by the teacher on the board
//
// Output
// Return distinct substrings of input string of size K with K distinct characters.
//
// Constraints
// 0 <= num <= 26
//
// Note
// The input string consists of only lowercase letters of the English alphabet
// Substrings are not necessarily distinct
//
// Examples
// Input
// inputStr = awaglknagawunagwkwagl
// num = 4
//
// Output
// {wagl, aglk, glkn, lkna, knag, gawu, awun, wuna, unag, nagw, agwk, kwag}


import java.util.List;

public class Solution {
    // method signature begins, this method is required
    public List<String> subStringKDist(String inputStr, int num) {
        // write your code here
        if (inputStr == null || inputStr.length() < num) {
            return Collections.emptyList();
        }

        int n = inputStr.length();
        Set<String> result = new HashSet<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < n - num + 1; i++) {
            int j = 0;
            while (j < num) {
                if (!set.add(inputStr.charAt(i + j))) {
                    break;
                }
                j++;
            }
            if (set.size() == num) {
                result.add(inputStr.substring(i, i + j));
            }
            set.clear(); // 如果没有这句， 或者把创建 set 的语句放在 for loop 内部， 则会 MLE
        }
        return new ArrayList<>(result);
    }
}
