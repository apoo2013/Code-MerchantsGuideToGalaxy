package com.mypractice.galaxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mypractice.utils.Helper;

/**
 * CurrencyProcessor: class that maps currency to values and computes values for the question 
 * @author apoorva
 *
 */
public class CurrencyProcessor 
{
	private static final String DELIM = "\\s+";
	private static final int QUESTION = 1;
	
	private static Map<String, String> currencyInRoman = new HashMap<String, String>();
	private static Map<String, Float> currencyValues = new HashMap<String, Float>(); 
	private static Map<String, Float> metalValues = new HashMap<String, Float>();
	
	private static List<String> linesToOutput = new ArrayList<String>();
	
	/**
	 * @param line
	 * currency mapping to Roman numerals 
	 */
	public static void mapCurrencyToRoman(String line)
	{
		String[] data = line.trim().split(DELIM);
		currencyInRoman.put(data[0], data[2]);
		mapCurrencyToValues(data[0], data[2]);
	}
	
	/**
	 * @param aCurrency
	 * @param aRoman
	 * currency mapping to values in floating-point representation
	 */
	private static void mapCurrencyToValues(String aCurrency, String aRoman)
	{
		Integer val = RomanNumerals.valueOfRomanLiteral(aRoman.charAt(0));
		currencyValues.put(aCurrency, new Float(val));
	}
	
	/**
	 * @param aLine
	 * processes "How much is.." question, computes value of the currency in integer 
	 */
	public static void convertHowMuch(String aLine)
	{
		//replace all words except the currency  
		String line = aLine.split("\\s+is\\s+")[1].trim().replace("?", "").trim();
		int currency = 0;
		
		//collect the currency values
		String[] tokens = line.split(DELIM);
		
		String romanSeq = "";
		
		for(String token:tokens)
		{
			if(!currencyInRoman.containsKey(token))
				addToList(Helper.errorMessage(4));
			//convert to Roman string
			romanSeq += currencyInRoman.get(token);
			
		}
		//convert Roman string to integer value
		if(!romanSeq.isEmpty())
			currency = RomanNumerals.convertRomanToArabic(romanSeq);
		
		String out = line+" is "+currency;
		
		addToList(out);
	}
	
	/**
	 * @param input
	 * computes the missing values of the corresponding metal and stores in map
	 */
	public static void computeMetalValues(String input)
	{
		String romanSeq = "";
		int currency = 0;
		Float metalValue;
		
		//replace all words except the currency and metal
		String line = input.replaceAll("(is\\s+)|([c|C]redits\\s*)","").trim();
		
		//collect the currency values
		String[] tokens = line.split(DELIM);
		
		//the last but one-th String is name of the metal
		String metal = tokens[tokens.length - 2];
		
		//last string is the value in credits
		Float credits = Float.parseFloat(tokens[tokens.length - 1]);
		
		for(int i = 0; i < tokens.length - 2; i++)
		{
			if(!currencyInRoman.containsKey(tokens[i]))
				addToList(Helper.errorMessage(4));
			//convert to Roman string
			romanSeq += currencyInRoman.get(tokens[i]);
		}
		
		//convert Roman string to integer value
		if(!romanSeq.isEmpty())
			currency = RomanNumerals.convertRomanToArabic(romanSeq);
		
		//the missing metal value is computed as (numerical credits/roman currency) and stored in map
		metalValue = (Float)credits/currency;
		metalValues.put(metal, metalValue);
	}
	
	/**
	 * @param input
	 */
	public static void computeHowManyCredits(String input)
	{
		String romanSeq = "";
		int currency = 0;
		//replace all words and '?' except the currency and metal
		String line = input.split("\\s+is\\s+")[1].trim().replace("?", "").trim();
		
		//collect the currency values
		String[] tokens = line.split(DELIM);
		
		//the last word is the name of the metal
		String metal = tokens[tokens.length - 1];
		
		if(!metalValues.containsKey(metal))
			addToList(Helper.errorMessage(3));
		//get corresponding value of the metal from map
		float metalValue = metalValues.get(metal);
		
		for(int i = 0; i < tokens.length - 1; i++)
		{
			if(!currencyInRoman.containsKey(tokens[i]))
				addToList(Helper.errorMessage(4));
			//convert to Roman string
			romanSeq += currencyInRoman.get(tokens[i]);
		}
		
		//convert Roman string to integer value
		if(!romanSeq.isEmpty())
			currency = RomanNumerals.convertRomanToArabic(romanSeq);
		
		//compute the value in credits and convert to integer (specified format)
		float creditsValue = metalValue * currency;
		int credits = (int)creditsValue;
		
		String out = line + " is "+ credits + " Credits";
		addToList(out);
	}
	
	/**
	 * Error message that is printed when the question is incorrect
	 */
	public static void errorOnWrongInput()
	{
		addToList(Helper.errorMessage(1));
	}
	
	/**
	 * @param out
	 * add all the output strings to a list for printing later
	 */
	public static void addToList(String out)
	{
		linesToOutput.add(out);
	}
	
	/**
	 * @param aFile
	 * @throws IOException
	 * write the list to a file in the same path as the input file
	 */
	public static void writeOutput(File aFile) throws IOException
	{
		FileWriter writer = null;
		File file = new File(aFile.getParent(), "output.txt");
		
		for(int i = 0; i < linesToOutput.size(); i++)
		try
		{
			writer = new FileWriter(file);
			for(String line:linesToOutput)
			{
				writer.write(line+"\n");
			}
			writer.flush();
		}
		finally
		{
			if(writer!=null)
	            writer.close();
		}
	}
}
