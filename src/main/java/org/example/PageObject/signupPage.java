package org.example.PageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class signupPage {
    public WebDriver driver;

    @FindBy(id = "uniform-id_gender1")
    private WebElement mrRadio;

    @FindBy(id = "uniform-id_gender2")
    private WebElement mrsRadio;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement daySelect;

    @FindBy(id = "months")
    private WebElement monthSelect;

    @FindBy(id = "years")
    private WebElement yearSelect;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement offersCheckbox;

    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountBtn;

    public signupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillAccountInfo(Boolean ismr, String name, String email, String password, String day, String month, String year, Boolean issignup, Boolean receiveoffers) {
        if (ismr) {
            mrRadio.click();
        } else {
            mrsRadio.click();
        }
        if (name != null) {
            nameInput.sendKeys(name);
        }
        if (email != null) {
            emailInput.sendKeys(email);
        }
        passwordInput.sendKeys(password);

        new Select(daySelect).selectByVisibleText(day);
        new Select(monthSelect).selectByVisibleText(month);
        new Select(yearSelect).selectByVisibleText(year);

        if (issignup && !newsletterCheckbox.isSelected()) {
            newsletterCheckbox.click();
        }
        if (receiveoffers && !offersCheckbox.isSelected()) {
            offersCheckbox.click();
        }
    }

    public void fillAddressInfo(String firstName, String lastName, String company, String address1, String address2, String state, String city, String zipcode, String mobileno) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        companyInput.sendKeys(company);
        address1Input.sendKeys(address1);
        address2Input.sendKeys(address2);
        stateInput.sendKeys(state);
        cityInput.sendKeys(city);
        zipcodeInput.sendKeys(zipcode);
        mobileNumberInput.sendKeys(mobileno);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountBtn)).click();
    }
}