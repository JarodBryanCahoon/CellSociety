package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ParameterBundle {	
	private Object[] parameters;
	private String[] names;
	private List<Slider> sliders = new ArrayList<Slider>();
	
	private ResourceBundle params = ResourceBundle.getBundle("resources/Parameters");

	public ParameterBundle(String cellType, Object ... parameters) {
		this.parameters = parameters;
		names = params.getString(cellType).split(",");
	}

	Object getParameter(int i) {
			return parameters[i];
	}
	
	public Pane getOptionPane(double width, double height) {
		Pane pane = new FlowPane(Orientation.VERTICAL);
		for(int i = 0; i < parameters.length; i++) {
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
			int i = (int)num;
			slider.setMin(0);
			slider.setMax(20);
			slider.setValue(i);
			slider.setMajorTickUnit(5);
			slider.setMinorTickCount(4);
			slider.setSnapToTicks(true);
			slider.valueProperty().addListener((a,b,newValue)-> {
				parameters[index] = newValue.intValue();
			});
		}catch(Exception e) {			
			double d = (double)num;
			slider.setMin(0.0);
			slider.setMax(1.0);
			slider.setValue(d);
			slider.setMajorTickUnit(.5);
			slider.setBlockIncrement(.01);
			slider.valueProperty().addListener((a,b,newValue)-> {
				parameters[index] = newValue;
			});
		}
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		
		return slider;
	}
	
	public Object[] getParameters() {
		return parameters.clone();
	}
}
