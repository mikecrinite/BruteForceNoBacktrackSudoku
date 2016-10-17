# BruteForceNoBacktrackSudoku

This is a brute force Sudoku solver, created for a Design and Analysis of Algorithms class. It is written entirely in Java.
While most brute force solvers also utilize backtracking, this one does not.
This solver identifies each '0' in the Sudoku puzzle ('0's represent blanks) and attempts to increment them one at a time
until a solution is found or it is confirmed that no solution is possible.
For example, if there are 4 blanks in a 2x2 puzzle, it will try to solve as follows:
  1 1 1 1
  2 1 1 1
  3 1 1 1
  4 1 1 1
  1 2 1 1
  2 2 1 1
  3 2 1 1
  4 2 1 1
  1 3 1 1
  2 3 1 1
  3 3 1 1
  4 3 1 1
  1 4 1 1
  2 4 1 1
  3 4 1 1
  4 4 1 1
  1 1 2 1
  2 1 2 1
  ... etc
THIS METHOD IS EXTREMELY SLOW! (The point of the assignment was to show how slow brute-forcing can be, despite its ease to code!)

For example, in a 9x9 Sudoku puzzle with 50 blanks, it could potentially generate 5.15 x 10^47 assignments!

If you are looking for a quick solver, this is not the project for you. In that case, feel free to check out 
my other project, the Brute-Force-Sudoku-Solver which speeds the process up significantly by utilizing backtracking.
If you are looking for a pure brute-force solver, this one is available for your reference. 

The /src folder contains the solver, and the root folder contains various Sudoku puzzles in .txt format

-------------------------------------------------------------
* The Input class was written by Jacob Caggese              *
* The Filler class was written by me, Michael Crinite       *
* The Checker class was written by Alexander Luongo         *
* The SudokuDriver class was written by me, Michael Crinite *
-------------------------------------------------------------
