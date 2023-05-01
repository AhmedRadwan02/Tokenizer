import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.spi.CurrencyNameProvider;

public class Tokenizer {
	Scanner scanner;
	// current character we want to match
	String lookahead;
	// Reserved words in java language
	static ArrayList<String> RESERVED_WORDS = new ArrayList<String>(Arrays.asList("abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else",
			"enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import",
			"instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected",
			"public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
			"throws", "transient", "true", "try", "void", "volatile", "while"));

	// state number and it's token
	public static Map<Integer, String> TOKENS_NAMES;

	//
	static ArrayList<String> Current_Token = new ArrayList<String>();

	static {
		TOKENS_NAMES = new HashMap<>();
		TOKENS_NAMES.put(2, "Increment");
		TOKENS_NAMES.put(3, "Add_Assign");
		TOKENS_NAMES.put(4, "Addition");
		TOKENS_NAMES.put(6, "Decrement");
		TOKENS_NAMES.put(7, "Sub_Assign");
		TOKENS_NAMES.put(8, "Subtract");
		TOKENS_NAMES.put(10, "Mul_Assign");
		TOKENS_NAMES.put(11, "Multiplication");
		TOKENS_NAMES.put(13, "Mod_Assign");
		TOKENS_NAMES.put(14, "Modulus");
		TOKENS_NAMES.put(16, "Equality");
		TOKENS_NAMES.put(17, "Assignment");
		TOKENS_NAMES.put(19, "Greater_EQ");
		TOKENS_NAMES.put(21, "Greater_Than");
		TOKENS_NAMES.put(22, "Zero_fill_RS");
		TOKENS_NAMES.put(23, "RS_Assign");
		TOKENS_NAMES.put(24, "Right_Shift");
		TOKENS_NAMES.put(26, "Less_EQ");
		TOKENS_NAMES.put(28, "Less_Than");
		TOKENS_NAMES.put(29, "LS_Assign");
		TOKENS_NAMES.put(30, "Left_Shift");
		TOKENS_NAMES.put(32, "Bit_AND");
		TOKENS_NAMES.put(33, "Logic_AND");
		TOKENS_NAMES.put(34, "BitAND_Assign");
		TOKENS_NAMES.put(36, "Bit_OR");
		TOKENS_NAMES.put(37, "Logic_OR");
		TOKENS_NAMES.put(38, "BitOR_Assign");
		TOKENS_NAMES.put(40, "Bit_XOR");
		TOKENS_NAMES.put(41, "BitXOR_Assign");
		TOKENS_NAMES.put(43, "Logic_Not");
		TOKENS_NAMES.put(44, "Not_EQ");
		TOKENS_NAMES.put(45, "Complement");
		TOKENS_NAMES.put(48, "Division");
		TOKENS_NAMES.put(49, "Div_Assign");
		TOKENS_NAMES.put(52, "Comma");
		TOKENS_NAMES.put(53, "Semicolon");
		TOKENS_NAMES.put(54, "Period");
		TOKENS_NAMES.put(55, "Right_Curly");
		TOKENS_NAMES.put(56, "Right_Paranthese");
		TOKENS_NAMES.put(57, "Left_Paranthese");
		TOKENS_NAMES.put(58, "Right_SQ_Bracket");
		TOKENS_NAMES.put(59, "Left_SQ_Bracket");
		TOKENS_NAMES.put(62, "single_quote");
		TOKENS_NAMES.put(65, "double_quote");
		TOKENS_NAMES.put(66, "String");
		TOKENS_NAMES.put(68, "Identifier");
		TOKENS_NAMES.put(70, "Integer");
		TOKENS_NAMES.put(72, "Float");
		TOKENS_NAMES.put(74, "Dot");
	}

	int state = 0;

	// constructor
	public Tokenizer(String path) {
		try {
			scanner = new Scanner(new File(path));
			// read character by character
			scanner.useDelimiter("");

		} catch (FileNotFoundException error) {
			System.out.println("The file cannot be found.\nThe program will exit.");
			System.exit(1);
		} catch (Exception error) {

		}
	}

