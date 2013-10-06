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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Test;

import com.moss.ach.file.format.AchFileFormat;
import com.moss.ach.file.format.AchFileFormatReader;
import com.moss.ach.file.format.AchFileFormatWriter;

public class TestFormatLegacyRoundTrip {
	
	@Test
	public void roundTrip() throws Exception {
		
		BasicConfigurator.configure();
		
		final String originalFileData;
		{
			InputStream in = new FileInputStream("src/test/resources/com/moss/ach/file/rck.ach");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[1024 * 10]; //10k buffer
			for(int numRead = in.read(buffer); numRead!=-1; numRead = in.read(buffer)){
				out.write(buffer, 0, numRead);
			}
			
			in.close();
			out.close();
			
			originalFileData = new String(out.toByteArray());
		}
		AchFileFormat file = new AchFileFormatReader(new StringReader(originalFileData)).read();
		
		StringWriter writer = new StringWriter();
		new AchFileFormatWriter(file, writer).write();
		
		Assert.assertEquals(originalFileData, writer.getBuffer().toString());
	}
}
