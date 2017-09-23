package cellsociety_team01;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public enum Direction {
	NORTH(0,-1), EAST(1,0), SOUTH(0,1), WEST(-1,0), NONE(0,0);

	public final int dx, dy;

	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
}
