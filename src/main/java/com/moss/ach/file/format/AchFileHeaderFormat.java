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

import com.moss.ach.file.FileIdModifier;
import com.moss.ach.file.PriorityCode;
import com.moss.ach.file.RecordType;
import com.moss.ach.file.SimpleDate;
import com.moss.ach.file.SimpleTime;
import com.moss.usbanknumbers.RoutingNumber;


public class AchFileHeaderFormat {

	public RecordType recordType;
	
	public PriorityCode priorityCode;
	
	/**
	 * This field contains the Routing Number of the ACH Operator or receiving 
	 * point to which the file is being sent. The 10 character field begins with 
	 * a blank in the first position, followed by the four digit Federal Reserve 
	 * Routing Symbol, the four digit ABA Insitution Identifier, and the Check 
	 * Digit (bTTTTAAAAC).
	 */
	public RoutingNumber immediateDestination;
	
	/**
	 * This field contains the Routing Number of the ACH Operator or sending 
	 * point that is sending the file. The 10 character field begins with 
	 * a blank in the first position, followed by the four digit Federal Reserve 
	 * Routing Symbol, the four digit ABA Insitution Identifier, and the Check 
	 * Digit (bTTTTAAAAC).
	 */
	public RoutingNumber immediateOrigin;
	
	/**
	 * The File Creation Date is expressed in a "YYMMDD" format. The File Creation 
	 * Date is the date on which the file is prepared by an ODFI (ACH input files) 
	 * or the date (exchange date) on which a file is transmitted from ACH Operator
	 * to ACH Operator, or from ACH Operator to RDFIs (ACH output files).
	 */
	public SimpleDate fileCreationDate;
	
	/**
	 * The File Creation Time is expressed ina n "HHMM" (24 hour clock) format.
	 */
	public SimpleTime fileCreationTime;
	
	public FileIdModifier fileIdModifier;
	
	/**
	 * The Record Size Field indicates the number of characters contained in each 
	 * record. At this time, the value "094" must be used.
	 */
	public long recordSize;
	
	/**
	 * The Blocking Factor defines the number of physical records within a block 
	 * (a block is 940 characters). For all files moving between a DFI and an ACH 
	 * Operator (either way), the value "10" must be used. If the number of records
	 * within the file is not a multiple of ten, the remainder of the block must 
	 * be nine-filled.
	 */
	public long blockingFactor;
	
	/**
	 * This field identifies a code to allow for future format variations. As 
	 * currently defined, this field will contain a value of "1".
	 */
	public long formatCode;
	
	/**
	 * This field contains the name of the ACH or receiving point for which that 
	 * file is destined.
	 */
	public String immediateDestinationName;
	
	/**
	 * This field contains the name of the ACH operator or sending point that is sending the file.
	 */
	public String immediateOriginName;
	
	/**
	 * This field is reserved for information pertinent to the Originator.
	 */
	public String referenceCode;
}
