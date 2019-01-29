public class Solution {
    /**
     * @param nums an array of integer
     * @return An integer: minimum different of 2 subset
     */
    public int minDiff(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // return method1(nums);

        return method2(nums);
    }

    private int method2(int[] nums) {
        // DP
        int n = nums.length;
        // Calculate sum of all elements
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // Create an array to store results of subproblems
        // definition: dp[i][j] = 是否能用前 i 个 num 组成 sum = j
        boolean dp[][] = new boolean[n + 1][sum + 1];

        // initialization
        // 1st col: 0 sum is possible with all elements.
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        // Initialize top row, except dp[0][0],
        // as false. With 0 elements, no other sum except 0 is possible
        for (int i = 1; i <= sum; i++) {
            dp[0][i] = false;
        }

        // DP in bottom up manner 
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                // If i'th element is excluded
                dp[i][j] = dp[i - 1][j];
                // If i'th element is included
                if (nums[i - 1] <= j)
                    dp[i][j] |= dp[i - 1][j - nums[i - 1]];
            }
        }

        // result
        // Initialize difference of two sums.
        int diff = Integer.MAX_VALUE;

        // Find the largest j such that dp[n][j]
        // is true where j loops from sum/2 t0 0
        for (int j = sum / 2; j >= 0; j--) {
            // Find the
            if (dp[n][j] == true) {
                diff = sum - 2 * j;
                break;
            }
        }
        return diff;
    }

    private int method1(int[] nums) {
        // recursively check if we put nums[i] into say 1st subset
        // 每一个 nums[i] 都有两种选择， 加入 subset 1 或者不加入， time complexity will be 2 * 2 *..... * 2 (For n times), that is O(2 ^ n).
        int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // Compute result using recursive function
        return recursion(nums, n, 0, sum);
    }
    private int recursion(int[] nums, int n, int curr, int sum) {
        // curr is 1st subset sum
        if (n == 0) {
            // reach the 1st position, get the difference
            return Math.abs((sum - curr) - curr);
        }
        // min {include nums[i - 1] to 1 set, not include nums[i - 1] to 1 set}
        return Math.min(recursion(nums, n - 1, curr + nums[n - 1], sum),
                        recursion(nums, n - 1, curr, sum));
    }
}
