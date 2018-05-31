package com.mypractice.galaxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.mypractice.utils.ReadableProperties;

/**
 * Fileprocessor: reads input file and maps to corresponding methods for further processing
 * @author apoorva
 *
 */
public class FileProcessor 
{
	private ReadableProperties props;
	private static Pattern ROMAN_CURRENCY;
	private static Pattern METAL_CREDITS;
	private static Pattern HOW_MUCH;
	private static Pattern HOW_MANY;
	
	/**
	 * @param aProps
	 * validation patterns for different types of lines in input
	 */
	public FileProcessor(ReadableProperties aProps) 
	{
		props = aProps;
		ROMAN_CURRENCY = Pattern.compile(props.getString("currency", "roman-value", null).trim());	//glob is I
		METAL_CREDITS = Pattern.compile(props.getString("currency", "metal-credits", null).trim()); //glob glob Silver is 34 Credits
		HOW_MUCH = Pattern.compile(props.getString("question", "how-much", null).trim()); //how much is pish tegj glob glob ?
		HOW_MANY = Pattern.compile(props.getString("question", "how-many", null).trim()); //how many Credits is glob prok Silver ?
	}
	
	/**
	 * @param aInput
	 * @throws IOException
	 * reads input file
	 */
	public void readInputFile(File aInput) throws IOException
	{
		BufferedReader br = null;
		String curLine;
		try
		{
			br = new BufferedReader(new FileReader(aInput));
			while((curLine = br.readLine()) != null)
			{
				process(curLine, aInput);
			}
		}
		finally
		{
			try 
			{
				if (br != null)
					br.close();
			}
			catch (Exception e) 
			{
				// ignore
			}
		}
	}
	
	/**
	 * @param line
	 * @param input
	 */
	private static void process(String line, File input)
	{
		if(ROMAN_CURRENCY.matcher(line).matches()) //glob is I
			CurrencyProcessor.mapCurrencyToRoman(line);
		else if(METAL_CREDITS.matcher(line).matches()) //glob glob Silver is 34 Credits
			CurrencyProcessor.computeMetalValues(line);
		else if(HOW_MUCH.matcher(line).matches()) //how much is pish tegj glob glob ?
			CurrencyProcessor.convertHowMuch(line);
		else if(HOW_MANY.matcher(line).matches()) //how many Credits is glob prok Silver ?
			CurrencyProcessor.computeHowManyCredits(line);
		else
			CurrencyProcessor.errorOnWrongInput();
		
		try 
		{
			//write to output file
			CurrencyProcessor.writeOutput(input);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
