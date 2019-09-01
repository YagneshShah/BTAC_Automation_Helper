/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import jxl.read.biff.BiffException;
import jxl.Cell;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;


// TODO: Auto-generated Javadoc
/**
 * The Class FilesAndFolders.
 */
public class FilesAndFolders{

	/** The driver. */
	public static WebDriver driver;	

	/**
	 * Instantiates a new files and folders.
	 *
	 * @param driver the driver
	 */
	public FilesAndFolders(WebDriver driver){
		this.driver=driver;
	}

	/**
	 * Gets the property value for the key(passed as parameter) from the config.properties file located at project root
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param key the key which exists in config.properties file
	 * @return the property value for the Key passed as parameter
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NumberFormatException 
	 */
	public static String getPropValue(String key) throws Exception
	{
		FileReader reader = new FileReader("./config.properties"); 
		Properties prop = new Properties();
		prop.load(reader); 

		String propValue = prop.getProperty(key);
		return propValue;
	}

	/**
	 * Gets the property value for the key(passed as parameter) from the .properties file path specified
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param key the key which exists in .properties file specified
	 * @param filePath the file path for the .properties file. Ex: "./config.properties"
	 * @return the property value for the Key passed as parameter
	 * @throws Exception the exception
	 * @throws NumberFormatException the number format exception
	 */
	public static String getPropValue(String key, String filePath) throws Exception
	{
		FileReader reader = new FileReader(filePath); 
		Properties prop = new Properties();
		prop.load(reader); 

		String propValue = prop.getProperty(key);
		return propValue;
	}


	//	public static String getPropValues(String[] key) throws IOException, Exception
	//	{
	//		Lic.jc();
	//
	//		FileReader reader = new FileReader("./config.properties"); 
	//		Properties prop = new Properties();
	//		prop.load(reader); 
	//
	//		for()
	//		String propValue = prop.getProperty(key[0]);
	//		return propValue;
	//	}

