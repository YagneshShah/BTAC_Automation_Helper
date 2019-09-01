package automationHelper.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;

import automationHelper.sa.FilesAndFolders;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredXmlUtils {

	public static Response sendAPIRequestAndReceiveResponse(String requestType, String apiBaseURL, String apiPath, Map <String,Object> queryParamsMap, Headers requestHeaders, String requestBody) throws IOException
	{
		Reporter.log("\n\nAPI Request Details:", true);
		Reporter.log("requestType:" + requestType, true);
		Reporter.log("apiBaseURL:" + apiBaseURL, true);
		Reporter.log("apiPath:" + apiPath, true);
		Reporter.log("queryParamsMap:\n" + queryParamsMap, true);
		Reporter.log("requestHeaders:\n" + requestHeaders, true);
		Reporter.log("requestBody:\n" + requestBody + "\n", true);

		int totalNamesapceCount = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("totalNamesapceCount"));
		HashMap<String, String> namespaceMap = new HashMap<String, String>();
		for(int i=0; i<totalNamesapceCount; i++)
		{
			namespaceMap.put(FilesAndFolders.getValueFromPropertiesFile("namespacePrefix"+i), FilesAndFolders.getValueFromPropertiesFile("namespaceUri"+i));
		}
		Reporter.log("SOAP namespaceMap for prefix/uri:"+namespaceMap,true);

		
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

//			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespace(namespacePrefix0, namespaceUri0));
			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig);//.queryParams("city", "Redmond","region", "WA","country", "US","postal", "98052");			
		}
		//one not null
		else if(queryParamsMap == null && requestHeaders == null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders == null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).body(requestBody); //.param("idp", "NGP");			
		}
		else if(queryParamsMap == null && requestHeaders != null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders != null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).headers(requestHeaders);
		}
		else if(queryParamsMap != null && requestHeaders == null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders == null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).queryParams(queryParamsMap);
		}
		//two not null
		else if(queryParamsMap != null && requestHeaders != null && requestBody == null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders != null && requestBody == null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).queryParams(queryParamsMap).headers(requestHeaders);
		}
		else if(queryParamsMap != null && requestHeaders == null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap != null && requestHeaders == null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).queryParams(queryParamsMap).body(requestBody);
		}
		else if(queryParamsMap == null && requestHeaders != null && requestBody != null)
		{
			Reporter.log("Building httpRequest, case: 'queryParamsMap == null && requestHeaders != null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).headers(requestHeaders).body(requestBody);
		}
		//all not null
		else
		{
			Reporter.log("Building httpRequest, case: 'queryParams != null && requestHeaders != null && requestBody != null'", true);
			RestAssured.baseURI = apiBaseURL;

			RestAssuredConfig raConfig = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespaces(namespaceMap));
			httpRequest = RestAssured.given().config(raConfig).queryParams(queryParamsMap).headers(requestHeaders).body(requestBody);
		}


		//sending api request
		Response response;
		switch(requestType.toLowerCase())
		{
		case "get":
			Reporter.log("\nSending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.GET, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "post":
			Reporter.log("\nSending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.POST, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "delete":
			Reporter.log("\nSending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.DELETE, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "patch":
			Reporter.log("\nSending API Request...", true);
			RestAssured.baseURI = apiBaseURL;
			response = httpRequest.request(Method.PATCH, apiPath);
			Reporter.log("Received Response from API Request...", true);
			return response;
		case "put":
			Reporter.log("\nSending API Request...", true);
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


	public static String getResponseBodySoapValueForKeyWithSingleOccurence(Response response, String keyXmlPath)
	{
		Reporter.log("\nGetting actualValueFromResponseBodyForKeyWithSingleOccurence for 'keyJsonPath':'" +keyXmlPath+ "'", true);
		String responseBody = response.getBody().asString();
		String actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.from(responseBody).get(keyXmlPath);

		Reporter.log("Value Returned:'" + actualValueFromResponseBodyForKeyWithSingleOccurence +"'", true);

		return actualValueFromResponseBodyForKeyWithSingleOccurence;
	}

//	public static String getResponseBodySoapValueForKeyWithSingleOccurence1(Response response, String keyXmlPath)
//	{
//		//RestAssuredConfig raconfig = RestAssured.config.xmlConfig.declareNamespace("test", "http://localhost/"))));
//
//		Reporter.log("\nGetting actualValueFromResponseBodyForKeyWithSingleOccurence for 'keyJsonPath':'" +keyXmlPath+ "'", true);
//		String responseBody = response.getBody().asString();
//		//		String actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.from(responseBody).getString(keyXmlPath);
//		//		String actualValueFromResponseBodyForKeyWithSingleOccurence = HasXPath.hasXPath(keyXmlPath).toString();
//
//
//
//		//		String responseBody = response.andReturn().body().asString();
//		//		System.out.println("aaa:"+responseBody);
//		//		Node actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.from(responseBody).get("result");\		
//		//        XmlPath xmlPath = new XmlPath(responseBody).setRoot("result");
//		//        String sessionId = xmlPath.get("sessionId");
//		//        System.out.println("bbb:"+sessionId);
//
//		//		XmlPath xmlPath = new XmlPath(responseBody);
//		//		Object a = xmlPath.get("loginResponse.result.sessionId");
//		//		System.out.println("ccc:"+ a);
//		//
//		//	    XmlPath xmlPath1 = new XmlPath(responseBody).using(xmlPathConfig().declaredNamespace("x", "http://something.com/test"));
//
//
//		//        RestAssuredConfig config = newConfig().xmlConfig(xmlConfig().with().namespaceAware(true).declareNamespace("x", "http://something.com/test"));
//		//		XmlPathConfig xmlPathConfig = new XmlPathConfig("http://schemas.xmlsoap.org/soap/envelope/");
//		//        System.out.println("NO NAMESPACE");
//		//        XmlPath xmlPath11 = XmlPath.from(responseBody).using(xmlPathConfig);
//		//        System.out.println("items: " + xmlPath11.get("soapenv:Envelope.soapenv:Body.loginResponse.result.sessionId"));
//
//
//		System.out.println("items1: " + XmlPath.from(responseBody).get("soapenv:Envelope.soapenv:Body.loginResponse.result.sessionId"));
//		System.out.println("items2: " + XmlPath.from(responseBody).getList("soapenv:Envelope.soapenv:Body.loginResponse.result.sessionId"));
//
//		//		XmlPathConfig xmlPathConfig = RestAssuredConfig.XmlPathConfig("soapenv");
//		//        System.out.println("NO NAMESPACE");
//		//        XmlPath xmlPath11 = XmlPath.from(responseBody).using(xmlPathConfig);
//		//        System.out.println("items: " + xmlPath11.get("soapenv:Envelope.soapenv:Body.loginResponse.result.sessionId"));
//
//		//        RestAssuredConfig ca = RestAssuredConfig.newConfig().xmlConfig(XmlConfig.xmlConfig().namespaceAware(true).declareNamespace("ns2", "http://namespace2.com"));
//		XmlConfig ca = XmlConfig.xmlConfig().namespaceAware(true).declareNamespace("ns2", "http://namespace2.com");
//		System.out.println("NO NAMESPACE");
//		XmlPath xmlPath11 = XmlPath.from(responseBody).using(xmlPathConfig);
//		System.out.println("items: " + xmlPath11.get("soapenv:Envelope.soapenv:Body.loginResponse.result.sessionId"));
//
//
//		//		  response = 
//		//		            config(RestAssuredConfig.newConfig().
//		//		                    xmlConfig(XmlConfig.xmlConfig().
//		//		                            namespaceAware(true).
//		//		                            declareNamespace("ns2", "http://namespace2.com"))).
//		//		            spec(requestSpec).
//		//		            header("SOAPAction", "http://localhost/getVendor").
//		//		            contentType("application/soap+xml; charset=UTF-8;").
//		//		            body(soapMessageInText).
//		//		    when().
//		//		            post("/v1/vendors").
//		//		    then().assertThat().
//		//		            contentType(ContentType.XML).
//		//		            statusCode(200).
//		//		            extract().response();
//		//		
//
//		//		Node actualValueFromResponseBodyForKeyWithSingleOccurence = HasXPath.hasXPath(keyXmlPath, namespaceContext);
//
//
//		//		String actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.from(responseBody).get(keyXmlPath);
//		//		String actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.with(responseBody).get(keyXmlPath);
//
//
//		//		Reporter.log("Value Returned:'" + actualValueFromResponseBodyForKeyWithSingleOccurence.toString() +"'", true);
//
//		return "";
//	}

	
	public static String getValueForKeyWithSingleOccurenceFrom(String xmlString, String keyXmlPath)
	{
		Reporter.log("\nGetting valueForKeyWithSingleOccurenceFrom xmlSource String for 'keyXmlPath':'" +keyXmlPath+ "'", true);

		String actualValueFromResponseBodyForKeyWithSingleOccurence = XmlPath.from(xmlString).get(keyXmlPath);
		Reporter.log("Value Returned:'" + actualValueFromResponseBodyForKeyWithSingleOccurence +"'", true);

		return actualValueFromResponseBodyForKeyWithSingleOccurence;
	}

}
