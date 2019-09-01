/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.addon;

import java.util.List;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import jxl.write.WriteException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import automationHelper.seleniumappium.StringManipulation;

public class LinkChecker 
{
	/** The driver. */
	public static WebDriver driver;	

	public LinkChecker(WebDriver driver){
		this.driver=driver;
	}

	private static List findAllLinks() throws Exception
	{
		List<WebElement> elementList = driver.findElements(By.tagName("a"));
		elementList.addAll(driver.findElements(By.tagName("link")));


		List finalList = new ArrayList(); 
		for (WebElement element : elementList)
		{
			if(element.getAttribute("href")!= null)
			{
				finalList.add(element);
			}
		}	
		return finalList;
	}

	private static List findAllImageJsIfNgiLinks() throws Exception
	{
		List<WebElement> elementList = driver.findElements(By.tagName("img"));//image src
		elementList.addAll(driver.findElements(By.tagName("script")));//js = javascript src
		elementList.addAll(driver.findElements(By.tagName("iframe")));//if = iframe src
		elementList.addAll(driver.findElements(By.tagName("ng-include")));//ngi

		List finalList = new ArrayList(); 
		for (WebElement element : elementList)
		{
			if(element.getAttribute("src")!= null)
			{
				finalList.add(element);
			}
		}	
		return finalList;
	}

	private static String[] isLinkBroken(URL url) throws Exception
	{
		//url = new URL("http://yahoo.com");
		String requestMethod = "";
		String responseCode = "";
		String responseMessage = "";

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try
		{
			connection.connect();
			requestMethod = connection.getRequestMethod();
			responseCode = Integer.toString(connection.getResponseCode());
			responseMessage = connection.getResponseMessage();	        
			connection.disconnect();

			//System.out.println("     requestMethod:" + requestMethod + "    responseCode:" + responseCode + "    responseMessage:" + responseMessage);
			String result[]={requestMethod,responseCode,responseMessage};
			return result;
		}
		catch(Exception exp)
		{
			requestMethod = "";
			responseCode = "";
			responseMessage = exp.getMessage();
			String result[]={requestMethod,responseCode,responseMessage};
			return result;
		}  				
	}

