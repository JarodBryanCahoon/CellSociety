package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * Holds the parameters for a given simulation so they can be accessed by cells,
 * modified by the user, and saved by the simulation.
 * 
 * Depends On Lists, ResourceBundle, Sliders, Panes, Labels
 * Assumes all parameters are ints/doubles, and that the correct parameters are given
 * 
 * Relies on its properties file
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ParameterBundle {
	private static final double MAX_INT_VAL = 20;
	private Object[] parameters;
	private String[] names;
	private List<Slider> sliders = new ArrayList<Slider>();

	private ResourceBundle params = ResourceBundle.getBundle("resources/Parameters");

	/**
	 * @param cellType Used to get the names of the parameters from the properties file
	 * @param parameters Integers and Doubles
	 */
	public ParameterBundle(String cellType, Object... parameters) {
		this.parameters = parameters;
		names = params.getString(cellType).split(",");
	}

	/**
	 * Accessible only to cells (package private)
	 * @param i index of parameter
	 * @return specified parameter
	 */
	Object getParameter(int i) {
		return parameters[i];
	}

	
	/**
	 * Returns a panel with sliders to directly modify the parameters
	 * 
	 * @param width width of pane
	 * @param height height of pane
	 * @return pane of specified size with sliders and labels
	 */
	public Pane getOptionPane(double width, double height) {
		Pane pane = new FlowPane(Orientation.VERTICAL);
		for (int i = 0; i < parameters.length; i++) {
			Slider s = constructSlider(i);
			sliders.add(s);
			pane.getChildren().add(new Label(names[i]));
			pane.getChildren().add(s);
		}
		return pane;
	}

	// See oracle documentation
	private Slider constructSlider(int index) {
		Object num = parameters[index];
		Slider slider = new Slider();
		try {
			int i = (int) num;
			slider.setMin(0);
			slider.setMax(MAX_INT_VAL); 
			slider.setValue(i);
			slider.setMajorTickUnit(5); // Reasonable scale
			slider.setMinorTickCount(4); // Count by 1s
			slider.setSnapToTicks(true);
			slider.valueProperty().addListener((a, b, newValue) -> {
				parameters[index] = newValue.intValue();
			});
		} catch (Exception e) {
			double d = (double) num;
			slider.setMin(0.0);
			slider.setMax(1.0);
			slider.setValue(d);
			slider.setMajorTickUnit(.5);
			slider.setBlockIncrement(.01); // Roughly continuous
			slider.valueProperty().addListener((a, b, newValue) -> {
				parameters[index] = newValue;
			});
		}
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);

		return slider;
	}

	/**
	 * @return a copy of all the parameters
	 */
	public Object[] getParameters() {
		return parameters.clone();
	}
}
