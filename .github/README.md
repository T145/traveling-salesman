## The Theory

After some research, my theory most closely resembles the _Nearest Neighbor Algorithm_ by Rosenkratz, Stearns and Lewis, detailed on page 242 of [this paper](https://www.sciencedirect.com/science/article/pii/037722179290138Y).
The concept I use is entirely original, but the worst-case performance is very similar to their take.

My first step was to think about the problem differently. The classic TSP (Traveling Salesman Problem) is stated along these lines:

> Find the shortest possible route that visits every city exactly once and returns to the starting point.

The problem is defined as the shortest route that starts and ends at the same point, which is essentially the shortest _circuit_ for the whole graph, making the start aribtrary.
In other words, no matter where you start on the graph, there will only be one "shortest path."
The question then becomes:

> Find the shortest possible circuit that visits every city exactly once.

The easiest way to make this understandable for a computer is to make the whole graph into one line.
Thinking abstractly, we take all of the points in the graph and arrange them into one straight line along the y-axis,
creating a path with the shortest distance between each point.

This would give us the shortest Hamiltonian, but we can't say that it's always the shortest possible circuit.
The length of the overall path must be considered when choosing each subpath.
This consideration is done by sorting the graph.
Intuitively, after sorting the points by their `y` value, we'll end up with the shortest path.
The only time we run into problems while sorting is if any point shares the same y-value with another point.

There are a few ways I've come up with to handle such points, which I call collisions:

1. To find the best place to put them into the graph, all we have to do is test the distance at each point and choose which index gives us the shortest distance. This is brute-forcing the point, which has a performance of `O(n^3)`.

2. Build a binary tree that holds the graph, making any search operation `O(nlgn)`.

3. Think about the graph 3-dimensionally, and tilt it to find the best distance. We tilt it up, and calculate the distance. Do the same downward. Each direction is sorted, so we plug it in at an index in a list by its y-value. Whichever is shorter is the spot we want to place it in. Now, in shifting the graph either direction there's a constant `offset` that's being added or subtracted. When normalizing the graph, we have to change back each point's values from this offset. The total performance of this calculation would be `O(n)`.

The pseudocode for the whole algorithm is as follows:

    init solution
    
    sort solution
    
    be sure we have the shortest path, and remove any collisions for future calculation
    
    if we have collisions then find the best places to put them

## Results

Currently my implementation works for the following cases:

 - a single point (length 0)
 - simple to complex polygons
 - lines (simple to complex functions implied)
 - scatter plots (implied from polygons)

Every possible path would have to be either one or a combination of these.

## Asymptotic Analysis

### Current implementation

Language: Java

Worst case: Graph with collisions; worst case time of `O(n^3)`

Best case: Graph contains no collisions; worst case runtime is `O(nlgn)`, due to the point sorting

### Planned implementation:

Langauge: Python

Worst and Best cases: worst case runtime is `O(nlgn)` due to point sorting, overall algorithm performance is `O(n)`
