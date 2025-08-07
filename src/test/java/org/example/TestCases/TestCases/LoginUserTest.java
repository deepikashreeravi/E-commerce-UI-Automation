package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginUserTest  {

    WebDriver driver = null;
    @BeforeClass
    public void setUp() {
        BaseTest baseTest = new BaseTest();
        baseTest.setUp();
        this.driver= baseTest.driver;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1, groups = "login", description = " Login User with correct email and password",enabled = true)
    public void loginSuccess(){
        //1. Launch browser and 2. Navigate to url 'http://automationexercise.com'
        //setUp();
        WebElement keyElement = driver.findElement(By.cssSelector(".logo > a > img"));
        assertTrue(keyElement.isDisplayed(), "Homepage key element not visible");

        //3. Verify that home page is visible successfully
        // check page title
        assertEquals(driver.getTitle(), "Automation Exercise", "Unexpected page title");

        // check URL
        assertTrue(driver.getCurrentUrl().startsWith("https://automationexercise.com/"),
                "Unexpected URL: " + driver.getCurrentUrl());

        System.out.println("✅ Homepage loaded and visible successfully.");

        //4. Click on 'Signup / Login' button
        homePage hp = new homePage(driver);
        hp.navigateToLoginPageViaHomePage();

        //5. Verify 'Login to your account' is visible
        String text = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/h2")).getText();
        assertEquals(text, "Login to your account", "Didn't land login page");

        loginPage lp = new loginPage(driver);

        try {
            lp.signup("deepikashree.r@zohocorp.com", "deepika-12161");

            signupPage sp = new signupPage(driver);
            sp.fillAccountInfo(true, null, null, "godi", "16", "December", "1999", true, true);
            sp.fillAddressInfo("deepika shree", "ravi", "zoho", "guduvancheri", "tenkasi", "tamilnadu", "chennai", "603202", "8987898765");

            hp.navigateHomePage();
            hp.logout();
        }catch(Exception ex){

        }

        //6. Enter correct email address and password and 7. Click 'login' button
        lp.login("deepikashree.r@zohocorp.com","godi");

        //8. Verify that 'Logged in as username' is visible
        String actual=driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10)")).getText();
        assertEquals(actual, "Logged in as deepika-12161", "Not logged in as user deepika");

//        //9. Click 'Delete Account' button
//        hp.accountDeletion();
//
//        //10. Verify that 'ACCOUNT DELETED!' is visible
//        actual = driver.findElement(By.className("title")).getText();
//        assertEquals(actual, "ACCOUNT DELETED!", "account not deleted");
    }

    @Test(priority = 2, groups = "login", description = "Logout User",enabled = true)
    public void logout(){
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.logout();
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/login", "User is not redirected to the login page after logout.");


    }

    @Test(priority = 3, groups = "login", description = "Login User with correct email and password",enabled = true)
    public void loginWithInvalidCredentials_ShouldFail(){
        loginPage lp = new loginPage(driver);
        lp.navigateLoginPage();
        lp.login("deepikashree.r@zohocorp.com","g899");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errormessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/p")
                )
        );

        String actual = errormessage.getText();
        assertEquals(actual, "Your email or password is incorrect!", "error message not displayed");
    }

    @Test(priority = 4, groups = "register", description = "Register User with existing email",enabled = true)
    public void registerUserWithExistingEmail(){
        loginPage lp = new loginPage(driver);
        lp.signup("deepikashree.r@zohocorp.com", "deepika-12161");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errormessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/section/div/div/div[3]/div/form/p")
                )
        );

        String actual = errormessage.getText();
        assertEquals(actual, "Email Address already exist!", "error message not displayed");

    }

    @Test(priority = 5, groups = "contact", description = "Contact Us Form",enabled = true)
    public void contactUsForm() throws IOException, InterruptedException, AWTException {
        homePage hp = new homePage(driver);
        //Navigate to url 'http://automationexercise.com'
        hp.navigateHomePage();
        //Click on 'Contact Us' button
        hp.navigateToContactPageViaHomePage();

        //Verify 'GET IN TOUCH' is visible
        String text = driver.findElement(By.cssSelector("h2.title:nth-child(2)")).getText();
        assertEquals(text, "GET IN TOUCH", "Didn't land contact page");

        //Enter name, email, subject and message
        //Upload file
        //Click 'Submit' button
        //Click OK button
        contactPage cp = new contactPage(driver);
        cp.fillform("deepika","deepikashree.r@zohocorp.com","positive feedback","happy testing");

        text = driver.findElement(By.cssSelector("#contact-page > div.row > div.col-sm-8 > div > div.status.alert.alert-success")).getText();
        assertEquals(text, "Success! Your details have been submitted successfully.", "message not submitted");

        driver.findElement(By.xpath("//*[@id=\"form-section\"]/a/span")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/", "User is not redirected to the login page after logout.");

    }

    @Test(priority = 6, groups = "testcasepage", description = "Verify Test Cases Page",enabled = true)
    public void testcasepagenavigation(){
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToTestCasePageViaHomePage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/test_cases", "User is not navigated to the testcases page.");

    }






}
