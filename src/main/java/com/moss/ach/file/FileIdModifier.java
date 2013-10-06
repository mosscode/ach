/**
 * Copyright (C) 2013, Moss Computing Inc.
 *
 * This file is part of ach.
 *
 * ach is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * ach is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ach; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version.
 */
package com.moss.ach.file;

import java.util.regex.Pattern;

/**
 * The File ID Modifier is provided in the File Header Record to permit
 * multiple files created on the same date and between the same participants 
 * to be distinguished. Only upper case A-Z and numeric 0-9 are permitted.
 */
public class FileIdModifier {

	private final char value;

	public FileIdModifier(char value) throws FileIdModifierException {
		
		if (!Pattern.matches("[0-9A-Z]{1}", Character.toString(value))) {
			throw new FileIdModifierException("must be a single character between 0-9 or A-Z: '" + value + "'");
		}
		
		this.value = value;
	}
	
	public char getValue() {
		return value;
	}
	
	public boolean equals(Object o) {
		return
			o != null
			&&
			o instanceof FileIdModifier
			&&
			((FileIdModifier)o).value == value;
	}
	
	public String toString() {
		return Character.toString(value);
	}
	
	public static char[] possibleValues() {
	
		char[] values = new char[36];
		
		for (int i=0; i<26; i++) {
			values[i] = (char) ('A' + i); 
		}
		
		for (int i=0; i<10; i++) {
			values[26 + i] = (char) ('0' + i);
		}
		
		return values;
	}
}
