/*
Given an array of n elements which contains elements from 0 to n-1, with any of these numbers appearing any number of times. Find these repeating numbers in O(n) and using only constant memory space.
*/

public class DuplicatesInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 1, 3, 6, 6, 0};
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[nums[i] % n] += n;
        }
        System.out.println("After changing, array is: ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            if (nums[i] / n > 1) {
                // 出现了多个数
                // 如果要找 unique 就把条件变为 nums[i] / n == 1
                System.out.print(i + " ");
            }
        }
    }

}
