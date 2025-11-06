/* ----------------------------------------------------------------------------  */
/*   ( The Authentic JS/JAVA CodeBuff )
 ___ _                      _              _ 
 | _ ) |_  __ _ _ _ __ _ __| |_ __ ____ _ (_)
 | _ \ ' \/ _` | '_/ _` / _` \ V  V / _` || |
 |___/_||_\__,_|_| \__,_\__,_|\_/\_/\__,_|/ |
                                        |__/ 
 */
/* --------------------------------------------------------------------------   */
/*    Youtube: https://youtube.com/@code-with-Bharadwaj                        */
/*    Github : https://github.com/Manu577228                                  */
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio       */
/* -----------------------------------------------------------------------  */

import java.io.*;
import java.util.*;

public class ClosestPairOfPoints {

    // Function to calculate Euclidean distance between two points
    static double dist(int[] p1, int[] p2) {
        // Formula: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    // Brute force method for smaller subsets (≤ 3 points)
    static double bruteForce(int[][] pts, int n) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double d = dist(pts[i], pts[j]);
                if (d < min)
                    min = d;  // keep track of smallest distance found
            }
        }
        return min;
    }

    // Function to find the minimum distance within a strip region
    static double stripClosest(List<int[]> strip, double d) {
        double min = d;
        // Sort points by y-coordinate to compare only nearby points
        strip.sort(Comparator.comparingInt(p -> p[1]));

        for (int i = 0; i < strip.size(); i++) {
            int j = i + 1;
            // A point needs to be compared with next 7 points (geometry limit)
            while (j < strip.size() && (strip.get(j)[1] - strip.get(i)[1]) < min) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
                j++;
            }
        }
        return min;
    }

    // Recursive function implementing Divide & Conquer
    static double closestUtil(int[][] pts, int n) {
        // Base case: when few points remain, use brute force
        if (n <= 3) return bruteForce(pts, n);

        int mid = n / 2;
        int[] midPoint = pts[mid]; // Midpoint for dividing

        // Recursively compute minimum distances in left & right halves
        double dl = closestUtil(Arrays.copyOfRange(pts, 0, mid), mid);
        double dr = closestUtil(Arrays.copyOfRange(pts, mid, n), n - mid);

        // Find smaller of the two distances
        double d = Math.min(dl, dr);

        // Build a strip containing points close (|x - mid_x| < d)
        List<int[]> strip = new ArrayList<>();
        for (int[] p : pts) {
            if (Math.abs(p[0] - midPoint[0]) < d)
                strip.add(p);
        }

        // Combine: check if strip gives smaller distance
        return Math.min(d, stripClosest(strip, d));
    }

    // Main function to find smallest distance
    static double closestPair(int[][] pts) {
        // Sort points initially by x-coordinate before recursion
        Arrays.sort(pts, Comparator.comparingInt(p -> p[0]));
        return closestUtil(pts, pts.length);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Example Input (Can be replaced with user input)
        int[][] pts = {
            {2, 3},
            {12, 30},
            {40, 50},
            {5, 1},
            {12, 10},
            {3, 4}
        };

        double res = closestPair(pts);
        System.out.println("The smallest distance is: " + res);
    }
}
