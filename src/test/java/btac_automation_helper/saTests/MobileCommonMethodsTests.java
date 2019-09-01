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

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import automationHelper.seleniumappium.MathOperations;

public class MobileCommonMethodsTests
{	
	 @Test
	    public void testScroll()throws Exception
	    {
	        for(int i=0;i<4;i++)
	        {
	            Thread.sleep(2000);
	            if (driver.findElement(By.name("end_item")).isDisplayed())
	            {
	                driver.findElement(By.name("end_item")).click();
	                break;
	            }
	            else
	            {
	                verticalScroll();
	            }

	        }
	    }
	
}