package qa.pages;

import java.util.HashMap;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import qa.core.BaseClass;

public class BookShowPage extends BaseClass {
	
	By iframe = By.xpath("//div[contains(@class,'seatmap')]/iframe");
	By userForm = By.xpath("//span[text()='Enter guest details']");
	By confirmBookingBtn = By.xpath("//div[contains(@class,'booking-card')]//span[text()='COMPLETE MY BOOKING']");
	
	/**
	 * Method to book a show ticket
	 * @param showName : Pass the showName to book
	 * @param month : Pass the month name to book the ticket
	 * @param date : Pass the date to book the ticket
	 * @param sectionType : Pass the section type e.g. "Stalls", "Boxes", "Dress Circle", "Upper Circle" or "Balcony"
	 * @param seatRow : Pass the seat row to book the ticket
	 * @param seatNum : Pass the seat number to book the ticket
	 * @param guestDetails : Pass the guest detail to book the ticket
	 * @return : true if booking is successful else false
	 */
	public boolean bookShow(String showName,String month, String date, String sectionType, String seatRow, String seatNum, HashMap<String, String> guestDetails) {
		By dateEle = By.xpath("//div[@class='month-title']/span[contains(text(), '"+ month + "')]/../..//div[@class='date-list']/div/div[@class='date-label']/span[text()='"  + date + "']/../..//div[contains(@class,'price')]/span");
		waitForElementToAppear(dateEle, 20);
		WebElement calDate = _eventFiringDriver.findElement(dateEle);
		boolean flag = false;
		String cost = calDate.getText();
		if(!cost.equals("Unavailable")) {
			flag = true;
			calDate.click();
			switchToIframe(iframe);
			String seatPath = "//*[contains(@display,'" + sectionType + "')]//*[@class='row'][@display='" + seatRow
					+ "']//*[@class='seat bookable'][@display='" + seatNum + "']";
			if (_eventFiringDriver.findElements(By.xpath(seatPath)).size() > 0) {
				WebElement hallSeat = _eventFiringDriver.findElement(By.xpath(seatPath));
				hallSeat.click();
				switchToParentIFrame();
				_eventFiringDriver.findElement(By.xpath("//div[contains(@class,'next-button')]")).click();
				waitForElementToAppear(userForm, 10);
				fillGuestDetailForm(guestDetails);
			} else {
				flag = false;
				System.out.println("Seat " + seatRow + seatNum + " is not booked");
			}
		}
		return flag;
	}
	
	/**
	 * Method to fill the guest detail frfom to book a ticket
	 * @param guestDetails : Pass the guestDetail
	 */
	private void fillGuestDetailForm(HashMap<String, String> guestDetails) {
		for (Entry<String, String> entry : guestDetails.entrySet()) {
			String inputboxName = entry.getKey();
			String inputboxValue = entry.getValue();
			WebElement box = _eventFiringDriver.findElement(By.xpath("//input[@name='" + inputboxName + "']"));
			box.click();
			box.sendKeys(inputboxValue);
		}
		clickConfirmBooking();
		System.out.println("confirming booking");
	}

	/**
	 * Method to click to confirm booking
	 */
	private void clickConfirmBooking() {
		_eventFiringDriver.findElement(confirmBookingBtn).click();
	}
}

