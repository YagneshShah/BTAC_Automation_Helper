package automationHelper.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.xml.HasXPath;
import org.json.JSONException;
//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.codehaus.jackson.map.ObjectMapper;



import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Node;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import automationHelper.seleniumappium.FilesAndFolders;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.assertion.HeaderMatcher;
import io.restassured.config.Config;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;

public class RestAssuredJsonUtils {
	public static long minResponseTime1,minResponseTime2,avgResponseTime1,avgResponseTime2,maxResponseTime1,maxResponseTime2;

	//constructor
	public RestAssuredJsonUtils() throws IOException 
	{
		minResponseTime1 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("minResponseTime1"));
		minResponseTime2 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("minResponseTime2"));
		avgResponseTime1 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("avgResponseTime1"));
		avgResponseTime2 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("avgResponseTime2"));
		maxResponseTime1 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("maxResponseTime1"));
		maxResponseTime2 = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("maxResponseTime2"));
	}


	public static String getBaseUrlForApi() throws IOException
	{
		String domainBaseUrl, baseUrl;
		domainBaseUrl = FilesAndFolders.getValueFromPropertiesFile("domainBaseUrl");    

		switch(domainBaseUrl) 
		{
		case "baseUrl_AnypointMockUrl": 
			baseUrl = FilesAndFolders.getValueFromPropertiesFile("baseUrl_AnypointMockUrl");
			break;
		case "baseUrlDev_ContactAPI": 
			baseUrl = FilesAndFolders.getValueFromPropertiesFile("baseUrlDev_ContactAPI");
			break;
		case "baseUrlSt_ContactAPI": 
			baseUrl = FilesAndFolders.getValueFromPropertiesFile("baseUrlSt_ContactAPI");
			break;
		case "baseUrlDev_DocumentAPI": 
			baseUrl = FilesAndFolders.getValueFromPropertiesFile("baseUrlDev_DocumentAPI");
			break;
		case "baseUrlSt_DocumentAPI": 
			baseUrl = FilesAndFolders.getValueFromPropertiesFile("baseUrlSt_DocumentAPI");
			break;

		default:
			baseUrl=null;
			Assert.assertTrue(baseUrl!=null,"Code Error!! baseUrl is 'null' since domainBaseUrl '"+domainBaseUrl+"' doesnt match any switch case!!");
		}

		return baseUrl;
	}

	public static String getStringFromResource(String pathWithFileName) throws IOException 
	{
		String resourceContact = new String(Files.readAllBytes(Paths.get(pathWithFileName)));
//		Reporter.log("resourceContent:"+resourceContact, true);
		return resourceContact;
	}

	public static Response sendAPIRequestAndReceiveResponse(String requestType, String apiBaseURL, String apiPath, Map <String,Object> queryParamsMap, Headers requestHeaders, String requestBody)
	{
		Reporter.log("\n\nAPI Request Details:", true);
		Reporter.log("requestType:" + requestType, true);
		Reporter.log("apiBaseURL:" + apiBaseURL, true);
		Reporter.log("apiPath:" + apiPath, true);
		Reporter.log("queryParamsMap:\n" + queryParamsMap, true);
		Reporter.log("requestHeaders:\n" + requestHeaders, true);
		Reporter.log("requestBody:\n" + requestBody + "\n", true);

		//building api request
		//params header body
		//0	 0	0
		//
		//0	 0	1
		//0	 1	0
		//1	 0	0
		//
		//1	 1	0
		//1	 0	1
		//0	 1	1
		//
		//1	 1	1
		RequestSpecification httpRequest;
		//all null
		if(queryParamsMap == null && requestHeaders == null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case:'queryParamsMap == null && requestHeaders == null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;		
			httpRequest = RestAssured.given();//.queryParams("city", "Redmond","region", "WA","country", "US","postal", "98052");			
		}
		//one not null
		else if(queryParamsMap == null && requestHeaders == null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders == null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().body(requestBody); //.param("idp", "NGP");			
		}
		else if(queryParamsMap == null && requestHeaders != null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders != null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().headers(requestHeaders);
		}
		else if(queryParamsMap != null && requestHeaders == null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders == null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().queryParams(queryParamsMap);
		}
		//two not null
		else if(queryParamsMap != null && requestHeaders != null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders != null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(requestHeaders);
		}
		else if(queryParamsMap != null && requestHeaders == null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders == null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().queryParams(queryParamsMap).body(requestBody);
		}
		else if(queryParamsMap == null && requestHeaders != null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders != null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().headers(requestHeaders).body(requestBody);
		}
		//all not null
		else
		{
			Reporter.log("Building httpRequest, case: 'queryParams != null && requestHeaders != null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;
			httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(requestHeaders).body(requestBody);
		}


		//sending api request
		Response response;
		switch(requestType.toLowerCase())
		{
		case "get":
			Reporter.log("Sending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.GET, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "post":
			Reporter.log("Sending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.POST, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "delete":
			Reporter.log("Sending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.DELETE, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "patch":
			Reporter.log("Sending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.PATCH, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "put":
			Reporter.log("Sending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.PUT, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		default:
			response=null;
			Assert.assertNotNull(response, "Error! response is null since no matching switch case for requestType '"+requestType+"' which is passed to method 'getResponseFromPostRequest'");
			return response;
		}
	}

	public static void printApiResponseBody(Response response)
	{
		Reporter.log("\n<br> API Response body: <br> \n" + response.getBody().asString(), true);
	}

	public static void assertStatusCode(Response response, int expectedStatusCode)
	{
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Error! actualStatusCode '"+ actualStatusCode +"' doesn't match expectedStatusCode '" + expectedStatusCode + "'...");
		Reporter.log("Success!! actualStatusCode '"+ actualStatusCode +"' == expectedStatusCode '" + expectedStatusCode + "'...", true);
	}

	public static void assertStatusLine(Response response, String expectedStatusLine)
	{
		String actualStatusLine = response.getStatusLine(); //Example:"HTTP/1.0 200 OK"
		Assert.assertEquals(actualStatusLine, expectedStatusLine, "Error! actualStatusLine '"+ actualStatusLine +"' doesn't match expectedStatusLine '" + expectedStatusLine + "'...");
		Reporter.log("Success!! actualStatusLine '"+ actualStatusLine +"' == expectedStatusLine '" + expectedStatusLine + "'...", true);
	}


	/**
	 * Print all the Response Headers value received from an API response. 
	 * Headers class implements Iterable interface, hence we can apply a for loop to go through all Headers
	 * @param response
	 */
	public static void printAllResponseHeaders(Response response) 
	{
		Headers allHeaders = response.headers();
		Reporter.log("\n\n<br> Getting all ResponseHeaders 'Key : Value':", true);
		for(Header header : allHeaders)
		{
			Reporter.log("\t'" + header.getName() + "' : '" + header.getValue() + "'", true);
		}	
		Reporter.log("\n",true);
	}

	/**
	 * Print all the Response Headers value received from an API response. 
	 * Headers class implements Iterable interface, hence we can apply a for loop to go through all Headers
	 * @param response
	 */
	public static Headers getAllResponseHeaders(Response response) 
	{
		Headers allHeaders = response.headers();
		return allHeaders;
	}

	public static void assertResponseHeaderHasValue(Response response, String keyFromResponseHeader, String expectedValueForTheKeyFromResponseHeader)
	{
		Reporter.log("\nAsserting:", true);
		//validate individual response header. Read header of a give name:
		String actualValueForTheKeyFromResponseHeader = response.header(keyFromResponseHeader); 
		Reporter.log("asserting for keyFromResponseHeader '" + keyFromResponseHeader + "'\n"
				+ "whose actualValueForTheKeyFromResponseHeader value is '" +actualValueForTheKeyFromResponseHeader+ "'\n"
				+ "and comparing with expected value as '" + expectedValueForTheKeyFromResponseHeader + "'", true);
		Assert.assertEquals(actualValueForTheKeyFromResponseHeader, expectedValueForTheKeyFromResponseHeader, "Error! actualValueForTheKeyFromResponseHeader '"+ actualValueForTheKeyFromResponseHeader +"' doesn't match expectedValueForTheKeyFromResponseHeader '" + expectedValueForTheKeyFromResponseHeader + "'...");
	}

	@Deprecated
	public static void assertResponseHeadersHasKeys1(Response response, List<String> expectedResponseHeaderKeys)
	{
		SoftAssert softAssertObj = new SoftAssert();
		Reporter.log("\nAsserting ResponseHeaders Has Keys:'"+expectedResponseHeaderKeys+"'", true);

		printAllResponseHeaders(response);

		for(int i=0; i<expectedResponseHeaderKeys.size(); i++)
		{
			String headerValue = response.header(expectedResponseHeaderKeys.get(i));
			//Reporter.log("debug log!! header value:"+headerValue, true);

			if(headerValue==null)
			{
				softAssertObj.assertTrue(false,"Bug!! Header Key '"+expectedResponseHeaderKeys.get(i)+"' is not present in Response Headers");
			}
		}
		softAssertObj.assertAll();
	}

	public static void assertResponseHeadersHasKeys(Response response, List<String> expectedResponseHeaderKeys)
	{
		SoftAssert softAssertObj = new SoftAssert();
		Reporter.log("\nAsserting ResponseHeaders Has Keys:'"+expectedResponseHeaderKeys+"'", true);

		printAllResponseHeaders(response);
		Headers actualHeaders = getAllResponseHeaders(response);

		for(int i=0; i<expectedResponseHeaderKeys.size(); i++)//for each expected header key
		{
			boolean headerPresentFlag=false;
			for(Header ah : actualHeaders)
			{
				if(expectedResponseHeaderKeys.get(i).equals(ah.getName()))
				{	
					headerPresentFlag=true;
				}
			}
			softAssertObj.assertTrue(headerPresentFlag,"Bug!! Header Key '"+expectedResponseHeaderKeys.get(i)+"' is not present in Response Headers");
		}
		softAssertObj.assertAll();
	}


	public static boolean isResponseBodyJsonValid(Response response) {
		Gson gson = new Gson();

		String jsonInString = response.getBody().asString();
		//		jsonInString = jsonInString + ",}";
		//		Reporter.log("Debug log! jsonInString: \n"+jsonInString,true);

		try 
		{
			gson.fromJson(jsonInString, Object.class);
			return true;
		} 
		catch(com.google.gson.JsonSyntaxException ex) 
		{ 
			//ex.printStackTrace();
			Reporter.log("<br>Bug!! Not a valid JSON format in Response body... "+ex +"<br>", true);
			return false;
		}
	}

	public static void assertResponseBodyJsonValid(Response response)
	{
		boolean validJsonFlag = isResponseBodyJsonValid(response);
		//Reporter.log("Debug log! validJsonFlag:"+validJsonFlag, true);

		Assert.assertTrue(validJsonFlag,"Bug!! Not a valid JSON format in Response body...");
	}

	public static void assertResponseBodyContainsString(Response response, String key)
	{
		Reporter.log("\nAsserting ResponseBodyContainsString:'"+key+"'", true);
		String responseBody = response.getBody().asString();

		//Assert.assertTrue(responseBody.contains(key));
		Assert.assertTrue(responseBody.contains(key));
	}

	//	@Deprecated
	//	private static void softAssertResponseBodyContainsString(Response response, String str)
	//	{
	//		SoftAssert softAssertObj = new SoftAssert();
	//
	//		Reporter.log("\nAsserting ResponseBodyContainsString:'"+str+"'", true);
	//		String responseBody = response.getBody().asString();
	//
	//		softAssertObj.assertTrue(responseBody.contains(str),"Bug!! String '"+str+"' is not present in the Response Body...");
	//	}

	public static void assertResponseBodyContainsString(Response response, List<String> stringList)
	{
		SoftAssert softAssertObj = new SoftAssert();
		for(int i=0;i<stringList.size();i++)
		{
			//			RestAssuredUtils.softAssertResponseBodyContainsString(response, stringList.get(i));//exists
			String str = stringList.get(i);

			Reporter.log("\nAsserting ResponseBodyContainsString:'"+str+"'", true);
			String responseBody = response.getBody().asString();

			softAssertObj.assertTrue(responseBody.contains(str),"Bug!! String '"+str+"' is not present in the Response Body...");
		}
		softAssertObj.assertAll();
	}


	//	@Deprecated
	//	private static void softAssertResponseBodyHasKeyJsonPath(Response response, String keyJsonPath)
	//	{
	//		SoftAssert softAssertObj = new SoftAssert();
	//		
	//		Reporter.log("\nAsserting if ResponseBodyHasKey(based on exact JsonPath):'"+keyJsonPath+"'", true);
	//		String responseBody = response.getBody().asString();
	//
	//		List<String> valuesList;
	//		//case1: checking for .getList() if key has multiple occurrence
	//		try 
	//		{
	//			valuesList = JsonPath.from(responseBody).getList(keyJsonPath);
	//			//String valuesList = JsonPath.from(responseBody).getList(keyJsonPath).toString();
	//
	//			//			Reporter.log("debug log! Checking for Multiple occurence of key. valuesList: "+valuesList, true);
	//			//			Reporter.log("debug log! :"+valuesList.contains(null)+"::"+valuesList.equals(null)+"::"+valuesList.isEmpty()+"::"+"valuesList.length()", true);
	//
	//			//Assert.assertFalse(valuesList.contains(null)||valuesList.equals(null)||valuesList.isEmpty(), "Bug 1!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
	//			softAssertObj.assertFalse(valuesList.contains(null)||valuesList.equals(null)||valuesList.isEmpty(), "Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
	//		}
	//		catch(NullPointerException | ClassCastException e1)
	//		{
	//			String keyValue=null;
	//			//case2: checking for .get() if key has single occurrence
	//			try
	//			{
	//				keyValue = JsonPath.from(responseBody).get(keyJsonPath).toString();
	//				//Reporter.log("debug log! 1st catch block! Checking for single occurrence of key. Value: "+keyValue, true);
	//			}
	//			catch(NullPointerException e2)
	//			{
	//				//Reporter.log("debug log! 2nd catch block! Checking for single occurrence of key. Value: "+keyValue, true);
	//
	//				//Assert.assertTrue(false,"Bug 2!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
	//				softAssertObj.assertTrue(false,"Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
	//			}			
	//		}
	//	}

	public static void assertResponseBodyJsonHasKeys(Response response, List<String> keyJsonPathList)
	{
		SoftAssert softAssertObj = new SoftAssert();

		for(int i=0;i<keyJsonPathList.size();i++)
		{
			//RestAssuredUtils.softAssertResponseBodyHasKeyJsonPath(response, keyJsonPathList.get(i));//exists
			String keyJsonPath = keyJsonPathList.get(i);

			Reporter.log("\nAsserting if ResponseBodyHasKey(based on exact JsonPath):'"+keyJsonPath+"'", true);
			String responseBody = response.getBody().asString();

			List<String> valuesList;
			//case1: checking for .getList() if key has multiple occurrence
			try 
			{
				valuesList = JsonPath.from(responseBody).getList(keyJsonPath);
				//String valuesList = JsonPath.from(responseBody).getList(keyJsonPath).toString();

				//			Reporter.log("debug log! Checking for Multiple occurence of key. valuesList: "+valuesList, true);
				//			Reporter.log("debug log! :"+valuesList.contains(null)+"::"+valuesList.equals(null)+"::"+valuesList.isEmpty()+"::"+"valuesList.length()", true);

				//Assert.assertFalse(valuesList.contains(null)||valuesList.equals(null)||valuesList.isEmpty(), "Bug 1!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
				softAssertObj.assertFalse(valuesList.contains(null)||valuesList.equals(null)||valuesList.isEmpty(), "Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
			}
			catch(NullPointerException | ClassCastException e1)
			{
				String keyValue=null;
				//case2: checking for .get() if key has single occurrence
				try
				{
					keyValue = JsonPath.from(responseBody).get(keyJsonPath).toString();
					//Reporter.log("debug log! 1st catch block! Checking for single occurrence of key. Value: "+keyValue, true);
				}
				catch(NullPointerException e2)
				{
					//Reporter.log("debug log! 2nd catch block! Checking for single occurrence of key. Value: "+keyValue, true);

					//Assert.assertTrue(false,"Bug 2!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
					softAssertObj.assertTrue(false,"Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
				}			
			}
		}
		softAssertObj.assertAll();
	}

	public static void assertResponseBodyJsonHasValues(Response response, HashMap<String,Object> jsonKeyAndExpectedValueHashMap)
	{
		SoftAssert softAssertObj = new SoftAssert();

		//JavaLibs.printHashMap(jsonKeyAndExpectedValueHashMap);

		Set keys = jsonKeyAndExpectedValueHashMap.keySet(); 		
		for (Iterator i = keys.iterator(); i.hasNext(); ) 
		{
			String keyJsonPath = (String) i.next();
			String expectedValueOfKey = (String) jsonKeyAndExpectedValueHashMap.get(keyJsonPath);
			Reporter.log("\nAsserting key:'"+keyJsonPath+"' | expectedValue:'"+expectedValueOfKey+"'", true);

			//--Reporter.log("\nAsserting if ResponseBodyHasKey(based on exact JsonPath):'"+keyJsonPath+"'", true);
			String responseBody = response.getBody().asString();

			List<String> actualValuesList;
			//case1: checking for .getList() if key has multiple occurrence
			try 
			{
				actualValuesList = JsonPath.from(responseBody).getList(keyJsonPath);

				//check if jsonPath key exists?
				//				Reporter.log("debug log! Checking for Multiple occurence of key. valuesList: "+actualValuesList, true);
				//				Reporter.log("debug log! :"+actualValuesList.contains(null)+"::"+actualValuesList.equals(null)+"::"+actualValuesList.isEmpty()+"::"+"valuesList.length()", true);

				//softAssertObj.assertFalse(actualValuesList.contains(null)||actualValuesList.equals(null)||actualValuesList.isEmpty(), "Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");

				//Since key exists now, check value:
				Reporter.log("Asserting for key '"+keyJsonPath+"' "
						+ "whose actualValuesListFromResponseBody With MultipleOccurence,\n"
						+ "list size:" + actualValuesList.size() + "\n"
						+ "list:" + actualValuesList, true);
				Reporter.log("& comparing with expectedValueFromResponseBody as:'" + expectedValueOfKey+"'", true);
				//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
				softAssertObj.assertTrue(actualValuesList.equals(expectedValueOfKey),"Bug!! For key '"+keyJsonPath+"' none of the values from actualValuesListFromResponseBody With MultipleOccurence '"+actualValuesList+"' doesn't match expectedValueFromResponseBodyForKeyWithMultipleOccurence '" + expectedValueOfKey + "'");
			}
			catch(NullPointerException | ClassCastException e1)
			{
				String actualKeyValue=null;
				//case2: checking for .get() if key has single occurrence
				try
				{
					actualKeyValue = JsonPath.from(responseBody).get(keyJsonPath).toString();
					//					Reporter.log("debug log! 1st catch block! Checking for single occurrence of key. Value: "+actualKeyValue, true);

					Reporter.log("Asserting for key: '" + keyJsonPath + "'\n"
							+ "whose actualValueFromResponseBody For WithSingleOccurence is: '" +actualKeyValue+ "'\n"
							+ "and comparing with expected value as: '" + expectedValueOfKey + "'", true);

					//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
					softAssertObj.assertTrue(actualKeyValue.equals(expectedValueOfKey),"Bug!! For key '"+keyJsonPath+"' whose actualValueFromResponseBody With SingleOccurence is '"+actualKeyValue+"' doesn't match expectedValueFromResponseBody '" + expectedValueOfKey + "'");	

				}
				catch(NullPointerException e2)
				{
					//					e2.printStackTrace(); //only displayed on console
					//					Reporter.log("Exception log! " + e2, true);
					//					Reporter.log("debug log! 2nd catch block! Checking for single occurrence of key. Value: "+actualKeyValue, true);

					softAssertObj.assertTrue(false,"Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
				}//end 2nd catch block
			}//end 1st catch block
		}//end for loop
		softAssertObj.assertAll();
	}

	public static void assertResponseBodyForKeyWithMultipleOccurenceHasValue(Response response, String keyJsonPath, String expectedValueFromResponseBodyForKeyWithMultipleOccurence)
	{
		Reporter.log("\nAsserting:", true);
		String responseBody = response.getBody().asString();

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath);
		Reporter.log("actualValuesListFromResponseBodyForKeyWithMultipleOccurence '" +keyJsonPath+ "'\n"
				+ "list size:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size() + "\n"
				+ "list:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence, true);
		Reporter.log("expectedValueFromResponseBodyForKeyWithMultipleOccurence:" + expectedValueFromResponseBodyForKeyWithMultipleOccurence, true);

		//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
		Assert.assertTrue(actualValuesListFromResponseBodyForKeyWithMultipleOccurence.contains(expectedValueFromResponseBodyForKeyWithMultipleOccurence),"Error! None of the values from actualValuesListFromResponseBodyForKeyWithMultipleOccurence '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence+"' contains expectedValueFromResponseBodyForKeyWithMultipleOccurence '" + expectedValueFromResponseBodyForKeyWithMultipleOccurence + "'");
	}

	public static void assertResponseBodyForKeyWithMultipleOccurenceDoesntHaveValue(Response response, String keyJsonPath, String wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence)
	{
		Reporter.log("\nAsserting:", true);
		String responseBody = response.getBody().asString();

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath);

		Reporter.log("actualValuesListFromResponseBodyForKeyWithMultipleOccurence '" +keyJsonPath+ "'\n"
				+ "list size:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size() + "\n"
				+ "list:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence, true);
		Reporter.log("wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence:" + wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence, true);

		//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
		Assert.assertFalse(actualValuesListFromResponseBodyForKeyWithMultipleOccurence.contains(wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence),"Error! actualValuesListFromResponseBodyForKeyWithMultipleOccurence list '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence+"' contains wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence '" + wrongExpectedValueFromResponseBodyForKeyWithMultipleOccurence + "' which is a wrong value");
	}


	public static void assertResponseBodyForKeyWithMultipleOccurenceHasValues(Response response, String keyJsonPath, List<String> expectedValuesListFromResponseBodyForKeyWithMultipleOccurence)
	{
		Reporter.log("\nAsserting:", true);
		String responseBody = response.getBody().asString();

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath);

		Reporter.log("actualValuesListFromResponseBodyForKeyWithMultipleOccurence '" +keyJsonPath+ "'\n"
				+ "list size:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size() + "\n"
				+ "list:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence, true);
		Reporter.log("expectedValuesListFromResponseBodyForKeyWithMultipleOccurence:" + expectedValuesListFromResponseBodyForKeyWithMultipleOccurence, true);

		//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
		Assert.assertTrue(actualValuesListFromResponseBodyForKeyWithMultipleOccurence.containsAll(expectedValuesListFromResponseBodyForKeyWithMultipleOccurence),"Error! None of the values from actualValuesListFromResponseBodyForKeyWithMultipleOccurence '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence+"' contains expectedValuesListFromResponseBodyForKeyWithMultipleOccurence '" + expectedValuesListFromResponseBodyForKeyWithMultipleOccurence + "'");
	}

	public static void assertResponseBodyForKeyWithMultipleOccurenceDoesntHaveValues(Response response, String keyJsonPath, List<String> wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence)
	{
		Reporter.log("\nAsserting:", true);
		String responseBody = response.getBody().asString();

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath);

		Reporter.log("actualValuesListFromResponseBodyForKeyWithMultipleOccurence '" +keyJsonPath+ "'\n"
				+ "list size:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size() + "\n"
				+ "list:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence, true);
		Reporter.log("wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence:" + wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence, true);

		//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
		Assert.assertFalse(actualValuesListFromResponseBodyForKeyWithMultipleOccurence.containsAll(wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence),"Error! actualValuesListFromResponseBodyForKeyWithMultipleOccurence list '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence+"' contains some or all values from wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence '" + wrongExpectedValuesListFromResponseBodyForKeyWithMultipleOccurence + "' which are wrong values");
	}

	//
	public static void assertResponseBodyForKeyWithSingleOccurenceHasValue(Response response, String keyJsonPath, String expectedValueFromResponseBodyForKeyWithSingleOccurence)
	{
		Reporter.log("\nAsserting:", true);
		String responseBody = response.getBody().asString();

		try 
		{
			String actualValueFromResponseBodyForKeyWithSingleOccurence = JsonPath.from(responseBody).get(keyJsonPath).toString();
			Reporter.log("asserting for key '" + keyJsonPath + "'\n"
					+ "whose actualValueFromResponseBodyForKeyWithSingleOccurence value is '" +actualValueFromResponseBodyForKeyWithSingleOccurence+ "'\n"
					+ "and comparing with expected value as '" + expectedValueFromResponseBodyForKeyWithSingleOccurence + "'", true);

			//Check if List contains specific single 'expectedValue' as part of one of the 'Key' in the list
			Assert.assertTrue(actualValueFromResponseBodyForKeyWithSingleOccurence.contains(expectedValueFromResponseBodyForKeyWithSingleOccurence),"Error! actualValueFromResponseBodyForKeyWithSingleOccurence '"+actualValueFromResponseBodyForKeyWithSingleOccurence+"' doesn't match expectedValueFromResponseBodyForKeyWithSingleOccurence '" + expectedValueFromResponseBodyForKeyWithSingleOccurence + "'");	
		}
		catch(NullPointerException e)
		{
			e.printStackTrace(); //only displayed on console
			Reporter.log("Exception log! " + e, true);

			Assert.assertTrue(false,"Bug!! keyJsonPath '"+keyJsonPath+"' doesn't exist in Response body...");
		}

	}


	public static List<String> getResponseBodyValuesListForKeyWithMultipleOccurence(Response response, String keyJsonPath)
	{
		Reporter.log("\nGetting actualValuesListFromResponseBodyForKeyWithMultipleOccurence for 'keyJsonPath':'" +keyJsonPath+ "'", true);
		String responseBody = response.getBody().asString();

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath);

		Reporter.log("list size:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size(), true);
		Reporter.log("list Returned:" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence, true);

		return actualValuesListFromResponseBodyForKeyWithMultipleOccurence;
	}

	public static int getResponseBodyIndexNumberForKeyWithMultipleOccurenceMatchesExpectedValue(Response response, String keyJsonPath, String expectedValueOfKey)
	{
		Integer indexNumber = null;
		
		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence = getResponseBodyValuesListForKeyWithMultipleOccurence(response, keyJsonPath);

		for(int i=0; i< actualValuesListFromResponseBodyForKeyWithMultipleOccurence.size(); i++)
		{
			if(actualValuesListFromResponseBodyForKeyWithMultipleOccurence.get(i).equalsIgnoreCase(expectedValueOfKey))
			{
				indexNumber=i;
			}				
		}
		
		if(indexNumber==null)
          throw new RuntimeException("Failed! expectedValueOfKey '"+expectedValueOfKey+"' not found in list of keyJsonPath '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence+"'");
		else {
//			System.out.println("indexNumber="+indexNumber);
			return indexNumber;
		}
	}

	public static Integer getResponseBodyIndexNumberWhenKeyvalue1AndKeyvalue2AreInSameBlock(Response response, String keyJsonPath1, String expectedValueOfKey1, String keyJsonPath2, String expectedValueOfKey2)
	{
//		String responseBody = response.getBody().asString();
		Integer indexNumber = null;

		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence1 = getResponseBodyValuesListForKeyWithMultipleOccurence(response, keyJsonPath1);
		List<String> actualValuesListFromResponseBodyForKeyWithMultipleOccurence2 = getResponseBodyValuesListForKeyWithMultipleOccurence(response, keyJsonPath2);

		for(int i=0; i< actualValuesListFromResponseBodyForKeyWithMultipleOccurence1.size(); i++)
		{
			//			Reporter.log("actualValuesListFromResponseBodyForKeyWithMultipleOccurence1.get("+i+") = '" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence1.get(i) + "' :: " + "actualValuesListFromResponseBodyForKeyWithMultipleOccurence2.get("+i+") = '" + actualValuesListFromResponseBodyForKeyWithMultipleOccurence2.get(i) + "'");
			if(actualValuesListFromResponseBodyForKeyWithMultipleOccurence1.get(i).equalsIgnoreCase(expectedValueOfKey1) && actualValuesListFromResponseBodyForKeyWithMultipleOccurence2.get(i).equalsIgnoreCase(expectedValueOfKey2))
			{
				indexNumber=i;
			}				
		}
		if(indexNumber==null)
	          throw new RuntimeException("Failed! expectedValueOfKey1 '"+expectedValueOfKey1+"' not found in list of keyJsonPath '"+actualValuesListFromResponseBodyForKeyWithMultipleOccurence1+"'");
			else {
//				System.out.println("indexNumber="+indexNumber);
				return indexNumber;
			}
	}

	public static String getResponseBodyValueAtIndexNumberForKeyWithMultipleOccurence(Response response, String keyJsonPath, int indexNumberToRetriveValueFromResponseBodyForKeyWithMultipleOccurence)
	{
		Reporter.log("\nGetting actualValueAtIndexNumberFromResponseBodyForKeyWithMultipleOccurence for 'keyJsonPath':'" +keyJsonPath+ "' and at index:'"+indexNumberToRetriveValueFromResponseBodyForKeyWithMultipleOccurence+"'", true);
		String responseBody = response.getBody().asString();
		String actualValueatIndexNumberFromResponseBodyForKeyWithMultipleOccurence = JsonPath.from(responseBody).getList(keyJsonPath).get(indexNumberToRetriveValueFromResponseBodyForKeyWithMultipleOccurence).toString();
		Reporter.log("Value Returned:'" + actualValueatIndexNumberFromResponseBodyForKeyWithMultipleOccurence +"'", true);

		return actualValueatIndexNumberFromResponseBodyForKeyWithMultipleOccurence;
	}

	public static String getResponseBodyValueForKeyWithSingleOccurence(Response response, String keyJsonPath)
	{
		Reporter.log("\nGetting actualValueFromResponseBodyForKeyWithSingleOccurence for 'keyJsonPath':'" +keyJsonPath+ "'", true);
		String responseBody = response.getBody().asString();
		String actualValueFromResponseBodyForKeyWithSingleOccurence = JsonPath.from(responseBody).get(keyJsonPath);

		Reporter.log("Value Returned:'" + actualValueFromResponseBodyForKeyWithSingleOccurence +"'", true);

		return actualValueFromResponseBodyForKeyWithSingleOccurence;
	}
	
	public static String getValueForKeyWithSingleOccurenceFrom(String jsonString, String keyJsonPath)
	{
//		File jsonFile = new File(filePathWithName);
		Reporter.log("\nGetting valueForKeyWithSingleOccurenceFrom jsonSource String for 'keyJsonPath':'" +keyJsonPath+ "'", true);

		String actualValueFromResponseBodyForKeyWithSingleOccurence = JsonPath.from(jsonString).get(keyJsonPath);

		Reporter.log("Value Returned:'" + actualValueFromResponseBodyForKeyWithSingleOccurence +"'", true);

		return actualValueFromResponseBodyForKeyWithSingleOccurence;
	}


	public static void checkResponseTime(Response response) throws NumberFormatException, IOException
	{
		SoftAssert softAssertObj = new SoftAssert();

		long benchMarkResponseTimeInMs= Long.parseLong(FilesAndFolders.getValueFromPropertiesFile("benchMarkResponseTimeInMs"));
		long userCanAdjustResponseTimeInMs= Long.parseLong(FilesAndFolders.getValueFromPropertiesFile("userCanAdjustResponseTimeInMs"));
		long userLoosesInterestResponseTimeInMs= Long.parseLong(FilesAndFolders.getValueFromPropertiesFile("userLoosesInterestResponseTimeInMs"));
		long userLostResponseTimeInMs= Long.parseLong(FilesAndFolders.getValueFromPropertiesFile("userLostResponseTimeInMs"));

		long actualResponseTime = response.getTime();

		softAssertObj.assertTrue(actualResponseTime < benchMarkResponseTimeInMs,"Warning!! API actualResponseTime(in ms) '"+actualResponseTime+"' is > 'performance bench mark' - ResponseTime(in ms) '"+benchMarkResponseTimeInMs+"'");
		softAssertObj.assertTrue(actualResponseTime < userCanAdjustResponseTimeInMs,"Warning!! API actualResponseTime(in ms) '"+actualResponseTime+"' is > 'user can adjust and wait to load' - ResponseTime(in ms) '"+userCanAdjustResponseTimeInMs+"'");
		softAssertObj.assertTrue(actualResponseTime < userLoosesInterestResponseTimeInMs,"Bug!! API actualResponseTime(in ms) '"+actualResponseTime+"' is > 'user starts to loose interest in waiting' - ResponseTime(in ms) '"+userLoosesInterestResponseTimeInMs+"'");
		softAssertObj.assertTrue(actualResponseTime < userLostResponseTimeInMs,"Bug!! API actualResponseTime(in ms) '"+actualResponseTime+"' is > 'user has lost interest in waiting' - ResponseTime(in ms) '"+userLostResponseTimeInMs+"'");
		softAssertObj.assertAll();
	}


	public static void updateJsonFile(String filePathWithName, String keyWhoseValueShouldBeUpdated, String valueTobeUpdated) throws JsonParseException, JsonMappingException, IOException, JSONException
	{
		filePathWithName="./testdata/requestBodySamples/apiPathPost_Customer_Contact/abc.json";

		//				ObjectMapper mapper = new ObjectMapper();
		//		        JSONObject root = mapper.readValue(new File(filePathWithName), JSONObject.class);
		//		        System.out.println("root:\n"+root.toString());
		//		        
		//		//      JSONObject person =  jsonArray.getJSONObject(0).getJSONObject("person");
		//		        String val_older = root.getString(keyWhoseValueShouldBeUpdated);
		//		        System.out.println(val_older);
		//
		//
		//				 ObjectMapper mapper = new ObjectMapper();
		//			        String key = "data"; //whatever
		//		
		//			        JSONObject jo = new JSONObject("{key1:\"val1\", key2:\"val2\"}");
		//			        //Read from file
		//			        JSONObject root = mapper.readValue(new File(filePathWithName), JSONObject.class);
		//		
		//			        String val_newer = jo.getString(key);
		//			        String val_older = root.getString(key);
		//		
		//			        //Compare values
		//			        if(!val_newer.equals(val_older))
		//			        {
		//			          //Update value in object
		//			           root.put(key,val_newer);
		//		
		//			           //Write into the file
		//			            try (FileWriter file = new FileWriter("json_file")) 
		//			            {
		//			                file.write(root.toString());
		//			                System.out.println("Successfully updated json object to file...!!");
		//			            }
		//			        }


		//		ObjectMapper mapper = new ObjectMapper();
		//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

		//		JSONObject root = mapper.readValue(new File(filePathWithName), JSONObject.class);
		//		System.out.println("root:"+root);

		//	    Gson gson = new Gson();
		//	    JsonElement countryJsonElement = gson.fromJson(root.toString(), JsonElement.class);




		//        JSONParser parser = new JSONParser();
		//        
		//        try {
		//        	
		//            Object obj = parser.parse(new FileReader(filePathWithName));
		//
		//            JSONObject jsonObject =  (JSONObject) obj;
		//
		//            String name = (String) jsonObject.get("name");
		//            System.out.println("aaa:"+name);
		//
		//            jsonObject.replace("name", "jim", "jim1");
		//
		//            String name1 = (String) jsonObject.get("data");
		//            System.out.println("aaa:"+name1);
		//
		//       
		//            // loop array
		////            JSONArray cars = (JSONArray) jsonObject.get("cars");
		////            Iterator<String> iterator = cars.iterator();
		////            while (iterator.hasNext()) {
		////             System.out.println(iterator.next());
		////            }
		//        } catch (FileNotFoundException e) {
		//            e.printStackTrace();
		//        } catch (IOException e) {
		//            e.printStackTrace();
		//        } catch (ParseException e) {
		//            e.printStackTrace();
		//        }




		File file = new File(filePathWithName); 
		String jsonString = JsonPath.from(file).get().toString();
		System.out.println("aaa:"+jsonString);


		//https://stackoverflow.com/questions/40112028/parsing-json-object-using-gson-and-updating-the-json-file-search-and-update
		
		Gson gson = new Gson();
		JsonElement jsonElementComplete = gson.fromJson("{\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"type\": \"contacts\",\r\n" + 
				"    \"attributes\": {\r\n" + 
				"      \"version\": 1,\r\n" + 
				"      \"persona\": \"PROVIDER\",\r\n" + 
				"      \"subType\": \"doctor\",\r\n" + 
				"      \"sourceSystem\": \"GW\",\r\n" + 
				"      \"salutation\": \"\",\r\n" + 
				"      \"firstName\": \"Captain 4\",\r\n" + 
				"      \"lastName\": \"Kirk 4\",\r\n" + 
				"      \"formerName\": \"\",\r\n" + 
				"      \"mobilePhone\": \"\",\r\n" + 
				"      \"mobilePhoneCountry\": \"\",\r\n" + 
				"      \"mobilePhoneExtension\": \"\",\r\n" + 
				"      \"homePhone\": \"4\",\r\n" + 
				"      \"homePhoneCountry\": \"\",\r\n" + 
				"      \"homePhoneExtension\": \"\",\r\n" + 
				"      \"otherPhone\": \"\",\r\n" + 
				"      \"otherPhoneCountry\": \"\",\r\n" + 
				"      \"otherPhoneExtension\": \"\",\r\n" + 
				"      \"fax\": \"\",\r\n" + 
				"      \"faxCountry\": \"\",\r\n" + 
				"      \"faxEtension\": \"\",\r\n" + 
				"      \"phonePreference\": \"\",\r\n" + 
				"      \"email\": \"\",\r\n" + 
				"      \"secondaryEmail\": \"\",\r\n" + 
				"      \"description\": \"\",\r\n" + 
				"      \"mailingStreet\": \"\",\r\n" + 
				"      \"mailingCity\": \"\",\r\n" + 
				"      \"mailingCountry\": \"\",\r\n" + 
				"      \"mailingState\": \"\",\r\n" + 
				"      \"mailingPostalCode\": \"\",\r\n" + 
				"      \"otherStreet\": \"\",\r\n" + 
				"      \"otherCity\": \"\",\r\n" + 
				"      \"otherCountry\": \"\",\r\n" + 
				"      \"otherState\": \"\",\r\n" + 
				"      \"otherPostalCode\": \"\",\r\n" + 
				"      \"birthdate\": \"\",\r\n" + 
				"      \"gender\": \"\",\r\n" + 
				"      \"maritalStatus\": \"\",\r\n" + 
				"      \"speciality\": \"\"\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"", JsonElement.class);
		
		System.out.println("222:"+ jsonElementComplete);

		JsonElement specificObjectJsonElement = jsonElementComplete.getAsJsonObject().get("attributes");
		System.out.println("333:"+ specificObjectJsonElement);//chances of getting 'null'

		boolean updatedFlag = false;

		//update specific key-value in target object 
		if (specificObjectJsonElement != null) {
			System.out.println("aaa:"+specificObjectJsonElement.getAsJsonObject().get("lastName").getAsString());
			if (specificObjectJsonElement.getAsJsonObject().get("lastName").getAsString().equalsIgnoreCase("Kirk 4")) 
			{
				specificObjectJsonElement.getAsJsonObject().remove("lastName");
				specificObjectJsonElement.getAsJsonObject().addProperty("lastName", "111");
				updatedFlag = true;
			}
		}

		//update entire json with updated target object
		if (updatedFlag) {
			jsonElementComplete.getAsJsonObject().remove("attributes");
			jsonElementComplete.getAsJsonObject().add("attributes", specificObjectJsonElement);

			System.out.println("name updated....");
			System.out.println(jsonElementComplete.toString());
		} else {
			System.out.println("name not updated....");
			System.out.println(jsonElementComplete.toString());
		}




//		//update file
//		try
//		{
//			FileWriter file1 = new FileWriter("/C:/Users/itaas/Desktop/text1.txt");
//			file1.write(js.toString());
//			System.out.println("Successfully Copied JSON Object to File...");
//			System.out.println("\nJSON Object: " + js);
//
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//



	}



}
