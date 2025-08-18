package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class loginPage {
    public WebDriver driver;

    public loginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)")
    private WebElement nameInput;

    @FindBy(css = "input[placeholder='Email Address']")
    private List<WebElement> emailInputs;

    @FindBy(xpath = "(//*[text()='Signup'])")
    private WebElement signupButton;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    private WebElement loginEmailInput;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    private WebElement loginPasswordInput;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    private WebElement loginButton;

    public void navigateLoginPage(){
        driver.navigate().to("https://automationexercise.com/login");
        driver.manage().window().maximize();
    }

    public void signup(String email, String name){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        nameInput.sendKeys(name);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                org.openqa.selenium.By.cssSelector("input[placeholder='Email Address']"), 1
        ));
        emailInputs.get(1).sendKeys(email);
        signupButton.click();
    }

    public void login(String email, String password){
        loginEmailInput.sendKeys(email);
        loginPasswordInput.sendKeys(password);
        loginButton.click();
    }
}
