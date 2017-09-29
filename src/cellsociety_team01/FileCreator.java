package cellsociety_team01;
/**
 * 
 */

import java.io.File;
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
	
	public void xmlCreator(String simType, int numRows, int numColumns, int numStates, double[] parameters) {
		/* 
		 * parameters will hold all other values 
		 * eg. Fire will need the double "probCatch"
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

		Element probCatch = doc.createElement("probCatch");
		probCatch.appendChild(doc.createTextNode("" + parameters[0]));
		rootElement.appendChild(probCatch);
		
		Element stateGrid = doc.createElement("grid");
		stateGrid.appendChild(doc.createTextNode(xmlGridCreator(numRows, numColumns, numStates)));
		rootElement.appendChild(stateGrid);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("\\data\\" + simType + ".xml"));
		transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			System.out.println("Incorrect parameters provided");	
			System.out.println("Check the number and type of values given to this class");
		} 
		catch (TransformerException tfe) {
			System.out.println("File not created properly");
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
	
	
	
}


