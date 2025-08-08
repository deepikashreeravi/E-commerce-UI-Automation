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

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

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
        hp.navigateHomePage();
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

        //11. Verify ' Logged in as username' at top
        String actual=hp.getUserName();
        assertEquals(actual, "Logged in as deepika-12161", "Not logged in as user deepika");

        //12.Click 'Cart' button
        hp.navigateToCartPageViaHomePage();

        //13. Click 'Proceed To Checkout' button
        cartPage.proceedToCheckout();


        checkoutPage checkoutPage = new checkoutPage(driver);
        List<Map<String, String>> orderInfoList = checkoutPage.getReviewOrderInfo();

        //14. Verify Address Details and Review Your Order

        Map<String, String> product = orderInfoList.get(0);
        assertEquals("Sleeveless Dress", product.get("productName"));
        assertEquals("1", product.get("quantity"));
        assertEquals("Rs. 1000", product.get("price"));
        assertEquals("Rs. 1000", product.get("total"));

        Map<String, String> grandTotalMap = orderInfoList.get(orderInfoList.size() - 1);
        assertEquals("Rs. 1000", grandTotalMap.get("grandTotal"));

        Map<String, String> addressInfo = checkoutPage.getDeliveryAddressInfo();

        assertEquals("Mr. deepika shree ravi", addressInfo.get("name"));
        assertEquals("zoho", addressInfo.get("company"));
        assertEquals("guduvancheri", addressInfo.get("addressLine"));
        assertEquals("tenkasi", addressInfo.get("cityOrDistrict"));
        assertEquals("chennai tamilnadu 603202", addressInfo.get("cityStatePostcode"));
        assertEquals("India", addressInfo.get("country"));
        assertEquals("8987898765", addressInfo.get("phone"));

        //Thread.sleep(5000);

        //15. Enter description in comment text area and click 'Place Order'
        driver.findElement(By.cssSelector("#ordermsg > textarea")).sendKeys("Please deliver it on time");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        //Thread.sleep(5000);
        paymentPage paymentPage = new paymentPage(driver);
        //16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.fillcardDetails("deepika shree", "4111111111111111", "12", "2025", "123");

        //17. Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();


//        //18. Verify success message 'Your order has been placed successfully!'
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));
//            WebElement toast = wait.until(
//                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-success.alert"))
//            );
//            String toastMsg = toast.getText().trim();
//            assertEquals(toastMsg, "Your order has been placed successfully!");
//        } catch (Exception e) {
//            System.out.println("Success message not found: " + e.getMessage());
//            //throw new RuntimeException(e);
//        }
        actual = driver.findElement(By.cssSelector("#form > div > div > div > p")).getText();
        assertEquals(actual, "Congratulations! Your order has been confirmed!", "Order not placed successfully");

        hp.navigateHomePage();
        //19. Click 'Delete Account' button
        hp.accountDeletion();

        //20. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        actual = driver.findElement(By.className("title")).getText();
        assertEquals(actual, "ACCOUNT DELETED!", "account not deleted");

        //a.btn

    }

    @Test(priority = 2, groups = "orderplacement", description = "Place Order: Register before Checkout", enabled = true)
    public void RegisterBeforeCheckout() throws InterruptedException {
        homePage hp = new homePage(driver);
        hp.navigateHomePage();
        hp.navigateToLoginPageViaHomePage();
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
        String actual=hp.getUserName();
        assertEquals(actual, "Logged in as deepika-12161", "Not logged in as user deepika");


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
        checkoutPage checkoutPage = new checkoutPage(driver);
        List<Map<String, String>> orderInfoList = checkoutPage.getReviewOrderInfo();

        //14. Verify Address Details and Review Your Order

        Map<String, String> product = orderInfoList.get(0);
        assertEquals("Sleeveless Dress", product.get("productName"));
        assertEquals("1", product.get("quantity"));
        assertEquals("Rs. 1000", product.get("price"));
        assertEquals("Rs. 1000", product.get("total"));

        Map<String, String> grandTotalMap = orderInfoList.get(orderInfoList.size() - 1);
        assertEquals("Rs. 1000", grandTotalMap.get("grandTotal"));

        Map<String, String> addressInfo = checkoutPage.getDeliveryAddressInfo();

        assertEquals("Mr. deepika shree ravi", addressInfo.get("name"));
        assertEquals("zoho", addressInfo.get("company"));
        assertEquals("guduvancheri", addressInfo.get("addressLine"));
        assertEquals("tenkasi", addressInfo.get("cityOrDistrict"));
        assertEquals("chennai tamilnadu 603202", addressInfo.get("cityStatePostcode"));
        assertEquals("India", addressInfo.get("country"));
        assertEquals("8987898765", addressInfo.get("phone"));

        //Thread.sleep(5000);

        //15. Enter description in comment text area and click 'Place Order'
        driver.findElement(By.cssSelector("#ordermsg > textarea")).sendKeys("Please deliver it on time");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
        //Thread.sleep(5000);
        paymentPage paymentPage = new paymentPage(driver);
        //16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.fillcardDetails("deepika shree", "4111111111111111", "12", "2025", "123");

        //17. Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();


//        //18. Verify success message 'Your order has been placed successfully!'
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));
//            WebElement toast = wait.until(
//                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-success.alert"))
//            );
//            String toastMsg = toast.getText().trim();
//            assertEquals(toastMsg, "Your order has been placed successfully!");
//        } catch (Exception e) {
//            System.out.println("Success message not found: " + e.getMessage());
//            //throw new RuntimeException(e);
//        }
        actual = driver.findElement(By.cssSelector("#form > div > div > div > p")).getText();
        assertEquals(actual, "Congratulations! Your order has been confirmed!", "Order not placed successfully");

        hp.navigateHomePage();
        //19. Click 'Delete Account' button
        hp.accountDeletion();

        //20. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        actual = driver.findElement(By.className("title")).getText();
        assertEquals(actual, "ACCOUNT DELETED!", "account not deleted");

    }


}
