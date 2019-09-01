package automationHelper.addon;

import java.io.File;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationHelper.sa.CurrencyDateTime;
import automationHelper.sa.FilesAndFolders;
import automationHelper.sa.SmsEmailNotification;
import automationHelper.sa.StringManipulation;

public class sendBulkMailsForTesting {

	public static void sendMails(int emailToBeSentInBulkCount,String mailSubject,String sourceFileTemplatePath,String destinationFilePath,String[] fileNamesForAttachment) throws Exception
	{	
		System.out.println("************************sendBulkMailsForTesting started***************************");

		
		for(int i=0;i<emailToBeSentInBulkCount;i++)
		{
			System.out.println("\n\nLoop:" + i);
			//allocate file name
			
			String dateTimeStamp = CurrencyDateTime.getCurrentDateTimeStamp();
			dateTimeStamp = dateTimeStamp + "_";

			for(int j=0; j<fileNamesForAttachment.length; j++)
			{
				//file name 1 for attachment 1
				String reportSampleFileTemplateName=fileNamesForAttachment[j];
				String oldFileNameWithPath = sourceFileTemplatePath + reportSampleFileTemplateName;
				String newFileNameWithPath = generateFileNameWithPath(dateTimeStamp, destinationFilePath, reportSampleFileTemplateName);
				Reporter.log("oldFileNameWithPath " + j + ":" + oldFileNameWithPath, true);
				Reporter.log("newFileNameWithPath " + j + ":" + newFileNameWithPath, true);

				//copy file 1
				File source=new File(oldFileNameWithPath);
				File destination=new File(newFileNameWithPath);
				FilesAndFolders.copyFile(source,destination);
				
				//update path and name for attachment in config.properties
				String emailAttachmentPath = "emailAttachmentPath" + j;
				FilesAndFolders.setPropValue(emailAttachmentPath, newFileNameWithPath);
			}
			//set subject
			String finalSubject=dateTimeStamp + mailSubject;
			FilesAndFolders.setPropValue("emailSubjectForQaServer", finalSubject);
			
			//send email
			SmsEmailNotification.sendEmail();//sent via gmail to other accounts				
		}

		System.out.println("++++++++++++++++++++++++sendBulkMailsForTesting success+++++++++++++++++++++++++++");
	}

	private static String generateFileNameWithPath(String dateTimeStamp, String reportSampleFileTemplatePath, String reportSampleFileTemplateName) throws Exception
	{
		String file = reportSampleFileTemplatePath;

		String finalFileName=null;
		String fileNameWithDateTimeStamp;
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
		fileNameWithDateTimeStamp = StringManipulation.appendStringAtIndex(fileNameWithoutTemplateKeyword, 1, dateTimeStamp);

		finalFileName = reportSampleFileTemplatePath + fileNameWithDateTimeStamp;
		//Reporter.log("Link Checker Status Report File:" + finalFileName  + "\n" ,true);		
		Assert.assertTrue(finalFileName!=null,"Error! finalFileName is null...");

		return finalFileName;
	}

}
