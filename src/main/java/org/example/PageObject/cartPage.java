package org.example.PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class cartPage {
    public WebDriver driver;

    @FindBy(css = "#cart_info_table tbody tr")
    private List<WebElement> cartRows;

    @FindBy(css = ".cart_quantity_delete")
    private List<WebElement> deleteButtons;

    @FindBy(css = "a.btn")
    private WebElement checkoutButton;

    @FindBy(css = "a[href='/login']")
    private WebElement loginPageBtn;

    public cartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateCartPage() {
        driver.navigate().to("https://automationexercise.com/view_cart");
        driver.manage().window().maximize();
    }

    public List<Map<String, String>> getCartProductsDetails() {
        List<Map<String, String>> products = new ArrayList<>();
        for (WebElement row : cartRows) {
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
        for (WebElement deleteBtn : deleteButtons) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(deleteBtn));
        }
    }

    public void proceedToCheckout() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutButton);
    }

    public void clickRegisterLoginButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginPageBtn);
    }
}