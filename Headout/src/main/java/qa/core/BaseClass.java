package qa.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import qa.pages.BookShowPage;
import qa.pages.HomePage;

public class BaseClass {

	private String currentDir = System.getProperty("user.dir");
	private String fileSeparator = System.getProperty("file.separator");
	private DesiredCapabilities capability;
	public WebDriver driver;
	public static EventFiringWebDriver _eventFiringDriver;
	public static Properties prop;

	public HomePage _homePage;
	public BookShowPage _bookShowPage;
	
	@BeforeClass(alwaysRun = true)
	public void login() throws Exception {
		getProptertiesUrl();
		setWebdriver();
		
		_homePage = PageFactory.initElements(_eventFiringDriver, HomePage.class);
		_bookShowPage = PageFactory.initElements(_eventFiringDriver, BookShowPage.class);
		
	}

	/**
	 * Method to set webdriver
	 * @throws Exception
	 */
	private void setWebdriver() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				currentDir + fileSeparator + "lib" + fileSeparator + "chromedriver.exe");
		capability = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("disable-infobars");
		capability.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capability);
		driver.manage().deleteAllCookies();
		_eventFiringDriver = new EventFiringWebDriver(driver);
		_eventFiringDriver.get(getUrl());
		_eventFiringDriver.manage().window().maximize();

	}

	/**
	 * Method to load properties
	 * @return
	 */
	private Properties getProptertiesUrl() {
		prop = new Properties();
		InputStream inputUrl = null;
		try {
			inputUrl = new FileInputStream(
					currentDir + fileSeparator + "properties" + fileSeparator + "prop.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// load a properties file
		try {
			prop.load(inputUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	protected String getUrl() {
		return prop.getProperty("launchURI");
	}
	
	/**
	 * Method to make webdriver sleep
	 * @param secs : Pass the seconds in number to sleep
	 */
	public void sleep(long secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to switch to show tab
	 * @param showName : Pass the show name
	 */
	public void switchTotab(String showName) {
		ArrayList<String> tabs = new ArrayList<String>(_eventFiringDriver.getWindowHandles());
		for (String tab : tabs) {
			_eventFiringDriver.switchTo().window(tab);
			if (_eventFiringDriver.getTitle().contains(showName))
				break;
		}
		sleep(5);
	}
	
	/**
	 * Method to switch to iframe
	 * @param locator : Pass the By locator of the iframe
	 */
	public void switchToIframe(By locator) {
		waitForElementToAppear(locator, 30);
		_eventFiringDriver.switchTo().frame(_eventFiringDriver.findElement(locator));
		System.out.println("switched to iframe");
	}
	
	/**
	 * Method to switch control to Parent Frame
	 */
	public void switchToParentIFrame() {
		_eventFiringDriver.switchTo().parentFrame();
		System.out.println("control is switched to Parent Frame");
	}
	
	/**
	 * Method to wait for element to appear
	 * @param locator
	 * @param timeToOut
	 */
	public void waitForElementToAppear(By locator,int timeToOut) {
		WebDriverWait wait = new WebDriverWait(_eventFiringDriver, timeToOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}
