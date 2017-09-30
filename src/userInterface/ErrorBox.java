package userInterface;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorBox implements AlertBox {
	public static final ResourceBundle GUI_TEXT = ResourceBundle.getBundle("resources/GuiNameBundle");
	public static final double BOX_WIDTH = 500;
	public static final double BUTTON_SPACING = 10;
	private Stage myStage;
	private String myTitle;
	private String myMessage;

	public ErrorBox() {
		this.display();
	}
	
	public void display(){
		// TODO Auto-generated method stub
		myTitle = GUI_TEXT.getString(this.getClass().getSimpleName() + "Title");
		myMessage = GUI_TEXT.getString(this.getClass().getSimpleName() + "Message");
		myStage = new Stage();
		myStage.initModality(Modality.APPLICATION_MODAL);
		myStage.setTitle(myTitle);
		myStage.setWidth(BOX_WIDTH);
		
		Label label = new Label();
		label.setText(myMessage);
		
		Button cb = new Button(GUI_TEXT.getString("CloseButton"));
		cb.setOnAction((event) -> close());
		
		VBox layout = new VBox(BUTTON_SPACING);
		layout.getChildren().addAll(label, cb);
		layout.setAlignment(Pos.CENTER);
		layout.setPrefWidth(BOX_WIDTH);
		
		Scene newScene = new Scene(layout);
		newScene.getStylesheets().add(getClass().getResource(GUI_TEXT.getString("CSS")).toExternalForm());
		
		myStage.setScene(newScene);
		myStage.showAndWait();
	}


	public void close() {
		myStage.close();
	}

}