	// read file
	public void readTokens() {
		readChar();
		while (scanner.hasNext()) {
			switches();
		}
		while (!Current_Token.isEmpty()) {
			switches();
		}

	}

	// Print
	public void Print() {
		if (!Current_Token.isEmpty() && state != 0) {
			for (int i = 0; i < Current_Token.size(); i++) {
				System.out.print(Current_Token.get(i));
			}
			System.out.println("\t" + TOKENS_NAMES.get(state));
		}
		Current_Token.clear();

	}

	// Ungetc function
	public void UngetC() {
		if (Current_Token.size() > 1)
			lookahead = Current_Token.remove(Current_Token.size() - 1);
	}

	// switch cases

	public void switches() {

		switch (state) {
		case 0:
			if (lookahead.matches("\\+")) {
				state = 1;
			} else if (lookahead.matches("-")) {
				state = 5;
			} else if (lookahead.matches("\\*")) {
				state = 9;
			} else if (lookahead.matches("%")) {
				state = 12;
			} else if (lookahead.matches("=")) {
				state = 15;
			} else if (lookahead.matches(">")) {
				state = 18;
			} else if (lookahead.matches("<")) {
				state = 25;
			} else if (lookahead.matches("&")) {
				state = 31;
			} else if (lookahead.matches("|")) {
				state = 35;
			} else if (lookahead.matches("^")) {
				state = 39;
			} else if (lookahead.matches("!")) {
				state = 42;
			} else if (lookahead.matches("~")) {
				state = 45;
			} else if (lookahead.matches(",")) {
				state = 52;
			} else if (lookahead.matches(";")) {
				state = 53;
			} else if (lookahead.matches("\"")) {
				state = 64;
			} else if (lookahead.matches("\'")) {
				state = 60;
			} else if (lookahead.matches("\\[")) {
				state = 59;
			} else if (lookahead.matches("]")) {
				state = 58;
			} else if (lookahead.matches("\\(")) {
				state = 57;
			} else if (lookahead.matches("\\)")) {
				state = 56;
			} else if (lookahead.matches("}")) {
				state = 55;
			} else if (lookahead.matches("\\{")) {
				state = 54;
			} else if (lookahead.matches("\n")) {
				state = 0;
			} else if (lookahead.matches("/")) {
				state = 51;
			} else if (lookahead.matches(".")) {
				state = 73;
			} else if (lookahead.matches("\\d")) {
				state = 69;
			} else if (lookahead.matches("[a-zA-Z_$]*")) {
				state = 67;
			} else if (lookahead.matches("\\t|\\n|\\r")) {
				state = 0;
			}else if(lookahead.matches(" ")){
				state = 0;
			}else {
			}
            
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 1:
			if(lookahead.matches("\\+")) {
				state =2 ;
			}else if(lookahead.matches("=")) {
				state = 3;
			}else {
				state = 4;
			}
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 2:
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 3:
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 4:
			UngetC();
			Print();	
			System.out.println(lookahead);
			state = 0;
			break;
		case 5:
			if(lookahead.matches("-")) {
				state =6;
			}else if(lookahead.matches("=")) {
				state = 7;
			}else {
				state = 8;
			}
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 6:
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
	        break;
		case 7:
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
	        break;
		case 8:
			UngetC();
			Print();
			Current_Token.add(lookahead);
			state = 0;
			break;
		case 9:
			if(lookahead.matches("=")) {
				state = 10;
			}else {
				state = 11;
			}
			Current_Token.add(lookahead);
	        readChar();
			break;
		case 10:
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
		case 11:
			UngetC();
			Print();
			state = 0;
			Current_Token.add(lookahead);
	        readChar();
			break;
		}

	}

	// read character

	public void readChar() {
		try {
			lookahead = scanner.next();
		} catch (NoSuchElementException e) {
			lookahead = "\0";
		}
	}

}
