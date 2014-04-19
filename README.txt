four-in-row-in-JavaSE
=====================

This project is a four in row game that has been written in Java
The algorithm that I used is min-max algorithm after that I have added alpha-beta functionality to reduce the number of node generated.

In this project the user able to set the depth since the algorithm uses depth-first search.

choosing depth to be 7 is the best in terms of memory performance.




/////////////////////
program running
/////////////////////


0: you play first, 1 computer play first: 1
enter max depth: 7
|               |
|               |
|               |
|               |
|               |
|               |
  - - - - - - -
  0 1 2 3 4 5 6

computer is thinking ...
computer played: 3
Created Nodes: 264415
took(0.454s)
|               |
|               |
|               |
|               |
|               |
|       O       |
  - - - - - - -
  0 1 2 3 4 5 6

your turn, type a column number: 3
|               |
|               |
|               |
|               |
|       X       |
|       O       |
  - - - - - - -
  0 1 2 3 4 5 6

computer is thinking ...
computer played: 2
Created Nodes: 318429
took(0.393s)
|               |
|               |
|               |
|               |
|       X       |
|     O O       |
  - - - - - - -
  0 1 2 3 4 5 6

your turn, type a column number: 4
|               |
|               |
|               |
|               |
|       X       |
|     O O X     |
  - - - - - - -
  0 1 2 3 4 5 6

computer is thinking ...
computer played: 1
Created Nodes: 473208
took(0.367s)
|               |
|               |
|               |
|               |
|       X       |
|   O O O X     |
  - - - - - - -
  0 1 2 3 4 5 6

your turn, type a column number: 0
|               |
|               |
|               |
|               |
|       X       |
| X O O O X     |
  - - - - - - -
  0 1 2 3 4 5 6

computer is thinking ...
computer played: 3
Created Nodes: 368927
took(0.359s)
|               |
|               |
|               |
|       O       |
|       X       |
| X O O O X     |
  - - - - - - -
  0 1 2 3 4 5 6
  
  
  
  ...
