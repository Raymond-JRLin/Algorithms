/*
An edge in an undirected connected graph is a bridge iff removing it disconnects the graph. For a disconnected undirected graph, definition is similar, a bridge is an edge removing which increases number of disconnected components.

How to find all bridges in a given graph?

A simple approach is to one by one remove all edges and see if removal of a edge causes disconnected graph. Following are steps of simple approach for connected graph.

1) For every edge (u, v), do following
…..a) Remove (u, v) from graph
..…b) See if the graph remains connected (We can either use BFS or DFS)
…..c) Add (u, v) back to the graph.

Time complexity of above method is O(E*(n+E)) for a graph represented using adjacency list.

Tarjan's bridge-finding algorithm: A O(n+E) algorithm to find all Bridges
The idea is similar to O(n+E) algorithm for Articulation Points. We do DFS traversal of the given graph. In DFS tree an edge (u, v) (u is parent of v in DFS tree) is bridge if there does not exist any other alternative to reach u or an ancestor of u from subtree rooted with v. As discussed in the previous post, the value low[next] indicates earliest visited vertex reachable from subtree rooted with v. The condition for an edge (u, v) to be a bridge is, “low[next] > disc[curr]”.

*/

import java.util.*;

public class BridgeInUndirectedGraph {
    public static void main(String[] args) {
        int[][] nums1 = new int[][]{{1, 0}, {0, 2}, {2, 1}, {0, 3}, {3, 4}};
        int[][] nums2 = new int[][]{{0, 1}, {1, 2}, {2, 3}};
        int[][] nums3 = new int[][]{{0, 1}, {1, 2}, {2, 0}, {1, 3}, {1, 4}, {1, 6}, {3, 5}, {4, 5}};

        // List<int[]> bridges = findBrideges(5, nums1);
        // List<int[]> bridges = findBrideges(4, nums2);
        List<int[]> bridges = findBrideges(7, nums3);
        for (int[] bridge : bridges) {
            System.out.println(bridge[0] + " -> " + bridge[1]);
        }
    }

    private static List<int[]> findBrideges(int n, int[][] nums) {
        // get adjacent list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] row : nums) {
            adj.get(row[0]).add(row[1]);
            adj.get(row[1]).add(row[0]);
        }
        boolean visited[] = new boolean[n];
        int disc[] = new int[n]; // dicover time
        int low[] = new int[n];
        int parent[] = new int[n];
        Arrays.fill(parent, -1);

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(result, adj, visited, disc, low, parent, i);
            }
        }
        return result;
    }
    private static int time = 0;
    private static void dfs(List<int[]> result, List<List<Integer>> adj, boolean[] visited, int[] disc, int[] low, int[] parent, int curr) {
        // u is curr, v is next

        // Mark the current node as visited
        visited[curr] = true;

        // Initialize discovery time and low value
        disc[curr] = low[curr] = ++time;

        // Go through all vertices adjacent to this
        for (int next : adj.get(curr)) {

            // If v is not visited yet, then make it a child
            // of u in DFS tree and recur for it.
            // If v is not visited yet, then recur for it
            if (!visited[next]) {
                parent[next] = curr;
                dfs(result, adj, visited, disc, low, parent, next);

                // Check if the subtree rooted with v has a
                // connection to one of the ancestors of u
                low[curr]  = Math.min(low[curr], low[next]);

                // If the lowest vertex reachable from subtree
                // under v is below u in DFS tree, then u-v is
                // a bridge
                if (low[next] > disc[curr])
                    result.add(new int[]{curr, next});
            } else if (next != parent[curr]) {
                // Update low value of u for parent function calls.
                low[curr] = Math.min(low[curr], disc[next]);
            }
        }
    }
}
