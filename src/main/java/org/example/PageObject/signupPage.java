package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class signupPage {
    public WebDriver driver;

    public signupPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillAccountInfo(Boolean ismr, String name, String email,String password,String day,String month,String year,Boolean issignup, Boolean receiveoffers){

        // Title
        if(ismr) {
            driver.findElement(By.id("uniform-id_gender1")).click();
        }else {
            driver.findElement(By.id("uniform-id_gender2")).click();
        }
        // Name
        if (name!=null) {
            driver.findElement(By.id("name")).sendKeys(name);
        }
        // Email
        if(email!=null) {
            driver.findElement(By.id("email")).sendKeys(email);
        }
        // Password
        driver.findElement(By.id("password")).sendKeys(password);
        // Day of DOB
        Select select = new Select(driver.findElement(By.id("days")));
        select.selectByVisibleText(day);
        // Month of DOB
        select = new Select(driver.findElement(By.id("months")));
        select.selectByVisibleText(month);
        // Year of DOB
        select = new Select(driver.findElement(By.id("years")));
        select.selectByVisibleText(year);

        //Select checkbox 'Sign up for our newsletter!'
        if(issignup) {
            WebElement checkbox = driver.findElement(By.id("newsletter"));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        //Select checkbox 'Receive special offers from our partners!'
        if(receiveoffers) {
            driver.findElement(By.id("optin")).click();
        }

    }

    public void fillAddressInfo(String firstName, String lastName, String company, String address1,String address2, String state,String city,String zipcode,String mobileno){
        //Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.id("first_name")).sendKeys(firstName);
        driver.findElement(By.id("last_name")).sendKeys(lastName);
        driver.findElement(By.id("company")).sendKeys(company);
        driver.findElement(By.id("address1")).sendKeys(address1);
        driver.findElement(By.id("address2")).sendKeys(address2);
        driver.findElement(By.id("state")).sendKeys(state);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("zipcode")).sendKeys(zipcode);
        driver.findElement(By.id("mobile_number")).sendKeys(mobileno);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        //Click 'Create Account button'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-qa='create-account']")
                )
        );
        createAccountBtn.click();
    }
}
