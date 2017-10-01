import java.util.ArrayList;
import java.util.List;

import cellsociety_team01.FileCreator;
import javafx.application.Application;
import userInterface.GUI;

public class Main {
	public static void main(String[] args){
		Application.launch(GUI.class, args);
		// Currently for testing
//		String neighbors = "Hex";
//		String simType = "Fire";
//		String gridType = "Hexagon";
//		String defaultState = "1";
//		int numRows = 10;
//		int numColumns = 10;
//		int numStates = 3;
//		double[] parameters = new double[1];
//		parameters[0] = 0.8;
//		List<Integer> gridLocations = null;
//	
//		FileCreator.xmlCreator(neighbors, simType, gridType, defaultState, numRows, numColumns, numStates, parameters, gridLocations);
		//testFireSimulation();
	}
	
	
//	private static void testFireSimulation() throws Exception {
//		Simulation sim = new FileHandler().fileReader("data\\Wator.xml");
//		AbstractGrid cells = sim.getGrid();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//	}
}
