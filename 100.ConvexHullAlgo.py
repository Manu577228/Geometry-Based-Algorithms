# The Convex Hull of a set of points is the smallest convex polygon that contains all the points.
# In simpler terms, imagine stretching a rubber band around the outermost points — 
# the shape it takes is the convex hull.

# Explanation

# Convex Hull algorithms find this “boundary” among a set of points in a 2D plane.
# There are multiple algorithms to do this — Graham’s Scan and 
# Jarvis March (Gift Wrapping) are the most common.
# Here, we’ll implement Graham’s Scan, which works efficiently in O(n log n) time.

# Steps of Graham’s Scan:

# Find the lowest point (pivot) – the point with the lowest y-coordinate (and lowest x if there’s a tie).

# Sort all other points by the polar angle they make with the pivot.

# Traverse the sorted list and maintain a stack to determine whether 
# moving from the two previous points to the current point makes a “left turn” or a “right turn.”

# If a right turn occurs, pop the last point — it cannot be part of the convex hull.

# Continue until all points are processed — the stack now contains the convex hull.

import math

def orientation(p, q, r):
    val = (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1])
    if val == 0:
        return 0
    return 1 if val > 0 else 2

def dist(p1, p2):
    return (p1[0] - p2[0]) ** 2 + (p1[1] - p2[1]) ** 2

def convexHull(points):
    n = len(points)
    if n < 3:
        return []
    
    min_y = points[0][1]
    min_i = 0
    for i in range(1, n):
        if(points[i][1] < min_y) or (points[i][1] == min_y and points[i][0] < points[min_i][0]):
            min_y = points[i][1]
            min_i = i

    points[0], points[min_i] = points[min_i], points[0]
    p0 = points[0]

    def polar_angle(p):
        return math.atan2(p[1] - p0[1], p[0] - p0[0])
    
    points = [p0] + sorted(points[1:], key=lambda p: (polar_angle(p), dist(p0, p)))

    stack = [points[0], points[1], points[2]]
    for i in range(3, n):
        while len(stack) > 1 and orientation(stack[-2], stack[-1], points[i]) != 2:
            stack.pop()
        stack.append(points[i])

    return stack

# Example Input Points
points = [(0, 3), (1, 1), (2, 2), (4, 4),
          (0, 0), (1, 2), (3, 1), (3, 3)]

# Function Call
hull = convexHull(points)

# Output
print("Convex Hull Points in Order:")
for p in hull:
    print(p)

