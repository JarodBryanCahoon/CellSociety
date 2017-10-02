package userInterface;

import java.util.ResourceBundle;

public class SaxErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	
	/**
	 * Not really sure when this error pops up, but when it does, this box will be
	 * here!!
	 */
	public SaxErrorBox(){
		display();
	}
}
