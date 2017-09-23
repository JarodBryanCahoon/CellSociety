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
		mainStage.setTitle("Cell Society Simulation");
		setLayout();
		mainScene = new Scene(guiLayout, 650, 650);
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
		inputField.setPromptText("Not currently working");
		guiLayout.setTop(topBox);
		
		
		topBox.getChildren().addAll(insLabel, inputField);
	}
	
	/**
	 * Initialized the gridPane to be of the appropriate size, with the appropriate number of cells
	 */
	private void initializeCellGrid() {
		cellGrid = new GridPane();
		cellGrid.setGridLinesVisible(true);
		int rows = 20;
		int cols = 20;
		
		for(int i = 0; i < rows; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / cols);
            cellGrid.getColumnConstraints().add(colConst);
		}
		for(int i = 0; i < cols; i++) {
			RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            cellGrid.getRowConstraints().add(rowConst);
		}
		
		
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				Button b = new Button(String.format("%d, %d",i,j));
				cellGrid.add(b, i, j);
			}
		}
		
		guiLayout.setCenter(cellGrid);
	}
	
	public static void main(String[] args) {
		launch(args); 
	}
	
	
}
