# Traveling Salesman
Potential solution to the Traveling Salesman problem!

## The Theory

This problem has been heavily studied, and at last may have a potential solution.
The concept I have come up with was made entirely on my own, but is close to some other theories that will be referenced.

My first step was to think about the problem differently. The classic TSP (Traveling Salesman Problem) is stated along these lines:

> A traveling salesman wants to sell his product in a set of given cities, and wants to travel along the shortest path between them all, returning to the city he started at.
> Each city is visited only one time.
> How is the length of this path found?

The problem is defined as the shortest route that starts and ends at the same point, which is essentially the shortest _circuit_ for the whole graph, making the start aribtrary.
In other words, no matter where you start on the graph, there will only be one "shortest path."
The question then becomes:

> What is the shortest circuit for the given graph, visiting each point only once?

To calculate this, I thought of how to make the graph more "understandable" for a computer, and the easiest way is just to make the whole graph into one line.
Thinking abstractly, we take all of the points in the graph and arrange them into one straight line along the y-axis, creating a path with the shortest distance between each point.
This produces the shortest line possible. To make a circuit, we just get the distance from the last point in the line to the first, and add it back onto the total distance of the line.
None of the point values are actually changed, just rearranged using abstract thought. Now for constructing this line.

Working in the first quadrant, it turns out you can just iterate over the x and y axis adding the points closest to y=0 in order from the bottom, and you'll just get the right path.
If you have negative y values, then the starting y becomes the most negative value.
This process will build the shortest line.
Again, since the start is aribtrary, the first point encountered can be the start.
Due to the nature of this line, all of the points must be sorted by order of least to greatest x and y.
This doesn't change their values; all points are put in an order that's easy to calculate.
Because the distance formula is used, any calculations done in the third quadrant are brought up to the first, and any done in the fourth are brought up to the second (by order of magnitude), which is also _conveniently_ friendly to a number line.

Now this works very well for any random scatter plot of data, but what happens if y-values between two or more points are shared?
How is this calculated? We'll call these kinds of points "collisions," and calculating them is challenging.
When a collision is encountered, we have to decide whether it goes before or after the first point that shared the same y-value but wasn't a collision, which we'll call a "source."
We also have to choose whether it goes directly before or after the source, or somewhere farther down the path in either direction.

I've tried many variations on a solution, and how I solve it right now is as follows.
If the graph contains any collisions, check to see if the collision matches a source.
If the collision has a shorter distance (a.k.a the most optimal choice) compared to the source's neighbor (the next point in the graph disregarding collisions) then set the collision as the neighbor and remove it from a collision cache.
After the shortest path is computed, if any collisions are leftover, find the best place to put them into the graph.
My initial intuition was to throw all leftover collisions onto the end of the line, which is right a suprising amount of the time.

After some research, my solution most closely resembles the _Nearest Neighbor Algorithm_ by Rosenkratz, Stearns and Lewis, detailed on page 242 of [this paper](https://www.sciencedirect.com/science/article/pii/037722179290138Y).
The fact that people have thought along these lines in the past gave me reassurance that I was on an accurate path.

## Results

Currently my implementation works for the following cases:
- a single point (length 0)
- simple to complex polygons
- lines (simple to complex functions implied)
- scatter plots (implied from polygons)

Every possible path would have to be either one or a mixture of these.

## Asymptotic Analysis

_*Against Graph Without Collisions*_

| Phase | Performance
--- | :---:
Pre | O(n^2)
Init | theta(1)
Post | theta(n)

_*Against Graph With Collisions*_

| Phase | Performance
--- | :---:
Pre | O(n^2)
Init | O(n^3)
Post | theta(n)

### [Copyright](https://www.infoworld.com/article/2615869/open-source-software/github-needs-to-take-open-source-seriously.html)
