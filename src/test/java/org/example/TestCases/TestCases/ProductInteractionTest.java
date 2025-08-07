package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.example.PageObject.productPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ProductInteractionTest {

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

//    2. Search and Product Interaction Tests
//    Test Case ID	Description
//    TC9	Search Product
//    TC21	Add review on product
@Test(priority = 1, groups = "interactiontest", description = "Search Product",enabled = true)
public void searchProduct(){
    homePage hp = new homePage(driver);
    hp.navigateHomePage();
    //Click on 'Products' button
    hp.navigateToProductPage();
    //Verify user is navigated to ALL PRODUCTS page successfully
    new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState").equals("complete"));
    assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/products", "User is not navigated to the product page.");
    productPage pp = new productPage(driver);
    pp.searchProduct("pure cotton");
    List<WebElement> productlist = pp.getAllProductElements();
    assertEquals(productlist.size()-1, 2, "filter not applied");
}


}
