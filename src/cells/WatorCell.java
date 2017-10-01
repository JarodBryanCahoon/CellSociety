package cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorCell extends Cell {
	// The order of these is important -- each prioritizes the things below it
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	private static final Color[] COLORS = new Color[]{ Color.DARKBLUE, Color.GREEN, Color.ALICEBLUE};
	
	private int energy;
	private int toNextSpawn;
	private WatorCell movedTo;
	
	private static final int SHARKLIFE = 0;
	private static final int SHARKSPAWN = 1;
	private static final int FISHSPAWN = 2;
	private static final int ENERGYGAIN = 3;
	/**
	 * @param initialState
	 * @param sharkLife
	 *            The life-span, in steps, of a shark without food
	 * @param sharkSpawn
	 *            The number of steps for a shark to spawn
	 * @param fishSpawn
	 *            The number of steps for a fish to spawn
	 */
	public WatorCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
		energy = (int)pars.getParameter(SHARKLIFE);
		toNextSpawn = initialSpawnTime();
	}

	/**
	 * @return fishSpawnTime or sharkSpawnTime, depending on the state
	 */
	private int initialSpawnTime() {
		return (int)parameters.getParameter(getState() == FISH ? FISHSPAWN : SHARKSPAWN);
	}

	/**
	 * This is not done in parallel, so it will call update as necessary
	 * 
	 * @param neighborhood
	 *            The immediate neighborhood
	 * 
	 * @see cells.Cell#step(java.util.List)
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		movedTo = this;
		if (getState() == EMPTY)
			return;
		toNextSpawn--;
		energy--;
		if (isDead()) {
			nextState = EMPTY;
		}
		else
			moveTo(cellToMoveTo(neighborhood, getState() - 1));
		update();
	}
	
	/**
	 * @return The cell this last moved to, to allow Simulation to prevent double motion
	 */
	public WatorCell getMovedTo() {
		return movedTo;
	}

	/**
	 * @param neighborhood
	 *            The immediate neighborhood (NWES) of the cell
	 * @param priority
	 *            What state to move towards
	 * @return returns this cell when not moving, otherwise gives destination
	 */
	private WatorCell cellToMoveTo(List<Cell> neighborhood, int priority) {
		if (priority == -1) {
			return this;
		}
		ArrayList<WatorCell> cells = new ArrayList<WatorCell>();
		for (Cell c : neighborhood) {
			if (c != null && c.getState() == priority)
				cells.add((WatorCell) c);
		}
		if (cells.size() == 0)
			return cellToMoveTo(neighborhood, priority - 1);
		// if looking for fish, try empty space. otherwise, done.
		else
			return cells.get((int) (Math.random() * cells.size()));
	}

	/**
	 * For sharks
	 */
	private boolean isDead() {
		return getState() == SHARK && energy <= 0;
	}

	/**
	 * Moves to the given cell and spawns if appropriate
	 * 
	 * @param cell
	 *            destination
	 * @return 
	 */
	private void moveTo(WatorCell cell) {
		movedTo = cell;
		if (cell == this)
			return;
		if(cell.getState() == FISH)
			energy+=(int)parameters.getParameter(ENERGYGAIN);
		cell.nextState = getState();
		cell.energy = energy;
		if (toNextSpawn <= 0) {
			toNextSpawn = initialSpawnTime();
			energy = (int)parameters.getParameter(SHARKLIFE);
		} else
			nextState = EMPTY;
		cell.toNextSpawn = toNextSpawn;
		cell.update();
	}

	@Override
	protected void cycle() {
		if(getState() == SHARK)
			nextState = EMPTY;
		else
			nextState = getState()+1;
		
		update();
		energy = (int)parameters.getParameter(SHARKLIFE);
		toNextSpawn = initialSpawnTime();
	}
	

	@Override
	public String getSimType() {
		return "Wator";
	}
}
