import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cells.FireCell;
import cellsociety_team01.Initializer;
import grids.AbstractGrid;
import grids.SquareGrid;
import simulations.Simulation;
import cellsociety_team01.FileHandler;

public class Main {
	public static void main(String[] args) throws Exception {
		// Currently for testing
		testFireSimulation();
	}
	
	
	private static void testFireSimulation() throws Exception {
		Simulation sim = new FileHandler().fileReader("data\\Wator.xml");
		AbstractGrid cells = sim.getGrid();
		System.out.println(cells);
		System.out.println();
		sim.step();
		System.out.println(cells);
		System.out.println();
		sim.step();
		System.out.println(cells);
		System.out.println();
		sim.step();
		System.out.println(cells);
		System.out.println();
		sim.step();
	}
}
