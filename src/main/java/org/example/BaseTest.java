package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class BaseTest {
    public WebDriver driver;

    WebDriverWait wait;
    private static  String ipaddress=null;


    public void close(){
        driver.quit();
    }

    public  void setUp() {
        String methodName="setup";  //No I18N
        try {

            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/GlobalData.properties");  //No I18N
            prop.load(fis);

           String browserName =  System.getProperty("browser")!=null ?  System.getProperty("browser"):prop.getProperty("browser");
            System.out.println("Browser Name: " + browserName);  //No I18N

            String browsertype =  System.getProperty("browsertype")!=null ?  System.getProperty("browsertype"):prop.getProperty("browsertype");
            System.out.println("Browser Type: " + browserName);  //No I18N

            WebDriverManager.firefoxdriver().setup();

            //System.setProperty("webdriver.chrome.driver",chromedriverPath );  //No I18NP
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .driverVersion("135.0.7049.84")
                    .setup();

            // 2. Create a unique temp directory for this run
            String uniqueProfileDir = Files.createTempDirectory("selenium-profile-" + UUID.randomUUID()).toString();

            // 3. Configure ChromeOptions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
            options.addArguments("--incognito");
            options.addArguments("--user-data-dir=" + uniqueProfileDir);


              options.addArguments("--disable-gpu", "--no‑sandbox", "--disable‑dev‑shm‑usage", "--remote‑allow‑origins=*");
            if (browsertype.equalsIgnoreCase("headless")) {  //No I18N
                options.addArguments("--headless=new");
            } else {
                options.addArguments("--start-maximized");
            }

            if (browserName.equalsIgnoreCase("chrome")) {  //No I18N
                driver = new ChromeDriver(options);
            } else {
            FirefoxOptions options1 = new FirefoxOptions();
            options.addArguments("-headless");   // Run in headless mode
            driver = new FirefoxDriver(options1);
                driver = new FirefoxDriver();
            }


//            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //Navigate to url 'http://automationexercise.com'
            ipaddress="http://automationexercise.com";  //No I18N
//            driver.manage().window().setSize(new Dimension(1440,900));
            driver.navigate().to(ipaddress);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            //driver.navigate().refresh();
        }
        catch (Exception ex){
            ex.printStackTrace();
            // ScrapyAutomationLogger.logMessage(Level.SEVERE,CLASS_NAME,methodName,null,ex);
        }
    }

    public void scrollToFooter() {
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footer);
    }

}
