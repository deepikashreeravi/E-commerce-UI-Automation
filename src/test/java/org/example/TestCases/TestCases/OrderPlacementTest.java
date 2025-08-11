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

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrderPlacementTest {
//    3. Checkout and Order Placement Tests
//    Test Case ID	Description
//    TC14	Place Order: Register while Checkout
//    TC15	Place Order: Register before Checkout
//    TC16	Place Order: Login before Checkout
//    TC23	Verify address details in checkout page
//    TC24	Download Invoice after purchase order

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

    @Test(priority = 1, groups = "orderplacement", description = "Place Order: Register while Checkout", enabled = true)
    public void placeOrderRegisterWhileCheckout() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateToProductPage();
        productPage pp = new productPage(driver);
        //4. Add products to cart
        int retry=0;
        baseTest.scrollToFooter();
        while(retry<3) {
            try {
                pp.addProductToCart("Sleeveless Dress");
                break;
            } catch (Exception ex) {
                retry++;
                if(retry==3)
                    throw ex;
            }
        }
        Thread.sleep(3000);
        //5. Click 'Cart' button
        pp.clickViewCartInModal();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        //pp.proceedToCheckout();
        //6. Click 'Proceed To Checkout' button
        cartPage cartPage = new cartPage(driver);
        cartPage.proceedToCheckout();
        //8. Click 'Register / Login' button
        cartPage.clickRegisterLoginButton();
        //9. Fill all details in Signup and create account
        loginOrSignUp(driver, hp);

        //12.Click 'Cart' button
        hp.navigateToCartPageViaHomePage();

        checkout(driver);

        String actual = driver.findElement(By.cssSelector("#form > div > div > div > p")).getText();
        assertEquals(actual, "Congratulations! Your order has been confirmed!", "Order not placed successfully");

        accountDeletion(driver, hp);

    }

    @Test(priority = 2, groups = "orderplacement", description = "Place Order: Register before Checkout", enabled = true)
    public void RegisterBeforeCheckout() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToLoginPageViaHomePage();
        loginOrSignUp(driver, hp);
        hp.navigateToProductPage();
        productPage pp = new productPage(driver);
        //4. Add products to cart
        int retry=0;
        baseTest.scrollToFooter();
        while(retry<3) {
            try {
                pp.addProductToCart("Sleeveless Dress");
                break;
            } catch (Exception ex) {
                retry++;
                if(retry==3)
                    throw ex;
            }
        }
        Thread.sleep(3000);
        //5. Click 'Cart' button
        pp.clickViewCartInModal();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        //pp.proceedToCheckout();
        checkout(driver);

        String actual = driver.findElement(By.cssSelector("#form > div > div > div > p")).getText();
        assertEquals(actual, "Congratulations! Your order has been confirmed!", "Order not placed successfully");

        accountDeletion( driver, hp);
    }

    public static void checkout(WebDriver driver) {
        cartPage cartPage = new cartPage(driver);
        cartPage.proceedToCheckout();
        checkoutPage checkoutPage = new checkoutPage(driver);
        List<Map<String, String>> orderInfoList = checkoutPage.getReviewOrderInfo();

        //Verify Address Details and Review Your Order
        Map<String, String> product = orderInfoList.get(0);
        assertEquals(product.get("productName"),"Sleeveless Dress", "product name mismatch");
        assertEquals(product.get("quantity"),"1", "quantity mismatch");
        assertEquals( product.get("price"),"Rs. 1000", "price mismatch");
        assertEquals(product.get("total"),"Rs. 1000", "total mismatch");

        Map<String, String> grandTotalMap = orderInfoList.get(orderInfoList.size() - 1);
        assertEquals("Rs. 1000", grandTotalMap.get("grandTotal"));

        Map<String, String> addressInfo = checkoutPage.getDeliveryAddressInfo();

        assertEquals( addressInfo.get("name"),"Mr. deepika shree ravi", "name mismatch");
        assertEquals( addressInfo.get("company"),"zoho", "company mismatch");
        assertEquals( addressInfo.get("addressLine"),"guduvancheri", "address mismatch");
        assertEquals( addressInfo.get("cityOrDistrict"),"tenkasi", "city mismatch");
        assertEquals( addressInfo.get("cityStatePostcode"),"chennai tamilnadu 603202", "city state postcode mismatch");
        assertEquals( addressInfo.get("country"),"India", "country mismatch");
        assertEquals( addressInfo.get("phone"),"8987898765", "phone mismatch");


        // Enter description in comment text area and click 'Place Order'
        driver.findElement(By.cssSelector("#ordermsg > textarea")).sendKeys("Please deliver it on time");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        paymentPage paymentPage = new paymentPage(driver);
        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.fillcardDetails("deepika shree", "4111111111111111", "12", "2025", "123");

        // Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();
    }

    public static void accountDeletion(WebDriver driver, homePage hp) {
        hp.navigateHomePage();
        // Click 'Delete Account' button
        hp.accountDeletion();

        // Verify 'ACCOUNT DELETED!' and click 'Continue' button
        String actual = driver.findElement(By.className("title")).getText();
        assertEquals(actual, "ACCOUNT DELETED!", "account not deleted");
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
    @Test(priority = 3, groups = "orderplacement", description = "Download Invoice after purchase order", enabled = true)
    public void downloadInvoiceAfterPurchaseOrder() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateToProductPage();
        productPage pp = new productPage(driver);
        //4. Add products to cart
        int retry=0;
        baseTest.scrollToFooter();
        while(retry<3) {
            try {
                pp.addProductToCart("Sleeveless Dress");
                break;
            } catch (Exception ex) {
                retry++;
                if(retry==3)
                    throw ex;
            }
        }
        Thread.sleep(3000);
        //5. Click 'Cart' button
        pp.clickViewCartInModal();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        //pp.proceedToCheckout();
        //6. Click 'Proceed To Checkout' button
        cartPage cartPage = new cartPage(driver);
        cartPage.proceedToCheckout();
        //8. Click 'Register / Login' button
        cartPage.clickRegisterLoginButton();
        //9. Fill all details in Signup and create account
        loginOrSignUp(driver, hp);

        //12.Click 'Cart' button
        hp.navigateToCartPageViaHomePage();

        checkout(driver);

        String actual = driver.findElement(By.cssSelector("#form > div > div > div > p")).getText();
        assertEquals(actual, "Congratulations! Your order has been confirmed!", "Order not placed successfully");

        WebElement wb =driver.findElement(By.cssSelector("#form > div > div > div > a"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",wb );

        File file = new File("/home/deepika-12161/Downloads" + File.separator + "invoice.txt");
        int elapsed = 0;
        int timeout = 30; // seconds

        while (elapsed < timeout) {
            if (file.exists()) {
                break;
            }
            Thread.sleep(1000);
            elapsed++;
        }

        assertTrue(file.exists(), "Invoice was not downloaded successfully");
        accountDeletion(driver, hp);
    }




}
