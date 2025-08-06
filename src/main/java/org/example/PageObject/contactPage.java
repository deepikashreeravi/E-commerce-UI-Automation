package org.example.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class contactPage {
    public WebDriver driver;

    public contactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateContactPage(){
        driver.navigate().to("https://automationexercise.com/contact_us");
        driver.manage().window().maximize();
    }

    public void fillform(String name, String email, String subject, String message) throws IOException, InterruptedException, AWTException {
        driver.findElement(By.xpath("//input[@data-qa='name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='subject']")).sendKeys(subject);
        driver.findElement(By.xpath("//textarea[@data-qa='message']")).sendKeys(message);

        File file = File.createTempFile("sample", ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Hello, this is a test file.");
        }
        driver.findElement(By.xpath("//*[@id=\"contact-us-form\"]/div[5]/input")).sendKeys(file.getAbsolutePath());
        //Thread.sleep(60000);
        driver.findElement(By.xpath("//input[@data-qa='submit-button']")).click();
        boolean deleted = Files.deleteIfExists(file.toPath());

        try{
            driver.switchTo().alert().accept();}catch (Exception ex){
        }

    }



}
