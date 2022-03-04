package com.ibm.epricer.test.solr;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * Zip serealized the childstructures and the childcollections
 * 
 *  @author Kiran Chowdhury
 */
class ChildNodeEncoder {
	private static final Logger LOG = LoggerFactory.getLogger(ChildNodeEncoder.class);
	private static final String COLLECTION_PREFIX = "JSCCOLLECTION:";
	private static final String STRUCTURE_PREFIX = "JSCSTRUCTURE:";
	
	static String encodeChildCollection(List<Map<String, Object>> docs) {
		String jsccollectionString = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(docs);
			LOG.info("Encoding collection \n{}", jsonString);
			String encodedString = encode(jsonString);
			jsccollectionString = COLLECTION_PREFIX + encodedString;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsccollectionString;
	}
	
	static String encodeChildStructure(Map<String, Object> doc) {
		String jsstructureString = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(doc);
			LOG.info("Encoding structure \n{}", jsonString);
			String encodedString = encode(jsonString);
			jsstructureString = STRUCTURE_PREFIX + encodedString;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsstructureString;
	}
	
	static String encode(String data) throws IOException
	{
		// Encode the output
		return encodeBase64(zipString(data));
	}
	
	private static byte[] zipString (String data) throws IOException
	{
		// Creates a string to writes the zipped data
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// Map a ZIP entry on it
		OutputStream zout = openZIPEntry(out);
		try
		{
			// Writes the data
			zout.write(data.getBytes());
		}
		finally
		{
			// Closes all
			zout.close();
		}
		return out.toByteArray();
	}
	private static OutputStream openZIPEntry(OutputStream out) throws IOException
	{
		// Creates the ZIP stream
		ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(out, 300 * 1024));
		// Creates an entry
		ZipEntry entry = new ZipEntry("zip");
		zip.putNextEntry(entry);
		// Ok
		return new BufferedOutputStream(zip, 300 * 1024);
	}
	
	public static String encodeBase64(byte[] data)
	{
		return Base64.getMimeEncoder().encodeToString(data);
	}
}
