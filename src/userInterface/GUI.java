package userInterface;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class GUI extends Application{
	public static final double BUTTON_MAX_WIDTH = 120;
	
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
		topBox.setSpacing(30);
		Pane.setTop(topBox);
		
		VBox leftSideBox = new VBox();
		leftSideBox.setPadding(new Insets(10));
		leftSideBox.setSpacing(13);
		Pane.setLeft(leftSideBox);
		Button playButton = new Button("Play");
		Button pauseButton = new Button("Pause");
		Button stepButton = new Button("Step");
		playButton.setTranslateY(320);
		pauseButton.setTranslateY(320);
		stepButton.setTranslateY(320);
		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepButton.setMaxWidth(BUTTON_MAX_WIDTH);
		
		leftSideBox.getChildren().addAll(playButton, pauseButton, stepButton);
		
		Label insLabel = new Label("Enter Name of XML File: ");
		inputField = new TextField();
		Pane.setTop(topBox);
		topBox.getChildren().addAll(insLabel, inputField);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
