
class NewMap {

	private static final int SIZE = 30;
	private int[][] map = new int[SIZE][SIZE];

	public NewMap() {

		addFixedObstacles();

	}

	public static void main(String[] args) {
		
		NewMap mapleStory = new NewMap();
		mapleStory.insertBlueInner(true);
		mapleStory.insertBlueOuter(true);
		mapleStory.insertGreenObstacle(true);
		mapleStory.insertRedObstacle(true);
		System.out.println(mapleStory.toString());

	}

	private void addFixedObstacles() {

		int cornerVal = 7;
		//adding borders
		for(int i = 0;i < SIZE;i++) {
			//horizontal border
			map[0][i] = -1; map[SIZE - 1][i] = -1;
			//vertical borders
			map[i][0] = -1; map[i][SIZE - 1] = -1;

		}

		//adding corners
		for(int i = 0;i <= 14;i++) {

			for(int j = cornerVal;j > 0;j--) {

				map[i][j] = -1;
				map[SIZE - i - 1][SIZE - j - 1] = -1;

			}

			--cornerVal;

		}
		//adding middle wall
		for(int wall = 11; wall < 19;wall++) {

			map[wall - 1][SIZE - wall - 1] = -1;
			map[wall][SIZE - wall - 1] = -1;
			map[wall + 1][SIZE - wall - 1] = -1;

		}

		//adding goal gate
		for(int sides = 22;sides > 16;sides--) {

			map[sides][28] = -1;
			map[sides][22] = -1;

		}

		//adding base
		for(int base = 23; base < SIZE - 1;base++) {

			map[22][base] = -1;

		}

		//adding goal
		for(int row = 19; row < 22; row++) {

			for(int column = 23; column < 28;column++) {

				map[row][column] = 1;

			}

		}

	}

	public void insertBlueInner(boolean addRemove) {
		//inner left side obstacle
		if(addRemove) {

			map[7][21] = -1;
			map[8][20] = -1; map[8][21] = -1; map[8][22] = -1;
			map[9][21] = -1;

		}

		else {
			//outer left side obstacle
			map[7][21] = 0;
			map[8][20] = 0; map[8][21] = 0; map[8][22] = 0;
			map[9][21] = 0;

		}

	}

	public void insertBlueOuter(boolean addRemove) {

		if(addRemove) {

			map[4][24] = -1;
			map[5][23] = -1; map[5][24] = -1; map[5][25] = -1;
			map[6][24] = -1;

		} else {

			map[4][24] = 0;
			map[5][23] = 0; map[5][24] = 0; map[5][25] = 0;
			map[6][24] = 0;

		}

	}

	public void insertGreenObstacle(boolean addRemove) {

		if(addRemove) {

			map[20][8] = -1;
			map[21][7] = -1; map[21][8] = -1; map[21][9] = -1;
			map[22][8] = -1;

		} else {

			map[20][8] = 0;
			map[21][7] = 0; map[21][8] = 0; map[21][9] = 0;
			map[22][8] = 0;

		}

	}

	public void insertRedObstacle(boolean addRemove) {

		if(addRemove) {

			map[24][4] = -1;
			map[23][5] = -1; map[24][5] = -1; map[25][5] = -1;
			map[24][6] = -1;

		} else {

			map[24][4] = 0;
			map[23][5] = 0; map[24][5] = 0; map[25][5] = 0;
			map[24][6] = 0;

		}

	}


	@Override
	public String toString() {

		String m = "";

		for(int i = 0;i < SIZE;i++) {

			if(i < 10) m += "[" + i + "] ";
			else m += "[" + i + "]"; 

		}

		m += "\n";

		for(int i = 0; i < SIZE;i++) {

			for(int j = 0; j < SIZE;j++) {

				if(map[i][j] == -1) m += "[X] ";
				else if(map[i][j] == 0) m += "[ ] ";
				else m += "[G] ";

			}

			m += "[" + i + "]\n\n";
			//m += "\n\n";

		}

		return m;

	}

}