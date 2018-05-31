package com.mypractice.app;

import java.io.File;
import java.io.IOException;

import com.mypractice.galaxy.FileProcessor;
import com.mypractice.galaxy.RomanNumerals;
import com.mypractice.utils.Helper;
import com.mypractice.utils.ReadableProperties;

/**
 * Main class- Application starts here.
 * @author apoorva
 *
 */
public class Application 
{
	public static void main(String[] args)
	{
		//input file passed as argument at runtime
		File input = Helper.getInputFileFromArgs(args);
		
		//load properties file from classpath
		File config = new File(Application.class.getClassLoader().getResource("guide.properties").getFile());
		ReadableProperties props = Helper.getConfigFromArgs(config);

		//invoke constructors
		FileProcessor fp = new FileProcessor(props);
		RomanNumerals rm = new RomanNumerals(props);
		try 
		{
			fp.readInputFile(input);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
