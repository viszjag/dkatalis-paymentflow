package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.base.PageBase;

/**
 * Class ShoppingCartPage. Contains all methods and WebElements related to Shopping cart Page
 */
public class ShoppingCartPage extends PageBase {

	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}

	/** Buy Now button */
	@FindBy(linkText = "BUY NOW") private WebElement btnBuy;

	/** Checkout button */
	@FindBy(xpath = "//div[.='CHECKOUT']") private WebElement btnCheckout;

	/** cart label */
	By shoppingCartLabel = By.xpath("//span[contains(.,'Shopping Cart')]");
	
	/** customer label */
	@FindBy(xpath = "//tr[.='Name']//input") private WebElement custName;
	@FindBy(xpath = "//tr[.='Email']//input") private WebElement custEmail;
	@FindBy(xpath = "//tr[.='Phone no']//input") private WebElement custPhone;
	@FindBy(xpath = "//tr[.='City']//input") private WebElement custCity;
	@FindBy(xpath = "//tr[td[.='Address']]//textarea") private WebElement custAddress;
	@FindBy(xpath = "//tr[.='Postal Code']//input") private WebElement custPostalCode;
	
	
	/** inline frame */
	@FindBy(id = "snap-midtrans") private WebElement iframe;
	
	/** order summary modal */
	By orderSummaryModal = By.xpath("//p[.='Order Summary']");
	
	/** payment selection modal */
	By paymentSelectionModal = By.xpath("//p[.='Select Payment']");
	
	/** Order Summary Continue button */
	@FindBy(xpath = "//a[.='Continue']") private WebElement btnContinue;
	
	/**
	 * Navigate to Shopping Cart
	 *
	 */
	public boolean openShopingCart() {
		btnBuy.click();
		return isPresentAndDisplayed(shoppingCartLabel);
	}

	/**
	 * Fill Customer name and contact details
	 *
	 */
	public void fillCustomerDetails(String name, String email, String phoneNumber, String city, String address, String postCode) {
		custName.clear();
		custEmail.clear();
		custPhone.clear();
		custCity.clear();
		custAddress.clear();
		custPostalCode.clear();
		
		custName.sendKeys(name);
		custEmail.sendKeys(email);
		custPhone.sendKeys(phoneNumber);
		custCity.sendKeys(city);
		custAddress.sendKeys(address);
		custPostalCode.sendKeys(postCode);
	}

	/**
	 * Confirm checkout & Navigate to Order Summary 
	 *
	 */
	public boolean checkoutCart() {
		btnCheckout.click();
		driver.switchTo().frame(iframe);
		boolean flag = isPresentAndDisplayed(orderSummaryModal);
		return flag;
	}
	
	/**
	 * Confirm Order summary & Navigate to Payment Selection Page 
	 *
	 */
	public boolean confirmOrderSummary() {
		btnContinue.click();
		return isPresentAndDisplayed(paymentSelectionModal);
	}
}
