package com.mypractice.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class
 * @author apoorva
 *
 */
public class Helper 
{
	private final File input;
    private final ReadableProperties props;
    private static List<String> linesToOutput;
    
    /**
     * @param aInput input text file
     * @param aProps properties file describing fields in input file
     */
    public Helper(File aInput)
    {
    	this(aInput, null);
    	linesToOutput =  new ArrayList<String>();
    }

    /**
     * @param aInput
     * @param aProps
     */
    public Helper(File aInput, ReadableProperties aProps)
    {
    	  input = aInput;
          props = aProps;
          linesToOutput =  new ArrayList<String>();
    }

    /**
     * @param args
     * @return
     * reads input file from the argument passed at runtime
     */
    public static File getInputFileFromArgs(String[] args)
    {
    	if (args.length < 0)
        {
            System.err.println("Input filename required as a parameter");
            System.exit(1);
        }
        File input = new File(args[0]);
        if (!input.exists())
        {
            throw new IllegalArgumentException(input.getAbsolutePath() + " does not exist");
        }
        if (!input.canRead())
        {
            throw new IllegalArgumentException(input.getAbsolutePath() + " cannot be read");
        }
        return input;
    }
    
    /**
     * @param config
     * @return
     * checks for properties file from the classpath
     */
    public static ReadableProperties getConfigFromArgs(File config)
    {
        if (!config.exists())
        {
            throw new IllegalArgumentException(config.getAbsolutePath()+ " does not exist");
        }
        if (!config.canRead())
        {
            throw new IllegalArgumentException(config.getAbsolutePath()+ " cannot be read");
        }
        
        return new ReadableProperties(config.getPath(), ',');
    }
 
    /**
     * @param aString
     * @return
     * convert String to char array
     */
    public static char[] getCharacterArrayFromString(String aString)
    {
    	if(aString.length() < 1)
    		throw new IllegalArgumentException("Invalid String, cannot convert to array.");
    	return aString.toCharArray();
    }
    
   	/**
   	 * @param errorCode
   	 * @return
   	 * different types of error messages categorized with codes
   	 */
   	public static String errorMessage(int errorCode)
	{
		String errorMessage = "";
		switch(errorCode) 
		{
			case 1: errorMessage = "I have no idea what you are talking about";
					break;
			case 2: errorMessage = "Invalid sequence of characters";
					break;
			case 3: errorMessage = "Invalid metal name, Not in the list";
					break;
			case 4: errorMessage = "Invalid currency, Not in the list";
					break;
			default: errorMessage = "Invalid data";
					 break;
		}
		return errorMessage;
	}
}
