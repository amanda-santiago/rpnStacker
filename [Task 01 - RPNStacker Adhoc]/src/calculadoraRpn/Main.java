package calculadoraRpn;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Stack;
import java.io.File;

public class Main {

	public static void main(String[] args) throws IOException {
		Stack<String> pilha = new Stack<String>();

		File file = new File("Calc1.stk");
		byte[] fileContent = Files.readAllBytes(file.toPath());
		String expressaoRpn[] = new String(fileContent).split("\n");
		int contExpressoes = 1;
		String resultado;

		for (int i = 0; i < expressaoRpn.length; i++) {
			if (!expressaoRpn[i].equals("=")) {
				boolean ehDigito = expressaoRpn[i].matches("[0-9]*");
				if (ehDigito) {
					pilha.push(expressaoRpn[i]);
				} else {
					if ("+-*/".contains(expressaoRpn[i])) {
						BigDecimal operando2 = new BigDecimal(pilha.pop());
						BigDecimal operando1 = new BigDecimal(pilha.pop());

						switch (expressaoRpn[i]) {
						case "+":
							pilha.push(operando1.add(operando2).toString());
							break;
						case "-":
							pilha.push(operando1.subtract(operando2).toString());
							break;
						case "*":
							pilha.push(operando1.multiply(operando2).toString());
							break;
						case "/":
							pilha.push(operando1.divide(operando2).toString());
							break;
						}
					}
				}
			} else {
				resultado = pilha.pop();
				System.out.println("O resultado da expressao " + contExpressoes + " em RPN é: " + resultado);
				contExpressoes++;
			}

		}

		resultado = pilha.pop();
		System.out.println("O resultado da expressao " + contExpressoes + " em RPN é: " + resultado);

	}

}
