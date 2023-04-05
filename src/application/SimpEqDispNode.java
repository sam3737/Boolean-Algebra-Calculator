package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SimpEqDispNode extends VBox {

	private Label simpEqLabel;

	public SimpEqDispNode() {

		// FORMATTING
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getStyleClass().add("box");
		this.setSpacing(10);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
				BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

		// HEADER
		this.getChildren().add(new Label("Simplified Equation(s): "));

		// EQUATION LABEL
		simpEqLabel = new Label("?");
		this.getChildren().add(simpEqLabel);

	}

	public void setSimpEq(ArrayList<String> strArr) {

		simpEqLabel.setText(strArr.get(0));
		for (int i = 1; i < strArr.size(); i++) {
			simpEqLabel.setText(simpEqLabel.getText() + "\n" + strArr.get(i));
		}

	}

	public void setUnknown() {
		simpEqLabel.setText("?");
	}

}
