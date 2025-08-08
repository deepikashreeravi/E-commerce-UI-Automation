package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.cartPage;
import org.example.PageObject.homePage;
import org.example.PageObject.productPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CartFunctionalityTest {

// 1. Cart-related Tests
//    Test Case ID	Description
//    TC12	Add Products in Cart
//    TC13	Verify Product quantity in Cart
//    TC17	Remove Products From Cart
//    TC22	Add to cart from Recommended items
//    TC20	Search Products and Verify Cart After Login

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

    @AfterMethod
    public void cleanUpCart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if ("addMultipleProductsToCartAndContinueShopping".equals(methodName)) {
            try {
                if (driver != null) {
                    cartPage cartPage = new cartPage(driver);
                    cartPage.emptyCart();
                }
            } catch (Exception e) {
                //throw new RuntimeException(e);
            }
        }
    }

    @Test(priority = 1, groups = "cartTest", description = "Add Products in Cart", enabled = true)
    public void addMultipleProductsToCartAndContinueShopping() throws InterruptedException {
        homePage hp = new homePage(driver);
        //hp.navigateHomePage();
        //4. Click 'Products' button
        hp.navigateToProductPage();

        //5. Hover over first product and click 'Add to cart'
        WebElement firstProduct = driver.findElement(By.cssSelector(".single-products:first-child"));
        Actions actions = new Actions(driver);
        actions.moveToElement(firstProduct).perform();

        Thread.sleep(3000);
        //Click 'Continue Shopping' button
        //driver.findElement(By.cssSelector("div.product-overlay > div > a")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartBtn1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-overlay > div > a")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn1);

        WebElement continueShoppingBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-success.close-modal.btn-block[data-dismiss='modal']")
        ));
        continueShoppingBtn.click();

        //Hover over second product and click 'Add to cart'
        WebElement secondProduct = driver.findElements(By.cssSelector(".single-products")).get(1);
        actions = new Actions(driver);
        actions.moveToElement(secondProduct).perform();

        // Wait for the overlay button to be visible and clickable
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                secondProduct.findElement(By.cssSelector(".product-overlay .add-to-cart"))
        ));

        // Click the button
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);

        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        //9. Verify both products are added to Cart
        cartPage cartPage = new cartPage(driver);
        List<Map<String, String>> cartinfo = cartPage.getCartProductsDetails();
        assertEquals(cartinfo.size(), 2, "Two products not added to cart");

        // Assert details for first product
        assertEquals(cartinfo.get(0).get("name"), "Blue Top");
        assertEquals(cartinfo.get(0).get("price"), "Rs. 500");
        assertEquals(cartinfo.get(0).get("quantity"), "1");
        assertEquals(cartinfo.get(0).get("total"), "Rs. 500");

        // Assert details for second product
        assertEquals(cartinfo.get(1).get("name"), "Men Tshirt");
        assertEquals(cartinfo.get(1).get("price"), "Rs. 400");
        assertEquals(cartinfo.get(1).get("quantity"), "1");
        assertEquals(cartinfo.get(1).get("total"), "Rs. 400");

    }

    @Test(priority = 2, groups = "cartTest", description = "Verify Product quantity in Cart", enabled = true)
    public void verifyProductQuantityInCart() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        //4. Click 'Products' button
        hp.navigateToProductPage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/products", "User is not navigated to the product page.");
        productPage pp = new productPage(driver);
        pp.searchProduct("pure cotton");
        List<WebElement> productlist = pp.getAllProductElements();
        Thread.sleep(2000);
        productlist.get(1).findElement(By.xpath("//a[starts-with(@href, '/product_details/')]")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/product_details/28", "User is not navigated to the product details page.");

        //6. Increase quantity to 4
        WebElement quantityInput = driver.findElement(By.id("quantity"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = 4;", quantityInput);

        //7. Click 'Add to cart' button
        driver.findElement(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > button")).click();

        //8. Click 'View Cart' button
        WebElement viewCartLink = driver.findElement(By.cssSelector("a[href='/view_cart']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartLink);

        //9. Verify that product is displayed in cart page with exact quantity
        cartPage cartPage = new cartPage(driver);
        List<Map<String, String>> cartinfo = cartPage.getCartProductsDetails();
        // Assert details for second product
        assertEquals(cartinfo.get(0).get("name"), "Pure Cotton V-Neck T-Shirt");
        assertEquals(cartinfo.get(0).get("price"), "Rs. 1299");
        assertEquals(cartinfo.get(0).get("quantity"), "4");
        assertEquals(cartinfo.get(0).get("total"), "Rs. 5196");
    }



}
