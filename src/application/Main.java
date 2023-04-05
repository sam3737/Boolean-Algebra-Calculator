package application;

import digitalLogic.PrimitiveBinaryData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The driver for the digitalLogicCalculator program.
 * 
 * @author Sam McDowell
 */
public class Main extends Application {

	/********************************************************************
	 * 
	 * FIELDS
	 * 
	 ********************************************************************/

	ScrollPane kMapPane, ttPane;
	BinaryDataEntryNode entry;
	SimpEqDispNode simpEq;

	/********************************************************************
	 * 
	 * FORMATTING
	 * 
	 ********************************************************************/

	public static int compute(int[] num, int k, int i) {
		if (i == num.length)
			return 0;
		else {
			if (num[i] == k) {
				num[i] += 2;
				return compute(num, k, i + 1) + 1;
			} else
				return compute(num, k, i + 1);
		}
	}

	@Override
	public void start(Stage primaryStage) {

		try {

			// ROOT
			VBox root = new VBox();
			root.setSpacing(10);
			root.setPadding(new Insets(10, 10, 10, 10));

			// ENTRY PANE
			entry = new BinaryDataEntryNode();
			entry.addEventHandler(ActionEvent.ACTION, new InputHandler());

			// SIMPLIFIED EQUATION
			simpEq = new SimpEqDispNode();

			// KMAP PANE
			kMapPane = new ScrollPane();
			kMapPane.setFitToWidth(true);
			kMapPane.prefHeightProperty().bind(root.heightProperty());
			kMapPane.setPadding(new Insets(10, 10, 10, 10));
			kMapPane.setContent(new Label("?"));
			kMapPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
					BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
					CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

			// TRUTH TABLE PANE
			ttPane = new ScrollPane();
			ttPane.setFitToWidth(true);
			ttPane.setPadding(new Insets(10, 10, 10, 10));
			ttPane.setContent(new Label("?"));
			ttPane.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
					BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
					CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

			// ROW FOR TT AND KMAP
			GridPane gp = new GridPane();
			gp.setHgap(10);
			gp.add(kMapPane, 0, 0);
			gp.add(ttPane, 1, 0);

			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(100);
			gp.getColumnConstraints().addAll(col1, col1);

			// ADD ALL TO ROOT
			root.getChildren().addAll(entry, simpEq, gp);
			root.getStyleClass().add("root");

			// SCENE
			Scene scene = new Scene(root, 600, 410);
			scene.getStylesheets().add("application/application.css");

			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("resources/binaryCalc.png"));
			primaryStage.show();
			primaryStage.setMinWidth(350);
			primaryStage.setMinHeight(400);
			primaryStage.setTitle("Boolean Algebra Calculator");

			root.requestFocus();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/********************************************************************
	 * 
	 * LOGIC
	 * 
	 ********************************************************************/

	/**
	 * Update output displays with appropriate data from a BinaryDataEntryNode.
	 */
	private class InputHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (((BinaryDataEntryNode) e.getSource()).checkInputValidity()) {
				PrimitiveBinaryData data = ((BinaryDataEntryNode) e.getSource()).getData();
				kMapPane.setContent(data.makeKMapElement());
				ttPane.setContent(data.makeTruthTableElement());
				simpEq.setSimpEq(data.makeEquation());
			} else {
				kMapPane.setContent(new Label("?"));
				ttPane.setContent(new Label("?"));
				simpEq.setUnknown();
			}

		}
	}

}
