package com.koch.automation.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportUtils {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-report.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Test Report");
        sparkReporter.config().setReportName("Automated Test Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logInfo(String message) {
        test.info(message);
    }

    public static void logFail(String message) {
        test.fail(message);
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
