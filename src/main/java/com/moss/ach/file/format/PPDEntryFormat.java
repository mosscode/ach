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

import com.moss.ach.file.RecordType;
import com.moss.ach.file.TraceNumber;
import com.moss.ach.file.TransactionCode;
import com.moss.usbanknumbers.RoutingNumber;

public class PPDEntryFormat extends AchEntryDetailFormat {

	public RecordType recordType;
	
	public TransactionCode transactionCode;
	
	/**
	 * The standard Routing Number as assigned by Thomson Financial Publishing 
	 * (with Check Digit) is used to identify the DFI in which the Receiver 
	 * maintains his account or a Routing Number assigned to a federal 
	 * government agency by the Federal Reserve.
	 */
	public RoutingNumber receivingDfiIdentification;
	
	/**
	 * See page OR 68
	 */
	public long checkDigit;
	
	/**
	 * See page OR 71
	 */
	public String dfiAccountNumber;
	
	/**
	 * The RDFI posts the amount to the appropriate account authorized by 
	 * the Receiver. A zero Amount is acceptable only with specific 
	 * {@link TransactionCode}s.
	 */
	public long amount;
	
	/**
	 * Except as otherwise noted, this field contains the accounting number by 
	 * which the Receiver is known to the Originator. It is included for 
	 * further identification and for descriptive purposes. 
	 * The RDFI shoudl assume no specific format to be present (e.g., presence 
	 * or absence of dashes), but can assume that the firld is pre-edited so 
	 * that it is suitable for description as is (including blanks in unused 
	 * positions).
	 * 
	 * (..more on page OR 74..)
	 */
	public String individualIdentificationNumber;
	
	/**
	 * Except as otherwise noted, this field entered by the Originator provides
	 * additinal identification for the receiver and may be helpful in 
	 * identifying returned entries.
	 */
	public String individualName;
	
	/**
	 * This field allows ODFIs to include codes, of significance only to them, 
	 * to enable specialized handling of the entry. There will be no 
	 * standardized interpretation for the value of this field. It can either 
	 * be a single two-character code, or two distince one-character codes, 
	 * according to the needs of the ODFI and/or Originator involved. This 
	 * field must be returned intact for any returned entry.
	 */
	public String discretionaryData;
	
	/**
	 * This field indicates the existence of an Addenda Record. A value of "1" 
	 * indicates that one ore more addenda records follow, and "0" means no 
	 * such record is present.
	 */
	public boolean addendaRecordIndicator;
	
	/**
	 * A trace number, assigned by the ODFI in ascending sequence, is included
	 * in each Entry Detail Record, Corporate Entry Detail Record, and 
	 * Addenda Record. Trace Numbers uniquely identify each entry within a 
	 * batch in an ACH input file. In association with the Batch Number, 
	 * Transmission (File Creation) Date, and File ID Modifier, the Trace 
	 * Number uniquely identifies an entry within a given file. For addenda 
	 * Records, the Trace Number will be identical to the Trace Number in the 
	 * associated Entry Detail Record, since the Trace Number is associated 
	 * with an entry or item rather than a physical record.
	 * 
	 * (..more on page OR 81..)
	 */
	public TraceNumber traceNumber;
	
	public PPDEntryAddendaFormat addenda;
}
