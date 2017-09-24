import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cells.FireCell;
import cellsociety_team01.Initializer;
import grids.SquareGrid;
import simulations.Simulation;
import cellsociety_team01.FileHandler;

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// Currently for testing
		testFireSimulation();
	}
	
	
	private static void testFireSimulation() throws ParserConfigurationException, SAXException, IOException {
		SquareGrid cells = new FileHandler().fileReader("data\\Fire.xml");
		Simulation sim = new Simulation(cells);
		System.out.println(cells);
		sim.step();
		System.out.println(cells);
		sim.step();
		System.out.println(cells);
		sim.step();
		System.out.println(cells);
		sim.step();
	}
}
