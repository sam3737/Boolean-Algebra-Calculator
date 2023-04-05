package digitalLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BinarySet {

	private ArrayList<ArrayList<Character>> data;
	private ArrayList<ArrayList<Character>> dontCareData;
	private ArrayList<ArrayList<Character>> primeImplicants;
	private ArrayList<ArrayList<Character>> essentialPrimeImplicants;
	private ArrayList<ArrayList<ArrayList<Character>>> solutions;

	public ArrayList<ArrayList<Character>> getData() {
		return data;
	}

	public ArrayList<ArrayList<Character>> getPrimeImplicants() {
		return primeImplicants;
	}

	public ArrayList<ArrayList<ArrayList<Character>>> getSolutions() {
		return solutions;
	}

	public BinarySet(ArrayList<ArrayList<Character>> data) {

		this.data = data;
		dontCareData = data;
		primeImplicants = new ArrayList<>();
		essentialPrimeImplicants = new ArrayList<>();
		solutions = new ArrayList<>();

		makePrimeImplicants();
		makeEssentialPrimeImplicants();
		solve();
	}

	public BinarySet(ArrayList<ArrayList<Character>> data, ArrayList<ArrayList<Character>> dontCareData) {

		this.data = data;
		this.dontCareData = dontCareData;
		this.dontCareData.addAll(data);
		primeImplicants = new ArrayList<>();
		essentialPrimeImplicants = new ArrayList<>();
		solutions = new ArrayList<>();

		makePrimeImplicants();
		makeEssentialPrimeImplicants();
		solve();
	}

	// generate prime implicants from data
	private void makePrimeImplicants() {

		ArrayList<ArrayList<Character>> activeData = new ArrayList<>();
		ArrayList<Integer> adjascentIndices = new ArrayList<>();
		ArrayList<Character> toAdd = new ArrayList<>();
		ArrayList<Boolean> isCovered = new ArrayList<>();

		// continue
		boolean cont = true;
		while (cont) {

			cont = false;

			// if no prime implicants have been created, set data to activeData
			if (primeImplicants.size() == 0) {
				activeData = dontCareData; // <------------- data
			}
			// otherwise, set activeData to a copy of primeImplicants
			else {
				activeData = new ArrayList<>(primeImplicants);
				primeImplicants.clear();
			}

			// records if each element of activeData has already been covered
			isCovered = new ArrayList<Boolean>(Collections.nCopies(activeData.size(), false));

			// for every element (i) and every element following that element (j)
			for (int i = 0; i < activeData.size(); i++) {
				for (int j = i + 1; j < activeData.size(); j++) {

					// if i and j are adjacent
					adjascentIndices = adjascentIndices(activeData.get(i), activeData.get(j));
					if (adjascentIndices.size() == 1) {

						cont = true; // do at least one more loop (TODO remove on of the cont)

						// set i and j to covered
						isCovered.set(i, true);
						isCovered.set(j, true);

						// add the common element to primeImplicants
						toAdd = new ArrayList<>(activeData.get(i));
						toAdd.set(adjascentIndices.get(0), 'X');
						if (!primeImplicants.contains(toAdd)) {
							primeImplicants.add(toAdd);
							cont = true;
						}
					}
				}
			}
			// add every element that was not covered, add it to primeImplicants
			for (int b = 0; b < isCovered.size(); b++) {
				if (!isCovered.get(b)) {
					primeImplicants.add(activeData.get(b));
				}
			}
		}
	}

	// generate essential prime implicants from primeImplicants and data
	private void makeEssentialPrimeImplicants() {

		// counts how many times each element in data is covered by elements in
		// primeImplicants
		ArrayList<Integer> coveredBy = new ArrayList<>(Collections.nCopies(data.size(), 0));

		// covers is a copy of data
		ArrayList<ArrayList<Character>> covers = new ArrayList<>(data);

		// count how many times each element in data is covered
		for (int d = 0; d < data.size(); d++) {
			for (ArrayList<Character> p : primeImplicants) {
				if (canCover(p, data.get(d))) {
					int current = coveredBy.get(d) + 1;
					coveredBy.set(d, current);
					covers.set(d, p); // set covers to the prime implicants that covered that element of data
				}
			}
		}
		// if a data is covered by only one prime implicants, add it to essential
		for (int i = 0; i < coveredBy.size(); i++) {
			if (coveredBy.get(i) <= 1 && !essentialPrimeImplicants.contains(covers.get(i))) {
				essentialPrimeImplicants.add(covers.get(i));
			}
		}
	}

	// get solutions from data, primeImplicanst, and essentialPrimeImplicants
	private void solve() {

		// remainingData is data that is not covered by essentialPrimeImplicants
		ArrayList<ArrayList<Character>> remainingData = new ArrayList<>(data);
		for (ArrayList<Character> d : data) {
			for (ArrayList<Character> ep : essentialPrimeImplicants) {
				if (canCover(ep, d)) {
					remainingData.remove(d);
				}
			}
		}

		// if there is none remaining, essentialPrimeImplicants is the solution
		if (remainingData.size() == 0) {
			solutions = new ArrayList<>();
			solutions.add(essentialPrimeImplicants);
		} else {
			// prime not essential is primeImplicants with essentialPrimeImplicants removed
			ArrayList<ArrayList<Character>> primeNotEssential = new ArrayList<>(primeImplicants);
			for (ArrayList<Character> epi : essentialPrimeImplicants) {
				primeNotEssential.remove(epi);
			}

			// every combo of primeNonEssential of every size
			ArrayList<ArrayList<ArrayList<Character>>> allCombos = new ArrayList<>();

			// list of binary arrays from 1 to the number of primeNotEssential elements
			ArrayList<ArrayList<Integer>> binList = binaryList(primeNotEssential.size());
			binList.remove(0);

			ArrayList<ArrayList<Character>> subList = new ArrayList<>(); // used to add to allCombos

			// create allCombos
			for (ArrayList<Integer> binCombo : binList) {
				for (int i = 0; i < binCombo.size(); i++) {
					if (binCombo.get(i) == 1) {
						subList.add(primeNotEssential.get(i));
					}
				}
				allCombos.add(new ArrayList<>(subList));
				subList.clear();
			}

			// sort allCombos in ascending order of size
			Collections.sort(allCombos, new Comparator<ArrayList<?>>() {
				public int compare(ArrayList<?> a1, ArrayList<?> a2) {
					return a1.size() - a2.size();
				}
			});

			// check every combo
			int maxSize = -1; // the smallest size with a solution
			for (ArrayList<ArrayList<Character>> solution : allCombos) {

				// if a solution has been found and the potential solution is larger, break
				if (maxSize != -1 && solution.size() > maxSize) {
					break;
				}
				// see if combo covers every element of remainingData
				ArrayList<ArrayList<Character>> toCheck = new ArrayList<>(remainingData);
				for (ArrayList<Character> combo : solution) {
					for (int chk = 0; chk < toCheck.size(); chk++) {
						if (canCover(combo, toCheck.get(chk))) {
							toCheck.remove(chk);
							chk--;
						}
					}
				}
				// if every data covered, add solution and set maxSize
				if (toCheck.size() == 0) {
					maxSize = solution.size();
					solution.addAll(essentialPrimeImplicants); // add essential to solution
					solutions.add(solution);
				}
			}
		}
		// a'b'c'+a'cd+abd+bc'd'
		// bcd+abd+a'b'd+bc'd' <-X
		// dispData();
	}

	// checks if one grouping covers another one
	private boolean canCover(ArrayList<Character> a1, ArrayList<Character> a2) {

		boolean canCover = true;
		for (int i = 0; i < a1.size(); i++) {
			if (!(a1.get(i) == a2.get(i) || a1.get(i) == 'X')) {
				canCover = false;
			}
		}
		return canCover;
	}

	// returns which indices are different between ArrayLists
	private ArrayList<Integer> adjascentIndices(ArrayList<Character> c1, ArrayList<Character> c2) {

		ArrayList<Integer> out = new ArrayList<>();
		for (int i = 0; i < c1.size(); i++) {
			if (c1.get(i) != c2.get(i)) {
				out.add(i);
			}
		}
		return out;
	}

	// Generate a list of binary codes up to a max size
	private ArrayList<ArrayList<Integer>> binaryList(int size) {

		ArrayList<Integer> sublist = new ArrayList<>();
		ArrayList<ArrayList<Integer>> out = new ArrayList<>();

		// from 0 to 2^n where n is the number of variables
		for (int n = 0; n < Math.pow(2, size); n++) {

			// convert n to a binary string with a length equal to the number of variables
			String num = String.format("%0" + size + "d", Integer.parseInt(Integer.toBinaryString(n)));

			// split the string and add it to inputs
			String[] spltStr = num.split("");
			for (String str : spltStr) {
				sublist.add(Character.getNumericValue(str.charAt(0)));
			}
			out.add(new ArrayList<>(sublist));
			sublist.clear();
		}
		return out;
	}

	@SuppressWarnings("unused")
	private void dispData() {

		System.out.println("DATA: ");
		for (ArrayList<Character> j : data) {
			for (char k : j) {
				System.out.print(k + " ");
			}
			System.out.print("\n");
		}
		System.out.println("PRIME: ");
		for (ArrayList<Character> j : primeImplicants) {
			for (char k : j) {
				System.out.print(k + " ");
			}
			System.out.print("\n");
		}
		System.out.println("ESSENTIAL: ");
		for (ArrayList<Character> j : essentialPrimeImplicants) {
			for (char k : j) {
				System.out.print(k + " ");
			}
			System.out.print("\n");
		}
		System.out.println("SOLUTIONS: ");
		for (ArrayList<ArrayList<Character>> s : solutions) {
			for (ArrayList<Character> j : s) {
				for (char k : j) {
					System.out.print(k + " ");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}
}
