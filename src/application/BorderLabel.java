package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * A JavaFX label which can have a border easily applied at construction.
 * 
 * @author Sam McDowell
 */
public class BorderLabel extends Label {

	/********************************************************************
	 * 
	 * CONSTRUCTOR
	 * 
	 ********************************************************************/

	/**
	 * Creates a BorderLabel object with specified text, and a black border on any
	 * of its four sides.
	 * 
	 * @param text   The text for the label to contain.
	 * @param top    If the label should have a top border.
	 * @param right  If the label should have a right border.
	 * @param bottom If the label should have a bottom border.
	 * @param left   If the label should have a left border.
	 */
	public BorderLabel(String text, boolean top, boolean right, boolean bottom, boolean left) {

		this.setAlignment(Pos.CENTER);

		this.setText(text);
		this.setPadding(new Insets(0, 5, 0, 5));

		this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
				strokeFromBool(top), strokeFromBool(right), strokeFromBool(bottom), strokeFromBool(left),
				CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
	}

	/********************************************************************
	 * 
	 * UTILITY
	 * 
	 ********************************************************************/

	/**
	 * Get a solid border stroke style from true and no stroke from false.
	 * 
	 * @param bool If there should be a border
	 * @return Either BorderStrokeStyle.SOLID from true or BorderStrokeStyle.NONE
	 *         from false.
	 */
	private BorderStrokeStyle strokeFromBool(boolean bool) {
		if (bool)
			return BorderStrokeStyle.SOLID;
		else
			return BorderStrokeStyle.NONE;
	}

}