	/**
	 * Broken link checker.
	 *<br><b>Advantages of in-house Broken Link Checker as compared to browser add-ons available(ex: Chrome add-on 'Link Checker'):</b>
	 *<ul>
	 *<li>Checks for broken links, Video links</li>
	 *<li><b>Link Crawler:</b> Total number of links crawled and checked is more than what browser add-on(like 'Link Checker') checks</li>
	 *<li><b>Complete website link checker solution:</b> As compared to browser add-on, you don't need to initialize add-on on each page you wish to get link checked. You can directly perform link checker for "N" number of pages at one click.</li>
	 *<li><b>Reporting:</b> Detailed automated report which can be assessed for broken links, empty links, missing link text, missing image alternate text, and other various parameters</li>
	 *</ul>
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param pageName the page title for reporting purpose
	 * @param reportSampleFileTemplatePath the report sample file template path. Ex:"D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\"
	 * @param reportSampleFileTemplateName the report sample file template name. Ex:"linkCheckerStatusTemplate.xls"
	 * @param siteDomainForAbsoluteLink the site base domain for absolute link. Ex:"http://www.abc.com/"
	 * @throws WriteException the write exception
	 * @throws Exception the exception
	 */
	public static void brokenLinkChecker(String pageName, String reportSampleFileTemplatePath, String reportSampleFileTemplateName, String siteDomainForAbsoluteLink) throws WriteException, Exception 
	{
		LinkCheckerReporting.connectExcel(pageName, reportSampleFileTemplatePath, reportSampleFileTemplateName);//open linkCheckerStatusTemplate for update

		//write pageName to excel report
		LinkCheckerReporting.setValueIntoCell("sheet1", 1, 4, pageName); //update #pageName
		
		//find totalLinks
		List <WebElement> allLinks = LinkChecker.findAllLinks();   
		int totalLinks = allLinks.size();
		Reporter.log("\n\n Total number of Link elements found " + totalLinks + "\n" ,true);
		LinkCheckerReporting.setValueIntoCell("sheet1", 1, 5, totalLinks); //update #totalLinks

		//display allLinks:
		int i=0;
		int rowNo=8;

		for( WebElement element : allLinks)
		{
			int no=000;
			String url=null;
			String tagName=null;
			String invalidRelativeLink;
			String hrefSrcAttributeisEmptyOrMissing="No";
			String emptyLinkText=null;
			String trailingHash="No";
			String tagWithoutHrefOrSrcAttribute=null;

			String statusReturned[]={"","",""}; //{requestMethod,responseCode,responseMessage}
			String errorAtAttribute=null;
			String exceptionOccured=null;

			try
			{
				rowNo++;
				i++;

				//no = Integer.toString(i);
				no=i;
				url = element.getAttribute("href"); //goes to 'catch' block if some error occurs
				tagName = element.getTagName(); //goes to 'catch' block if some error occurs
				
				//part1 - #invalidRelativeLink (hrefDoesntStartWith'Http' Or '..')
				if(url.contains("../")) //future update for: url.contains("./") 
				{
					url = url.replace("../", siteDomainForAbsoluteLink);
				}
				
				//statusReturned={requestMethod,responseCode,responseMessage}
				statusReturned = LinkChecker.isLinkBroken(new URL(url));
				
				//part2 - #invalidRelativeLink (hrefDoesntStartWith'Http' Or '..')    //if response code belongs to 4xx or 5xx series then link is broken
				if(LinkCheckerReporting.isStatusCodeOfXxxSeries(statusReturned[1], "4")|| LinkCheckerReporting.isStatusCodeOfXxxSeries(statusReturned[1], "5"))
					invalidRelativeLink="Yes";
				else
					invalidRelativeLink=statusReturned[2];

				//#href/SrcAttributeisEmptyOrMissing[emptyLink(hrefOrSrc="") or attribute itself is missing]
				Reporter.log("urlLength " + i + ": " + url.length() ,true);
				if(url.length()==0)
				{
					hrefSrcAttributeisEmptyOrMissing="Yes";
				}
				
				//#emptyLinkText
				if(element.getText().length()==0)
					emptyLinkText = "Yes";
				else
					emptyLinkText=element.getText();
				
				//#trailingHash
				if(url.length()!=0)//link not empty then
				{
					if(url.charAt(url.length()-1)=='#')//last char # then
						trailingHash="Yes";					
				}


				//display report on console
				Reporter.log("tagname " + i + ": " + tagName ,true);
				Reporter.log("href " + i + ": " + url ,true);
				Reporter.log("requestMethod " + i + ": " + statusReturned[0] ,true);
				Reporter.log("responseCode " + i + ": " + statusReturned[1] ,true);
				Reporter.log("responseMessage " + i + ":" + statusReturned[2] ,true);
				//#errorAtAttribute   will not come in try block
				//##exceptionOccured   will not come in try block
				Reporter.log("invalidRelativeLink " + i + ":" + invalidRelativeLink ,true);
				Reporter.log("hrefSrcAttributeisEmptyOrMissing " + i + ":" + hrefSrcAttributeisEmptyOrMissing,true);
				//#imageAltAttributeisEmptyOrMissing   will not come in this(ie, brokenLinkChecker) method
				Reporter.log("emptyLinkText " + i + ":" + emptyLinkText,true);
				Reporter.log("trailingHash " + i + ":" + trailingHash ,true);
				Reporter.log("getLocation " + i + ":" + element.getLocation() + "\n",true); //not needed for excel report


				//Reporter.log("URL: " + element.getAttribute("outerHTML")+ " returned " + isLinkBroken(new URL(element.getAttribute("href"))) ,true);

				//update excel report
				LinkCheckerReporting.setValueIntoCell("sheet1", 0, rowNo, no); //update #no column
				LinkCheckerReporting.setValueIntoCell("sheet1", 1, rowNo, url); //update #url column
				LinkCheckerReporting.setValueIntoCell("sheet1", 2, rowNo, tagName); //update #tagName column
				LinkCheckerReporting.setValueIntoCell("sheet1", 3, rowNo, statusReturned[0]); //update #requestMethod column
				LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, Integer.parseInt(statusReturned[1])); //update #responseCode column
				LinkCheckerReporting.setValueIntoCell("sheet1", 5, rowNo, statusReturned[2]); //update #responseMessage column
				LinkCheckerReporting.setValueIntoCell("sheet1", 8, rowNo, invalidRelativeLink); //update #invalidRelativeLink column
				LinkCheckerReporting.setValueIntoCell("sheet1", 9, rowNo, hrefSrcAttributeisEmptyOrMissing); //update #hrefSrcAttributeisEmptyOrMissing column
				LinkCheckerReporting.setValueIntoCell("sheet1", 11, rowNo, emptyLinkText); //update #emptyLinkText column
				LinkCheckerReporting.setValueIntoCell("sheet1", 12, rowNo, trailingHash); //update #trailingHash column
			}
			catch(Exception exp)
			{
				errorAtAttribute = element.getAttribute("innerHTML");
				exceptionOccured = exp.getMessage();
				
				//#href/SrcAttributeisEmptyOrMissing[emptyLink(hrefOrSrc="") or attribute itself is missing]
				Reporter.log("urlLength " + i + ": " + url.length() ,true);
				if(url.length()==0)
				{
					hrefSrcAttributeisEmptyOrMissing="Yes";
				}
				
				//#emptyLinkText
				if(element.getText().length()==0)
					emptyLinkText = "Yes";
				else
					emptyLinkText=element.getText();
				
				//#trailingHash 
				if(url.length()!=0)//link not empty then
				{
					if(url.charAt(url.length()-1)=='#')//last char # then
						trailingHash="Yes";					
				}


				//display report on console
				Reporter.log("tagname " + i + ": " + tagName ,true);
				Reporter.log("href " + i + ": " + url ,true);
				Reporter.log("requestMethod " + i + ": " + statusReturned[0] ,true);
				Reporter.log("responseCode " + i + ": " + statusReturned[1] ,true);
				Reporter.log("responseMessage " + i + ":" + statusReturned[2] ,true);
				Reporter.log("Error! At Inner HTML:" + errorAtAttribute + " Exception occured:" + exceptionOccured + "\n" ,true);
				//#invalidRelativeLink    will not come in catch block
				Reporter.log("hrefSrcAttributeisEmptyOrMissing " + i + ":" + hrefSrcAttributeisEmptyOrMissing,true);
				//#emptyImageAltAttributeValue   will not come in this(ie, brokenLinkChecker) method
				Reporter.log("emptyLinkText " + i + ":" + emptyLinkText,true);
				Reporter.log("trailingHash " + i + ":" + trailingHash ,true);
				Reporter.log("getLocation " + i + ":" + element.getLocation() + "\n",true); //not needed for excel report

				
				//update excel report
				LinkCheckerReporting.setValueIntoCell("sheet1", 0, rowNo, no); //update #no column
				LinkCheckerReporting.setValueIntoCell("sheet1", 1, rowNo, url); //update #url column
				LinkCheckerReporting.setValueIntoCell("sheet1", 2, rowNo, tagName); //update #tagName column
				LinkCheckerReporting.setValueIntoCell("sheet1", 3, rowNo, statusReturned[0]); //update #requestMethod column
				if(statusReturned[1].equals(""))
					LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, statusReturned[1]); //update #responseCode column with 0 as String value
				else
					LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, Integer.parseInt(statusReturned[1])); //update #responseCode column as int value
				LinkCheckerReporting.setValueIntoCell("sheet1", 5, rowNo, statusReturned[2]); //update #responseMessage column
				LinkCheckerReporting.setValueIntoCell("sheet1", 6, rowNo, errorAtAttribute); //#errorAtAttribute
				LinkCheckerReporting.setValueIntoCell("sheet1", 7, rowNo, exceptionOccured); //#exceptionOccured
				LinkCheckerReporting.setValueIntoCell("sheet1", 9, rowNo, hrefSrcAttributeisEmptyOrMissing); //#hrefSrcAttributeisEmptyOrMissing
				LinkCheckerReporting.setValueIntoCell("sheet1", 11, rowNo, emptyLinkText); //#emptyLinkText
				LinkCheckerReporting.setValueIntoCell("sheet1", 12, rowNo, trailingHash); //#trailingHash
			}
		}//end for
		LinkCheckerReporting.closeFile(); //close linkCheckerStatusTemplate after update

	}



	/**
	 * Broken Image link checker.
	 *<br><b>Advantages of in-house Broken Link Checker as compared to browser add-ons available(ex: Chrome addon 'Link Checker'):</b>
	 *<ul>
	 *<li>Checks for Image links, iFrame Links, JavaScript Links</li>
	 *<li><b>Link Crawler:</b> Total number of links crawled and checked is more than what browser addon(like 'Link Chcker') checks</li>
	 *<li><b>Complete website link checker solution:</b> As compared to browser add-on, you don't need to initialize addon on each page you wish to get link checked. You can directly perform link checker for "N" number of pages at one click.</li>
	 *<li><b>Reporting:</b> Detailed automated report which can be assessed for broken links, empty links, missing link text, missing image alternate text, and other various parameters</li>
	 *</ul>
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param pageName the page title for reporting purpose
	 * @param reportSampleFileTemplatePath the report sample file template path. Ex:"D:\\Softwares\\workspacePersonal\\BTAC_SeleniumAppiumLibrary\\linkCheckerReports\\"
	 * @param reportSampleFileTemplateName the report sample file template name. Ex:"linkCheckerStatusTemplate.xls"
	 * @param siteDomainForAbsoluteLink the site base domain for absolute link. Ex:"http://www.abc.com/"
	 * @throws WriteException the write exception
	 * @throws Exception the exception
	 */
	public static void brokenImageLinkChecker(String pageName, String reportSampleFileTemplatePath, String reportSampleFileTemplateName, String siteDomainForAbsoluteLink) throws WriteException, Exception
	{
		reportSampleFileTemplateName = "Image" + reportSampleFileTemplateName;
		LinkCheckerReporting.connectExcel(pageName, reportSampleFileTemplatePath, reportSampleFileTemplateName);//open linkCheckerStatusTemplate for update

		//write pageName to excel report
		LinkCheckerReporting.setValueIntoCell("sheet1", 1, 4, pageName); //update #pageName

		//find totalImageLinks
		List <WebElement> allImageLinks = LinkChecker.findAllImageJsIfNgiLinks();   
		int totalImageLinks = allImageLinks.size();
		Reporter.log("Total number of ImageLink elements found " + totalImageLinks + "\n" ,true);
		LinkCheckerReporting.setValueIntoCell("sheet1", 1, 6, totalImageLinks); //update #totalImageLinks


		//display allLinks:
		int i=0;
		int rowNo=8;

		for( WebElement element : allImageLinks)
		{
			int no=000;
			String url=null;
			String tagName=null;
			String invalidRelativeLink;
			String hrefSrcAttributeisEmptyOrMissing="No";
			String imageAlt=null;
			String imageAltAttributeisEmptyOrMissing="No";
			String emptyLinkText=null;
			String trailingHash="No";
			String tagWithoutHrefOrSrcAttribute=null;

			String statusReturned[]={"","",""}; //{requestMethod,responseCode,responseMessage}
			String errorAtAttribute=null;
			String exceptionOccured=null;

			try
			{
				rowNo++;
				i++;
				//no = Integer.toString(i);
				no = i;
				tagName = element.getTagName(); //goes to 'catch' block if some error occurs
				url = element.getAttribute("src"); //goes to 'catch' block if some error occurs
				imageAlt = element.getAttribute("alt"); //goes to 'catch' block if some error occurs

				//convert relative link to absolute
				if(tagName.equals("ng-include"))
				{
					url=StringManipulation.removeSingleQuoteFromString(url);//removeQuoteFromString
					url = siteDomainForAbsoluteLink + url; //convert relative link to absolute
				}

				//part1 - #invalidRelativeLink (hrefDoesntStartWith'Http' Or '..')
				if(url.contains("../")) //future update for: url.contains("./") 
				{
					url = url.replace("../", siteDomainForAbsoluteLink);
				}
				
				//statusReturned={requestMethod,responseCode,responseMessage}
				statusReturned = LinkChecker.isLinkBroken(new URL(url));

				//part2 - #invalidRelativeLink (hrefDoesntStartWith'Http' Or '..')    //if response code belongs to 4xx or 5xx series then link is broken
				if(LinkCheckerReporting.isStatusCodeOfXxxSeries(statusReturned[1], "4")|| LinkCheckerReporting.isStatusCodeOfXxxSeries(statusReturned[1], "5"))
					invalidRelativeLink="Yes";
				else
					invalidRelativeLink=statusReturned[2];

				//#href/SrcAttributeisEmptyOrMissing[emptyLink(hrefOrSrc="") or attribute itself is missing]
				Reporter.log("urlLength " + i + ": " + url.length() ,true);
				if(url.length()==0)
				{
					hrefSrcAttributeisEmptyOrMissing="Yes";
				}
				
				//#imageAltAttributeisEmptyOrMissing
				Reporter.log("imageAltLength " + i + ": " + imageAlt.length() ,true);
				if(imageAlt.equals("null"))
				{
					imageAltAttributeisEmptyOrMissing="Yes";
				}
				else
					imageAltAttributeisEmptyOrMissing=imageAlt;	
				
				//#emptyLinkText
				if(element.getText().length()==0)
					emptyLinkText = "Yes";
				else
					emptyLinkText=element.getText();
				
				//#trailingHash
				if(url.length()!=0)//link not empty then
				{
					if(url.charAt(url.length()-1)=='#')//last char # then
						trailingHash="Yes";					
				}
				
				//display report on console
				Reporter.log("tagname " + i + ": " + tagName ,true);
				Reporter.log("src " + i + ": " + url ,true);
				Reporter.log("requestMethod " + i + ": " + statusReturned[0] ,true);
				Reporter.log("responseCode " + i + ": " + statusReturned[1] ,true);
				Reporter.log("responseMessage " + i + ":" + statusReturned[2] ,true);
				//#errorAtAttribute   will not come in try block
				//##exceptionOccured   will not come in try block
				Reporter.log("invalidRelativeLink " + i + ":" + invalidRelativeLink ,true);
				Reporter.log("hrefSrcAttributeisEmptyOrMissing " + i + ":" + hrefSrcAttributeisEmptyOrMissing,true);
				Reporter.log("imageAltAttributeisEmptyOrMissing " + i + ":" + imageAltAttributeisEmptyOrMissing,true);
				Reporter.log("emptyLinkText " + i + ":" + emptyLinkText,true);
				Reporter.log("trailingHash " + i + ":" + trailingHash ,true);
				Reporter.log("getLocation " + i + ":" + element.getLocation() + "\n",true); //not needed for excel report
				//Reporter.log("URL: " + element.getAttribute("outerHTML")+ " returned " + isLinkBroken(new URL(element.getAttribute("src"))) ,true);
				
				
				//update excel report
				LinkCheckerReporting.setValueIntoCell("sheet1", 0, rowNo, no); //update #no column
				LinkCheckerReporting.setValueIntoCell("sheet1", 1, rowNo, url); //update #url column
				LinkCheckerReporting.setValueIntoCell("sheet1", 2, rowNo, tagName); //update #tagName column
				LinkCheckerReporting.setValueIntoCell("sheet1", 3, rowNo, statusReturned[0]); //update #requestMethod column
				LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, Integer.parseInt(statusReturned[1])); //update #responseCode column
				LinkCheckerReporting.setValueIntoCell("sheet1", 5, rowNo, statusReturned[2]); //update #responseMessage column
				//#errorAtAttribute    will not come in try block
				//#exceptionOccured    will not come in try block
				LinkCheckerReporting.setValueIntoCell("sheet1", 8, rowNo, invalidRelativeLink); //update #invalidRelativeLink column
				LinkCheckerReporting.setValueIntoCell("sheet1", 9, rowNo, hrefSrcAttributeisEmptyOrMissing); //update #hrefSrcAttributeisEmptyOrMissing column
				LinkCheckerReporting.setValueIntoCell("sheet1", 10, rowNo, imageAltAttributeisEmptyOrMissing); //update #imageAltAttributeisEmptyOrMissing column
				LinkCheckerReporting.setValueIntoCell("sheet1", 11, rowNo, emptyLinkText); //update #emptyLinkText column
				LinkCheckerReporting.setValueIntoCell("sheet1", 12, rowNo, trailingHash); //update #trailingHash column
			}
			catch(Exception exp)
			{
				errorAtAttribute = element.getAttribute("innerHTML");
				exceptionOccured = exp.getMessage();

				//#href/SrcAttributeisEmptyOrMissing[emptyLink(hrefOrSrc="") or attribute itself is missing]
				Reporter.log("urlLength " + i + ": " + url.length() ,true);
				if(url.length()==0)
				{
					hrefSrcAttributeisEmptyOrMissing="Yes";
				}
				
				//#imageAltAttributeisEmptyOrMissing       [emptyAltText(alt="") or attribute itself is missing]
				imageAlt = element.getAttribute("alt"); //goes to 'catch' block if some error occurs
				System.out.println("imageAltText:" + i + imageAlt);
				if(imageAlt == null)
				{
					Reporter.log("imageAltLength " + i + ": null" ,true);
					imageAltAttributeisEmptyOrMissing="Yes";
				}
				else
					imageAltAttributeisEmptyOrMissing=imageAlt;					

				
				//#emptyLinkText
				if(element.getText().length()==0)
					emptyLinkText = "Yes";
				else
					emptyLinkText=element.getText();
				
				//#trailingHash 
				if(url.length()!=0)//link not empty then
				{
					if(url.charAt(url.length()-1)=='#')//last char # then
						trailingHash="Yes";					
				}

				//display report on console
				Reporter.log("tagname " + i + ": " + tagName ,true);
				Reporter.log("href " + i + ": " + url ,true);
				Reporter.log("requestMethod " + i + ": " + statusReturned[0] ,true);
				Reporter.log("responseCode " + i + ": " + statusReturned[1] ,true);
				Reporter.log("responseMessage " + i + ":" + statusReturned[2] ,true);
				Reporter.log("Error! At Inner HTML:" + errorAtAttribute + " Exception occured:" + exceptionOccured + "\n" ,true);
				//#invalidRelativeLink    will not come in catch block
				Reporter.log("hrefSrcAttributeisEmptyOrMissing " + i + ":" + hrefSrcAttributeisEmptyOrMissing,true);
				Reporter.log("imageAltAttributeisEmptyOrMissing " + i + ":" + imageAltAttributeisEmptyOrMissing,true);
				Reporter.log("emptyLinkText " + i + ":" + emptyLinkText,true);
				Reporter.log("trailingHash " + i + ":" + trailingHash ,true);
				Reporter.log("getLocation " + i + ":" + element.getLocation() + "\n",true); //not needed for excel report

				
				//update excel report
				LinkCheckerReporting.setValueIntoCell("sheet1", 0, rowNo, no); //update #no column
				LinkCheckerReporting.setValueIntoCell("sheet1", 1, rowNo, url); //update #url column
				LinkCheckerReporting.setValueIntoCell("sheet1", 2, rowNo, tagName); //update #tagName column
				LinkCheckerReporting.setValueIntoCell("sheet1", 3, rowNo, statusReturned[0]); //update #requestMethod column
				if(statusReturned[1].equals(""))
					LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, statusReturned[1]); //update #responseCode column with 0 as String value
				else
					LinkCheckerReporting.setValueIntoCell("sheet1", 4, rowNo, Integer.parseInt(statusReturned[1])); //update #responseCode column as int value

				LinkCheckerReporting.setValueIntoCell("sheet1", 5, rowNo, statusReturned[2]); //update #responseMessage column
				LinkCheckerReporting.setValueIntoCell("sheet1", 6, rowNo, errorAtAttribute); //#errorAtAttribute
				LinkCheckerReporting.setValueIntoCell("sheet1", 7, rowNo, exceptionOccured); //#exceptionOccured
				//#invalidRelativeLink    will not come in catch block
				LinkCheckerReporting.setValueIntoCell("sheet1", 9, rowNo, hrefSrcAttributeisEmptyOrMissing); //#hrefSrcAttributeisEmptyOrMissing
				LinkCheckerReporting.setValueIntoCell("sheet1", 10, rowNo, imageAltAttributeisEmptyOrMissing); //#imageAltAttributeisEmptyOrMissing
				LinkCheckerReporting.setValueIntoCell("sheet1", 11, rowNo, emptyLinkText); //#emptyLinkText
				LinkCheckerReporting.setValueIntoCell("sheet1", 12, rowNo, trailingHash); //#trailingHash
			}
		}//end for
		LinkCheckerReporting.closeFile(); //close linkCheckerStatusTemplate after update
	}
}


