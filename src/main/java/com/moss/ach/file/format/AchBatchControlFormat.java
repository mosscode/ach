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
import com.moss.ach.file.ServiceClassCode;
import com.moss.usbanknumbers.RoutingNumber;

public class AchBatchControlFormat {

	public RecordType recordType;
	
	public ServiceClassCode serviceClassCode;
	
	/**
	 * This count is a tally of each Entry Detail Record and each Addenda 
	 * Record processed, within either the batch or file as appropriate.
	 */
	public long entryAddendaCount;
	
	/**
	 * The Receiving DFI Identification in each Entry Detail Record is hashed
	 * to provide a check against inadvertent alteration of data contents due
	 * to hardware failure or program error. (NOTE: Addenda Records are not 
	 * hashed.)
	 * 
	 * In this context the Entry Hash is the sum of the corresponding fields in 
	 * the Entry Detail Records on the file.
	 */
	public long entryHash;
	
	/**
	 * Contains accumulated Entry debit totals within the batch.
	 */
	public long totalDebitEntryDollarAmount;
	
	/**
	 * Contains accumulated Entry credit totals within the batch.
	 */
	public long totalCreditEntryDollarAmount;
	
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
	
	/**
	 * The MAC is an eight character code derived from a special key used in 
	 * conjunction with the DES algorithm. The purpose of the MAC is to 
	 * validate the authenticity of ACH entries. The DES algorithm and key 
	 * message standards must be in accordance with standards adopted by the 
	 * American National Standards Institute. The remaining eleven characters 
	 * of this field are blank. 
	 */
	public String messageAuthenticationCode;
	
	/**
	 * Blank, 6 characters long
	 */
	public String reserved;
	
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
