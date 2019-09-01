/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah, Adil Imroz
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * The Class launchMobileApp.
 */
public class launchMobileApp 
{
	/** The driver. */
	public static AppiumDriver driver;



	/**
	 * Launch app based on appName. This will assist to launch multiple app with different appPackage/appActivity capabilities within same test script scope<br><br>
	 * Ex:"flipkart". This App name passed will concat with AppPackage/AppActivity string. The resulting string variable value is retrieved from config.properties file & will automatically launch the app/driver session 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param appName the app name. Ex:"flipkart" or"fk" or anything which is exactly same as variable name in config.properties file
	 * @throws Exception the exception
	 */
	public static void launchRealAndroidApp(String appName) throws Exception 
	{ //Update this method based on your app context. This is just for code reference
		
		System.out.println("*****Launching the app*****");

		String appPack = StringManipulation.concatString(appName, "AppPackage");
		String appAct = StringManipulation.concatString(appName, "AppActivity");
		String appPackage = FilesAndFolders.getPropValue(appPack);
		String appActivity = FilesAndFolders.getPropValue(appAct);
		System.out.println("appPackage: " + appPackage);
		System.out.println("appActivity: " + appActivity);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage",appPackage);
		capabilities.setCapability("appActivity",appActivity);

		//capabilities.setCapability("sessionOverride", true);
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		MobileCommonMethods.callingImplicitSleep();
		//return driver;
	}

	
	/**
	 * Launch emulator and installs android app via local system APK path and then launches the App directly.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param localSysApkPath the local sys apk path.<br>
	 * Ex for windows: "D:/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk"<br>
	 * Ex for mac: "/Users/username/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk"
	 * @return the appium driver
	 * @throws Exception the exception
	 */
	public static AppiumDriver launchEmulatorAndroidAppViaLocalAPKPath(String localSysApkPath) throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Native App******");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
		capabilities.setCapability(MobileCapabilityType.APP, localSysApkPath);//Ex: D:/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		MobileCommonMethods.callingImplicitSleep();
		return driver;
	}
	
	
	/**
	 * Launch emulator android app. This will assist to launch multiple app with different appPackage/appActivity capabilities within same test script scope<br><br>
	 * Ex:"flipkart". This App name passed will concat with AppPackage/AppActivity string. The resulting string "fkAppPackage/fkAppActivity" variable value is retrieved from config.properties file & will automatically launch the app/driver session
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param appName the app name. Ex:"flipkart" or"fk" or anything which is exactly same as variable name in config.properties file
	 * @return the appium driver
	 * @throws Exception the exception
	 */
	public static AppiumDriver launchEmulatorAndroidApp(String appName) throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Native App******");

		String appPack = StringManipulation.concatString(appName, "AppPackage");
		String appAct = StringManipulation.concatString(appName, "AppActivity");
		String appPackage = FilesAndFolders.getPropValue(appPack);
		String appActivity = FilesAndFolders.getPropValue(appAct);
		System.out.println("appPackage: " + appPackage);
		System.out.println("appActivity: " + appActivity);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "4.4"); 
		capabilities.setCapability("appPackage",appPackage);
		capabilities.setCapability("appActivity",appActivity);
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		MobileCommonMethods.callingImplicitSleep();
		return driver;
	}
	
	
	/**
	 * Launch emulator android browser.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the appium driver
	 * @throws Exception the exception
	 */
	public static AppiumDriver launchEmulatorAndroidBrowser() throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Browser******");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "4.4"); 
		capabilities.setCapability("browserName", "Chrome");
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		MobileCommonMethods.callingImplicitSleep();
		return driver;
	}

}


