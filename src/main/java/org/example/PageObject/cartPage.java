package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cartPage {
    public WebDriver driver;

    public cartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateCartPage(){
        driver.navigate().to("https://automationexercise.com/view_cart");
        driver.manage().window().maximize();
    }

    public List<Map<String, String>> getCartProductsDetails() {
        List<Map<String, String>> products = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.cssSelector("#cart_info_table tbody tr"));
        for (WebElement row : rows) {
            Map<String, String> product = new HashMap<>();
            product.put("name", row.findElement(By.cssSelector(".cart_description h4 a")).getText());
            product.put("price", row.findElement(By.cssSelector(".cart_price p")).getText());
            product.put("quantity", row.findElement(By.cssSelector(".cart_quantity button")).getText());
            product.put("total", row.findElement(By.cssSelector(".cart_total_price")).getText());
            products.add(product);
        }
        return products;
    }

    public void emptyCart() {
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector(".cart_quantity_delete"));
        for (WebElement deleteBtn : deleteButtons) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(deleteBtn));
        }
//        // Optionally verify cart is empty
//        boolean isCartEmpty = driver.findElements(By.cssSelector("#cart_info_table tbody tr")).isEmpty();
//        assertEquals(isCartEmpty, true, "Cart is not empty after deletion.");
    }

    public void proceedToCheckout() {
        WebElement checkoutButton = driver.findElement(By.cssSelector("a.btn"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",checkoutButton);
    }

    public void clickRegisterLoginButton() {
        WebElement loginpagebtn = driver.findElement(By.cssSelector("a[href='/login']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginpagebtn);
    }
}
