package userInterface;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class GUI extends Application{
	public static final double BUTTON_MAX_WIDTH = 120;
	public static final double GRID_SIZE = 300;
	
	private Stage mainStage;
	private Scene mainScene;
	private BorderPane Pane;
	private GridPane cellGrid;
	private TextField inputField;
	
	@Override
	private void start(Stage pStage) throws Exception {
		setMain(pStage);
		
	}

	private void setMain(Stage pStage) {
		mainStage = pStage;
		mainStage.setTitle("Cell Society Simulation");
		setCellGrid();
		setBorderPane();
		mainScene = new Scene(Pane, 650, 650);
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
		Button pauseButton = new Button("Pause (inactive)");
		Button stepButton = new Button("Step (inactive)");
		
		playButton.setTranslateY(320);
		pauseButton.setTranslateY(320);
		stepButton.setTranslateY(320);
		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepButton.setMaxWidth(BUTTON_MAX_WIDTH);
		leftSideBox.getChildren().addAll(playButton, pauseButton, stepButton);
		
		Label insLabel = new Label("Enter Name of XML File: ");
		inputField = new TextField();
		inputField.setPrefWidth(300);
		inputField.setFocusTraversable(false);
		inputField.setPromptText("For instance \"GOLSim.xml\"");
		Pane.setTop(topBox);
		Pane.setCenter(cellGrid);
		Pane.setBottom(null);
		Pane.setRight(null);
		
		topBox.getChildren().addAll(insLabel, inputField);
	}
	
	private void setCellGrid() {
		cellGrid = new GridPane();
		cellGrid.setPrefHeight(GRID_SIZE);
		cellGrid.setPrefWidth(GRID_SIZE);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