	/**
	 * Sets the property value for the Key(passed as parameter) in config.properties located at project root
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param key the key which exists in config.properties file
	 * @param value the value which you wish to assign to the key in config.properties file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static void setPropValue(String key, String value) throws IOException, Exception
	{
		FileReader reader = new FileReader("./config.properties"); 
		Properties prop = new Properties();
		prop.load(reader); 

		prop.setProperty(key,value);
		prop.store(new FileOutputStream("./config.properties"), null);
	}


	/**
	 * Reads a default excel file(located at: ./testdata/webTestData.xls) and returns the entire record for the uniqueValue specified from the sheetName specified.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param sheetName the sheet name from which you wish to read and return a record
	 * @param uniqueValue the unique value for which the entire record(row) will be returned
	 * @return the cell[] array for the entire record(row)
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	//Method to dynamically retrieve any 1 specific record from any web excel sheet
	public static Cell[] readExcel(String sheetName, String uniqueValue) throws BiffException, IOException,Exception
	{
		Workbook wrk1;
		Sheet sheet1;
		//Cell colRow;

		String testDataForDefaultReadExcel = getPropValue("testDataForDefaultReadExcel");
		wrk1 = Workbook.getWorkbook(new File(testDataForDefaultReadExcel)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row);
		return record;
	}

	/**
	 * Reads an excel file(located at filePath passed as parameter) and returns the entire record for the uniqueValue specified from the sheetName specified.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param filePath the file path Ex:"./testdata/webTestData.xls"
	 * @param sheetName the sheet name from which you wish to read and return a record
	 * @param uniqueValue the unique value for which the entire record(row) will be returned
	 * @return the cell[] array for the entire record(row)
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static Cell[] readExcel(String filePath, String sheetName, String uniqueValue) throws BiffException, IOException, Exception
	{
		Workbook wrk1;
		Sheet sheet1;

		wrk1 = Workbook.getWorkbook(new File(filePath)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row);
		return record;
	}

	/**
	 * Reads an excel file(located at: ./testdata/webTestData.xls) and returns 1 specific record which is just below the uniqueValue specified in the sheetName(specified as parameter).
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param sheetName the sheet name from which you wish to read and return a record
	 * @param uniqueValue the unique value for which the entire record(row) will be returned
	 * @return the cell[] array for the entire record(row) which is next to the uniqueValue
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	//Method to dynamically retrieve any 1 specific record which is just below the uniqueValue specified
	public static Cell[] readExcelNextRowOfUniqueValue(String sheetName, String uniqueValue) throws BiffException, IOException, Exception 
	{
		Workbook wrk1;
		Sheet sheet1;

		String testDataForDefaultReadExcel = getPropValue("testDataForDefaultReadExcel");
		wrk1 = Workbook.getWorkbook(new File(testDataForDefaultReadExcel)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row+1);
		return record;
	}

	/**
	 * Reads an excel file(located at filePath passed as parameter) and returns 1 specific record which is just below the uniqueValue specified in the sheetName(specified as parameter).
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param filePath the file path Ex:"./testdata/webTestData.xls"
	 * @param sheetName the sheet name from which you wish to read and return a record
	 * @param uniqueValue the unique value for which the entire record(row) will be returned
	 * @return the cell[] array for the entire record(row) which is next to the uniqueValue
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static Cell[] readExcelNextRowOfUniqueValue(String filePath, String sheetName, String uniqueValue) throws BiffException, IOException,Exception  
	{
		Workbook wrk1;
		Sheet sheet1;

		wrk1 = Workbook.getWorkbook(new File(filePath)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row+1);
		return record;
	}

	//	//Method to dynamically retrieve "N" records from any excel sheet...This is still not complete and requires work to be done...
	//	@SuppressWarnings("null")
	//	public static String[][] readExcel(String sheetName, int nRecords) throws BiffException, IOException
	//	{
	//		//List<String> list1 = new ArrayList();
	//		String records[][]=new String [nRecords][50];
	//
	//		wrk1 = Workbook.getWorkbook(new File("../seleniumWebDriverProjectTemplateWithPageObjects/testdata/webTestData.xls")); // Connecting to excel workbook.
	//		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
	//
	//		for(int i=1; i<nRecords; i++) //starting from i=1 since i=0 is a column name and not the value
	//		{
	//			Cell uname = sheet1.getCell(0,i);  //Column=0=username,
	//			String check = uname.getContents();
	//			Boolean b1= check.isEmpty();
	//			if(b1){
	//				break;// Script will  end when it found no entry in the Serial No. column of the excel sheet.
	//			} 
	//
	//			Cell[] row = sheet1.getRow(i);
	//			System.out.println("rows: "+row.length);
	//
	//			//System.out.println("record: " + record);
	//			for (int j=0; j<row.length; j++)
	//			{
	//				records[i-1][j]=row[j].getContents();
	//				//System.out.println("array: "+ records[i-1][j]);
	//			}
	//		}
	//
	//		//		for (int i=0; i<records.length; i++)
	//		//        {	//String row[]=records[i];
	//		//			System.out.println("rows: "+records.length);
	//		////			System.out.println("column: "+records[i].length);
	//		//			for (int j=0; j < 2; j++)
	//		//			{
	//		//	        	System.out.println("array: "+ records[i][j]);				
	//		//			}
	//		//        }	
	//		return records;
	//	}


	/**
	 * Read excel(located at default location:"./testdata/webTestData.xls") column from keyword onwards until it finds the empty cell.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param sheetName the sheet name
	 * @param keyWord the key word(next cell) from which you wish to get the data
	 * @return the array list 
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static ArrayList readExcelColumnFromKeywordOnwards(String sheetName, String keyWord) throws BiffException, IOException, Exception
	{
		Workbook wrk1;
		Sheet sheet1;
		Cell columnData;

		String testDataForDefaultReadExcel = getPropValue("testDataForDefaultReadExcel");
		wrk1 = Workbook.getWorkbook(new File(testDataForDefaultReadExcel)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
		LabelCell cell=sheet1.findLabelCell(keyWord);
		int iCol=cell.getColumn();
		int jRow=cell.getRow();

		//List<String> column=null;
		ArrayList column = new ArrayList(); 

		for(;;)
		{
			columnData = sheet1.getCell(iCol, jRow);
			//System.out.println("columnData:" + columnData.getContents());
			if(columnData.getContents().isEmpty())
			{
				//Reporter.log("null" ,true);	
				break;				
			}
			else
				//Reporter.log("columnData:" + columnData.getContents() ,true);
				column.add(columnData.getContents());
			jRow++;
		}

		return column;
	}

	/**
	 * Read excel(located at custom location - filePath passed as parameter) column from keyword onwards until it finds the empty cell.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param fileNameWithPath the file name with path. Ex:"./testdata/linkCheckerInputData.xls"
	 * @param sheetName the sheet name
	 * @param keyWord the key word(next cell) from which you wish to get the data
	 * @return the array list
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static ArrayList readExcelColumnFromKeywordOnwards(String fileNameWithPath, String sheetName, String keyWord) throws BiffException, IOException, Exception 
	{
		Workbook wrk1;
		Sheet sheet1;
		Cell columnData;

		wrk1 = Workbook.getWorkbook(new File(fileNameWithPath)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
		LabelCell cell=sheet1.findLabelCell(keyWord);
		int iCol=cell.getColumn();
		int jRow=cell.getRow();

		//List<String> column=null;
		ArrayList column = new ArrayList(); 

		for(;;)
		{
			//			System.out.println("iCol:" + iCol);
			//			System.out.println("jRow:" + jRow);
			columnData = sheet1.getCell(iCol, jRow);
			//System.out.println("columnData:" + columnData.getContents());
			if(columnData.getContents().isEmpty())
			{
				//Reporter.log("null" ,true);	
				break;				
			}
			else
				//Reporter.log("columnData:" + columnData.getContents() ,true);
				column.add(columnData.getContents());
			jRow++;
		}

		return column;
	}

	/**
	 * Read excel(located at default location:"./testdata/webTestData.xls") column from start, until it finds the empty cell.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param sheetName the sheet name from which column data is to be extracted
	 * @param coulumnNo the coulumn no. Index starts from 0 for Column A.
	 * @return the array list which contains data of entire column until empty cell is found
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static ArrayList readExcelColumnFromStart(String sheetName, int coulumnNo) throws BiffException, IOException, Exception 
	{
		Workbook wrk1;
		Sheet sheet1;
		Cell columnData;

		String testDataForDefaultReadExcel = getPropValue("testDataForDefaultReadExcel");
		wrk1 = Workbook.getWorkbook(new File(testDataForDefaultReadExcel)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		//List<String> column=null;
		ArrayList column = new ArrayList(); 
		int jRow=0;

		for(;;)
		{
			columnData = sheet1.getCell(coulumnNo, jRow);
			//System.out.println("columnData:" + columnData.getContents());
			if(columnData.getContents().isEmpty())
			{
				//Reporter.log("null" ,true);	
				break;				
			}
			else
				//Reporter.log("columnData:" + columnData.getContents() ,true);
				column.add(columnData.getContents());
			jRow++;
		}

		return column;
	}

	/**
	 * Read excel(located at custom location - fileNameWithPath passed as parameter) column from start, until it finds the empty cell.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param fileNameWithPath the file name with path. Ex:"./testdata/linkCheckerInputData.xls"
	 * @param sheetName the sheet name from which column data is to be extracted
	 * @param coulumnNo the coulumn no. Index starts from 0 for Column A.
	 * @return the array list which contains data of entire column until empty cell is found
	 * @throws BiffException the biff exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static ArrayList readExcelColumnFromStart(String fileNameWithPath,String sheetName, int coulumnNo) throws BiffException, IOException, Exception  
	{
		Workbook wrk1;
		Sheet sheet1;
		Cell columnData;

		wrk1 = Workbook.getWorkbook(new File(fileNameWithPath)); // Connecting to excel workbook.
		sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		//List<String> column=null;
		ArrayList column = new ArrayList(); 
		int jRow=0;

		for(;;)
		{
			columnData = sheet1.getCell(coulumnNo, jRow);
			//System.out.println("columnData:" + columnData.getContents());
			if(columnData.getContents().isEmpty())
			{
				//Reporter.log("null" ,true);	
				break;				
			}
			else
				//Reporter.log("columnData:" + columnData.getContents() ,true);
				column.add(columnData.getContents());
			jRow++;
		}
		return column;
	}

	/**
	 * Copy file from source to destination.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param sourceFile the source file with path. Ex:"D:\\workspace\\BTAC_SeleniumAppiumLibrary\\testdata\\sample.docx"
	 * @param destFile the destination file with path. Ex:"D:\\workspace\\BTAC_SeleniumAppiumLibrary\\testdata\\sampleCopy.docx"
	 * @throws Exception the exception
	 */
	public static void copyFile(File sourceFile, File destFile) throws Exception 
	{
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new RandomAccessFile(sourceFile,"rw").getChannel();
			destination = new RandomAccessFile(destFile,"rw").getChannel();

			long position = 0;
			long count    = source.size();

			source.transferTo(position, count, destination);
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}


