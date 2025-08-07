package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class Listener extends BaseTest  implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result) {
        test= extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //Field driverField = testClass.getClass().getSuperclass().getField("driver");
        extentTest.get().fail(result.getThrowable());
        WebDriver driver=null;

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
         //   Field driverField = result.getTestClass().getRealClass().getSuperclass().getDeclaredField("driver");
//            driverField.setAccessible(true);
//            driver = (WebDriver) driverField.get(result.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String filepath =null;
        try {
            filepath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Screenshot, Attach to report
        extentTest.get().addScreenCaptureFromPath(filepath,result.getMethod().getMethodName());


    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Failed but within success percentage: " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        System.out.println("Test Failed with Timeout: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null, cannot take screenshot.");
        }
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("/home/deepika-12161/build/test/ss"+testCaseName+".png"));
        return "/home/deepika-12161/build/test/ss"+testCaseName+".png";
    }


}
