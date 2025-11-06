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

public class LineSweepIntersection {

    // Class representing a line segment with endpoints (x1, y1) and (x2, y2)
    static class Segment {
        int x1, y1, x2, y2;
        Segment(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    // Orientation function to determine relative direction of three points
    static int orientation(int[] p, int[] q, int[] r) {
        long val = (long)(q[1] - p[1]) * (r[0] - q[0]) - (long)(q[0] - p[0]) * (r[1] - q[1]);
        if (val == 0) return 0; // collinear
        return (val > 0) ? 1 : 2; // 1 = clockwise, 2 = counterclockwise
    }

    // Check if point q lies on segment pr
    static boolean onSegment(int[] p, int[] q, int[] r) {
        return (q[0] >= Math.min(p[0], r[0]) && q[0] <= Math.max(p[0], r[0]) &&
                q[1] >= Math.min(p[1], r[1]) && q[1] <= Math.max(p[1], r[1]));
    }

    // Function to check if two segments intersect
    static boolean intersect(Segment s1, Segment s2) {
        int[] p1 = {s1.x1, s1.y1}, q1 = {s1.x2, s1.y2};
        int[] p2 = {s2.x1, s2.y1}, q2 = {s2.x2, s2.y2};

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General intersection case
        if (o1 != o2 && o3 != o4) return true;

        // Collinear cases
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }

    // Line Sweep algorithm to find all intersections
    static List<String> lineSweep(List<Segment> segments) {
        // Step 1: Prepare event points (x-coordinate, type, segment)
        class Event {
            int x; char type; Segment seg;
            Event(int x, char type, Segment seg) {
                this.x = x; this.type = type; this.seg = seg;
            }
        }

        List<Event> events = new ArrayList<>();
        for (Segment s : segments) {
            events.add(new Event(Math.min(s.x1, s.x2), 'L', s)); // left endpoint
            events.add(new Event(Math.max(s.x1, s.x2), 'R', s)); // right endpoint
        }

        // Step 2: Sort events by x-coordinate
        events.sort(Comparator.comparingInt(e -> e.x));

        // Step 3: Active set storing segments currently intersecting sweep line
        TreeMap<Double, Segment> active = new TreeMap<>();
        List<String> result = new ArrayList<>();

        // Step 4: Process each event
        for (Event e : events) {
            double yMid = (e.seg.y1 + e.seg.y2) / 2.0;

            if (e.type == 'L') { 
                // Insert segment into active set
                active.put(yMid, e.seg);
                // Find neighbors (previous and next)
                Map.Entry<Double, Segment> lower = active.lowerEntry(yMid);
                Map.Entry<Double, Segment> higher = active.higherEntry(yMid);

                // Check for intersection with immediate neighbors
                if (lower != null && intersect(e.seg, lower.getValue()))
                    result.add(format(e.seg, lower.getValue()));
                if (higher != null && intersect(e.seg, higher.getValue()))
                    result.add(format(e.seg, higher.getValue()));
            } 
            else {
                // Remove segment from active set
                Map.Entry<Double, Segment> lower = active.lowerEntry(yMid);
                Map.Entry<Double, Segment> higher = active.higherEntry(yMid);
                active.remove(yMid);

                // Check intersection between neighbors after removal
                if (lower != null && higher != null && intersect(lower.getValue(), higher.getValue()))
                    result.add(format(lower.getValue(), higher.getValue()));
            }
        }

        return result;
    }

    // Helper to format output
    static String format(Segment s1, Segment s2) {
        return "Segment (" + s1.x1 + "," + s1.y1 + ")-(" + s1.x2 + "," + s1.y2 + 
               ") intersects with (" + s2.x1 + "," + s2.y1 + ")-(" + s2.x2 + "," + s2.y2 + ")";
    }

    // Main driver method
    public static void main(String[] args) throws IOException {
        // Input: list of line segments
        List<Segment> segments = Arrays.asList(
            new Segment(1, 1, 4, 4),
            new Segment(1, 4, 4, 1),
            new Segment(5, 2, 7, 2)
        );

        // Run Line Sweep algorithm
        List<String> intersections = lineSweep(segments);

        // Print results
        System.out.println("Intersections found:");
        for (String s : intersections) System.out.println(s);
    }
}
