package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.example.PageObject.productPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ProductInteractionTest {

    public WebDriver driver = null;
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

    @Test(priority = 2, groups = "interactiontest", description = "Add review on product",enabled = true)
    public void addReviewOnProduct() {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToProductPage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/products", "User is not navigated to the product page.");

        productPage pp = new productPage(driver);
        List<WebElement> productlist = pp.getAllProductElements();
        WebElement pdpbtn=productlist.get(1).findElement(By.xpath("//a[starts-with(@href, '/product_details/')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",pdpbtn );
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/product_details/1", "User is not navigated to the product details page.");

        String actual = driver.findElement(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.category-tab.shop-details-tab > div.col-sm-12 > ul > li > a")).getText();
        assertEquals(actual, "WRITE YOUR REVIEW", "Write Your Review text not found");

        driver.findElement(By.cssSelector("#name")).sendKeys("deepika");
        driver.findElement(By.cssSelector("#email")).sendKeys("deepikashree.r@zohocorp.com");
        driver.findElement(By.cssSelector("#review")).sendKeys("happy testing");
        driver.findElement(By.cssSelector("#button-review")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));
        WebElement toast = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-success.alert"))
        );
        String toastMsg = toast.getText().trim();
        assertEquals(toastMsg, "Thank you for your review.", "Toast message not found or incorrect");
    }



}
