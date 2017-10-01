package cells;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class LangtonCell extends Cell {

	private static final Color[] COLORS = new Color[] { Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW,
			Color.PINK, Color.WHITE, Color.CYAN };

	private static final Map<String, Integer> rules = loadRules("data/LangtonRules");

	private static final int MAX_STATE = 7;

	public LangtonCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	/*
	 * Basic syntax from
	 * https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in
	 * -java.html
	 */
	private static Map<String, Integer> loadRules(String fileName) {
		Map<String, Integer> rules = new HashMap<String, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				List<Integer> ints = new ArrayList<Integer>();
				ints.add(-1);
				for (int i = 1; i < 5; i++) {
					ints.add(Integer.parseInt(line.substring(i, i + 1)));
				}
				ints.set(0, Integer.parseInt(line.substring(0, 1)));
				for(int i = 0; i < 4; i++) {
					
					rules.put(ints.toString(), Integer.parseInt(line.substring(5, 6)));
					int temp = ints.get(1);
					for(int n = 1; n < 4; n++)
						ints.set(n, ints.get(n+1));
					ints.set(4, temp);
				}
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
		states.add(getState());
		for (int i = 0; i < neighborhood.size(); i++) {
			Cell c = neighborhood.get(i);
			if (c == null)
				states.add(0);
			else
				states.add(c.getState());
		}
		if (rules.containsKey(states.toString()))
			nextState = rules.get(states.toString());
	}

	@Override
	protected void cycle() {
		if(getState() == MAX_STATE)
			nextState = EMPTY;
		else
			nextState = getState()+1;
		update();
	}
}
