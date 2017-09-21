package cellsociety_team01;

public class Simulation {
	AbstractGrid cells;
	
	public Simulation(SquareGrid cells) {
		this.cells = cells;
	}

	public void step() {
		for(int i = 0; i < cells.getSize(); i++)
			cells.get(i).step(cells.getNeighbors(i));
		for(Cell cell:cells)
			cell.update();
		
	}
	
	public String toString() {
		return cells.toString();
	}
}
