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

/**
 * The first position of all record formats. These codes are uniquely 
 * assigned for each type of record as follows.
 */
public enum RecordType {
	
	FILE_HEADER("1"),
	BATCH_HEADER("5"),
	ENTRY_DETAIL("6"),
	ENTRY_ADDENDA("7"),
	BATCH_CONTROL("8"),
	FILE_CONTROL("9")
	;
	
	public static RecordType forValue(String c){
		if(c==null || c.length()!=1) 
			return null;
		for(RecordType next:values()){
			if(next.value.equals(c))
				return next;
		}
		return null;
	}
	
	private final String value;
	
	private RecordType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}