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

public enum AddendaTypeCode {

	/**
	 * Addenda Record is used to provide foreign payment information.
	 * Applies to {@link StandardEntryClassCode#CBR}, 
	 * {@link StandardEntryClassCode#PBR}.
	 */
	CROSS_BORDER("01"),
	
	/**
	 * Addenda Record is used for terminal location description information.
	 * 
	 * Applies to {@link StandardEntryClassCode#POS}, 
	 * {@link StandardEntryClassCode#SHR}, {@link StandardEntryClassCode#MTE}. 
	 */
	POINT_OF_SALE("02"),
	
	/**
	 * Applies to 
	 * {@link StandardEntryClassCode#ACK}, {@link StandardEntryClassCode#ATX}, 
	 * {@link StandardEntryClassCode#CCD}, {@link StandardEntryClassCode#CIE}, 
	 * {@link StandardEntryClassCode#CTX}, {@link StandardEntryClassCode#DNE}, 
	 * {@link StandardEntryClassCode#ENR}, {@link StandardEntryClassCode#PPD}, 
	 * {@link StandardEntryClassCode#TRX}, {@link StandardEntryClassCode#WEB},
	 */
	ADDENDA("05"),
	
	/**
	 * Applies to {@link StandardEntryClassCode#COR}
	 */
	AUTOMATED_CHANGE_NOTIFICATION("98"),
	
	/**
	 * Applies to the following addenda types: 
	 * Automated Return Entry Addenda Record, 
	 * Automated Dishonored Return Entry Addenda Record and 
	 * Automated Contested Dishonored Return Entry Addenda Record.
	 */
	AUTOMATED_RETURN_NOTIFICATION("99");
	
	public static AddendaTypeCode forValue(String c){
		if(c==null || c.length()!=2) 
			return null;
		for(AddendaTypeCode next:values()){
			if(next.value.equals(c))
				return next;
		}
		return null;
	}
	private final String value;

	private AddendaTypeCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
