package userInterface;

import java.util.ResourceBundle;

public class NoSimulationsOpenBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	
	/**
	 * For when a simulation is called to move, but none are open.
	 */
	public NoSimulationsOpenBox(){
		display();
	}
}
