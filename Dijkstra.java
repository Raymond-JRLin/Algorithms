/*

迪杰斯特拉(Dijkstra)算法是典型单源最短路径算法，用于计算一个节点到其他节点的最短路径。
它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)，直到扩展到终点为止。

基本思想: 采用贪心法思想

     通过Dijkstra计算图G中的最短路径时，需要指定起点s(即从顶点s开始计算)。

     此外，引进两个集合S和U。S的作用是记录已求出最短路径的顶点(以及相应的最短路径长度)，而U则是记录还未求出最短路径的顶点(以及该顶点到起点s的距离)。

     初始时，S中只有起点s；U中是除s之外的顶点，并且U中顶点的路径是"起点s到该顶点的路径"。然后，从U中找出路径最短的顶点，并将其加入到S中；接着，更新U中的顶点和顶点对应的路径。 然后，再从U中找出路径最短的顶点，并将其加入到S中；接着，更新U中的顶点和顶点对应的路径。 ... 重复该操作，直到遍历完所有顶点。


操作步骤

(1) 初始时，S只包含起点s；U包含除s外的其他顶点，且U中顶点的距离为"起点s到该顶点的距离"[例如，U中顶点v的距离为(s,v)的长度，然后s和v不相邻，则v的距离为∞]。

(2) 从U中选出"距离最短的顶点k"，并将顶点k加入到S中；同时，从U中移除顶点k。

(3) 更新U中各个顶点到起点s的距离。之所以更新U中顶点的距离，是由于上一步中确定了k是求出最短路径的顶点，从而可以利用k来更新其它顶点的距离；例如，(s,v)的距离可能大于(s,k)+(k,v)的距离。

(4) 重复步骤(2)和(3)，直到遍历完所有顶点。

*/

public class Dijkstra {

    public static void main(String[] args) {
        // MAX_VALUE means cannot reach directly
        final int MAX = Integer.MAX_VALUE;
        int[][] matrix = {{0, 6, 3, MAX, MAX, MAX},
                          {6, 0, 2, 5, MAX, MAX},
                          {3, 2, 0, 3, 4, MAX},
                          {MAX, 5, 3, 0, 2, 3},
                          {MAX, MAX, 4, 2, 0, 5},
                          {MAX, MAX, MAX, 3, 5, 0}
                         };
        dijkstra1(matrix, 0);
        dijkstra2(matrix, 0);
    }

