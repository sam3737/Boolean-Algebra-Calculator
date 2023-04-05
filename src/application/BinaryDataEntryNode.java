package application;

import java.util.ArrayList;
import java.util.Collections;

import digitalLogic.Equation;
import digitalLogic.PrimitiveBinaryData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * BinaryDataEntryNode
 * 
 * This class is a JavaFX node which allows a user to enter data which can be
 * interpreted in the form of PrimitiveBinaryData.
 * 
 * @author Sam McDowell
 */
public class BinaryDataEntryNode extends GridPane {

	/********************************************************************
	 * 
	 * FIELDS
	 * 
	 ********************************************************************/

	private ComboBox<String> entrySelect;
	private TextField mainField, variableField, dontCareField;
	private HBox mainEntryBox, variableEntryBox, dontCareEntryBox;
	private Label overviewLabel;
	private Label variableEntryLabel;
	private Label dontCareEntryLabel;
	private InfoButton infoButton;

	/********************************************************************
	 * 
	 * CONSTRUCTOR
	 * 
	 ********************************************************************/

	/**
	 * Creates a BinaryDataEntry node.
	 */
	public BinaryDataEntryNode() {

		// FORMATTING
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setHgap(10);
		this.setVgap(10);
		this.getStyleClass().add("box");
		ColumnConstraints labelCol = new ColumnConstraints();
		ColumnConstraints entryCol = new ColumnConstraints();
		labelCol.setPercentWidth(20);
		entryCol.setPercentWidth(80);
		this.getColumnConstraints().addAll(labelCol, entryCol);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
				BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

		// MAIN ENTRY
		entrySelect = new ComboBox<String>();
		entrySelect.setMinHeight(30);
		entrySelect.getItems().add("Equation");
		entrySelect.getItems().add("Minterms");
		entrySelect.getItems().add("Data");
		entrySelect.setOnAction(new EntrySelectHandler());
		entrySelect.getSelectionModel().select("Equation");

		mainField = new TextField();

		mainEntryBox = new HBox();
		mainEntryBox.getChildren().addAll(entrySelect, mainField);

		// VARAIBLE ENTRY
		variableEntryLabel = new Label("Varaibles");
		variableEntryLabel.setMinHeight(30);
		variableEntryLabel.setPadding(new Insets(0, 10, 0, 0));

		variableField = new TextField();

		variableEntryBox = new HBox();
		variableEntryBox.getChildren().addAll(variableEntryLabel, variableField);

		// DON'T CARES ENTRY
		dontCareEntryLabel = new Label("Don't Cares");
		dontCareEntryLabel.setMinHeight(30);
		dontCareEntryLabel.setPadding(new Insets(0, 10, 0, 0));

		dontCareField = new TextField();

		dontCareEntryBox = new HBox();
		dontCareEntryBox.getChildren().addAll(dontCareEntryLabel, dontCareField);

		// OVERVIEW LABEL
		overviewLabel = new Label("Enter some data");

		// INFO BUTTON
		String INFO = "This software solves and displays information pertaining to binary logic.\n\n" + "INPUTS:\n\n"
				+ "Equation - The operators * (AND), + (OR), ' (NOT), as well as parantheses can be used to denote operations. All other characters can be used as varaibles. Directly adjascent variables will have the AND operator applied.\n\n"
				+ "Minterms - The minterm box should contain the indices of all elements that should be considered HIGH. The varaible box contains the characters which will be used as variables. There must be enough varaibles to accomadate the largest index entered. The \"Don't Cares\" box should be filled with the indices of elements which should be DON'T CARES. All other indices will be LOW.\n\n"
				+ "Data - The Data box should contain a list of 1 (HIGH), 0 (LOW), and X (DON'T CARE). This represents the output of the binary system at each index, starting at zero. The variables box contains the characters which will be used as varaibles. It must have enough to accomdate the length of the data input. All un-entered indices accomodated by the variables will be considered LOW.\n\n"
				+ "OUTPUTS:\n\n"
				+ "Simplified Equation - This shows the simplest possible equation that can be created from the data entered. If there are multiple options, all simple solutions will be listed.\n\n"
				+ "Karnaugh Map - The K-map is a method often used to simplify binary algebra. If contains the same data as a truth table, but in a multi-dimensional matrix rather than a list.\n\n"
				+ "Truth Table - The truth table shows the output of the system for every input.";
		infoButton = new InfoButton(INFO);
		GridPane.setHalignment(infoButton, HPos.RIGHT);

		enableEquationEntry();

	}

