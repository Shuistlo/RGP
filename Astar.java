import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//import abc.*;

public class Astar {
	private Point[] neighbors = null;
	List<AstarCell>   opens_    = null;
	List<AstarCell>   closes_   = null;
	
	public Astar() {
		neighbors = new Point[8];
		neighbors[0] = new Point(-1, -1);neighbors[1] = new Point(-1,  0);
		neighbors[2] = new Point(-1, +1);neighbors[3] = new Point(+1, -1);
		neighbors[4] = new Point(+1,  0);neighbors[5] = new Point(+1, +1);
		neighbors[6] = new Point( 0, -1);neighbors[7] = new Point( 0, +1);
		
		opens_  = new ArrayList<AstarCell>();
		closes_ = new ArrayList<AstarCell>();
	}
	
    private int heuristic(Point current, Point target) {
        return (int) (Math.abs(current.getX() - target.getX())
        		+ Math.abs(current.getY() - target.getY()));
    }
    
    ///! find the minimum cost cell, return it and remove it from opens.
    private AstarCell getMinCostCell() {
    	if (opens_.isEmpty()) return null;
    	
    	int idx = 0;
    	AstarCell min = opens_.get(idx);
    	for (int i = 1; i < opens_.size(); ++i) {
    		AstarCell e = opens_.get(i);
    		// if min.f > e.f
    		if (min.compareTo(e) < 0) {
    			idx = i;
    			min = e;
    		}
    	}
    	opens_.remove(idx);
    	return min;
    }
	
	public Stack<Point> planning(BoxMap map, Point start, Point end) {
		opens_.clear();
		closes_.clear();
		opens_.add(new AstarCell(map.getCell(start)));

		AstarCell curr_cell = null;
		while (!opens_.isEmpty()) {
			// find the minimum f_cost object.
			curr_cell = getMinCostCell();
			if (null == curr_cell) return null;
			
			///! The only place to exit while, if have a path.
			if (curr_cell.equals(end)) {
				Stack<Point> path = new Stack<Point>();
				do {
					path.push(curr_cell);
					curr_cell = curr_cell.parent;
				} while (null != curr_cell);
				
				return path;
			}
			
			closes_.add(curr_cell);
			// for each neighbor
			for (Point _n : neighbors) {
				AstarCell _nbor = map.getCell(curr_cell.plus(_n));
				if (null == _nbor) continue;
				
				AstarCell nbor_cell = new AstarCell((int)_nbor.getX(), (int)_nbor.getY(), curr_cell, _nbor.isBlock(), heuristic(_nbor, end));
				int index_open  = opens_.indexOf(_nbor);
				int index_close = closes_.indexOf(_nbor);
				///! not in opens and not in closes
				if ((-1 == index_open) && (-1 == index_close)) {
					opens_.add(nbor_cell);
				///! in opens
				} else if (-1 != index_open) { 
					// get the nbor_open cell from opens
					AstarCell nbor_open = opens_.get(index_open);
					// nbor_open.f > nbor_cell.f
					if (nbor_open.compareTo(nbor_cell) < 0) {
						nbor_open = new AstarCell(nbor_cell);
						// re-insert into opens
						opens_.set(index_open, nbor_cell);
					}
				///! in closes.
				} else { 
					// get the cell from closes
					AstarCell nbor_close = closes_.get(index_close);
					// nbor_close.f > nbor_cell.f
					if (nbor_close.compareTo(nbor_cell) < 0) {
						// remove the nbor_close cell from closes.
						closes_.remove(index_close);
						opens_.add(nbor_cell);
					}
				}
			} // end for each neighbor
		} // end while (!_opens.isEmpty())
		
		return null;
	}
	
	
	
	
		// Just for debug BoxMap
	public static void main(String[] args) {
		BoxMap map = new BoxMap();
		// test block 1
		;
		// test block 2
		map.setBlock(new Point(2, 2), new Point(10, 10));
		// test block 3
		// map.setBlock(new Point(2, 2), new Point(6, 6));
		System.out.println(map);
		
		// test path 1
		System.out.println("test path 1");
		Point start = new Point(0, 0);
		Point end   = new Point(11, 11);
		
		Astar slover = new Astar();
		Stack<Point> path = slover.planning(map, start, end);
		if (null == path) {
			System.out.println("Could not found the path!");
			return;
		}
		
		System.out.println("The path:");
		System.out.println(map.printPath(path));
		
		Point p = path.pop();
		System.out.print(p);
		while (!path.isEmpty()) {
			p = path.pop();
			System.out.print("->" + p);
		}
		System.out.println();
		
		// test path 2
		System.out.println("test path 2");
		start = new Point(4, 1);
		end   = new Point(19, 17);
		path = slover.planning(map, start, end);
		if (null == path) {
			System.out.println("Could not found the path!");
			return;
		}
		
		System.out.println("The path:");
		System.out.println(map.printPath(path));
		
		p = path.pop();
		System.out.print(p);
		while (!path.isEmpty()) {
			p = path.pop();
			System.out.print("->" + p);
		}
		
		// test path 3
		System.out.println("test path 3");
		start = new Point(1, 2);
		end   = new Point(19, 12);
		path = slover.planning(map, start, end);
		if (null == path) {
			System.out.println("Could not found the path!");
			return;
		}
		
		System.out.println("The path:");
		System.out.println(map.printPath(path));
		
		p = path.pop();
		System.out.print(p);
		while (!path.isEmpty()) {
			p = path.pop();
			System.out.print("->" + p);
		}
	}
	
	
}
