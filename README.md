# 364_5_ConwayGameOfLife Conway's Game Of Life

Implement Conway's Game of Life as a console app. See  https://en.wikipedia.org/wiki/Conway's_Game_of_Life for details.

Allow the user to select from 5 Oscillator configurations as shown on Wikipedia

1. Blinker (period 2)
1. Toad (period 2)	
1. Beacon (period 2)	
1. Pulsar (period 3)	
1. Pentadecathlon (period 15)	

transition from one state to another is a user option to either
1. automate at a generation per 500 ms
2. based on user hitting a key

__Using a threadPool divide the task of computing the next generation of the board using 25 threads. Set the thread pool size to 10 so that additional threads will be queued up until a thread  is available for execution. Also, when a new thread begins and ends execution, it should output to the console that is has begun and ended, respectively__

## Note

1. Run the starter code to see how delays and clearing screen can be accomplished

2. Implement and test methods 
```java
public class GameOfLife {
    public int neighborCount(int row, int col){
        ...
    }    
    
    public boolean isAliveNextGeneration(int row, int col){
        ...
    }
    
} 
```
1. Use the `clearScreen()` method provided in the starter code will clear the screen so that the patterns may appear as smooth transition

1. `Thread.sleep` will pause the game if automation is selected.

1. Use best practices and clean code
1. Add the yml file (change as needed) so that Travis will run your tests

[![Build Status](https://travis-ci.org/MCO364-1/hw4-yliff.svg?branch=master)](https://travis-ci.org/MCO364-1/hw4-yliff)
