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
	public static final int EMPTY = 0;

	private int state;
	protected int nextState;
	private Shape image;
	protected ParameterBundle parameters;

	private Color[] colors;

	/**
	 * @param initialState
	 */
	public Cell(int initialState, ParameterBundle pars, Color[] colors) {
		state = nextState = initialState;
		parameters = pars;
		this.colors = colors;
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
		updateState();
		if(image != null)
			image.setFill(getColor());
	}
	
	public void updateState() {
		state = nextState;
	}

	public int getState() {
		return state;
	}

	public void acceptImage(Shape image) {
		this.image = image;
		image.setFill(getColor());
		image.setOnMouseClicked(e -> cycle());
	}

	protected abstract void cycle();

	protected Color getColor() {
		return colors[getState()];
	}

	@Override
	public String toString() {
		return "" + getState();
	}

	public Cell getEmptyInstance() {
		try {
			return (Cell) (getClass().getConstructors()[0].newInstance(EMPTY, parameters));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Cell parameters do not agree with (int, parameterBundle");
			// This should never be possible
		}
	}
	
	public abstract String getSimType();
}