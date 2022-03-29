package com.qa.Pages;

import com.qa.Base.BasePage;
import com.qa.Utility.Config;
import com.qa.Utility.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

public class PaymentPage extends BasePage {

    @FindBy(xpath = "//div[@data-testid = 'test__vehicle-price-cards-0']")
    private WebElement calculate_delivery_button;

    @FindBy (id = "street_address")
    private WebElement delivery_address;

    @FindBy(xpath = "//button[contains(@class, 'save-button')]")
    private WebElement save_and_confirm_button;

    @FindBy(xpath = "//button[contains(@class, 'save-and-confirm')]")
    private WebElement warranty_Save_and_confirm_button;

    @FindBy(xpath = "//div[@data-testid = 'test__vehicle-price-cards-1']")
    private WebElement select_warranty_button;

    @FindBy(xpath = "//div[contains(text(), '48 Months')]/parent::*")
    private WebElement fourtyEight_months_plan_button;

    @FindBy(xpath = "//input[@autocomplete = 'street-address']")
    private WebElement StreetAddress;

    @FindBy(xpath = "//input[@autocomplete = 'address-level2']")
    private WebElement city;



    Config testConfig;
    public PaymentPage(Config testConfig){
        this.testConfig = testConfig;
        PageFactory.initElements(testConfig.driver, this);
    }

    public void click_on_calculated_delivery() {
        doClick(testConfig, this.calculate_delivery_button, "Clicking on calculated delivery button");
    }

    public void fill_the_address(String address){
        By address_webElement = By.xpath(String.format("//div[contains(@class, 'pca pcalist')]/*[contains(@title, '%s')]", address));
        By pcaList = By.xpath("//div[contains(@class, 'pca pcalist')]/*");

        typeData(testConfig, this.delivery_address, address, "Entering the address as -->" + address);
        WaitHelper.waitForSeconds(testConfig, 1);


        if (!testConfig.driver.findElement(address_webElement).isDisplayed()) {
            boolean isVisible = WaitHelper.waitForElement(testConfig, testConfig.driver.findElement(address_webElement), (long)WaitHelper.pauseHigh());
            if (!isVisible) {
                clearWithBackspace(this.delivery_address);
                typeData(testConfig, this.delivery_address, address, "Entering the address again as -->" + address);
                WaitHelper.waitForSeconds(testConfig, 1);
            }
        }

        WebElement address_element = testConfig.driver.findElement(address_webElement);
        doClick(testConfig, address_element, "choosing address as Toronto");

        WaitHelper.waitForSeconds(testConfig, 1);
        WebElement street_address = testConfig.driver.findElement(pcaList);
        doClick(testConfig, street_address, "choosing street address as --> "+street_address.getText());
         WaitHelper.waitForSeconds(testConfig, 1);
        click_Save_and_confirm_button();
    }

    private void enter_address_manually(String address) {

    }

    public void click_Save_and_confirm_button() {
        WaitHelper.waitForElement(testConfig, this.save_and_confirm_button);
        doClick(testConfig, this.save_and_confirm_button, "Clicking on save and confirm button");
    }

    public void click_on_warranty_button() {
        doClick(testConfig, this.select_warranty_button, "Clicking on warranty button");
    }

    public void choose_the_warranty_plan(String warranty_months) {
        WebElement plan_option = testConfig.driver.findElement(By.xpath(String.format("//div[contains(text(), '%s Months')]/parent::*", warranty_months)));
        WaitHelper.waitForElement(testConfig, plan_option);
        doClick(testConfig, plan_option, "Choosing the plan --> " + warranty_months + "Months");
    }

    public void choose_warranty_save_button() {
        doClick(testConfig, this.warranty_Save_and_confirm_button, "Choosing warranty save button");
    }

    public void Choose_warranty_and_save(String warranty_month) {
        click_on_warranty_button();
        choose_the_warranty_plan(warranty_month);
        choose_warranty_save_button();
    }

    public String get_payment_Page_title() {
        WaitHelper.waitForElement(testConfig, this.calculate_delivery_button);
        WaitHelper.waitForSeconds(testConfig, 1);
        return getTitle(testConfig).trim();
    }






}
