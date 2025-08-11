package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReporterNG {
    public static ExtentReports getReportObject(){
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test" + File.separator + "ExtentReport.html");
        reporter.config().setReportName("Automation Exercise Test Report");
        reporter.config().setDocumentTitle("Test Report");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Deepika Shree");
        extent.setSystemInfo("Environment", "Automation Exercise");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Selenium Version", "4.0.0");
        extent.setSystemInfo("Test Suite", "Register User Test Suite");
        extent.setSystemInfo("Test Priority", "High");
        extent.setSystemInfo("Test Group", "Registration");
        extent.setSystemInfo("Test Description", "Tests for user registration and account management on Automation Exercise website");
        extent.setSystemInfo("Test Status", "Completed");
        extent.setSystemInfo("Test Execution Time", java.time.LocalDateTime.now().toString());
        extent.setSystemInfo("Test Duration", "5 minutes");
        return extent;
    }
}
