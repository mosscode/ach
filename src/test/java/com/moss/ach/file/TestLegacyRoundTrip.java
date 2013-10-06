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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moss.ach.file.AchBatch;
import com.moss.ach.file.AchFile;
import com.moss.ach.file.AchFileReader;
import com.moss.ach.file.AchFileWriter;
import com.moss.ach.file.FileIdModifier;
import com.moss.ach.file.OriginatorStatusCode;
import com.moss.ach.file.PPDEntry;
import com.moss.ach.file.RCKEntry;
import com.moss.ach.file.ServiceClassCode;
import com.moss.ach.file.SimpleDate;
import com.moss.ach.file.SimpleTime;
import com.moss.ach.file.StandardEntryClassCode;
import com.moss.ach.file.TraceNumber;
import com.moss.ach.file.TransactionCode;
import com.moss.usbanknumbers.RoutingNumber;

public class TestLegacyRoundTrip {
	
//	static {
//		BasicConfigurator.configure();
//	}
	
	private String originalRCKData, originalPPDDebitData;
	
	@Before
	public void before() throws Exception {
		
		originalRCKData = read("src/test/resources/com/moss/ach/file/rck.ach");
		originalPPDDebitData = read("src/test/resources/com/moss/ach/file/ppd-debit.ach");
	}
	
	private String read(String file) throws Exception {
		
		InputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024 * 10]; //10k buffer
		for(int numRead = in.read(buffer); numRead!=-1; numRead = in.read(buffer)){
			out.write(buffer, 0, numRead);
		}

		in.close();
		out.close();
		
		return new String(out.toByteArray());
	}

	/**
	 * Reads in the original file, writes it out again, then compares the 
	 * original file data to the written file data.
	 */
	@Test
	public void rckReadWrite() throws Exception {
		
		AchFile file = new AchFileReader(new StringReader(originalRCKData)).read();
		
		StringWriter writer = new StringWriter();
		new AchFileWriter(file, writer).write();
		
		
		
		Assert.assertEquals(originalRCKData, writer.getBuffer().toString());
	}
	
	/**
	 * Replicates the data from the original file, writes it out, and compares it to 
	 * the original file data.
	 */
	@Test
	public void rckWriteRead() throws Exception {
		
		AchFile file = new AchFile();
		
		file.creationDate = new SimpleDate(8, 7, 29);
		file.creationTime = new SimpleTime(15, 33);
		file.fileIdModifier = new FileIdModifier('A');
		file.immediateDestination = new RoutingNumber("076401251");
		file.immediateDestinationName = "achdestname";
		file.immediateOrigin = new RoutingNumber("076401251");
		file.immediateOriginName = "companyname";
		file.referenceCode = null;
		
		AchBatch<RCKEntry> batch = new AchBatch<RCKEntry>();
		
		batch.companyDescriptiveDate = "000003";
		batch.companyDiscretionaryData = null;
		batch.companyEntryDescription = "REDEPCHECK";
		batch.companyIdentification = "origid";
		batch.companyName = "companyname";
		batch.effectiveEntryDate = new SimpleDate(8, 7, 30);
		batch.originatingDfiIdentification = new RoutingNumber("076401251");
		batch.originatorStatusCode = OriginatorStatusCode.AGENT_SUBJECT_TO_RULES;
		batch.serviceClassCode = ServiceClassCode.ACH_DEBITS_ONLY;
		batch.settlementDate = null;
		batch.standardEntryClassCode = StandardEntryClassCode.RCK;
		file.addBatch(batch);
		
		RCKEntry entry = new RCKEntry();
		
		entry.amount = 11500;
		entry.checkSerialNumber = "sadf";
		entry.dfiAccountNumber = "12345";
		entry.discretionaryData = "DD";
		entry.individualName = "Bachman Eric";
		entry.receivingDfiIdentification = new RoutingNumber("053200019");
		entry.traceNumber = new TraceNumber(new RoutingNumber("076401251"), 1660936);
		entry.transactionCode = TransactionCode.CODE_27;
		batch.addEntry(entry);
		
		StringWriter writer = new StringWriter();
		new AchFileWriter(file, writer).write();
		
		Assert.assertEquals(originalRCKData, writer.getBuffer().toString());
	}
	
	/**
	 * Reads in the original file, writes it out again, then compares the 
	 * original file data to the written file data.
	 */
	@Test
	public void ppdReadWrite() throws Exception {
		
		AchFile file = new AchFileReader(new StringReader(originalPPDDebitData)).read();
		
		StringWriter writer = new StringWriter();
		new AchFileWriter(file, writer).write();
		
		Assert.assertEquals(originalPPDDebitData, writer.getBuffer().toString());
	}
	
	/**
	 * Replicates the data from the original file, writes it out, and compares it to 
	 * the original file data.
	 */
	@Test
	public void ppdWriteRead() throws Exception {
		
		AchFile file = new AchFile();
		
		file.creationDate = new SimpleDate(8, 7, 29);
		file.creationTime = new SimpleTime(15, 11);
		file.fileIdModifier = new FileIdModifier('A');
		file.immediateDestination = new RoutingNumber("076401251");
		file.immediateDestinationName = "achdestname";
		file.immediateOrigin = new RoutingNumber("076401251");
		file.immediateOriginName = "companyname";
		file.referenceCode = null;
		
		AchBatch<PPDEntry> batch = new AchBatch<PPDEntry>();
		
		batch.companyDescriptiveDate = "000002";
		batch.companyDiscretionaryData = null;
		batch.companyEntryDescription = "CHECKPAYMT";
		batch.companyIdentification = "origid";
		batch.companyName = "companyname";
		batch.effectiveEntryDate = new SimpleDate(8, 7, 30);
		batch.originatingDfiIdentification = new RoutingNumber("076401251");
		batch.originatorStatusCode = OriginatorStatusCode.AGENT_SUBJECT_TO_RULES;
		batch.serviceClassCode = ServiceClassCode.ACH_DEBITS_ONLY;
		batch.settlementDate = null;
		batch.standardEntryClassCode = StandardEntryClassCode.PPD;
		file.addBatch(batch);
		
		PPDEntry entry = new PPDEntry();
		
		entry.amount = 10500;
		entry.individualIdentificationNumber = "c-1";
		entry.dfiAccountNumber = "12345";
		entry.discretionaryData = "DD";
		entry.individualName = "Bachman Eric";
		entry.receivingDfiIdentification = new RoutingNumber("053200019");
		entry.traceNumber = new TraceNumber(new RoutingNumber("076401251"), 5655291);
		entry.transactionCode = TransactionCode.CODE_27;
		batch.addEntry(entry);
		
		StringWriter writer = new StringWriter();
		new AchFileWriter(file, writer).write();
		
		Assert.assertEquals(originalPPDDebitData, writer.getBuffer().toString());
	}
}
