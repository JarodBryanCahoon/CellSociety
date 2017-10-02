package userInterface;

import java.util.ResourceBundle;

public class NoFileErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	
	public NoFileErrorBox(){
		display();
	}

}
