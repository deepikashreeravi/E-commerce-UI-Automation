package org.example.TestCases.TestCases;

import org.example.BaseTest;
import org.example.PageObject.*;
import org.example.Retry;
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

    @Test(priority = 1, groups = "cartTest", description = "Add Products in Cart", enabled = true, retryAnalyzer = Retry.class)
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueShoppingBtn);

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

    @Test(priority = 2, groups = "cartTest", description = "Verify Product quantity in Cart", enabled = true, retryAnalyzer = Retry.class)
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
        WebElement we=productlist.get(1).findElement(By.xpath("//a[starts-with(@href, '/product_details/')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",we );
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

    @Test(priority = 3, groups = "cartTest", description = "Remove Products From Cart", enabled = true)
    public void removeProductsFromCart() throws InterruptedException {
        cartPage cartPage = new cartPage(driver);
        cartPage.emptyCart();

        // Verify that cart is empty
        List<Map<String, String>> cartInfoAfterRemoval = cartPage.getCartProductsDetails();
        assertEquals(cartInfoAfterRemoval.size(), 0, "Cart is not empty after removal of product");
        String emptyCartMessage = driver.findElement(By.cssSelector("#empty_cart")).getText();
        assertEquals(emptyCartMessage, "Cart is empty! Click here to buy products.", "Empty cart message not displayed as expected");
    }

    @Test(priority = 4, groups = "cartTest", description = "Search Products and Verify Cart After Login", enabled = true, retryAnalyzer = Retry.class)
    public void searchProductsAndVerifyCartAfterLogin() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToProductPage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/products", "User is not navigated to the product page.");
        productPage pp = new productPage(driver);
        pp.searchProduct("pure cotton");
        List<WebElement> productlist = pp.getAllProductElements();
        assertEquals(productlist.size()-1, 2, "filter not applied");
        int retry = 0;
        while(retry<3) {
            try {
                pp.addProductToCart("Pure Cotton V-Neck T-Shirt");
                break;
            } catch (Exception ex) {
                retry++;
                if(retry==3)
                    throw ex;
            }
        }
        Thread.sleep(3000);
        pp.clickContinueShoppingInModal();
        retry = 0;
        while(retry<3) {
            try {
                pp.addProductToCart("Pure Cotton Neon Green Tshirt");
                break;
            } catch (Exception ex) {
                retry++;
                if(retry==3)
                    throw ex;
            }
        }
        Thread.sleep(3000);
        pp.clickViewCartInModal();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        cartPage cartPage = new cartPage(driver);
        List<Map<String, String>> cartinfo = cartPage.getCartProductsDetails();
        // Assert details for second product
        assertEquals(cartinfo.get(0).get("name"), "Pure Cotton V-Neck T-Shirt");
        assertEquals(cartinfo.get(0).get("price"), "Rs. 1299");
        assertEquals(cartinfo.get(0).get("quantity"), "1");
        assertEquals(cartinfo.get(0).get("total"), "Rs. 1299");

        assertEquals(cartinfo.get(1).get("name"), "Pure Cotton Neon Green Tshirt");
        assertEquals(cartinfo.get(1).get("price"), "Rs. 850");
        assertEquals(cartinfo.get(1).get("quantity"), "1");
        assertEquals(cartinfo.get(1).get("total"), "Rs. 850");

        hp.navigateHomePage();
        hp.navigateToLoginPageViaHomePage();
        loginOrSignUp(driver,hp);
        hp.navigateToCartPageViaHomePage();

        cartPage = new cartPage(driver);
        cartinfo = cartPage.getCartProductsDetails();
        // Assert details for second product
        assertEquals(cartinfo.get(0).get("name"), "Pure Cotton V-Neck T-Shirt");
        assertEquals(cartinfo.get(0).get("price"), "Rs. 1299");
        assertEquals(cartinfo.get(0).get("quantity"), "1");
        assertEquals(cartinfo.get(0).get("total"), "Rs. 1299");

        assertEquals(cartinfo.get(1).get("name"), "Pure Cotton Neon Green Tshirt");
        assertEquals(cartinfo.get(1).get("price"), "Rs. 850");
        assertEquals(cartinfo.get(1).get("quantity"), "1");
        assertEquals(cartinfo.get(1).get("total"), "Rs. 850");
    }

    @Test(priority = 5, groups = "cartTest", description = "Add to cart from Recommended items", enabled = true, retryAnalyzer = Retry.class)
    public void addToCartFromRecommendedItems() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        cartPage cartPage = new cartPage(driver);
        hp.navigateToCartPageViaHomePage();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        cartPage.emptyCart();
        hp.navigateHomePage();

        String actual = driver.findElement(By.cssSelector("body > section:nth-child(3) > div > div > div.col-sm-9.padding-right > div.recommended_items > h2")).getText();
        assertEquals(actual, "RECOMMENDED ITEMS", "RECOMMENDED ITEMS text not found in home page");
        baseTest.scrollToFooter();
        WebElement recommendedCarousel = driver.findElements(By.cssSelector(".carousel-inner")).get(1);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement activeItem = wait.until(d -> recommendedCarousel.findElement(By.cssSelector(".item.active")));

        String itemText = activeItem.findElements(By.cssSelector(".single-products")).get(0).findElement(By.tagName("p")).getText();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement singleProduct = activeItem.findElements(By.cssSelector(".single-products")).get(0);
        WebElement eb = wait.until(ExpectedConditions.visibilityOf(singleProduct.findElement(By.tagName("a"))));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", eb);
        Thread.sleep(5000);
        eb =driver.findElement(By.cssSelector("#cartModal > div > div > div.modal-body > p:nth-child(2) > a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", eb);


        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        List<Map<String, String>> cartinfo = cartPage.getCartProductsDetails();
        assertEquals(cartinfo.get(0).get("name"), itemText, "Product name mismatch in cart");

    }


    public static void loginOrSignUp(WebDriver driver,homePage hp){
        // Fill all details in Signup and create account
        loginPage lp = new loginPage(driver);
        try {
            lp.signup("deepikashree.r@zohocorp.com", "deepika-12161");
            signupPage sp = new signupPage(driver);
            sp.fillAccountInfo(true, null, null, "godi", "16", "December", "1999", true, true);
            sp.fillAddressInfo("deepika shree", "ravi", "zoho", "guduvancheri", "tenkasi", "tamilnadu", "chennai", "603202", "8987898765");
            driver.findElement(By.cssSelector("a.btn")).click();
        }catch (Exception ex){

            lp.login("deepikashree.r@zohocorp.com","godi");
        }

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));

        // Verify ' Logged in as username' at top
        String actual=hp.getUserName();
        assertEquals(actual, "Logged in as deepika-12161", "Not logged in as user deepika");
    }


}
