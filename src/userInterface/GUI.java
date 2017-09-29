package userInterface;

import java.util.ResourceBundle;

import cellsociety_team01.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulations.Simulation;

public class GUI extends Application {
	public static final double BUTTON_MAX_WIDTH = 170;
	public static final double LABEL_Y_TRANSLATION = 4;
	public static final double GRID_SIZE = 520;
	public static final double GUI_SIZE = 650;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	public static final Color[] COLORS = { Color.web("#DFE2E5"), Color.TURQUOISE, Color.DARKBLUE };
	public static final double BORDER_FRACTION = .05;
	public static final double BUTTON_SPACING = 13;
	public static final double[] SPEEDS = { 1, .5, .25 };

	private ResourceBundle GuiText = ResourceBundle.getBundle("resources/GuiNameBundle");
	private Timeline myAnimation;
	private KeyFrame myFrame;
	private double updateRate = .5;
	private Simulation currentSim;
	private Stage mainStage;
	private Scene mainScene;
	private BorderPane guiLayout;
	private TextField inputField;
	private int speedIndex = 1;

	@Override
	public void start(Stage pStage) throws Exception {
		setMain(pStage);
		// initializeCellGrid();
	}

	/**
	 * Sets the main stage with title and Appropriate panes
	 * 
	 * @param pStage
	 *            Main stage upon which the Animation and scenes will be placed
	 */
	private void setMain(Stage pStage) {
		mainStage = pStage;
		mainStage.setTitle(GuiText.getString("GuiTitle"));
		setLayout();
		mainScene = new Scene(guiLayout, GUI_SIZE, GUI_SIZE);
		mainScene.getStylesheets().add(getClass().getResource("CellSociety.css").toExternalForm());
		mainStage.setScene(mainScene);
		mainStage.show();

		myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
		myAnimation = new Timeline();//
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(myFrame);
	}

	/**
	 * Adds all appropriate widgets to the main GUI (Buttons, Labels, TextBoxes,
	 * etc) except for the GridPane containing the cells, which is handled by a
	 * different method which requires more information.
	 */
	private void setLayout() {
		guiLayout = new BorderPane();
		
		HBox bottomBox = new HBox();
		guiLayout.setBottom(bottomBox);	
		setBottomBox(bottomBox);
		
		HBox topBox = new HBox();
		guiLayout.setTop(topBox);
		setTopBox(topBox);
	}

	protected void setTopBox(HBox topBox) {
		topBox.setPadding(new Insets(BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING));
		topBox.setSpacing(BUTTON_SPACING);
		Label insLabel = new Label(GuiText.getString("XmlLabel"));
		insLabel.setTranslateY(LABEL_Y_TRANSLATION);
		inputField = new TextField();
		inputField.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
		inputField.setFocusTraversable(false);
		inputField.setPromptText(GuiText.getString("PromptText"));
		Button submitButton = new Button(GuiText.getString("SubmitButton"));
		submitButton.setOnAction((event) -> {
			loadFile(inputField.getText());
			initializeCellGrid();
		});
		topBox.getChildren().addAll(insLabel, inputField, submitButton);
	}

	protected void setBottomBox(HBox bottomBox) {
		bottomBox.setPadding(new Insets(BUTTON_SPACING));
		bottomBox.setSpacing(BUTTON_SPACING);
		Button playButton = new Button(GuiText.getString("PlayButton"));
		playButton.setOnAction((event) -> myAnimation.play());

		Button pauseButton = new Button(GuiText.getString("PauseButton"));
		pauseButton.setOnAction((event) -> myAnimation.pause());

		Button stepButton = new Button(GuiText.getString("StepButton"));
		stepButton.setOnAction((event) -> this.update());

		Button speedyButton = new Button(GuiText.getString("SpeedyButton"));
		speedyButton.setOnAction((event) -> speedUp());

		Button slowButton = new Button(GuiText.getString("SlowButton"));
		slowButton.setOnAction((event) -> slow());

		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepButton.setMaxWidth(BUTTON_MAX_WIDTH);
		speedyButton.setMaxWidth(BUTTON_MAX_WIDTH);
		slowButton.setMaxWidth(BUTTON_MAX_WIDTH);
		bottomBox.getChildren().addAll(playButton, speedyButton, slowButton, pauseButton, stepButton);
	}

	private void speedUp() {
		if (speedIndex < SPEEDS.length - 1) {
			speedIndex++;
			changeSpeed(SPEEDS[speedIndex]);
		}
	}

	private void changeSpeed(double speed) {
			updateRate = speed;
			myAnimation.stop();
			myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
			myAnimation.getKeyFrames().clear();
			myAnimation.getKeyFrames().add(myFrame);
			myAnimation.play();
	}

	private void slow() {
		if(speedIndex > 0) {
			speedIndex--;
			changeSpeed(SPEEDS[speedIndex]);
		}
	}

	/**
	 * Retrieves the data from the XML file specified by the reader, starts a
	 * simulation with the information, and then returns and array of cell images to
	 * the GUI
	 * 
	 * @param s
	 *            Name of the file
	 */
	private void loadFile(String s) {
		myAnimation.stop();
		guiLayout.setCenter(null);
		currentSim = null;

		try {
			currentSim = FileHandler.fileReader(s);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void update() {
		currentSim.step();
	}

	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate
	 * number of cells
	 */
	private void initializeCellGrid() {
		guiLayout.setCenter(currentSim.getView(GRID_SIZE,GRID_SIZE));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
