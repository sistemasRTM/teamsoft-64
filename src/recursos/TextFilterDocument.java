package recursos;

/**
 * Copyright 2009 JosÈ Montero.
 *
 *
 * This class is part of JMC Java Utilities library.
 * 
 * This class is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This class is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this class.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * If you wish, you can contact the author at: jmc.java.utilities@gmail.com
 * 
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFilterDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;
	public final static String DIGITS = "0123456789";
	public final static String PUNTO = ".";
	public final static String ESPACIO = " ";

	public final static String LETTERS =
		"abcdefghijklmnÒopqrstuvwxyzABCDEFGHIJKLMN—OPQRSTUVWXYZ·ÈÌÛ˙¡…Õ”⁄¸‹";
	public final static String DIGITS_AND_LETTERS = DIGITS + LETTERS;
	
	public final static String DIGITS_AND_PUNTO=DIGITS + PUNTO;
	public final static String DIGITS_AND_ESPACIO=DIGITS + ESPACIO;
	public final static String LETTERS_AND_PUNTO=LETTERS + PUNTO;
	public final static String LETTERS_AND_ESPACIO=LETTERS + ESPACIO;

	public final static String DIGITS_AND_PUNTO_AND_ESPACIO=DIGITS + PUNTO+ ESPACIO;
	public final static String LETTERS_AND_PUNTO_AND_ESPACIO=LETTERS + PUNTO+ ESPACIO;
	
	public final static int UNRESTRICTED_LENGTH = -1;

	private String acceptedCharacters;
	private int maximumLength = UNRESTRICTED_LENGTH;

	public TextFilterDocument(String acceptedCharacters) {
		this.acceptedCharacters = acceptedCharacters;
	}

	public TextFilterDocument(String acceptedCharacters, int maximumLength) {
		this.acceptedCharacters = acceptedCharacters;
		setMaximumLength(maximumLength);
	}

	public TextFilterDocument(int maximumLength) {
		setMaximumLength(maximumLength);
	}

	public String getAcceptedCharacters() {
		return acceptedCharacters;
	}

	public void setAcceptedCharacters(String acceptedCharacters) {
		this.acceptedCharacters = acceptedCharacters;
	}

	public int getMaximumLength() {
		return maximumLength;
	}

	public void setMaximumLength(int maximumLength) {
		if (maximumLength < UNRESTRICTED_LENGTH || maximumLength == 0) {
			throw new IllegalArgumentException(
				"The maximum length must be >=1 or UNRESTRICTED_LENGTH.");
		}
		this.maximumLength = maximumLength;
	}

	@Override
	public void insertString(int offset, String string, AttributeSet attributes)
			throws BadLocationException {
		if (string == null) {
			return;
		}

		// Checks maximum length
		if (maximumLength != UNRESTRICTED_LENGTH &&
			getLength() + string.length() > maximumLength) {
			return;
		}

		// Checks accepted characters
		if (acceptedCharacters != null) {
			for (int i = 0; i < string.length(); i++) {
				if (acceptedCharacters.indexOf(string.charAt(i)) == -1) {
					return;
				}
			}
		}

		// Performs the insert operation
		super.insertString(offset, string, attributes);
	}
}
