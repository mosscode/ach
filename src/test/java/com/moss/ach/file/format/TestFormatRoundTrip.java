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

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moss.ach.file.FileIdModifier;
import com.moss.ach.file.OriginatorStatusCode;
import com.moss.ach.file.PriorityCode;
import com.moss.ach.file.RecordType;
import com.moss.ach.file.ServiceClassCode;
import com.moss.ach.file.SimpleDate;
import com.moss.ach.file.SimpleTime;
import com.moss.ach.file.StandardEntryClassCode;
import com.moss.ach.file.TraceNumber;
import com.moss.ach.file.TransactionCode;
import com.moss.ach.file.format.AchFileFormat;
import com.moss.ach.file.format.AchFileFormatReader;
import com.moss.ach.file.format.AchFileFormatWriter;
import com.moss.ach.file.format.RCKEntryFormat;
import com.moss.usbanknumbers.RoutingNumber;

public class TestFormatRoundTrip {
	
	private String file;
	private Reader reader;
	
	static {
//		BasicConfigurator.configure();
	}
	
	@Before
	public void before() throws Exception {
		
		final StringBuilder achFileData = new StringBuilder();
		
		/*
		 * File Header
		 */
		{
			final String
				RECORD_TYPE_CODE = 				"1",
				PRIORITY_CODE = 				"01",
				IMMEDIATE_DESTINATION = 		" 076401251",
				IMMEDIATE_ORIGIN = 				" 076401251",
				FILE_CREATION_DATE = 			"080725",
				FILE_CREATION_TIME = 			"1650",
				FILE_ID_MODIFIER = 				"0",
				RECORD_SIZE = 					"094",
				BLOCKING_FACTOR = 				"10",
				FORMAT_CODE = 					"1",
				IMMEDIATE_DESTINATION_NAME = 	"FunZone                ",
				IMMEDIATE_ORIGIN_NAME = 		"FunZone                ",
				REFERENCE_CODE = 				"asD 1234"
			;

			achFileData
			.append(RECORD_TYPE_CODE)
			.append(PRIORITY_CODE)
			.append(IMMEDIATE_DESTINATION)
			.append(IMMEDIATE_ORIGIN)
			.append(FILE_CREATION_DATE)
			.append(FILE_CREATION_TIME)
			.append(FILE_ID_MODIFIER)
			.append(RECORD_SIZE)
			.append(BLOCKING_FACTOR)
			.append(FORMAT_CODE)
			.append(IMMEDIATE_DESTINATION_NAME)
			.append(IMMEDIATE_ORIGIN_NAME)
			.append(REFERENCE_CODE)
			.append("\n");
		}
				
		/*
		 * Generic Batch Header 
		 */
		{
			final String
				RECORD_TYPE_CODE =					"5",
				SERVICE_CLASS_CODE = 				"200",
				COMPANY_NAME =						"SookySoo        ",
				COMPANY_DISCRETIONARY_DATA =		"OOpaOOpa            ",
				COMPANY_IDENTIFICATION =			"1dent1fy  ",
				STANDARD_ENTRY_CLASS_CODE =			"RCK",
				COMPANY_ENTRY_DESCRIPTION =			"SookySoo  ",
				COMPANY_DESCRIPTIVE_DATE =			"ToDaY ",
				EFFECTIVE_ENTRY_DATE =				"080101",
				SETTLEMENT_DATE =					"111",
				ORIGINATOR_STATUS_CODE =			"0",
				ORIGINATING_DFI_IDENTIFICATION =	"07640125",
				BATCH_NUMBER = 						"0000001"
			;
			
			achFileData
				.append(RECORD_TYPE_CODE)
				.append(SERVICE_CLASS_CODE)
				.append(COMPANY_NAME)
				.append(COMPANY_DISCRETIONARY_DATA)
				.append(COMPANY_IDENTIFICATION)
				.append(STANDARD_ENTRY_CLASS_CODE)
				.append(COMPANY_ENTRY_DESCRIPTION)
				.append(COMPANY_DESCRIPTIVE_DATE)
				.append(EFFECTIVE_ENTRY_DATE)
				.append(SETTLEMENT_DATE)
				.append(ORIGINATOR_STATUS_CODE)
				.append(ORIGINATING_DFI_IDENTIFICATION)
				.append(BATCH_NUMBER)
				.append("\n")
			;
		}
		

		/*
		 * RCK Detail Entry
		 */
		{
			final String
				RECORD_TYPE_CODE = "6",
				TRANSACTION_CODE = "21",
				RECEIVING_DFI_IDENTIFICATION = "07640125",
				CHECK_DIGIT = "1",
				DFI_ACCOUNT_NUMBER = "00000000000000001",
				AMOUNT = "0000000001",
				CHECK_SERIAL_NUMBER = "000000000000001",
				INDIVIDUAL_NAME = "John Doe              ",
				DISCRETIONARY_DATA = "DF",
				ADDENDA_RECORD_INDICATOR = "0",
				TRACE_NUMBER = "076401251660936"
			;
			
			achFileData
				.append(RECORD_TYPE_CODE)
				.append(TRANSACTION_CODE)
				.append(RECEIVING_DFI_IDENTIFICATION)
				.append(CHECK_DIGIT)
				.append(DFI_ACCOUNT_NUMBER)
				.append(AMOUNT)
				.append(CHECK_SERIAL_NUMBER)
				.append(INDIVIDUAL_NAME)
				.append(DISCRETIONARY_DATA)
				.append(ADDENDA_RECORD_INDICATOR)
				.append(TRACE_NUMBER)
				.append("\n")
			;
		}

		/*
		 * Generic Batch Control 
		 */
		{
			final String
				RECORD_TYPE_CODE =					"8",
				SERVICE_CLASS_CODE = 				"200",
				ENTRY_ADDENDA_COUNT = 				"000000",
				ENTRY_HASH = 						"0000000000",
				TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT = 	"000000000000",
				TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT = 	"000000000000",
				COMPANY_IDENTIFICATION =			"1dent1fy  ",
				MESSAGE_AUTHENTICATION_CODE = 		"0000000000000000000",
				RESERVED = 							"      ",
				ORIGINATING_DFI_IDENTIFICATION =	"07640125",
				BATCH_NUMBER = 						"0000001"
			;
			
			achFileData
				.append(RECORD_TYPE_CODE)
				.append(SERVICE_CLASS_CODE)
				.append(ENTRY_ADDENDA_COUNT)
				.append(ENTRY_HASH)
				.append(TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT)
				.append(TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT)
				.append(COMPANY_IDENTIFICATION)
				.append(MESSAGE_AUTHENTICATION_CODE)
				.append(RESERVED)
				.append(ORIGINATING_DFI_IDENTIFICATION)
				.append(BATCH_NUMBER)
				.append("\n")
			;
		}

		/*
		 * File Control
		 */
		{
			final String
				RECORD_TYPE_CODE = "9",
				BATCH_COUNT = "000000",
				BLOCK_COUNT = "000001",
				ENTRY_ADDENDA_COUNT = "00000000",
				ENTRY_HASH = "0000000001",
				TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT_IN_FILE = "000000000000",
				TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT_IN_FILE = "000000000000",
				RESERVED = "                                       "
			;

			achFileData
			.append(RECORD_TYPE_CODE)
			.append(BATCH_COUNT)
			.append(BLOCK_COUNT)
			.append(ENTRY_ADDENDA_COUNT)
			.append(ENTRY_HASH)
			.append(TOTAL_DEBIT_ENTRY_DOLLAR_AMOUNT_IN_FILE)
			.append(TOTAL_CREDIT_ENTRY_DOLLAR_AMOUNT_IN_FILE)
			.append(RESERVED);
		}
		
		file = achFileData.toString();
		reader = new StringReader(file);
	}

