package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class paymentPage {
    public WebDriver driver;

    public paymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigatePaymentPage(){
        driver.navigate().to("https://automationexercise.com/payment");
        driver.manage().window().maximize();
    }

    public void fillcardDetails(String cardName, String cardNumber, String cvc, String expiryMonth, String expiryYear) {
        driver.findElement(By.xpath("//input[@data-qa='name-on-card']")).sendKeys(cardName);
        driver.findElement(By.xpath("//input[@data-qa='card-number']")).sendKeys(cardNumber);
        driver.findElement(By.xpath("//input[@data-qa='cvc']")).sendKeys(cvc);
        driver.findElement(By.xpath("//input[@data-qa='expiry-month']")).sendKeys(expiryMonth);
        driver.findElement(By.xpath("//input[@data-qa='expiry-year']")).sendKeys(expiryYear);
    }

    public void clickPayAndConfirmButton() {
        driver.findElement(By.xpath("//button[@data-qa='pay-button']")).click();
    }

}
