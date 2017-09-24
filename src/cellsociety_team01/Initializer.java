package cellsociety_team01;

import java.util.ResourceBundle;

import cells.Cell;
import grids.AbstractGrid;
import simulations.Simulation;

public class Initializer {
	private static final ResourceBundle classNames = ResourceBundle.getBundle("resources/ClassNameBundle");
	
	private String simulationType;
	
	/**
	 * @param simType EG: 'Fire', 'Seg', 'Life', 'Wator' 
	 */
	public Initializer(String simType) {
		simulationType = simType;
	}
	
	/**
	 * @param args The expected argument for the given class
	 */
	public Simulation getSimulation(Object ... args) throws Exception {
		return (Simulation) instantiate(simulationType + "S", args);
	}
	
	/**
	 * @param args The expected argument for the given class
	 */
	public AbstractGrid getGrid(Object ... args) throws Exception {
		return (AbstractGrid) instantiate(simulationType + "G", args);
	}
	
	/**
	 * @param args The expected argument for the given class
	 */
	public Cell getCell(Object ... args) throws Exception {
		return (Cell) instantiate(simulationType + "C", args);
	}
	
	private Object instantiate(String classType, Object ... args) throws Exception{
		return Class.forName(classNames.getString(classType)).getConstructors()[0].newInstance(args);
	}
}
