import cells.LangtonCell;

public class Main {
	public static void main(String[] args) throws Exception {
		new LangtonCell(0,null);
		// Currently for testing
		//testFireSimulation();
	}
	
	
//	private static void testFireSimulation() throws Exception {
//		Simulation sim = new FileHandler().fileReader("data\\Wator.xml");
//		AbstractGrid cells = sim.getGrid();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//		System.out.println(cells);
//		System.out.println();
//		sim.step();
//	}
}
