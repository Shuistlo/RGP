class Map {

	private static final int SIZE = 60;
	private int[][] map = new int[SIZE][SIZE];

	public Map() {

		addFixedObstacles();

	}

	public static void main(String[] args) {
		
		Map mapleStory = new Map();
		mapleStory.insertObstacleRInner();
		mapleStory.insertObstacleROuter();
		mapleStory.insertObstacleLInner();
		mapleStory.insertObstacleLOuter();

		System.out.println(mapleStory.toString());

	}

	private void addFixedObstacles() {
		int cornerVal = 14;
		//insertig borders
		for(int horizontal = 0, vertical = 0;vertical < SIZE;horizontal++, vertical++) {

			//horizontal border
			map[0][horizontal] = -1;
			map[SIZE - 1][horizontal] = -1;
			//vertical border
			map[vertical][0] = -1;
			map[vertical][SIZE - 1] = -1;

		}

		//inserting middle wall
		for(int wall = 22; wall < 37;wall++) {

			map[wall - 1][SIZE - wall - 1] = -1;
			map[wall][SIZE - wall - 1] = -1;
			map[wall + 1][SIZE - wall - 1] = -1;

		}

		//inserting corners
		for(int i = 0;i <= 14;i++) {

			for(int j = cornerVal;j > 0;j--) {

				map[i][j] = -1;
				map[SIZE - i - 1][SIZE - j - 1] = -1;

			}

			--cornerVal;

		}

		//inserting goal gate
		for(int ggSides = 35;ggSides < 46;ggSides++) {

			map[ggSides][SIZE - 2] = -1;
			map[ggSides][SIZE - 14] = -1;

		}

		for(int bottom = 46; bottom < 58;bottom++) {

			map[45][bottom] = -1;

		}

		//inserting goal
		for(int i = 39;i < 45;i++) {

			for(int j = 47;j < 58;j++) {

				map[i][j] = 1;

			}

		}

	}

	public void insertObstacleRInner() {

		for(int tb = 0;tb < 3;tb++) {

			map[41][14 + tb] = -1;
			map[45][14 + tb] = -1;

		}

		for(int i = 0;i < 3;i++) {

			for(int j = 0;j < 5;j++) {

				map[42 + i][13 + j] = -1;

			}

		}

	}

	public void insertObstacleROuter() {

		for(int tb = 0;tb < 3;tb++) {

			map[48][7 + tb] = -1;
			map[52][7 + tb] = -1;
		}

		for(int i = 0;i < 3;i++) {

			for(int j = 0;j < 5;j++) {

				map[49 + i][6 + j] = -1;

			}

		}

	}

	public void insertObstacleLInner() {

		for(int tb = 0;tb < 3;tb++) {

			map[13][42 + tb] = -1;
			map[17][42 + tb] = -1;
		}

		for(int i = 0;i < 3;i++) {

			for(int j = 0;j < 5;j++) {

				map[14 + i][41 + j] = -1;

			}

		}

	}

	public void insertObstacleLOuter() {

		for(int tb = 0;tb < 3;tb++) {

			map[6][49 + tb] = -1;
			map[10][49 + tb] = -1;
		}

		for(int i = 0;i < 3;i++) {

			for(int j = 0;j < 5;j++) {

				map[7 + i][48 + j] = -1;

			}

		}

	}

	@Override
	public String toString() {

		String m = "";

		/*for(int i = 0;i < SIZE;i++) {

			if(i < 10) m += "[" + i + "] ";
			else m += "[" + i + "]"; 

		}

		m += "\n";*/

		for(int i = 0; i < SIZE;i++) {

			for(int j = 0; j < SIZE;j++) {

				if(map[i][j] == -1) m += ("[X] ");
				else if(map[i][j] == 0) m += "[ ] ";
				else m += "[0] ";

			}

			//m += "[" + i + "]\n\n";
			m += "\n\n";

		}

		return m;

	}
	

}