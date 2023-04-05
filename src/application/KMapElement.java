package application;

import java.util.ArrayList;
import java.util.List;

import digitalLogic.PrimitiveBinaryData;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A JavaFX node which shows the data from PrimitiveBinaryData in the form of a
 * Karnaugh map.
 * 
 * @author Sam McDowell
 */
public class KMapElement extends VBox {

	/********************************************************************
	 * 
	 * CONSTRUCTOR
	 * 
	 ********************************************************************/

	/**
	 * Creates a KMapElement from PrimitiveBinaryData
	 * 
	 * @param data a PrimitiveBinaryData object
	 */
	public KMapElement(PrimitiveBinaryData data) {

		// <2 variables
		if (data.getVariables().size() < 2) {
			this.getChildren().add(new Label("None"));
			// 2 variables
		} else if (data.getVariables().size() == 2) {
			this.getChildren().add(twoVariableKMap(data.getData(), data.getVariables()));
			// 3 variables
		} else if (data.getVariables().size() == 3) {
			this.getChildren().add(threeVariableKMap(data.getData(), data.getVariables()));
			// 4 variables
		} else if (data.getVariables().size() == 4) {
			this.getChildren().add(fourVariableKMap(data.getData(), data.getVariables()));
			// >4 variables
		} else if (data.getVariables().size() > 4) {

			for (int mapNum = 0; mapNum < Math.pow(2, data.getVariables().size() - 4); mapNum++) {

				// Make Labels
				ArrayList<ArrayList<Character>> binList = PrimitiveBinaryData
						.binaryList(data.getVariables().size() - 4);
				List<Character> upperVar = data.getVariables().subList(0, data.getVariables().size() - 4);
				String labelText = "";
				for (int var = 0; var < data.getVariables().size() - 4; var++) {
					labelText += upperVar.get(var) + "=" + binList.get(mapNum).get(var) + "  ";
				}
				this.getChildren().add(new Label(labelText));

				// Make K-Map
				this.getChildren()
						.add(fourVariableKMap(new ArrayList<>(data.getData().subList(16 * mapNum, 16 * (mapNum + 1))),
								new ArrayList<>(data.getVariables().subList(data.getVariables().size() - 4,
										data.getVariables().size()))));
			}
		}
	}

	/********************************************************************
	 * 
	 * UTILITY
	 * 
	 ********************************************************************/

	/**
	 * Creates a 4-variable Karnaugh map as a GridPane object
	 * 
	 * @param data      The internal data as a 16-element ArrayList of characters.
	 * @param variables The variables to use for the header as a 4-element ArrayList
	 *                  of characters.
	 * @return A four variable Karnaugh map as a GridPane object.
	 */
	private GridPane fourVariableKMap(ArrayList<Character> data, ArrayList<Character> variables) {

		GridPane out = new GridPane();

		// Row headers
		out.add(new BorderLabel(
				variables.get(0) + "" + variables.get(1) + "/" + variables.get(2) + "" + variables.get(3), false, true,
				true, false), 0, 0);
		out.add(new BorderLabel("00", false, true, false, false), 0, 1);
		out.add(new BorderLabel("01", false, true, false, false), 0, 2);
		out.add(new BorderLabel("11", false, true, false, false), 0, 3);
		out.add(new BorderLabel("10", false, true, false, false), 0, 4);

		for (Node n : out.getChildren()) {
			((Label) n).setPrefWidth(50);
		}

		// Column headers
		out.add(new BorderLabel("00", false, false, true, false), 1, 0);
		out.add(new BorderLabel("01", false, false, true, false), 2, 0);
		out.add(new BorderLabel("11", false, false, true, false), 3, 0);
		out.add(new BorderLabel("10", false, false, true, false), 4, 0);

		// Data
		out.add(new Label(data.get(0) + ""), 1, 1);
		out.add(new Label(data.get(1) + ""), 2, 1);
		out.add(new Label(data.get(2) + ""), 4, 1);
		out.add(new Label(data.get(3) + ""), 3, 1);

		out.add(new Label(data.get(4) + ""), 1, 2);
		out.add(new Label(data.get(5) + ""), 2, 2);
		out.add(new Label(data.get(6) + ""), 4, 2);
		out.add(new Label(data.get(7) + ""), 3, 2);

		out.add(new Label(data.get(8) + ""), 1, 4);
		out.add(new Label(data.get(9) + ""), 2, 4);
		out.add(new Label(data.get(10) + ""), 4, 4);
		out.add(new Label(data.get(11) + ""), 3, 4);

		out.add(new Label(data.get(12) + ""), 1, 3);
		out.add(new Label(data.get(13) + ""), 2, 3);
		out.add(new Label(data.get(14) + ""), 4, 3);
		out.add(new Label(data.get(15) + ""), 3, 3);

		for (Node n : out.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
		}

		out.setAlignment(Pos.CENTER);

		return out;
	}

