package com.koch.automation.framework;


import com.koch.automation.framework.utils.CustomWebDriverManager;
import com.koch.automation.framework.utils.LogUtils;
import com.koch.automation.framework.utils.ReportUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite() {
        ReportUtils.initReport();
        LogUtils.logInfo("Test Suite setup");
    }

    @BeforeMethod
    public void setupTest() {
        driver = CustomWebDriverManager.getDriver();
        driver.manage().window().maximize();
        LogUtils.logInfo("Test setup complete");
    }

    @AfterMethod
    public void teardownTest(ITestResult result) {
        if (!result.isSuccess()) {
            takeScreenshot(result.getMethod().getMethodName());
            ReportUtils.logFail("Test failed: " + result.getMethod().getMethodName());
        }
        CustomWebDriverManager.quitDriver();
        LogUtils.logInfo("Test teardown complete");
    }

    @AfterSuite
    public void teardownSuite() {
        ReportUtils.flushReport();
        LogUtils.logInfo("Test Suite teardown complete");
    }

    private void takeScreenshot(String methodName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File destFile = new File("screenshots/" + methodName + "_" + timestamp + ".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            LogUtils.logError("Failed to take screenshot", e);
        }
    }
}
