package userInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {
	public static final double BUTTON_MAX_WIDTH = 130;
	public static final double BUTTON_SPACING = 15;
	public static final double LABEL_Y_TRANSLATION = 4;
	public static final double GUI_HEIGHT = 300;
	public static final double GUI_WIDTH = 200;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	public static final double[] SPEEDS = { 1, .5, .25 };

	private int speedIndex = 1;
	private ResourceBundle GuiText = ResourceBundle.getBundle("resources/GuiNameBundle");
	private Timeline myAnimation;
	private KeyFrame myFrame;
	private double updateRate = .5;
	private Stage mainStage;
	private Scene mainScene;
	private Pane guiLayout;
	private List<SimulationInterface> simList;

	@Override
	public void start(Stage pStage) throws Exception {
		setMain(pStage);
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
		createInterface();
		mainScene = new Scene(guiLayout, GUI_WIDTH, GUI_HEIGHT);
		mainScene.getStylesheets().add(getClass().getResource(GuiText.getString("CSS")).toExternalForm());
		mainStage.setScene(mainScene);
		mainStage.show();
		simList = new ArrayList<SimulationInterface>();
	}

	/**
	 * Creates the interface of the initial gui
	 */
	private void createInterface() {
		guiLayout = new StackPane();

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(BUTTON_SPACING);
		Button startButton = new Button(GuiText.getString("NewButton"));
		Button stepAll = new Button(GuiText.getString("StepButton"));
		stepAll.setOnAction((event) -> {
			update();
		});
		startButton.setOnAction((event) -> {
			SimulationInterface si = new SimulationInterface();
			simList.add(si);
		});
		Button playButton = new Button(GuiText.getString("PlayButton"));
		playButton.setOnAction((event) -> play());
		startButton.setId("new-sim-button");

		Button pauseButton = new Button(GuiText.getString("PauseButton"));
		pauseButton.setOnAction((event) -> pause());

		Button speedyButton = new Button(GuiText.getString("SpeedyButton"));
		speedyButton.setOnAction((event) -> speedUp());

		Button slowButton = new Button(GuiText.getString("SlowButton"));
		slowButton.setOnAction((event) -> slow());

		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepAll.setMaxWidth(BUTTON_MAX_WIDTH);
		speedyButton.setMaxWidth(BUTTON_MAX_WIDTH);
		slowButton.setMaxWidth(BUTTON_MAX_WIDTH);
		box.getChildren().addAll(startButton, playButton, speedyButton, slowButton, pauseButton, stepAll);

		guiLayout.getChildren().add(box);

	}

	/**
	 *Increases speed of all of the simulations currently open
	 */
	private void speedUp() {
		if (speedIndex < SPEEDS.length - 1)
			speedIndex++;

		speedChange(SPEEDS[speedIndex]);

	}

	/**
	 * Changes the speed of the simulations that are open
	 * @param speed Update speed 
	 */
	private void speedChange(double speed) {
		for (SimulationInterface s : simList) {
			s.changeSpeed(speed);
		}
	}
	/**
	 * Slows all of the speeds of the simulations
	 */
	private void slow() {
		if (speedIndex > 0)
			speedIndex--;
		speedChange(SPEEDS[speedIndex]);

	}

	/**
	 * Goes through and calls for all of the simulations to update
	 */
	private void update() {
		for (SimulationInterface s : simList) {
			try {
			s.update();
			} catch (NullPointerException e) {
				e.printStackTrace();
				NoSimulationsOpenBox bx = new NoSimulationsOpenBox();
			}
		}
	}

	/**
	 * Plays all of the simulations
	 */
	private void play() {
		for (SimulationInterface s : simList) {
			s.simPlay();
		}
	}

	/**
	 * Pause all of the simulations
	 */
	private void pause() {
		for (SimulationInterface s : simList) {
			s.simPause();
		}
	}

	/**
	 * Main method
	 * @param args from cmd line
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
