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

public class RotatingCalipers {

    // Step 1: Function to calculate Euclidean distance between two points
    static double distance(int[] p1, int[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Step 2: Rotating Calipers function to find the diameter (max distance)
    static double rotatingCalipers(int[][] points) {
        int n = points.length;
        if (n == 2) return distance(points[0], points[1]);

        double maxDist = 0;
        int j = 1;

        // Step 3: Loop through all vertices
        for (int i = 0; i < n; i++) {
            // Step 4: Move j while area (cross product) increases
            while (true) {
                int nxt = (j + 1) % n;
                long cross1 = Math.abs(
                    (points[nxt][0] - points[i][0]) * (points[(i + 1) % n][1] - points[i][1]) -
                    (points[nxt][1] - points[i][1]) * (points[(i + 1) % n][0] - points[i][0])
                );
                long cross2 = Math.abs(
                    (points[j][0] - points[i][0]) * (points[(i + 1) % n][1] - points[i][1]) -
                    (points[j][1] - points[i][1]) * (points[(i + 1) % n][0] - points[i][0])
                );
                if (cross1 > cross2) j = nxt;
                else break;
            }
            // Step 5: Update maximum distance
            maxDist = Math.max(maxDist, distance(points[i], points[j]));
        }
        return maxDist;
    }

    public static void main(String[] args) throws Exception {
        // Step 6: Example convex hull (square)
        int[][] points = {{0,0}, {0,3}, {3,3}, {3,0}};
        
        // Step 7: Compute result using Rotating Calipers
        double result = rotatingCalipers(points);
        
        // Step 8: Print the maximum distance
        System.out.println("Maximum Distance (Diameter): " + String.format("%.2f", result));
    }
}
