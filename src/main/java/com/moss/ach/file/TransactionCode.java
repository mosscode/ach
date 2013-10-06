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

/**
 * Transaction Codes have been defined to identify various types of debit and
 * credit entries. POS entries will utilize existing debit/credit Transaction
 * Codes. 
 */
public enum TransactionCode {
	
	/*
	 * Demand Credit Records (for checking, NOW, and share draft accounts).
	 */

	/**
	 * Reserved.
	 */
	RESERVED_20("20"),
	
	/**
	 * Automated Return or Notification of Change for original transaction 
	 * codes {@link #CODE_22}, {@link #CODE_23}, or {@link #CODE_24}.
	 */
	CODE_21("21"),
	
	/**
	 * Automated Deposit
	 */
	CODE_22("22"),
	
	/**
	 * Prenotification of Demand Credit Authorization; Death Notification 
	 * (non-dollar); Automated Enrollment Entry (non-dollar).
	 */
	CODE_23("23"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only); Acknowledgement 
	 * Entries ({@link StandardEntryClassCode#ACK} and 
	 * {@link StandardEntryClassCode#ATX} entries only).
	 */
	CODE_24("24"),
	
	/*
	 * Demand Debit Records (for checking, NOW, and share draft accounts).
	 */
	
	/**
	 * Reserved.
	 */
	RESERVED_25("25"),
	
	/**
	 * Automated Return or Notification of Change for original transaction code 
	 * {@link #CODE_27}, {@link #CODE_28}, or {@link #CODE_29}.
	 */
	CODE_26("26"),
	
	/**
	 * Automated Payment
	 */
	CODE_27("27"),
	
	/**
	 * Prenotification of Demand Debit Authorization (non-dollar).
	 */
	CODE_28("28"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only).
	 */
	CODE_29("29"),
	
	/*
	 * Savings Account Credit Records
	 */
	
	/**
	 * Reserved.
	 */
	RESERVED_30("30"),
	
	/**
	 * Automated Return or Notification of Change for original transaction 
	 * code {@link #CODE_32}, {@link #CODE_33} or {@link #CODE_34}.
	 */
	CODE_31("31"),
	
	/**
	 * Automated Deposit
	 */
	CODE_32("32"),
	
	/**
	 * Prenotification of Savings Credit Authorization; Death Notification 
	 * (non-dollar); Automated Enrollment Entry (non-dollar).
	 */
	CODE_33("33"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only); Acknowledgement 
	 * Entries ({@link StandardEntryClassCode#ACK} and 
	 * {@link StandardEntryClassCode#ATX} entries only).
	 */
	CODE_34("34"),
	
	/*
	 * Savings Account Debit Records
	 */
	
	/**
	 * Reserved.
	 */
	RESERVED_35("35"),
	
	/**
	 * Automated Return or Notification of Change for original transaction 
	 * code {@link #CODE_37}, {@link #CODE_38} or {@link #CODE_39}.
	 */
	CODE_36("36"),
	
	/**
	 * Automated Payment
	 */
	CODE_37("37"),
	
	/**
	 * Prenotification of Savings Debit Authorization (non-dollar)
	 */
	CODE_38("38"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only).
	 */
	CODE_39("39"),
	
	/*
	 * Financial Institution General Ledger Credit Records
	 */
	
	/**
	 * Automated Return or Notification of Change for originatl transaction 
	 * {@link #CODE_42}, {@link #CODE_43}, or {@link #CODE_44};
	 */
	CODE_41("41"),
	
	/**
	 * Automated General Ledger Deposit (Credit).
	 */
	CODE_42("42"),
	
	/**
	 * Prenotification of General Ledger Credit Authorization (non-dollar).
	 */
	CODE_43("43"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only).
	 */
	CODE_44("44"),
	
	/*
	 * Financial Institution General Ledger Debit Records
	 */

	/**
	 * Automated Return or Notification of Change for original transaction 
	 * {@link #CODE_47}, {@link #CODE_48} or {@link #CODE_49}.
	 */
	CODE_46("46"),
	
	/**
	 * Automated General Ledger Payment (Debit).
	 */
	CODE_47("47"),
	
	/**
	 * Prenotification of General Ledger Debit Authorization (non-dollar).
	 */
	CODE_48("48"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only).
	 */
	CODE_49("49"),
	
	/*
	 * Loan Account Credit Records
	 */
	
	/**
	 * Automated Return or Notification of Change for original transaction 
	 * {@link #CODE_52}, {@link #CODE_53}, or {@link #CODE_54}.
	 */
	CODE_51("51"),
	
	/**
	 * Automated Loan Account Deposit (Credit).
	 */
	CODE_52("52"),
	
	/**
	 * Prenotification of Loan Account Credit Authorization (non-dollar).
	 */
	CODE_53("53"),
	
	/**
	 * Zero dollar with remittance data (for {@link StandardEntryClassCode#CCD}
	 * and {@link StandardEntryClassCode#CTX} entries only).
	 */
	CODE_54("54"),
	
	/*
	 * Loan Account Debit Records (for Reversals Only)
	 */
	
	/**
	 * Automated Loan Account Debit (Reversals Only).
	 */
	CODE_55("55"),
	
	/**
	 * Automated Return or Notification of Change for original transaction 
	 * {@link #CODE_55}.
	 */
	CODE_56("56"),

	/*
	 * Automated Accounting Records (for use in ADV files only).
	 */
	
	/**
	 * Credit for ACH debits originated.
	 */
	CODE_81("81"),
	
	/**
	 * Debit for ACH debits originated.
	 */
	CODE_82("82"),
	
	/**
	 * Credit for ACH credits received.
	 */
	CODE_83("83"),
	
	/**
	 * Debit for ACH debits received.
	 */
	CODE_84("84"),
	
	/**
	 * Credit for ACH credits in rejected balances.
	 */
	CODE_85("85"),
	
	/**
	 * Debit for ACH debits in rejected balances.
	 */
	CODE_86("86"),
	
	/**
	 * Summary credit for respondent ACH activity.
	 */
	CODE_87("87"),
	
	/**
	 * Summary debit for respondent ACH activity. 
	 */
	CODE_88("88");
	
	private final String value;

	private TransactionCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
