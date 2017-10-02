package cellsociety_team01;

import java.util.ResourceBundle;

import cells.Cell;
import grids.AbstractGrid;
import simulations.Simulation;

/**
 * @author Ian Eldridge-Allegra
 */
public class Initializer {
	private static final ResourceBundle classNames = ResourceBundle.getBundle("resources/ClassNameBundle");
	private static final ResourceBundle errors = ResourceBundle.getBundle("resources/ErrorBundle");
	private String simulationType;
	private String gridType;

	/**
	 * @param simType
	 *            EG: 'Fire', 'Seg', 'Life', 'Wator'
	 * @param gridType
	 *            EG: Square, HexagonTor, HexagonInf
	 */
	public Initializer(String simType, String gridType) {
		simulationType = simType;
		this.gridType = gridType;
	}

	/**
	 * @param args
	 *            The expected argument for the given class
	 */
	public Simulation getSimulation(Object... args) throws IllegalArgumentException {
		try {
			return (Simulation) instantiate(simulationType + "S", args);
		} catch (Exception e) {
			throw new IllegalArgumentException(errors.getString("IllSim"));
		}
	}

	/**
	 * @param args
	 *            The expected argument for the given class
	 */
	public AbstractGrid getGrid(Object... args) throws IllegalArgumentException {
		try {
			return (AbstractGrid) instantiate(gridType, args);
		} catch (Exception e) {
			throw new IllegalArgumentException(errors.getString("IllGrid"));
		}
	}

	/**
	 * @param args
	 *            The expected argument for the given class
	 */
	public Cell getCell(Object... args) throws IllegalArgumentException {
		try {
			return (Cell) instantiate(simulationType + "C", args);
		} catch (Exception e) {
			throw new IllegalArgumentException(errors.getString("IllCell"));
		}
	}

	private Object instantiate(String classType, Object... args) throws Exception {
		return Class.forName(classNames.getString(classType)).getConstructors()[0].newInstance(args);
	}
}
