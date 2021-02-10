import java.util.Scanner;

/**
 * Joel Gritter
 * Solution to https://open.kattis.com/problems/justpassingthrough
 */

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numRows = sc.nextInt();
        int numCols = sc.nextInt();
        int numPasses = sc.nextInt();

        int[][] board = new int[numRows][numCols];
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                board[i][j] = sc.nextInt();
            }
        }

        boolean[][] isPass = new boolean[numRows][numCols];
        for(int i = 1; i < numRows - 1; i++){
            for(int j = 1; j < numCols - 1; j++){
                if(board[i-1][j] > board[i][j] && board[i+1][j] > board[i][j]){
                    if(board[i][j-1] < board[i][j] && board[i][j+1] < board[i][j]){
                        if(board[i-1][j] != -1 && board[i+1][j] != -1 && board[i][j-1] != -1 && board[i][j+1] != -1){
                            isPass[i][j] = true;
                        }
                    }
                }
            }
        }

        int[][][] dp = new int[numPasses + 1][numRows][numCols];

        // ensure no impossible locations are considered
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(board[i][j] == -1){
                    for(int d = 0; d <= numPasses; d++){
                        dp[d][i][j] = -1;
                    }
                }
            }
        }

        // start off the first column of depth 0
        for(int i = 0; i < numRows; i++){
            dp[0][i][0] = board[i][0];
        }

        // when you don't want passes, make passes impossible
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(isPass[i][j]){
                    dp[0][i][j] = -1;
                }
            }
        }

        // first layer, just because edge cases
        for(int curCol = 1; curCol < numCols; curCol++){
            for(int curRow = 0; curRow < numRows; curRow++){
                if(dp[0][curRow][curCol] == -1) continue;
                int min = 999999999;
                for(int diffRow = -1; diffRow <= 1; diffRow++){
                    if (0 <= curRow + diffRow && curRow + diffRow < numRows){
                        if(dp[0][curRow + diffRow][curCol - 1] == -1) continue;
                        min = Math.min(min, dp[0][curRow + diffRow][curCol - 1]);
                    }
                }
                if(min == 999999999){
                    dp[0][curRow][curCol] = -1;
                } else {
                    dp[0][curRow][curCol] = min + board[curRow][curCol];
                }
            }
        }

        // impossible to have a pass on the first column, two passes on second column, etc.
        for(int depth = 1; depth <= numPasses; depth++){
            for(int curCol = 0; curCol < depth; curCol++){
                for(int curRow = 0; curRow < numRows; curRow++){
                    dp[depth][curRow][curCol] = -1;
                }
            }
        }

        // for each depth, go from left to right
        // if the current location is a pass, we need to look one depth down (one fewer pass)
        // otherwise, just check the same depth
        for(int depth = 1; depth <= numPasses; depth++){
            for(int curCol = depth; curCol < numCols; curCol++){
                for(int curRow = 0; curRow < numRows; curRow++){
                    if(dp[depth][curRow][curCol] == -1) continue;

                    int min = 999999999;
                    for(int diffRow = -1; diffRow <= 1; diffRow++){
                        if(0 <= curRow + diffRow && curRow + diffRow < numRows){
                            if(isPass[curRow][curCol]){
                                if(dp[depth - 1][curRow + diffRow][curCol - 1] != -1){
                                    min = Math.min(min, dp[depth - 1][curRow + diffRow][curCol - 1]);
                                }
                            } else {
                                if(dp[depth][curRow + diffRow][curCol - 1] != -1){
                                    min = Math.min(min, dp[depth][curRow + diffRow][curCol - 1]);
                                }
                            }
                        }
                    }

                    if(min == 999999999){
                        dp[depth][curRow][curCol] = -1;
                    } else {
                        dp[depth][curRow][curCol] = min + board[curRow][curCol];
                    }
                }
            }
        }

        boolean good = false;
        int result = Integer.MAX_VALUE;

        for(int i = 0; i < numRows; i++){
            if(dp[numPasses][i][numCols-1] != -1){
                good = true;
                result = Math.min(result, dp[numPasses][i][numCols-1]);
            }
        }

        if(good){
            System.out.println(result);
        } else {
            System.out.println("impossible");
        }
    }
}