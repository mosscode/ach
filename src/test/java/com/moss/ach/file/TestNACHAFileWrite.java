package com.moss.ach.file;

import com.moss.usbanknumbers.RoutingNumber;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class TestNACHAFileWrite {

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
        entry.addenda = new PPDEntryAddenda();
        entry.addenda.paymentRelatedInformation = "as;ljasdl;kjasdl;jasdf;";

        batch.addEntry(entry);

        StringWriter writer = new StringWriter();
        new AchFileWriter(file, writer).write();

        FileWriter fw = new FileWriter("src/test/resources/com/moss/ach/file/ach_sample.ach");
        StringWriter sw = new StringWriter();
        sw.write(writer.getBuffer().toString());
        fw.write(sw.toString());
        fw.close();

        AchFileReader reader = new AchFileReader(new StringReader(writer.getBuffer().toString()));
        AchFile readFile = reader.read();

        PPDEntry e = (PPDEntry) readFile.getBatches().get(0).getEntries().get(0);

        Assert.assertEquals(entry.addenda.paymentRelatedInformation, e.addenda.paymentRelatedInformation);
    }
}
