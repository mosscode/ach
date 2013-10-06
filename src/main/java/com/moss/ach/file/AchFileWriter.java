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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.moss.ach.file.format.AchBatchControlFormat;
import com.moss.ach.file.format.AchBatchFormat;
import com.moss.ach.file.format.AchBatchHeaderFormat;
import com.moss.ach.file.format.AchEntryDetailFormat;
import com.moss.ach.file.format.AchFileControlFormat;
import com.moss.ach.file.format.AchFileFormat;
import com.moss.ach.file.format.AchFileFormatException;
import com.moss.ach.file.format.AchFileFormatWriter;
import com.moss.ach.file.format.AchFileHeaderFormat;
import com.moss.ach.file.format.PPDEntryAddendaFormat;
import com.moss.ach.file.format.PPDEntryFormat;
import com.moss.ach.file.format.RCKEntryFormat;

/**
 * TODO: There is currently no support for multiple batch/transaction types.
 * Only {@link StandardEntryClassCode#RCK}
 * 
 * Also, the value types could use some massaging.
 */
public class AchFileWriter {
	
	private static final int BLOCKING_FACTOR = 10;
	private static final int FORMAT_CODE = 1;
	private static final PriorityCode PRIORITY_CODE = PriorityCode.DEFAULT;
	private static final int RECORD_SIZE = 94;

	private final Writer writer;
	private final AchFile file;
	
	public AchFileWriter(AchFile file, Writer writer) {
		this.file = file;
		this.writer = writer;
	}
	
