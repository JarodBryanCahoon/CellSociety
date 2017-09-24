package userInterface;

import java.util.ResourceBundle;

import cellsociety_team01.*;
import grids.AbstractGrid;
import grids.SquareGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulations.Simulation;


public class GUI extends Application{
	public static final double BUTTON_MAX_WIDTH = 120;
	public static final double GRID_SIZE = 300;
	public static final double GUI_SIZE = 650;
	public static final double BUTTON_VERTICAL_SHIFT = 320;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	public static final Color[] COLORS = {Color.WHITE, Color.TURQUOISE, Color.DARKBLUE};
	
	private ResourceBundle GuiText = ResourceBundle.getBundle("resources/GuiNameBundle");
	private Timeline myAnimation;
	private KeyFrame myFrame;
	private double updateRate = .5;
	private Simulation currentSim;
	private SquareCellDisplay imageGrid;
	private int gridRows;
	private int gridCols;
	private Stage mainStage;
	private Scene mainScene;
	private BorderPane guiLayout;
	private GridPane cellGrid;
	private TextField inputField;
	
	@Override
	public void start(Stage pStage) throws Exception {
		setMain(pStage);
		initializeCellGrid();
	}
	
	/**
	 * Sets the main stage with title and Appropriate panes
	 * @param pStage Main stage upon which the Animation and scenes will be placed
	 */
	private void setMain(Stage pStage) {
		mainStage = pStage;
		mainStage.setTitle(GuiText.getString("GuiTitle"));
		setLayout();
		mainScene = new Scene(guiLayout, GUI_SIZE, GUI_SIZE);
		mainStage.setScene(mainScene);
		mainStage.show();
		
		myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(myFrame);
	}
	
	
	/**
	 * Adds all appropriate widgets to the main GUI (Buttons, Labels, TextBoxes, etc) except for the GridPane containing the cells,
	 * which is handled by a different method which requires more information.
	 */
	private void setLayout() {
		guiLayout = new BorderPane();
		
		
		VBox leftSideBox = new VBox();
		leftSideBox.setPadding(new Insets(10));
		leftSideBox.setSpacing(13);
		guiLayout.setLeft(leftSideBox);
		
		Button playButton = new Button(GuiText.getString("PlayButton"));
		playButton.setOnAction((event) -> {
			myAnimation.play();
		});
		
		Button pauseButton = new Button(GuiText.getString("PauseButton"));
		pauseButton.setOnAction((event) -> {
			myAnimation.pause();
		});
		
		Button stepButton = new Button(GuiText.getString("StepButton"));
		stepButton.setOnAction((event) -> {
			this.update();
		}); 
		
		Button speedyButton = new Button(GuiText.getString("SpeedyButton"));
		speedyButton.setOnAction((event) -> {
			if(updateRate > .5) {
				updateRate = .5;
				myAnimation.stop();
				myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
				myAnimation.getKeyFrames().clear();
				myAnimation.getKeyFrames().add(myFrame);
				myAnimation.play();
			}
			else {
				updateRate = .25;
				myAnimation.stop();
				myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
				myAnimation.getKeyFrames().clear();
				myAnimation.getKeyFrames().add(myFrame);
				myAnimation.play();
			}
		});
		Button slowButton = new Button(GuiText.getString("SlowButton"));
		slowButton.setOnAction((event) -> {
			if(updateRate < .5) {
				updateRate = .5;
				myAnimation.stop();
				myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
				myAnimation.getKeyFrames().clear();
				myAnimation.getKeyFrames().add(myFrame);
				myAnimation.play();
			}
			else {
				updateRate = 1;
				myAnimation.stop();
				myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
				myAnimation.getKeyFrames().clear();
				myAnimation.getKeyFrames().add(myFrame);
				myAnimation.play();
			}
		});
		
		playButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		pauseButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		stepButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		speedyButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		slowButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepButton.setMaxWidth(BUTTON_MAX_WIDTH);
		speedyButton.setMaxWidth(BUTTON_MAX_WIDTH);
		slowButton.setMaxWidth(BUTTON_MAX_WIDTH);
		leftSideBox.getChildren().addAll(playButton, speedyButton, slowButton, pauseButton, stepButton);
		
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(15, 12, 15, 12));
		topBox.setSpacing(30);
		Label insLabel = new Label(GuiText.getString("XmlLabel"));
		inputField = new TextField();
		inputField.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
		inputField.setFocusTraversable(false);
		inputField.setPromptText(GuiText.getString("PromptText"));
		Button submitButton = new Button(GuiText.getString("SubmitButton"));
		submitButton.setOnAction((event) -> {
			loadFile(inputField.getText());
		});
		guiLayout.setTop(topBox);
	
		
		
		topBox.getChildren().addAll(insLabel, inputField, submitButton);
	}
	
	/**
	 * Retrieves the data from the XML file specified by the reader, starts a simulation with the information, and then returns 
	 * and array of cell images to the GUI
	 * @param s Name of the file
	 */
	private void loadFile(String s) {
		guiLayout.setCenter(null);
		
		try {
			currentSim = FileHandler.fileReader(s);
		} catch (Exception e) {
			e.printStackTrace();

		}
		imageGrid = new SquareCellDisplay((SquareGrid)currentSim.getGrid(), COLORS);
	}
	
	private void update() {
		System.out.println(updateRate);
		
		//currentSim.step();
		//imageGrid.update();
	}
	
	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate number of cells
	 */
	private void initializeCellGrid() {
		cellGrid = new GridPane();
		cellGrid.setGridLinesVisible(true);
		Rectangle[][] images = imageGrid.constructImages();
		gridRows = images.length;
		gridCols = images[0].length;
		
		for(int i = 0; i < gridCols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / gridCols);
            cellGrid.getColumnConstraints().add(colConst);
		}
		for(int i = 0; i < gridRows; i++) {
			RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / gridRows);
            cellGrid.getRowConstraints().add(rowConst);
		}
		
		for(int i = 0; i < gridRows; i++) {
			for(int j = 0; j <gridCols; j++) {
				cellGrid.add(images[i][j], j, i);
			}
		}
		
		guiLayout.setCenter(cellGrid);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
