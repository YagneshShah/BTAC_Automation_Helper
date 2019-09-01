package automationHelper.addon;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;

import automationHelper.sa.FilesAndFolders;
import automationHelper.sa.excelCustomReportGeneration;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class SocialMediaReviewExtractor
{
	public static void TwitterReviews(String appName, String searchQuery, int totalTweetsToBeExtracted, String reportSampleFileTemplatePath, String reportSampleFileTemplateName) throws Exception 
	{
		Boolean debugEnabled = Boolean.parseBoolean(FilesAndFolders.getPropValue("debugEnabled", "./twitter4j.properties"));
		String oauthConsumerKey = FilesAndFolders.getPropValue("oauthConsumerKey", "./twitter4j.properties");
		String oauthConsumerSecret = FilesAndFolders.getPropValue("oauthConsumerSecret", "./twitter4j.properties");
		String oauthAccessToken = FilesAndFolders.getPropValue("oauthAccessToken", "./twitter4j.properties");
		String oauthAccessTokenSecret = FilesAndFolders.getPropValue("oauthAccessTokenSecret", "./twitter4j.properties");

		//String searchQuery = FilesAndFolders.getPropValue("searchQuery", "./twitter4j.properties");
		//int totalTweetsToBeExtracted = Integer.parseInt(FilesAndFolders.getPropValue("totalTweetsToBeExtracted", "./twitter4j.properties"));

		Reporter.log("**************************************Input Configurations******************************************",true);
		Reporter.log("debugEnabled From Twitter Side:" + debugEnabled, true);
		Reporter.log("searchQuery:" + searchQuery, true);
		Reporter.log("totalTweetsToBeExtracted:" + totalTweetsToBeExtracted, true);
		Reporter.log("****************************************************************************************************",true);
		Reporter.log("\n", true);

		Reporter.log("**************************************Extracting Tweets******************************************",true);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debugEnabled)
		.setOAuthConsumerKey(oauthConsumerKey)
		.setOAuthConsumerSecret(oauthConsumerSecret)
		.setOAuthAccessToken(oauthAccessToken)
		.setOAuthAccessTokenSecret(oauthAccessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		Query query = new Query(searchQuery);
		long lastID = Long.MAX_VALUE;
		Reporter.log("lastID - Default value from datatype Long.MAX_VALUE:" + lastID, true);

		ArrayList<Status> tweetsCollected = new ArrayList<Status>();//Size will be 0 for the first time initialization
		Reporter.log("tweetsCollected.size:" + tweetsCollected.size(),true);

		int currentCalculation=0;
		int repeatCount=1;
		//Extracts 100 tweets each time in a loop, since "query.setCount()" method from twitter can't extract more than 100 at a time. So use a "List" and keep appending 100 tweets to that list each time in the loop 
		while (tweetsCollected.size() < totalTweetsToBeExtracted) //In each loop 100 tweets are extracted due to if statement, so total it shall loop for totalTweetsToBeExtracted/100...
		{
			//***************************Step1***************************
			//Condition to BREAK the while loop since totalTweetsToBeExtracted count is more than actual available tweets in twitter...
			if(currentCalculation == (totalTweetsToBeExtracted - tweetsCollected.size()))//comparing previous value of 'currentCalculation' with new value
			{
				Reporter.log("Repeat Try " + repeatCount + " :: currentCalculation == (totalTweetsToBeExtracted - tweetsCollected.size()) '" 
						+ currentCalculation +"=="+ (totalTweetsToBeExtracted - tweetsCollected.size()),true);

				if(repeatCount++ >=5)//currentCalculation is same from last 5 times, break from while loop...
				{
					Reporter.log("currentCalculation == (totalTweetsToBeExtracted - tweetsCollected.size()) '" 
							+ currentCalculation +"=="+ (totalTweetsToBeExtracted - tweetsCollected.size()) 
							+"'...\nSo breaking the loop and starting to print tweets which were Gathered so far...",true);
					break;									
				}
			}
			else //continue extracting tweets..
			{
				currentCalculation = totalTweetsToBeExtracted - tweetsCollected.size();
				Reporter.log("numberOfTweetsExpected - tweetsCollected.size():" 
						+ totalTweetsToBeExtracted + "-" + tweetsCollected.size() + "=" + currentCalculation, true);
			}
			
			//***************************Step2***************************
			//Tells how many tweets to extract in current round of while loop...remaining tweets would be extracted in next round...
			//Extracts 100 tweets each time in a loop/round, since "query.setCount()" method from twitter can't extract more than 100 at a time. So use a "List" and keep appending 100 tweets to that list each time in the loop 
			if (currentCalculation > 100) //setCount to 100, since totalTweetsToBeExtracted is still > 100
			{
				Reporter.log("inside if...query.setCount(100)",true);
				
				//Typing more than 100 would not make any difference and twitter wont return more than 100 tweets due to its limit.
				query.setCount(100); //sets the number of tweets to return per page, up to a max of 100 is allowed by default from twitter.
			}
			else //total un-found tweets <= 100 then
			{
				Reporter.log("inside else...query.setCount(" + currentCalculation + ")",true);
				query.setCount(currentCalculation);				
			}
			
			//***************************Step3***************************
			//Start collecting tweets in a ArrayList depending on value set for "query.setCount()" method in above if/else condition...
			try 
			{
				query.resultType(Query.MIXED);// Get all tweets
				QueryResult result = twitter.search(query);
				tweetsCollected.addAll(result.getTweets()); //append more tweets to "tweetsCollected" list
				System.out.println("\nGathered " + tweetsCollected.size() + " tweets..."+"\n");
				int j=0;
				for (Status t: tweetsCollected) 
				{
					if(t.getId() < lastID)  //t.getId() == TweetId in Twitter. Example: "773109567215562752" is TweetId in this Tweet URL - https://twitter.com/Rob_StreamRec/status/773109567215562752
					{
						lastID = t.getId();	
						//Reporter.log("lastID - Current value " + j++ + ":" + lastID, true);
					}
				}
				Reporter.log("lastID - Final value " + j++ + ":" + lastID, true);

				Assert.assertTrue(tweetsCollected.size()!=0, "\n\nSorry!! There are 0 Tweets for the specified '" + searchQuery + "' search keyword...Following are few good practice to search a keyword:"
						+ "\n1. Start search keyword with #. Example: #selenium"
						+ "\n2. Don't give multiple keywords with space in between. Example:#selenium sucks"
						+ "\n3. Don't type multiple # for each keywords. Example: #selenium #sucks"
						+ "Detailed reference guide at: 'https://dev.twitter.com/rest/public/search'\n\n");
			}
			catch (TwitterException te) 
			{
				System.out.println("Couldn't connect: " + te);
			}; 
			query.setMaxId(lastID-1);
		}//end while

		
		
		Reporter.log("***********************************************************************************************",true);
		Reporter.log("****************************Printing Tweets on Console and Excel Report************************",true);

		excelCustomReportGeneration.connectExcel(appName, reportSampleFileTemplatePath, reportSampleFileTemplateName);//connect and open excel file for Reporting
		excelCustomReportGeneration.setValueIntoCell("sheet1", 1, 2, appName); //update #no column
		excelCustomReportGeneration.setValueIntoCell("sheet1", 1, 3, tweetsCollected.size()); //update #no column
		int rowNo=8;

		//Print the content of extracted tweets from the ArrayList...
		for (int i = 0; i < tweetsCollected.size(); i++) 
		{
			Status t = (Status) tweetsCollected.get(i);
			GeoLocation loc = t.getGeoLocation();

			//Print data on Console from here on....
			Reporter.log("\nTweet:" + (i+1),true);
			
			//Tweet related data:
			//Reporter.log("Tweet Id:" + t.getId(),true);
			Reporter.log("Tweet Created At:"+ t.getCreatedAt(), true); //dateTime
			Reporter.log("Tweet Direct URL:" + "https://twitter.com/" + t.getUser().getScreenName() + "/status/" + t.getId(),true);
			Reporter.log("Tweet Content:"+ t.getText(),true); //Tweet
			
			//User related data:
			//Reporter.log("Shortlink of URL from User's Profile: @" + t.getUser().getURL(),true);
			//Reporter.log("Description from User's Profile: @" + t.getUser().getDescription(),true);
			//Reporter.log("Link of Image from User's Profile: @" + t.getUser().getBiggerProfileImageURL(),true);
			Reporter.log("User Name:" + t.getUser().getName(),true);
			//Reporter.log("TwitterId:" + t.getUser().getScreenName(),true); //twitterId
			Reporter.log("User Location:" + t.getUser().getLocation(),true);
			
			//Tweet related data again:
			Reporter.log("Retweet Count:" + t.getRetweetCount(),true);
			Reporter.log("Favorite Count:" + t.getFavoriteCount(),true);
			Reporter.log("Hasgtag Entities:" + t.getHashtagEntities().length,true);
			Reporter.log("User Mention Entities:" + t.getUserMentionEntities().length,true);
			Reporter.log("Symbol Entities:" + t.getSymbolEntities().length,true);
			Reporter.log("Media Entities:" + t.getMediaEntities().length,true);

			
			
			//Collecting HashTags into single String from an Array of HashtagEntity[]
			HashtagEntity[] hashTagEntities = t.getHashtagEntities();
			String finalHashTags="";
			for(int j=0; j<hashTagEntities.length;j++)
			{
				finalHashTags = finalHashTags + hashTagEntities[j].getText() + ", ";
			}
			Reporter.log("HashTags String:" + finalHashTags,true);

			//Collecting UserMentionEntities into single String from an Array of UserMentionEntity[]
			UserMentionEntity[] userMentionEntities = t.getUserMentionEntities();
			String finalUserMentionEntities = "";
			for(int j=0; j<userMentionEntities.length;j++)
			{
				finalUserMentionEntities = finalUserMentionEntities + userMentionEntities[j].getText() + ", ";
			}
			Reporter.log("User Mention Entities String:" + finalUserMentionEntities,true);
			
			//Collecting SymbolEntities into single String from an Array of SymbolEntity[]
			SymbolEntity[] symbolEntities = t.getSymbolEntities();
			String finalSymbolEntities = "";
			for(int j=0; j<symbolEntities.length;j++)
			{
				finalSymbolEntities = finalSymbolEntities + symbolEntities[j].getText() + ", ";
			}
			Reporter.log("Symbol Entities String:" + finalSymbolEntities,true);
			
			//Collecting MediaEntities into single String from an Array of MediaEntity[]
			MediaEntity[] mediaEntities = t.getMediaEntities();
			String finalMediaEntities = "";
			for(int j=0; j<mediaEntities.length;j++)
			{
				finalMediaEntities = finalMediaEntities + mediaEntities[j].getText() + ", ";
			}
			Reporter.log("Media Entities String:" + finalMediaEntities,true);

			
			
			//Print data in Excel file from here on...
			excelCustomReportGeneration.setValueIntoCell("sheet1", 0, rowNo, (i+1)); //update #no column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 1, rowNo, t.getCreatedAt().toString()); //update #TweetCreatedAt column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 2, rowNo, "https://twitter.com/" + t.getUser().getScreenName() + "/status/" + t.getId()); //update #TweetDirectURL column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 3, rowNo, t.getText()); //update #TweetContent column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 4, rowNo, t.getUser().getName()); //update #UserName column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 5, rowNo, t.getUser().getLocation()); //update #UserLocation column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 6, rowNo, t.getRetweetCount()); //update #RetweetCount column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 7, rowNo, t.getFavoriteCount()); //update #FavoriteCount column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 8, rowNo, finalHashTags); //update #HasgtagEntities column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 9, rowNo, finalUserMentionEntities); //update #UserMentionEntities column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 10, rowNo, finalSymbolEntities); //update #SymbolEntities column
			excelCustomReportGeneration.setValueIntoCell("sheet1", 11, rowNo, finalMediaEntities); //update #MediaEntities column
			rowNo++;
			
			//String time = "";
			//if (loc!=null) {
			//Double lat = t.getGeoLocation().getLatitude();
			//Double lon = t.getGeoLocation().getLongitude();*/
		}
		Reporter.log("***********************************************************************************************",true);
		excelCustomReportGeneration.closeFile(); //close excel file connection after update
	}


	
	
	
	
	
	
	//using Twitter Stream...needs more work
	public static void GetTweetStreamForKeywords()
    {
    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

    StatusListener statusListener = new StatusListener() {

     @Override
     public void onStatus(Status status) {
       // The main section that you get the tweet. You can access it by status object.
       // You can save it in a database table.
     }


            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onException(Exception ex) {
                //logWriter.WriteErrorLog(ex, "onException()");
            }
        };

        FilterQuery fq = new FilterQuery();        

        String keywords[] = {"sport", "politics", "health"};

        fq.track(keywords);        

        twitterStream.addListener(statusListener);
        twitterStream.filter(fq);  
        twitterStream.user();
  }  
	
	
	
	
	

	//works fine...but no control on query.setCount(200)
	/*
	public static void searchTweet5() throws Exception
	{
		
		Boolean debugEnabled = Boolean.parseBoolean((FilesAndFolders.getPropValue("debugEnabled", "./twitter4j.properties")));
		String oauthConsumerKey = FilesAndFolders.getPropValue("oauthConsumerKey", "./twitter4j.properties");
		String oauthConsumerSecret = FilesAndFolders.getPropValue("oauthConsumerSecret", "./twitter4j.properties");
		String oauthAccessToken = FilesAndFolders.getPropValue("oauthAccessToken", "./twitter4j.properties");
		String oauthAccessTokenSecret = FilesAndFolders.getPropValue("oauthAccessTokenSecret", "./twitter4j.properties");

		String searchQuery = FilesAndFolders.getPropValue("searchQuery", "./twitter4j.properties");
		String totalTweetsToBeExtracted = FilesAndFolders.getPropValue("totalTweetsToBeExtracted", "./twitter4j.properties");

		Reporter.log("debugEnabled:" + debugEnabled);

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debugEnabled)
		.setOAuthConsumerKey(oauthConsumerKey)
		.setOAuthConsumerSecret(oauthConsumerSecret)
		.setOAuthAccessToken(oauthAccessToken)
		.setOAuthAccessTokenSecret(oauthAccessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();


		Query query = new Query("sucks #Treebo");
		query.setCount(200);//max allowed value is 100, entering more than 100 doesn't make any difference
		QueryResult result=twitter.search(query);
		int i = 0;
		int page =0;
		do{
			Reporter.log("\n\n\nTweet Results from page:" + page++,true);
			List<Status> tweets = result.getTweets();
			for(Status tweet: tweets){
				System.out.println("Tweet " + i + ":" + tweet.getText());
				i++;
			}
			query=result.nextQuery();//Returns a Query instance to fetch next page or null if there is no next page.
			if(query!=null)
				result=twitter.search(query);
		}while(query!=null);
	}

	//don't use this method as it will not print more than 100 tweets...
	private static void searchTweet3() throws Exception
	{
		Boolean debugEnabled = Boolean.parseBoolean(FilesAndFolders.getPropValue("debugEnabled", "./twitter4j.properties"));
		String oauthConsumerKey = FilesAndFolders.getPropValue("oauthConsumerKey", "./twitter4j.properties");
		String oauthConsumerSecret = FilesAndFolders.getPropValue("oauthConsumerSecret", "./twitter4j.properties");
		String oauthAccessToken = FilesAndFolders.getPropValue("oauthAccessToken", "./twitter4j.properties");
		String oauthAccessTokenSecret = FilesAndFolders.getPropValue("oauthAccessTokenSecret", "./twitter4j.properties");

		String searchQuery = FilesAndFolders.getPropValue("searchQuery", "./twitter4j.properties");
		int totalTweetsToBeExtracted = Integer.parseInt(FilesAndFolders.getPropValue("totalTweetsToBeExtracted", "./twitter4j.properties"));

		Reporter.log("debugEnabled:" + debugEnabled);

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debugEnabled)
		.setOAuthConsumerKey(oauthConsumerKey)
		.setOAuthConsumerSecret(oauthConsumerSecret)
		.setOAuthAccessToken(oauthAccessToken)
		.setOAuthAccessTokenSecret(oauthAccessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();


		//twitter4j.Twitter twitter =  TwitterFactory.getSingleton();
		Query query = new Query("#selenium");
		query.count(105);

		//Search Tweets
		QueryResult result = twitter.search(query);
		Reporter.log("Gathered Tweets:" + result.getCount(), true);

		int i=0;
		for (Status status : result.getTweets()) {
			Reporter.log("\nTweet:" + i,true);
			Reporter.log("@" + status.getUser().getScreenName() + " : " + status.getText() + " : " + status.getGeoLocation(), true);
			Reporter.log(""+ status.getCreatedAt(), true);
			i++;
		}
	}*/

	
}


