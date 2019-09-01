package automationHelper.api;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.IOException;

import com.github.tomakehurst.wiremock.WireMockServer;

import wiremock.org.apache.http.HttpStatus;

public class WiremockUtils {

	//starting the service on port
	public static int wireMockPortNo;
	public static Boolean wireMockEnabled; 
	public static WireMockServer WireMockServer;
	public static String apiPathMockSample = "/api/tax/";


	//	public static void main(String[] args) {
	//
	//		//forming the request and the response
	//		getStub_sampleTaxResponse_200();
	//		//starting the server
	//		server.start();
	//
	//		System.out.println("WireMock Server started");
	//		//server.stop();
	//	}


	public static void startWireMockServer() throws IOException
	{
		wireMockEnabled = Boolean.parseBoolean(FilesAndFolders.getValueFromPropertiesFile("./config.properties", "wireMockEnabled"));
				
		if(wireMockEnabled == true)
		{
			wireMockPortNo = Integer.parseInt(FilesAndFolders.getValueFromPropertiesFile("./config.properties", "wireMockPortNo"));
			WireMockServer = new WireMockServer(options().port(wireMockPortNo));
			
			WireMockServer.start();
			System.out.println("\nWireMock Server started on port:'"+wireMockPortNo+"'");
		}
	}

	public static void stopWireMockServer() throws IOException
	{
		Boolean wireMockEnabled = Boolean.parseBoolean(FilesAndFolders.getValueFromPropertiesFile("./config.properties", "wireMockEnabled"));
		if(wireMockEnabled == true)
		{
			WireMockServer.stop();
			System.out.println("\nWireMock Server stopped on port:'"+wireMockPortNo+"'\n");
		}
	}


	public static void getStub_sampleTaxResponse_200() {

		WireMockServer.stubFor(
				get(urlPathMatching(apiPathMockSample))
				.withQueryParam("city", equalTo("Redmond"))
				.withQueryParam("region", equalTo("WA"))
				.withQueryParam("postal", equalTo("98052"))
				.withQueryParam("country", equalTo("US"))

				.willReturn(
						aResponse() //response has to be the
						.withHeader("Content-Type", "application/json")
						.withStatus(HttpStatus.SC_OK)
						.withBody("{\n" +
								"  \"taxAmount\": 0,\n" +
								"  \"totalAmount\": 0.5,\n" +
								"  \"subTotalAmount\": 0.5\n" +
								"}")
						));

	}


}