	/********************************************************************
	 * 
	 * MODE ENABLERS
	 * 
	 ********************************************************************/

	/**
	 * Formats the node to accept data in the form of an equation string.
	 */
	private void enableEquationEntry() {
		this.getChildren().clear();
		this.add(entrySelect, 0, 0);
		this.add(mainField, 1, 0);
		this.add(overviewLabel, 0, 1, 2, 1);
		this.add(infoButton, 1, 1);
		this.requestFocus();
	}

	/**
	 * Formats the node to accept data in the form of raw data.
	 */
	private void enableDataEntry() {
		this.getChildren().clear();
		this.add(entrySelect, 0, 0);
		this.add(mainField, 1, 0);
		this.add(variableEntryLabel, 0, 1);
		this.add(variableField, 1, 1);
		this.add(overviewLabel, 0, 2, 2, 1);
		this.add(infoButton, 1, 2);
		this.requestFocus();
	}

	/**
	 * Formats the node to accept data in the form of minterms.
	 */
	private void enableMintermEntry() {
		this.getChildren().clear();
		this.add(entrySelect, 0, 0);
		this.add(mainField, 1, 0);
		this.add(variableEntryLabel, 0, 1);
		this.add(variableField, 1, 1);
		this.add(dontCareEntryLabel, 0, 2);
		this.add(dontCareField, 1, 2);
		this.add(overviewLabel, 0, 3, 2, 1);
		this.add(infoButton, 1, 3);
		this.requestFocus();
	}