	/**
	 * Gets the project root path.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the project root path as a String. Ex:"D:\Softwares\workspacePersonal\BTAC_SeleniumAppiumLibrary" 
	 * @throws Exception 
	 */
	public static String getProjectRootPath() throws Exception
	{
		String projectRootPath = System.getProperty("user.dir");
		//Reporter.log("Log! projectRootPath: " + projectRootPath,true);
		return projectRootPath;
	}

	/**
	 * Upload attachment via java robot library for windows operating system. 
	 * Sometimes Selenium API ".sendKeys("path")" doesn't work to upload a file. Hence we can use this API to paste the file path in a text box and then upload the file.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param filePathInProject the file path in project. Ex: "\\testdata\\webTestData.xls"
	 * @throws Exception 
	 */
	public static void uploadAttachmentForWindowsViaJavaRobotLib(String filePathInProject) throws Exception
	{
		StringSelection selection = new StringSelection(System.getProperty("user.dir") + filePathInProject);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);

		Robot robot = null;
		try {
			robot = new Robot();
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}

		robot.keyPress(KeyEvent.VK_CONTROL); //press CTRL
		robot.keyPress(KeyEvent.VK_V); //press V
		robot.keyRelease(KeyEvent.VK_V); //release V
		robot.keyRelease(KeyEvent.VK_CONTROL); //release CTRL

		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER); //press enter
		robot.keyRelease(KeyEvent.VK_ENTER); //release enter
	}

	
	/**
	 * Download file in IE via Java Robot Library.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param webElementOfDownloadLink the web element of download link
	 * @throws Exception the exception
	 */
	public static void downloadFileInIE(WebElement webElementOfDownloadLink) throws Exception
	{
		try {
			/*
			 * In IE 8, saving a file would be three steps:
			 * 1) Click link  or Press Enter key on the link.
			 * 2) type S. 
			 * 3) Hit Enter.
			 */

			Robot robot = new Robot();
			//get the focus on the element..don't use click since it stalls the driver          
			webElementOfDownloadLink.sendKeys("");
			//simulate pressing enter            
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			//wait for the modal dialog to open            
			Thread.sleep(2000);
			//press s key to save            
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			Thread.sleep(2000);
			
			//press enter to save the file with default name and in default location
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(10000);
		} 
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
	}


	/**
	 * Gets the last modified file from given directory.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dirPath the directory path. Ex:"D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary"
	 * @return the last modified file from given directory
	 * @throws Exception 
	 */
	public static File getLastModifiedFileFromDir(String dirPath) throws Exception
	{
		File dir = new File(dirPath);
		File[] files = dir.listFiles(); //get all files from a directory

		Assert.assertFalse(files == null || files.length == 0,"Error!! No files found in the specified directory '" + dirPath + "'..."); //Stop execution if no files found in the specified directory

		File lastModifiedFile = files[0];
		for (int i = 0; i < files.length; i++) //check which file has greatest value of lastModified(). That file is what we want.
		{
			if (lastModifiedFile.lastModified() <= files[i].lastModified()) //if lastModified value for a files[i] is greater than lastModifiedFile, then update value of lastModifiedFile  
				lastModifiedFile = files[i];
		}

		//Reporter.log("Log!! lastModifiedFile: " + lastModifiedFile,true);
		return lastModifiedFile;
	}

	/**
	 * Gets the all files and folder names from the given directory.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dirPath the directory path. Ex:"D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary"
	 * @return all files and folder names from the given directory
	 * @throws Exception the exception
	 */
	public static File[] getAllFilesAndFolderNamesFromDir(String dirPath) throws Exception
	{
		File dir = new File(dirPath);
		File[] files = dir.listFiles(); //get all files from a directory

		Assert.assertFalse(files == null || files.length == 0,"Error!! No files found in the specified directory '" + dirPath + "'..."); //Stop execution if no files found in the specified directory

		//		for (int i = 0; i < files.length; i++)
		//		{
		//			Reporter.log("Log!! fileName: " + files[i],true);
		//		}

		return files;
	}


	/**
	 * Gets the all files and folder containing a 'String' in the name, from the given directory.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dirPath the directory path. Ex:"D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary"
	 * @param fileNameContains the string value which should be contained in all file names
	 * @return all files and folder names containing given 'string value' from the given directory
	 * @throws Exception the exception
	 */
	public static List getAllFilesAndFolderNamesFromDir(String dirPath, String fileNameContains) throws Exception
	{
		File dir = new File(dirPath);
		File[] files = dir.listFiles(); //get all files from a directory

		Assert.assertFalse(files == null || files.length == 0,"Error!! No files found in the specified directory '" + dirPath + "'..."); //Stop execution if no files found in the specified directory

		//		ArrayList<File> finalFiles = null;
		//		Reporter.log("Log!! fileName:" + finalFiles, true);
		//
		//		for (int i = 0; i < files.length; i++)
		//		{
		//			if(files[i].getName().contains(fileNameContains))
		//			{
		//				Reporter.log("Log!! fileName:" + files[i].getName(), true);
		//				finalFiles.add(files[i]);
		//				Reporter.log("Log!! fileName:" + finalFiles, true);
		//			}
		//		}


		List <String> finalFiles = null;
		for (int i = 0; i < files.length; i++)
		{
			if(files[i].getName().contains(fileNameContains))
			{
				Reporter.log("Log!! fileName:" + files[i].getName(), true);
				finalFiles.add(files[i].getName());
				Reporter.log("Log!! fileName:" + finalFiles, true);
			}
		}



		//		for (int i = 1; i < finalFiles.size(); i++)
		//		{
		//			Reporter.log("Log!! fileName: " + finalFiles[i],true);
		//		}

		return finalFiles;
	}
	
	/**
	 * File exists.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param fileNameWithPath the file name with path. Ex: System.getProperty("user.dir")+ "\\downloads\\abc.xls"
	 * @return true, if successful
	 * @throws Exception 
	 */
	public static boolean fileExists(String fileNameWithPath) throws Exception
	{
		File tmpFile = new File(fileNameWithPath);
		boolean fileExists = tmpFile.exists();
		
		Assert.assertTrue(fileExists == true, "Error!! File doesn't exist at location '" + fileNameWithPath + "'"); 
		
		return fileExists;
	}


	
	public static String getValueFromPropertiesFile(String filePath, String key) throws IOException
	{
		//		System.out.println("filePath:" + filePath);
		//		System.out.println("key:" + key);

		FileReader reader = new FileReader(filePath); //Example: filePath = "./Global Settings.properties";
		Properties prop = new Properties();
		prop.load(reader);    	  

		String keyValue = prop.getProperty(key);
		return keyValue;
	}

	public static String getValueFromPropertiesFile(String key) throws IOException
	{
		//		System.out.println("filePath:" + filePath);
		//		System.out.println("key:" + key);

		FileReader reader = new FileReader("./config.properties"); //Example: filePath = "./Global Settings.properties";
		Properties prop = new Properties();
		prop.load(reader);    	  

		String keyValue = prop.getProperty(key);
		return keyValue;
	}

	//Method to dynamically retrieve any 1 specific record from any web excel sheet
	public static Cell[] webReadExcel(String sheetName, String uniqueValue) throws BiffException, IOException
	{
		Workbook wrk1 = Workbook.getWorkbook(new File("./testdata/apiTestData.xls")); // Connecting to excel workbook.
		Sheet sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row);
		return record;
	}

	//Method to dynamically retrieve any 1 specific record from any web excel sheet
	public static Cell[] webReadExcel(String filePathAndName, String sheetName, String uniqueValue) throws BiffException, IOException
	{
		Workbook wrk1 = Workbook.getWorkbook(new File(filePathAndName)); // Connecting to excel workbook.
		Sheet sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet

		LabelCell cell=sheet1.findLabelCell(uniqueValue);
		int row=cell.getRow();
		Cell[] record = sheet1.getRow(row);
		return record;
	}

