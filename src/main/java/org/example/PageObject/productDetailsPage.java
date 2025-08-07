package org.example.PageObject;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class productDetailsPage {
    public WebDriver driver;

    public productDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public JSONObject fetchProductDetails() {
        JSONObject productdetailsjson = new JSONObject();
        String productName = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > h2"), "Product Name");
        String category = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(3)"), "Category");
        String price = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > span"), "Price");
        String availability = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(6)"), "Availability");
        String condition = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(7)"), "Condition");
        String brand = getElementText(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(8)"), "Brand");
        if (productName!=null) {
            productdetailsjson.put("productName", productName);
        }
        if (category!=null) {
            productdetailsjson.put("category", category);
        }
        if (price!=null) {
            productdetailsjson.put("price", price);
        }
        if(availability!=null) {
            productdetailsjson.put("availability", availability);
        }
        if(condition!=null) {
            productdetailsjson.put("condition", condition);
        }
        if(brand!=null) {
            productdetailsjson.put("brand", brand);
        }

        return productdetailsjson;
    }

    private String getElementText(By locator, String fieldName) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getText().trim();
        } catch (NoSuchElementException e) {
            System.out.println(fieldName + " not found on the page.");
            return null;
        }
    }


}
