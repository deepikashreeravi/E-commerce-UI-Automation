package org.example.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class paymentPage {
    public WebDriver driver;

    @FindBy(xpath = "//input[@data-qa='name-on-card']")
    private WebElement cardNameInput;

    @FindBy(xpath = "//input[@data-qa='card-number']")
    private WebElement cardNumberInput;

    @FindBy(xpath = "//input[@data-qa='cvc']")
    private WebElement cvcInput;

    @FindBy(xpath = "//input[@data-qa='expiry-month']")
    private WebElement expiryMonthInput;

    @FindBy(xpath = "//input[@data-qa='expiry-year']")
    private WebElement expiryYearInput;

    @FindBy(xpath = "//button[@data-qa='pay-button']")
    private WebElement payAndConfirmButton;

    public paymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigatePaymentPage() {
        driver.navigate().to("https://automationexercise.com/payment");
        driver.manage().window().maximize();
    }

    public void fillcardDetails(String cardName, String cardNumber, String cvc, String expiryMonth, String expiryYear) {
        cardNameInput.sendKeys(cardName);
        cardNumberInput.sendKeys(cardNumber);
        cvcInput.sendKeys(cvc);
        expiryMonthInput.sendKeys(expiryMonth);
        expiryYearInput.sendKeys(expiryYear);
    }

    public void clickPayAndConfirmButton() {
        payAndConfirmButton.click();
    }
}