	@Test
	public void roundTrip() throws Exception {
		
		AchFileFormatReader fileReader = new AchFileFormatReader(reader);
		AchFileFormat achFile = fileReader.read();
		
		Assert.assertEquals(RecordType.FILE_HEADER, achFile.header.recordType);
		Assert.assertEquals(PriorityCode.DEFAULT, achFile.header.priorityCode);
		Assert.assertEquals(new RoutingNumber("076401251"), achFile.header.immediateDestination);
		Assert.assertEquals(new RoutingNumber("076401251"), achFile.header.immediateOrigin);
		Assert.assertEquals(new SimpleDate(8, 7, 25), achFile.header.fileCreationDate);
		Assert.assertEquals(new SimpleTime(16, 50), achFile.header.fileCreationTime);
		Assert.assertEquals(new FileIdModifier('0'), achFile.header.fileIdModifier);
		Assert.assertEquals(94, achFile.header.recordSize);
		Assert.assertEquals(10, achFile.header.blockingFactor);
		Assert.assertEquals(1, achFile.header.formatCode);
		Assert.assertEquals("FunZone", achFile.header.immediateDestinationName);
		Assert.assertEquals("FunZone", achFile.header.immediateOriginName);
		Assert.assertEquals("asD 1234", achFile.header.referenceCode);
		
		Assert.assertEquals(RecordType.BATCH_HEADER, achFile.batches.get(0).header.recordType);
		Assert.assertEquals(ServiceClassCode.ACH_ENTRIES_MIXED_DEBITS_CREDITS, achFile.batches.get(0).header.serviceClassCode);
		Assert.assertEquals("SookySoo", achFile.batches.get(0).header.companyName);
		Assert.assertEquals("OOpaOOpa", achFile.batches.get(0).header.companyDiscretionaryData);
		Assert.assertEquals("1dent1fy", achFile.batches.get(0).header.companyIdentification);
		Assert.assertEquals(StandardEntryClassCode.RCK, achFile.batches.get(0).header.standardEntryClassCode);
		Assert.assertEquals("SookySoo", achFile.batches.get(0).header.companyEntryDescription);
		Assert.assertEquals("ToDaY", achFile.batches.get(0).header.companyDescriptiveDate);
		Assert.assertEquals(new SimpleDate(8, 1, 1), achFile.batches.get(0).header.effectiveEntryDate);
		Assert.assertEquals("111", achFile.batches.get(0).header.settlementDate);
		Assert.assertEquals(OriginatorStatusCode.ADV_FROM_ACH_OPERATOR, achFile.batches.get(0).header.originatorStatusCode);
		Assert.assertEquals(new RoutingNumber("076401251"), achFile.batches.get(0).header.originatingDfiIdentification);
		Assert.assertEquals(1, achFile.batches.get(0).header.batchNumber);

		Assert.assertEquals(RecordType.ENTRY_DETAIL, ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).recordType);
		Assert.assertEquals(TransactionCode.CODE_21, ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).transactionCode);
		Assert.assertEquals(new RoutingNumber("076401251"), ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).receivingDfiIdentification);
		Assert.assertEquals(1, ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).checkDigit);
		Assert.assertEquals("00000000000000001", ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).dfiAccountNumber);
		Assert.assertEquals(1, ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).amount);
		Assert.assertEquals("000000000000001", ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).checkSerialNumber);
		Assert.assertEquals("John Doe", ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).individualName);
		Assert.assertEquals("DF", ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).discretionaryData);
		Assert.assertEquals(false, ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).addendaRecordIndicator);
		Assert.assertEquals(new TraceNumber(new RoutingNumber("076401251"), 1660936), ((RCKEntryFormat)achFile.batches.get(0).entries.get(0)).traceNumber);
		
		Assert.assertEquals(RecordType.BATCH_CONTROL, achFile.batches.get(0).control.recordType);
		Assert.assertEquals(ServiceClassCode.ACH_ENTRIES_MIXED_DEBITS_CREDITS, achFile.batches.get(0).control.serviceClassCode);
		Assert.assertEquals(0, achFile.batches.get(0).control.entryAddendaCount);
		Assert.assertEquals(0, achFile.batches.get(0).control.entryHash);
		Assert.assertEquals(0, achFile.batches.get(0).control.totalDebitEntryDollarAmount);
		Assert.assertEquals(0, achFile.batches.get(0).control.totalCreditEntryDollarAmount);
		Assert.assertEquals("1dent1fy", achFile.batches.get(0).control.companyIdentification);
		Assert.assertEquals("0000000000000000000", achFile.batches.get(0).control.messageAuthenticationCode);
		Assert.assertNull(achFile.batches.get(0).control.reserved);
		Assert.assertEquals(new RoutingNumber("076401251"), achFile.batches.get(0).control.originatingDfiIdentification);
		Assert.assertEquals(1, achFile.batches.get(0).control.batchNumber);
		
		Assert.assertEquals(RecordType.FILE_CONTROL, achFile.control.recordType);
		Assert.assertEquals(0, achFile.control.batchCount);
		Assert.assertEquals(1, achFile.control.blockCount);
		Assert.assertEquals(0, achFile.control.entryAddendaCount);
		Assert.assertEquals(1, achFile.control.entryHash);
		Assert.assertEquals(0, achFile.control.totalDebitEntryDollarAmountInFile);
		Assert.assertEquals(0, achFile.control.totalCreditEntryDollarAmountInFile);
		Assert.assertEquals(null, achFile.control.reserved);
		
		StringWriter out = new StringWriter();
		
		AchFileFormatWriter writer = new AchFileFormatWriter(achFile, out);
		writer.write();
		
		Assert.assertEquals(file, out.getBuffer().toString());
	}
}
