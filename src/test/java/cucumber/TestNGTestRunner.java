package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/cucumber/registeruser.feature",glue="stepDefinition",monochrome = true,plugin = {"html:target/cucumber-reports/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

    // This class is used to run Cucumber tests with TestNG
    // The @CucumberOptions annotation specifies the feature file and step definitions
    // The plugin option generates a report in HTML format
    // The monochrome option makes the console output more readable
}
