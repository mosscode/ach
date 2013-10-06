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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import com.moss.ach.file.AddendaTypeCode;
import com.moss.ach.file.RecordType;
import com.moss.ach.file.StandardEntryClassCode;

public class AchFileFormatReader {
	
	private final BufferedReader reader;
	private AchFileFormat achFile;
	private AchBatchFormat currentBatch;
	
	public AchFileFormatReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}
	
	public AchFileFormat read() throws AchFileFormatException, IOException {
		
		achFile = new AchFileFormat();
		
		String record;
		
		while ( (record = reader.readLine()) != null) {
			
			Reader recordReader = new StringReader(record);
			FieldReader fieldReader = new FieldReader(recordReader);
			
			RecordType recordType = fieldReader.read(RecordTypeField.class);
			
			if (RecordType.FILE_HEADER == recordType) {
				handleFileHeader(fieldReader);
			}
			else if (RecordType.BATCH_HEADER == recordType) {
				handleBatchHeader(fieldReader);
			}
			else if (RecordType.ENTRY_DETAIL == recordType) {
				handleEntryDetail(fieldReader);
			}
			else if (RecordType.ENTRY_ADDENDA == recordType) {
				handleEntryAddenda(fieldReader);
			}
			else if (RecordType.BATCH_CONTROL == recordType) {
				handleBatchControl(fieldReader);
			}
			else if (RecordType.FILE_CONTROL == recordType) {
				handleFileControl(fieldReader);
			}
			else {
				throw new AchFileFormatException("Unhandled record type: " + recordType);
			}
		}
		
		reader.close();
		
		if (currentBatch != null) {
			throw new AchFileFormatException("No terminating batch control record found in file for batch");
		}
		
		if (achFile.control == null) {
			throw new AchFileFormatException("No ach file control record found in file");
		}
		
		return achFile;
	}
	
	private void handleFileHeader(FieldReader reader) throws AchFileFormatException, IOException {
		
		if (achFile.header != null) {
			throw new AchFileFormatException("Found a second ach file header in the same file; only one is allowed.");
		}
		
		achFile.header = new AchFileHeaderFormat();
		achFile.header.recordType = RecordType.FILE_HEADER;
		achFile.header.priorityCode = reader.read(PriorityCodeField.class);
		achFile.header.immediateDestination = reader.read(ImmediateDestinationField.class);
		achFile.header.immediateOrigin = reader.read(ImmediateOriginField.class);
		achFile.header.fileCreationDate = reader.read(FileCreationDateField.class);
		achFile.header.fileCreationTime = reader.read(FileCreationTimeField.class);
		achFile.header.fileIdModifier = reader.read(FileIdModifierField.class);
		achFile.header.recordSize = reader.read(RecordSizeField.class);
		achFile.header.blockingFactor = reader.read(BlockingFactorField.class);
		achFile.header.formatCode = reader.read(FormatCodeField.class);
		achFile.header.immediateDestinationName = reader.read(ImmediateDestinationNameField.class);
		achFile.header.immediateOriginName = reader.read(ImmediateOriginNameField.class);
		achFile.header.referenceCode = reader.read(ReferenceCodeField.class);
		achFile.batches = new ArrayList<AchBatchFormat>();
	}
	
	private void handleBatchHeader(FieldReader reader) throws AchFileFormatException, IOException {
		
		if (currentBatch != null) {
			throw new AchFileFormatException("No terminating batch control record found in file for previous batch");
		}
		
		currentBatch = new AchBatchFormat();
		currentBatch.header = new AchBatchHeaderFormat();
		currentBatch.header.recordType = RecordType.BATCH_HEADER;
		currentBatch.header.serviceClassCode = reader.read(ServiceClassCodeField.class);
		currentBatch.header.companyName = reader.read(CompanyNameField.class);
		currentBatch.header.companyDiscretionaryData = reader.read(CompanyDiscretionaryDataField.class);
		currentBatch.header.companyIdentification = reader.read(CompanyIdentificationField.class);
		currentBatch.header.standardEntryClassCode = reader.read(StandardEntryClassCodeField.class);
		currentBatch.header.companyEntryDescription = reader.read(CompanyEntryDescriptionField.class);
		currentBatch.header.companyDescriptiveDate = reader.read(CompanyDescriptiveDateField.class);
		currentBatch.header.effectiveEntryDate = reader.read(EffectiveEntryDateField.class);
		currentBatch.header.settlementDate = reader.read(SettlementDateField.class);
		currentBatch.header.originatorStatusCode = reader.read(OriginatorStatusCodeField.class);
		currentBatch.header.originatingDfiIdentification = reader.read(OriginatingDfiIdentificationField.class);
		currentBatch.header.batchNumber = reader.read(BatchNumberField.class);
		currentBatch.entries = new ArrayList<AchEntryDetailFormat>();
	}
	
	private void handleEntryDetail(FieldReader reader) throws AchFileFormatException, IOException {
	
		if (currentBatch == null) {
			throw new AchFileFormatException("Found an entry detail record outside of a batch");
		}
		
		if (currentBatch.header.standardEntryClassCode == StandardEntryClassCode.RCK) {
			
			RCKEntryFormat entry = new RCKEntryFormat();
			entry.recordType = RecordType.ENTRY_DETAIL;
			entry.transactionCode = reader.read(TransactionCodeField.class);
			entry.receivingDfiIdentification = reader.read(ReceivingDfiIdentificationField.class);
			entry.checkDigit = reader.read(CheckDigitField.class);
			entry.dfiAccountNumber = reader.read(DfiAccountNumberField.class);
			entry.amount = reader.read(AmountField.class);
			entry.checkSerialNumber = reader.read(CheckSerialNumberField.class);
			entry.individualName = reader.read(IndividualNameField.class);
			entry.discretionaryData = reader.read(DiscretionaryDataField.class);
			entry.addendaRecordIndicator = reader.read(AddendaRecordIndicatorField.class);
			entry.traceNumber = reader.read(TraceNumberField.class);
			
			currentBatch.entries.add(entry);
		}
		else if (currentBatch.header.standardEntryClassCode == StandardEntryClassCode.PPD) {
			
			PPDEntryFormat entry = new PPDEntryFormat();
			entry.recordType = RecordType.ENTRY_DETAIL;
			entry.transactionCode = reader.read(TransactionCodeField.class);
			entry.receivingDfiIdentification = reader.read(ReceivingDfiIdentificationField.class);
			entry.checkDigit = reader.read(CheckDigitField.class);
			entry.dfiAccountNumber = reader.read(DfiAccountNumberField.class);
			entry.amount = reader.read(AmountField.class);
			entry.individualIdentificationNumber = reader.read(IndividualIdentificationNumber.class);
			entry.individualName = reader.read(IndividualNameField.class);
			entry.discretionaryData = reader.read(DiscretionaryDataField.class);
			entry.addendaRecordIndicator = reader.read(AddendaRecordIndicatorField.class);
			entry.traceNumber = reader.read(TraceNumberField.class);
			
			currentBatch.entries.add(entry);
		}
		else {
			throw new RuntimeException("Support for detail entries of standard entry class " + currentBatch.header.standardEntryClassCode + " has not been implemented");
		}
	}
	
	private void handleEntryAddenda(FieldReader reader) throws AchFileFormatException, IOException {
		
		if (currentBatch == null) {
			throw new AchFileFormatException("Found an entry addenda record outside of a batch");
		}
		
		if (currentBatch.entries.isEmpty()) {
			throw new AchFileFormatException("Found an entry addenda without a preceeding entry");
		}
		
		if (currentBatch.header.standardEntryClassCode == StandardEntryClassCode.PPD) {
			
			int entryIndex = currentBatch.entries.size() - 1;
			PPDEntryFormat entry = (PPDEntryFormat)currentBatch.entries.get(entryIndex);
		
			if (entry.addendaRecordIndicator) {

				entry.addenda = new PPDEntryAddendaFormat();
				entry.addenda.recordType = RecordType.ENTRY_ADDENDA;
				entry.addenda.addendaTypeCode = reader.read(AddendaTypeCodeField.class);
				entry.addenda.paymentRelatedInformation = reader.read(PaymentRelatedInformationField.class);
				entry.addenda.addendaSequenceNumber = reader.read(AddendaSequenceNumberField.class);
				entry.addenda.entryDetailSequenceNumber = reader.read(EntryDetailSequenceNumberField.class);
			}
			else {
				throw new AchFileFormatException("Found an addenda record but its corresponding entry record claims it has no addenda record.");
			}
		}
		else {
			throw new AchFileFormatException("Addenda records are not supported for standard entry class " + currentBatch.header.standardEntryClassCode); 
		}
	}
	
	private void handleBatchControl(FieldReader reader) throws AchFileFormatException, IOException {
		
		if (currentBatch == null) {
			throw new AchFileFormatException("Found a batch control record without preceeding batch");
		}
		
		if (currentBatch.control != null) {
			throw new AchFileFormatException("Found more than one batch control record for a single batch");
		}
		
		currentBatch.control = new AchBatchControlFormat();
		currentBatch.control.recordType = RecordType.BATCH_CONTROL;
		currentBatch.control.serviceClassCode = reader.read(ServiceClassCodeField.class);
		currentBatch.control.entryAddendaCount = reader.read(BatchControlEntryAddendaCountField.class);
		currentBatch.control.entryHash = reader.read(EntryHashField.class);
		currentBatch.control.totalDebitEntryDollarAmount = reader.read(TotalDebitEntryDollarAmountInFileField.class);
		currentBatch.control.totalCreditEntryDollarAmount = reader.read(TotalCreditEntryDollarAmountInFileField.class);
		currentBatch.control.companyIdentification = reader.read(CompanyIdentificationField.class);
		currentBatch.control.messageAuthenticationCode = reader.read(MessageAuthenticationCodeField.class);
		currentBatch.control.reserved = reader.read(BatchControlReservedField.class);
		currentBatch.control.originatingDfiIdentification = reader.read(OriginatingDfiIdentificationField.class);
		currentBatch.control.batchNumber = reader.read(BatchNumberField.class);
		
		achFile.batches.add(currentBatch);
		currentBatch = null;
	}
	
	private void handleFileControl(FieldReader reader) throws AchFileFormatException, IOException {
		
		if (achFile.control != null) {
			throw new AchFileFormatException("Found a second ach file control record in the same file; only one is allowed.");
		}
		
		achFile.control = new AchFileControlFormat();
		achFile.control.recordType = RecordType.FILE_CONTROL;
		achFile.control.batchCount = reader.read(BatchCountField.class);
		achFile.control.blockCount = reader.read(BlockCountField.class);
		achFile.control.entryAddendaCount = reader.read(FileControlEntryAddendaCountField.class);
		achFile.control.entryHash = reader.read(EntryHashField.class);
		achFile.control.totalDebitEntryDollarAmountInFile = reader.read(TotalDebitEntryDollarAmountInFileField.class);
		achFile.control.totalCreditEntryDollarAmountInFile = reader.read(TotalCreditEntryDollarAmountInFileField.class);
		achFile.control.reserved = reader.read(FileControlReservedField.class);
	}
}
