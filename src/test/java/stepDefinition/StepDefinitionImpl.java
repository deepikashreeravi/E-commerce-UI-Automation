package stepDefinition;

import io.cucumber.java.en.*;
import org.example.BaseTest;
import org.example.PageObject.homePage;
import org.example.PageObject.loginPage;
import org.example.PageObject.signupPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class StepDefinitionImpl {
    public WebDriver driver = null;


    @Given("^the user is on the home page$")
    public void user_is_on_the_home_page() {
        System.out.println("Setting up the driver and navigating to the home page...");
        BaseTest baseTest = new BaseTest();
        baseTest.setUp();
        this.driver = baseTest.driver;
    }

    @When("^the user clicks on the \"Signup / Login\" button$")
    public void user_clicks_signup_login() {
        homePage page = new homePage(driver);
        page.navigateToLoginPageViaHomePage();
    }

    @And("^the user enters a new username \"([^\"]*)\" and email address \"([^\"]*)\"$")
    public void user_enters_username_and_email(String username, String email) {
        System.out.println("Entering username: " + username + " and email: " + email);
        loginPage lp = new loginPage(driver);
        lp.signup(email, username);
    }

    @And("^the user fills in the registration details$")
    public void user_fills_registration_details() {
        System.out.println("Filling in registration details...");
        // Fill in registration form fields as required
        signupPage sp = new signupPage(driver);
        sp.fillAccountInfo(true, null, null, "godi", "16", "December", "1999", true, true);
        sp.fillAddressInfo("deepika shree", "ravi", "zoho", "guduvancheri", "tenkasi", "tamilnadu", "chennai", "603202", "8987898765");

        // Add more fields as needed
    }

    @Then("^the user should see a message \"([^\"]*)\"$")
    public void user_should_see_message(String expectedMessage) throws InterruptedException {
        Thread.sleep(5000);
        String text = driver.findElement(By.className("title")).getText();
        assertEquals(text, expectedMessage, "Account not created");
        driver.close();
    }

    @Then("the user should see an error message {string} or {string}")
    public void the_user_should_see_an_error_message_or(String msg1, String msg2) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean found = false;
        try {
            WebElement error1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'" + msg1 + "')]")));
            found = error1.isDisplayed();
        } catch (TimeoutException ignored) {}
        if (!found) {
            try {
                WebElement error2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'" + msg2 + "')]")));
                found = error2.isDisplayed();
            } catch (TimeoutException ignored) {}
        }
        assert found : "Expected error message not found";

    }
}