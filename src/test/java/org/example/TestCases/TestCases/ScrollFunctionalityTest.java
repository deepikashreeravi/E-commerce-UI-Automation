package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ScrollFunctionalityTest {
//     6. UI Scroll Functionality Tests
//    Test Case ID	Description
//    TC25	Scroll Up using 'Arrow' button and Scroll Down
//    TC26	Scroll Up without 'Arrow' button and Scroll Down

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

    @Test(priority = 1, groups = "scrollTest", description = "Verify Scroll Up using 'Arrow' button and Scroll Down functionality",enabled = true)
    public void verifyScrollUpWithArrowAndScrollDow() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        //Click on 'Products' button
        //hp.navigateToProductPage();

        // Scroll down page to bottom
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        String text = driver.findElement(By.cssSelector("#footer h2")).getText();
        assertEquals(text, "SUBSCRIPTION", "Subscription text not found in footer");

        Thread.sleep(3000);
        WebElement eb = driver.findElement(By.cssSelector("#scrollUp"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", eb);


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement headerText = wait.until(d -> d.findElement(By.xpath("//*[contains(text(),'Full-Fledged practice website for Automation Engineers')]")));

        Assert.assertTrue(headerText.isDisplayed(), "'Full-Fledged practice website for Automation Engineers' text is not visible on screen");
    }

    @Test(priority = 2, groups = "scrollTest", description = "Verify Scroll Up without 'Arrow' button and Scroll Down functionality",enabled = true)
    public void verifyScrollUpWithoutArrowAndScrollDown() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();

        // Scroll down page to bottom
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        String text = driver.findElement(By.cssSelector("#footer h2")).getText();
        assertEquals(text, "SUBSCRIPTION", "Subscription text not found in footer");
        Thread.sleep(3000);
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement headerText = wait.until(d -> d.findElement(By.xpath("//*[contains(text(),'Full-Fledged practice website for Automation Engineers')]")));

        Assert.assertTrue(headerText.isDisplayed(), "'Full-Fledged practice website for Automation Engineers' text is not visible on screen");

    }


}
