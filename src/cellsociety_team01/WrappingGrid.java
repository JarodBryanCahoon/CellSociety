package cellsociety_team01;

public class WrappingGrid extends SquareGrid {
	int numRows;
	int numCols;

	public WrappingGrid(int rows, int cols) {
		super(rows, cols);
		numRows = rows;
		numCols = cols;
	}

	@Override
	public Cell get(int row, int col) {
		return super.get(row%numRows, col%numCols);
	}
}
