package userInterface;

import java.util.ResourceBundle;

public class ResetErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	
	/**
	 * For when you attempt to reset the simulation when no previous simulation
	 * Has been ran
	 */
	public ResetErrorBox() {
		display();
	}
	
	
	
}
