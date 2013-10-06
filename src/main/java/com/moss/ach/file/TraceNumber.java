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

import com.moss.ach.file.format.FieldUtil;
import com.moss.usbanknumbers.RoutingNumber;
import com.moss.usbanknumbers.RoutingNumberException;

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
public class TraceNumber {
	
	private final RoutingNumber routingNumber;
	private final long sequenceNumber;
	
	public TraceNumber(RoutingNumber routingNumber, long sequenceNumber) throws TraceNumberException {
		
		if (routingNumber == null) {
			throw new NullPointerException();
		}
		
		if (sequenceNumber < 0) {
			throw new TraceNumberException("The entry detail sequence number must be a number greater than or equal to zero");
		}
		
		String value = Long.toString(sequenceNumber);
		if (value.length() > 7) {
			throw new TraceNumberException("The entry detail sequence number must be less than or equal to seven digits.");
		}
		
		this.sequenceNumber = sequenceNumber;
		this.routingNumber = routingNumber;
	}
	
	public TraceNumber(String value)  throws TraceNumberException {
		
		RoutingNumber routingNumber;
		try {
			routingNumber = RoutingNumber.fromNoChecksum(value.substring(0, 8));
		}
		catch (RoutingNumberException ex) {
			throw new TraceNumberException(ex.getMessage());
		}
		
		String sequenceNumberString = value.substring(8);
		sequenceNumberString = FieldUtil.leftStrip(sequenceNumberString, '0');
		long sequenceNumber = Long.parseLong(sequenceNumberString);
		
		this.sequenceNumber = sequenceNumber;
		this.routingNumber = routingNumber;
	}
	
	public RoutingNumber getOdfiRoutingNumber() {
		return routingNumber;
	}

	public long getEntryDetailSequenceNumber() {
		return sequenceNumber;
	}
	
	public boolean equals(Object o) {
		return
			o != null
			&&
			o instanceof TraceNumber
			&&
			routingNumber.equals(((TraceNumber)o).routingNumber)
			&&
			sequenceNumber == ((TraceNumber)o).sequenceNumber;
	}
	
	public String toString() {
		
		String routingNumberString = this.getOdfiRoutingNumber().toString();
		routingNumberString = routingNumberString.substring(0, routingNumberString.length() - 1);
		
		String sequenceNumberString = Long.toString(this.getEntryDetailSequenceNumber());
		sequenceNumberString = FieldUtil.leftPad(sequenceNumberString, '0', 7);
		
		return routingNumberString + sequenceNumberString;
	}
}
