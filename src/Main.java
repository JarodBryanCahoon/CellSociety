import java.util.ArrayList;
import java.util.List;

import cellsociety_team01.FileCreator;
import javafx.application.Application;
import userInterface.GUI;

public class Main {
	public static void main(String[] args){
		
		// Currently for testing, creating xml Files
		//for Fire simulation
		String neighbors = "Hex";
		String simType = "Fire";
		String gridType = "Hexagon";
		String defaultState = "0";
		int numRows = 10;
		int numColumns = 10;
		int numStates = 3;
		double[] parameters = new double[1];
		parameters[0] = 0.8;
		List<Integer> gridLocations = null;
		FileCreator.xmlCreator(neighbors, simType, gridType, defaultState, numRows, numColumns, numStates, parameters, gridLocations);

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

	
}
	

