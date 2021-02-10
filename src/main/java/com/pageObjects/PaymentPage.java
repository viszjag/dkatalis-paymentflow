package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.base.PageBase;

/**
 * Class PaymentPage. Contains all methods and WebElements related to Payment page
 * Page
 */
public class PaymentPage extends PageBase {

	public PaymentPage(WebDriver driver) {
		super(driver);
	}

	/** Credit/Debit Card payment method */
	@FindBy(partialLinkText = "Credit/Debit Card") private WebElement creditCardPayment;
	

	/** payment selection modal */
	By cardPaymentModal = By.xpath("//p[.='Credit/Debit Card']");
	
	/** Payment screen pay now button */
	@FindBy(xpath = "//a[.='Pay Now']") private WebElement btnPayNow;
	
	
	/** card details */
	@FindBy(name = "cardnumber") private WebElement cardNumber;
	@FindBy(xpath = "//label[.='Expiry date']/..//preceding-sibling::input") private WebElement expiryDate;
	@FindBy(xpath = "//label[.='CVV']/..//preceding-sibling::input") private WebElement cvv;
	
	/** inline frame */
	@FindBy(xpath = "//iframe[1]") private WebElement iframe;
	
	/** payment password modal */
	By paymentPasswordModal = By.id("acsForm");
	
	
	/** card password */
	@FindBy(id = "PaRes") private WebElement password;
	
	/** submit payment button */
	@FindBy(xpath = "//button[@type='submit' and @name='ok']") private WebElement btnOk;
	
	/** transaction success modal */
	By successfulTransactionMsg = By.xpath("//*[.='Thank you for your purchase.']");
	
	/**
	 * Select Card Payment method and navigate to card payment page
	 *
	 */
	public boolean selectCardPayment() {
		creditCardPayment.click();
		return isPresentAndDisplayed(cardPaymentModal);
	}
	
	/**
	 * Fill card details details on payment screen
	 *
	 */
	public void fillCardDetails(String cardNumber, String expiryDate, String cvv) {
		this.cardNumber.sendKeys(cardNumber);
		this.expiryDate.sendKeys(expiryDate);
		this.cvv.sendKeys(cvv);
	}
	
	/**
	 * Click pay now on payment screen
	 *
	 */
	public boolean confirmCardDetails() {
		btnPayNow.click();
		driver.switchTo().frame(iframe);
		return isPresentAndDisplayed(paymentPasswordModal);
	}
	
	/**
	 * Enter bank otp and submit
	 *
	 */
	public boolean enterOTPAndConfirm(String otp) {
		password.sendKeys(otp);
		btnOk.click();
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isPresentAndDisplayed(successfulTransactionMsg);
	}
	
}