//	//Method to dynamically retrieve any 1 specific record which is just below the uniqueValue specified
//	public static Cell[] readExcelNextRowOfUniqueValue(String sheetName, String uniqueValue) throws BiffException, IOException 
//	{
//		Workbook wrk1 = Workbook.getWorkbook(new File("./testdata/apiTestData.xls")); // Connecting to excel workbook.
//		Sheet sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
//		LabelCell cell=sheet1.findLabelCell(uniqueValue);
//		int row=cell.getRow();
//		Cell[] record = sheet1.getRow(row+1);
//		return record;
//	}
//
//	//Method to dynamically retrieve any 1 specific record which is just below the uniqueValue specified
//	public static Cell[] readExcelNextRowOfUniqueValue(String filePathAndName, String sheetName, String uniqueValue) throws BiffException, IOException 
//	{
//		Workbook wrk1 = Workbook.getWorkbook(new File(filePathAndName)); // Connecting to excel workbook.
//		Sheet sheet1 = wrk1.getSheet(sheetName); // Connecting to the sheet
//		LabelCell cell=sheet1.findLabelCell(uniqueValue);
//		int row=cell.getRow();
//		Cell[] record = sheet1.getRow(row+1);
//		return record;
//	}







	//Commenting for Java Doc purpose:

	//	public static void writeCellToExcel(String fileNameWithPath, String sheetName, int colNo, int rowNo, String data) throws IOException, RowsExceededException, WriteException, Exception 
	//	{---;
	//	Lic.jc();
	//
	//	File file = new File(fileNameWithPath);
	//
	//	WorkbookSettings wbSettings = new WorkbookSettings();
	//	wbSettings.setLocale(new Locale("en", "EN"));
	//
	//	WritableWorkbook wwb = Workbook.createWorkbook(file, wbSettings);
	//	wwb.createSheet(sheetName, 0);
	//	wwb.createSheet("res1", 1);
	//
	//	//display all sheet names
	//	String sNames[] = wwb.getSheetNames();
	//	for(int i=0;i<sNames.length;i++){
	//		System.out.println(i + ":" + sNames[i]);
	//	}
	//
	//	WritableSheet ws = wwb.getSheet(sheetName);
	//	System.out.println();
	//	WritableCell label= new Label(colNo, rowNo, data);
	//	ws.addCell(label);  
	//
	//	}
	//
	//
	//
	//	public static void writeCellToExcelForLinkChecker(String backupOriginalFile, String finalFileNameWithDateTimeStamp, String strSheetName,int iColumnNumber, int iRowNumber,String strData) throws BiffException, IOException, InterruptedException, Exception 
	//	{---;
	//	Lic.jc();
	//
	//	Workbook wbook;
	//	WritableWorkbook wwbCopy;
	//	String ExecutedTestCasesSheet;
	//	WritableSheet shSheet;
	//
	//
	//	//connect and open excel file
	//	wbook = Workbook.getWorkbook(new File(backupOriginalFile));
	//	wwbCopy = Workbook.createWorkbook(new File(finalFileNameWithDateTimeStamp), wbook);
	//	//wwbCopy = Workbook.createWorkbook(new File(finalFileNameWithDateTimeStamp));
	//
	//
	//	//write
	//	shSheet = wwbCopy.getSheet(strSheetName);
	//	Label labTemp = new Label(iColumnNumber, iRowNumber, strData);
	//
	//	try {
	//		shSheet.addCell(labTemp);
	//	} 
	//	catch (Exception e) 
	//	{
	//		e.printStackTrace();
	//	}
	//
	//	//update and close file
	//	try 
	//	{
	//		// Closing the writable work book
	//		wwbCopy.write();
	//		wwbCopy.close();
	//		// Closing the original work book
	//		wbook.close();
	//	} catch (Exception e)
	//
	//	{
	//		e.printStackTrace();
	//	}
	//	}


	//	//not working yet...copies empty file
	//	public static void excelWorkbookCopy(String srcFileNameWithPath) throws BiffException, IOException, Exception
	//	{--;
	//		Lic.jc();
	//
	//		Workbook wbook;
	//		WritableWorkbook wwbCopy;
	//		String ExecutedTestCasesSheet;
	//		WritableSheet shSheet;
	//
	//		//make destination file name with path
	//		String copy = "_Copy";
	//
	//		String destinationFileNameWithPath=null;
	//
	//		for(int i=srcFileNameWithPath.length()-1 ;i>0 ;i--)
	//		{
	//			if(srcFileNameWithPath.charAt(i)=='.')
	//			{
	//				destinationFileNameWithPath = StringManipulation.appendStringAtIndex(srcFileNameWithPath, i+1, copy);
	//				Reporter.log("finalFile:" + destinationFileNameWithPath ,true);
	//				break;
	//			}
	//		}
	//		Assert.assertTrue(destinationFileNameWithPath!=null,"Error! destinationFileNameWithPath is null...");
	//
	//		//connect and open excel file
	//		wbook = Workbook.getWorkbook(new File(srcFileNameWithPath));
	//		wwbCopy = Workbook.createWorkbook(new File(destinationFileNameWithPath), wbook);
	//	}


































	//	// Methods for Apache POI
	//
	//	/** The g workbook. */
	//	public static Workbook gWORKBOOK = null;	
	//	
	//	/**
	//	 * Gets the sheet name.
	//	 *
	//	 * @param ExcelFilePath the excel file path
	//	 * @param SheetName the sheet name
	//	 * @return the sheet name
	//	 */
	//	public static Sheet getsheetName(String ExcelFilePath, String SheetName)
	//	{		
	//		Workbook wb = getExcelSheet(ExcelFilePath);
	//		Sheet sheet = null;
	//		try {
	//			sheet = wb.getSheet(SheetName);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return sheet;
	//	}
	//
	//	/**
	//	 * Gets the excel sheet.
	//	 *
	//	 * @param ExcelFilePath the excel file path
	//	 * @return the excel sheet
	//	 */
	//	// full path : eg. c:\data\test.xls
	//	public static Workbook getExcelSheet(String ExcelFilePath)
	//	{
	//
	//		//	Workbook workbook = null;
	//		try {
	//			gWORKBOOK = Workbook.getWorkbook(new File(ExcelFilePath));		
	//		} catch (BiffException e) {
	//			// TODO Auto-generated catch block
	//
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//			//return null;
	//		}
	//		return gWORKBOOK;
	//	}
	//
	//	/**
	//	 * Gets the row count.
	//	 *
	//	 * @param SheetName the sheet name
	//	 * @return the row count
	//	 */
	//	public static int getRowCount(Sheet SheetName)
	//	{
	//		return SheetName.getRows();
	//	}
	//	
	//	/**
	//	 * Gets the column name.
	//	 *
	//	 * @param SheetName the sheet name
	//	 * @param record the record
	//	 * @param columnName the column name
	//	 * @return the column name
	//	 */
	//	public static String getColumnName(Sheet SheetName,Cell[] record,String columnName)
	//	{
	//		try {
	//			Map<String,Integer> header = readHeader(SheetName);
	//			Integer index = header.get(columnName);
	//			return record[index].getContents();
	//		} catch (Exception e) {
	//			System.out.println(columnName + " Column is not exists, or could be empty");
	//		}	
	//		return "";
	//	}
	//	
	//	/**
	//	 * Read header.
	//	 *
	//	 * @param SheetName the sheet name
	//	 * @return the map
	//	 */
	//	public static Map<String,Integer> readHeader(Sheet SheetName)
	//	{
	//		Map<String,Integer> Record_Index = new HashMap<String,Integer>();
	//		Cell[] row = SheetName.getRow(0);
	//
	//
	//		for(int i=0;i<row.length;i++)
	//		{
	//			Record_Index.put(row[i].getContents(),i);
	//		}
	//		return Record_Index;
	//	}

}