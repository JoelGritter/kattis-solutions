import java.util.Scanner;

/**
 * Joel Gritter
 * Solution to https://open.kattis.com/problems/musicalchairs
 */

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] profs = new int[n];
        boolean[] used = new boolean[n];
        int remainingProfs = n;

        for(int i = 0; i < n; i++){
            profs[i] = sc.nextInt();
        }

        int curIndex = 0;
        while(remainingProfs > 1){
            while(used[curIndex]){
                curIndex = (curIndex + 1) % n;
            }

            int curDiff = profs[curIndex];
            curDiff %= remainingProfs;
            if(curDiff == 0) curDiff = remainingProfs;

            while(curDiff > 0){
                curDiff--;
                if(curDiff == 0) break;
                curIndex = (curIndex + 1) % n;
                while(used[curIndex]){
                    curIndex = (curIndex + 1) % n;
                }
            }

            used[curIndex] = true;
            remainingProfs--;
        }

        for(int i = 0; i < n; i++){
            if(!used[i]){
                System.out.println(i+1);
                return;
            }
        }
    }
}
