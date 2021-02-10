package com.ui.tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pageObjects.PaymentPage;
import com.pageObjects.ShoppingCartPage;

public class FailedItemPurchase extends TestBase {
	private String baseURL = CONFIG.getProperty("baseURL");

	private String name = CONFIG.getProperty("name");
	private String email = CONFIG.getProperty("email");
	private String phoneNumber = CONFIG.getProperty("phoneNumber");
	private String city = CONFIG.getProperty("city");
	private String address = CONFIG.getProperty("address");
	private String postalCode = CONFIG.getProperty("postalCode");

	private String cardNumber = CONFIG.getProperty("invalidCardNumber");
	private String expiryDate = CONFIG.getProperty("invalidExpiryDate");
	private String cvvNumber = CONFIG.getProperty("invalidCVV");
	private String bankOTP = CONFIG.getProperty("invalidBankOTP");

	@BeforeTest
	@Parameters("browser")
	public void initDriver(@Optional String browser) {
		if (browser == null)
			browser = CONFIG.getProperty("browser");
		super.initDriver(browser);
	}
	
	@BeforeClass
	public void launchWebsite() {
		APP_LOGS.debug("Launching website: " + baseURL);
		openURL(baseURL);
	}

	@Test(priority = 1)
	public void checkShoppingCartCheckout() {
		ShoppingCartPage shoppingCart = new ShoppingCartPage(getDriver());
		
		APP_LOGS.debug("Navigate to Shopping cart..");
		boolean navigatedToCart = shoppingCart.openShopingCart();
		Assert.assertTrue(navigatedToCart, "Error in opening Shopping Cart page");
		
		APP_LOGS.debug("Enter customer details..");
		shoppingCart.fillCustomerDetails(name, email, phoneNumber, city, address, postalCode);
		boolean naviagtedToOrderSummary = shoppingCart.checkoutCart();
		
		APP_LOGS.debug("Navigate to Order Summary..");
		Assert.assertTrue(naviagtedToOrderSummary, "Error in opening Order Summary page");
		boolean naviagtedPaymentSelection = shoppingCart.confirmOrderSummary();
		
		APP_LOGS.debug("Navigate to Payment Selection..");
		Assert.assertTrue(naviagtedPaymentSelection, "Error in opening Payment Selection page");

		
	}

	@Test(dependsOnMethods = { "checkShoppingCartCheckout" })
	public void checkFailedPurchase() {
		PaymentPage payment = new PaymentPage(getDriver());
		
		APP_LOGS.debug("Navigate to Card Payment..");
		boolean naviagtedCardPayment = payment.selectCardPayment();
		Assert.assertTrue(naviagtedCardPayment, "Error in opening Payment Card page");
		
		APP_LOGS.debug("Enter card details..");
		payment.fillCardDetails(cardNumber, expiryDate, cvvNumber);
		boolean naviagtedBankOTP = payment.confirmCardDetails();
		
		APP_LOGS.debug("Validate failed purchase..");
		Assert.assertFalse(naviagtedBankOTP, "Error in opening Bank OTP page");
	}


	@AfterTest
	private void tearDown() {
		quitDriver();
	}

	@AfterMethod
	public void testResult(ITestResult result) {
		super.testResult(result);
	}

}
