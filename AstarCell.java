import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AstarCell extends Cell implements Comparable<AstarCell> {
	public int g_cost_ = 0;
	public int h_cost_ = 0;
	public int f_cost_ = 0;
	//???
	public AstarCell parent = null;
	//change according to needs and double this value for diagonal move cost
	final int MOVE_COST = 1;
		
//	public AstarCell(unused.Point _p) {
//			super(_p);
//	}
//		
//	public AstarCell(unused.Point _c, AstarCell _p) {
//		super(_c);
//			
//	}
//		
	public AstarCell(Cell _c) {
		super(_c);
		if (_c.isBlock()) f_cost_ = Integer.MAX_VALUE;
	}

	public AstarCell(int x, int y, boolean isBlock) {

		super(x, y, isBlock);

	}

	public void setBlock(boolean _b) {

		setBlock(_b);
		if(_b) {f_cost_ = Integer.MAX_VALUE;}
		else {f_cost_ = 0;}

	}

	/**
	 * initialize the AstarCell
	 * @param _c     The location
	 * @param _p     The parent
	 * @param h_cost The H cost
	 */
	public AstarCell(Cell _c, AstarCell _p, int h_cost) {
		super(_c);
		parent = _p;
			
		if (_c.isBlock()) {
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