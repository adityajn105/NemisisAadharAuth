package com.nemesis.nemesis.Aadhar.util;

import java.io.Reader;
import java.io.SerializablePermission;
import java.io.StringReader;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

public class XMLUtilities {

	private XMLUtilities() {
	}

	public static Object parseXML(Class clazz, String xmlToParse) throws Exception {
		 
		//Create an XMLReader to use with our filter 
		Serializer serializer = new Persister();  
		Reader reader = new StringReader(xmlToParse);
		Object res = serializer.read(clazz, reader,false);
		return res;
	}	
	public static String getXML(Object input) throws Exception{
		StringWriter xml = new StringWriter();

		Format format = new Format();
		Serializer serializer = new Persister(format);
		serializer.write(input, xml);
		return xml.toString();
	}
}
