package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import qa.core.BaseClass;

public class HomePage extends BaseClass {
	By searchBox = By.xpath("//form[@id='search-form']//input");
	By searchResult = By.xpath("//div[contains(@class,'searchRes')]//div[@class='item']");
	By bookShowBtn = By.xpath("//div[text()='Book Now']");
	
	/**
	 * Method to search show
	 * @param showName : Pass the show name you want to search
	 * @return : True if show is available else false
	 */
	public boolean searchShow(String showName){
		boolean flag = false;
		WebElement searchBoxEle = _eventFiringDriver.findElement(searchBox);
		Actions act = new Actions(_eventFiringDriver);
		act.moveToElement(searchBoxEle).click().sendKeys(showName).build().perform();
		sleep(1);
		if(_eventFiringDriver.findElements(searchResult).size() > 0) {
			flag = true;
			System.out.println("Show is available");
			String searchPath = "//div[contains(@class,'searchRes')]//div[@class='item']/div[@class='title'][contains(text(),'"
					+ showName + "')]";
			WebElement searchOption = _eventFiringDriver.findElement(By.xpath(searchPath));
			searchOption.click();
			sleep(2);
		}
		else
			System.out.println("Search is not matching with any show. Please enter the correct show name");
		return flag;
	}
	
	/**
	 * Method to click on book show button
	 */
	public void clickBookShow() {
		_eventFiringDriver.findElement(bookShowBtn).click();
	}
}
