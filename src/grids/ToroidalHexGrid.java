package grids;

import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;

/**
 * Hexagonal grid, but simulating periodic tiling
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ToroidalHexGrid extends HexGrid {

	public ToroidalHexGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
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
	
	@Override
	public String getType() {
		return "HexagonTor";
	}
}
