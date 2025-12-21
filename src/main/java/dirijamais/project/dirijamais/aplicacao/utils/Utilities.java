package dirijamais.project.dirijamais.aplicacao.utils;

import java.util.Collection;

public class Utilities {

	/**
	 * Verifica se um character é um caracter numérico.
	 * @param c
	 * @return {@code true} apenas se o caracter passado como parâmetro estiver entre '0' e '9', caso contrário retorna {@code false}
	 */
	public static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}
	
	/**
	 * Verifica se uma String possui apenas caracteres numéricos
	 * @param str
	 * @return {@code true} apenas se todos os caracteres da string forem numéricos, caso contrário retorna {@code false}
	 */
	public static boolean containsOnlyDigits(String str) {
		for (int i1 = 0; i1 < str.length(); i1++) {
			if (!isDigit(str.charAt(i1))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifica se uma String possui apenas caracteres numéricos ou '.' (ponto).
	 * @param str
	 * @return
	 */
	public static boolean containsOnlyDigitsOrDots(String str) {
		for (int i1 = 0; i1 < str.length(); i1++) {
			char c = str.charAt(i1);
			if (!isDigit(c) && c != '.') {
				return false;
			}
		}
		str.matches(str);
		return true;
	}

	/**
	 * Remove os caracteres não numéricos de uma string. Util para remover formatação de cpf, cnpj etc...
	 * @param str
	 * @return {@link String} contendo apenas os algarismos numéricos.
	 */
	public static String removeNonDigits(String str) {
		StringBuilder ret = new StringBuilder();
		for (int i1 = 0; i1 < str.length(); i1++) {
			char c = str.charAt(i1);
			if (isDigit(c)) {
				ret.append(c);
			}
		}
		return ret.toString();
	}
	
    public static boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }
	
}
