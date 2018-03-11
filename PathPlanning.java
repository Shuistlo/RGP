import java.util.PriorityQueue;

/*
Pseudo code:
OPEN //the set of nodes to be evaluated
CLOSED //the set of nodes already evaluated
add the start node to OPEN
loop
 current = node in OPEN with the lowest f_cost
 remove current from OPEN
 add current to CLOSED
 if current is the target node //path has been found
 return
 foreach neighbour of the current node
 if neighbour is not traversable or neighbour is in CLOSED
 skip to the next neighbour
 if new path to neighbour is shorter OR neighbour is not in OPEN
 set f_cost of neighbour
 set parent of neighbour to current
 if neighbour is not in OPEN
 add neighbour to OPEN
*/
/*Note that the grid is like below and the robot can go both straight and diagonal
Grid:
 ST 0 0 0
 0 0 0 0
 0 0 BL 0
 0 0 0 DE
*/
public class PathPlanning {
    public static final int DIAGONAL_COST = 2; //Cost for robot to go diagonal
    public static final int V_H_COST = 1; //Cost for robot to go straight
    //The class defining the grid cell with all the variables
    class Cell{
        int heuristicCost = 0; //Heuristic cost
        int g = V_H_COST;
        int finalCost = 0; //
        int x, y;
        Cell parent;
        Cell(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        @Override
        public String toString(){
            return "["+this.x+", "+this.y+"]";
        }
    }
    //The grid for the robot to explore
    Cell [][] grid = new Cell[5][5];
    //The open and close list
    PriorityQueue<Cell> open;
    boolean closed[][];
    //x and y coordinates of the start and target position
    int startI, startJ;
    int endI, endJ;
    //Blocked cells are just null Cell values in grid, resemble the obstacles
    public void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    public void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }
    public void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }
    public int heuristic(Cell current, Cell target) {
        int d1 = Math.abs(current.getX() - target.getX());
        int d2 = Math.abs(current.getY() - target.getY());
        return d1 + d2;
    }
    //Main function to check the cost of target position and decide whether to put it into
    //the close list or open list.
    public void checkAndUpdateCost(Cell current, Cell target, int gcost){

        if(target == null || closed[target.x][target.y]){
            return;
        }
        // total cost for the target position
        int t_final_cost =___________________;
        boolean inOpen = open.contains(target);
        if(!inOpen || t_final_cost<target.finalCost){
            target.finalCost = t_final_cost;
            target.parent = current;
            if(!inOpen)open.add(target);
        }
    }
    public void AStar(){
        //add the start location to open list.
        open.add(grid[startI][startJ]);
        Cell current;
    /* What while loop does:
     current = node in OPEN with the lowest f_cost
     remove current from OPEN
     add current to CLOSED

     if current is the target node //path has been found
     return

     foreach neighbour of the current node
     if neighbour is not traversable or neighbour is in CLOSED
     skip to the next neighbour
     if new path to neighbour is shorter OR neighbour is not in OPEN
     set f_cost of neighbour
     set parent of neighbour to current
     if neighbour is not in OPEN
     add neighbour to OPEN
    */
        while(true){
            current = open.poll();
            if(current==null)break;
            closed[current.x][current.y]=true;

            // if current is the target node which means path has been found, return,
            //fill in the underlined places
            if(current.equals(grid[____][____])){
                return;
            }
            Cell neighbour;
            if(current.x-1>=0){
                neighbour = grid[current.x-1][current.y];
                checkAndUpdateCost(current, neighbour, current.finalCost+V_H_COST); // If
                //the neighbour can be reached by going straight, use V_H_COST
                if(current.y-1>=0){
                    neighbour = grid[current.x-1][current.y-1];
                    checkAndUpdateCost(current, neighbour,
                            current.finalCost+DIAGONAL_COST); // If the neighbour can be reached by going diagonal, use
                    //DIAGONAL_COST
                }
                if(current.y+1<grid[0].length){
                    neighbour = grid[current.x-1][current.y+1];
                    checkAndUpdateCost(current, neighbour,
                            current.finalCost+DIAGONAL_COST);
                }
            }
            if(current.y-1>=0){
                neighbour = grid[current.x][current.y-1];
                checkAndUpdateCost(current, neighbour, current.finalCost+V_H_COST);
            }
            if(current.y+1<grid[0].length){
                neighbour = grid[current.x][current.y+1];
                checkAndUpdateCost(current, neighbour, current.finalCost+V_H_COST);
            }
            if(current.x+1<grid.length){
                neighbour = grid[current.x+1][current.y];
                checkAndUpdateCost(current, neighbour, current.finalCost+V_H_COST);
                if(current.y-1>=0){
                    neighbour = grid[current.x+1][current.y-1];
                    checkAndUpdateCost(current, neighbour,
                            current.finalCost+DIAGONAL_COST);
                }
                if(current.y+1<grid[0].length){
                    neighbour = grid[current.x+1][current.y+1];

                    //right type of cost for this situation
                    checkAndUpdateCost(current, neighbour, current.finalCost+_________);
                }
            }
        }
    }
    //The simulation test to test the performance of the AStar class
     /*
     Params :
     tCase = test case No.
     x, y = Board's dimensions
     si, sj = start location's x and y coordinates
     ei, ej = end location's x and y coordinates
     int[][] blocked = array containing inaccessible cell coordinates
     */
    public void test(int tCase, int x, int y, int si, int sj, int ei, int ej,
                            int[][] blocked){
        System.out.println("\n\nTest Case #"+tCase);
        //Reset the grid map, the closed list, open list
        grid = new Cell[x][y];
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;
            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });

        //Set start position
        setStartCell(si, sj); //Setting to 0,0 by default. Will be useful for the UI part
        //Set the destination
        setEndCell(ei, ej);
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                grid[i][j] = new Cell(i, j);
        //Calculate the heuristicCost for each Cell
                grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
            }
        }
        grid[si][sj].finalCost = 0;

/*
Set blocked cells. Simply set the cell values to null
for obstacles.
*/
        for(int i=0;i<blocked.length;++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }
        //Display initial grid map
        System.out.println("Grid: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                if(i==si&&j==sj)System.out.print("ST "); //Source
                else if(i==ei && j==ej)System.out.print("DE "); //Destination
                else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                else System.out.print("BL ");
            }
            System.out.println();
        }
        System.out.println();

// Fill in the underlined place to perform the AStar function
        ______________; //
        //For visualization
        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                else System.out.print("BL ");
            }
            System.out.println();
        }
        System.out.println();
        if(closed[endI][endJ]){
            //Trace back the path
            System.out.println("Path: ");
            Cell current = grid[endI][endJ];
            System.out.print(current);
            while(current.parent!=null){
                System.out.print(" -> "+current.parent);
                current = current.parent;
            }
            System.out.println();
        }else System.out.println("No possible path");
    }
    /*
    public static void main(String[] args) throws Exception{
        //The grid is 4*4, start position is [0,0], destination is [3,3], obstacle's
        location is [2,2]
        test(4, 4, 4, 0, 0, 3, 3, new int[][]{{2,2}});
    }*/
}
