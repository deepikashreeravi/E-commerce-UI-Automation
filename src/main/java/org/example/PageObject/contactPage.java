package org.example.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class contactPage {
    public WebDriver driver;

    @FindBy(xpath = "//input[@data-qa='name']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@data-qa='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@data-qa='subject']")
    private WebElement subjectInput;

    @FindBy(xpath = "//textarea[@data-qa='message']")
    private WebElement messageInput;

    @FindBy(xpath = "//*[@id='contact-us-form']/div[5]/input")
    private WebElement uploadInput;

    @FindBy(xpath = "//input[@data-qa='submit-button']")
    private WebElement submitButton;

    public contactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateContactPage() {
        driver.navigate().to("https://automationexercise.com/contact_us");
        driver.manage().window().maximize();
    }

    public void fillform(String name, String email, String subject, String message) throws IOException, InterruptedException, AWTException {
        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        subjectInput.sendKeys(subject);
        messageInput.sendKeys(message);

        File file = File.createTempFile("sample", ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Hello, this is a test file.");
        }
        uploadInput.sendKeys(file.getAbsolutePath());
        submitButton.click();
        Files.deleteIfExists(file.toPath());

        try {
            driver.switchTo().alert().accept();
        } catch (Exception ex) {
            // Ignore if no alert
        }
    }
}