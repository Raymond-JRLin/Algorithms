// Same problem with LintCode 1635. Max Pair, but return all qualified pairs including duplicates

import java.util.*;

public class AllMaxPairs {

    private static int[][] mytry(int[] a, int[] b, int x) {
        // 和 MAX Pair 的做法一样， 只是记录下 b[] 中每个值的个数， 在加入答案的时候分别乘上这些个数
        Map<Integer, Integer> counts = new HashMap<>(); // <b[i], # b[i]>
        for (int num : b) {
           counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        // 2 pointers
        Arrays.sort(a);
        Arrays.sort(b);
        int max = 0;
        List<int[]> list = new ArrayList<>();
        int i = 0; // go a[] from left to right
        int j = b.length - 1; // go b[] from right to left
        while (i < a.length && j >= 0) {
            int curr = a[i] + b[j];
            if (curr > x) {
               // sum 太大了
               j--;
            } else {
                // sum 能够满足条件， 但是注意本题重复答案不算， 需要去重， 所以要注意查看 sum 和 当前最大值之间的关系
                if (curr >= max) {
                    // 只有当 sum 大于或等于当前最大值的时候， 才有可能加入当前 pair
                    if (curr > max) {
                        // 当 sum 大于当前最大值的时候， 抹掉之前的答案， 并更新最大值
                        list = new ArrayList<>();
                        max = curr;
                    }
                    // b 中可能有多个， 要全部加入为当前的 pair
                    for (int k = 0; k < counts.get(b[j]); k++) {
                        list.add(new int[]{a[i], b[j]});
                    }

                }
                // 无论怎样， i 向右移动， 因为当前 sum 比 x 来的小， 我们去找更大一些的 pair
                i++;
            }
        }
        int[][] result = new int[list.size()][2];
        for (int k = 0; k < list.size(); k++) {
            result[k] = list.get(k);
        }
        return result;
    }

    private static void printInt2DArr(int[][] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String []args){
       int[] a = {6,5,4,5,6,7,8};
       int[] b = {4,3,4,2,1,9,1, 3};
       int x = 10;
       int[][] result = mytry(a, b, x);
       printInt2DArr(result);
       // 6 4
       // 6 4
       // 6 4
       // 6 4
       // 7 3
       // 7 3
       // 8 2
    }
}
