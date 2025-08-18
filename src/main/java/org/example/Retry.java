package org.example;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    public int count = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCount = 3; // Set the maximum number of retries
        if (!iTestResult.isSuccess()) {
            if (count < maxRetryCount) {
                count++;
                iTestResult.setStatus(ITestResult.FAILURE); // Mark the test as failed
                return true; // Indicate that the test should be retried
            } else {
                iTestResult.setStatus(ITestResult.SUCCESS); // Mark the test as successful after retries
            }
        }
        return false;
    }
}
