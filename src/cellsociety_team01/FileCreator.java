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

	public FileCreator() {
		//Intentionally left blank
	}
	
	public void xmlCreator(List<Integer> neighborList, String simType, int numRows, int numColumns, int numStates, double[] parameters, List<Integer> gridLocations) {
		/* 
		 * parameters will hold all other values 
		 * e.g. Fire will need the double "probCatch"
		 */
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("simulation");
		doc.appendChild(rootElement);
		
		Element simulation = doc.createElement("Simulation");
		simulation.appendChild(doc.createTextNode(simType));
		rootElement.appendChild(simulation);

		Element rows = doc.createElement("rows");
		rows.appendChild(doc.createTextNode("" + numRows));
		rootElement.appendChild(rows);

		Element columns = doc.createElement("columns");
		columns.appendChild(doc.createTextNode("" + numColumns));
		rootElement.appendChild(columns);

		Element values = doc.createElement("values");
		StringBuilder valueString = new StringBuilder();
		for(int i = 0; i < parameters.length; i++) {
			valueString.append("" + parameters[i] + ",");
		}
		valueString.append("" + parameters[parameters.length - 1]);
		values.appendChild(doc.createTextNode("" + valueString.toString()));
		rootElement.appendChild(values);
		
		Element locations = doc.createElement("locations");
		if(!(gridLocations==null)) {
			ArrayList<Integer> locationsAL = (ArrayList<Integer>) gridLocations;
			for(int i = 0; i < 6 ; i++) {
				locationsAL.add(i);
			}
			String[] locArray = locationsAL.toString().split("\\[");
			locArray[0] = locArray[1].split("]")[0];
			locations.appendChild(doc.createTextNode(locArray[0]));
		}
		else {
			locations.appendChild(doc.createTextNode(xmlGridCreator(numRows, numColumns, numStates)));
		}
		rootElement.appendChild(locations);
		
		Element neighbors = doc.createElement("neighbors");
		if(!(neighborList==null)) {
			ArrayList<Integer> neighborAL = (ArrayList<Integer>) neighborList;
			for(int i = 0; i < 6 ; i++) {
				neighborAL.add(i);
			}
			String[] neighborArray = neighborAL.toString().split("\\[");
			neighborArray[0] = neighborArray[1].split("]")[0];
			locations.appendChild(doc.createTextNode(neighborArray[0]));
		}
		rootElement.appendChild(neighbors);

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
	
		for (int i = 0; i < gridSize - 1; i++) {
			state = stateGenerator.nextInt(numStates) + 1;
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
			StreamResult result = new StreamResult(new File("\\data\\" + simType + "Saved.xml"));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			System.out.println("File not created properly");
		}
	}	
	
	
}


