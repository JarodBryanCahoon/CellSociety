import java.util.ArrayList;
import java.util.List;

import cellsociety_team01.FileCreator;
import javafx.application.Application;
import userInterface.GUI;

public class Main {
	public static void main(String[] args){
		
		// Currently for testing, creating xml Files
		//for Fire simulation
		List<Integer> neighbors= new ArrayList<Integer>();
		for(int i = 0; i < 6; i++) {
			neighbors.add(i);
		}
		String simType = "Fire";
		String fileName = "Test";
		String gridType = "Hexagon";
		int numRows = 10;
		int numColumns = 10;
		double[] parameters = new double[1];
		parameters[0] = 0.8;
		List<Integer> gridLocations = null;
		FileCreator.xmlCreator(fileName, neighbors, simType, gridType, numRows, numColumns, parameters, gridLocations);

		//Application.launch(GUI.class, args);

	}

	
}
	

