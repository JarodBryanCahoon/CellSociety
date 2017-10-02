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

import cells.ParameterBundle;
import grids.AbstractGrid;
import simulations.Simulation;
import cellsociety_team01.FileCreator;


public class FileHandler {
	
	public FileHandler() {	
	}
	
	public static Simulation fileReader(String file) throws ParserConfigurationException, SAXException, IOException {
		/*
		 * arbitrary number used to avoid NullPointerException
		 * used for simulations without default parameters (Life, Langton, etc.)
		 */
		String LifeString = "99";
		/*
		 * initializes the data structures necessary to create the simulation and the cells
		 */
		List<Object> initial = new ArrayList<Object>();
		List<Integer> neighbors = new ArrayList<Integer>();
		String simulation = null;
		String gridType = null;
		int defaultState = 0;
		int rows = 0;
		int columns = 0;
		String[] locations = null;
		Simulation sim = null;
		//provides a file for the method to read and pass back a nodeList to decipher
		NodeList nList = nodeList(file);
		//iterates through the nodeList, can be extended for higher complexity xml files
		for (int temp = 0; temp < nList.getLength(); temp++) {	
			
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				//records simulation type
				simulation = eElement.getElementsByTagName("simulation").item(0).getTextContent();
				//records gridType
				gridType = eElement.getElementsByTagName("gridType").item(0).getTextContent();
				//records the number of rows for the desired simulation
				rows = Integer.parseInt(eElement.getElementsByTagName("rows").item(0).getTextContent());
				//gets the number of columns from the xml file
				columns = Integer.parseInt(eElement.getElementsByTagName("columns").item(0).getTextContent());
				//gets a list of states
				locations = eElement.getElementsByTagName("locations").item(0).getTextContent().split(",");
				/*
				 * gets the list of important neighbors
				 * will be in a style file for our created files
				 * will be contained in the original file if this is going through a saved file
				 */
				String neighborType = eElement.getElementsByTagName("neighbors").item(0).getTextContent();
				String[] neighborArray = neighborType.split(",");
				//indirectly checks to see if the file is saved by checking the type of information stored in neighbors
				if(neighborArray.length > 1) {
					for(int i = 0; i < neighborArray.length; i++) {
							neighbors.add(Integer.parseInt(neighborArray[i]));
					}
				}
				else {
					neighbors = getNeighborList(neighborType);
				}
				//obtains a list of values used in the simulaion, such as probCatch
				//some values are ints and some are doubles, so they need to be parsed using both types
				String values = eElement.getElementsByTagName("values").item(0).getTextContent();
				if(!(values.equals(LifeString))) {
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
		}
		//creates an initilaizer object to help in creating
		Initializer init = new Initializer(simulation, gridType);
		
		Object[] argumentArray = initial.toArray();
		//creates a bundle of parameters that are necessary for the given simulation type
		ParameterBundle parameters = new ParameterBundle(simulation, argumentArray);
		//creates an abstract grid to pass to the simulation
		AbstractGrid cells = arrayCreator(neighbors, parameters, init, rows, columns, locations);
		//creates a simulation
		sim = init.getSimulation(cells, parameters);
		return sim;
	}
	/*
	 * given a file name, it gives back a nodeList corresponding to the elements in the file
	 */
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
	// creates a grid based upon the initial conditions provided by the xml file
	public static AbstractGrid arrayCreator(List<Integer> neighbors, ParameterBundle parameters, Initializer init, int rows, int columns, String[] locations) {
		
		AbstractGrid cellArray = init.getGrid(rows, columns, neighbors);
		int defaultState = 0;
		
		for(int i = 0; i < cellArray.getSize(); i++) {
			defaultState = Integer.parseInt(locations[i]);
					cellArray.set(init.getCell(defaultState, parameters), i);
		}
		
		return cellArray;
	}
	// used to read in the list of neighbors from a style file 
	public static List<Integer> getNeighborList(String fileName) throws ParserConfigurationException, SAXException, IOException {
		List<Integer> neighbors = new ArrayList<Integer>();
		String neighborString = null;
		NodeList neighborNodeList = nodeList("data\\" + fileName + ".xml");
		
		for (int temp = 0; temp < neighborNodeList.getLength(); temp++) { 
			
			Node nNode = neighborNodeList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				neighborString = eElement.getElementsByTagName("neighbors").item(0).getTextContent();
				String[] neighborArray = neighborString.split(","); 
				for(int i = 0; i < neighborArray.length; i++) {
						neighbors.add(Integer.parseInt(neighborArray[i]));
				}
			}
		}
		return neighbors;
	}

	
}
