package com.qa.Pages;

import com.qa.Base.BasePage;
import com.qa.Utility.Config;
import com.qa.Utility.WaitHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import java.util.regex.Pattern;

public class VehicleDetailsPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'Start Purchase')]/parent::*/parent::*[contains(@class, 'desktop')]")
    private WebElement purchase_button;

    Config testConfig;
    public VehicleDetailsPage(Config testConfig){
        this.testConfig = testConfig;
        PageFactory.initElements(testConfig.driver, this);
    }


    public void click_on_purchaseButton(){
        doClick(testConfig, purchase_button, "Clicking on purchase button");
    }

    public String getSelectedVehicle() {
        WaitHelper.waitForElement(testConfig, this.purchase_button);
        String selected_car = testConfig.driver.getTitle().trim();
        return selected_car;
    }




}
