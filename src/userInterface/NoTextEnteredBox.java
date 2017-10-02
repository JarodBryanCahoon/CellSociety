package userInterface;

import java.util.ResourceBundle;

public class NoTextEnteredBox extends ErrorBox {
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	
	/**
	 * For when no text is entered into the box, but it is called
	 */
	public NoTextEnteredBox(){
		display();
	}
}