	/**
	 * Changes entry state when drop box is changed.
	 */
	private class EntrySelectHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// Call enabler based on what it is changed to
			switch (((ComboBox<?>) e.getSource()).getValue().toString()) {
			case "Equation":
				enableEquationEntry();
				break;
			case "Minterms":
				enableMintermEntry();
				break;
			case "Data":
				enableDataEntry();
				break;
			default:
				break;
			}
			mainField.clear();
			variableField.clear();
			dontCareField.clear();
			e.consume();
		}
	}

	/********************************************************************
	 * 
	 * INPUT VALIDATION
	 * 
	 ********************************************************************/

	/**
	 * Gets how many instances of a character occur in a string.
	 * 
	 * @param str The string to check.
	 * @param ch  The character to look for.
	 * @return The number of instances of the character.
	 */
	private int count(String str, char ch) {
		int out = 0;
		for (char c : str.toCharArray()) {
			if (c == ch)
				out++;
		}
		return out;
	}

	/**
	 * Checks if each string in a string array is unique
	 * 
	 * @param strArr The string to check
	 * @return True if each is unique
	 */
	private boolean eachIsUnique(String[] strArr) {
		for (int i = 0; i < strArr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (strArr[i].equals(strArr[j]))
					return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a TextField contains any text.
	 * 
	 * @param field The TextField to check.
	 * @return If any text is present in the TextField.
	 */
	private boolean isFilled(TextField field) {
		if (field.getText().length() > 0)
			return true;
		else {
			overviewLabel.setText("Required field is empty");
			return false;
		}
	}

	/**
	 * Verifies if the variable entry field is valid.
	 * 
	 * @param maxValue The largest index that must be accommodated by the variables.
	 * @return If the variable field will return valid data.
	 */
	private boolean varFieldIsValid(int maxValue) {

		char[] varStr = variableField.getText().replace(" ", "").replace(",", "").toCharArray();
		for (int i = 0; i < varStr.length; i++) {
			for (int j = i + 1; j < varStr.length; j++) {
				if (varStr[i] == varStr[j]) {
					overviewLabel.setText("Duplicate variables entered");
					return false;
				}
			}
		}
		// check if there are enough variables
		if (Math.pow(2, varStr.length) - 1 < maxValue) {
			overviewLabel.setText("More varaibles required");
			return false;
		}
		return true;
	}

	/**
	 * Checks if all inputs for the current input type are valid.
	 * 
	 * @return If the node can return a PrimitiveBinaryData object successfully.
	 */
	public boolean checkInputValidity() {

		// text from mainField
		String mainFieldText = mainField.getText();

		// Verify based on selected entry mode
		switch (entrySelect.getValue()) {

		// Equation validation
		case "Equation":

			if (!isFilled(mainField)) {
				return false;
			}

			if (mainFieldText.contains("++") || // consecutive operators
					mainFieldText.contains("**") || mainFieldText.charAt(mainFieldText.length() - 1) == '+' || // unpaired
																												// operator
					mainFieldText.charAt(mainFieldText.length() - 1) == '*' || mainFieldText.charAt(0) == '+'
					|| mainFieldText.charAt(0) == '*' || (count(mainFieldText, '(') != (count(mainFieldText, ')')))) { // unpaired
																														// parenthesis
				overviewLabel.setText("Invalid equation");
				return false;
			}
			overviewLabel.setText("Equation entered");
			break;

		// Minterm validation
		case "Minterms":

			if (!isFilled(mainField) || !isFilled(variableField)) {
				return false;
			}

			// Check if minterms and dont cares can be formatted as numbers
			int maxValue = -1;
			String[] data = (mainField.getText() + " " + dontCareField.getText()).replace(",", " ").split(" ");
			try {
				for (String s : data) {
					if (Integer.valueOf(s) > maxValue)
						maxValue = Integer.valueOf(s);
				}
			} catch (NumberFormatException e) {
				overviewLabel.setText("Number format error");
				return false;
			}

			if (!eachIsUnique(data)) {
				overviewLabel.setText("Duplicate input error");
				return false;
			}

			// check for duplicate variables
			if (!varFieldIsValid(maxValue)) {
				return false;
			}
			overviewLabel.setText("Minterm data entered");
			break;

		// Data entry validation
		case "Data":

			if (!isFilled(mainField) || !isFilled(variableField)) {
				return false;
			}

			// if a character other that 1, 0, or X is entered
			if (mainField.getText().replace("X", "").replace("1", "").replace("0", "").replace(" ", "").replace(",", "")
					.replace("x", "").length() != 0) {
				overviewLabel.setText("Invalid data character (X, 1, and 0 only)");
				return false;
			}

			if (!varFieldIsValid(mainFieldText.replace(" ", "").replace(",", "").length() - 1)) {
				return false;
			}

			overviewLabel.setText("Data entered");
			break;

		default:
			return false;
		}
		// if valid, return true
		return true;
	}

	/********************************************************************
	 * 
	 * DATA RETRIEVAL
	 * 
	 ********************************************************************/

	/**
	 * Get the variables from the variable entry TextField.
	 * 
	 * @return An ArrayList of characters that are the variables.
	 */
	private ArrayList<Character> getVarEntry() {

		char[] var = variableField.getText().replace(" ", "").replace(",", "").toCharArray();
		ArrayList<Character> varArr = new ArrayList<Character>();
		for (char c : var) {
			varArr.add(c);
		}
		return varArr;
	}

	/**
	 * Get the data in equation entry form.
	 * 
	 * @return A PrimitiveBinaryDataObject.
	 */
	private PrimitiveBinaryData getEqData() {
		return new PrimitiveBinaryData(new Equation(mainField.getText().replace(" ", "")));
	}

	/**
	 * Get the data in minterm entry form.
	 * 
	 * @return A PrimitiveBinaryDataObject.
	 */
	private PrimitiveBinaryData getMintermData() {

		ArrayList<Character> varArr = getVarEntry();

		// make data array from minterms and dont cares
		String[] data = mainField.getText().replace(",", " ").split(" ");
		String[] dontCares = dontCareField.getText().replace(",", " ").split(" ");
		ArrayList<Character> dataArr = new ArrayList<Character>(
				Collections.nCopies((int) Math.pow(2, varArr.size()), '0'));
		for (String s : data) {
			dataArr.set(Integer.valueOf(s), '1');
		}
		for (String s : dontCares) {
			if (!s.equals(""))
				dataArr.set(Integer.valueOf(s), 'X');
		}

		return new PrimitiveBinaryData(dataArr, varArr);
	}

	/**
	 * Get the data in data entry form.
	 * 
	 * @return A PrimitiveBinaryDataObject.
	 */
	private PrimitiveBinaryData getDataData() {

		ArrayList<Character> varArr = getVarEntry();

		// get data from data input
		char[] data = mainField.getText().replace(",", "").replace(" ", "").toCharArray();
		ArrayList<Character> dataArr = new ArrayList<Character>(
				Collections.nCopies((int) Math.pow(2, varArr.size()), '0'));
		for (int i = 0; i < data.length; i++) {
			dataArr.set(i, Character.toUpperCase(data[i]));
		}
		return new PrimitiveBinaryData(dataArr, varArr);
	}

	/**
	 * Get the PrimitiveBinaryData for the active data entry type.
	 * 
	 * @return A PrimitiveBinaryData object.
	 */
	public PrimitiveBinaryData getData() {

		switch (entrySelect.getValue()) {
		case "Equation":
			return getEqData();
		case "Minterms":
			return getMintermData();
		case "Data":
			return getDataData();
		default:
			return null;
		}
	}

}
