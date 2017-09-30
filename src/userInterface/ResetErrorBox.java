package userInterface;

import java.util.ResourceBundle;

import javafx.stage.Stage;

public class ResetErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	private String myTitle;
	private Stage myStage;
	private String myMessage;
	
	public ResetErrorBox() {
		display();
	}
	
	
	
}
