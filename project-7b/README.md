Project 7b
==========

Brian Staheli

First come first serve was the easiest to write the code for since it went in order of what came into the queue. it also was the second slowest of all of the algorithms to get through the data. I can see how you would need something faster.
Shortest job first was a little harder to implement, but it was a lot faster than first come first serve. I can see the problems with it since it would create starvation for the bigger processes. 
Shortest remaining time next was the one that took the least amount of time, but we programmed it ignoring the overhead of the context switches. It probably would take a lot longer if those were factored into the algorithm.
Round robin did have longer turnaround times than shortest job first and shortest remaining time next algorithms, but it seemed a better way to do things since it doesn't create the starvation that the other two would make. When we had to program round robin with the context switches, it made it so it had the longest turnaround time of all of the algorithms. 
