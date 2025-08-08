package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.example.PageObject.productDetailsPage;
import org.example.PageObject.productPage;
import org.json.JSONObject;
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
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CategoryAndBrandTest {

    //    Product Listing & Category Tests
//    Test Case ID	Description
//    TC18	View Category Products
//    TC19	View & Cart Brand Products

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

@Test(priority = 1, groups = "productpage", description = "Verify All Products and product detail page",enabled = true)
public void testProductPage(){
    homePage hp = new homePage(driver);
    hp.navigateHomePage();
    //Click on 'Products' button
    hp.navigateToProductPage();
    //Verify user is navigated to ALL PRODUCTS page successfully
//    new WebDriverWait(driver, Duration.ofSeconds(10))
//            .until(d -> ((JavascriptExecutor) d)
//                    .executeScript("return document.readyState").equals("complete"));
    assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/products", "User is not navigated to the product page.");

    String text = driver.findElement(By.cssSelector("body > section:nth-child(3) > div > div > div.col-sm-9.padding-right > div > h2")).getText();
    assertEquals(text, "ALL PRODUCTS", "message not submitted");
    //The products list is visible
    productPage pp = new productPage(driver);
    List<WebElement> productlist = pp.getAllProductElements();
    assertEquals(productlist.size()-1, 34, "35 products not listed");

    WebElement pdpbtn=productlist.get(1).findElement(By.xpath("//a[starts-with(@href, '/product_details/')]"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].click();",pdpbtn );
    new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState").equals("complete"));
    assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/product_details/1", "User is not navigated to the product details page.");

    //Verify that detail detail is visible: product name, category, price, availability, condition, brand
    productDetailsPage pdp = new productDetailsPage(driver);
    JSONObject json =pdp.fetchProductDetails();
    List<String> expectedKeys = Arrays.asList("productName", "category", "price", "availability", "condition", "brand");

    for (String key : expectedKeys) {
        Assert.assertTrue(json.has(key), "Missing key in JSON: " + key);
        System.out.println("✅ Key found: " + key);
    }

}


}
