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

import java.io.IOException;
import java.io.Writer;


public class AchFileFormatWriter {

	private final FieldWriter writer;
	private final AchFileFormat file;

	public AchFileFormatWriter(AchFileFormat file, Writer writer) {
		this.file = file;
		this.writer = new FieldWriter(writer);
	}

	public void write() throws AchFileFormatException, IOException {

		writeFileHeader();

		for (AchBatchFormat batch : file.batches) {

			writeBatchHeader(batch.header);

			for (AchEntryDetailFormat entry : batch.entries) {

				writeDetailAddendaEntries(entry);
			}

			writeBatchControl(batch.control);
		}

		writeFileControl();

		writeTrail();

		writer.close();
	}

	private void writeFileHeader() throws AchFileFormatException, IOException {

		writer.write(RecordTypeField.class, file.header.recordType);
		writer.write(PriorityCodeField.class, file.header.priorityCode);
		writer.write(ImmediateDestinationField.class, file.header.immediateDestination);
		writer.write(ImmediateOriginField.class, file.header.immediateOrigin);
		writer.write(FileCreationDateField.class, file.header.fileCreationDate);
		writer.write(FileCreationTimeField.class, file.header.fileCreationTime);
		writer.write(FileIdModifierField.class, file.header.fileIdModifier);
		writer.write(RecordSizeField.class, file.header.recordSize);
		writer.write(BlockingFactorField.class, file.header.blockingFactor);
		writer.write(FormatCodeField.class, file.header.formatCode);
		writer.write(ImmediateDestinationNameField.class, file.header.immediateDestinationName);
		writer.write(ImmediateOriginNameField.class, file.header.immediateOriginName);
		writer.write(ReferenceCodeField.class, file.header.referenceCode);
	}

	private void writeBatchHeader(AchBatchHeaderFormat header) throws AchFileFormatException, IOException {

		writer.newRecord();

		writer.write(RecordTypeField.class, header.recordType);
		writer.write(ServiceClassCodeField.class, header.serviceClassCode);
		writer.write(CompanyNameField.class, header.companyName);
		writer.write(CompanyDiscretionaryDataField.class, header.companyDiscretionaryData);
		writer.write(CompanyIdentificationField.class, header.companyIdentification);
		writer.write(StandardEntryClassCodeField.class, header.standardEntryClassCode);
		writer.write(CompanyEntryDescriptionField.class, header.companyEntryDescription);
		writer.write(CompanyDescriptiveDateField.class, header.companyDescriptiveDate);
		writer.write(EffectiveEntryDateField.class, header.effectiveEntryDate);
		writer.write(SettlementDateField.class, header.settlementDate);
		writer.write(OriginatorStatusCodeField.class, header.originatorStatusCode);
		writer.write(OriginatingDfiIdentificationField.class, header.originatingDfiIdentification);
		writer.write(BatchNumberField.class, header.batchNumber);
	}

	private void writeDetailAddendaEntries(AchEntryDetailFormat e) throws AchFileFormatException, IOException {

		if (e instanceof RCKEntryFormat) {

			RCKEntryFormat entry = (RCKEntryFormat)e;

			writer.newRecord();

			writer.write(RecordTypeField.class, entry.recordType);
			writer.write(TransactionCodeField.class, entry.transactionCode);
			writer.write(ReceivingDfiIdentificationField.class, entry.receivingDfiIdentification);
			writer.write(CheckDigitField.class, entry.checkDigit);
			writer.write(DfiAccountNumberField.class, entry.dfiAccountNumber);
			writer.write(AmountField.class, entry.amount);
			writer.write(CheckSerialNumberField.class, entry.checkSerialNumber);
			writer.write(IndividualNameField.class, entry.individualName);
			writer.write(DiscretionaryDataField.class, entry.discretionaryData);
			writer.write(AddendaRecordIndicatorField.class, entry.addendaRecordIndicator);
			writer.write(TraceNumberField.class, entry.traceNumber);

		}
		else if (e instanceof PPDEntryFormat) {

			PPDEntryFormat entry = (PPDEntryFormat)e;

			writer.newRecord();

			writer.write(RecordTypeField.class, entry.recordType);
			writer.write(TransactionCodeField.class, entry.transactionCode);
			writer.write(ReceivingDfiIdentificationField.class, entry.receivingDfiIdentification);
			writer.write(CheckDigitField.class, entry.checkDigit);
			writer.write(DfiAccountNumberField.class, entry.dfiAccountNumber);
			writer.write(AmountField.class, entry.amount);
			writer.write(IndividualIdentificationNumber.class, entry.individualIdentificationNumber);
			writer.write(IndividualNameField.class, entry.individualName);
			writer.write(DiscretionaryDataField.class, entry.discretionaryData);
			writer.write(AddendaRecordIndicatorField.class, entry.addendaRecordIndicator);
			writer.write(TraceNumberField.class, entry.traceNumber);

			if (entry.addenda != null) {
				writer.newRecord();

				writer.write(RecordTypeField.class, entry.addenda.recordType);
				writer.write(AddendaTypeCodeField.class, entry.addenda.addendaTypeCode);
				writer.write(PaymentRelatedInformationField.class, entry.addenda.paymentRelatedInformation);
				writer.write(AddendaSequenceNumberField.class, entry.addenda.addendaSequenceNumber);
				writer.write(EntryDetailSequenceNumberField.class, entry.addenda.entryDetailSequenceNumber);
			}
		}
		else {
			throw new RuntimeException("Support for detail entries of standard entry class " + e.getClass().getName() + " has not been implemented");
		}
	}

	private void writeBatchControl(AchBatchControlFormat control) throws AchFileFormatException, IOException {

		writer.newRecord();

		writer.write(RecordTypeField.class, control.recordType);
		writer.write(ServiceClassCodeField.class, control.serviceClassCode);
		writer.write(BatchControlEntryAddendaCountField.class, control.entryAddendaCount);
		writer.write(EntryHashField.class, control.entryHash);
		writer.write(TotalDebitEntryDollarAmountInFileField.class, control.totalDebitEntryDollarAmount);
		writer.write(TotalCreditEntryDollarAmountInFileField.class, control.totalCreditEntryDollarAmount);
		writer.write(CompanyIdentificationField.class, control.companyIdentification);
		writer.write(MessageAuthenticationCodeField.class, control.messageAuthenticationCode);
		writer.write(BatchControlReservedField.class, control.reserved);
		writer.write(OriginatingDfiIdentificationField.class, control.originatingDfiIdentification);
		writer.write(BatchNumberField.class, control.batchNumber);
	}

	private void writeFileControl() throws AchFileFormatException, IOException {

		writer.newRecord();

		writer.write(RecordTypeField.class, file.control.recordType);
		writer.write(BatchCountField.class, file.control.batchCount);
		writer.write(BlockCountField.class, file.control.blockCount);
		writer.write(FileControlEntryAddendaCountField.class, file.control.entryAddendaCount);
		writer.write(EntryHashField.class, file.control.entryHash);
		writer.write(TotalDebitEntryDollarAmountInFileField.class, file.control.totalDebitEntryDollarAmountInFile);
		writer.write(TotalCreditEntryDollarAmountInFileField.class, file.control.totalCreditEntryDollarAmountInFile);
		writer.write(FileControlReservedField.class, file.control.reserved);
	}

    private void writeTrail() throws IOException, AchFileFormatException {
        int numberOfTrailingLines = 10 - (FieldWriter.lineCounter % 10);
        for (int i = 0; i < numberOfTrailingLines; i++) {
            writer.newRecord();
            writer.write(TrailField.class, TrailField.TRAIL_LINE);
        }
    }
}
