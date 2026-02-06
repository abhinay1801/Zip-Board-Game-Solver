
/*
    code for Zip Board Game Problem
    
    Problem Description:
    
    The Zip Board Game is a grid-based logic puzzle where the objective is to connect a 
    sequence of numbered "nodes"(1 to N) using a single continuous path that fills every 
    available cell on the grid. The path must move incrementally (1 to 2, 2 to 3, etc.) 
    until it reaches the maximum number.
    
    
    
    Constraints :
    
        -Adjacency: Movement is restricted to four orthogonal directions (Up, Down, Left, Right). 
                    Diagonal moves are prohibited.
                    
        -Path Continuity: A path to node X must originate exactly from node X-1.
        
        -Cell Exclusivity: Each cell can be visited exactly once.No paths may overlap or cross.
        
        -Grid Saturation: A solution is only valid if all empty cells (-1) are occupied by a path or a node.
        
        -Boundary Conditions: Pathfinding must remain within the bounds 0 < row < M and 0 < col < N.
        
    
    Input / output :
        
        
        1.
            --------- ZIP GAME BOARD ----------
            1        .        .        2        
            .        .        .        .        
            
            Solved Grid:
            --------- ZIP GAME BOARD ----------
            1        1 ^      1 >      2        
            1 v      1 >      1 v      1 >    
            
            
            
        2.
            --------- ZIP GAME BOARD ----------
            1        .        .        .        
            .        .        .        2        
            
            No solution found for given grid.
            
        
        3.
            --------- ZIP GAME BOARD ----------
            .        8        .        .        .        .        .        
            7        .        .        4        .        12       .        
            .        .        2        .        .        .        9        
            .        .        .        .        .        .        .        
            6        .        .        .        5        .        .        
            .        3        .        1        .        .        10       
            .        .        .        .        .        11       .        
            
            Solved Grid:
            --------- ZIP GAME BOARD ----------
            7 ^      8        8 >      8 >      8 >      8 >      8 >      
            7        3 ^      3 >      4        4 >      12       8 v      
            6 ^      3 ^      2        1 ^      4 v      11 ^     9        
            6 ^      3 ^      2 v      1 ^      4 v      11 ^     9 v      
            6        3 ^      2 v      1 ^      5        11 ^     9 v      
            5 ^      3        2 v      1        5 v      11 ^     10       
            5 <      5 <      5 <      5 <      5 v      11       10 v     



            
    
    Implementation:
    
    The ZIP Board Game is implemented using a backtracking-based pathfinding approach.
    The solution systematically explores all valid paths that connect numbered nodes in
    sequence while ensuring complete grid coverage.
    
    Steps:
    
    1)Grid Initialization:
        -Create an M X N integer matrix initialized with -1 to represent unvisited/empty cells.
         Pre-place the given k numbers at their specific (x, y) coordinates.
         
    2)Starting Point:
        -Locate the position of the first number (1) to serve as the starting point 
         for the backtracking algorithm.
    
    3)Recursive Backtracking (DFS):
        -Starting from the current node's position, explore the four cardinal directions (Up, Right, Down, Left).

            -Scenario A (Target Reached):
                -If a neighboring cell contains the next sequential number, move to that cell and increment 
                 the target search.

            -Scenario B (Path Extension):
                -If a neighboring cell is empty (-1), mark it with an encoded path value and continue the search
                 from this new cell, maintaining the same target.
    
    4)State Encoding:
        -To preserve memory and avoid extra data structures, store path information using a custom integer formula:

        formula : 1000 + (previousNumber * 10) + direction_index(0-3)
        
                        [thousands] → path marker (1000)[like offset]
                        [tens]      → sequence number (next)
                        [ones]      → direction index(0-3)

        -This allows the print() function to later decode which number the path belongs to and
         which way the arrow should point.
    
    5)Grid Saturation Check:
        -When the final number N is reached,execute the check() function. A solution is only marked as solved
          if every single cell in the grid has been filled (no Empty Cells).
    
    6)Backtracking (Undo Step):
        -If a path leads to a dead end or fails to saturate the grid (Found the empty Cells),
         the algorithm "backtracks" by resetting cells to -1, allowing the exploration of alternative routes.
    
    7)Visual Decoding:
        -The print() function iterates through the grid. It leaves -1 as dots, prints small integers as fixed nodes,
         and decodes the "encoded" integers to display the flow of the "Zip" using ASCII characters (^, >, v, <).
         
         
         
         
         
    Author : Abhinay Reddy
    
*/



import java.util.*;
public class Main
{
    
    //class variables static   
    static int m,n; // size of the grid (m rows , n columns)
    static boolean solved= false;  // to check whether we found valid path or not
    
    
    
    // check() function is used to check whether the all cells in the grid are filled with path or not
    //time complexity of check() : O(m*n)
    
