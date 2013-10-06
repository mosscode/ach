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
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FieldReader {
	
	private final Log log = LogFactory.getLog(this.getClass());
	private final Reader reader;
	
	public FieldReader(Reader reader) {
		this.reader = reader;
	}

	public <T> T read(Class<? extends Field<T>> fieldClass) throws AchFileFormatException, IOException {
		
		try {
			Field<T> field = fieldClass.newInstance();
			String fieldName = FieldUtil.getName(field);
			
			char[] unparsedValue = new char[field.getLength()];
			for (int i=0; i<field.getLength(); i++) {
				
				int readChar = reader.read();
				
				if (readChar == -1) {
					throw new AchFileFormatException("Unexpected EOF found while reading field " + fieldName);
				}
				
				unparsedValue[i] = (char)readChar;
			}
			
			String value = new String(unparsedValue);

			if (value.trim().equals("")) {

				if (RequirementType.MANDATORY == field.getRequirementType()) {
					throw new AchFileFormatException("Mandatory field '" + fieldName + "' is empty.");
				}
				else if (RequirementType.REQUIRED == field.getRequirementType()) {
					if (log.isWarnEnabled()) {
						log.warn("Required field '" + fieldName + "' is empty.");
					}
				}
				else if (RequirementType.OPTIONAL == field.getRequirementType()) {
					if (log.isDebugEnabled()) {
						log.debug("Optional field '" + fieldName + "' is empty.");
					}
				}
				else {
					throw new RuntimeException("Unhandled or invalid requirement type: " + value);
				}
				
				return null;
			}

			if (field.getFormat() != null) {
				/*
				 * TODO: compare formatted output against its regex format pattern
				 */
				throw new RuntimeException("Not implemented");
			}
			else {
				if (log.isDebugEnabled()) {
					log.debug("Skipping format validation of field " + this.getClass().getName() + "(" + fieldName + ") as no format was defined.");
				}
			}

			try {
				return field.parse(unparsedValue);
			}
			catch (InvalidFormatException ex) {
				
				String msg = field.getRequirementType() + " field '" + fieldName + "' is invalid: " + ex.getMessage() + ": '" + new String(unparsedValue) + "'"; 
				
				if (log.isDebugEnabled()) {
					log.info(msg, ex);
				}
				
				throw new AchFileFormatException(msg);
			}
		}
		catch (Exception ex) {
			
			while (ex instanceof InvocationTargetException && ex.getCause() != null && ex.getCause() instanceof Exception) {
				ex = (Exception) ex.getCause();
			}
			
			if (ex instanceof AchFileFormatException) {
				throw (AchFileFormatException)ex;
			}
			else if (ex instanceof IOException) {
				throw (IOException)ex;
			}
			
			throw new RuntimeException(ex);
		}
	}
	
	public void close() throws IOException {
		reader.close();
	}
}
