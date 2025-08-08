package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class checkoutPage {
    public WebDriver driver;

    public checkoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateCheckOutPage(){
        driver.navigate().to("https://automationexercise.com/checkout");
        driver.manage().window().maximize();
    }

    public List<Map<String, String>> getReviewOrderInfo() {
        List<Map<String, String>> orderInfoList = new ArrayList<>();

        List<WebElement> productRows = driver.findElements(By.cssSelector("tr[id^='product-']"));
        for (WebElement productRow : productRows) {
            Map<String, String> orderInfo = new HashMap<>();
            String productName = productRow.findElement(By.cssSelector(".cart_description h4 a")).getText();
            String quantity = productRow.findElement(By.cssSelector(".cart_quantity button")).getText();
            String price = productRow.findElement(By.cssSelector(".cart_price p")).getText();
            String total = productRow.findElement(By.cssSelector(".cart_total_price")).getText();

            orderInfo.put("productName", productName);
            orderInfo.put("quantity", quantity);
            orderInfo.put("price", price);
            orderInfo.put("total", total);

            orderInfoList.add(orderInfo);
        }

        // Grand total (bottom row)
        String grandTotal = driver.findElement(By.xpath("//tr[td/h4/b[text()='Total Amount']]/td/p")).getText();
        Map<String, String> grandTotalMap = new HashMap<>();
        grandTotalMap.put("grandTotal", grandTotal);
        orderInfoList.add(grandTotalMap);

        return orderInfoList;
    }

    public Map<String, String> getDeliveryAddressInfo() {
        Map<String, String> addressInfo = new HashMap<>();
        WebElement deliveryAddress = driver.findElement(By.id("address_delivery"));
        List<WebElement> addressItems = deliveryAddress.findElements(By.tagName("li"));

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