    static boolean check(int grid[][])
    {
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(grid[i][j]==-1) return false;
            }
            
        }
        return true;
    }
    
    
    
    //Core Logic of Zip Board GAME
    
    //This function recursively explores all valid paths in 4 directions
    //to connect numbers in increasing order using a backtracking approach.
    //It tries all possible paths and backtracks when a path becomes invalid.
    
    //compute function uses backtracking algorithm 
    
    /* Time complexity :
    
       Theoretical Time Complexity(Worst Case):
            O(4^(m*n))
            -Each cell can branch into 4 directions in the worst case.
       
       Tighter Theoretical Bound(No immediate backtracking):
            You usually cannot go back to the previous cell
            Effective branching factor ≈ 3
            
            Time Complexity : O(3^(k))
            
                where k number of empty cells (-1) in the grid
                
        Practical Runtime Behavior : 
            Much less than O(3^K) due to pruning from:
            - Sequential number constraints
            - Grid saturation as paths grow
            - Early termination when a valid solution is found (solved boolean variable)
            - Practical performance is manageable for grids up to 7×7.
	*/

            
    static void compute(int grid[][],int x,int y,int next,int prev,int max)
    {
        
        if(solved) return;
        
        if(next>max)
        {
            
            if(check(grid))
            {
                //any valid paths between all numbers are found then stop the computing 
                solved = true;
            }
            return;
        }
        
        int dir[] = {-1,0,1,0,-1}; // for iterating the neighbouring cells
        
        
        for(int i=0;i<4;i++)
        {
            int nr = x+dir[i]; //new row
            int nc = y+dir[i+1]; //new column
            
            //check  whether the new row and new column are within bound or not
            if(nr>=0 && nr<m && nc>=0 && nc<n)
            {
                //if we found the path between previous number and next Number
                //then try to find the path between next number and next+1 Number
                if(grid[nr][nc]==next)
                {
                    compute(grid,nr,nc,next+1,next,max);
                    if(solved) return;
                }
                else if(grid[nr][nc]==-1)
                {
                    
                    //Integer state encoding  : to store the Number and direction in single Integer value
                    /*
                        [thousands] → path marker (1000)[like offset]
                        [tens]      → sequence number (next)
                        [ones]      → direction index(0-3)
                    */
                    grid[nr][nc] = 1000 + (prev * 10) + i;
                    
                    compute(grid,nr,nc,next,prev,max);
                    
                    //if found paths between all numbers then stop computing
                    
                    if(solved) return;
                    
                    //backtracking 
                    grid[nr][nc] = -1;
                }
                
            }
        }
        
    }
    static void print(int grid[][])
    {
        // String[] arrows = {" ↑ "," → "," ↓ "," ← "}; // console printing Issuse due Encoding formats
        
        //Arrows array is used to indicated the direction of path between two numbers
        String[] arrows = {"^",">","v","<"};
        
        System.out.println("--------- ZIP GAME BOARD ----------");
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                int val=grid[i][j];
                String s = "";
                if(val==-1)
                {
                    s = ".";
                }
                else if(val<100)
                {
                    // given numbers in grid
                    s  = Integer.toString(val);
                }
                else
                {
                    // to show the path between two numbers 
                    //Decoding the values from grid values
                    /*
                        [thousands] → path marker (1000)[like offset]
                        [tens]      → sequence number (next)
                        [ones]      → direction index(0-3)
                    */
                    
                    int num = (val-1000) / 10;
                    int dir = val % 10;
                    // Show which node the path belongs to and where it points
                    s = num+" "+arrows[dir];
                }
                System.out.printf("%-9s",s);
            }
            System.out.println();
        }
    }
    
    
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//input for rows,columns
		
		System.out.println("Enter the size of grid(row * column) : ");
		m = sc.nextInt();
		n = sc.nextInt();
		
		//check whether entered rows,columns are valid are not
		while(m<0 || n<0)
		{
		    System.out.println("Enter the size of grid are invalid .Enter the valid size of grid :  ");
		    System.out.println("Enter the size of grid(row * column) : ");
		    m = sc.nextInt();
		    n = sc.nextInt();
		}
		
		//store the Numbers
		int grid[][] = new int[m][n];
		
		int numbers;
		System.out.println("Enter the Range of Numbers (1-N) , Enter upperBound Number : ");
		numbers = sc.nextInt();
		
		//filling the All grid position to -1 
		for(int a[]:grid) Arrays.fill(a,-1);
		
		
		int firstX= -1,firstY = -1; // to store the position of 1st number
		
		
		//check whether we can fit all numbers in grid or not
		int maxNumbers = m*n;
		while(maxNumbers<numbers)
		{
		    System.out.println("We can't fill more than "+maxNumbers+" so entered the valid range .");
		    System.out.println("Enter the Range of Numbers (1-N) , Enter upperBound Number : ");
		    numbers = sc.nextInt();
		}
		
		
		//marking down the numbers positions and reamining all are -1 (Empty cell)
		for(int i=1;i<=numbers;i++)
		{
		    System.out.println("enter the location "+i+" "+"as row,column format : ");
		    int x = sc.nextInt();
		    int y = sc.nextInt();
		    
		    //check whether entered x,y positions are valid are not
		    if(x<0 || x>=m || y<0 || y>=n)
		    {
		        System.out.println("Entered position is the invalid . Enter the valid position of "+i+" : ");
		        i--;
		        continue;
		    }
		    
		    //check whether already any number exit or not
		    if(grid[x][y]!=-1)
		    {
		        System.out.println("you already entered the number in cell ("+x+","+y+") so reverify once.");
		        i--;
		        continue;
		    }
		    if(firstX==-1) firstX = x;
		    if(firstY==-1) firstY = y;
		    grid[x][y] = i;
		}
		System.out.println("Initial grid before Solving : ");
		print(grid);
		compute(grid,firstX,firstY,2,1,numbers);
		
		if(solved)
		{
            System.out.println("\nSolved Grid:");
            print(grid);
        }
        else
        {
            System.out.println("\nNo solution found for given grid.");
        }
        
	}
}
