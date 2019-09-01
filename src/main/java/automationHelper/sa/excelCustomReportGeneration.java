/*
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import automationHelper.sa.CurrencyDateTime;
import automationHelper.sa.StringManipulation;
import jxl.Workbook;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.WriteException;


public class excelCustomReportGeneration {

	static Workbook wbook;
	static WritableWorkbook wwbCopy;
	static String ExecutedTestCasesSheet;
	static WritableSheet shSheet;


	public static String fileNameForReport(String addUniqueNameToFile, String reportSampleFileTemplatePath, String reportSampleFileTemplateName) throws Exception
	{
		addUniqueNameToFile = addUniqueNameToFile + "_";

		String dateTimeStamp = CurrencyDateTime.getCurrentDateTimeStamp();
		dateTimeStamp = dateTimeStamp + "_";

		String file = reportSampleFileTemplatePath;

		String finalFileName=null;
		String fileNameWithDateTimeStamp;
		String fileNameWithPageName;
		String fileNameWithoutTemplateKeyword=null;

		for(int i=reportSampleFileTemplateName.length()-1 ;i>0 ;i--)
		{
			//sample file name: 20160224_202640_page name_linkCheckerStatus
			//System.out.println(i + ":" + fileNameWithoutTemplateKeyword);
			if(reportSampleFileTemplateName.charAt(i)=='.')
			{
				fileNameWithoutTemplateKeyword = StringManipulation.deAppendStringAtIndex(reportSampleFileTemplateName, i-7, "Template");
				break;
			}
		}		
		fileNameWithPageName = StringManipulation.appendStringAtIndex(fileNameWithoutTemplateKeyword, 1, addUniqueNameToFile);
		fileNameWithDateTimeStamp = StringManipulation.appendStringAtIndex(fileNameWithPageName, 1, dateTimeStamp);

		finalFileName = reportSampleFileTemplatePath + fileNameWithDateTimeStamp;
		Reporter.log("Report File Name:" + finalFileName  + "\n" ,true);		
		Assert.assertTrue(finalFileName!=null,"Error! finalFileName is null...");

		return finalFileName;
	}

	public static void connectExcel(String addUniqueNameToFile, String reportSampleFileTemplatePath, String reportSampleFileTemplateName) throws Exception
	{
		String finalFileNameWithDateTimeStamp;
		
		String file=null;		
		file = reportSampleFileTemplatePath + reportSampleFileTemplateName;

		try{
			finalFileNameWithDateTimeStamp = fileNameForReport(addUniqueNameToFile, reportSampleFileTemplatePath, reportSampleFileTemplateName);
			wbook = Workbook.getWorkbook(new File(file));
			wwbCopy = Workbook.createWorkbook(new File(finalFileNameWithDateTimeStamp), wbook);
			shSheet = wwbCopy.getSheet(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void setValueIntoCell(String strSheetName,int iColumnNumber, int iRowNumber,String strData) throws WriteException, Exception
	{
		WritableSheet wshTemp = wwbCopy.getSheet(strSheetName);
		Label labTemp = new Label(iColumnNumber, iRowNumber, strData);

		try {
			wshTemp.addCell(labTemp);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void setValueIntoCell(String strSheetName,int iColumnNumber, int iRowNumber,int intData) throws WriteException, Exception
	{
		WritableSheet wshTemp = wwbCopy.getSheet(strSheetName);
		//Label labTemp = new Label(iColumnNumber, iRowNumber, strData);

		WritableCellFormat numberFormat = new WritableCellFormat(new NumberFormat("#"));
		try 
		{
			//write to datasheet
			wshTemp.addCell(new jxl.write.Number(iColumnNumber, iRowNumber, intData, numberFormat));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void closeFile() throws Exception
	{
		try {
			// Closing the writable work book
			wwbCopy.write();
			wwbCopy.close();

			// Closing the original work book
			wbook.close();
		} catch (Exception e)

		{
			e.printStackTrace();
		}
	}

}