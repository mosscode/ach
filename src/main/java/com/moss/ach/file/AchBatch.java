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

import java.util.ArrayList;
import java.util.List;

import com.moss.usbanknumbers.RoutingNumber;

public class AchBatch<T extends AchEntry> {
	
	String companyDescriptiveDate;
	String companyDiscretionaryData;
	String companyEntryDescription;
	String companyIdentification;
	String companyName;
	SimpleDate effectiveEntryDate;
	RoutingNumber originatingDfiIdentification;
	OriginatorStatusCode originatorStatusCode;
	ServiceClassCode serviceClassCode;
	String settlementDate;
	StandardEntryClassCode standardEntryClassCode;

	List<T> entries = new ArrayList<T>();

	public String getCompanyDescriptiveDate() {
		return companyDescriptiveDate;
	}

	public void setCompanyDescriptiveDate(String descriptiveDate) {
		this.companyDescriptiveDate = descriptiveDate;
	}

	public String getCompanyDiscretionaryData() {
		return companyDiscretionaryData;
	}

	public void setCompanyDiscretionaryData(String companyDiscretionaryData) {
		this.companyDiscretionaryData = companyDiscretionaryData;
	}

	public String getCompanyEntryDescription() {
		return companyEntryDescription;
	}

	public void setCompanyEntryDescription(String companyEntryDescription) {
		this.companyEntryDescription = companyEntryDescription;
	}

	public String getCompanyIdentification() {
		return companyIdentification;
	}

	public void setCompanyIdentification(String companyIdentification) {
		this.companyIdentification = companyIdentification;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public SimpleDate getEffectiveEntryDate() {
		return effectiveEntryDate;
	}

	public void setEffectiveEntryDate(SimpleDate effectiveEntryDate) {
		this.effectiveEntryDate = effectiveEntryDate;
	}

	public RoutingNumber getOriginatingDfiIdentification() {
		return originatingDfiIdentification;
	}

	public void setOriginatingDfiIdentification(
			RoutingNumber originatingDfiIdentification) {
		this.originatingDfiIdentification = originatingDfiIdentification;
	}

	public OriginatorStatusCode getOriginatorStatusCode() {
		return originatorStatusCode;
	}

	public void setOriginatorStatusCode(OriginatorStatusCode originatorStatusCode) {
		this.originatorStatusCode = originatorStatusCode;
	}

	public ServiceClassCode getServiceClassCode() {
		return serviceClassCode;
	}

	public void setServiceClassCode(ServiceClassCode serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public StandardEntryClassCode getStandardEntryClassCode() {
		return standardEntryClassCode;
	}

	public void setStandardEntryClassCode(StandardEntryClassCode standardEntryClassCode) {
		this.standardEntryClassCode = standardEntryClassCode;
	}

	public List<T> getEntries() {
		return entries;
	}

	public void setEntries(List<T> entries) {
		this.entries = entries;
	}
	
	public void addEntry(T entry) {
		entries.add(entry);
	}
}
