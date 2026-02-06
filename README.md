# Zip Board Game Solver

A grid-based logic puzzle solver implemented in Java.  
The Zip Board Game challenges the player (or algorithm) to connect a sequence of numbered nodes using a single continuous path that saturates every available cell on the grid.

---

## Problem Description

The objective is to connect nodes (numbered 1 to N) in incremental order.  
The solution must satisfy the following rules:

- Orthogonal Movement: You can only move Up, Down, Left, or Right.
- Sequential Continuity: A path to node X must originate exactly from node X-1.
- Hamiltonian Constraint: Every single cell in the grid must be occupied by either a node or a path segment.
- Cell Exclusivity: No overlapping or crossing of paths is allowed.

---

## Features

- Backtracking Algorithm: Efficiently explores path possibilities and prunes invalid branches.
- State Encoding: Uses a custom integer formula `1000 + (prev * 10) + direction` to store path data without additional memory overhead.
- Cross-Platform UI: Uses ASCII markers (^, >, v, <) to visualize the flow of the "Zip" in any terminal environment.
- Robust Input Validation: Handles out-of-bounds coordinates and overlapping node placement.

---

## Complexity

- Worst Case: `O(4^(M × N))`
- Practical Bound: `≈ O(3^K)` (where K is the number of empty cells), significantly reduced by sequential constraints and grid saturation requirements.
