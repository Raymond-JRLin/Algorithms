/*

inspired: https://medium.com/@alexgolec/google-interview-questions-deconstructed-the-knights-dialer-f780d516f029

Question:

Imagine you place a knight chess piece on a phone dial pad. This chess piece moves in an uppercase “L” shape: two steps horizontally followed by one vertically, or one step horizontally then two vertically.

Suppose you dial keys on the keypad using only hops a knight can make. Every time the knight lands on a key, we dial that key and make another hop. The starting position counts as being dialed.

How many distinct numbers can you dial in N hops from a particular starting position?

*/

import java.util.*;

public class knightsDialer {
    public static void main(String[] args) {
        long t0 = System.nanoTime();

        // System.out.println("method1 answer: " + method1(6, 20));
        long t1 = System.nanoTime();
        System.out.println("method1 time: " + (t1 - t0));

        System.out.println("method2 answer: " + method2(6, 20));
        long t2 = System.nanoTime();
        System.out.println("method2 time: " + (t2 - t1));


        System.out.println("method3 answer: " + method3(6, 20));
        long t3 = System.nanoTime();
        System.out.println("method3 time: " + (t3 - t2));

        System.out.println("method4 answer: " + method4(6, 20));
        long t4 = System.nanoTime();
        System.out.println("method4 time: " + (t4 - t3));
    }

    private static int method4(int start, int hops) {
        // bottom-up iteration DP
        // definition
        int[] prev = new int[10];
        int[] curr = new int[10];
        // initialization
        Arrays.fill(prev, 1); // 每个不需要 hop 的时候基本情况就是 1 种选择， 即它自身
        // DP
        for (int hop = 0; hop < hops; hop++) {
            // loop hops
            curr = new int[10];
            for (int pos = 0; pos < 10; pos++) {
                // 对每个按键， 分别出发去下一个 neighbor
                for (int next : getNeighbors(pos)) {
                    curr[next] += prev[pos];
                }
            }
            // update
            prev = curr;
        }
        // result
        return curr[start];
    }

    private static int method3(int start, int hops) {
        // 会发现有很多重复计算， 一个数可以从多个上一个数过来， 那么都会再次计算 => memorization
        // Linear time, 这个实际蛮难去算多少次 recursion, 但是我们可以看 memo cache， we can conclude that the cache grows in direct proportion to the number of requested hops， 最多就是 10 * hops 存入， 之后都可以 hit cache， 所以是 linear time
        // 依然不是最优的， 其中一个原因就是用的是 recursion 的方法， 当 hops 过大的时候不好， 由此引出 method4 用 iteration DP 来做
        int[][] memo = new int[10][hops + 1];
        return recursion(start, hops, memo);
    }
    private static int recursion(int start, int hops, int[][] memo) {
        if (hops == 0) {
            return 1;
        }
        if (memo[start][hops] != 0) {
            return memo[start][hops];
        }
        int result = 0;
        for (int next : getNeighbors(start)) {
            result += method2(next, hops - 1);
        }
        return memo[start][hops] = result;
    }

    private static int method2(int start, int hops) {
        // 只需要返回可能的数， 并不需要每一种可能的排列， 所以可以考虑计算个数
        // 当前的 hop 种类个数 = SUM {当前 pos 的 neighbors 分别 hop 的个数}
        // exponential time, 每个 method2 最多调两次 method2， 因为每个 digit 差不多有 2 个 数可以 hop 过去
        if (hops == 0) {
            return 1;
        }
        int result = 0;
        for (int next : getNeighbors(start)) {
            result += method2(next, hops - 1);
        }
        return result;
    }

    private static int method1(int start, int hops) {
        // DFS to get all possible results
        if (hops < 1) {
            return 1;
        }
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), start, hops);
        // # possibilities
        return result.size();
    }
    private static void dfs(List<List<Integer>> result, List<Integer> list, int start, int hops) {
        if (list.size() == 0) {
            list.add(start);
        }
        if (hops == 0) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int next : getNeighbors(start)) {
            list.add(next);
            dfs(result, list, next, hops - 1);
            list.remove(list.size() - 1);
        }
    }

    private static List<Integer> getNeighbors(int pos) {
        if (pos == 0) {
            return Arrays.asList(4, 6);
        } else if (pos == 1) {
            return Arrays.asList(6, 8);
        } else if (pos == 2) {
            return Arrays.asList(7, 9);
        } else if (pos == 3) {
            return Arrays.asList(4, 8);
        } else if (pos == 4) {
            return Arrays.asList(3, 9, 0);
        } else if (pos == 5) {
            return new ArrayList<Integer>();
        } else if (pos == 6) {
            return Arrays.asList(1, 7, 0);
        } else if (pos == 7) {
            return Arrays.asList(2, 6);
        } else if (pos == 8) {
            return Arrays.asList(1, 3);
        } else {
            return Arrays.asList(2, 4);
        }
    }
}
