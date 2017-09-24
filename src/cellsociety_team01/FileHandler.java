package cellsociety_team01;
 
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
  
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import grids.SquareGrid;
import simulations.Simulation;
 
// test comment  


public class FileHandler {

	private static String simulation;
	private static int rows;
	private static int columns;
	private static double probCatch;
	private static int state;
	private static String fileName;
	private static double threshold;
	private static double sharkLife;
	private static double sharkSpawn;
	private static double fishSpawn;
	private static ArrayList initial = new ArrayList();
	private static Object[] argumentArray;
	public FileHandler() {	
	}
	
	public static Simulation fileReader(String file) throws Exception {
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
				initial.add(state);
				rows = Integer.parseInt(eElement.getElementsByTagName("rows").item(0).getTextContent());
				columns = Integer.parseInt(eElement.getElementsByTagName("columns").item(0).getTextContent());
				
				if(simulation.equals("Fire")) {
					probCatch = Double.parseDouble(eElement.getElementsByTagName("probCatch").item(0).getTextContent());
					initial.add(probCatch);
				}
				if(simulation.equals("Seg")) {
					threshold = Double.parseDouble(eElement.getElementsByTagName("threshold").item(0).getTextContent());
					initial.add(threshold);
				}
				if(simulation.equals("Wator")) {
					sharkLife = Double.parseDouble(eElement.getElementsByTagName("sharkLife").item(0).getTextContent());
					sharkSpawn = Double.parseDouble(eElement.getElementsByTagName("sharkLife").item(0).getTextContent());
					fishSpawn = Double.parseDouble(eElement.getElementsByTagName("sharkLife").item(0).getTextContent());
					initial.add(sharkLife);
					initial.add(sharkSpawn);
					initial.add(fishSpawn);
				}
				
			}
		}
		
		argumentArray = initial.toArray();
		SquareGrid cells = arrayCreator();
		//Will probably change if tree to something more flexible
		Simulation sim = new Simulation(cells);
		return sim;
	}

	public static SquareGrid arrayCreator() throws Exception {
		
		
		try {
			new Initializer(simulation).getCell(1, .5);
		} catch (Exception e) {
			// will fix
			e.printStackTrace();
		}
		
		SquareGrid cellArray = new SquareGrid(rows, columns);
		
		for(int i = 0; i < cellArray.getSize(); i++) {
			cellArray.set(new Initializer(simulation).getCell(argumentArray), i);
		}
		cellArray.set(new Initializer(simulation).getCell(2, 0.8), 13);
		
		return cellArray;
	}

	
}
