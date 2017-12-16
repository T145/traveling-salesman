# Traveling Salesman
Potential solution to the Traveling Salesman problem!

## The Theory

After some research, my theory most closely resembles the _Nearest Neighbor Algorithm_ by Rosenkratz, Stearns and Lewis, detailed on page 242 of [this paper](https://www.sciencedirect.com/science/article/pii/037722179290138Y).
The concept I use is entirely original, but the performance is very similar to their take.

My first step was to think about the problem differently. The classic TSP (Traveling Salesman Problem) is stated along these lines:

> Find the shortest possible route that visits every city exactly once and returns to the starting point.

The problem is defined as the shortest route that starts and ends at the same point, which is essentially the shortest _circuit_ for the whole graph, making the start aribtrary.
In other words, no matter where you start on the graph, there will only be one "shortest path."
The question then becomes:

> Find the shortest possible circuit that visits every city exactly once.

The easiest way to make this understandable for a computer is to make the whole graph into one line.

Thinking abstractly, we take all of the points in the graph and arrange them into one straight line along the y-axis, creating a path with the shortest distance between each point.

Now this would give us the shortest Hamiltonian, but we can't say that the shortest Hamiltonian always results in the shortest possible circuit.
The length of the overall path must be considered when choosing each subpath.
This is done in the initialization phase of the algorithm.
The pseudocode for the whole algorithm is as follows:

    
    solution = new list
    collisions = new list
    
    // init solution
    // sort solution
    // remove collisions
    
    if we have no collisions
    // verify the solution by swapping points and making sure we still have the shortest distance
    // // if you think in terms of triangles this verifies if a point is close to its neighbor
    else
    // find the best spots to insert collisions
    

The only time we run into problems when using my method is if any point shares the same y-value with another point.

I call these points collisions. To find the best place to put them into the graph, all we have to do is test the distance at each point and choose which index gives us the shortest distance.

This is the part of the algorithm that performs the worst, and has the most room for improvement.

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
Init | O(n^2)
Post | theta(n)

_*Against Graph With Collisions*_

| Phase | Performance
--- | :---:
Pre | O(n^2)
Init | O(n^3)
Post | theta(n)

### [Copyright](https://www.infoworld.com/article/2615869/open-source-software/github-needs-to-take-open-source-seriously.html)
