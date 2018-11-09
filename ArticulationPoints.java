/*

A vertex in an undirected connected graph is an articulation point (or cut vertex) iff removing it (and edges through it) disconnects the graph. Articulation points represent vulnerabilities in a connected network – single points whose failure would split the network into 2 or more disconnected components.

A simple approach is to one by one remove all vertices and see if removal of a vertex causes disconnected graph. Following are steps of simple approach for connected graph.

1) For every vertex v, do following
…..a) Remove v from graph
..…b) See if the graph remains connected (We can either use BFS or DFS)
…..c) Add v back to the graph



Time complexity of above method is O(V*(V+E)) for a graph represented using adjacency list. Can we do better?

A O(V+E) algorithm to find all Articulation Points (APs)
The idea is to use DFS (Depth First Search). In DFS, we follow vertices in tree form called DFS tree. In DFS tree, a vertex u is parent of another vertex v, if v is discovered by u (obviously v is an adjacent of u in graph). In DFS tree, a vertex u is articulation point if one of the following two conditions is true.
1) u is root of DFS tree and it has at least two children.
2) u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.

*/
public class ArticulationPoints {
    public static void main(String[] args) {
        int[][] graph1 = new int[][]{
                                    {0, 1, 1, 1, 0},
                                    {1, 0, 1, 0, 0},
                                    {1, 1, 0, 0, 0},
                                    {1, 0, 0, 0, 1},
                                    {0, 0, 0, 1, 0}
                                };
        int[][] graph2 = new int[][]{
                                    {0, 1, 0, 0},
                                    {1, 0, 1, 0},
                                    {0, 1, 0, 1},
                                    {0, 0, 1, 0},
                                };
        int[][] graph3 = new int[][]{
                                    {0, 1, 1, 0, 0, 0, 0},
                                    {1, 0, 1, 1, 1, 0, 1},
                                    {1, 1, 0, 0, 0, 0, 0},
                                    {0, 1, 0, 0, 0, 1, 0},
                                    {0, 1, 0, 0, 0, 1, 0},
                                    {0, 0, 0, 1, 1, 0, 0},
                                    {0, 1, 0, 0, 0, 0, 0}
                                };
        print(ap(graph1));
        print(ap(graph2));
        print(ap(graph3));
    }

    private static List<Integer> ap(int[][] matrix) {
        int n = matrix.length;
        // construct adjacent list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                adj.get(i).add(j);
                adj.get(j).add(i);
            }
        }
        boolean visited[] = new boolean[n];
        int disc[] = new int[n]; // dicover time
        int low[] = new int[n]; // indicates whether there's some other early node (based on disc) that can be visited by the subtree rooted with that node
        int parent[] = new int[n];
        Arrays.fill(parent, -1);

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(adj, visited, disc, low, parent, i, set);
            }
        }
        return new ArrayList<>(set);
    }
    private static int time = 0;
    private static void dfs(List<List<Integer>> adj, boolean[] visited, int[] disc, int[] low, int[] parent, int curr, Set<Integer> set) {
        // u is curr, v is next
        // Count of children in DFS Tree
        int children = 0;

        // Mark the current node as visited
        visited[curr] = true;

        // Initialize discovery time and low value
        disc[curr] = low[curr] = ++time;

        // Go through all vertices adjacent to this
        for (int next : adj.get(curr)) {

            // If v is not visited yet, then make it a child of u
            // in DFS tree and recur for it
            if (!visited[next]) {
                children++;
                parent[next] = curr;
                dfs(adj, visited, disc, low, parent, next, set);

                // Check if the subtree rooted with v has a connection to
                // one of the ancestors of u
                low[curr]  = Math.min(low[curr], low[next]);

                // u is an articulation point in following cases

                // (1) u is root of DFS tree and has two or more chilren.
                if (parent[curr] == -1 && children > 1) {
                     set.add(curr);
                }
                // (2) If u is not root and low value of one of its child
                // is more than discovery value of u.
                if (parent[curr] != -1 && low[next] >= disc[curr]) {
                    set.add(curr);
                }
            } else if (next != parent[curr]) {
                // Update low value of u for parent function calls.
                low[curr] = Math.min(low[curr], disc[next]);
            }
        }
    }
    private static void print(List<Integer> list) {
        System.out.print("articulation points: ");
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
