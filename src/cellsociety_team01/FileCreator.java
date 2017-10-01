package cellsociety_team01;
/**
 * 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Keegan O'Reilly
 *
 */
public class FileCreator {

	public FileCreator(String neighborList, String simType, String gType, String defaultState,int numRows, int numColumns, int numStates, double[] parameters, List<Integer> gridLocations) {
		//Intentionally left blank
	}
	
	public static void xmlCreator(List<Integer> neighborList, String simType, String gType, String defaultState, int numRows, int numColumns, int numStates, double[] parameters, List<Integer> gridLocations) {
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		//creates a document with an initial element node
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Simulation");
		doc.appendChild(rootElement);
		//stores the name of the simulation type
		Element simulation = doc.createElement("simulation");
		simulation.appendChild(doc.createTextNode(simType));
		rootElement.appendChild(simulation);
		//stores the grid type
		Element gridType = doc.createElement("gridType");
		gridType.appendChild(doc.createTextNode(gType));
		rootElement.appendChild(gridType);
		//stores a default state 
		Element state = doc.createElement("state");
		state.appendChild(doc.createTextNode(defaultState));
		rootElement.appendChild(state);
		//stores a string representing the list of noteworthy neighbors
		Element neighbors = doc.createElement("neighbors");
		StringBuilder neighborString = new StringBuilder();
		for(int i = 0; i < neighborList.size() - 1; i++) {
			neighborString.append("" + neighborList.get(i) + ",");
		}
		if(neighborList.size() > 1) {
		neighborString.append("" + neighborList.get(neighborList.size() - 1));
		}
		neighbors.appendChild(doc.createTextNode("" + neighborString.toString()));
		rootElement.appendChild(neighbors);
		//stores the number of rows in the initial simulation
		Element rows = doc.createElement("rows");
		rows.appendChild(doc.createTextNode("" + numRows));
		rootElement.appendChild(rows);
		//stores the initial number of columns in the simulation
		Element columns = doc.createElement("columns");
		columns.appendChild(doc.createTextNode("" + numColumns));
		rootElement.appendChild(columns);
		//catch-all array to store various values held in the different simulations
		Element values = doc.createElement("values");
		StringBuilder valueString = new StringBuilder();
		for(int i = 0; i < parameters.length; i++) {
			valueString.append("" + parameters[i] + ",");
		}
		if(parameters.length > 1) {
		valueString.append("" + parameters[parameters.length - 1]);
		}
		values.appendChild(doc.createTextNode("" + valueString.toString()));
		rootElement.appendChild(values);
		/*
		 * stores the locations of the cells
		 * generates a random grid if no grid is given
		 */
		Element locations = doc.createElement("locations");
		if(!(gridLocations==null)) {
			StringBuilder locationString = new StringBuilder();
			for(int i = 0; i < gridLocations.size() - 1; i++) {
				locationString.append("" + gridLocations.get(i) + ",");
			}
			if(gridLocations.size() > 1) {
			locationString.append("" + gridLocations.get(gridLocations.size() - 1));
			}
			locations.appendChild(doc.createTextNode("" + locationString.toString()));
			rootElement.appendChild(locations);
		}
		else {
			locations.appendChild(doc.createTextNode(xmlGridCreator(numRows, numColumns, numStates)));
		}
		rootElement.appendChild(locations);

		// write the content into xml file
		writeXml(doc, simType);
		} catch (ParserConfigurationException pce) {
			System.out.println("Incorrect parameters provided");	
			System.out.println("Check the number and type of values given to this class");
		} 
	}

	public static String xmlGridCreator(int rows, int columns, int numStates ) {
		int gridSize = rows*columns;
		StringBuilder stateArray = new StringBuilder();
		int state = 0;
		Random stateGenerator = new Random();
		//creates a 
		for (int i = 0; i < gridSize - 1; i++) {
			state = stateGenerator.nextInt(numStates) ;
			stateArray.append("" + state + ",");
		}
		state = stateGenerator.nextInt(numStates) + 1;
		stateArray.append("" + state);
	
		return stateArray.toString();
	}


	public static void writeXml(Document doc, String simType) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data\\" + simType + ".xml"));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			System.out.println("File not created properly");
		}
	}	
	
	
}


