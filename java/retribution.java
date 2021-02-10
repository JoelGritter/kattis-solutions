import java.util.Scanner;

/**
 * Joel Gritter
 * Solution to https://open.kattis.com/problems/retribution
 */

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numJudges = sc.nextInt();
        int numTar = sc.nextInt();
        int numFeather = sc.nextInt();

        int[][] judgeLocations = new int[numJudges][2];
        int[][] tarLocations = new int[numTar][2];
        int[][] featherLocations = new int[numFeather][2];

        boolean[] usedTJudges = new boolean[numJudges];
        boolean[] usedFJudges = new boolean[numJudges];
        boolean[] usedTar = new boolean[numTar];
        boolean[] usedFeather = new boolean[numFeather];

        double[][] judgeTarDist = new double[numJudges][numTar];
        double[][] judgeFeatherDist = new double[numJudges][numFeather];

        for(int i = 0; i < numJudges; i++){
            judgeLocations[i] = new int[] {sc.nextInt(), sc.nextInt()};
        }

        for(int i = 0; i < numTar; i++){
            tarLocations[i] = new int[] {sc.nextInt(), sc.nextInt()};
        }

        for(int i = 0; i < numFeather; i++){
            featherLocations[i] = new int[] {sc.nextInt(), sc.nextInt()};
        }

        // pre-calculate all distances
        for(int i = 0; i < numJudges; i++){
            for(int j = 0; j < numTar; j++){
                judgeTarDist[i][j] = dist(judgeLocations[i][0], judgeLocations[i][1], tarLocations[j][0], tarLocations[j][1]);
            }
            for(int j = 0; j < numFeather; j++){
                judgeFeatherDist[i][j] = dist(judgeLocations[i][0], judgeLocations[i][1], featherLocations[j][0], featherLocations[j][1]);
            }
        }

        double result = 0;

        // match up judges to closest tar
        for(int judgeCount = 0; judgeCount < numJudges; judgeCount++){
            int minJudge = -1;
            int minTar = -1;
            double minTarDist = Double.MAX_VALUE;

            for(int i = 0; i < numJudges; i++){
                if(usedTJudges[i]) continue;
                for(int j = 0; j < numTar; j++){
                    if(usedTar[j]) continue;
                    if(judgeTarDist[i][j] < minTarDist){
                        minJudge = i;
                        minTar = j;
                        minTarDist = judgeTarDist[i][j];
                    }
                }
            }

            usedTJudges[minJudge] = true;
            usedTar[minTar] = true;
            result += minTarDist;
        }

        // match up judges to closest feather
        for(int judgeCount = 0; judgeCount < numJudges; judgeCount++){
            int minJudge = -1;
            int minFeather = -1;
            double minFeatherDist = Double.MAX_VALUE;

            for(int i = 0; i < numJudges; i++){
                if(usedFJudges[i]) continue;
                for(int j = 0; j < numFeather; j++){
                    if(usedFeather[j]) continue;
                    if(judgeFeatherDist[i][j] < minFeatherDist){
                        minJudge = i;
                        minFeather = j;
                        minFeatherDist = judgeFeatherDist[i][j];
                    }
                }
            }

            usedFJudges[minJudge] = true;
            usedFeather[minFeather] = true;
            result += minFeatherDist;
        }

        System.out.println(result);
    }

    public static double dist(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
