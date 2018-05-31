Merchant's Guide to Galaxy:
----------------------------------------------------------------------
Folder includes:
- README.txt
- guide.properties (src\main\resources folder)
- input.txt
- output.txt
- Requirements.txt
- classes described below (src\main\java folder)
 
--------------------------------------------------------------------- 
Source code is separated into three packages: app, galaxy, utils

1. app contains the main class Application.java
    Takes the input file as an argument.
    Reads guide.properties included in resources folder, for configuration
    OUTPUT - generates output to output.txt in the same folder location as the input supplied as argument

2. galaxy contains contains 3 classes (service layer)
   
   a. RomanNumerals - 
   	  Reads properties file for regex for Roman sequence validation
   	  mapping of Roman to integer values, conversion to Arabic number
   b. FileProcessor - 
   	  Reads properties file for regex mapping for 4 different types of line input
      reads input file, passes each line to CurrencyProcessor according to line type
   c. CurrencyProcessor - 
      maps galaxy currency to Roman characters and their corresponding numeric values
      computes missing values of the metal elements
      computes how much each galaxy currency amounts to
      computes how many credits each galaxy currency and metal elements amount to
3. utils contains Helper and ReadableProperties 
	a. Helper - 
	   see file for description of each helper method
	b. ReadableProperties - reads properties configuration 
	   see file
--------------------------------------------------------------------------
ASSUMPTIONS:
Application is built with the following assumptions:
- assumes input text file contains only four different types of line
- assignment statements are always specified before 'how much..' and 'how many..' questions 
- Roman numerals are always specified in upper case
- each line in input file is terminated by newline (test case)
- 'how much..' and 'how many.. ' are assumed as questions and they end with '?'
- assumes all words to be lower case with the exception of galaxy currency that can be either upper or lower case
- 'credits' can be specified with 'c' camel case 
- the metal values can be floating-point numbers
- assumes the credits value in 'how many credits..' question can be integer
- error messages with custom integer codes are used, no particular order   