/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Adil Imroz, Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 * **********************************************
 * Modified by: Yagnesh Shah
 * Date: March 13th 2015
 * Modification: 
 * 		a. Updated code to parse Testng Report without hyperlink for Test Names.
 * 		b. Configured all variables as data-driven to easily configure from config.properties file
 * 		c. Google login via oAuth2.0 since old version is deprecated
 */

package automationHelper.seleniumappium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.Reporter;
import org.testng.annotations.Test;

//imports for oAuth2.0 login code for Google:
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.spreadsheet.CellQuery;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;


// TODO: Auto-generated Javadoc
/**
 * The Class ReportParseGoogleUpdateForTestngReportWithoutLinks.
 */
public class ReportParseGoogleUpdateForTestngReportWithoutLinks {

	/** The spreadsheet feed url. */
	public static URL SPREADSHEET_FEED_URL;

	/** The client id. */
	public static String googleClientID;

	/** The p12. */
	public static File googleP12File;

	/** The service. */
	private static SpreadsheetService service;

	/** The factory. */
	private static FeedURLFactory factory;

	/** The url. */
	private static URL url;

	/** The spread sheet demo. */
	public static ReportParseGoogleUpdateForTestngReportWithoutLinks spreadSheetDemo;

	/** The spreadsheet. */
	public static SpreadsheetEntry spreadsheet;

	/** The worksheet entry. */
	public static WorksheetEntry worksheetEntry;

	/** The worksheet feed. */
	public static WorksheetFeed worksheetFeed;

	/** The cell feed. */
	public static CellFeed cellFeed;

	/** The query. */
	public static SpreadsheetQuery query;

	/** The row from search. */
	static int rowFromSearch;

	/** The col from search. */
	static int colFromSearch;

	/** The search arr. */
	static List<String> searchArr=new ArrayList<String>();//for storing values of each cell from top-down & left-right from google sheet

	/** The objects. */
	static List<Object> objects = new ArrayList<Object>();//for storing cell 

	/** The google report parser execution flag. */
	public static String googleReportParserExecutionFlag;

	/** The spreadsheet name. */
	public static String spreadsheetName;		
	//	public static String parserGoogleEmail; //Client login deprecated by Google...using oAuth2.0 now which doesn't require credentials
	/** The parser index spreadsheet. */
	//	public static String parserGooglePassword; //Client login deprecated by Google...using oAuth2.0 now which doesn't require credentials
	public static int parserIndexSpreadsheet;

	/** The parser index worksheet. */
	public static int parserIndexWorksheet;

	/** The parser input file path. */
	public static String parserAttachmentPath;

	/** The parser spreadsheet title. */
	public static String parserSpreadsheetTitle;

