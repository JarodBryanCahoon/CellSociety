package grids;

import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {

	public static final int NUM_SIDES = 6;
	public static final double ANGLE = 2*Math.PI/NUM_SIDES;

	public Hexagon(double xCenter, double yCenter, double width, double height) {
		super(getVertexLocations(xCenter, yCenter, width, height));
	}
	
	/**
	 * Why is this static? This does not depend on the state of the object, so there
	 * is no reason it needs to be non-static, and by making it static, it can be
	 * called directly and used as a parameter for super().
	 */
	private static double[] getVertexLocations(double xCenter, double yCenter, double width, double height) {
		double[] vertices = new double[NUM_SIDES*2];
		for(int i = 0; i < NUM_SIDES; i++) {
			vertices[2*i] = xCenter + width/2*Math.cos(i*ANGLE);
			vertices[2*i+1] = yCenter + height/2*Math.sin(i*ANGLE);
		}
		return vertices;
	}
}
