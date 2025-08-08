package org.example.PageObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class productPage {
    public WebDriver driver;

    public productPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateProductPage(){
        driver.navigate().to("https://automationexercise.com/products");
        driver.manage().window().maximize();
    }

    public List<WebElement> getAllProductElements() {
        List<WebElement> productList = driver.findElements(By.cssSelector(".col-sm-4"));
        return productList;
    }

    public void addProductToCart(String productName) {
        List<WebElement> productList = getAllProductElements();
        int i=0;
        for (WebElement product : productList) {
            if(i==0){
                i++;
                continue;
            }
            WebElement nameElement = product.findElement(By.cssSelector(".productinfo p"));
            String nameText = nameElement.getText();
            if (nameText.equalsIgnoreCase(productName)) {
                Actions actions = new Actions(driver);
                actions.moveToElement(product).perform();
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(d -> ((JavascriptExecutor) d)
                                .executeScript("return document.readyState").equals("complete"));
                WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(product.findElement(By.cssSelector("div.product-overlay > div > a"))));
                clickable.click();
            }

        }
    }

    public void clickContinueShoppingInModal() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement continueShoppingButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-overlay > div > a")));
        continueShoppingButton.click();
    }

    public void clickViewCartInModal() {
        WebElement viewCartLink = driver.findElement(By.cssSelector("a[href='/view_cart']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartLink);
    }

    public JSONArray fetchListingPageProductDetails(){
        List<WebElement> productList = driver.findElements(By.cssSelector(".col-sm-4"));
        JSONArray productlarr = new JSONArray();
        for (WebElement product : productList) {
            JSONObject productjson = new JSONObject();

            // ---------- Image ----------
            WebElement image = product.findElement(By.cssSelector(".productinfo img"));
            String imgSrc = image.getAttribute("src");
            productjson.put("image",image);
            //Assert.assertTrue(imgSrc != null && !imgSrc.trim().isEmpty(), "Product image is missing.");

            // ---------- Price ----------
            WebElement price = product.findElement(By.cssSelector(".productinfo h2"));
            String priceText = price.getText();
            productjson.put("price",priceText);
            //Assert.assertTrue(priceText.matches("Rs\\.\\s?\\d+"), "Price format is invalid: " + priceText);

            // ---------- Product Name ----------
            WebElement name = product.findElement(By.cssSelector(".productinfo p"));
            String nameText = name.getText();
            productjson.put("name",nameText);
            //Assert.assertFalse(nameText.isEmpty(), "Product name is missing.");

            // ---------- Add to Cart Button ----------
            WebElement addToCartBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));
            productjson.put("addToCartBtn",addToCartBtn.isDisplayed());
            //Assert.assertTrue(addToCartBtn.isDisplayed(), "'Add to Cart' button is not visible.");
            //Assert.assertTrue(addToCartBtn.getAttribute("data-product-id") != null, "'data-product-id' attribute missing.");

//            // ---------- View Product Link ----------
//            WebElement viewProductLink = product.findElement(By.cssSelector(".choose a"));
//            String href = viewProductLink.getAttribute("href");
//            //Assert.assertTrue(href.contains("/product_details/"), "'View Product' link is invalid: " + href);
//            //Assert.assertEquals(viewProductLink.getText().trim(), "View Product", "'View Product' link text mismatch.");
//
//            // ---------- Optional: Overlay Matches ----------
//            WebElement overlay = product.findElement(By.cssSelector(".product-overlay"));
//            WebElement overlayPrice = overlay.findElement(By.tagName("h2"));
//            WebElement overlayName = overlay.findElement(By.tagName("p"));
//            //Assert.assertEquals(overlayPrice.getText(), priceText, "Overlay price doesn't match main price.");
//            //Assert.assertEquals(overlayName.getText(), nameText, "Overlay name doesn't match main name.");
            productlarr.put(productjson);
        }
        return productlarr;
    }

    public void searchProduct(String productname){
        driver.findElement(By.id("search_product")).sendKeys(productname);
        driver.findElement(By.id("submit_search")).click();

    }



}
