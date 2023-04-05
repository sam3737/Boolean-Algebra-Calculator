package application;

import java.util.ArrayList;

import digitalLogic.PrimitiveBinaryData;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * A JavaFX node which displays binary data in the form of a truth table.
 * 
 * @author Sam McDowell
 */
public class TruthTableElement extends GridPane {

	/********************************************************************
	 * 
	 * CONSTRUCTOR
	 * 
	 ********************************************************************/

	/**
	 * Constructs a TruthTableElement from specified PrimitiveBinaryData.
	 * 
	 * @param data The PrimitiveBinaryData to display.
	 */
	public TruthTableElement(PrimitiveBinaryData data) {

		this.setAlignment(Pos.CENTER);

		// width and height of gridPane
		int width = data.getVariables().size() + 1;
		int height = data.getData().size() + 1;

		// add headers for variables
		for (int header = 0; header < width - 1; header++) {
			this.add(new BorderLabel(data.getVariables().get(header) + "", false, false, true, false), header, 0);
		}
		// add header for output
		this.add(new BorderLabel("-", false, false, true, true), width, 0);

		// add data
		ArrayList<ArrayList<Character>> inputs = PrimitiveBinaryData.binaryList(data.getVariables().size());
		for (int row = 0; row < height - 1; row++) {
			// input data
			for (int col = 0; col < width - 1; col++) {
				this.add(new BorderLabel(inputs.get(row).get(col) + "", false, false, false, false), col, row + 1);
			}
			// output data
			this.add(new BorderLabel(data.getData().get(row) + "", false, false, false, true), width, row + 1);
		}
	}
}
