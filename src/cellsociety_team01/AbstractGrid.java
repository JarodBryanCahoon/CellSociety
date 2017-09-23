package cellsociety_team01;

import java.util.List;

public abstract class AbstractGrid implements Iterable<Cell>{
	public abstract Cell get(int index);
	
	public abstract void set(Cell input, int index);
	
	public abstract List<Cell> getNeighbors(int index);

	public abstract int getSize();
}
