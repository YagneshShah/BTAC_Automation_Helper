/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.Assert;
import org.testng.annotations.Test;


// TODO: Auto-generated Javadoc
/**
 * The Class SmsEmailNotification.
 */
public class SmsEmailNotification {  
	
	/**
	 * Sends automated email with attached TestNg HTML Report.
	 *<br> config.properties file, if "emailExecutionFlag" key is set as true then only the Email logic will work else it won't.
	 *<br> config.properties file, Under "Email properties" section, all keys(like: subject, from, to, attachments, and so on) needs to be properly configured for sending Email. 
	 *
	 *<br><br>If unable to execute this code due to Authentication issues then try either of following:
	 *<br>1. For specific device: go to https://myaccount.google.com/security#activity for your account and add the device as trusted device
	 *<br>2. For any device: go to https://www.google.com/settings/security/lesssecureapps and allow less secure apps to send emails 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	@Test(priority=1)
	public static void sendEmail() throws Exception
	{
		//Config Parameters
		int flag;
		String project;
		String DbDomain = FilesAndFolders.getPropValue("DbDomain"); //Based on DbDomain you need to select Email Subject
		String emailExecutionFlag = FilesAndFolders.getPropValue("emailExecutionFlag");

		//Email parameters
		String emailSubjectForJenkinsServer = FilesAndFolders.getPropValue("emailSubjectForJenkinsServer");
		String emailSubjectForQaServer = FilesAndFolders.getPropValue("emailSubjectForQaServer");
		String emailSubjectForAutomationServer = FilesAndFolders.getPropValue("emailSubjectForAutomationServer");
		String emailSubjectForProductionServer = FilesAndFolders.getPropValue("emailSubjectForProductionServer");

		String suiteName = FilesAndFolders.getPropValue("suiteName");
		
		String emailFromUser = FilesAndFolders.getPropValue("emailFromUser");
		String emailFromPassword = FilesAndFolders.getPropValue("emailFromPassword");

//		String emailTo1 = prop.getProperty("emailTo1");
//		String emailTo2 = prop.getProperty("emailTo2");
//		String emailTo3 = prop.getProperty("emailTo3");
//		String emailTo4 = prop.getProperty("emailTo4");
//		String emailTo5 = prop.getProperty("emailTo5");
		
		int emailToTotalCount = Integer.parseInt(FilesAndFolders.getPropValue("emailToTotalCount"));
		String[] emailTo= new String[emailToTotalCount];
		//System.out.println("emailToTotalCount:"+ emailToTotalCount);
		String configFileEmailParameter;
		for(int i=0; i < emailToTotalCount;i++)
		{
			configFileEmailParameter = "emailTo" + i;
			//System.out.println("configFileEmailParameter:"+configFileEmailParameter);
			emailTo[i] = FilesAndFolders.getPropValue(configFileEmailParameter);
			System.out.println("emailTo[" + i + "] = " + emailTo[i]);
		}

		//email body
		String emailBodyMessage = FilesAndFolders.getPropValue("emailBodyMessage");
		
		//String emailAttachmentPath1 = FilesAndFolders.getPropValue("emailAttachmentPath1");
		//String emailAttachmentPath2 = FilesAndFolders.getPropValue("emailAttachmentPath2");
		int emailAttachmentCount = Integer.parseInt(FilesAndFolders.getPropValue("emailAttachmentCount"));
		String emailAttachmentPath[] = new String[emailAttachmentCount];
		for(int i=0;i<emailAttachmentCount;i++)
		{
			String emailAttachmentKey = "emailAttachmentPath" + i;
			emailAttachmentPath[i] = FilesAndFolders.getPropValue(emailAttachmentKey);
			//System.out.println("emailAttachmentPath" + i + ":" + emailAttachmentPath[i]);
		}


		if (emailExecutionFlag.contentEquals("true"))
		{
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy kk:mm");
			switch(DbDomain)
			{
			case "jenkins":
				project = emailSubjectForJenkinsServer; //set emailSubject
				flag=1;
				break;
				
			case "qa":
				project = emailSubjectForQaServer; //set emailSubject
				flag=1;
				break;

			case "automation":
				project = emailSubjectForAutomationServer; //set emailSubject
				flag=1;
				break;

			case "production":
				project = emailSubjectForProductionServer; //set emailSubject
				flag=1;
				break;

			default:
				project=null;
				flag=0;
				Assert.assertTrue(flag==1, "DbDomain value in config.properties file is not a valid match with switch case for setting appropriate emailSubject...");
			}

			String[] to = new String[emailToTotalCount];
			for(int i=0; i < emailToTotalCount;i++)
			{
				to[i] = emailTo[i];
				System.out.println("to[" + i + "] = " + to[i]);
			}
//			String to1 = emailTo1;//change accordingly
//			String to2 = emailTo2;//change accordingly
//			String to3 = emailTo3;//change accordingly
//			String to4 = emailTo4;//change accordingly
//			String to5 = emailTo5;//change accordingly

			final String user = emailFromUser;//change accordingly  
			final String password = emailFromPassword;//change accordingly 

			//1) get the session object     
			Properties properties = System.getProperties();  
			properties.setProperty("mail.transport.protocol", "smtp");  
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");  
			properties.put("mail.smtp.auth", "true");  
			properties.put("mail.smtp.port", "465");

			properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
			//properties.put("mail.smtp.socketFactory.fallback", "false"); 

			Session session = Session.getDefaultInstance(properties,  
					new javax.mail.Authenticator() {  
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication(user,password);  
				}  
			});  

			//2) compose message     
			try{  
				MimeMessage message = new MimeMessage(session);  
				
				//set 'From'
				message.setFrom(new InternetAddress(user)); 
				
				//set To's
				for(int i=0; i < emailToTotalCount;i++)
				{
					message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
				}

				//message.setSubject(project + "_" + suiteName + "_" + sdf.format(c.getTime())); // use this statement to append date to subject
				message.setSubject(project + " : " + suiteName); 

				//3) mail body - create MimeBodyPart object and set your message text     
				BodyPart messageBodyPart00 = new MimeBodyPart();  
				messageBodyPart00.setText(emailBodyMessage);
				
				//4) mail attachment - create new MimeBodyPart object and set DataHandler object to this object 
//				MimeBodyPart messageBodyPart0 = new MimeBodyPart();					
//				String filename0 = emailAttachmentPath0;//change accordingly
//				DataSource source0 = new FileDataSource(filename0);  
//				messageBodyPart0.setDataHandler(new DataHandler(source0));  
//				messageBodyPart0.setFileName(filename0);

				//				MimeBodyPart messageBodyPart3 = new MimeBodyPart();  
				//				String filename3 = emailAttachment2Path;//change accordingly
				//				DataSource source3 = new FileDataSource(filename3);  
				//				messageBodyPart3.setDataHandler(new DataHandler(source3));  
				//				messageBodyPart3.setFileName(filename3);

				//5) create Multipart object and add MimeBodyPart objects to this object      
				Multipart multipart = new MimeMultipart();  
				multipart.addBodyPart(messageBodyPart00); //mail body

				for(int i=0;i<emailAttachmentCount;i++)//mail attachments
				{
					addAttachment(multipart, emailAttachmentPath[i]);
				}

				//multipart.addBodyPart(messageBodyPart0);//in section 4
				//multipart.addBodyPart(messageBodyPart3);//in section 4

				//6) set the multiplart object to the message object  
				message.setContent(multipart );  

				//7) send message  
				Transport.send(message);  
				System.out.println("Mail sent...");  
			}catch (MessagingException ex) {ex.printStackTrace();}  
		}
		else
		{
			System.out.println("Email feature is turned off via emailExecutionFlag in config.properties file...");
		}
	} 
	
	private static void addAttachment(Multipart multipart, String filename) throws MessagingException
	{
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}
}
