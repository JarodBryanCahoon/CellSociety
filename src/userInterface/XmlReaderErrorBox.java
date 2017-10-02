package userInterface;

import java.util.ResourceBundle;

import javafx.stage.Stage;

public class XmlReaderErrorBox extends ErrorBox{
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 200;
	private String myTitle;
	private Stage myStage;
	private String myMessage;
	
	/**
	 * When something goes wrong with the code that isn't one of the other errors
	 * it is probably something to do with the XML reader or the XML file itself.
	 */
	public XmlReaderErrorBox(){
		display();
	}
}
