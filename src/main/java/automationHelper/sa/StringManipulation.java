/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class StringManipulation.
 */
public class StringManipulation{

	/** The driver. */
	public static WebDriver driver;	

	/**
	 * Instantiates a new string manipulation.
	 *
	 * @param driver the driver
	 */
	public StringManipulation(WebDriver driver){
		this.driver=driver;
	}

	/**
	 * Append new string at specified index in original string.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param originalStr the original str in which you wish to append string
	 * @param appendAtIndex the index at which you wish to append 
	 * @param appendString the string which will be appended at given index in original string
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String appendStringAtIndex(String originalStr, int appendAtIndex, String appendString) throws Exception
	{
		//dont use due to recursion: Lic.jc() & Cryptography 

		String first="";
		for(int i=0;i<appendAtIndex-1;i++)
		{
			first = first + originalStr.charAt(i);		
		}

		String last="";
		for(int i=appendAtIndex-1; i<originalStr.length(); i++)
		{
			last = last + originalStr.charAt(i);		
		}

		return first + appendString + last;
	}

	/**
	 * DeAppend/Remove string from specified index in original string.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param originalStr the original str in which you wish to DeAppend/Remove string
	 * @param deAppendFromIndex the index from which the string needs to be removed
	 * @param deAppendString the string which needs to be removed from index specified
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String deAppendStringAtIndex(String originalStr, int deAppendFromIndex, String deAppendString) throws Exception
	{
		//dont use due to recursion: Lic.jc() & Cryptography 

		String first="";
		for(int i=0;i<deAppendFromIndex-1;i++)
		{
			first = first + originalStr.charAt(i);		
		}

		String deAppend="";
		for(int i=deAppendFromIndex-1; i<deAppendString.length();i++)
		{
			deAppend = deAppend + originalStr.charAt(i); 
		}

		String last="";
		for(int i=deAppendFromIndex-1 + deAppendString.length(); i<originalStr.length(); i++)
		{
			last = last + originalStr.charAt(i);		
		}

		return first + last;
	}

	/**
	 * Concat/Join two string.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param str1 the str1
	 * @param str2 the str2
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String concatString(String str1, String str2) throws Exception
	{
		return str1.concat(str2);
	}

	/**
	 * String will be split after each occurrence of delimiter and returns the array of Strings.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param inputStr the input str
	 * @param delimiter the delimiter
	 * @return the string[]
	 * @throws Exception the exception
	 */
	public static String[] splitString(String inputStr, String delimiter) throws Exception
	{
		return inputStr.split(delimiter);		
	}

	/**
	 * Replace char at specific index in a string with any other char of your choice.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param str the input string
	 * @param index the index 
	 * @param replaceWith Old char at index will be replaced with this new char
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String replaceCharInString(String str, int index, char replaceWith) throws Exception
	{     
		if(str==null)
		{
			return str;
		}
		else if(index<0 || index>str.length())
		{
			return str;
		}
		char[] chars = str.toCharArray();
		chars[index-1] = replaceWith;
		return String.valueOf(chars);       
	}

	/**
	 * Delete char in string at specific index. Ex: input string:abcdef, index:3, output returned:abdef 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param str the input string
	 * @param indexToDeleteChar the index to delete char
	 * @return the string builder
	 * @throws Exception the exception
	 */
	public static String deleteCharInString(String str, int indexToDeleteChar) throws Exception
	{
		StringBuilder string = new StringBuilder(str);
		return string.deleteCharAt(indexToDeleteChar-1).toString();
	}
	
	/**
	 * Removes the single quote from start and end of a string. 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param str the str with quotes. Ex:"'asssssssssz'" or "'asssssssssz" or "asssssssssz'"
	 * @return the string without single quotes at start and end of a String
	 * @throws Exception the exception
	 */
	public static String removeSingleQuoteFromString(String str) throws Exception
	{
		//remove ' from start & end of str string
		//System.out.println("str 1stChar:" + str.charAt(0));
		//System.out.println("str lastChar:" + str.charAt(str.length()-1));
		if(str.charAt(0)=='\'')//start of str
		{
			str = StringManipulation.deleteCharInString(str, 1);
		}
		if(str.charAt(str.length()-1)=='\'')//start of str
		{
			str = StringManipulation.deleteCharInString(str, str.length());
		}
		//System.out.println("strb:" + strb);
		//System.out.println("str without quotes: " + str);
		return str;
	}
	
	/**
	 * Finds all occurrences of a given Regular Expression in a given input String.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param str the str. Ex:"404"
	 * @param regEx the reg ex. Ex:"4\\d\\d"
	 * @throws Exception the exception
	 */
	public static void regex(String str, String regEx) throws Exception
	{
	    int count=0;

	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(str); // get a matcher object
	    
	    while(m.find()) {
	         count++;
	         System.out.println("Match number "+count);
	         
	         System.out.println("start(): "+m.start());
	         System.out.println("end(): "+ m.end());
	      }

	}
}