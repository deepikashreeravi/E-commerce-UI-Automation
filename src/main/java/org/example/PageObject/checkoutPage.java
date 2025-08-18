package org.example.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class checkoutPage {
    public WebDriver driver;

    @FindBy(css = "tr[id^='product-']")
    private List<WebElement> productRows;

    @FindBy(xpath = "//tr[td/h4/b[text()='Total Amount']]/td/p")
    private WebElement grandTotalElement;

    @FindBy(id = "address_delivery")
    private WebElement deliveryAddress;

    public checkoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateCheckOutPage() {
        driver.navigate().to("https://automationexercise.com/checkout");
        driver.manage().window().maximize();
    }

    public List<Map<String, String>> getReviewOrderInfo() {
        List<Map<String, String>> orderInfoList = new ArrayList<>();

        for (WebElement productRow : productRows) {
            Map<String, String> orderInfo = new HashMap<>();
            String productName = productRow.findElement(org.openqa.selenium.By.cssSelector(".cart_description h4 a")).getText();
            String quantity = productRow.findElement(org.openqa.selenium.By.cssSelector(".cart_quantity button")).getText();
            String price = productRow.findElement(org.openqa.selenium.By.cssSelector(".cart_price p")).getText();
            String total = productRow.findElement(org.openqa.selenium.By.cssSelector(".cart_total_price")).getText();

            orderInfo.put("productName", productName);
            orderInfo.put("quantity", quantity);
            orderInfo.put("price", price);
            orderInfo.put("total", total);

            orderInfoList.add(orderInfo);
        }

        // Grand total (bottom row)
        Map<String, String> grandTotalMap = new HashMap<>();
        grandTotalMap.put("grandTotal", grandTotalElement.getText());
        orderInfoList.add(grandTotalMap);

        return orderInfoList;
    }

    public Map<String, String> getDeliveryAddressInfo() {
        Map<String, String> addressInfo = new HashMap<>();
        List<WebElement> addressItems = deliveryAddress.findElements(org.openqa.selenium.By.tagName("li"));

        addressInfo.put("name", addressItems.get(1).getText());
        addressInfo.put("company", addressItems.get(2).getText());
        addressInfo.put("addressLine", addressItems.get(3).getText());
        addressInfo.put("cityOrDistrict", addressItems.get(4).getText());
        addressInfo.put("cityStatePostcode", addressItems.get(5).getText());
        addressInfo.put("country", addressItems.get(6).getText());
        addressInfo.put("phone", addressItems.get(7).getText());

        return addressInfo;
    }
}