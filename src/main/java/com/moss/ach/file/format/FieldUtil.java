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
package com.moss.ach.file.format;

public class FieldUtil {

	public static String getName(Field<?> field) {
		
		String[] parts = field.getClass().getName().split("\\.");
		String className = parts[parts.length - 1];
		String name = className.substring(0, 1).toLowerCase() + className.substring(1);
		
		if (name.endsWith("Field")) {
			name = name.substring(0, name.length() - "Field".length());
		}
		
		return name;
	}
	
	public static String leftPad(String s, char c, int length) {
		
		int count = length - s.length();
		
		if (count < 1) {
			return s;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<count; i++) {
			sb.append(c);
		}
		
		sb.append(s);
		
		return sb.toString();
	}
	
	public static String rightPad(String s, char c, int length) {
		
		int count = length - s.length();
		
		if (count < 1) {
			return s;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(s);
		
		for (int i=0; i<count; i++) {
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	public static String leftStrip(String s, char c) {
		
		int count = 0;
		
		for (int i=0; i<s.length(); i++) {
			if (s.charAt(i) != c) {
				break;
			}
			count++;
		}
		
		return s.substring(count);
	}
}
