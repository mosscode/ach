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

public enum StandardEntryClassCode {

	/**
	 * ACH Payment Acknowlegement
	 */
	ACK,
	
	/**
	 * Automated Accounting Advice
	 */
	ADV,
	
	/**
	 * Accounts Receivable Entry
	 */
	ARC,
	
	/**
	 * Financial EDI Acknowlegement 
	 */
	ATX,
	
	/**
	 * Corporate Cross-Border Payment
	 */
	CBR,
	
	/**
	 * Cash Concentration or Disbursement
	 */
	CCD,
	
	/**
	 * Customer Initiated Entry
	 */
	CIE,
	
	/**
	 * Automated Notification of Change and Automated Refused Notification of Change
	 */
	COR,
	
	/**
	 * Corporate Trade Exchange
	 */
	CTX,
	
	/**
	 * Death Notification Entry
	 */
	DNE,
	
	/**
	 * Automated Enrollment Entry
	 */
	ENR,
	
	/**
	 * Machine Transfer Entry
	 */
	MTE,
	
	/**
	 * Consumer Cross-Border Payment
	 */
	PBR,
	
	/**
	 * Point-of-Purchase Entry
	 */
	POP,
	
	/**
	 * Point of Sale Entry
	 */
	POS,
	
	/**
	 * Prearranged Payment and Deposit Entry
	 */
	PPD,
	
	/**
	 * Re-presented Check Entry
	 */
	RCK,
	
	/**
	 * Shared Network Transaction
	 */
	SHR,
	
	/**
	 * Telephone Initiated Entry
	 */
	TEL,
	
	/**
	 * Truncated Entry
	 */
	TRC,
	
	/**
	 * Truncated Entries Exchange
	 */
	TRX,
	
	/**
	 * Internet-Initiated Entry
	 */
	WEB,
	
	/**
	 * Destroyed Check Entry
	 */
	XCK;
}
