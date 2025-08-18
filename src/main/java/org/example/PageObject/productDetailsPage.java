package org.example.PageObject;

import org.json.JSONObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class productDetailsPage {
    public WebDriver driver;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > h2")
    private WebElement productNameElement;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(3)")
    private WebElement categoryElement;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > span")
    private WebElement priceElement;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(6)")
    private WebElement availabilityElement;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(7)")
    private WebElement conditionElement;

    @FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > p:nth-child(8)")
    private WebElement brandElement;

    public productDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public JSONObject fetchProductDetails() {
        JSONObject productdetailsjson = new JSONObject();
        String productName = getElementText(productNameElement, "Product Name");
        String category = getElementText(categoryElement, "Category");
        String price = getElementText(priceElement, "Price");
        String availability = getElementText(availabilityElement, "Availability");
        String condition = getElementText(conditionElement, "Condition");
        String brand = getElementText(brandElement, "Brand");

        if (productName != null) productdetailsjson.put("productName", productName);
        if (category != null) productdetailsjson.put("category", category);
        if (price != null) productdetailsjson.put("price", price);
        if (availability != null) productdetailsjson.put("availability", availability);
        if (condition != null) productdetailsjson.put("condition", condition);
        if (brand != null) productdetailsjson.put("brand", brand);

        return productdetailsjson;
    }

    private String getElementText(WebElement element, String fieldName) {
        try {
            return element.getText().trim();
        } catch (NoSuchElementException | NullPointerException e) {
            System.out.println(fieldName + " not found on the page.");
            return null;
        }
    }
}