package userInterface;

import java.util.ResourceBundle;

import javafx.stage.Stage;

public class SaxErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	private String myTitle;
	private Stage myStage;
	private String myMessage;
	
	/**
	 * Not really sure when this error pops up, but when it does, this box will be
	 * here!!
	 */
	public SaxErrorBox(){
		display();
	}
}
