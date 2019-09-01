/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;


// TODO: Auto-generated Javadoc
/**
 * The Class MouseAndKeyboard.
 */
public class MouseAndKeyboard{
	
	/** The driver. */
	public static WebDriver driver;	
	
	/**
	 * Instantiates a new mouse and keyboard.
	 *
	 * @param driver the driver
	 */
	public MouseAndKeyboard(WebDriver driver){
		this.driver=driver;
	}
	
	
	/**
	 * Scrollup.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param xValue the x value. Ex:"500"
	 * @throws Exception 
	 */
	public static void scrollup(String xValue) throws Exception 
	{    
		String parameter="scroll(" +xValue+ ",0)"; 
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript(parameter); //x value '500' can be altered
	}

	/**
	 * Scroll down.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param yValue the y value. Ex:"500"
	 * @throws Exception 
	 */
	public static void scrollDown(String yValue) throws Exception 
	{       
		String parameter="scroll(0," +yValue+ ")"; 
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript(parameter); //y value '500' can be altered
	}

	/**
	 * Scroll to element via javascript.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @throws Exception 
	 */
	public static void scrollToElementViaJavascript(WebElement element) throws Exception 
	{    
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);     
	}


	/**
	 * Scroll to element via cordinate.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 * @throws Exception the exception
	 */
	public static void scrollToElementViaCordinate(WebElement element) throws Exception 
	{     
		Coordinates coordinate = ((Locatable)element).getCoordinates(); 
		coordinate.onPage(); 
		coordinate.inViewPort();     
	}

	/**
	 * Move to element.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param element the element
	 */
	public static void moveToElement(WebElement element)
	{
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();		
	}

	/**
	 * Move to element and select item.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param menuElement the menu element
	 * @param menuItem the menu item
	 * @throws InterruptedException the interrupted exception
	 */
	public static void moveToElementAndSelectItem(WebElement menuElement, WebElement menuItem) throws InterruptedException
	{
		Actions action = new Actions(driver);
		action.moveToElement(menuElement).moveToElement(menuItem).click().build().perform();
		Thread.sleep(3000);
	}

	
}