	/**
	 * Report parsing google doc update.
	 *
	 * @author Adil Imroz, Yagnesh Shah - yash.shah.g@gmail.com / yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	@Test
	public static void ReportParsingGoogleDocUpdate() throws Exception 
	{
		Reporter.log("Script to parse test report and update google doc with results",true);

		googleReportParserExecutionFlag = FilesAndFolders.getPropValue("googleReportParserExecutionFlag");

		googleClientID = FilesAndFolders.getPropValue("googleClientID");
		googleP12File = new File(FilesAndFolders.getPropValue("googleP12File"));
		
		//spreadsheetName = prop.getProperty("spreadsheetName");		
		//		parserGoogleEmail = prop.getProperty("parserGoogleEmail"); //Client login deprecated by Google...using oAuth2.0 now which doesn't require credentials
		//		parserGooglePassword = prop.getProperty("parserGooglePassword"); //Client login deprecated by Google...using oAuth2.0 now which doesn't require credentials

		parserSpreadsheetTitle = FilesAndFolders.getPropValue("parserSpreadsheetTitle");
		//parserIndexSpreadsheet = Integer.parseInt(FilesAndFolders.getPropValue("parserIndexSpreadsheet"));
		parserIndexWorksheet = Integer.parseInt(FilesAndFolders.getPropValue("parserIndexWorksheet"));
		parserAttachmentPath = FilesAndFolders.getPropValue("parserAttachmentPath");

		Reporter.log("googleReportParserExecutionFlag:" + googleReportParserExecutionFlag, true);
		if(googleReportParserExecutionFlag.contentEquals("true"))
		{
			//Auth to access google doc ---deprecated by Google now...use oAuth2.0 now
			//spreadSheetDemo = new ReportParseGoogleUpdateForTestngReportWithoutLinks(parserGoogleEmail, parserGooglePassword); //gmail account via which doc will be accessed for updating test results

			//Auth to access google doc via oAuth2.0 
			//spreadSheetDemo = new ReportParseGoogleUpdateForTestngReportWithoutLinks();
			spreadsheet = googleSpreadsheetLogin();
		}
		else
		{
			Reporter.log("",true);
			Reporter.log("googleReportParserExecution feature is turned off via googleReportParserExecutionFlag in config.properties file...",true);
		}
	}


	/*
	 * Method Definitions
	 */
	//Method for authentication to access google doc ----deprecated now by Google...Use oAuth2.0 now
	
	
	//Constructor
//	public ReportParseGoogleUpdateForTestngReportWithoutLinks(String username, String password) throws AuthenticationException 
//	{
//		service = new SpreadsheetService("SpreadSheet-Demo");
//		factory = FeedURLFactory.getDefault();        
//		Reporter.log("Authenticating......",true);
//		service.setUserCredentials(username, password);
//		Reporter.log("Successfully authenticated",true);
//	}



	//Method for authentication to access google doc via oAuth2.0
	/*
		reference read: http://stackoverflow.com/questions/30483601/create-spreadsheet-using-google-spreadsheet-api-in-google-drive-in-java
	 	Steps to follow for oAuth2.0:
		Register at https://console.developers.google.com
		Create new project
		Under APIs & Auth -> Credential -> Create New Client ID for Service Account
		When the Client ID is generated You have to generate P12 key.
		Client id will be needed in code below, Email addres is the addres You have to share Your spreadsheet
	 */
	//constructor
	//	public ReportParseGoogleUpdateForTestngReportWithoutLinks() throws MalformedURLException, GeneralSecurityException, IOException, ServiceException  
	//	{
	//		googleSpreadsheetLogin();
	//	}


	//	//Google spreadsheets connection via oAuth2.0
	public static SpreadsheetEntry googleSpreadsheetLogin() throws MalformedURLException, GeneralSecurityException, IOException, ServiceException, URISyntaxException, Exception 
	{
		Reporter.log("inside googleSpreadsheetLogin",true);
		// Define the URL to request.  This should never change.
		SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/public/full");

		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		String[] SCOPESArray = {"https://spreadsheets.google.com/feeds", "https://spreadsheets.google.com/feeds/spreadsheets/public/full", "https://docs.google.com/feeds"};
		final List SCOPES = Arrays.asList(SCOPESArray);
		GoogleCredential credential = new GoogleCredential.Builder()
		.setTransport(httpTransport)
		.setJsonFactory(jsonFactory)
		.setServiceAccountId(googleClientID)
		.setServiceAccountScopes(SCOPES)
		.setServiceAccountPrivateKeyFromP12File(googleP12File)
		.build();		

		SpreadsheetService service = new SpreadsheetService("Google Spreadsheet Update...");

		factory = FeedURLFactory.getDefault();
		//	        String key = "1arJFUxSghwdv0QpnJJ3lBu36X4I3d_uB4xx_xNGLKHU";
		//	        URL spreadSheetUrl = factory.getWorksheetFeedUrl(key, "public", "full");
		//	        WorksheetFeed feed = service.getFeed(spreadSheetUrl, WorksheetFeed.class);
		Reporter.log("Authenticating...",true);
		service.setOAuth2Credentials(credential);
		Reporter.log("Successfully authenticated",true);

		// Make a request to the API and get all spreadsheets...
		//SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		SpreadsheetFeed feed = service.getFeed(factory.getSpreadsheetsFeedUrl(), SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			Reporter.log("Error! No spreadsheets found...",true);
		}

