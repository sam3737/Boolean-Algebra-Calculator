package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A button which creates a new window containing some text.
 * 
 * @author Sam McDowell
 */
public class InfoButton extends Button {

	/********************************************************************
	 * 
	 * FIELDS
	 * 
	 ********************************************************************/

	private String windowText, windowTitle;
	private int windowWidth, windowHeight, textMargin;

	/********************************************************************
	 * 
	 * CONSTRUCTORS
	 * 
	 ********************************************************************/

	/**
	 * Creates a button which creates a new window containing the specified text.
	 * Uses default values for other fields.
	 * 
	 * @param windowText A text string for the new window.
	 */
	public InfoButton(String windowText) {
		this.windowText = windowText;
		this.setOnAction(new WindowOpenHandler());

		this.setText("?");
		windowWidth = windowHeight = 450;
		textMargin = 20;
		windowTitle = "Info";
	}

	/**
	 * Creates a button which creates a new window. All fields must be specified.
	 * 
	 * @param windowText   A text string for the new window.
	 * @param windowTitle  A title for the new window.
	 * @param windowWidth  The pixel width of the new window.
	 * @param windowHeight The pixel height of the new window.
	 * @param textMargin   The pixel margin for the text wrap at the edge of the
	 *                     window.
	 */
	public InfoButton(String windowText, String windowTitle, int windowWidth, int windowHeight, int textMargin) {
		this.windowText = windowText;
		this.windowTitle = windowTitle;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.textMargin = textMargin;
	}

	/********************************************************************
	 * 
	 * EVENT HANDLING
	 * 
	 ********************************************************************/

	/**
	 * Opens a new window with windowText contained in it.
	 */
	private class WindowOpenHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {

			ScrollPane root = new ScrollPane();
			root.setPadding(new Insets(textMargin));
			root.getStyleClass().add("root");
			Text text = new Text(windowText);
			root.setContent(text);
			text.setWrappingWidth(windowWidth - 3 * textMargin);

			Stage stage = new Stage();
			stage.getIcons().add(new Image("resources/qmark.png"));
			stage.setTitle(windowTitle);
			stage.setScene(new Scene(root, windowWidth, windowHeight));
			stage.getScene().getStylesheets().add("application/application.css");
			stage.show();

			event.consume();
		}
	}
}
