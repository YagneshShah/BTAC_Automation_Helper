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

import org.testng.Reporter;
import org.testng.annotations.Test;

import automationHelper.seleniumappium.DataStructure;
import automationHelper.seleniumappium.RandomDataGenerator;

public class RandomDataGeneratorTests
{		
	@Test
	public void generateFileNameWithDateTimeStamp() throws Exception
	{
		Reporter.log(RandomDataGenerator.generateFileNameWithDateTimeStamp("sample.docx"),true);
	}
	
	@Test
	public void generateFileNameWithDateTimeStampAndPath() throws Exception
	{
		Reporter.log(RandomDataGenerator.generateFileNameWithDateTimeStampAndPath("D:\\workspace\\BTAC_SeleniumAppiumLibrary\\testdata\\","sample.docx"),true);
	}

}