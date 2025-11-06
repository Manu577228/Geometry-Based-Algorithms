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
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio      */
/* -----------------------------------------------------------------------  */

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {

    // ---------------------- Circle-Line Intersection ----------------------
    static List<double[]> circleLineIntersection(double a, double b, double r, double m, double c) {
        // Circle: (x - a)^2 + (y - b)^2 = r^2
        // Line: y = mx + c

        double A = 1 + m * m;                        // Coefficient of x^2
        double B = 2 * (m * (c - b) - a);            // Coefficient of x
        double C = a * a + (c - b) * (c - b) - r * r;// Constant term

        double D = B * B - 4 * A * C;                // Discriminant check

        List<double[]> res = new ArrayList<>();

        if (D < 0) return res;                       // No intersection
        else if (D == 0) {
            double x = -B / (2 * A);
            double y = m * x + c;
            res.add(new double[]{x, y});             // Tangent point
        } else {
            double sqrtD = Math.sqrt(D);
            double x1 = (-B + sqrtD) / (2 * A);
            double x2 = (-B - sqrtD) / (2 * A);
            double y1 = m * x1 + c;
            double y2 = m * x2 + c;
            res.add(new double[]{x1, y1});           // First intersection
            res.add(new double[]{x2, y2});           // Second intersection
        }
        return res;
    }

    // ---------------------- Circle-Circle Intersection ----------------------
    static List<double[]> circleCircleIntersection(double x0, double y0, double r0,
                                                   double x1, double y1, double r1) {
        // Circle 1: (x - x0)^2 + (y - y0)^2 = r0^2
        // Circle 2: (x - x1)^2 + (y - y1)^2 = r1^2

        double dx = x1 - x0;
        double dy = y1 - y0;
        double d = Math.hypot(dx, dy);               // Distance between centers

        List<double[]> res = new ArrayList<>();

        if (d > r0 + r1 || d < Math.abs(r0 - r1)) return res; // No intersection

        double a = (r0 * r0 - r1 * r1 + d * d) / (2 * d);
        double h = Math.sqrt(Math.abs(r0 * r0 - a * a));

        double xm = x0 + a * dx / d;
        double ym = y0 + a * dy / d;

        double xs1 = xm + h * dy / d;
        double ys1 = ym - h * dx / d;
        double xs2 = xm - h * dy / d;
        double ys2 = ym + h * dx / d;

        if (h == 0) res.add(new double[]{xs1, ys1}); // Tangent circles
        else {
            res.add(new double[]{xs1, ys1});         // First intersection
            res.add(new double[]{xs2, ys2});         // Second intersection
        }

        return res;
    }

    // ---------------------- Example Usage ----------------------
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out);

        // Circle center (0, 0), radius 5
        // Line: y = x + 1
        List<double[]> linePoints = circleLineIntersection(0, 0, 5, 1, 1);
        out.println("Circle-Line Intersection Points:");
        for (double[] p : linePoints) out.println(Arrays.toString(p));

        // Circle1: center (0, 0), radius 5
        // Circle2: center (4, 0), radius 3
        List<double[]> circlePoints = circleCircleIntersection(0, 0, 5, 4, 0, 3);
        out.println("Circle-Circle Intersection Points:");
        for (double[] p : circlePoints) out.println(Arrays.toString(p));

        out.flush();
    }
}
