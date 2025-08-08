package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SubscriptionTests {
//    TC10	Verify Subscription in home page
//    TC11	Verify Subscription in Cart page

    public WebDriver driver = null;
    BaseTest baseTest = null;

    @BeforeClass
    public void setUp() {
        this.baseTest = new BaseTest();
        baseTest.setUp();
        this.driver = baseTest.driver;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1, groups = "subscriptionTest", description = "Verify Subscription in home page", enabled = true)
    public void verifySubscriptionInHomePage() {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        baseTest.scrollToFooter();

        subscribeAndVerify("deepikashree.r@zohocorp.com");

    }

    @Test(priority = 2, groups = "subscriptionTest", description = "Verify Subscription in Cart page", enabled = true)
    public void verifySubscriptionInCartPage() {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToCartPageViaHomePage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/view_cart", "User is not navigated to the cart page.");
        baseTest.scrollToFooter();

        subscribeAndVerify("deepikashree.r@zohocorp.com");

    }

    private void subscribeAndVerify(String email) {
        // Verify 'SUBSCRIPTION' text
        String text = driver.findElement(By.cssSelector("#footer h2")).getText();
        assertEquals(text, "SUBSCRIPTION", "Subscription text not found in footer");
        // Enter email and submit
        driver.findElement(By.cssSelector("#susbscribe_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#subscribe")).click();
        // Verify success message
        assertTrue(driver.findElement(By.className("alert-success")).isDisplayed(), "Subscription success message not displayed");
    }


}
