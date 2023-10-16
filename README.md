# Boolean-Algebra-Calculator

This is software tha allows you to input binary data in a variety of forms, from which it will then generate a simplified equation, truth table, and karnaugh map. To run this program, download the digitalLogicCalculator.jar file and run it (you must have Java installed). The digitalLogicCalculator.bat file is provided for easy launching for those on Windows.

## How to Use

Select a data entry method from the dropdown menu (see blow), and hit enter. A two simplified equations (SOP and POS) will appear below, along with a karnaugh map and a truth table. If they do not appear, look for an error message to appear at the bottom of the data entry box. Additionally, if you attempt to parse an exceptionally complex set of data, the calculation may take some time.

### Equation

Enter a boolean algebra equation expressed in terms of variables and operations (AND *, OR +, and NOT '). All other characters will be treated as variables, spaces will be ignored, and adjascent variables will be considered ANDed.

### Minterms

Enter the index position of the minterms (starting at 0) seperated by spaces in the first box. In the second, enter the variables to use for the representation; enough must be present to represent the highest index given. Enter "don't cares" in the same way as minterms.

### Data

Enter the data directly (1s, 0s, and Xs) in increasing order of index. Then enter variables to represent the data. If more points can be represented by the variables, trailing 0s will be added.

