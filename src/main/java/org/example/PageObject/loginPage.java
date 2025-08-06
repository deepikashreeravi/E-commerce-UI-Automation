package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class loginPage {
    public WebDriver driver;

    public loginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateLoginPage(){
        driver.navigate().to("https://automationexercise.com/login");
        driver.manage().window().maximize();
    }

    public void signup(String email,String name){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)")).sendKeys(name);
        List<WebElement> emailInputs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("input[placeholder='Email Address']"), 1
        ));
        emailInputs.get(1).sendKeys(email);

        //Click 'Signup' button
        driver.findElement(
                By.xpath("(//*[text()='Signup'])")
        ).click();
    }

    public void login(String email, String password){
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
    }
}