	public void write() throws AchFileFormatException, IOException {
		
		long totalRecordsInFile = 0;

		AchFileFormat format = new AchFileFormat();
		
		format.header = new AchFileHeaderFormat();
		format.header.fileCreationDate = file.getCreationDate();
		format.header.fileCreationTime = file.getCreationTime();
		format.header.fileIdModifier = file.getFileIdModifier();
		format.header.immediateDestination = file.getImmediateDestination();
		format.header.immediateDestinationName = file.getImmediateDestinationName();
		format.header.immediateOrigin = file.getImmediateOrigin();
		format.header.immediateOriginName = file.getImmediateOriginName();
		format.header.referenceCode = file.getReferenceCode();
		/*
		 * automatically assigned
		 */
		format.header.blockingFactor = BLOCKING_FACTOR;
		format.header.formatCode = FORMAT_CODE;
		format.header.priorityCode = PRIORITY_CODE;
		format.header.recordSize = RECORD_SIZE;
		format.header.recordType = RecordType.FILE_HEADER;
		totalRecordsInFile++;
		
		long formatEntryAddendaCount = 0;
		long formatEntryHashSum = 0;
		long totalDebitEntryDollarAmountInFile = 0;
		long totalCreditEntryDollarAmountInFile = 0;
		
		format.batches = new ArrayList<AchBatchFormat>();
		for (AchBatch<?> batch : file.getBatches()) {
			
			AchBatchFormat batchFormat = new AchBatchFormat();
			format.batches.add(batchFormat);
			
			batchFormat.header = new AchBatchHeaderFormat();
			batchFormat.header.companyDescriptiveDate = batch.getCompanyDescriptiveDate();
			batchFormat.header.companyDiscretionaryData = batch.getCompanyDiscretionaryData();
			batchFormat.header.companyEntryDescription = batch.getCompanyEntryDescription();
			batchFormat.header.companyIdentification = batch.getCompanyIdentification();
			batchFormat.header.companyName = batch.getCompanyName();
			batchFormat.header.effectiveEntryDate = batch.getEffectiveEntryDate();
			batchFormat.header.originatingDfiIdentification = batch.getOriginatingDfiIdentification();
			batchFormat.header.originatorStatusCode = batch.getOriginatorStatusCode();
			batchFormat.header.serviceClassCode = batch.getServiceClassCode();
			batchFormat.header.settlementDate = batch.getSettlementDate();
			batchFormat.header.standardEntryClassCode = batch.getStandardEntryClassCode();
			batchFormat.header.recordType = RecordType.BATCH_HEADER;
			batchFormat.header.batchNumber = format.batches.size();
			batchFormat.entries = new ArrayList<AchEntryDetailFormat>();
			totalRecordsInFile++;
			
			long entryAddendaCount = 0;
			long totalCreditEntryDollarAmount = 0;
			long totalDebitEntryDollarAmount = 0;
			long entryHashSum = 0;
			
			for (AchEntry e : batch.getEntries()) {
				
				if (e instanceof RCKEntry) {
					
					RCKEntry entry = (RCKEntry)e;
					RCKEntryFormat entryFormat = new RCKEntryFormat();
					
					entryFormat.amount = entry.getAmount();
					entryFormat.receivingDfiIdentification = entry.getReceivingDfiIdentification();
					entryFormat.checkSerialNumber = entry.getCheckSerialNumber();
					entryFormat.dfiAccountNumber = entry.dfiAccountNumber;
					entryFormat.discretionaryData = entry.getDiscretionaryData();
					entryFormat.individualName = entry.getIndividualName();
					entryFormat.traceNumber = entry.getTraceNumber();
					entryFormat.transactionCode = entry.getTransactionCode();
					
					if (entry.getReceivingDfiIdentification() == null) {
						throw new AchFileFormatException("The RCKEntry.receivingDfiIdentification field cannot be empty.");
					}
					entryFormat.checkDigit = entry.getReceivingDfiIdentification().checkDigit();
					entryFormat.recordType = RecordType.ENTRY_DETAIL;
					entryFormat.addendaRecordIndicator = false;
					
					batchFormat.entries.add(entryFormat);
					
					entryAddendaCount++;
					entryHashSum += entry.getReceivingDfiIdentification().dfiIdentity();
					totalDebitEntryDollarAmount += entry.getAmount();
					
					totalRecordsInFile++;
				}
				else if (e instanceof PPDEntry) {
					
					PPDEntry entry = (PPDEntry)e;
					PPDEntryFormat entryFormat = new PPDEntryFormat();
					
					entryFormat.amount = entry.getAmount();
					entryFormat.receivingDfiIdentification = entry.getReceivingDfiIdentification();
					entryFormat.individualIdentificationNumber = entry.getIndividualIdentificationNumber();
					entryFormat.dfiAccountNumber = entry.dfiAccountNumber;
					entryFormat.discretionaryData = entry.getDiscretionaryData();
					entryFormat.individualName = entry.getIndividualName();
					entryFormat.traceNumber = entry.getTraceNumber();
					entryFormat.transactionCode = entry.getTransactionCode();
					
					if (entry.getReceivingDfiIdentification() == null) {
						throw new AchFileFormatException("The PPDEntry.receivingDfiIdentification field cannot be empty.");
					}
					entryFormat.checkDigit = entry.getReceivingDfiIdentification().checkDigit();
					entryFormat.recordType = RecordType.ENTRY_DETAIL;
					
					if (entry.getAddenda() != null) {
						
						entryFormat.addendaRecordIndicator = true;
						
						entryFormat.addenda = new PPDEntryAddendaFormat();
						entryFormat.addenda.recordType = RecordType.ENTRY_ADDENDA;
						entryFormat.addenda.addendaTypeCode = AddendaTypeCode.ADDENDA;
						entryFormat.addenda.paymentRelatedInformation = entry.getAddenda().getPaymentRelatedInformation();
						entryFormat.addenda.entryDetailSequenceNumber = entryFormat.traceNumber.getEntryDetailSequenceNumber();
						
						/*
						 * TODO: Not sure how this sequence number is to be assigned.
						 */
						entryFormat.addenda.addendaSequenceNumber = 1;
						
						entryAddendaCount++;
					}
					else {
						entryFormat.addendaRecordIndicator = false;
					}
					
					batchFormat.entries.add(entryFormat);
					
					entryAddendaCount++;
					entryHashSum += entry.getReceivingDfiIdentification().dfiIdentity();
					totalDebitEntryDollarAmount += entry.getAmount();
					
					totalRecordsInFile++;
				}
				else {
					throw new RuntimeException("Unsupported entry type: " + e);
				}
			}
			
			batchFormat.control = new AchBatchControlFormat();
			batchFormat.control.batchNumber = format.batches.size();
			batchFormat.control.entryAddendaCount = entryAddendaCount;
			batchFormat.control.entryHash = truncateHash(entryHashSum);
			batchFormat.control.messageAuthenticationCode = null;
			batchFormat.control.totalCreditEntryDollarAmount = totalCreditEntryDollarAmount;
			batchFormat.control.totalDebitEntryDollarAmount = totalDebitEntryDollarAmount;
			batchFormat.control.reserved = null;
			batchFormat.control.serviceClassCode = ServiceClassCode.ACH_DEBITS_ONLY;
			batchFormat.control.companyIdentification = batch.getCompanyIdentification();;
			batchFormat.control.originatingDfiIdentification = batch.getOriginatingDfiIdentification();
			batchFormat.control.recordType = RecordType.BATCH_CONTROL;
			
			formatEntryAddendaCount += entryAddendaCount;
			formatEntryHashSum += entryHashSum;
			totalCreditEntryDollarAmountInFile += totalCreditEntryDollarAmount;
			totalDebitEntryDollarAmountInFile += totalDebitEntryDollarAmount;
			
			totalRecordsInFile++;
		}
		
		format.control = new AchFileControlFormat();
		format.control.batchCount = format.batches.size();
		format.control.entryAddendaCount = formatEntryAddendaCount;
		format.control.entryHash = truncateHash(formatEntryHashSum);
		format.control.recordType = RecordType.FILE_CONTROL;
		format.control.reserved = null;
		format.control.totalCreditEntryDollarAmountInFile = totalCreditEntryDollarAmountInFile;
		format.control.totalDebitEntryDollarAmountInFile = totalDebitEntryDollarAmountInFile;
		totalRecordsInFile++;
		
		long blockCount;
		
		if (totalRecordsInFile % 10 != 0) {
			blockCount = (totalRecordsInFile / 10) + 1;
		}
		else {
			blockCount = totalRecordsInFile / 10;
		}
		
		format.control.blockCount = blockCount;
		
		AchFileFormatWriter formatWriter = new AchFileFormatWriter(format, writer);
		formatWriter.write();
	}
	
	private long truncateHash(long hashSum) {
		
		String entryHashString = Long.toString(hashSum);

		if (entryHashString.length() > 8) {
			int index = entryHashString.length() - 8;
			entryHashString = entryHashString.substring(index);
		}

		return Long.parseLong(entryHashString);
	}
}
