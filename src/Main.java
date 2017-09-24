import cells.FireCell;
import cellsociety_team01.Initializer;
import grids.SquareGrid;
import simulations.Simulation;

public class Main {
	public static void main(String[] args) {
		// Currently for testing
		try { // Expected Null pointer
			new Initializer("Fire").getCell(FireCell.TREE, .5).step(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(FireCell.class.getName());
		testFireSimulation();
	}
	
	
	private static void testFireSimulation() {
		SquareGrid cells = new SquareGrid(10,10);
		Simulation sim = new Simulation(cells);
		for(int i = 0; i < cells.getSize(); i++) {
			cells.set(new FireCell(FireCell.TREE, .8), i);
		}
		cells.set(new FireCell(FireCell.BURNING, .8), 13);
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
