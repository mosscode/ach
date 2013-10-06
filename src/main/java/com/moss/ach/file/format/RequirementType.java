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

/**
 * The following documentation and values define the need for inclusion of 
 * certain data fields in ACH entries. This involves the standardization 
 * of three definitions: Mandatory, Required, and Optional.
 */
enum RequirementType {
	
	/**
	 * Mandatory for ACH Processing - A "Mandatory" field is neccessary to 
	 * ensure the proper routing and/or posting of an ACH entry. Any 
	 * "Mandatory" field not included in an ACH record will cause that entry, 
	 * batch, or file to be rejected by the ACH Operator. A rejected entry will 
	 * be returned to the ODFI by the ACH operator. A rejected batch or 
	 * rejected file will be reported to the ODFI or Sending Point by tthe ACH 
	 * Operator.
	 */
	MANDATORY,
	
	/**
	 * Required - The omission of a"Required" field will not cause an entry 
	 * reject at the ACH Operator, but may cause a reject at the RDFI. An 
	 * example is the DFI Account Number field in the Entry DetailRecord. If 
	 * this field is omitted by an ODFI, the RDFI may return the entry as 
	 * nonpostable. Data classified as "Required" shoudlbe included by the 
	 * Originator and ODFI to aviod processing and control problems at the 
	 * RDFI.
	 */
	REQUIRED,
	
	/**
	 * Optional - The inclusion of omission of an "Optional" data field is 
	 * at the discretion of the Originator and ODFI. However, if a DFI does 
	 * originate files using optional data fields, these fields must be 
	 * returned to the ODFI if the entry is returned. 
	 */
	OPTIONAL
}
