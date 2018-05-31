package com.mypractice.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * ReadableProperties file is a helper class that allows to process the properties file
 * @author apoorva
 *
 */
public class ReadableProperties
{
	private final Properties props = new SortedProperties();
	private final Character escape = '\\';
	private final String filename;
	private final Character delim;
	
	 /**
	  * @param aFilename name of file to load
	  * @param aDelimiter list delimiter
	  */
	 public ReadableProperties(String aFilename, Character aDelimiter)
	 {
	     if (aDelimiter.equals(escape))
	     {
	         throw new IllegalArgumentException("delimiter cannot be " + escape);
	     }
	
	     filename = aFilename;
	     delim = aDelimiter;
	     reload();
	 }
	
	 /** {@inheritDoc} */
	 public String getString(String aDomain, String aKey, String aDefault)
	 {
	     String domainKey = key(aDomain, aKey);
	     if (props.containsKey(domainKey))
	     {
	         return trim(props.getProperty(domainKey));
	     }
	
	     return aDefault;
	 }
	
	 /** {@inheritDoc} */
	 public boolean hasValue(String aDomain, String aKey)
	 {
	     return props.containsKey(key(aDomain, aKey));
	 }
	
	 /**
	  * @param aDomain 
	  * @param aKey 
	  * @return intelligently combined value
	  */
	 protected String key(String aDomain, String aKey)
	 {
	     // convert blank/empty strings to nulls
		 String domain = notEmpty(aDomain);
	     String key = notEmpty(aKey); 
	     
	     if (domain == null && key == null)
	     {
	         throw new IllegalArgumentException("domain and/or key is required");
	     }
	
	     if (domain != null && key != null)
	     {
	         return domain + "." + key;
	     }
	     else if (domain != null)
	     {
	         return domain;
	     }
	     else
	     {
	         return key;
	     }
	 }
	
	 /**
	  * @param aStr a string, possibly null
	  * @return trimmed string or null
	  */
	 protected String trim(String aStr)
	 {
	     return aStr == null ? null : aStr.trim();
	 }
	
	 /**
	  * @return input stream used to load our properties
	  */
	 protected InputStream getInputStream()
	 {
	     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
	     if (is == null)
	     {
	         try
	         {
	             is = new FileInputStream(filename);
	         }
	         catch (FileNotFoundException ex)
	         {
	             return null;
	         }
	     }
	     return is;
	 }
	
	 /** {@inheritDoc} */
	 public void reload()
	 {
	     final InputStream is = getInputStream();
	     if (is == null)
	     {
	    	 System.out.println("Couldn't find the file");
	     }
	     else
	     {
	         try
	         {
	             props.load(is);
	         }
	         catch (IOException ex)
	         {
	             if (props.size() == 0)
	             {
	                 // throw a runtime exception only if we have never loaded props.
	                 // the theory is that we shouldn't fail due to temporary IO problem
	                 // if valid props exist...
	                 throw new RuntimeException("unable to reload file: " + filename);
	             }
	         }
	         
	         try
	         {
	             is.close();
	         }
	         catch (IOException ex)
	         {
	             System.err.println(ex);
	         }
	     }
	 }
	
	 /** {@inheritDoc} */
	 public Properties getProperties()
	 {
	     return new Properties(props);
	 }
	
	 /**
	  * Retrieves the name of the properties file.
	  * @return name of the properties file
	  */
	 protected String getFilename()
	 {
	     return filename;
	 }
	 
	 public static String notEmpty(String aStr)
	 {
	     if (aStr != null)
	     {
	         if (aStr.trim().length() == 0)
	         {
	             return null; // don't return empty/blank strings
	         }
	     }
	     return aStr;
	 }
	}
	
	/**
	 * Overrides Properties.keys() so that serialization is in ascending order of keys
	 * @author apoorva
	 */
	class SortedProperties extends Properties
	{
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public synchronized Enumeration<Object> keys()
		{
			Enumeration<Object> e = super.keys();
			Vector k = new Vector();
			while(e.hasMoreElements())
			{
				k.add(e.nextElement());
			}
			Collections.sort(k);
			return k.elements();
		}
		
}
