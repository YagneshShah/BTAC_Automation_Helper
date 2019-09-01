/*
 * Date: October 12th 2015
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 */

package btac_automation_helper.saTests;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.testng.annotations.Test;

import automationHelper.seleniumappium.MathOperations;

public class MathOperationsTests
{	
	@Test
	public void isNumberDivisibleBy() throws Exception
	{
		for(int i=0;i<=100;i++)
		{
			System.out.println("i:" + i + " / res:" + MathOperations.isNumberDivisibleBy(i, 15));		
		}
	}
	
}