public class Hamiltonian {
    public static void main(String[] args) {
        int[][] matrixFalse = new int[][]{{0, 1, 0, 0},
                                         {1, 0, 1, 1},
                                         {0, 1, 0, 0},
                                         {0, 1, 0, 0}};
        int[][] matrixTrue = new int[][]{{0, 1, 0, 0},
                                         {1, 0, 1, 1},
                                         {0, 1, 0, 1},
                                         {0, 1, 1, 0}};
                                         int graph1[][] = {{0, 1, 0, 1, 0},
                                             {1, 0, 1, 1, 1},
                                             {0, 1, 0, 0, 1},
                                             {1, 1, 0, 0, 1},
                                             {0, 1, 1, 1, 0},
                                         };

         int graph2[][] = {{0, 1, 0, 1, 0},
             {1, 0, 1, 1, 1},
             {0, 1, 0, 0, 1},
             {1, 1, 0, 0, 0},
             {0, 1, 1, 0, 0},
         };
        System.out.println(hamiltonianPath(matrixFalse));
        System.out.println(hamiltonianPath(matrixTrue));
        System.out.println(hamiltonianPath(graph1));
        System.out.println(hamiltonianPath(graph2));

        print(hamiltonianCycle(matrixFalse));
        print(hamiltonianCycle(matrixTrue));
        print(hamiltonianCycle(graph1));
        print(hamiltonianCycle(graph2));
    }

    private static List<Integer> hamiltonianCycle(int[][] matrix) {
        int n = matrix.length;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            visited[i] = true;
            List<Integer> path = new ArrayList<>();
            path.add(i);
            if (isCycle(matrix, n, visited, path, 1, i)) {
                return path;
            }
            visited[i] = false;
        }
        return new ArrayList<>();
    }
    private static boolean isCycle(int[][] matrix, int n, boolean[] visited, List<Integer> path, int count, int vertex) {
        // System.out.println("check " + vertex + ", count is " + count);
        if (count == n) {
            if (matrix[path.get(0)][path.get(path.size() - 1)] == 1) {
                path.add(path.get(0));
                return true;
            } else {
                return false;
            }
        }

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            if (matrix[vertex][i] == 0) {
                continue;
            }
            // System.out.println("next is " + i);
            visited[i] = true;
            path.add(i);
            if (isCycle(matrix, n, visited, path, count + 1, i)) {
                return true;
            }
            path.remove(path.size() -1);
            visited[i] = false;
        }
        return false;
    }

    private static boolean hamiltonianPath(int[][] matrix) {
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            visited[i] = true;
            if (dfs(matrix, n, visited, 1, i)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
    private static boolean dfs(int[][] matrix, int n, boolean[] visited, int count, int vertex) {
        // System.out.println("check " + vertex + ", count is " + count);
        if (count == n) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            if (matrix[vertex][i] == 0) {
                continue;
            }
            // System.out.println("next is " + i);
            visited[i] = true;
            if (dfs(matrix, n, visited, count + 1, i)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }

    private static void print(List<Integer> list) {
        System.out.print("Hamiltonian cycle: ");
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