		//Choose a spreadsheet more intelligently based on your app's needs.
		int flag=0;
		SpreadsheetEntry spreadsheet = null;
		for (int i = 0; i < spreadsheets.size(); i++) 
		{
			System.out.println(spreadsheets.get(i).getTitle().getPlainText() + "//" + parserSpreadsheetTitle);
			if (spreadsheets.get(i).getTitle().getPlainText().startsWith(parserSpreadsheetTitle)) 
			{
				spreadsheet = spreadsheets.get(i);
				Reporter.log("Name of editing spreadsheet: " + spreadsheets.get(i).getTitle().getPlainText(),true);
				Reporter.log("SpreadSheet index in Drive: " + i,true);
				flag=1;
				break;
			}
		}
		
		//Assert.assertTrue("Spreadsheet with matching title: " + parserSpreadsheetTitle + " doesn't exist!!!",flag==1);
		if(flag==0)
		{
			Reporter.log("Spreadsheet with matching title: " + parserSpreadsheetTitle + " doesn't exist!!!",true);
		}

		
		// Get the first worksheet of the first spreadsheet.
		//spreadsheet = getSpreadSheet(parserIndexSpreadsheet);//give index to select the spreadsheet from the folder
		worksheetEntry = spreadsheet.getWorksheets().get(parserIndexWorksheet); //providing index to access the desired worksheet
		Reporter.log("Title of spreadsheet being updated :: "+spreadsheet.getTitle().getPlainText(),true);
		Reporter.log("Title of worksheet being updated :: "+worksheetEntry.getTitle().getPlainText(),true);


		url = worksheetEntry.getCellFeedUrl();
		query = new SpreadsheetQuery(url);
		cellFeed = service.query(query, CellFeed.class);

		//Parsing the Test report from desired folder
		File input = new File(parserAttachmentPath);
		Reporter.log("Starting ....",true);
		Document doc = Jsoup.parse(input,null);
		Reporter.log("midway ....",true);
		//use below xpath for TestNg reports via Ant execution & if it contains hyperlinks for Test names
		//Elements testNames = doc.select("html>body>table>tbody>tr>td>a[href~=#t]");//gets the name of the test names from the report
		Elements testNames = doc.select("html>body>table>tbody>tr>td[style~=text-align]");//gets the name of the test names from the report
		Elements testNameSiblings = doc.select("html>body>table>tbody>tr>td[class~=num]");//gets the siblings of all the test names...they are the test  results numbers
		Reporter.log("testNames size ::"+testNames.size(),true);
		Reporter.log("testNameSiblings size :: "+testNameSiblings.size(),true);

		for (CellEntry entry : cellFeed.getEntries()) {
			searchArr.add(entry.getCell().getInputValue()); //stores values of each cell from top-down & left-right from google sheet
			objects.add(entry.getCell());
		}
		Reporter.log("searchArr:\n" +searchArr,true); //prints values of each cell from top-down & left-right from google sheet
		Reporter.log("objects:\n" + objects,true);

		int j =1;//from 2 column onwards

