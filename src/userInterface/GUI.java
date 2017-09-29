package userInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {
	public static final double BUTTON_MAX_WIDTH = 100;
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
		mainScene.getStylesheets().add(getClass().getResource("CellSociety.css").toExternalForm());
		mainStage.setScene(mainScene);
		mainStage.show();
		myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
		myAnimation = new Timeline();//
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(myFrame);
		simList = new ArrayList<SimulationInterface>();
	}
	
	private void createInterface() {
		guiLayout = new Pane();
		
		VBox box = new VBox();
		box.setSpacing(20);
		box.setAlignment(Pos.BASELINE_CENTER);
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
		box.getChildren().addAll(playButton, speedyButton, slowButton, pauseButton, stepAll, startButton);
		
		guiLayout.getChildren().add(box);
		
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
	
	private void update() {
		for(SimulationInterface s : simList) {
			s.update();
		}
	}
	
	private void play() {
		for(SimulationInterface s : simList) {
			s.simPlay();
		}
	}
	
	private void pause() {
		for(SimulationInterface s: simList) {
			s.simPause();
		}
	}
	
	
}
