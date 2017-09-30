package userInterface;

import java.util.ResourceBundle;

import cellsociety_team01.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulations.Simulation;

public class SimulationInterface extends Application {
	public static final double BUTTON_MAX_WIDTH = 170;
	public static final double LABEL_Y_TRANSLATION = 4;
	public static final double GRID_SIZE = 200;
	public static final double GUI_WIDTH = 650;
	public static final double GUI_HEIGHT = 300;
	public static final double INSET_DISTANCE = (GUI_WIDTH - GRID_SIZE) / 2;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	public static final double BORDER_FRACTION = .05;
	public static final double BUTTON_SPACING = 13;
	public static final double[] SPEEDS = { 1, .5, .25 };

	private ResourceBundle GuiText = ResourceBundle.getBundle("resources/GuiNameBundle");
	private Timeline myAnimation;
	private KeyFrame myFrame;
	private double updateRate = .5;
	private Simulation currentSim;
	private Stage simStage;
	private Scene mainScene;
	private BorderPane guiLayout;
	private TextField inputField;
	private Pane centerPane;
	private String mySimUrl;

	@Override
	public void start(Stage pStage) throws Exception {
		setMain(pStage);
		// initializeCellGrid();
	}

	public SimulationInterface() {
		setLayout();
		mainScene = new Scene(guiLayout, GUI_WIDTH, GUI_HEIGHT);
		mainScene.getStylesheets().add(getClass().getResource("CellSociety.css").toExternalForm());
		simStage = new Stage();
		simStage.setScene(mainScene);
		simStage.show();

		myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
		myAnimation = new Timeline();//
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(myFrame);
	}

	/**
	 * Sets the main stage with title and Appropriate panes
	 * 
	 * @param pStage
	 *            Main stage upon which the Animation and scenes will be placed
	 */
	private void setMain(Stage pStage) {

	}

	/**
	 * Adds all appropriate widgets to the main GUI (Buttons, Labels, TextBoxes,
	 * etc) except for the GridPane containing the cells, which is handled by a
	 * different method which requires more information.
	 */
	private void setLayout() {
		guiLayout = new BorderPane();

		HBox topBox = new HBox();
		guiLayout.setTop(topBox);
		setTopBox(topBox);
	}

	private void setTopBox(HBox topBox) {
		topBox.setPadding(new Insets(BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING));
		topBox.setSpacing(BUTTON_SPACING);
		topBox.getChildren().addAll(makeLabel(), makeTextBox(), makeResetButton());
	}

	private ButtonBase makeResetButton() {
		Button resetButton = new Button(GuiText.getString("ResetButton"));
		resetButton.setOnAction((event) -> {

			reset();
		});
		return resetButton;
	}

	protected void reset() {
		Object ErrorBox;
		if (!(mySimUrl == null)) {
			loadFile(mySimUrl);
			initializeCellGrid();
			myAnimation.stop();
		} else
			ErrorBox = new ResetErrorBox();
	}

	private Label makeLabel() {
		Label insLabel = new Label(GuiText.getString("XmlLabel"));
		insLabel.setTranslateY(LABEL_Y_TRANSLATION);
		return insLabel;
	}

	private TextInputControl makeTextBox() {
		inputField = new TextField();
		inputField.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
		inputField.setFocusTraversable(false);
		inputField.setPromptText(GuiText.getString("PromptText"));
		inputField.setOnAction((event) -> {
			mySimUrl = inputField.getText();
			loadFile(inputField.getText());
			initializeCellGrid();
			inputField.clear();
			myAnimation.stop();
		});
		return inputField;
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

	public void update() {
		currentSim.step();
	}

	public void simPlay() {
		myAnimation.play();
	}

	public void simPause() {
		myAnimation.stop();
	}

	public void simStop() {
		myAnimation.stop();
	}

	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate
	 * number of cells
	 */
	private void initializeCellGrid() {
		Pane bufferPane = new Pane();
		bufferPane.setPrefWidth(INSET_DISTANCE);
		guiLayout.setLeft(bufferPane);
		centerPane = currentSim.getView(GRID_SIZE, GRID_SIZE);
		guiLayout.setCenter(centerPane);
	}
}