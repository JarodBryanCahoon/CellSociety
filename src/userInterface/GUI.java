package userInterface;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class GUI extends Application{
	private Stage mainStage;
	private Scene mainScene;
	private BorderPane Pane;
	private TextField inputField;
	@Override
	public void start(Stage pStage) throws Exception {
		setMain(pStage);
		
	}

	private void setMain(Stage pStage) {
		mainStage = pStage;
		mainStage.setTitle("Damn this is GUI");
		Button mainButton = new Button("Please press me :D");
		setBorderPane();
		Pane.getChildren().add(mainButton);
		mainScene = new Scene(Pane, 500, 500);
		mainStage.setScene(mainScene);
		mainStage.show();
	}

	private void setBorderPane() {
		Pane = new BorderPane();
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(15, 12, 15, 12));
		Label insLabel = new Label("Enter Name of XML File: ");
		inputField = new TextField();
		topBox.getChildren().addAll(insLabel, inputField);
		topBox.setSpacing(10);
		Pane.setTop(topBox);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
