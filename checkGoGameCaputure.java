// Uber phone call interview with Summer on 01/28/2019

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

// board game - Go
// Go is a strategy board game for two players, in which the aim is to surround more territory than the opponent.

// #     0 1 2 3 4 5
// # 0  |.|.|.|.|.|.|
// # 1  |.|.|x|.|.|.|
// # 2  |.|x|o|x|.|.|
// # 3  |.|.|x|.|.|.|
// # 4  |.|.|.|.|.|x|
// # 5  |.|.|.|.|x|o|
// # o is captured

// #     0 1 2 3 4 5
// # 0  |.|.|.|.|.|.|
// # 1  |.|.|x|.|.|.|
// # 2  |.|x|o|x|.|.|
// # 3  |.|x|o|o|x|.|
// # 4  |.|.|x|x|.|.|
// # 5  |.|.|.|.|.|.|
// # all 3 o's are captured
// # isCaptured(2,2) = true
// # isCaptured(3,2) = true
// # isCaptured(2,1) = false


// #     0 1 2 3 4 5
// # 0  |.|.|.|.|.|.|
// # 1  |.|.|x|.|.|.|
// # 2  |.|x|o|x|.|.|
// # 3  |.|x|o|o|.|.|
// # 4  |.|.|x|x|.|.|
// # 5  |.|.|.|.|.|.|
// # none of the o's are captured

// # isCaptured(board, x, y) => bool


public class Solution {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        char[][] board = new char[][] {
            {'.', '.', '.', '.', '.', '.'},
            {'.', '.', 'x', '.', '.', '.'},
            {'.', 'x', 'o', 'x', '.', '.'},
            {'.', 'x', 'o', 'o', 'x', '.'},
            {'.', '.', 'x', 'x', '.', '.'},
            {'.', '.', '.', '.', '.', '.'}
        };
        System.out.println(captured(board, 2, 2));
    }
    private static boolean captured(char[][] board, int x, int y) {
        int n = board.length;
        int m = board[0].length;
        // BFS
        Queue<Coordinate> queue = new LinkedList<>(); // queue to store coordinate [x, y]
        boolean[][] visited = new boolean[n][m];
        queue.offer(new Coordinate(x, y));
        char ori = board[x][y]; // checking o/ x
        visited[x][y] = true;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        while (!queue.isEmpty()) {
            Coordinate curr = queue.poll();
            for (int k = 0; k < 4; k++) {
                int nextX = curr.x + dx[k];
                int nextY = curr.y + dy[k];
                if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) {
                    continue;
                }
                if (visited[nextX][nextY]) {
                    continue;
                }
                if (board[nextX][nextY] == '.') {
                    return false;
                }
                if (board[nextX][nextY] == ori) {
                    queue.offer(new Coordinate(nextX, nextY));
                    visited[nextX][nextY] = true;
                }
            }
        }
        return true;
    }
    private static class Coordinate {
        private int x;
        private int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
