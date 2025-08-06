package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class homePage {
    public WebDriver driver;

    public homePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateHomePage(){
        driver.navigate().to("https://automationexercise.com/");
        driver.manage().window().maximize();
    }

    public void navigateToLoginPageViaHomePage(){
        driver.findElement(By.cssSelector("a[href='/login']")).click();
    }


    public void navigateToContactPageViaHomePage(){
        driver.findElement(By.cssSelector("a[href='/contact_us']")).click();
    }


    public void navigateToTestCasePageViaHomePage(){
        driver.findElement(By.cssSelector("a[href='/test_cases']")).click();
    }

    public void logout(){
        driver.findElement(By.cssSelector("a[href='/logout']")).click();
    }

    public void accountDeletion(){
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();
    }





}
