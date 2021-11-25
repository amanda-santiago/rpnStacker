package rpn;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File;

public class Rpn {

	public static void main(String[] args) throws Exception {
		Stack<String> pilha = new Stack<String>();

		File file = new File("Calc1.stk");
		byte[] fileContent = Files.readAllBytes(file.toPath());
		String expressaoRpn[] = new String(fileContent).split("\n");

		String resultado;
		boolean continua = true;
		ArrayList<Token> listaToken = new ArrayList<Token>();

		for (int i = 0; i < expressaoRpn.length; i++) {

			boolean ehDigito = expressaoRpn[i].matches("[0-9]*");
			if (ehDigito) {

				listaToken.add(new Token(TokenType.NUM, expressaoRpn[i]));

			} else if ("+-*/".contains(expressaoRpn[i])) {

				switch (expressaoRpn[i]) {
				case "+":
					listaToken.add(new Token(TokenType.PLUS, expressaoRpn[i]));
					break;
				case "-":
					listaToken.add(new Token(TokenType.MINUS, expressaoRpn[i]));
					break;
				case "*":
					listaToken.add(new Token(TokenType.STAR, expressaoRpn[i]));
					break;
				case "/":
					listaToken.add(new Token(TokenType.SLASH, expressaoRpn[i]));
					break;
				}
			} else {
				continua = false;
				throw new Exception("Error: Unexpected character: " + expressaoRpn[i]);

			}
		}

		if (continua) {
			System.out.println(listaToken.toString());
			
			for (int i = 0; i < listaToken.size(); i++) {

				if (listaToken.get(i).type == TokenType.NUM) {

					pilha.push(listaToken.get(i).lexeme);

				} else {
					BigDecimal operando2 = new BigDecimal(pilha.pop());
					BigDecimal operando1 = new BigDecimal(pilha.pop());

					switch (listaToken.get(i).type) {
					case PLUS:
						pilha.push(operando1.add(operando2).toString());
						break;
					case MINUS:
						pilha.push(operando1.subtract(operando2).toString());
						break;
					case STAR:
						pilha.push(operando1.multiply(operando2).toString());
						break;
					case SLASH:
						pilha.push(operando1.divide(operando2).toString());
						break;
					default:
						break;
					}
				}
			}

			resultado = pilha.pop();
			System.out.println("O resultado da expressao em RPN é: " + resultado);
		}
	}

}