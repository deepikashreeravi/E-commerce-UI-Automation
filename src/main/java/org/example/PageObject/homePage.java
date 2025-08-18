package org.example.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class homePage {
    public WebDriver driver;

    @FindBy(css = "a[href='/login']")
    private WebElement loginLink;

    @FindBy(css = "a[href='/contact_us']")
    private WebElement contactLink;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartLink;

    @FindBy(css = "a[href='/test_cases']")
    private WebElement testCaseLink;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutLink;

    @FindBy(css = "a[href='/products']")
    private WebElement productsLink;

    @FindBy(css = "a[href='/delete_account']")
    private WebElement deleteAccountLink;

    @FindBy(css = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10)")
    private WebElement userNameElement;

    public homePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateHomePage() {
        driver.navigate().to("https://automationexercise.com/");
        driver.manage().window().maximize();
    }

    public void navigateToLoginPageViaHomePage() {
        loginLink.click();
    }

    public void navigateToContactPageViaHomePage() {
        contactLink.click();
    }

    public void navigateToCartPageViaHomePage() {
        cartLink.click();
    }

    public void navigateToTestCasePageViaHomePage() {
        testCaseLink.click();
    }

    public void logout() {
        logoutLink.click();
    }

    public void navigateToProductPage() {
        productsLink.click();
    }

    public void accountDeletion() {
        deleteAccountLink.click();
    }

    public String getUserName() {
        return userNameElement.getText();
    }
}