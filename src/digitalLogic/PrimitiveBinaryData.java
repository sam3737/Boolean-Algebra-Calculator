package digitalLogic;

import java.util.ArrayList;

import application.KMapElement;
import application.TruthTableElement;

/**
 * This class holds the basic data pertaining to binary operations. This is a
 * list of variables as well as the actual data (1, 0 or X).
 * 
 * @author Sam McDowell
 */
public class PrimitiveBinaryData {

	/********************************************************************
	 * 
	 * FIELDS
	 * 
	 ********************************************************************/

	private ArrayList<Character> variables;
	private ArrayList<Character> data;

	/********************************************************************
	 * 
	 * CONSTRUCTORS
	 * 
	 ********************************************************************/

	/**
	 * Creates a PrimitiveBinaryData object from specified data and variable inputs.
	 * 
	 * @param data      The data (0,1,X) of the system.
	 * @param variables The variables which make up the system.
	 */
	public PrimitiveBinaryData(ArrayList<Character> data, ArrayList<Character> variables) {
		this.data = data;
		this.variables = variables;
	}

	/**
	 * Creates a PrimitiveBinaryData object from an Equation object.
	 * 
	 * @param equation An equation object.
	 */
	public PrimitiveBinaryData(Equation equation) {
		this.variables = equation.getVars();
		this.data = equation.evaluateAll();
	}

	/********************************************************************
	 * 
	 * GETTERS
	 * 
	 ********************************************************************/

	public ArrayList<Character> getData() {
		return data;
	}

	public ArrayList<Character> getVariables() {
		return variables;
	}

	/********************************************************************
	 * 
	 * NODE CREATION
	 * 
	 ********************************************************************/

	/**
	 * Creates a TruthTableElement
	 * 
	 * @return a TruthTableElement
	 */
	public TruthTableElement makeTruthTableElement() {
		return new TruthTableElement(this);
	}

	/**
	 * Creates a KMapElement
	 * 
	 * @return a KMapElement
	 */
	public KMapElement makeKMapElement() {
		return new KMapElement(this);
	}

	/**
	 * Creates a list of all valid simplified equations.
	 * 
	 * @return All simplified equations as an ArrayList of strings.
	 */
	public ArrayList<String> makeEquation() {

		// holds output
		ArrayList<String> out = new ArrayList<>();

		// holds solutions in char array format
		ArrayList<ArrayList<ArrayList<Character>>> solutions;

		// for converting char array solution to string
		String strSolution;

		// to convert data to BinarySet format
		ArrayList<ArrayList<Character>> binarySetData = new ArrayList<>();
		ArrayList<ArrayList<Character>> dontCareData = new ArrayList<>();

		// list of binary numbers (in char array form)
		ArrayList<ArrayList<Character>> binaryList = binaryList(variables.size());

		// convert data to BinarySet format
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == '1') {
				binarySetData.add(binaryList.get(i));
			} else if (data.get(i) == 'X') {
				dontCareData.add(binaryList.get(i));
			}
		}

		// Get solutions from BinarySet object
		solutions = new BinarySet(binarySetData, dontCareData).getSolutions();

		// Convert solutions from BinarySet format to strings
		strSolution = "";
		for (ArrayList<ArrayList<Character>> solution : solutions) {
			for (ArrayList<Character> expression : solution) {
				for (int var = 0; var < expression.size(); var++) {
					if (expression.get(var) == '1') {
						strSolution += variables.get(var);
					} else if (expression.get(var) == '0') {
						strSolution += variables.get(var) + "'";
					}
				}
				strSolution += "+";
			}
			// Add string solution to output
			out.add(strSolution.substring(0, strSolution.length() - 1));
			strSolution = "";
		}
		return out;
	}

	/********************************************************************
	 * 
	 * STATIC METHOD
	 * 
	 ********************************************************************/

	/**
	 * Creates a list of all numbers in binary which can be created with a specified
	 * number of digits.
	 * 
	 * @param size The number of digits to use to create the list.
	 * @return All binary numbers which can be created with n digits as a
	 *         2-dimensional ArrayList of characters.
	 */
	public static ArrayList<ArrayList<Character>> binaryList(int size) {

		ArrayList<Character> sublist = new ArrayList<>();
		ArrayList<ArrayList<Character>> out = new ArrayList<>();

		// from 0 to 2^n where n is the number of variables
		for (int n = 0; n < Math.pow(2, size); n++) {

			// convert n to a binary string with a length equal to the number of variables
			String num = String.format("%0" + size + "d", Integer.parseInt(Integer.toBinaryString(n)));

			// split the string and add it to inputs
			String[] spltStr = num.split("");
			for (String str : spltStr) {
				sublist.add(str.charAt(0));
			}
			out.add(new ArrayList<>(sublist));
			sublist.clear();
		}
		return out;
	}

	/********************************************************************
	 * 
	 * DEBUGGING UTILITY
	 * 
	 ********************************************************************/

	/**
	 * Displays all data to the console.
	 */
	public void disp() {
		System.out.println("VARAIBLES:");
		for (char v : variables) {
			System.out.print(v + " ");
		}
		System.out.println("\nDATA:");
		for (char d : data) {
			System.out.print(d + " ");
		}
		System.out.print("\n");
	}
}
