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

public class Main {

    // A simple Point class
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Step 1: Orientation check function
    // 0 -> Collinear, 1 -> Clockwise, 2 -> Counterclockwise
    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    // Step 2: Distance squared (used for tie-breaking while sorting)
    static int distSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x)
             + (p1.y - p2.y) * (p1.y - p2.y);
    }

    // Reference point for sorting by polar angle
    static Point p0;

    // Step 3: Comparator for sorting points with respect to p0
    static class PolarComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            int o = orientation(p0, p1, p2);
            if (o == 0)
                return (distSq(p0, p2) >= distSq(p0, p1)) ? -1 : 1;
            return (o == 2) ? -1 : 1;
        }
    }

    // Step 4: Main Convex Hull Function (Graham's Scan)
    static List<Point> convexHull(Point[] points, int n) {
        if (n < 3) return new ArrayList<>();

        // Find the point with the lowest y (pivot)
        int minY = points[0].y, minIdx = 0;
        for (int i = 1; i < n; i++) {
            if ((points[i].y < minY) ||
                (points[i].y == minY && points[i].x < points[minIdx].x)) {
                minY = points[i].y;
                minIdx = i;
            }
        }

        // Place pivot at first position
        Point temp = points[0];
        points[0] = points[minIdx];
        points[minIdx] = temp;

        // Sort the remaining points by polar angle w.r.t. pivot
        p0 = points[0];
        Arrays.sort(points, 1, n, new PolarComparator());

        // Remove collinear points near pivot keeping the farthest one
        int m = 1;
        for (int i = 1; i < n; i++) {
            while (i < n - 1 && orientation(p0, points[i], points[i + 1]) == 0)
                i++;
            points[m] = points[i];
            m++;
        }

        if (m < 3) return new ArrayList<>();

        // Create stack to store hull points
        Stack<Point> stack = new Stack<>();
        stack.push(points[0]);
        stack.push(points[1]);
        stack.push(points[2]);

        // Step 5: Process the remaining points
        for (int i = 3; i < m; i++) {
            while (stack.size() > 1 && orientation(
                    nextToTop(stack), stack.peek(), points[i]) != 2) {
                stack.pop(); // Remove right-turn points
            }
            stack.push(points[i]);
        }

        // Collect hull points
        List<Point> hull = new ArrayList<>(stack);
        return hull;
    }

    // Helper to get the second top element of stack
    static Point nextToTop(Stack<Point> stack) {
        Point top = stack.pop();
        Point next = stack.peek();
        stack.push(top);
        return next;
    }

    // Step 6: Main method
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        // Example Input Points
        Point[] points = {
            new Point(0, 3), new Point(1, 1), new Point(2, 2),
            new Point(4, 4), new Point(0, 0), new Point(1, 2),
            new Point(3, 1), new Point(3, 3)
        };

        int n = points.length;

        // Compute Convex Hull
        List<Point> hull = convexHull(points, n);

        // Output
        pw.println("Convex Hull Points in Order:");
        for (Point p : hull) {
            pw.println("(" + p.x + ", " + p.y + ")");
        }

        pw.flush();
    }
}
