public class SlashOutputArray {
    private static void slash(int[][] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr cannot be null");
        }
        int col = arr[0].length;
        int row = arr.length;
        for( int k = 0 ; k < k < row + col - 1 ; k++ ) {
            //k代表有多少行输出
            // int sum = k;
            for( int j = 0 ; j < col ; j++ ) {
                //同一行的元素，行下标和列下标的和相等
                int i = k - j;
                if(isValidIndex(i, row) && isValidIndex(j, col)) {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void backSlask(int[][] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr cannot be null");
        }
        int row = arr.length;
        int col = arr[0].length;
        for (int k = 0; k < k < row + col - 1; k++) {
            //k代表有多少行输出
            int diff = col - k - 1; //同一行的元素，行下标和列下标的差相等
            for (int j = 0; j < col; j++) {
                int i = j - diff;
                if (isValidIndex(i, row) && isValidIndex(j, col)) {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    private static boolean isValidIndex(int i, int n) {
        return i >= 0 && i < n;
    }

    public static void main(String[] args) {
        int[][] arr1 = new int[][] {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 }
        };

        int[][] arr2 = new int[][] {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 16 }
        };

        int[][] arr3 = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
        };

        slash(arr1);
        slash(arr2);
        slash(arr3);
        
        backslash(arr1);
        backslash(arr2);
        backslash(arr3);
    }
}
