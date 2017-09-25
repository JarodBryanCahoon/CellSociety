package cellsociety_team01;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import grids.AbstractGrid;
import grids.SquareGrid;
import simulations.Simulation;


public class FileHandler {

	private static String simulation;
	private static int rows;
	private static int columns;
	private static double probCatch;
	private static int state;
	private static String fileName;
	private static double threshold;
	private static int sharkLife;
	private static int sharkSpawn;
	private static int fishSpawn;
	private static ArrayList initial = new ArrayList();
	private static Object[] argumentArray;
	
	private static String[] locations;

	public FileHandler() {	
	}
	
	public static Simulation fileReader(String file) throws Exception {
		initial = new ArrayList();
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document xmlDoc = dBuilder.parse(fXmlFile);
		xmlDoc.getDocumentElement().normalize();
		NodeList nList = xmlDoc.getElementsByTagName("Simulation");
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
					probCatch = Double.parseDouble(eElement.getElementsByTagName("probCatch").item(0).getTextContent());
					initial.add(probCatch);

				}
				if(simulation.equals("Seg")) {
					threshold = Double.parseDouble(eElement.getElementsByTagName("threshold").item(0).getTextContent());
					initial.add(threshold);
				
				}
				if(simulation.equals("Wator")) {
					sharkLife = Integer.parseInt(eElement.getElementsByTagName("sharkLife").item(0).getTextContent());
					sharkSpawn = Integer.parseInt(eElement.getElementsByTagName("sharkSpawn").item(0).getTextContent());
					fishSpawn = Integer.parseInt(eElement.getElementsByTagName("fishSpawn").item(0).getTextContent());
					initial.add(sharkLife);
					initial.add(sharkSpawn);
					initial.add(fishSpawn);
				}
				
				
			}
		}
		
		argumentArray = initial.toArray();
		AbstractGrid cells = arrayCreator();
		//Will probably change if tree to something more flexible
		Simulation sim = new Initializer(simulation).getSimulation(cells);
		return sim;
	}

	public static AbstractGrid arrayCreator() throws Exception {
		
		AbstractGrid cellArray = new Initializer(simulation).getGrid(rows, columns);
		
		for(int i = 0; i < cellArray.getSize(); i++) {
			argumentArray[0] = Integer.parseInt(locations[i]);
					cellArray.set(new Initializer(simulation).getCell(argumentArray), i);
		}
		
		return cellArray;
	}

	
}
