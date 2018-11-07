public class BellmanFord {
    public static void main(String[] args) {
        final int MAX = Integer.MAX_VALUE;
        int[][] matrix = {{0, -1, 4, MAX, MAX},
                          {MAX, 0, 3, 2, 2},
                          {4, 3, 0, MAX, MAX},
                          {MAX, 1, 5, 0, MAX},
                          {MAX, MAX, MAX, -3, 0}
                         };
        bellmanFord(matrix, 0);
    }

    // The main function that finds shortest distances from source
    // to all other vertices using Bellman-Ford algorithm.  The
    // function also detects negative weight cycle
    private void bellmanFord(int[][] matrix, int source) {
        // O(VE)
        int n = matrix.length;

        int dist[] = new int[n];

        // Step 1: Initialize distances from source to all other
        // vertices as INFINITE
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // construct adjacent list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != Integer.MAX_VALUE) {
                    // i -> j, there may be different weight for j -> i
                    adj.get(i).add(j);
                }
            }
        }

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from source to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 0; i < n - 1; ++i) {
            for (int from = 0; from < adj.size(); from++) {
                if (adj.get(from).size() == 0) {
                    continue;
                }
                for (int next : adj.get(from)) {
                    int weight = matrix[from][next];
                    if (dist[from] != Integer.MAX_VALUE && dist[from] + weight < dist[next]) {
                        dist[next] = dist[from] + weight;
                    }
                }
            }
        }

        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int from = 0; from < adj.size(); from++) {
            if (adj.get(from).size() == 0) {
                continue;
            }
            for (int next : adj.get(from)) {
                int weight = matrix[from][next];
                if (dist[from] != Integer.MAX_VALUE && dist[from] + weight < dist[next]) {
                    dist[next] = dist[from] + weight;
                }
            }
        }

        // print results
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < n; i++) {
            System.out.println(i + " \t " + dist[i]);
        }
    }
}
