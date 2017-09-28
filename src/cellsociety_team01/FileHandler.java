package cellsociety_team01;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import grids.AbstractGrid;
import grids.SquareGrid;
import simulations.Simulation;


public class FileHandler {
	
	public FileHandler() {	
	}
	
	public static Simulation fileReader(String file) throws Exception {
		List<Object> initial = new ArrayList<Object>();
		NodeList nList = nodeList(file);
		String simulation = null;
		int state, rows = 0,columns = 0;
		String[] locations = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				simulation = eElement.getElementsByTagName("simulation").item(0).getTextContent();
				state = Integer.parseInt(eElement.getElementsByTagName("state").item(0).getTextContent());
				rows = Integer.parseInt(eElement.getElementsByTagName("rows").item(0).getTextContent());
				columns = Integer.parseInt(eElement.getElementsByTagName("columns").item(0).getTextContent());
				locations = eElement.getElementsByTagName("locations").item(0).getTextContent().split(",");
				initial.add(0);
				if(simulation.equals("Fire")) {
					double probCatch = Double.parseDouble(eElement.getElementsByTagName("probCatch").item(0).getTextContent());
					initial.add(probCatch);

				}
				if(simulation.equals("Seg")) {
					double threshold = Double.parseDouble(eElement.getElementsByTagName("threshold").item(0).getTextContent());
					initial.add(threshold);
				
				}
				if(simulation.equals("Wator")) {
					int sharkLife = Integer.parseInt(eElement.getElementsByTagName("sharkLife").item(0).getTextContent());
					int sharkSpawn = Integer.parseInt(eElement.getElementsByTagName("sharkSpawn").item(0).getTextContent());
					int fishSpawn = Integer.parseInt(eElement.getElementsByTagName("fishSpawn").item(0).getTextContent());
					int energyGain = Integer.parseInt(eElement.getElementsByTagName("energyGain").item(0).getTextContent());
					initial.add(sharkLife);
					initial.add(sharkSpawn);
					initial.add(fishSpawn);
					initial.add(energyGain);
				}
				
				
			}
		}
		
		Initializer init = new Initializer(simulation);
		
		Object[] argumentArray = initial.toArray();
		AbstractGrid cells = arrayCreator(argumentArray, init, rows, columns, locations);
		//Will probably change if tree to something more flexible
		Simulation sim = init.getSimulation(cells);
		return sim;
	}

	private static NodeList nodeList(String file) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document xmlDoc = dBuilder.parse(fXmlFile);
		xmlDoc.getDocumentElement().normalize();
		NodeList nList = xmlDoc.getElementsByTagName("Simulation");
		return nList;
	}

	public static AbstractGrid arrayCreator(Object[] argumentArray, Initializer init, int rows, int columns, String[] locations) throws Exception {
		
		AbstractGrid cellArray = init.getGrid(rows, columns);
		
		for(int i = 0; i < cellArray.getSize(); i++) {
			argumentArray[0] = Integer.parseInt(locations[i]);
					cellArray.set(init.getCell(argumentArray), i);
		}
		
		return cellArray;
	}

	
}
