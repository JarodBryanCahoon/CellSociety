package userInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cellsociety_team01.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulations.Simulation;

public class SimulationInterface extends Application {
	private static final int CHART_X_PLACEMENT = 50;
	public static final double BUTTON_MAX_WIDTH = 170;
	public static final double LABEL_Y_TRANSLATION = 4;
	public static final double GRID_SIZE = 200;
	public static final double GUI_WIDTH = 650;
	private static final double CHART_WIDTH = 4 * GUI_WIDTH / 5;
	public static final double GUI_HEIGHT = 00;
	public static final double INSET_DISTANCE = (GUI_WIDTH - GRID_SIZE) / 2;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	public static final double BORDER_FRACTION = .05;
	public static final double BUTTON_SPACING = 13;
	public static final double[] SPEEDS = { 1, .5, .25 };
	private int stepNumber = 0;

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
	private LineChart<Number, Number> myChart;
	private HashMap<Paint, Series> mySeries;
	private double totalCells = 0;
	private Axis xAxis;
	private Axis yAxis;

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
		guiLayout.setTop(setTopBox());
		guiLayout.setLeft(setLeftBox());
	}

	private Pane setTopBox() {
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING));
		topBox.setSpacing(BUTTON_SPACING);
		topBox.getChildren().addAll(makeLabel(), makeTextBox(), makeResetButton());
		return topBox;
	}

	private ButtonBase makeResetButton() {
		Button resetButton = new Button(GuiText.getString("ResetButton"));
		resetButton.setOnAction((event) -> reset());
		return resetButton;
	}

	protected void reset() {
		try {
			loadFile(mySimUrl);
			initializeCellGrid();
			myAnimation.stop();
		} catch (NullPointerException np) {
			ResetErrorBox rse = new ResetErrorBox();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Label makeLabel() {
		Label insLabel = new Label(GuiText.getString("XmlLabel"));
		insLabel.setTranslateY(LABEL_Y_TRANSLATION);
		return insLabel;
	}

	private Pane setBottomBox() {
		Pane p = new Pane();
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		myChart = new LineChart<Number, Number>(xAxis, yAxis);
		myChart.setMaxHeight(GUI_HEIGHT / 3);
		myChart.setMaxWidth(CHART_WIDTH);
		populateData();
		graphData(p);
		return p;

	}

	private void graphData(Pane pa) {
		myChart.setTranslateX(CHART_X_PLACEMENT);
		pa.getChildren().add(myChart);

	}

	private void populateData() {
		totalCells = 0;
		mySeries = new HashMap<Paint, XYChart.Series>();
		Pane pa = (Pane) guiLayout.getCenter();
		Map<Paint, Integer> colorMap = new HashMap<>();
		for (Node p : pa.getChildren()) {
			Polygon poly = (Polygon) p;
			totalCells++;
			if (!colorMap.containsKey(poly.getFill()))
				colorMap.put(poly.getFill(), 0);
			
			colorMap.put(poly.getFill(), colorMap.get(poly.getFill()) + 1);
		}

		for (Paint p : colorMap.keySet()) {
			XYChart.Series<Object, Object> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>(stepNumber, colorMap.get(p) / totalCells));
			mySeries.put((Paint) p, series);
		}
		
		for (Object p : mySeries.keySet()) {
			myChart.getData().add(mySeries.get((Paint)p));
		}

	}
	
	private void updateData() {
		
	}
	
	private TextInputControl makeTextBox() {
		inputField = new TextField();
		inputField.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
		inputField.setFocusTraversable(false);
		inputField.setPromptText(GuiText.getString("PromptText"));
		inputField.setOnAction((event) -> {
			tryFile(inputField.getText());
			guiLayout.setBottom(setBottomBox());
		});
		return inputField;
	}

	protected void tryFile(String s) {
		NoTextEnteredBox tb;
		if (s.equals(""))
			tb = new NoTextEnteredBox();
		else {
			try {
				loadFile(s);
				mySimUrl = s;
				initializeCellGrid();
				inputField.clear();
				myAnimation.stop();
			} catch (NullPointerException e) {
				NoTextEnteredBox nte = new NoTextEnteredBox();
			} catch (IOException e) {
				XmlReaderErrorBox xeBox = new XmlReaderErrorBox();
			} catch (ParserConfigurationException e) {
				XmlReaderErrorBox xeBox = new XmlReaderErrorBox();
			} catch (SAXException e) {
				XmlReaderErrorBox xeBox = new XmlReaderErrorBox();
			}
		}
	}

	/**
	 * Retrieves the data from the XML file specified by the reader, starts a
	 * simulation with the information, and then returns and array of cell images to
	 * the GUI
	 * 
	 * @param s
	 *            Name of the file
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws Exception
	 *             FileNotFoundException
	 */
	private void loadFile(String s) throws ParserConfigurationException, SAXException, IOException {
		myAnimation.stop();
		guiLayout.setCenter(null);
		currentSim = FileHandler.fileReader(s);
	}

	public void update() throws NullPointerException {
		if (currentSim == null)
			throw new NullPointerException("Current Sim Null");
		else {
			currentSim.step();
			populateData();
			stepNumber++;
		}
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

	public void changeSpeed(double speed) {
		updateRate = speed;
		myAnimation.stop();
		myFrame = new KeyFrame(Duration.seconds(updateRate), e -> update());
		myAnimation.getKeyFrames().clear();
		myAnimation.getKeyFrames().add(myFrame);
		myAnimation.play();
	}

	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate
	 * number of cells
	 */
	private void initializeCellGrid() {
		centerPane = currentSim.getView(GRID_SIZE, GRID_SIZE);
		guiLayout.setCenter(centerPane);
	}

	private Pane setLeftBox() {
		VBox leftPane = new VBox(BUTTON_SPACING);
		leftPane.setAlignment(Pos.TOP_CENTER);
		leftPane.setPrefWidth(INSET_DISTANCE);
		Button eButton = new Button(GuiText.getString("ExportButton"));
		leftPane.getChildren().addAll(eButton);
		return leftPane;
	}
}
