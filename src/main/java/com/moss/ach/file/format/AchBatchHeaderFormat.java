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

import com.moss.ach.file.OriginatorStatusCode;
import com.moss.ach.file.RecordType;
import com.moss.ach.file.ServiceClassCode;
import com.moss.ach.file.SimpleDate;
import com.moss.ach.file.StandardEntryClassCode;
import com.moss.usbanknumbers.RoutingNumber;


public class AchBatchHeaderFormat {

	public RecordType recordType;
	
	public ServiceClassCode serviceClassCode;
	
	/**
	 * Except as otherwise noted below, the value of this field is established 
	 * by the Originator for purposes of further identifying the source of the 
	 * entry and for descriptive purposes for the Receiver.
	 * 
	 * (...more on page OR 70...)
	 */
	public String companyName;
	
	/**
	 * This field allows Originators and/or ODFIs to include codes (one or more), 
	 * of significance only to them, to enable specialized handling of all 
	 * subsequent entries in that batch. There will be no standardized 
	 * interpretation for the value of the field. This field must be returned 
	 * intact on any return entry.
	 */
	public String companyDiscretionaryData;
	
	/**
	 * The Company Identifiation is an alphameric code used to identify an 
	 * Originator. The Company Identification Field must be included on all 
	 * prenotification records and on each entry initiated puruant to such 
	 * prenotification. The Company ID may begin with the ANSI one-digit 
	 * Identification Code Designators (ICD), followed by the identification 
	 * number. The ANSI Identification Numbers and related Identification Code 
	 * Designators (ICD) are:
	 * 
	 * IRS Employer Identification Number (EIN) "1"
	 * Data Universal Numbering Systems (DUNS) "3"
	 * User Assigned Number "9"
	 * 
	 * (...more on page OR 70..)
	 */
	public String companyIdentification;
	
	public StandardEntryClassCode standardEntryClassCode;
	
	/**
	 * The Originator establishes the value of this field to provide a 
	 * description of the purpose of the entry to be displayed back to 
	 * the receiver. For example, "GAS BILL," "REG. SALARY," "INS. PREM," 
	 * "SOC. SEC.," "DTC," "TRADE PAY," "PURCHASE," etc.
	 * 
	 * This field must contain the word "REVERSAL" (left justified) when the 
	 * batch contains reversing entries.
	 * 
	 * This field must contain the word "RECLAIM" (left justified) when the 
	 * batch contains reclamation entries.
	 * 
	 * This field must contain the word "NONSETTLED" (left justified) when the 
	 * batch contains entries which could not settle.
	 * 
	 * (..more on page OR 69..)
	 */
	public String companyEntryDescription;
	
	/**
	 * Except as otherwise noted below, the Originator establishes this field 
	 * as the date it would like to see displayed to the receiver for 
	 * descriptive purposes. This field is never used to control timing of any
	 * computer or manual operation. It is solely for descriptive purposes. 
	 * The RDFI should not assume any specific format. Examples of possible 
	 * entries in this field are "011392,", "01 92," "JAN 13," "JAN 92," etc.
	 */
	public String companyDescriptiveDate;
	
	/**
	 * The effective entry date is the date specified by the Originator on 
	 * which it intends a batch of entries to be settled. For credit entries,
	 * the efective entry date shall be either one or two banking days 
	 * following the banking day of processing as established by the 
	 * Originating ACH Operator (the processing date). For debit entries, the
	 * effective entry date shall be one banking day following the processing date.
	 * 
	 * Batches of entries containing an effective entry date beyond the 
	 * designated number of days allowed will be rejected by the ACH
	 * Operator and returned to the ODFI. If this field is blank or zero, 
	 * or partial blank or partially non-numeric, or contains an incomplete 
	 * date, day numbers higher than 31 or month numbers higher than 12, the
	 * Originating ACH Operator shall insert the next banking day after the 
	 * processing date as the effective entry date.
	 * 
	 * (..more on page OR 72..)
	 * 
	 * For purposes of this provision, the term "banking day" refers to a day
	 * on which the Originating ACH Operator's facility is being operated.
	 */
	public SimpleDate effectiveEntryDate;
	
	/**
	 * The scheduled Settlement Date for a batch of entries shall be inserted 
	 * by the Receiving ACH Operator. This is the date on which the 
	 * Participating DFI or its correspondent is scheduled to be debited or 
	 * credited by the Federal Reserve. For all entries except return entries 
	 * and check safekeeping entries, the Settlement Date inserted by the 
	 * Receiving ACH operator will be the same as the effective enry date 
	 * unless the date specified is the same as or earlier than the banking 
	 * day of processing as established by the Originating ACH Operator 
	 * (the processing date), in which case the scheduled Settlement Date will 
	 * be the next banking day folowing the processing date. Retruns, 
	 * Notifications of Change, and TRC/TRX entries will be settles at the 
	 * earliest opportunity, i.e., same banking day of processing or next 
	 * banking day following the processing date. For purposes of this 
	 * provision, the term "banking day" refers to a day on which the 
	 * Originating ACH Operator's facility is being operated.
	 */
	public String settlementDate; // Julian
	
	public OriginatorStatusCode originatorStatusCode;
	
	/**
	 * The routing number is used to identify the DFI originating entries 
	 * within a given branch.
	 */
	public RoutingNumber originatingDfiIdentification;
	
	/**
	 * This number is assigned in ascending sequence to each batch by the ODFI 
	 * or its Sending Point in a given file of entries. Since the batch number 
	 * in the Batch Header Record and the Batch Control Record is the same, 
	 * the ascending sequence number should be assigned by batch and not by 
	 * record.
	 */
	public long batchNumber;
}
