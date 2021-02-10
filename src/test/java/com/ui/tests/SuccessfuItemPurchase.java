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

public class SuccessfuItemPurchase extends TestBase {
	private String baseURL = CONFIG.getProperty("baseURL");

	private String name = CONFIG.getProperty("name");
	private String email = CONFIG.getProperty("email");
	private String phoneNumber = CONFIG.getProperty("phoneNumber");
	private String city = CONFIG.getProperty("city");
	private String address = CONFIG.getProperty("address");
	private String postalCode = CONFIG.getProperty("postalCode");

	private String cardNumber = CONFIG.getProperty("validCardNumber");
	private String expiryDate = CONFIG.getProperty("validExpiryDate");
	private String cvvNumber = CONFIG.getProperty("validCVV");
	private String bankOTP = CONFIG.getProperty("validBankOTP");

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
	public void checkSuccessfulPurchase() {
		PaymentPage payment = new PaymentPage(getDriver());
		
		APP_LOGS.debug("Navigate to Card Payment..");
		boolean naviagtedCardPayment = payment.selectCardPayment();
		Assert.assertTrue(naviagtedCardPayment, "Error in opening Payment Card page");
		
		APP_LOGS.debug("Enter card details..");
		payment.fillCardDetails(cardNumber, expiryDate, cvvNumber);
		boolean naviagtedBankOTP = payment.confirmCardDetails();
		
		APP_LOGS.debug("Navigate to Bank OTP page..");
		Assert.assertTrue(naviagtedBankOTP, "Error in opening Bank OTP page");
		boolean isSuccessfulPurchase = payment.enterOTPAndConfirm(bankOTP);
		
		APP_LOGS.debug("Validate successful purchase..");
		Assert.assertTrue(isSuccessfulPurchase, "Error in successful end to end purchase transaction.");
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
