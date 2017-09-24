package userInterface;

import java.util.ResourceBundle;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ResourceBundle;


public class GUI extends Application{
	public static final double BUTTON_MAX_WIDTH = 120;
	public static final double GRID_SIZE = 300;
	public static final double GUI_SIZE = 650;
	public static final double BUTTON_VERTICAL_SHIFT = 320;
	public static final double TEXT_FIELD_PREF_WIDTH = 300;
	
	private ResourceBundle GuiText = ResourceBundle.getBundle("resources/GuiNameBundle");
	private int gridWidth;
	private int gridHeight;
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
		
		
	}
	
	
	/**
	 * Adds all appropriate widgets to the main GUI (Buttons, Labels, TextBoxes, etc) except for the GridPane containing the cells,
	 * which is handled by a different method which requires more information.
	 */
	private void setLayout() {
		guiLayout = new BorderPane();
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(15, 12, 15, 12));
		topBox.setSpacing(30);
		guiLayout.setTop(topBox);
		
		VBox leftSideBox = new VBox();
		leftSideBox.setPadding(new Insets(10));
		leftSideBox.setSpacing(13);
		guiLayout.setLeft(leftSideBox);
		
		Button playButton = new Button(GuiText.getString("PlayButton"));
		Button pauseButton = new Button(GuiText.getString("PauseButton"));
		Button stepButton = new Button(GuiText.getString("StepButton"));
		
		playButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		pauseButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		stepButton.setTranslateY(BUTTON_VERTICAL_SHIFT);
		playButton.setMaxWidth(BUTTON_MAX_WIDTH);
		pauseButton.setMaxWidth(BUTTON_MAX_WIDTH);
		stepButton.setMaxWidth(BUTTON_MAX_WIDTH);
		leftSideBox.getChildren().addAll(playButton, pauseButton, stepButton);
		
		Label insLabel = new Label(GuiText.getString("XmlLabel"));
		inputField = new TextField();
		inputField.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
		inputField.setFocusTraversable(false);
		inputField.setPromptText(GuiText.getString("PromptText"));
		guiLayout.setTop(topBox);
		//guiLayout.setBottom(null);
		//guiLayout.setRight(null);
		
		
		topBox.getChildren().addAll(insLabel, inputField);
	}
	
	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate number of cells
	 */
	private void initializeCellGrid() {
		cellGrid = new GridPane();
		cellGrid.setGridLinesVisible(true);
		int rows = 10;
		int cols = 10;
		
		for(int i = 0; i < rows; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / cols);
            cellGrid.getColumnConstraints().add(colConst);
		}
		for(int i = 0; i < rows; i++) {
			RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            cellGrid.getRowConstraints().add(rowConst);
		}
		
		
		guiLayout.setCenter(cellGrid);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
