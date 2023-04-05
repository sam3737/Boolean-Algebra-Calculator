package digitalLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Equation {

	// variable equation where each element is a variable or an operation
	private ArrayList<Character> eq;

	private List<Character> RESTRICTED_CHARACTERS = new ArrayList<>(Arrays.asList('+', '*', '^', '\'', '(', ')'));

	// construct from string
	public Equation(String str) {
		eq = new ArrayList<Character>();
		for (char ch : str.toCharArray()) {
			eq.add(ch);
		}
	}

	public ArrayList<Character> evaluateAll() {

		// get inputs
		ArrayList<ArrayList<Character>> truthInputList = PrimitiveBinaryData.binaryList(this.getVars().size()); // getTruthTableInput();
		ArrayList<Character> out = new ArrayList<>();

		// evaluate for every possible input
		for (ArrayList<Character> truthInput : truthInputList) {
			out.add(evaluate(truthInput));
		}
		return out;
	}

	private String evaluatePhase1(String numEq) {

		String holdNumEq;
		do {
			holdNumEq = new String(numEq);

			numEq = numEq.replace("(0)", "0");
			numEq = numEq.replace("(1)", "1");

			numEq = numEq.replace("0'", "1");
			numEq = numEq.replace("1'", "0");

			numEq = numEq.replace("00", "0");
			numEq = numEq.replace("01", "0");
			numEq = numEq.replace("10", "0");
			numEq = numEq.replace("11", "1");

			numEq = numEq.replace("0*0", "0");
			numEq = numEq.replace("0*1", "0");
			numEq = numEq.replace("1*0", "0");
			numEq = numEq.replace("1*1", "1");

		} while (!numEq.equals(holdNumEq));
		return numEq;
	}

	private String evaluatePhase2(String numEq) {

		String holdNumEq;
		do {
			holdNumEq = new String(numEq);

			numEq = numEq.replace("0+0", "0");
			numEq = numEq.replace("0+1", "1");
			numEq = numEq.replace("1+0", "1");
			numEq = numEq.replace("1+1", "1");

		} while (!numEq.equals(holdNumEq));
		return numEq;
	}

	public char evaluate(ArrayList<Character> truth) {

		ArrayList<Character> vars = getVars(); // list of variables

		ArrayList<Character> numEq = new ArrayList<>(eq); // duplicate eq

		// replace vars with elements of truth
		for (int var = 0; var < vars.size(); var++) {

			char changeVar = vars.get(var);
			char replaceChar = truth.get(var);

			Collections.replaceAll(numEq, changeVar, replaceChar);
		}

		String strEq = "";
		for (char c : numEq) {
			strEq += c;
		}
		while (strEq.length() > 1) {

			String holdStr = new String(strEq);

			// System.out.println(strEq);
			strEq = evaluatePhase1(strEq);
			// System.out.println(strEq);
			strEq = evaluatePhase2(strEq);
			// System.out.println(strEq);

			if (strEq.equals(holdStr)) {
				return '-';
			}
		}
		return strEq.charAt(0);

	}

	// evaluate eq for an input set
	public char eval(List<Character> truth) {

		int count = 0;
		String pair, triple;

		List<Character> vars = getVars(); // list of variables

		List<Character> numEq = new ArrayList<>(eq); // duplicate eq

		// replace vars with elements of truth
		for (int var = 0; var < vars.size(); var++) {

			char changeVar = vars.get(var);
			char replaceChar = truth.get(var);

			Collections.replaceAll(numEq, changeVar, replaceChar);

		}

		boolean trySecond = false;

		// while not minimized
		while (numEq.size() > 1) {

			// get 2 and 3 chars at index = count
			if (numEq.size() - count >= 2)
				pair = "" + numEq.get(count) + numEq.get(count + 1);
			else
				pair = "";

			if (numEq.size() - count >= 3)
				triple = "" + numEq.get(count) + numEq.get(count + 1) + numEq.get(count + 2);
			else
				triple = "";

			if (!trySecond) {
				switch (pair) {

				case "11":
					replacePair(numEq, count, '1');
					count = 0;
					break;
				case "10":
				case "01":
				case "00":
					replacePair(numEq, count, '0');
					count = 0;
					break;
				case "1'":
					replacePair(numEq, count, '0');
					count = 0;
					break;
				case "0'":
					replacePair(numEq, count, '1');
					count = 0;
					break;
				default:
					switch (triple) {

					case "0*0":
					case "1*0":
					case "0*1":
						replaceTriple(numEq, count, '0');
						count = 0;
						break;
					case "(0)":
						replaceTriple(numEq, count, '0');
						count = 0;
						break;
					case "(1)":
						replaceTriple(numEq, count, '1');
						count = 0;
						break;

					default:
						if (count < numEq.size()) {
							count++;
							break;
						} else {
							trySecond = true;
							count = 0;
						}
					}
				}
			} else {
				switch (triple) {
				case "0+0":
					replaceTriple(numEq, count, '0');
					trySecond = false;
					count = 0;
					break;
				case "1+1":
				case "1+0":
				case "0+1":
					replaceTriple(numEq, count, '1');
					trySecond = false;
					count = 0;
					break;
				default:
					if (count < numEq.size()) {
						count++;
						break;
					} else {
						return 'X';
					}
				}
			}
		}
		return numEq.get(0);
	}

	// replace 2 elements at index with 1 element
	public void replacePair(List<Character> lst, int index, char ch) {
		lst.set(index, ch);
		lst.remove(index + 1);
	}

	// replace 3 elements at index with 1 element
	public void replaceTriple(List<Character> lst, int index, char ch) {
		lst.set(index, ch);
		lst.remove(index + 1);
		lst.remove(index + 1);
	}

	public ArrayList<Character> getVars() {

		ArrayList<Character> unique = new ArrayList<>();

		for (char ch : eq) {
			if (!RESTRICTED_CHARACTERS.contains(ch) && !unique.contains(ch)) {
				unique.add(ch);
			}
		}
		Collections.sort(unique);
		return unique;
	}

}
