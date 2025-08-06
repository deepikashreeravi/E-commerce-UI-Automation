package org.example.TestCases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RegisterUser extends BaseTest {


    public static void main(String[] args) {
        RegisterUser lp = new RegisterUser();
        lp.setUp();

    }

    @Test(priority = 1, groups = "combinedvendors", description = "Verify that home page is visible successfully")
    public void homepage(){
//        WebElement keyElement = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logo > a > img"))
//        );
        //Verify that home page is visible successfully
        WebElement keyElement = driver.findElement(By.cssSelector(".logo > a > img"));
        assertTrue(keyElement.isDisplayed(), "Homepage key element not visible");

        // check page title
        assertEquals(driver.getTitle(), "Automation Exercise", "Unexpected page title");

        // check URL
        assertTrue(driver.getCurrentUrl().startsWith("https://automationexercise.com/"),
                "Unexpected URL: " + driver.getCurrentUrl());

        System.out.println("✅ Homepage loaded and visible successfully.");
    }

    @Test(priority = 2, groups = "combinedvendors", description = "Verify 'New User Signup!' is visible")
    public void loginpage(){
        // Click on 'Signup / Login' button
        driver.findElement(By.cssSelector("a[href='/login']")).click();
        //Verify 'New User Signup!' is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean textPresent = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/h2"),
                        "New User Signup!"
                )
        );

        assertTrue(textPresent, "'New User Signup!' text is NOT visible");

        //Enter name and email address
        driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)")).sendKeys("deepika");
        List<WebElement> emailInputs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("input[placeholder='Email Address']"), 1
        ));
        emailInputs.get(1).sendKeys("deepikashreego@gmail.com");

        //Click 'Signup' button
        WebElement second = driver.findElement(
                By.xpath("(//*[text()='Signup'])")
        );

        second.click();
    }

    @Test(priority = 3, groups = "combinedvendors", description = "Filling Account Information")
    public void signupPage() throws InterruptedException {
        //Verify that 'ENTER ACCOUNT INFORMATION' is visible
        String text = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div[1]/h2/b")).getText();
        assertEquals(text, "ENTER ACCOUNT INFORMATION", "Didn't land signup page");

        //Fill details: Title, Name, Email, Password, Date of birth
        WebElement radio = driver.findElement(By.id("uniform-id_gender2"));
        assertTrue(radio.isDisplayed(), "Radio button should be visible");
        assertTrue(radio.isEnabled(), "Radio button should be enabled");
        radio.click();
        assertTrue(radio.isEnabled(), "Radio button is not selected after click");

        driver.findElement(By.id("password")).sendKeys("godi");

        Select select = new Select(driver.findElement(By.id("days")));
        select.selectByVisibleText("16");;


        String selected = select.getFirstSelectedOption().getText();
        System.out.println("Selected day is: " + selected);

        assert selected.equals("16") : "Failed to select day 16";


        select = new Select(driver.findElement(By.id("months")));
        select.selectByVisibleText("December");

        select = new Select(driver.findElement(By.id("years")));
        select.selectByVisibleText("1999");

        //Select checkbox 'Sign up for our newsletter!'
        WebElement checkbox = driver.findElement(By.id("newsletter"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        assertTrue(checkbox.isSelected(), "Newsletter checkbox is not selected");

        //Select checkbox 'Receive special offers from our partners!'
        checkbox = driver.findElement(By.id("optin"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        assertTrue(checkbox.isSelected(), "Receive special offers from our partners! checkbox is not selected");



    }
    @Test(priority = 4, groups = "combinedvendors", description = "Account Creation")
    public void accountCreation() throws InterruptedException {
        //Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.id("first_name")).sendKeys("deepika shree");
        driver.findElement(By.id("last_name")).sendKeys("ravi");
        driver.findElement(By.id("company")).sendKeys("zoho");
        driver.findElement(By.id("address1")).sendKeys("guduvancheri");
        driver.findElement(By.id("address2")).sendKeys("tenkasi");
        driver.findElement(By.id("state")).sendKeys("tamilnadu");
        driver.findElement(By.id("city")).sendKeys("chennai");
        driver.findElement(By.id("zipcode")).sendKeys("603202");
        driver.findElement(By.id("mobile_number")).sendKeys("8987898765");

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

        //account-created
        //Verify that 'ACCOUNT CREATED!' is visible
        String text = driver.findElement(By.className("title")).getText();
        assertEquals(text, "ACCOUNT CREATED!", "Account not created");

    }

    @Test(priority = 5, groups = "combinedvendors", description = "Verify \"Logged in as [username]\" Display After Login")
    public void displayAfterLogin() throws InterruptedException {
        //Verify that 'Logged in as username' is visible
        driver.findElement(By.xpath("//a[text()='Continue']")).click();
        String actual=driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10)")).getText();
        assertEquals(actual, "Logged in as deepika", "Not logged in as user deepika");
    }

    @Test(priority = 6, groups = "combinedvendors", description = "Account Deletion")
    public void accountDeletion() throws InterruptedException {
        //Click 'Delete Account' button
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();

        //Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        String actual = driver.findElement(By.className("title")).getText();
        assertEquals(actual, "ACCOUNT DELETED!", "account not deleted");
    }

}
