# Traveling Salesman
Potential solution to the Traveling Salesman problem!

## The Theory

This problem has been heavily studied, and at last may have a potential solution.
The concept I have come up with was made entirely on my own, but is close to some other theories that will be referenced.

My first step was to think about the problem differently. The classic TSP (Traveling Salesman Problem) is stated along these lines:

> A traveling salesman wants to sell his product in a set of given cities, and wants to travel along the shortest path between them all, returning to the city he started at.
> How is the length of this path found?

The problem is defined as the shortest route that starts and ends at the same point, which is essentially the shortest _circuit_ for the whole graph, making the start aribtrary.
In other words, no matter where you start on the graph, there will only be one "shortest path."
The question then becomes:

> What is the shortest circuit for the given graph, visiting each point only once?

To calculate this, I thought of how to make the graph more "understandable" for a computer, and the easiest way to do so is just make the whole thing into one line.
Thinking abstractly, we take all of the points in the graph and arrange them into one straight line along the y-axis, creating a path with the shortest distance between each point.
This produces the shortest line possible. To make a circuit, we just get the distance from the last point in the line to the first, and add it back onto the total distance of the line.
None of the point values are actually changed, just arranged using abstract thought. Now for constructing this line.

Working in the first quadrant, it turns out you can just iterate over the x and y axis adding the points closest to y=0 in order from the bottom, and you'll just get the right path.
If you have negative y values, then the starting y becomes whatever that most negative value.
This process will build the shortest line.
Again, since the start is aribtrary, I can just pick the first y value I come across and have that be my start.
Due to the nature of how I think about this line, I have to sort all of the points by order of least to greatest x and y.
This doesn't change their values, just puts everything in an order that's easy to calculate.
Because I'm using the distance formula, any calculations done in the third quadrant are brought up to the first, and any done in the fourth are brought up to the second (by order of magnitude), which is also _conveniently_ friendly to a number line.

Now this works very well for any random scatter plot of data, but what happens if y-values between two or more points are shared?
How is this calculated? I call these kinds of points "collisions," and calculating them is challenging.
When a collision is encountered, we have to decide whether it goes before or after the first point that shared the same y-value but wasn't a collision, which I call a "source."
We also have to choose whether it goes directly before or after the source, or goes somewhere farther down the path in either direction.

I've tried many variations on a solution, and how I solve it right now is as follows.
If the graph contains any collisions, check to see if the collision matches a source.
If the collision has a shorter distance (a.k.a the most optimal choice) compared to the source's neighbor (the next point in the graph disregarding collisions) then set the collision as the neighbor and remove it from a collision cache.
After the shortest path is computed, if we have any leftover collisions add them onto the end of the solution.
The logic behind this is that we're supposed to already have the shortest path, so any points that cannot fit as the shortest option can just link the circuit, and still give us the shortest path.

After some research, my solution most closely resembles the _Nearest Neighbor Algorithm_ by Rosenkratz, Stearns and Lewis, detailed on page 242 of [this paper](https://www.sciencedirect.com/science/article/pii/037722179290138Y).
The beauty in my solution is that in its best case we can get O(n) performance, and in its worst we get O(n^2).
So I can optimize their theoretical solution by one full loop.
Best case is a graph with no collisions, but if it contains any we need to loop over them to find the best spot for them.
There may also be post-processing cost(s).
The fact that people have thought along these lines in the past gave me reassurance that I was on an accurate path.

## Results

Currently my implementation works for random scatter plots and lines.
It has been tested across a large suite of random graphs from 5-100 points, and generates the correct solution.
As of now it has difficulty in calculating simple to complex polygons, like a square, because it calculates the path including diagonals, rather than just the sides.
This is after a significant code update, though. A fix is in the works. I also plan to develop a small graphics application to show how the algorithm solves the graph. Random to hard-coded graphs can be used.
My previous versions of the algorithm will also be provided, though the earlier versions are not up to "professional" coding standards, as I was just wanted to get everything working at that point.

### [Copyright](https://www.infoworld.com/article/2615869/open-source-software/github-needs-to-take-open-source-seriously.html)
