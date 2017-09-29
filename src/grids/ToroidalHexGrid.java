package grids;

import java.util.ResourceBundle;

import cells.Cell;

public class ToroidalHexGrid extends HexGrid {

	public ToroidalHexGrid(int rows, int cols) {
		super(rows, cols);
		if(rows%2!=0)
			throw new IllegalArgumentException(ResourceBundle.getBundle("ErrorBundle").getString("HexToroidError"));
	}

	@Override
	public Cell get(int row, int col) {
		return super.get(mod(row, getHeight()), mod(col, getWidth()));
	}
	
	private int mod(int a, int b) {
		return ((a%b)+b)%b;
	}
}
