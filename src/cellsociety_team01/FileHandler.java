package cellsociety_team01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import grids.AbstractGrid;
import simulations.Simulation;
import cellsociety_team01.FileCreator;


public class FileHandler {
	
	public FileHandler() {	
	}
	
	public static Simulation fileReader(String file) {
		
		List<Object> initial = new ArrayList<Object>();
		String simulation = null;
		int rows = 0;
		int columns = 0;
		String[] locations = null;
		Simulation sim = null;
		try {
		if(!(new File(file).exists())) {
			file = "data\\Fire.xml";
			System.out.println("Incorrect File Name");
		}
		NodeList nList = nodeList(file);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				simulation = eElement.getElementsByTagName("simulation").item(0).getTextContent();
				rows = Integer.parseInt(eElement.getElementsByTagName("rows").item(0).getTextContent());
				columns = Integer.parseInt(eElement.getElementsByTagName("columns").item(0).getTextContent());
				locations = eElement.getElementsByTagName("locations").item(0).getTextContent().split(",");
				String values = eElement.getElementsByTagName("values").item(0).getTextContent();
				
				String[] valueArray = values.split(","); 
				for(int i = 0; i < valueArray.length; i++) {
					try{
						initial.add(Integer.parseInt(valueArray[i]));
					}
					catch(Exception e) {
						initial.add(Double.parseDouble(valueArray[i]));
					}
				}		
			}
		}
		Initializer init = new Initializer(simulation);
		
		Object[] argumentArray = initial.toArray();
		AbstractGrid cells = arrayCreator(argumentArray, init, rows, columns, locations);
		sim = init.getSimulation(cells);
		}
		catch(Exception e) {
			System.out.println("Incorrect File Name");
		}
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