    /*
    *  improved dijkstra algorithm with min heap
    *  @param: adjacent matrix and source point
    */
    private static void dijkstra2_2(int[][] matrix,  int source) {
        // use PriorityQueue with adjacent list: O(|E|log|V|)?
        // ref: https://blog.csdn.net/Yaokai_AssultMaster/article/details/79878371
        // ref: https://codeday.me/bug/20180524/168274.html
        int n = matrix.length;
        // construct adjacent list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != Integer.MAX_VALUE) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        // map can used for dist and visited at the same time
        Map<Integer, Integer> map = new HashMap<>();
        // prev[i] = 从 source 到达 i 最短路径中 i 的前一个节点
        int[] prev = new int[n];
        Arrays.fill(prev, -1);
        // int[0] = 下一个到达的 point， 它们的距离为 int[1]， 前驱节点是 int[2]
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        pq.offer(new int[]{source, 0, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            if (map.containsKey(curr[0])) {
                continue;
            }
            map.put(curr[0], curr[1]);
            prev[curr[0]] = curr[2];
            for (int next : adj.get(curr[0])) {
                // 把所有从 curr 点 能够到达的点都放进去
                if (matrix[curr[0]][next] != Integer.MAX_VALUE) {
                    // j 是下一个到达的点， source 到 j 的距离 = source 到 curr 的距离 + curr 到 j 的距离， j 的前驱节点是 curr
                    pq.offer(new int[]{next, matrix[curr[0]][next] + curr[1], curr[0]});
                }
            }
        }
        // 设不能到达的点为 MAX
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, Integer.MAX_VALUE);
            }
        }

        // print results
        System.out.println("Vertex   Distance from Source   Prev point");
        for (int i = 0; i < n; i++) {
            System.out.println(i + " \t " + map.get(i) + " \t " + prev[i]);
        }
    }

    /*
    *  improved dijkstra algorithm with min heap
    *  @param: adjacent matrix and source point
    */
    private static void dijkstra2(int[][] matrix,  int source) {
        // use PriorityQueue: O((|E|+|V|)log|V|)
        // ref: https://blog.csdn.net/Yaokai_AssultMaster/article/details/79878371
        // ref: https://codeday.me/bug/20180524/168274.html
        // time complexity: http://www-inst.eecs.berkeley.edu/~cs61bl/r//cur/graphs/dijkstra-algorithm-runtime.html?topic=lab24.topic&step=4&course=
        // Adding these running times together, we have O(|E|log|V|) for all priority value updates and O(|V|log|V|) for removing all vertices (there are also some other O(|E|) and O(|V|) additive terms, but they are dominated by these two terms). This means the running time for Dijkstra's algorithm using a binary min-heap as a priority queue is O((|E|+|V|)log|V|).
        int n = matrix.length;
        // construct adjacent list
        // List<List<Integer>> adj = new ArrayList<>();
        // for (int i = 0; i < n; i++) {
        //     adj.add(new ArrayList<>());
        // }
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         if (matrix[i][j] != Integer.MAX_VALUE) {
        //             adj.get(i).add(j);
        //             adj.get(j).add(i);
        //         }
        //     }
        // }
        // map can used for dist and visited at the same time
        Map<Integer, Integer> map = new HashMap<>();
        // prev[i] = 从 source 到达 i 最短路径中 i 的前一个节点
        int[] prev = new int[n];
        Arrays.fill(prev, -1);
        // int[0] = 下一个到达的 point， 它们的距离为 int[1]， 前驱节点是 int[2]
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        pq.offer(new int[]{source, 0, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            if (map.containsKey(curr[0])) {
                continue;
            }
            map.put(curr[0], curr[1]);
            prev[curr[0]] = curr[2];
            for (int j = 0; j < n; j++) {
                // 把所有从 curr 点 能够到达的点都放进去
                if (matrix[curr[0]][j] != Integer.MAX_VALUE) {
                    // j 是下一个到达的点， source 到 j 的距离 = source 到 curr 的距离 + curr 到 j 的距离， j 的前驱节点是 curr
                    pq.offer(new int[]{j, matrix[curr[0]][j] + curr[1], curr[0]});
                }
            }
        }
        // 设不能到达的点为 MAX
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, Integer.MAX_VALUE);
            }
        }

        // print results
        System.out.println("Vertex   Distance from Source   Prev point");
        for (int i = 0; i < n; i++) {
            System.out.println(i + " \t " + map.get(i) + " \t " + prev[i]);
        }
    }

    /*
    *  basic dijkstra algorithm
    *  @param: adjacent matrix and source point
    */
    private static void dijkstra1(int[][] matrix,  int source) {
        // O(V ^ 2) time
        // ref: http://www.cnblogs.com/liuzhen1995/p/6527929.html
        // ref: https://www.cnblogs.com/skywang12345/p/3711516.html
        // ref: https://blog.csdn.net/oChangWen/article/details/50730937
        // ref: http://jake-12345.iteye.com/blog/2097224
        int n = matrix.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        // prev[i] = 从 source 到达 i 最短路径中 i 的前一个节点
        int[] prev = new int[n];
        // intialization
        for (int i = 0; i  < n; i++) {
            dist[i] = matrix[source][i];
            prev[i] = matrix[source][i] == Integer.MAX_VALUE ? -1 : source;
        }
        // mark source
        visited[source] = true;
        // iterate all other vertices
        for (int i = 0; i < n; i++) {
            if (i == source) {
                continue;
            }
            // find the current point with min path in unvisited points
            int min = Integer.MAX_VALUE;
            int next = -1;
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    continue;
                }
                if (dist[j] < min) {
                    min = dist[j];
                    next = j;
                }
            }
            // mark next point we found
            visited[next] = true;
            // update distance min path and previous point for unvisited points, passing this 'next' point
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    continue;
                }
                // 比较的距离是： source 到当前最短距离 point next 的 min + 从 next 到 j 点的距离
                int temp = matrix[next][j] == Integer.MAX_VALUE ? Integer.MAX_VALUE : matrix[next][j] + min;
                if (temp < dist[j]) {
                    dist[j] = temp;
                    prev[j] = next;
                }
            }
        }
        // print results
        System.out.println("Vertex   Distance from Source   Prev point");
        for (int i = 0; i < n; i++) {
            System.out.println(i + " \t " + dist[i] + " \t " + prev[i]);
        }
    }


}
