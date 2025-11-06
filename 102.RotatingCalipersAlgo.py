# The Rotating Calipers Algorithm is a geometric technique that uses a pair
# (or more) of "calipers" (imaginary parallel lines) that rotate around a convex polygon 
# to find extreme points or optimal geometric measurements such as width, diameter, or area efficiently.
# It transforms complex geometry problems into simple linear sweeps.

# Detailed Explanation

# Imagine you have a convex polygon (say, a convex hull of a set of points).
# Now, imagine placing two parallel lines 
# (calipers) that touch the polygon — one at an extreme point and the other opposite to it.

# You rotate these calipers around the polygon, maintaining their parallelism, 
# and at each rotation step, you measure something — for example, 
# the distance between opposite points (to find the polygon’s diameter).

# Each vertex is considered only once per rotation, making the time complexity O(n).

# Common applications:

# Finding farthest pair of points (Convex Polygon Diameter)

# Finding minimum bounding rectangle

# Finding width or height of a polygon

import math

def distance(p1, p2):
    return math.hypot(p1[0] - p2[0], p1[1] - p2[1])

def rotating_calipers(points):
    n = len(points)
    if n == 2:
        return distance(points[0], points[1])
    
    max_dist = 0
    j = 1

    for i in range(n):
        while True:
            nxt = (j + 1) % n
            cross = abs((points[nxt][0] - points[i][0]) * (points[(i+1)%n][1] - points[i][1]) -
                        (points[nxt][1] - points[i][1]) * (points[(i+1)%n][0] - points[i][0]))
            next_cross = abs((points[j][0] - points[i][0]) * (points[(i+1)%n][1] - points[i][1]) -
                             (points[j][1] - points[i][1]) * (points[(i+1)%n][0] - points[i][0]))
            if cross > next_cross:
                j = nxt
            else:
                break

        max_dist = max(max_dist, distance(points[i], points[j]))

    return max_dist


points = [(0,0), (0,3), (3,3), (3,0)]

result = rotating_calipers(points)

print("Maximum Distance (Diameter):", round(result, 2))
