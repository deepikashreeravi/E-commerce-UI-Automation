package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    public WebDriver driver;

    WebDriverWait wait;
    private static  String ipaddress=null;

    @AfterSuite
    public void close(){
        driver.quit();
    }

    @Parameters({"ip"})
    @BeforeSuite
    public  void setUp() {
        String methodName="setup";  //No I18N
        try {
            //System.setProperty("webdriver.chrome.driver",chromedriverPath );  //No I18N
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .driverVersion("135.0.7049.84")
                    .setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu", "--no‑sandbox", "--disable‑dev‑shm‑usage", "--remote‑allow‑origins=*");

            driver = new ChromeDriver(options);
//            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //Navigate to url 'http://automationexercise.com'
            ipaddress="http://automationexercise.com";  //No I18N
//            driver.manage().window().setSize(new Dimension(1440,900));
            driver.navigate().to(ipaddress);
            driver.manage().window().maximize();
            driver.navigate().refresh();
        }
        catch (Exception ex){
            ex.printStackTrace();
            // ScrapyAutomationLogger.logMessage(Level.SEVERE,CLASS_NAME,methodName,null,ex);
        }
    }


    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("/home/deepika-12161/build/test/ss"+testCaseName+".png"));
        return "/home/deepika-12161/build/test/ss"+testCaseName+".png";
    }
}
