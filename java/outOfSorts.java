import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Joel Gritter
 * Solution to https://open.kattis.com/problems/outofsorts
 */

public class Main {

    int[] sequence;
    Set<Integer> possible;

    public Main() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int a = sc.nextInt();
        int c = sc.nextInt();
        long x0 = sc.nextInt();

        this.sequence = new int[n];
        possible = new HashSet<>();

        for(int i = 0; i < n; i++){
            x0 = (((a * x0) % m) + c) % m;
            sequence[i] = (int) x0;
        }

        deeperPossible(Integer.MAX_VALUE, -1, 0, n-1);

        System.out.println(possible.size());
    }

    public void deeperPossible(int strictLower, int strictHigher, int low, int high){
        if(low >= high){
            if(strictHigher < sequence[low] && sequence[low] < strictLower){
                possible.add(sequence[low]);
            }
        } else {
            int mid = (high + low) / 2;
            if(strictHigher < sequence[mid] && sequence[mid] < strictLower){
                possible.add(sequence[mid]);
            }

            // left side
            deeperPossible(Math.min(strictLower, sequence[mid]), strictHigher, low, mid-1);

            // right side
            deeperPossible(strictLower, Math.max(strictHigher, sequence[mid]), mid+1, high);
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}