		//for 11 testNames, there are 112 testNameSiblings. Now we need only 11rows*7columns=77 testNameSiblings. 
		for (int i = 0; i < testNames.size(); i++) {	

			Reporter.log("test>"+testNames.get(i).text(),true);
			Reporter.log("Passed:" + testNameSiblings.get(j).text(),true);
			int passed = Integer.parseInt(testNameSiblings.get(j).text());	
			j=j+1; //2nd column
			Reporter.log("skipped:" + testNameSiblings.get(j).text(),true);
			int skipped = Integer.parseInt(testNameSiblings.get(j).text());
			j=j+1;//3rd column
			Reporter.log("failed:" + testNameSiblings.get(j).text(),true);
			int failed = Integer.parseInt(testNameSiblings.get(j).text());
			j=j+1;//4th column
			Reporter.log("testTime:"+testNameSiblings.get(j).text(),true);
			String testTime = testNameSiblings.get(j).text();
			j=j+4;//skip 5,6,7th & then 1st column. shifting again to 2nd column
			Reporter.log("******************************",true);

			if (passed!=0) {
				String fullTextSearchString = testNames.get(i).text();
				CellQuery query = new CellQuery(url);
				query.setFullTextQuery(fullTextSearchString);
				CellFeed cfeed = service.query(query, CellFeed.class);

				Reporter.log("Results for [" + fullTextSearchString + "]",true);

				for (CellEntry entry : cfeed.getEntries()) {
					CellEntry cell = entry;
					String shortId = cell.getId().substring(cell.getId().lastIndexOf('/') + 1);
					Reporter.log(" -- Cell(" + shortId + "/" + cell.getTitle().getPlainText()+ ") formula(" + cell.getCell().getInputValue() + ") numeric("+ cell.getCell().getNumericValue() + ") value("+ cell.getCell().getValue() + ")",true);
					rowFromSearch = cell.getCell().getRow();
					colFromSearch = cell.getCell().getCol();
					Reporter.log("row :: "+rowFromSearch,true);
					Reporter.log("col :: "+colFromSearch,true);
				}



				URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
				CellEntry newentry = new CellEntry(rowFromSearch, colFromSearch+2, "PASSED");
				service.insert(cellFeedUrl, newentry);
				Reporter.log("Cell Updated!",true);
			}

			else if (skipped!=0) 
			{					
				String fullTextSearchString = testNames.get(i).text();
				CellQuery query = new CellQuery(url);
				query.setFullTextQuery(fullTextSearchString);
				CellFeed cfeed = service.query(query, CellFeed.class);

				Reporter.log("Results for [" + fullTextSearchString + "]",true);

				for (CellEntry entry : cfeed.getEntries()) {
					CellEntry cell = entry;
					String shortId = cell.getId().substring(cell.getId().lastIndexOf('/') + 1);
					Reporter.log(" -- Cell(" + shortId + "/" + cell.getTitle().getPlainText()+ ") formula(" + cell.getCell().getInputValue() + ") numeric("+ cell.getCell().getNumericValue() + ") value("+ cell.getCell().getValue() + ")",true);
					rowFromSearch = cell.getCell().getRow();
					colFromSearch = cell.getCell().getCol();
					Reporter.log("row :: "+rowFromSearch,true);
					Reporter.log("col :: "+colFromSearch,true);
				}



				URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
				CellEntry newEntry = new CellEntry(rowFromSearch, colFromSearch+2, "SKIPPED");
				service.insert(cellFeedUrl, newEntry);
				Reporter.log("Cell Updated!",true);
			}

			else if (failed!=0) 
			{
				String fullTextSearchString = testNames.get(i).text();
				CellQuery query = new CellQuery(url);
				query.setFullTextQuery(fullTextSearchString);
				CellFeed cfeed = service.query(query, CellFeed.class);

				Reporter.log("Results for [" + fullTextSearchString + "]",true);

				for (CellEntry entry : cfeed.getEntries()) {
					CellEntry cell = entry;
					String shortId = cell.getId().substring(cell.getId().lastIndexOf('/') + 1);
					Reporter.log(" -- Cell(" + shortId + "/" + cell.getTitle().getPlainText()+ ") formula(" + cell.getCell().getInputValue() + ") numeric("+ cell.getCell().getNumericValue() + ") value("+ cell.getCell().getValue() + ")",true);
					rowFromSearch = cell.getCell().getRow();
					colFromSearch = cell.getCell().getCol();
					Reporter.log("row :: "+rowFromSearch,true);
					Reporter.log("col :: "+colFromSearch,true);
				}



				URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
				CellEntry newEntry = new CellEntry(rowFromSearch, colFromSearch+2, "FAILED");
				service.insert(cellFeedUrl, newEntry);
				Reporter.log("Cell Updated!",true);
			}
		}

