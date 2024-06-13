package com.koch.automation.framework.pages;

import com.koch.automation.framework.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends BasePage {

    @FindBy(name = "q")
    private WebElement searchBox;

    public SamplePage(WebDriver driver) {
        super(driver);
    }

    public void search(String query) {
        searchBox.sendKeys(query);
        searchBox.submit();
    }
}