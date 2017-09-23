import cellsociety_team01.FireCell;
import cellsociety_team01.Simulation;
import cellsociety_team01.SquareGrid;

public class Main {
	public static void main(String[] args) {
		testFireSimulation();
	}
	
	
	private static void testFireSimulation() {
		SquareGrid cells = new SquareGrid(10,10);
		Simulation sim = new Simulation(cells);
		for(int i = 0; i < cells.getSize(); i++) {
			cells.set(new FireCell(.8), i);
		}
		cells.set(new FireCell(FireCell.State.BURNING, .8), 13);
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
