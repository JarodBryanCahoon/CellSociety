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
	
	public SquareCellDisplay(SquareGrid cells, Color[] colors) {
		this.cells = cells;
		this.colors = colors;
	}
	
	public Rectangle[][] constructImages(){
		images = new Rectangle[cells.getHeight()][cells.getWidth()];
		for(int row = 0; row < cells.getHeight(); row++) {
			for(int col = 0; col < cells.getWidth(); col++) {
				images[row][col] = new Rectangle(1,1,toColor(cells.get(row,col)));
			}
		}
		return images;
	}
	
	private Color toColor(Cell cell) {
		return colors[cell.getState()];
	}

	public void update() {
		for(int row = 0; row < cells.getHeight(); row++) {
			for(int col = 0; col < cells.getWidth(); col++) {
				images[row][col].setFill(toColor(cells.get(row,col)));
			}
		}
	}
}