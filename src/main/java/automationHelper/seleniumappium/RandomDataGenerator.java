/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * The Class RandomDataGenerator.
 */
public class RandomDataGenerator{

	/**
	 * Generate file name with date time stamp.
	 *
	 * @param fileNameWithExtension the file name with extension. Ex:"sample.docx"
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String generateFileNameWithDateTimeStamp(String fileNameWithExtension) throws Exception
	{
		String dateTimeStamp = CurrencyDateTime.getCurrentDateTimeStamp();
		dateTimeStamp = dateTimeStamp + "_";

		String finalFileName = dateTimeStamp + fileNameWithExtension;

		return finalFileName;
	}
	
	/**
	 * Generate file name with date time stamp and path.
	 *
	 * @param filePath the file path. Ex:"D:\\workspace\\BTAC_SeleniumAppiumLibrary\\testdata\\"
	 * @param fileNameWithExtension the file name with extension. Ex:"sample.docx"
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String generateFileNameWithDateTimeStampAndPath(String filePath, String fileNameWithExtension) throws Exception
	{
		String dateTimeStamp = CurrencyDateTime.getCurrentDateTimeStamp();
		dateTimeStamp = dateTimeStamp + "_";

		String finalFileName = filePath + dateTimeStamp + fileNameWithExtension;

		return finalFileName;
	}




}