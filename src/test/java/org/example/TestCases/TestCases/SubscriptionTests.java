package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class SubscriptionTests extends BaseTest {
//    TC10	Verify Subscription in home page
//    TC11	Verify Subscription in Cart page
@Test(priority = 1, groups = "subscriptionTest", description = "Verify Test Cases Page",enabled = true)
public void testcasepagenavigation1(){
    homePage hp = new homePage(driver);
    hp.navigateHomePage();
    hp.navigateToTestCasePageViaHomePage();
    new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState").equals("complete"));
    assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/test_cases", "User is not navigated to the testcases page.");

}

    @Test(priority = 2, groups = "subscriptionTest", description = "Verify Test Cases Page",enabled = true)
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
