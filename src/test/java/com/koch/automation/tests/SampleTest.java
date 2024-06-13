package com.koch.automation.tests;

import com.koch.automation.framework.BaseTest;
import com.koch.automation.framework.pages.SamplePage;
import com.koch.automation.framework.utils.CsvUtils;
import com.koch.automation.framework.utils.LogUtils;
import com.koch.automation.framework.utils.ReportUtils;
import com.opencsv.exceptions.CsvException;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SampleTest extends BaseTest {

    @DataProvider(name = "suiteData")
    public Object[][] getSuiteData() throws IOException, CsvException {
        List<String[]> csvData = CsvUtils.readCsv("src/test/resources/test_suites.csv");
        Map<String, String> suites = csvData.stream()
                .skip(1) // Skip header row
                .filter(row -> row[2].equalsIgnoreCase("true")) // Filter based on Runmode
                .collect(Collectors.toMap(
                        row -> row[0], // TestSuiteId
                        row -> row[4]  // TestCasesFileName
                ));
        return new Object[][]{{suites}};
    }

    @DataProvider(name = "testData")
    public Object[][] getTestData() throws IOException, CsvException {
        List<String[]> csvData = CsvUtils.readCsv("src/test/resources/test_suites.csv");
        List<String> testCasesFiles = csvData.stream()
                .skip(1) // Skip header row
                .filter(row -> row[2].equalsIgnoreCase("true")) // Filter based on Runmode
                .map(row -> "src/test/resources/" + row[4])
                .collect(Collectors.toList());

        return testCasesFiles.stream()
                .flatMap(file -> {
                    try {
                        return CsvUtils.readCsv(file).stream();
                    } catch (IOException | CsvException e) {
                        LogUtils.logError("Failed to read test cases file: " + file, e);
                        return null;
                    }
                })
                .filter(row -> row[2].equalsIgnoreCase("true")) // Filter based on Runmode
                .map(row -> new Object[]{row[1]})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "testData", dependsOnMethods = "filterSuites")
    public void sampleTest(String query) {
        ReportUtils.createTest("Google Search Test with query: " + query);
        LogUtils.logInfo("Executing test with query: " + query);
        driver.get("https://www.google.com");
        SamplePage samplePage = new SamplePage(driver);
        samplePage.search(query);
        ReportUtils.logInfo("Test executed successfully with query: " + query);
    }

    @Test(dataProvider = "suiteData")
    public void filterSuites(Map<String, String> suites) {
        boolean runThisTest = suites.containsKey("1"); // Check for the suite ID you want to run
        if (!runThisTest) {
            throw new SkipException("Skipping GoogleSearchTests as per test_suites.csv");
        }
        LogUtils.logInfo("Test suite GoogleSearchTests will run");
    }
}