package com.mypractice.galaxy;

import java.util.regex.Pattern;

import com.mypractice.utils.Helper;
import com.mypractice.utils.ReadableProperties;

/**
 * Roman Numerals: class for processing Roman numbers based on the conditions specified  
 * @author apoorva
 */
public class RomanNumerals 
{
	private static ReadableProperties props;
	
	/**
	 * @param aProps
	 */
	public RomanNumerals(ReadableProperties aProps)
	{
		props = aProps;
	}
	
	/**
	 * enum that maps roman to integer values
	 */
	public enum Roman
	{
		I(1), V(5), X(10), L(50), C(100), D(500), M(1000);
		
		private int romantoArabic;
		
		Roman(int aRomanNumeral)
		{
			romantoArabic = aRomanNumeral;
		}

		public int getRomantoArabic() 
		{
			return romantoArabic;
		}
	};
	
	/**
	 * @param aRomanLiteral
	 * @return
	 * Helper class that returns integer value for the given Roman character
	 */
	public static int valueOfRomanLiteral(char aRomanLiteral)
	{
		int numeric;
		
		switch(aRomanLiteral)
		{
			case 'I': numeric = Roman.I.getRomantoArabic();
			          break;
			case 'V': numeric = Roman.V.getRomantoArabic();
					  break;
			case 'X': numeric = Roman.X.getRomantoArabic();
					  break;
			case 'L': numeric = Roman.L.getRomantoArabic();
						break;
			case 'C': numeric = Roman.C.getRomantoArabic();
						break;
			case 'D': numeric = Roman.D.getRomantoArabic();
						break;			
			case 'M': numeric = Roman.M.getRomantoArabic();
						break;
			default: numeric = 0;	
					break;
		}
		return numeric;
	}
	
	/**
	 * @param aSequence
	 * @return
	 * Helper class that checks if the input roman sequence is valid, and satisfies the specified criteria
	 */
	private static boolean isValidRomanSequence(String aSequence)
	{
		String seqPattern = props.getString("roman", "valid-characters", null).trim();
		Pattern correctPattern = Pattern.compile(seqPattern);
		return correctPattern.matcher(aSequence).matches();
	}

	/**
	 * @param aSequence
	 * @return
	 * converts the Roman sequence to char array and computes the integer value 
	 */
	public static int convertRomanToArabic(String aSequence)
	{
		if(!isValidRomanSequence(aSequence))
			Helper.errorMessage(3);
		
		int sum = 0;
		
		char[] seq = Helper.getCharacterArrayFromString(aSequence);
		
		for(int i = 0; i < seq.length - 1; i++)
		{
			int current = valueOfRomanLiteral(seq[i]);
			int next = valueOfRomanLiteral(seq[i+1]);
			
			//if the preceding char is smaller than the current char, the difference of larger to smaller value is added to the sum
			if(current < next)
				sum -= current;
			else
				sum += current;
			
		}
		//finally adding the last char to the sum 
		sum += valueOfRomanLiteral(seq[seq.length - 1]);
		return sum;
	}
}
