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
	
	public void xmlCreator(String simType, int numRows, int numColumns, int numStates, double[] parameters, List<Integer> locations) {
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
		
		Element stateGrid = doc.createElement("grid");
		if(!(locations==null)) {
			ArrayList<Integer> locationsAL = (ArrayList<Integer>) locations;
			for(int i = 0; i < 6 ; i++) {
				locationsAL.add(i);
			}
			String[] locArray = locationsAL.toString().split("\\[");
			locArray[0] = locArray[1].split("]")[0];
			stateGrid.appendChild(doc.createTextNode(locArray[0]));
		}
		else {
			stateGrid.appendChild(doc.createTextNode(xmlGridCreator(numRows, numColumns, numStates)));
		}
		rootElement.appendChild(stateGrid);

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
			StreamResult result = new StreamResult(new File("\\data\\" + simType + ".xml"));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
			System.out.println("File not created properly");
		}
	}	
	
	
}