	/**
	 * Creates a 3-variable Karnaugh map as a GridPane object
	 * 
	 * @param data      The internal data as a 8-element ArrayList of characters.
	 * @param variables The variables to use for the header as a 3-element ArrayList
	 *                  of characters.
	 * @return A three variable Karnaugh map as a GridPane object.
	 */
	private GridPane threeVariableKMap(ArrayList<Character> data, ArrayList<Character> variables) {

		GridPane out = new GridPane();

		// Column headers
		out.add(new BorderLabel(variables.get(0) + "" + variables.get(1) + "/" + variables.get(2), false, true, true,
				false), 0, 0);
		out.add(new BorderLabel("00", false, true, false, false), 0, 1);
		out.add(new BorderLabel("01", false, true, false, false), 0, 2);
		out.add(new BorderLabel("11", false, true, false, false), 0, 3);
		out.add(new BorderLabel("10", false, true, false, false), 0, 4);

		for (Node n : out.getChildren()) {
			((Label) n).setPrefWidth(50);
		}

		// Row headers
		out.add(new BorderLabel("0", false, false, true, false), 1, 0);
		out.add(new BorderLabel("1", false, false, true, false), 2, 0);

		// Data
		out.add(new Label(data.get(0) + ""), 1, 1);
		out.add(new Label(data.get(1) + ""), 2, 1);

		out.add(new Label(data.get(2) + ""), 1, 2);
		out.add(new Label(data.get(3) + ""), 2, 2);

		out.add(new Label(data.get(4) + ""), 1, 4);
		out.add(new Label(data.get(5) + ""), 2, 4);

		out.add(new Label(data.get(6) + ""), 1, 3);
		out.add(new Label(data.get(7) + ""), 2, 3);

		for (Node n : out.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
		}

		out.setAlignment(Pos.CENTER);

		return out;

	}

	/**
	 * Creates a 2-variable Karnaugh map as a GridPane object
	 * 
	 * @param data      The internal data as a 4-element ArrayList of characters.
	 * @param variables The variables to use for the header as a 2-element ArrayList
	 *                  of characters.
	 * @return A two variable Karnaugh map as a GridPane object.
	 */
	private GridPane twoVariableKMap(ArrayList<Character> data, ArrayList<Character> variables) {

		GridPane out = new GridPane();

		// Column headers
		out.add(new BorderLabel(variables.get(0) + "/" + variables.get(1), false, true, true, false), 0, 0);
		out.add(new BorderLabel("0", false, true, false, false), 0, 1);
		out.add(new BorderLabel("1", false, true, false, false), 0, 2);

		for (Node n : out.getChildren()) {
			((Label) n).setPrefWidth(50);
		}

		// Row headers
		out.add(new BorderLabel("0", false, false, true, false), 1, 0);
		out.add(new BorderLabel("1", false, false, true, false), 2, 0);

		// Data
		out.add(new Label(data.get(0) + ""), 1, 1);
		out.add(new Label(data.get(1) + ""), 2, 1);
		out.add(new Label(data.get(2) + ""), 1, 2);
		out.add(new Label(data.get(3) + ""), 2, 2);

		for (Node n : out.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
		}

		out.setAlignment(Pos.CENTER);

		return out;

	}

}
