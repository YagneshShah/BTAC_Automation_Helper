/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
// TODO: Auto-generated Javadoc
//import jxl.read.biff.BiffException;


/**
 * The Class CurrencyAndDates...
 */
public class DataStructure{

	/** The driver. */
	public static WebDriver driver;	

	public DataStructure(WebDriver driver){
		this.driver=driver;
	}
	
	
	/**
	 * Checks if the Input item is greater than all Array items.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param input the input
	 * @param array the array
	 * @return true, if successful else false
	 * @throws Exception the exception
	 */
	public static boolean isInputGreaterThanArrayItems(int input,int[] array) throws Exception
	{
		boolean flag;
		int count=0;
		
		for(int i=0;i<array.length;i++)
		{
			if(input<=array[i])
				count++;
		}
		if(count>0)
			flag=false;
		else
			flag=true;
		return flag;
	}

	/**
	 * Checks if Input item equals Array's last item.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param input the input
	 * @param array the array
	 * @return true, if successful else false
	 * @throws Exception the exception
	 */
	public static boolean isInputEqualToArrayLastItem(int input, int[] array) throws Exception 
	{
		if (array.length == 0)//empty array
			return false;
		
		if(input == array[array.length-1])//true if input equals last element of array
			return true;
		else
			return false;
	}

	/**
	 * Checls if Input item is less than all Array items.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param input the input
	 * @param array the array
	 * @return true, if successful else false
	 * @throws Exception the exception
	 */
	public static boolean isInputLessThanArrayItems(int input, int[] array) throws Exception 
	{
		int count=0;
		for(int i=0;i<array.length;i++)
		{
			if(input<array[i])
				count++;
		}
		if(count>0)
			return true;
		else
			return false;
	}

	/**
	 * Display array(Of type Int) content.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param array the array of type Int
	 * @throws Exception the exception
	 */
	public static void displayArrayContent(int[] array) throws Exception
	{
		for(int i=0;i<array.length;i++)
		{
			System.out.println("Value [" + i + "]: " + array[i]);
		}
	}

	/**
	 * Display array(Of type String) content.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param array the array of typr String
	 * @throws Exception the exception
	 */
	public static void displayArrayContent(String[] array) throws Exception
	{
		for(int i=0;i<array.length;i++)
		{
			System.out.println("Value [" + i + "]: " + array[i]);
		}
	}
	

}//end class


