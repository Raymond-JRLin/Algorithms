/*

Prim算法也是贪婪算法的一个典型例子，有点类似于dijkstra算法。核心思想：将点分为两拨，已经加入最小生成树的，未加入的，找到未加入中距离集合最近的点，添加该点，修改其它点到集合的距离,直到所有结点都加入到最小生成树。

Prim’s algorithm is also a Greedy algorithm. It starts with an empty spanning tree. The idea is to maintain two sets of vertices. The first set contains the vertices already included in the MST, the other set contains the vertices not yet included. At every step, it considers all the edges that connect the two sets, and picks the minimum weight edge from these edges. After picking the edge, it moves the other endpoint of the edge to the set containing MST.

A group of edges that connects two set of vertices in a graph is called cut in graph theory. So, at every step of Prim’s algorithm, we find a cut (of two sets, one contains the vertices already included in MST and other contains rest of the verices), pick the minimum weight edge from the cut and include this vertex to MST Set (the set that contains already included vertices).

How does Prim’s Algorithm Work? The idea behind Prim’s algorithm is simple, a spanning tree means all vertices must be connected. So the two disjoint subsets (discussed above) of vertices must be connected to make a Spanning Tree. And they must be connected with the minimum weight edge to make it a Minimum Spanning Tree.

Algorithm
1) Create a set mstSet that keeps track of vertices already included in MST.
2) Assign a key value to all vertices in the input graph. Initialize all key values as INFINITE. Assign key value as 0 for the first vertex so that it is picked first.
3) While mstSet doesn’t include all vertices
….a) Pick a vertex u which is not there in mstSet and has minimum key value.
….b) Include u to mstSet.
….c) Update key value of all adjacent vertices of u. To update the key values, iterate through all adjacent vertices. For every adjacent vertex v, if weight of edge u-v is less than the previous key value of v, update the key value as weight of u-v

*/
public class Prim {
    public static void main(String[] args) {
        final int MAX = Integer.MAX_VALUE;
        int graph[][] = new int[][] {{0, 2, MAX, 6, MAX},
                                    {2, 0, 3, 8, 5},
                                    {MAX, 3, 0, MAX, 7},
                                    {6, 8, MAX, 0, 9},
                                    {MAX, 5, 7, 9, 0}};
        System.out.println(method1(graph));
    }

    private static int method2(int[][] matrix) {
        // O((E + V)LogV) time 
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
        // initialization: pick 0 as starting point
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        Arrays.fill(prev, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0));
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            isMST[curr.vertex] = true;
            for (int next : adj.get(curr.vertex)) {
                if (isMST[next]) {
                    continue;
                }
                if (matrix[curr.vertex][next] != 0 && matrix[curr.vertex][next] < key[next]) {
                    pq.offer(new Node(next, matrix[curr.vertex][next]));
                    prev[next] = curr.vertex;
                    key[next] = matrix[curr.vertex][next];
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
    private static class Node implements Comparable<Node> {
        private int vertex;
        private int key;

        public Node(int v, int k) {
            this.vertex = v;
            this.key = k;
        }

        @Override
        public int compareTo(Node o2){
            return Integer.compare(this.key, o2.key);
        }
    }

    private static int method1(int[][] matrix) {
        // O(V ^ 2) time
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
        // initialization: pick 0 as starting point
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
