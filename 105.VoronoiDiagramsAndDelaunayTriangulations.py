# Voronoi Diagram:
# It divides a plane into regions based on the distance to a set of given points — 
# each region contains all points closer to one specific seed point than to any other.

# Delaunay Triangulation:
# It connects a set of points into triangles so that no point lies inside the circumcircle 
# of any triangle — maximizing the minimum angle of all triangles.

# Explanation (Simplified)

# Imagine you drop a few stones on a flat surface.

# A Voronoi Diagram shows how the surface gets divided into “territories” 
# around each stone — closer areas belong to that stone.

# A Delaunay Triangulation connects stones with lines to 
# form triangles in such a way that no stone falls inside the circle made by any triangle.

# These two are dual of each other — if you have one, you can derive the other.

import numpy as np
from scipy.spatial import Voronoi, Delaunay
import matplotlib.pyplot as plt

points = np.random.rand(10, 2)

vor = Voronoi(points)

tri = Delaunay(points)

fig, ax = plt.subplots(1, 2, figsize = (10, 5))

ax[0].set_title("Voronoi Diagram")
ax[0].plot(points[:, 0], points[:, 1], 'ko')

for region in vor.regions:
    if not -1 in region and len(region) > 0:
        polygon = [vor.vertices[i] for i in region]
        ax[0].fill(*zip(*polygon), alpha = 0.3)

ax[1].set_title("Delaunay Triangulation")
ax[1].triplot(points[:, 0], points[:, 1], tri.simplices)

ax[1].plot(points[:, 0], points[:, 1], 'ko')

plt.show()

print("Voronoi vertices:\n", vor.vertices)
print("\nDelaunay simplices (triangles):\n", tri.simplices)


