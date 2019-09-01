/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import org.openqa.selenium.WebDriver;

import automationHelper.sa.L;

/**
 * The Class MathOperations.
 */
public class MathOperations{
	
	public static WebDriver driver;	
	public MathOperations(WebDriver driver){
		this.driver=driver;
	}
	
	/**
	 * Checks if given number is divisible by X(integer).
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param number the number Ex: 20 
	 * @param divisibleBy the divisible by X(integer). Ex: 2
	 * @return true, if number is divisible by X
	 * @throws Exception the exception
	 */
	public static boolean isNumberDivisibleBy(int number, int divisibleBy) throws Exception
	{		
		if(number % divisibleBy == 0)
			return true;
		else
			return false;
	}
	

	
}