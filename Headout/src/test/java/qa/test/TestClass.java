package qa.test;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import qa.pages.HomePage;

public class TestClass extends HomePage {

	@Test(priority = 0, enabled = true)
	public void bookTicket() {
		String showName = "The Simon and Garfunkel Story";
		String homoPageTitle = "Book London Theater Tickets | Get Exclusive Deals & Offers";
		String showPageTitle = "The Simon and Garfunkel Story London | Best Prices on West End Tickets";
		HashMap<String, String> guestDetails = new HashMap<String, String>();
		guestDetails.put("First Name", "Test");
		guestDetails.put("Last Name", "User");
		guestDetails.put("Email", "testuser@headout.com");
		guestDetails.put("Confirm Email", "testuser@headout.com");
		guestDetails.put("Phone", "9876543210");
		guestDetails.put("Card Number", "4321 3456 8765 5678");
		guestDetails.put("Expiry (MM/YY)", "02/21");
		guestDetails.put("CVV", "323");
		guestDetails.put("Card Holder Name", "Test User");
		
		Assert.assertTrue(_eventFiringDriver.getTitle().equals(homoPageTitle));
		Assert.assertTrue(searchShow(showName));
		clickBookShow();
		switchTotab(showName);
		System.out.println("show page title : " + _eventFiringDriver.getTitle());
		Assert.assertTrue(_eventFiringDriver.getTitle().equals(showPageTitle));
		
		_bookShowPage.bookShow(showName, "May", "20", "Stalls", "L", "10", guestDetails);
	}
}
