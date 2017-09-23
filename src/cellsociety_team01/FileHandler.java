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

import cellsociety_team01.Cell;
import cellsociety_team01.FireCell;
import cellsociety_team01.FireCell.State;


public class FileHandler {

	private static String simulation;
	private static int rows;
	private static int columns;
	private static int probCatch;
	private static FireCell.State state;
	
	
	public static Cell[][] fileReader(String file) throws ParserConfigurationException, SAXException, IOException {
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
				probCatch = Integer.parseInt(eElement.getElementsByTagName("probCatch").item(0).getTextContent());
			}
		}
		Cell[][] cells = arrayCreator();
		return cells;
	}

	public static Cell[][] arrayCreator() {
		Cell[][] cellArray = new Cell[rows][columns];
		
		if(simulation.equals("Fire")) {
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					if(j==3) {
						state = FireCell.State.BURNING;
					}
					else if(j==8 | j==1) {
						state = FireCell.State.EMPTY;
					}
					else {
						state = FireCell.State.TREE;
					}
					cellArray[i][j] = new FireCell(state, probCatch);
				}
			}
		}
		return cellArray;
	}

	
}
