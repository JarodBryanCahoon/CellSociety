package cells;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class LangtonCell extends Cell {

	private static final Color[] COLORS = new Color[] { Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.PINK,
			Color.ORANGE, Color.PURPLE, Color.WHITE };

	private static final Map<Integer[], Integer> rules = loadRules("data/LangtonRules");

	public LangtonCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	/*
	 * Basic syntax from
	 * https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in
	 * -java.html
	 */
	private static Map<Integer[], Integer> loadRules(String fileName) {
		Map<Integer[], Integer> rules = new HashMap<Integer[], Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				Integer[] ints = new Integer[5];
				ints[0] = -1;
				for (int i = 1; i < 5; i++) {
					ints[i] = Integer.parseInt(line.substring(i, i + 1));
				}
				Arrays.sort(ints);
				ints[0] = Integer.parseInt(line.substring(0, 1));
				rules.put(ints, Integer.parseInt(line.substring(5, 6)));
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("LangtonRules has been modified");
		}
		return rules;
	}

	@Override
	public void step(List<Cell> neighborhood) {
		List<Integer> states = new ArrayList<Integer>();
		for (Cell c : neighborhood) {
			states.add(c.getState());
		}
		Collections.sort(states);
		states.add(0, getState());
		nextState = rules.get(states.toArray());
	}
}
