public class Prim {
    public static void main(String[] args) {
        final int MAX = Integer.MAX_VALUE;
        int graph[][] = new int[][] {{0, 2, MAX, 6, MAX},
                                    {2, 0, 3, 8, 5},
                                    {MAX, 3, 0, MAX, 7},
                                    {6, 8, MAX, 0, 9},
                                    {MAX, 5, 7, 9, 0}};
        System.out.println(prim(graph));
    }

    private static int prim(int[][] matrix) {
        //
        int n = matrix.length; // # vertices
        // construct adjacent list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                if (matrix[i][j] != Integer.MAX_VALUE) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] key = new int[n];
        int[] prev = new int[n];
        boolean[] isMST = new boolean[n];
        // initialization
        // pick 0 as starting point
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        Arrays.fill(prev, -1);
        // Prim algorithm
        for (int i = 0; i < n - 1; i++) {
            // iterate n - 1
            // 1. find the next point with min key value in unvisited points to be added into MST
            int min = Integer.MAX_VALUE;
            int curr = -1;
            for (int j = 0; j < n; j++) {
                if (isMST[j]) {
                    continue;
                }
                if (key[j] < min) {
                    min = key[j];
                    curr = j;
                }
            }
            // Add the picked vertex to the MST Set
            isMST[curr] = true;
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int j = 0; j < adj.size(); j++) {
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                for (int next : adj.get(j)) {
                    if (isMST[next]) {
                        continue;
                    }
                    if (matrix[curr][next] != 0 && matrix[curr][next] < key[next]) {
                        prev[next] = curr;
                        key[next] = matrix[curr][next];
                    }
                }
            }
        }

        // print edges and return the total weight/cost for this MST
        int result = 0;
        System.out.println("from   to   weight");
        for (int i = 1; i < n; i++) {
            if (prev[i] != -1) {
                result += key[i];
                System.out.println(prev[i] + "  ->  " + i + "\t" + key[i]);
            }
        }
        return result;
    }
}
