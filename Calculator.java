package menuPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCalc;
	private StringBuilder expression = new StringBuilder(20);
	private List<String> numbers = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private float resultFinal;
	
	//Initializing the signs pattern
	Pattern signs = Pattern.compile("\\+|-|\\*-|/-|\\*|/");
	
	//Method which governs the plus/minus button
	private void plusMinus() {
		
		try {
			
			StringBuilder found = new StringBuilder();
			int foundLength;
			
			Pattern plusMinusPattern = Pattern.compile("\\+\\d+\\.?\\d+|\\+\\d+|" + "-\\d+\\.?\\d+|-\\d+"
												+ "|\\*\\d+\\.?\\d+|\\*\\d+|" + "/\\d+\\.?\\d+|/\\d+");
			
			final Matcher plusMinusMatcher = plusMinusPattern.matcher(expression);
			
			
				
			while(plusMinusMatcher.find()) {
					
				found.replace(0, found.length(), plusMinusMatcher.group());
				
			}
			
			
			foundLength = found.length();
			
			if(found.length() != 0 && 
			   expression.indexOf(found.toString()) > 0) {
			
				if(found.charAt(0) == '+') {
						
					found.replace(0, 1, "-");
					expression.replace(expression.length()-foundLength, expression.length(), found.toString());
					textFieldCalc.setText(expression.toString());
				}
					
				else if(found.charAt(0) == '-' 
						&& expression.charAt(expression.length()-foundLength - 1) != '*' 
						&& expression.charAt(expression.length() - foundLength - 1) != '/') {
							
					found.replace(0, 1, "+");
					expression.replace(expression.length()-foundLength, expression.length(), found.toString());
					textFieldCalc.setText(expression.toString());
				}
				
				else if(found.charAt(0) == '*' 
					 || found.charAt(0) == '/') {
						
					found.insert(1, "-");
					expression.replace(expression.length()-foundLength, expression.length(), found.toString());
					textFieldCalc.setText(expression.toString());
				}
				
				else {
					
					found.deleteCharAt(0);
					expression.replace(expression.length()-foundLength, expression.length(), found.toString());
					textFieldCalc.setText(expression.toString());
				}
				
			}
			
			else if(found.length() != 0 && expression.indexOf(found.toString()) == 0) {
				
				expression.deleteCharAt(0);
				textFieldCalc.setText(expression.toString());
			}
			
			else if(found.length() == 0 && expression.length() == 0) {
				
				expression.insert(0, '-');
				textFieldCalc.setText(expression.toString());
			}
			
			else if(found.length() == 0 && expression.length() > 0 && expression.charAt(0) != '-') {
				
				expression.insert(0, '-');
				textFieldCalc.setText(expression.toString());
			}
			
			else {
				
				expression.deleteCharAt(0);
				textFieldCalc.setText(expression.toString());
			}
			
			
		}
		
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "The following error has occured within the plus/minus method: " +e, "Error message", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
			
		
		
	}
	
	//Used for storing the signs of the equation
	private void operatorsFill() {
		
		final Matcher signsMatcher = signs.matcher(expression);

		while(signsMatcher.find()) {
			
			operators.add(signsMatcher.group());
			
		}
		
	}
	
	
	//Main method used when the equals button is pressed
	private void mainLogic() {
		
		operatorsFill();
		
		int arrayIndex;
		int numbersIndex = 2;
		float firstNum;
		float secondNum;
		
		try {
		
			if(numbers.size() >= 2 && operators.size() >= 1) {
				
				if(numbers.get(0).isEmpty()) {
				
					firstNum = 0;
					secondNum = Float.parseFloat(numbers.get(1));
				}
				else {
					
					firstNum = Float.parseFloat(numbers.get(0));
					secondNum = Float.parseFloat(numbers.get(1));
				}
				
		
				switch(operators.get(0)) {
				
					case "+" :
						resultFinal = firstNum + secondNum;
						break;
						
					case "-" :
						resultFinal = firstNum - secondNum;
						break;
						
					case "*" :
						resultFinal = firstNum*secondNum;
						break;
						
					case "/" :
						resultFinal = firstNum/secondNum;
						break;
						
					case "*-" :
						resultFinal = -(firstNum*secondNum);
						break;
						
					case "/-" :
						resultFinal = -(firstNum/secondNum);
						break;
						
					default:
						JOptionPane.showMessageDialog(null, "Error with sign recognition. Please try again.", "Sign error", JOptionPane.ERROR_MESSAGE);
						break;
					
				 }
				
			}
			
				else if(numbers.get(0) != null && numbers.get(0).length() > 0) {
					
					resultFinal = Float.parseFloat(numbers.get(0));
					
				}
				
				else {
					
					resultFinal = 0;
					
				}
			
			
			
			for(arrayIndex = 1; arrayIndex < operators.size(); arrayIndex++) {
				
				if(numbers.size() > 2 && operators.size() > 1) {
					
				
					switch(operators.get(arrayIndex)) {
						
						case "+" :
							resultFinal = resultFinal + Float.parseFloat(numbers.get(numbersIndex));
							numbersIndex++;
							break;
							
						case "-" :
							resultFinal = resultFinal - Float.parseFloat(numbers.get(numbersIndex));
							numbersIndex++;
							break;
							
						case "*" :
							resultFinal = resultFinal * Float.parseFloat(numbers.get(numbersIndex));
							numbersIndex++;
							break;
							
						case "/" :
							resultFinal = resultFinal / Float.parseFloat(numbers.get(numbersIndex));
							numbersIndex++;
							break;
							
						case "*-" :
							resultFinal = -(resultFinal * Float.parseFloat(numbers.get(numbersIndex)));
							numbersIndex++;
							break;
							
						case "/-" :
							resultFinal = -(resultFinal / Float.parseFloat(numbers.get(numbersIndex)));
							numbersIndex++;
							break;
							
						default:
							break;
						
					
						}
					}
			
			
			}
		}
		
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the main logic: " +e, "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//Method that removes the character that was entered last
	private void removeChar() {
		
		
		if(operators.size() >= 1) {
			
			operators.remove(operators.size()-1);
		}
		
		if(expression.length() >= 1) {
			
			expression.deleteCharAt(expression.length()-1);
		}
		
	}
		
	//For setting the result as the only character in the StringBuilder
	private void setResult() {
		
		expression.delete(0 ,19);
		operators.clear();
		expression.append(resultFinal);
	}
	
	//The method used for the percentage button
	private void percentageMethod() {
		
		StringBuilder lastNum = new StringBuilder();
		Pattern percentPatt = Pattern.compile("\\d+\\.?\\d+|\\d+");
		final Matcher matcherPercent = percentPatt.matcher(expression);
		
		try {
			while(matcherPercent.find()) {
				
				lastNum.replace(0, lastNum.length(), matcherPercent.group());
			}
			
			Float lastNumFloat = Float.parseFloat(lastNum.toString());
			
			lastNumFloat = lastNumFloat / 100;
			expression.replace(expression.length()-lastNum.length(), expression.length(), lastNumFloat.toString());
			textFieldCalc.setText(String.valueOf(expression));
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the percent method: " +e, "Percentage error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	
	//Method which splits the expression based on the signs pattern
	private void patternCheck() {
		
		String result = expression.toString();
		
		numbers = Arrays.asList(signs.split(result)); 
	}
	
	
	//Method for the minus button
	private void minusFunc() {
		
		try {
			
			if(expression.length() != 0) {
		
				if (expression.charAt(expression.length()-1) == '+' || expression.charAt(expression.length()-1) == '*' || expression.charAt(expression.length()-1) == '/') {
					
					expression.replace(expression.length()-1, expression.length(), "-");
				}
				
				else if(expression.charAt(expression.length()-1) == '-') {
					
				}
				else {
					
					expression.append("-");
				}
				
			  textFieldCalc.setText(expression.toString());
			}
			else {
				
				expression.replace(0, 1, "-");
				textFieldCalc.setText(expression.toString());
			}
		}
		catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the minus method: " +e, "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//Method for the plus button
	private void plusFunc() {
		
		try {
			
			if(expression.length() != 0) {
		
				if (expression.charAt(expression.length()-1) == '-' || expression.charAt(expression.length()-1) == '*' || expression.charAt(expression.length()-1) == '/') {
						
						expression.replace(expression.length()-1, expression.length(), "+");
					}
					
					else if(expression.charAt(expression.length()-1) == '+') {
						
					}
					else {
						
						expression.append("+");
					}
				
				textFieldCalc.setText(expression.toString());
			}
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the plus method: " +e, "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//Method for the multiply button
	private void multiFunc() {
		
		try {
			
			if(expression.length() != 0) {
		
				if (expression.charAt(expression.length()-1) == '+' || expression.charAt(expression.length()-1) == '-' || expression.charAt(expression.length()-1) == '/') {
					
					expression.replace(expression.length()-1, expression.length(), "*");
				}
				
				else if(expression.charAt(expression.length()-1) == '*') {
					
				}
				else {
					
					expression.append("*");
				}
				
			  textFieldCalc.setText(expression.toString());
			}
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the multiply method: " +e, "Error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Method for the divide button 
	private void divideFunc() {
		
		try {
			
			if(expression.length() != 0) {
		
				if (expression.charAt(expression.length()-1) == '+' || expression.charAt(expression.length()-1) == '-' || expression.charAt(expression.length()-1) == '*') {
					
					expression.replace(expression.length()-1, expression.length(), "/");
				}
				
				else if(expression.charAt(expression.length()-1) == '/') {
					
				}
				else {
					
					expression.append("/");
				}
				
				textFieldCalc.setText(expression.toString());
			}
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the divide method: " +e, "Divide error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//The method for the dot button logic
	private void dotLogic() {
		
		StringBuilder dotFound = new StringBuilder();
		Pattern dotPattern = Pattern.compile("\\d+.?\\d+|\\d+");
		Matcher dotMatch = dotPattern.matcher(expression);
		
		try {
			while(dotMatch.find()) {
				
				dotFound.replace(0, dotFound.length(), dotMatch.group());
			}
			
			if(expression.length() != 0) {
				
				if(checkLength() == true 
				   && dotFound.toString().contains(".") == false 
				   && expression.charAt(expression.length()-1) != '.') {
					
					expression.append(".");
					textFieldCalc.setText(expression.toString());
				}
			}
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured within the dot method: " +e, "Dot error message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//Checks for the max length of the expression
	private boolean checkLength() {
		
		int expressionLen = expression.length();
		
		if(expressionLen > 20) {
			
			JOptionPane.showMessageDialog(null, "The maximum length of the expression is 20 characters!", "Maximum length reached", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else {
			
			return true;
		}
	}
	
	/**
	 * Launching the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Calculator frame = new Calculator();
					frame.setVisible(true);
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "Critical error when starting the program.\nThe error has the following description: " +e, "Calculator crash report", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Creating the frame with all the different panels.
	 */
	public Calculator() {
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Calculator");
		setBounds(100, 100, 391, 368);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelResultField = new JPanel();
		panelResultField.setBounds(10, 11, 358, 34);
		contentPane.add(panelResultField);
		panelResultField.setLayout(new GridLayout(1, 0, 0, 0));
		
		textFieldCalc = new JTextField();
		textFieldCalc.setBackground(Color.WHITE);
		textFieldCalc.setEditable(false);
		textFieldCalc.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCalc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelResultField.add(textFieldCalc);
		textFieldCalc.setColumns(10);
		
		//Creating the panel where all the number buttons are.
		JPanel panelNumbers = new JPanel();
		panelNumbers.setBounds(10, 56, 198, 271);
		contentPane.add(panelNumbers);
		panelNumbers.setLayout(new GridLayout(4, 3));
		
		JButton btnSeven = new JButton("7");
		btnSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("7");
					textFieldCalc.setText(expression.toString());
				}
				
			}
		});
		btnSeven.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnSeven);
		
		JButton btnEight = new JButton("8");
		btnEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("8");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnEight.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnEight);
		
		JButton btnNine = new JButton("9");
		btnNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("9");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnNine.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnNine);
		
		JButton btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("4");
					textFieldCalc.setText(expression.toString());
				}
				
			}
		});
		
		btnFour.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnFour);
		
		JButton btnFive = new JButton("5");
		btnFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("5");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnFive.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnFive);
		
		JButton btnSix = new JButton("6");
		btnSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("6");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnSix.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnSix);
		
		JButton btnOne = new JButton("1");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("1");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnOne.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnOne);
		
		JButton btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("2");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnTwo.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnTwo);
		
		JButton btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("3");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnThree.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnThree);
		
		JButton btnDot = new JButton(".");
		btnDot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dotLogic();
			}
		});
		btnDot.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnDot);
		
		JButton btnZero = new JButton("0");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					expression.append("0");
					textFieldCalc.setText(expression.toString());
				}
			}
		});
		btnZero.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNumbers.add(btnZero);
		
		//Creating the panel where all the operation buttons are.
		
		JButton btnPlusMinus = new JButton("");
		btnPlusMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					plusMinus();
				}
			}
		});
		btnPlusMinus.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-plus-minus-48.png")));
		panelNumbers.add(btnPlusMinus);
		btnPlusMinus.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		
		JPanel panelOperations = new JPanel();
		panelOperations.setBounds(218, 56, 150, 271);
		contentPane.add(panelOperations);
		panelOperations.setLayout(new GridLayout(4, 2));
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				removeChar();
				textFieldCalc.setText(expression.toString());
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnBack.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-arrow-pointing-left-48.png")));
		panelOperations.add(btnBack);
		
		JButton btnClear = new JButton("C");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				expression.delete(0 ,19);
				operators.clear();
				textFieldCalc.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 40));
		panelOperations.add(btnClear);
		
		JButton btnMultiply = new JButton("");
		btnMultiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					multiFunc();
				}
			}
		});
		btnMultiply.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-multiply-48.png")));
		btnMultiply.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelOperations.add(btnMultiply);
		
		JButton btnDivide = new JButton("");
		btnDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					divideFunc();
				}
			}
		});
		btnDivide.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnDivide.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-divide-48.png")));
		panelOperations.add(btnDivide);
		
		JButton btnPlus = new JButton("");
		btnPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					plusFunc();
				}
			}
		});
		btnPlus.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnPlus.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-plus-math-48.png")));
		panelOperations.add(btnPlus);
		
		JButton btnMinus = new JButton("");
		btnMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(checkLength() == true) {
					
					minusFunc();
				}
			}
		});
		btnMinus.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-minus-48.png")));
		btnMinus.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelOperations.add(btnMinus);
		
		JButton btnEquals = new JButton("");
		btnEquals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				patternCheck();
				mainLogic();
				
				textFieldCalc.setText(String.valueOf(resultFinal));
				setResult();
			}
		});
		btnEquals.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnEquals.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-equal-sign-48.png")));
		panelOperations.add(btnEquals);
		
		JButton btnPercentage = new JButton("");
		btnPercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				percentageMethod();
			}
		});
		btnPercentage.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnPercentage.setIcon(new ImageIcon(Calculator.class.getResource("/Resources/Icons/icons8-percentage-48.png")));
		panelOperations.add(btnPercentage);
	}
}
