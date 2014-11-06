 package truthtablegenerator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * The Expression class. Holds the entered Expression, validates it, and does boolean algebra on it.
 * A Singleton Class.
 * @author McAllister, ...
 */
public class Expression {
	private static final Expression expression = new Expression();
	private static String enteredExpression = null;
	private static String workableExpression = null;
	private static int size = 0;
	private static List <String> variableList = new ArrayList<>();
	private Expression() {
	}
	
	/**
	 * setter for fullExpression
	 * @param expression the user entered string "expression"
	 */
	public static void setEnteredExpression (String expression) {
		enteredExpression = expression;
	}
	
	/**
	 * Converts the expression into a workable one with only single character symbols and no spaces or uppercase letters.
	 */
	public static void cleanup() {
		if (!enteredExpression.equals(null)) { //dont do if there is no expression
			workableExpression = enteredExpression.toLowerCase();
			workableExpression = workableExpression.replaceAll("\\s","");
			workableExpression = workableExpression.replaceAll("-->",">");
			workableExpression = workableExpression.replaceAll("\\\\/","+");
			workableExpression = workableExpression.replaceAll("/\\\\","*");
			workableExpression = workableExpression.replaceAll("!","`");
			workableExpression = "@" + workableExpression + "@";
		}
	}
	
	/**
	 * Validates the users Expression
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate() { //needs to throw error codes for display
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0, 1) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			// get size
			return true;
		}
		return false; // note false is only if there are nonmatching parentheses. all other errors are thrown.
	}
	
	/**
	 * Validates the users Expression and sets the 
	 * @param expression The string the user entered.
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate(String expression) { //needs to throw error codes for display
		setEnteredExpression(expression);
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0, 1) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			// get sizereturn true;
		}
		return false; // note false is only if there are nonmatching parentheses. all other errors are thrown.
	}
	
	/**
	 * Checks if the expression is invalid. 
	 * @param unclosedCount number of left parentheses w/o right ones. if ever negative,  expression is FALSE.
	 *		Initial pass in should be 0;
	 * @param position the position in the String. Initial call should be 1
	 * @return unclosedCount. If not 0, then the expression is invalid
	 */
	private static int invalidate(int unclosedCount, Integer position) { //throws error codes
            //grab importantant characters
            String temp = workableExpression.substring(position, position + 1);
            String RHS;
            if (temp.equals("@") && position > 0)
                return unclosedCount;
            else
                RHS = workableExpression.substring(position + 1, position + 2);
            
            
            if (Character.isLetter(temp.charAt(0)) || temp.equals(")")) { // checking for other letters
                if (temp.equals(")"))
                   unclosedCount--;
                // check RHS
                if (Character.isLetter(RHS.charAt(0)) || RHS.equals("~") || RHS.equals("("))
                    return -1;
                
            } else {
                if (temp.equals("("))
                    unclosedCount++;
                // check RHS
                if (RHS.equals(">") || RHS.equals("*") || RHS.equals("+")
                        || RHS.equals(")") || RHS.equals("@"))
                    return -1;
            }
            return unclosedCount;
	}
	
	 public static ArrayList<Character> getVariables() {
		 ArrayList <Character> vars = new ArrayList<>();
		for( int i = 1; i < workableExpression.length() - 1; i++) { // first and last elements are just placeholder "@" skipp them
			if (Character.isLetter(workableExpression.charAt(i))) {
				boolean add = true;
				for (int j = 0; j < vars.size(); j++) {
					if (workableExpression.charAt(i) == vars.get(j)) { // the string is only 1 long so 0 is the whole string
						add = false;
					}
				}
				if (add) {
					vars.add(workableExpression.charAt(i));
				}
			}
		}
		return vars;
	 }
	 
	 public static ArrayList<String> getSteps(int startPoint, Integer skip) {
		 ArrayList <String> steps = new ArrayList<>();
		 for (int i = startPoint; i < workableExpression.length() - 1; i++) {
			 if (workableExpression.charAt(i) == '(') {
				 Integer skipTo = 0;
				 steps.addAll(getSteps(i +1, skipTo));
				 i = skipTo;
			 }
			 if (workableExpression.charAt(i) == ')') {
				 skip = i + 1; //skip to the next char, dont want to cascade out of all function  calls of off one ')'
			 }
		 }
		 
		 return steps;
	 }
	 
	public static void setFullExpression() {
		// steps, answer
		ArrayList <Character> vars = getVariables();
		System.out.println(vars);
		ArrayList<String> steps = getSteps(1, 0);
		System.out.println(steps);
		
	}
	
	public static void setCompactExpression() {
	}

	public static List<String> getFullExpression() {
		return null;
	}
	
	public static String getCompactExpression() {
		return null;
	}
	
	public static String evaluateFullExpression(int row) {
		return null;
	}
	 
	public static String evaluateCompactExpression(int row) {
		return null;
	}
	
}
