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

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.*;

/*  In this code, we implement both Voronoi Diagram and Delaunay Triangulation 
    in Java using Swing for visualization.
    We'll generate random points, compute triangulation, 
    and display both diagrams side by side.
*/

public class VoronoiDelaunay extends JPanel {
    private final int n = 10; // number of random points
    private final Point[] pts = new Point[n];
    private final java.util.List<Triangle> triangles = new ArrayList<>();

    // Constructor
    public VoronoiDelaunay() {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(r.nextInt(400) + 50, r.nextInt(400) + 50);
        }
        computeDelaunay();
    }

    // Class to represent a triangle
    static class Triangle {
        Point a, b, c;
        Triangle(Point a, Point b, Point c) {
            this.a = a; this.b = b; this.c = c;
        }
    }

    // Function to compute Delaunay (naive O(n^4))
    private void computeDelaunay() {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (isDelaunay(pts[i], pts[j], pts[k])) {
                        triangles.add(new Triangle(pts[i], pts[j], pts[k]));
                    }
                }
            }
        }
    }

    // Check if any other point lies inside circumcircle
    private boolean isDelaunay(Point a, Point b, Point c) {
        double[] circle = circumcircle(a, b, c);
        double cx = circle[0], cy = circle[1], r = circle[2];
        for (Point p : pts) {
            if (p != a && p != b && p != c) {
                double d = p.distance(cx, cy);
                if (d < r - 1e-6) return false;
            }
        }
        return true;
    }

    // Find circumcircle center and radius
    private double[] circumcircle(Point a, Point b, Point c) {
        double A = b.x - a.x, B = b.y - a.y;
        double C = c.x - a.x, D = c.y - a.y;
        double E = A * (a.x + b.x) + B * (a.y + b.y);
        double F = C * (a.x + c.x) + D * (a.y + c.y);
        double G = 2 * (A * (c.y - b.y) - B * (c.x - b.x));
        if (Math.abs(G) < 1e-6) return new double[]{0, 0, Double.MAX_VALUE};
        double cx = (D * E - B * F) / G;
        double cy = (A * F - C * E) / G;
        double r = Math.hypot(a.x - cx, a.y - cy);
        return new double[]{cx, cy, r};
    }

    // Paint both diagrams
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setStroke(new BasicStroke(1.2f));
        gg.setColor(Color.BLACK);
        for (Point p : pts) gg.fillOval(p.x - 3, p.y - 3, 6, 6);

        gg.setColor(Color.BLUE);
        for (Triangle t : triangles)
            gg.draw(new Line2D.Double(t.a.x, t.a.y, t.b.x, t.b.y)),
            gg.draw(new Line2D.Double(t.b.x, t.b.y, t.c.x, t.c.y)),
            gg.draw(new Line2D.Double(t.c.x, t.c.y, t.a.x, t.a.y));

        gg.setColor(new Color(255, 0, 0, 70));
        for (Point p : pts) {
            Polygon region = new Polygon();
            for (Triangle t : triangles) {
                double[] circle = circumcircle(t.a, t.b, t.c);
                region.addPoint((int) circle[0], (int) circle[1]);
            }
            gg.fillPolygon(region);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Voronoi Diagram & Delaunay Triangulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new VoronoiDelaunay());
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}

/*
Line-by-Line Explanation:
1. We define a JPanel to draw both diagrams.
2. Random points are generated inside the panel.
3. We find all unique triplets of points (i, j, k) and check if they form a Delaunay triangle.
4. For each triangle, we compute its circumcircle and verify that no other point lies inside it.
5. Valid triangles are stored and drawn in blue.
6. Approximate Voronoi regions are filled using circumcenters of triangles in red transparency.
7. The final result shows Delaunay connections and Voronoi partitions.

Output:
- Black dots: seed points.
- Blue lines: Delaunay triangles.
- Red shaded regions: approximate Voronoi cells.

Summary:
✅ Delaunay forms triangles connecting nearby points.
✅ Voronoi divides space by nearest distance.
✅ Both are geometric duals and widely used in spatial analysis, mesh generation, and computational geometry.
*/
