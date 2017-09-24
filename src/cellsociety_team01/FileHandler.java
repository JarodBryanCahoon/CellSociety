package cellsociety_team01;

import java.io.File;
import java.io.IOException;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
  
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cells.Cell;
import cells.FireCell;
import cells.LifeCell;
import grids.SquareGrid;
import cells.Cell;




public class FileHandler {

	private static String simulation;
	private static int rows;
	private static int columns;
	private static int probCatch;
	private static int state;
	private static String fileName;
	
	public FileHandler() {
		
	}
	
	
	public static SquareGrid fileReader(String file) throws ParserConfigurationException, SAXException, IOException {
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
				rows = Integer.parseInt(eElement.getElementsByTagName("rows").item(0).getTextContent());
				columns = Integer.parseInt(eElement.getElementsByTagName("columns").item(0).getTextContent());
				if(simulation.equals("Fire")) {
					probCatch = Integer.parseInt(eElement.getElementsByTagName("probCatch").item(0).getTextContent())/2;
				}
				
			}
		}
		SquareGrid cells = arrayCreator();
		for(int i = 0; i < cells.getSize(); i++) {
			cells.set(new FireCell(FireCell.TREE, .8), i);
		}
		cells.set(new FireCell(FireCell.BURNING, .8), 13);
		return cells;
	}

	public static SquareGrid arrayCreator() {
		
		try {
			new Initializer(simulation).getCell(1, .5).step(null);
		} catch (Exception e) {
			// will fix
			e.printStackTrace();
		}
		
		SquareGrid cellArray = new SquareGrid(rows, columns);
		
		return cellArray;
	}

	
}
