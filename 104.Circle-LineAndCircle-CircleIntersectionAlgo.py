# Circle-Line Intersection: Determines where (if at all) a given straight line crosses a circle.
# Circle-Circle Intersection: Finds the intersection points (if any) between two circles.

# Explanation

# When a line (usually represented as y = mx + c) passes 
# through a circle ((x - a)² + (y - b)² = r²), substituting y gives a quadratic in x.
# If the discriminant (Δ) > 0 → two intersections,
# Δ = 0 → tangent (one intersection),
# Δ < 0 → no intersection.

# Similarly, for two circles, subtract one from another to form a line, 
# then substitute back into one circle equation to get intersection coordinates.

import math

def circle_line_intersection(a, b, r, m, c):
    A = 1 + m**2
    B = 2 * (m * (c - b) - a)
    C = a**2 + (c - b)**2 - r**2

    D = B**2 - 4 * A * C

    if D < 0:
        return []
    
    elif D == 0:
        x = -B / (2 * A)
        y = m * x + c
        return[(x, y)]
    
    else:
        sqrt_D = math.sqrt(D)
        x1 = (-B + sqrt_D) / (2 * A)
        x2 = (-B - sqrt_D) / (2 * A)
        y1 = m * x1 + c
        y2 = m * x2 + c
        return [(x1, y1), (x2, y2)]
    
def circle_circle_intersection(x0, y0, r0, x1, y1, r1):
    dx = x1 - x0
    dy = y1 - y0
    d = math.hypot(dx, dy)

    if d > r0 + r1 or d < abs(r0 - r1):
        return []
    
    a = (r0**2 - r1**2 + d**2) / (2 * d)
    h = math.sqrt(abs(r0**2 - a**2))

    xm = x0 + a * dx / d
    ym = y0 + a * dy / d

    xs1 = xm + h * dy / d
    ys1 = ym - h * dx / d
    xs2 = xm - h * dy / d
    ys2 = ym + h * dx / d

    if h == 0:
        return [(xs1, ys1)]
    
    else:
        return [(xs1, ys1), (xs2, ys2)]
    

# Circle center (0, 0), radius 5
# Line: y = x + 1
line_points = circle_line_intersection(0, 0, 5, 1, 1)
print("Circle-Line Intersection Points:", line_points)

# Circle1: center (0, 0), radius 5
# Circle2: center (4, 0), radius 3
circle_points = circle_circle_intersection(0, 0, 5, 4, 0, 3)
print("Circle-Circle Intersection Points:", circle_points)