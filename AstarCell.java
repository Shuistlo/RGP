import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AstarCell extends Point implements Comparable<AstarCell> {
	public int g_cost_ = 0;
	public int h_cost_ = 0;
	public int f_cost_ = 0;
	private boolean is_block_ = false, is_goal_ = false;

	public AstarCell parent = null;
	//change according to needs and double this value for diagonal move cost
	final int MOVE_COST = 1;

	public AstarCell(Point _l) {
		super(_l);
		if ((_l.getX() < 0) || (_l.getY() < 0)) {
			System.out.println("ERROR LOCATION" + _l.toString() + " FOR CELL");
			super.setLocation(0, 0);
		}

		if (this.isBlock()) f_cost_ = Integer.MAX_VALUE;
	}

	public AstarCell(int x, int y, boolean isBlock) {
		super(x, y);
		is_block_ = isBlock;
	}

	public AstarCell(Point _l, boolean is_block) {
		super(_l);
		is_block_ = is_block;

		if ((_l.getX() < 0) || (_l.getY() < 0)) {
			System.out.println("ERROR LOCATION" + _l.toString() + " FOR CELL");
			super.setLocation(0, 0);
		}
	}

	public AstarCell(int x, int y) {
		super(x, y);
	}

	public void setBlock(boolean _b) {

		is_block_ock_ = _b;
		if(_b) {f_cost_ = Integer.MAX_VALUE;}
		else {f_cost_ = 0;}

	}

	public boolean isBlock() { return is_block_; }

	public void setGoal(boolean b) { is_goal_ = b;}

	public boolean isGoal() { return is_goal_;}

	public Point plus(Point n) {
		return new Point( (int)(this.x + n.getX()),(int)(this.y + n.getY()));
	}


	/**
	 * initialize the AstarCell
	 * @param x,y     The location
	 * @param _p     The parent
	 * @param h_cost The H cost
	 */

	public AstarCell(int x, int y, AstarCell _p, boolean _b, int h_cost) {
		super(x,y);
		is_block_ = _b;
		parent = _p;
			
		if (_b) {
			f_cost_ = Integer.MAX_VALUE;
		} else {
			g_cost_ = _p.g_cost_ + MOVE_COST;
			h_cost_ = h_cost;
			f_cost_ = g_cost_ + h_cost_;
		}
	}
	public int f_cost() { return f_cost_; }
		
	public boolean equals(Point _p) {
		return super.equals(_p);
	}
		
	/**
	 * If this.f > _o.f, return -1;
	 * If this.f = _o,f, return  0;
	 * If this.f < _o.f, return +1;
	 */
	@Override
	public int compareTo(AstarCell _o) {
		if (f_cost_ == _o.f_cost_)
			return 0;
		else if (f_cost_ < _o.f_cost_)
			return 1;
		else
			return -1;
	}
}