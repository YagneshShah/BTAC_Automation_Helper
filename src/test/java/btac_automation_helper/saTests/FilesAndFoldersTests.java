/*
 * Date: October 12th 2015
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 */

package btac_automation_helper.saTests;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import automationHelper.sa.CurrencyDateTime;
import automationHelper.sa.FilesAndFolders;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyAndDatesTests.
 */
public class FilesAndFoldersTests
{
	@Test
	public void getPropValue() throws Exception
	{
		System.out.println(FilesAndFolders.getPropValue("fd"));
	}
	
	
	@Test
	public void setPropValue() throws Exception
	{
		FilesAndFolders.setPropValue("date0", "1234");			
	}
	
	@Test
	public void excelWorkbookCopy() throws BiffException, IOException
	{---;
		FilesAndFolders.excelWorkbookCopy("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\linkCheckerStatusTemplate.xls");
	}
	
	@Test
	public void writeCellToExcel1() throws BiffException, IOException, InterruptedException
	{---;
		String backupOriginalFile = "D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\linkCheckerStatusTemplate.xls";
		
		String finalFileNameWithDateTimeStamp;
		//finalFileNameWithDateTimeStamp = FilesAndFolders.fileNameForLinkCheckerStatusReport();
		finalFileNameWithDateTimeStamp="D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\linkCheckerStatusTemplate_20160224_180302.xls";
		
		FilesAndFolders.writeCellToExcelForLinkChecker(backupOriginalFile,finalFileNameWithDateTimeStamp,"sheet1", 5, 1, "PASS");
		//FilesAndFolders.writeCellToExcelForLinkChecker(backupOriginalFile,finalFileNameWithDateTimeStamp,"sheet1", 5, 2, "FAIL");
	}
	
	
	@Test
	public void writeCellToExcel() throws RowsExceededException, WriteException, IOException
	{---;
		FilesAndFolders.writeCellToExcel("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\sample.xls\\", "report", 2, 2, "dddd123^y");
	}
	
	@Test
	public void readExcelColumnFromKeywordOnwards1() throws Exception
	{
		ArrayList column = FilesAndFolders.readExcelColumnFromKeywordOnwards("validLogin", "Username");
		for(int i=0;i<column.size();i++)
		{
			System.out.println("str: " + column.get(i));
		}
	}
	
	@Test
	public void readExcelColumnFromKeywordOnwards2() throws BiffException, IOException, Exception
	{
		ArrayList column = FilesAndFolders.readExcelColumnFromKeywordOnwards("./testdata/linkCheckerInputData.xls","pageCrawler", "#url");
		for(int i=0;i<column.size();i++)
		{
			System.out.println("str: " + column.get(i));
		}
	}
	
	@Test
	public void readExcelColumnFromStart1() throws BiffException, IOException, Exception
	{
		ArrayList column = FilesAndFolders.readExcelColumnFromStart("validLogin", 0);
		for(int i=0;i<column.size();i++)
		{
			System.out.println("str: " + column.get(i));
		}
	}
	
	@Test
	public void readExcelColumnFromStart2() throws BiffException, IOException, Exception
	{
		ArrayList column = FilesAndFolders.readExcelColumnFromStart("./testdata/linkCheckerInputData.xls","pageCrawler", 1);
		for(int i=0;i<column.size();i++)
		{
			System.out.println("str: " + column.get(i));
		}
	}
	
	@Test
	public void copyFile() throws Exception
	{
		File source=new File("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\testdata\\sample.docx");
		File destination=new File("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\testdata\\sampleCopy.docx");
		FilesAndFolders.copyFile(source,destination);
	}

	@Test
	public void getProjectRootPath() throws Exception
	{
		FilesAndFolders.getProjectRootPath();
	}
	
	@Test
	public void uploadAttachmentForWindowsViaJavaRobotLib() throws Exception
	{
		FilesAndFolders.uploadAttachmentForWindowsViaJavaRobotLib("\\testdata\\webTestData.xls");
	}
	
	@Test
	public void getLastModifiedFileFromDir() throws Exception
	{
		File lastModifiedFile = FilesAndFolders.getLastModifiedFileFromDir("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\socialMediaReviewExtractorReports");
		Reporter.log("lastModifiedFile: " + lastModifiedFile, true);
	}
	
	@Test
	public void getAllFilesAndFolderNamesFromDir() throws Exception
	{
		File[] files = FilesAndFolders.getAllFilesAndFolderNamesFromDir("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary");
		
		for (int i = 0; i < files.length; i++)
		{
			Reporter.log("Log!! fileName: " + files[i],true);
		}
	}

	@Test
	public void getAllFilesAndFolderNamesFromDir2() throws Exception
	{
		List<String> files = FilesAndFolders.getAllFilesAndFolderNamesFromDir("D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\socialMediaReviewExtractorReports","Springboard");
		
		for (int i = 0; i < files.size(); i++)
		{
			Reporter.log("Log!! fileName: " + files.get(i),true);
		}
	}
	
	@Test
	public void fileExists() throws Exception
	{
		String fileNameWithPath = System.getProperty("user.dir")+ "\\socialMediaReviewExtractorReports\\abc.pdf";
		boolean fileExists = FilesAndFolders.fileExists(fileNameWithPath);
		
		Reporter.log("fileExists: '" + fileExists + "' for '" + fileNameWithPath + "'", true);
	}

}