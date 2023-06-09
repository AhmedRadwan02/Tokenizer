import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.spi.CurrencyNameProvider;

public class Tokenizer {
	Scanner scanner;
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
		TOKENS_NAMES.put(54, "Left_Curly");
		TOKENS_NAMES.put(55, "Right_Curly");
		TOKENS_NAMES.put(56, "Right_Paranthese");
		TOKENS_NAMES.put(57, "Left_Paranthese");
		TOKENS_NAMES.put(58, "Right_SQ_Bracket");
		TOKENS_NAMES.put(59, "Left_SQ_Bracket");
		TOKENS_NAMES.put(62, "single_quote");
		TOKENS_NAMES.put(63, "Character");
		TOKENS_NAMES.put(65, "double_quote");
		TOKENS_NAMES.put(66, "String");
		TOKENS_NAMES.put(68, "Identifier");
		TOKENS_NAMES.put(70, "Integer");
		TOKENS_NAMES.put(72, "Float");
		TOKENS_NAMES.put(74, "Dot");
		TOKENS_NAMES.put(75, "Colon");
		TOKENS_NAMES.put(76, "At_sign");
		TOKENS_NAMES.put(77, "Question_mark");

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
		printHeader();
		readChar();
		while (scanner.hasNext()) {
			switches();
		}
		while (!Current_Token.isEmpty() || !lookahead.matches("\0")) {
			switches();
		}
	}

	// header
	public void printHeader() {
		System.out.println("--------------------------------------------------------------");
		System.out.printf("\t%-32s%s%n", "Lexemes", "Tokens");
		System.out.println("--------------------------------------------------------------\n");

	}

	// Print
	public void Print() {
		StringBuilder tokens = new StringBuilder();
		if (!Current_Token.isEmpty() && state != 0) {
			for (int i = 0; i < Current_Token.size(); i++) {
				if (!Current_Token.get(i).matches("\r"))
					tokens.append(Current_Token.get(i));
			}
			System.out.printf("\t%-30s", tokens);

			System.out.printf("\t%s%n", TOKENS_NAMES.get(state));

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
			} else if (lookahead.matches("\\&")) {
				state = 31;
			} else if (lookahead.matches("\\|")) {
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
				state = 46;
			} else if (lookahead.matches("\\d")) {
				state = 69;
			} else if (lookahead.matches("\\.")) {
				state = 73;
			} else if (lookahead.matches("[a-zA-Z_$]*")) {
				state = 67;
			} else if (lookahead.matches(":")) {
				state = 75;
			} else if (lookahead.matches("@")) {
				state = 76;
			} else if (lookahead.matches("\\?")) {
				state = 77;
			} else {
			}
			if (lookahead.matches("\\s")) {
				state = 0;
			}

			if (state != 0)
				Current_Token.add(lookahead);
			readChar();
			break;
		case 1:
			if (lookahead.matches("\\+")) {
				state = 2;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("=")) {
				state = 3;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 4;
			}
			break;
		case 2:
			Print();
			state = 0;
			break;
		case 3:
			Print();
			state = 0;
			break;
		case 4:
			UngetC();
			Print();
			state = 0;
			break;
		case 5:
			if (lookahead.matches("-")) {
				state = 6;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("=")) {
				state = 7;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 8;
			}
			break;
		case 6:
			Print();
			state = 0;
			break;
		case 7:
			Print();
			state = 0;
			break;
		case 8:
			UngetC();
			Print();
			state = 0;
			break;
		case 9:
			if (lookahead.matches("=")) {
				state = 10;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 11;
			}
			break;
		case 10:
			Print();
			state = 0;
			break;
		case 11:
			UngetC();
			Print();
			state = 0;
			break;
		case 12:
			if (lookahead.matches("=")) {
				state = 13;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 14;
			}
			break;
		case 13:
			Print();
			state = 0;
		case 14:
			UngetC();
			Print();
			state = 0;
			break;
		case 15:
			if (lookahead.matches("=")) {
				state = 16;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 17;
			}
			break;
		case 16:
			Print();
			state = 0;
			break;
		case 17:
			UngetC();
			Print();
			state = 0;
			break;
		case 18:
			if (lookahead.matches("=")) {
				state = 19;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches(">")) {
				state = 20;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 21;
			}
			break;
		case 19:
			Print();
			state = 0;
		case 20:
			if (lookahead.matches(">")) {
				state = 22;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("=")) {
				state = 23;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 24;
			}
			break;
		case 21:
			UngetC();
			Print();
			state = 0;
			break;
		case 22:
			Print();
			state = 0;
			break;
		case 23:
			Print();
			state = 0;
			break;
		case 24:
			UngetC();
			Print();
			state = 0;
			break;
		case 25:
			if (lookahead.matches("=")) {
				state = 26;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("<")) {
				state = 27;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 28;
			}
			break;
		case 26:
			Print();
			state = 0;
		case 27:
			if (lookahead.matches("=")) {
				state = 29;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 30;
			}
			break;
		case 28:
			Print();
			state = 0;
			break;
		case 29:
			Print();
			state = 0;
			break;
		case 30:
			UngetC();
			Print();
			state = 0;
			break;
		case 31:
			if (lookahead.matches("=")) {
				state = 34;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\\&")) {
				state = 33;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 32;
			}
			break;
		case 32:
			UngetC();
			Print();
			state = 0;
			break;
		case 33:
			Print();
			state = 0;
			break;
		case 34:
			Print();
			state = 0;
			break;
		case 35:
			if (lookahead.matches("=")) {
				state = 38;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\\|")) {
				state = 37;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 36;
			}
			break;
		case 36:
			UngetC();
			Print();
			state = 0;
			break;
		case 37:
			Print();
			state = 0;
			break;
		case 38:
			Print();
			state = 0;
			break;
		case 39:
			if (lookahead.matches("=")) {
				state = 41;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 40;
			}
			break;
		case 41:
			Print();
			state = 0;
			break;
		case 40:
			UngetC();
			Print();
			state = 0;
			break;
		case 42:
			if (lookahead.matches("=")) {
				state = 44;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 43;
			}
			break;
		case 43:
			UngetC();
			Print();
			state = 0;
			break;
		case 44:
			Print();
			state = 0;
			break;
		case 45:
		case 52:
		case 53:
		case 59:
		case 58:
		case 57:
		case 56:
		case 55:
		case 54:
		case 75:
		case 76:
		case 77:
			Print();
			state = 0;
			break;
		case 64:
			// 65 double quote
			// 66 String
			// 79 safe state for upcoming \
			if (lookahead.matches("\\\\")) {
				state = 79;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\"")) {
				state = 66;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\n|\r")) {
				state = 65;
			} else {
				state = 64;
				Current_Token.add(lookahead);
				readChar();
			}
			break;

		case 79:
			state = 64;
			Current_Token.add(lookahead);
			readChar();
			break;
		case 65:
			System.out.println(lookahead);
			UngetC();
			Print();
			state = 0;
			break;
		case 66:
			Print();
			state = 0;
			break;
		// new
		case 67:
			if (lookahead.matches("[a-zA-Z_$\\d]")) {
				state = 67;
				Current_Token.add(lookahead);
				readChar();
			} else {
				Current_Token.add(lookahead);
				state = 68;
			}
			break;
		case 68:
			UngetC();
			Printid();
			state = 0;
			break;
		// end new
		case 69:
			if (lookahead.matches("\\d")) {
				state = 69;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\\.")) {
				state = 71;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 70;
				Current_Token.add(lookahead);
			}
			break;
		case 70:
			UngetC();
			Print();
			state = 0;
			break;
		case 71:
			if (lookahead.matches("\\d")) {
				state = 71;
				Current_Token.add(lookahead);
				readChar();
			}

			else {
				Current_Token.add(lookahead);
				state = 72;
			}
			break;
		case 72:
			UngetC();
			Print();
			state = 0;
			break;
		case 73:
			if (lookahead.matches("\\d")) {
				state = 71;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("[a-zA-Z]")) {
				UngetC();
				state = 74;

			} else {
				state = 74;
				Current_Token.add(lookahead);
				readChar();
			}
			break;
		case 74:
			UngetC();
			Print();
			state = 0;
			break;
		case 60:
			if (lookahead.matches("\\\\")) {
				state = 60;
				Current_Token.add(lookahead);
				readChar();
			}
			if (lookahead.matches("\n") || lookahead.matches("\r")) {
				state = 62;
			} else {
				state = 61;
				Current_Token.add(lookahead);
				readChar();
			}

			break;

		case 61:
			if (lookahead.matches("'")) {
				state = 63;
				Current_Token.add(lookahead);
				readChar();
			} else {
				state = 62;
			}
			break;
		case 62:
			UngetC();
			Print();
			state = 0;
			break;
		case 63:
			Print();
			state = 0;
			break;
		case 46:
			if (lookahead.matches("/")) {
				state = 47;
				readChar();
			} else if (lookahead.matches("=")) {
				state = 49;
				Current_Token.add(lookahead);
				readChar();
			} else if (lookahead.matches("\\*")) {
				state = 50;
				readChar();
				Current_Token.remove(Current_Token.size() - 1);
			} else {
				state = 48;
			}
			break;
		case 47:
			if (lookahead.matches("\n")) {
				state = 0;
				Current_Token.remove(Current_Token.size() - 1);
				readChar();
			} else {
				state = 47;
				readChar();
			}
			break;
		case 48:
			UngetC();
			Print();
			state = 0;
			break;
		case 49:
			Print();
			state = 0;
			break;
		case 50:
			if (lookahead.matches("\\*")) {
				state = 51;
				readChar();
			} else {
				state = 50;
				readChar();
			}
			break;
		case 51:
			if (lookahead.matches("\\*")) {
				state = 51;
				readChar();
			} else if (lookahead.matches("/")) {
				state = 0;
				readChar();
			} else {
				state = 50;
				readChar();
			}
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

	public void Printid() {
		boolean flag = false;
		String GetName = "";
		if (!Current_Token.isEmpty() && state != 0) {
			for (int i = 0; i < Current_Token.size(); i++) {
				GetName += Current_Token.get(i);
			}
		}

		flag = RESERVED_WORDS.contains(GetName.toLowerCase());

		if (flag) {
			System.out.printf("\t%-30s\t%s%n", GetName, GetName.toUpperCase());
			Current_Token.clear();
		} else {
			System.out.printf("\t%-30s\t%s%n", GetName, "Identifier");
			Current_Token.clear();
		}
	}

}