		return spreadsheet;

	}	

	//	//Method to get all the spreadsheets associated to the given account
	//	public static void GetAllSpreadsheets() throws IOException, ServiceException{
	//		//SpreadsheetFeed feed = service.getFeed(factory.getSpreadsheetsFeedUrl(), SpreadsheetFeed.class);
	//		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	//		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
	//
	//		Reporter.log("Total number of SpreadSheets found : " + spreadsheets.size(),true);
	//		for (int i = 0; i < spreadsheets.size(); ++i) {
	//			Reporter.log("("+i+") : "+spreadsheets.get(i).getTitle().getPlainText(),true);
	//		}
	//	}	
	//
	//	// Returns spreadsheet
	//	public static SpreadsheetEntry getSpreadSheet(int spreadsheetIndex) throws IOException, ServiceException {
	//		//SpreadsheetFeed feed = service.getFeed(factory.getSpreadsheetsFeedUrl(), SpreadsheetFeed.class);
	//		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	//		SpreadsheetEntry spreadsheet = feed.getEntries().get(spreadsheetIndex);
	//		return spreadsheet;
	//	}
	//
	//	//Method to get Spread sheet details
	//	public static void GetSpreadsheetDetails(SpreadsheetEntry spreadsheet) throws IOException, ServiceException{ 
	//		Reporter.log("SpreadSheet Title : "+spreadsheet.getTitle().getPlainText(),true);
	//		List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();
	//		for (int i = 0; i < worksheets.size(); ++i) {
	//			WorksheetEntry worksheetEntry = worksheets.get(i);
	//			Reporter.log("("+i+") Worksheet Title : "+worksheetEntry.getTitle().getPlainText()+", num of Rows : "+worksheetEntry.getRowCount()+", num of Columns : "+worksheetEntry.getColCount(),true);
	//		}
	//
	//	}



	public static void CellContentUpdate(WorksheetEntry worksheetEntry,int row, int col, String formulaOrValue) throws IOException, ServiceException, Exception
	{
		URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
		CellEntry newEntry = new CellEntry(row, col, formulaOrValue);
		service.insert(cellFeedUrl, newEntry);
		Reporter.log("Cell Updated!",true);

	}

	//		public static void searchTestNameGDoc(String testNameToSearch) throws IOException, ServiceException{
	//			
	//			CellFeed feed = service.query(query, CellFeed.class);
	//
	//			for (CellEntry entry : feed.getEntries()) {
	//				searchArr.add(entry.getCell().getInputValue());
	//				objects.add(entry.getCell());
	//			}
	//			
	//			for (int i = 0; i < objects.size(); i++) {
	//				if (searchArr.get(i)=="BalanaceEnquiry_MandatoryField" ){
	//					rowFromSearch = feed.getEntries().get(i).getCell().getRow();
	//					colFromSearch = feed.getEntries().get(i).getCell().getCol();
	//					break;
	//				}
	//				
	//			}
	//			
	//		}

	//		public static void printCell(CellEntry cell) {
	//		  	Reporter.log(cell.getCell().getValue(),true);
	//  }


	public static void printCell(CellEntry cell) throws Exception 
	{
		String shortId = cell.getId().substring(cell.getId().lastIndexOf('/') + 1);
		Reporter.log(" -- Cell(" + shortId + "/" + cell.getTitle().getPlainText()+ ") formula(" + cell.getCell().getInputValue() + ") numeric("+ cell.getCell().getNumericValue() + ") value("+ cell.getCell().getValue() + ")",true);
		rowFromSearch = cell.getCell().getRow();
		colFromSearch = cell.getCell().getCol();
		Reporter.log("row :: "+rowFromSearch,true);
		Reporter.log("col :: "+colFromSearch,true);
	}


	public static void search(String fullTextSearchString) throws IOException,ServiceException 
	{

	}

}
