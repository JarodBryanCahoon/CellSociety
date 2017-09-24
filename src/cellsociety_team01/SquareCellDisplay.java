package cellsociety_team01;

import cells.Cell;
import grids.SquareGrid;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Ian Eldridge-Allegra
 */
public class SquareCellDisplay{
	private SquareGrid cells;
	private Rectangle[][] images;
	private Color[] colors;
	
	/**
	 * @param cells The cells to display
	 * @param colors Possible colors to use
	 */
	public SquareCellDisplay(SquareGrid cells, Color[] colors) {
		this.cells = cells;
		this.colors = colors;
	}
	
	/**
	 * @return An array of Rectangles representing states of cells by colors
	 */
	public Rectangle[][] constructImages(){
		images = new Rectangle[cells.getHeight()][cells.getWidth()];
		for(int row = 0; row < cells.getHeight(); row++) {
			for(int col = 0; col < cells.getWidth(); col++) {
				images[row][col] = new Rectangle(300/cells.getHeight(),300/cells.getWidth(),toColor(cells.get(row,col)));
			}
		}
		return images;
	}
	
	/**
	 * @param cell 
	 * @return Color of cell, based on state
	 */
	private Color toColor(Cell cell) {
		return colors[cell.getState()];
	}

	/**
	 * Updates the images
	 */
	public void update() {
		for(int row = 0; row < cells.getHeight(); row++) {
			for(int col = 0; col < cells.getWidth(); col++) {
				images[row][col].setFill(toColor(cells.get(row,col)));
			}
		}
	}
}