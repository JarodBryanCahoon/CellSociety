package cells;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Represents individual cells in the simulation's grid. They are responsible
 * for most of the logic of the simulation, but may require assistance when
 * moving
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class Cell {
	private int state;
	protected int nextState;
	private Shape image;
	protected ParameterBundle parameters;
	
	private Color[] colors;

	/**
	 * @param initialState
	 */
	public Cell(int initialState, ParameterBundle pars) {
		state = nextState = initialState;
		parameters = pars;
	}

	/**
	 * Sets the next state of the cells based on the rules of the simulation
	 * 
	 * @param neighborhood
	 *            The cells surrounding this one -- depends on simulation
	 * @param parameters 
	 */
	public abstract void step(List<Cell> neighborhood);

	/**
	 * Updates state to nextState
	 */
	public void update() {
		state = nextState;
		image.setFill(getColor());
	}

	public int getState() {
		return state;
	}
	
	public void acceptImage(Shape image) {
		this.image = image;
		image.setFill(getColor());
	}
	
	protected void setColors(Color ... colors) {
		this.colors = colors;
	}
	
	protected Color getColor() {
		return colors[getState()];
	}
	
	@Override
	public String toString() {
		return "" + getState();
	}
}