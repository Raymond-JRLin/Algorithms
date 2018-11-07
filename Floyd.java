/*
Floyd 算法又称为插点法，是一种用于寻找给定的加权图中多源最短路径的算法, 算出来的是所有的节点到其余各节点之间的最短距离。

Floyd算法是一个经典的动态规划算法

从任意节点i到任意节点j的最短路径不外乎2种可能，1是直接从i到j，2是从i经过若干个节点k到j。所以，我们假设Dis(i,j)为节点u到节点v的最短路径的距离，对于每一个节点k，我们检查Dis(i,k) + Dis(k,j) < Dis(i,j)是否成立，如果成立，证明从i到k再到j的路径比i直接到j的路径短，我们便设置Dis(i,j) = Dis(i,k) + Dis(k,j)，这样一来，当我们遍历完所有节点k，Dis(i,j)中记录的便是i到j的最短路径的距离。
*/

public class Floyd {
    public static void main(String[] args) {
        final int MAX = Integer.MAX_VALUE;
        int[][] matrix = new int[][] {{0, 12, MAX, MAX, MAX,16, 14},
                                      {12, 0, 10, MAX, MAX, 7, MAX},
                                      {MAX, 10, 0, 3, 5, 6, MAX},
                                      {MAX, MAX, 3, 0, 4, MAX, MAX},
                                      {MAX, MAX, 5, 4, 0, 2, 8},
                                      {16, 7, 6, MAX, 2, 0, 9},
                                      {14, MAX, MAX, MAX, 8, 9, 0},
                                  };
        floyd(matrix);
    }

    private static void floyd(int[][] matrix) {
        // O(N ^ 3) time and O(N ^ 2) space
        // ref: https://www.cnblogs.com/skywang12345/p/3711532.html
        // ref: https://blog.csdn.net/oChangWen/article/details/50730937
        // ref: https://blog.csdn.net/sinat_22013331/article/details/51000331
        // ref: http://blog.51cto.com/wangyq2013/1866915
        // ref: http://developer.51cto.com/art/201403/433874.htm
        int n = matrix.length;
        int[][] dist = new int[n][n];
        int[][] path = new int[n][n];
        // intialization
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = matrix[i][j]; // "顶点 i " 到 "顶点 j " 的路径长度为 " i 到 j 的权值 "
                path[i][j] = j; // "顶点 i " 到 " 顶点 j " 的最短路径是经过顶点 j
            }
        }
        // Floyd algorithm
        for (int k = 0; k < n; k++) {
            // 如果经过下标为 k 顶点路径比原两点间路径更短，则更新 dist[i][j] 和 path[i][j]
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int temp = (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) ? Integer.MAX_VALUE : dist[i][k] + dist[k][j];
                    if (dist[i][j] > temp) {
                        dist[i][j] = temp;
                        // path[i][j] = path[i][k]; wrong
                        path[i][j] = k; // i -> k -> j
                    }
                }
            }
        }

        // print results
        System.out.println("min distances: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d ", dist[i][j]);
            }
            System.out.println();
        }
        System.out.println("path points: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d ", path[i][j]);
            }
            System.out.println();
        }
    }
}
