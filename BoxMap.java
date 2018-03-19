import unused.Cell;

import java.awt.Point;

import java.util.Stack;
import java.util.function.Consumer;

/*!
 * @brief The class for BoxMap, the origin is placed at the left top. example as:
 * 			0 1 2 3 4 -- y
 *          1
 *          2
 *          3
 *          4
 *          |
 *          x
 */
public class BoxMap {	
	///! the data of map
	private static final int SIZE  = 30;
	private AstarCell[][] map = null;
	private NewMap mapModel = new NewMap();
	//if blue = true, blue inner obstacle is present else blue outer
	//redGreen = true means red obstacle is present else green
	private boolean blue = true, redGreen = true;
	//removed the parameters as they were not necessary
	public BoxMap() {
		//creating empty BoxMap of Coordinates
		map = new AstarCell[SIZE][SIZE];

		for (int w = 0; w < SIZE; ++w) {

			for (int h = 0; h < SIZE; ++h) {

				if(mapModel.getMap()[w][h] == -1) {

					map[w][h] = new AstarCell(w, h, true);

				} else {map[w][h] = new AstarCell(w, h, false);}

			}

		}

		map[15][25].setGoal(true);

	}
	
	public void forEachNeighbour(final Cell _p, Consumer<Cell> action) {
		action.accept(new Cell(_p));
	}
	
	public AstarCell getCell(Point p) {
		if ((p.getX() < 0) || (p.getX() >= SIZE) ||
				(p.getY() < 0) || (p.getY() >= SIZE)) {
			// System.out.println("ERROR LOCATION" + p.toString() + " FOR CELL");
			return null;
		}
		return map[(int)p.getX()][(int)p.getY()];
	}
	
	public void setBlock(int x, int y/*, boolean _b*/) { map[x][y].setBlock(true); }
	public void setBlock(int x, int y, boolean _b)     { map[x][y].setBlock(_b); }
	/**
	 * Set the block in the left-top to right-bottom.
	 */
	public void setBlock(Point _lt, Point _rb) {
		for (int x = (int)_lt.getX(); x < _rb.getX(); ++x) {
			for (int y = (int)_lt.getY(); y < (int)_rb.getY(); ++y) {
				setBlock(x, y);
			}
		}
	}
	public void setBlock(Point _lt, Point _rb, boolean _b) {
		for (int x = (int)_lt.getX(); x < (int)_rb.getX(); ++x) {
			for (int y = (int)_lt.getY(); y < (int)_rb.getY(); ++y) {
				setBlock(x, y, _b);
			}
		}
	}
	
	public String printPath(Stack<Point> path) {
		String ret = "";
		for (int w = 0; w < SIZE; ++w) {
			for (int h = 0; h < SIZE; ++h) {
				if (map[w][h].isBlock())
					ret += "*";
				else if (-1 != path.indexOf(new Point(w, h)))
					ret += "-";
				else
					ret += "o";
			}
			ret += "\n";
		}
		return ret;
	}

	public void insertBlueInner(boolean addRemove) {
		//inner left side obstacle
		if(addRemove) {

			map[7][21].setBlock(true);
			map[8][20].setBlock(true); map[8][21].setBlock(true); map[8][22].setBlock(true);
			map[9][21].setBlock(true);

		}

		else {
			//outer left side obstacle
			map[7][21].setBlock(false);
			map[8][20].setBlock(false); map[8][21].setBlock(false); map[8][22].setBlock(false);
			map[9][21].setBlock(false);

		}

	}

	public void insertBlueOuter(boolean addRemove) {

		if(addRemove) {

			map[4][24].setBlock(true);
			map[5][23].setBlock(true); map[5][24].setBlock(true); map[5][25].setBlock(true);
			map[6][24].setBlock(true);

		} else {

			map[4][24].setBlock(false);
			map[5][23].setBlock(false); map[5][24].setBlock(false); map[5][25].setBlock(false);
			map[6][24].setBlock(false);

		}

	}

	public void insertGreenObstacle(boolean addRemove) {

		if(addRemove) {

			map[20][8].setBlock(true);
			map[21][7].setBlock(true); map[21][8].setBlock(true); map[21][9].setBlock(true);
			map[22][8].setBlock(true);

		} else {

			map[20][8].setBlock(false);
			map[21][7].setBlock(false); map[21][8].setBlock(false); map[21][9].setBlock(false);
			map[22][8].setBlock(false);

		}

	}

	public void insertRedObstacle(boolean addRemove) {

		if(addRemove) {

			map[24][4].setBlock(true);
			map[23][5].setBlock(true); map[24][5].setBlock(true); map[25][5].setBlock(true);
			map[24][6].setBlock(true);

		} else {

			map[24][4].setBlock(false);
			map[23][5].setBlock(false); map[24][5].setBlock(false); map[25][5].setBlock(false);
			map[24][6].setBlock(false);

		}

	}
	
	public void wallOffRight(boolean wallVal) {

		if(wallVal) {

			for(int wall = 19; wall < SIZE - 1;wall++) {

				map[wall - 1][SIZE - wall - 1].setBlock(true);
				map[wall][SIZE - wall - 1].setBlock(true);
				map[wall + 1][SIZE - wall - 1].setBlock(true);

			}

		} else {

			for(int wall = 19; wall < SIZE - 1;wall++) {

				map[wall - 1][SIZE - wall - 1].setBlock(false);
				map[wall][SIZE - wall - 1].setBlock(false);
				map[wall + 1][SIZE - wall - 1].setBlock(false);
				map[SIZE - 1][1].setBlock(true);
				if(redGreen) {insertRedObstacle(true);}
				else {insertGreenObstacle(true);}
				
			}

		}

	}


	public void wallOffLeft(boolean wallVal) {

		if(wallVal) {

			for(int wall = 1; wall < 11;wall++) {

				map[wall - 1][SIZE - wall - 1].setBlock(true);
				map[wall][SIZE - wall - 1].setBlock(true);
				map[wall + 1][SIZE - wall - 1].setBlock(true);

			}

		} else {

			for(int wall = 1; wall < 11;wall++) {

				map[wall - 1][SIZE - wall - 1].setBlock(false);
				map[wall][SIZE - wall - 1].setBlock(false);
				map[wall + 1][SIZE - wall - 1].setBlock(false);
				map[0][SIZE - 2].setBlock(true);
				if(blue) {insertBlueInner(true);}
				else {insertBlueOuter(true);}

			}

		}

	}

	@Override
	public String toString() {
		String ret = "";
		for (int w = 0; w < SIZE; ++w) {
			for (int h = 0; h < SIZE; ++h) {
				if (map[w][h].isBlock())
					ret += "[X] ";
				else if(map[w][h].isGoal())
					ret += "[G] ";
				else
					ret += "[ ] ";
			}
			ret += "\n\n";
		}
		return ret;
	}
	
	
	
	
	//Just for debug BoxMap
	public static void main(String[] args) {
		BoxMap map = new BoxMap();
		map.wallOffLeft(true);
		map.wallOffLeft(false);
		map.wallOffRight(true);
		map.wallOffRight(false);
		System.out.println(map.toString());
		/*map.setBlock(0, 1);
		map.setBlock(0, 2);
		map.setBlock(0, 3);
		map.setBlock(1, 1);
		
		map.setBlock(new unused.Point(2,2), new unused.Point(4,4));
		
//		map.forEachNeighbour(new unused.Point(100, 100), (_p) -> {
//			System.out.println("foreach: [" + _p.getX() + ", " + _p.getY() + "]");
//		});
		
		System.out.println(map);
		*/
	}
	//*/
}
