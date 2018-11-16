package de.htwsaar.nytSearchEngine.util;

import org.jsoup.Jsoup;
import java.text.BreakIterator;
import java.util.Locale;

/**
 * Tokenizer class, clean and split any incoming strings
 *
 * @author Shenna RWP
 * @author Agra Bimantara
 */
class Tokenizer
{
	private static final Locale LOCALE_US = Locale.US;
	private static final String NONALPHANUMERIC = "[^A-Za-z0-9.]";
	private static final String ENDPERIOD = "\\.$";
	private static final String WHITESPACES = "\\s+";
	private static final String SPACE = " ";

	/**
	 * remove any remaining XML-Tags
	 * @param string string to be cleaned
	 * @return clean string
	 */
	private static String removeTags(String string) {
		return Jsoup.parse(string).text();
	}

	/**
	 * remove any non alphanumeric characters in the string
	 * @param string string to be cleaned
	 * @return clean string
	 */
	private static String removeSymbols(String string) {
		return string.replaceAll(NONALPHANUMERIC, SPACE);
	}

	/**
	 * Tokenize the string, BreakIterator will separate each sentence in the text
	 * @param string text to be tokenized
	 * @return each words from the text inside an array
	 */
	static String[] tokenizeString(String string) {
		String text = "";

		BreakIterator breakIterator = BreakIterator.getSentenceInstance(LOCALE_US);
		breakIterator.setText(removeTags(string));
		int start = breakIterator.first();
		for (int end = breakIterator.next();
			 end != BreakIterator.DONE;
			 start = end, end = breakIterator.next()) {
			 String toAdd = removeSymbols(string.substring(start, end))
												 .trim()
												 .replaceAll(ENDPERIOD, SPACE)
												 .toLowerCase();
			text += toAdd + SPACE; //add space in the end of each sentence
		}

		return text.split(WHITESPACES);
	}

}
