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
import java.io.Reader;

import com.moss.ach.file.format.AchBatchFormat;
import com.moss.ach.file.format.AchEntryDetailFormat;
import com.moss.ach.file.format.AchFileFormat;
import com.moss.ach.file.format.AchFileFormatException;
import com.moss.ach.file.format.AchFileFormatReader;
import com.moss.ach.file.format.PPDEntryFormat;
import com.moss.ach.file.format.RCKEntryFormat;

/**
 * TODO: 
 * 
 * There is currently no support for multiple batch/transaction types.
 * Only {@link StandardEntryClassCode#RCK}
 * 
 * Also, the value types could use some massaging.
 */
public class AchFileReader {
	
	private final AchFileFormatReader reader;
	
	public AchFileReader(Reader reader) {
		this.reader = new AchFileFormatReader(reader);
	}
	
	public AchFile read() throws AchFileFormatException, IOException {
		
		AchFileFormat format = reader.read();
		AchFile file = new AchFile();
		
		file.setCreationDate(format.header.fileCreationDate);
		file.setCreationTime(format.header.fileCreationTime);
		file.setFileIdModifier(format.header.fileIdModifier);
		file.setImmediateDestination(format.header.immediateDestination);
		file.setImmediateDestinationName(format.header.immediateDestinationName);
		file.setImmediateOrigin(format.header.immediateOrigin);
		file.setImmediateOriginName(format.header.immediateOriginName);
		file.setReferenceCode(format.header.referenceCode);
		
		for (AchBatchFormat batchFormat : format.batches) {
			
			if (StandardEntryClassCode.RCK == batchFormat.header.standardEntryClassCode) {
				
				AchBatch<RCKEntry> batch = new AchBatch<RCKEntry>();
				
				batch.setCompanyDiscretionaryData(batchFormat.header.companyDiscretionaryData);
				batch.setCompanyEntryDescription(batchFormat.header.companyEntryDescription);
				batch.setCompanyIdentification(batchFormat.header.companyIdentification);
				batch.setCompanyName(batchFormat.header.companyName);
				batch.setCompanyDescriptiveDate(batchFormat.header.companyDescriptiveDate);
				batch.setEffectiveEntryDate(batchFormat.header.effectiveEntryDate);
				batch.setOriginatingDfiIdentification(batchFormat.header.originatingDfiIdentification);
				batch.setOriginatorStatusCode(batchFormat.header.originatorStatusCode);
				batch.setServiceClassCode(batchFormat.header.serviceClassCode);
				batch.setSettlementDate(batchFormat.header.settlementDate);
				batch.setStandardEntryClassCode(batchFormat.header.standardEntryClassCode);
				
				for (AchEntryDetailFormat ef : batchFormat.entries) {
					
					RCKEntryFormat entryFormat = (RCKEntryFormat)ef;
					RCKEntry entry = new RCKEntry();
					
					entry.setAmount(entryFormat.amount);
					entry.setCheckSerialNumber(entryFormat.checkSerialNumber);
					entry.setDfiAccountNumber(entryFormat.dfiAccountNumber);
					entry.setDiscretionaryData(entryFormat.discretionaryData);
					entry.setIndividualName(entryFormat.individualName);
					entry.setReceivingDfiIdentification(entryFormat.receivingDfiIdentification);
					entry.setTraceNumber(entryFormat.traceNumber);
					entry.setTransactionCode(entryFormat.transactionCode);
					
					batch.addEntry(entry);
				}

				file.addBatch(batch);
			}
			else if (StandardEntryClassCode.PPD == batchFormat.header.standardEntryClassCode) {
				
				AchBatch<PPDEntry> batch = new AchBatch<PPDEntry>();
				
				batch.setCompanyDiscretionaryData(batchFormat.header.companyDiscretionaryData);
				batch.setCompanyEntryDescription(batchFormat.header.companyEntryDescription);
				batch.setCompanyIdentification(batchFormat.header.companyIdentification);
				batch.setCompanyName(batchFormat.header.companyName);
				batch.setCompanyDescriptiveDate(batchFormat.header.companyDescriptiveDate);
				batch.setEffectiveEntryDate(batchFormat.header.effectiveEntryDate);
				batch.setOriginatingDfiIdentification(batchFormat.header.originatingDfiIdentification);
				batch.setOriginatorStatusCode(batchFormat.header.originatorStatusCode);
				batch.setServiceClassCode(batchFormat.header.serviceClassCode);
				batch.setSettlementDate(batchFormat.header.settlementDate);
				batch.setStandardEntryClassCode(batchFormat.header.standardEntryClassCode);
				
				for (AchEntryDetailFormat ef : batchFormat.entries) {
					
					PPDEntryFormat entryFormat = (PPDEntryFormat)ef;
					PPDEntry entry = new PPDEntry();
					
					entry.setAmount(entryFormat.amount);
					entry.setDfiAccountNumber(entryFormat.dfiAccountNumber);
					entry.setDiscretionaryData(entryFormat.discretionaryData);
					entry.setIndividualName(entryFormat.individualName);
					entry.setIndividualIdentificationNumber(entryFormat.individualIdentificationNumber);
					entry.setReceivingDfiIdentification(entryFormat.receivingDfiIdentification);
					entry.setTraceNumber(entryFormat.traceNumber);
					entry.setTransactionCode(entryFormat.transactionCode);
					
					if (entryFormat.addenda != null) {
						entry.addenda = new PPDEntryAddenda();
						entry.addenda.paymentRelatedInformation = entryFormat.addenda.paymentRelatedInformation;
					}
					
					batch.addEntry(entry);
				}

				file.addBatch(batch);
			}
			else {
				throw new RuntimeException("Unsupported batch standard entry class code: " + batchFormat.header.standardEntryClassCode);
			}
		}
		
		return file;
	}
}
