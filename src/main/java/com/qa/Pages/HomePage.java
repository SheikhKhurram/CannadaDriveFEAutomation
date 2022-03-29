package com.qa.Pages;

import com.qa.Base.BasePage;
import com.qa.Utility.Config;
import com.qa.Utility.WaitHelper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[contains(text(), ' Search Results')]//input[starts-with(@id, 'input')]/parent::*")
    private WebElement provinceDropDown;

    @FindAll(@FindBy (xpath = "//div[contains(@id, 'list-item')]"))
    private List<WebElement> provinceDropDownItems;

    @FindBy(xpath = "//div[contains(text() , 'Featured')]")
    private WebElement findBy_button;

    @FindAll(@FindBy (xpath = "//button[contains(@class, ' fav-icon')]"))
    private List<WebElement> fav_icon;

    @FindAll(@FindBy(xpath = "//div[@class = 'vehicle-card']"))
    private List<WebElement> vehicle_card;

    @FindAll(@FindBy(xpath = "//div[@class = 'vehicle-card']//div[contains(@class, 'vehicle-card__title')]"))
    private List<WebElement> vehicle_name;

    @FindBy(xpath = "//div[contains(@class, 'province-menu')]/span")
    private WebElement selected_province;

    @FindAll(@FindBy(xpath = "//div[contains(@class, 'drawer')]//div[@class = 'vehicle-card']"))
    private List<WebElement> drawer_vehicle_cards;

    @FindBy(xpath = "//span[contains(@class, ' favourite-badge')]")
    private WebElement fav_icon_button;

    @FindBy(xpath = "//span[contains(@class, 'v-chip__content')]")
    private WebElement chipContent;


    Config testConfig;

    public HomePage(Config testConfig){
        PageFactory.initElements(testConfig.driver, this);
        this.testConfig = testConfig;
    }



    public void select_Province(String province) {
        doClick(testConfig, this.provinceDropDown, "Clicking on search result dropdown");
        for (WebElement element : this.provinceDropDownItems){
            if (get_Text(this.testConfig, element).trim().equals(province)){
                doClick(this.testConfig, element, "Selecting the province as -->" + province);
                return;
            }
        }
        throw new Error("passed province doesn't exit");
    }

    public void chooseFilter(String filterName) {
        WebElement filterElement = testConfig.driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", filterName)));
        WaitHelper.waitForElement(testConfig, filterElement);
        doClick(testConfig, filterElement, "Choosing the filter" + filterName);
        WaitHelper.waitForSeconds(testConfig,1);
    }


    public HashMap<String, String> choose_Maker_And_Model(String maker , String model) {
        WebElement maker_element = testConfig.driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", maker)));
        //scroll_Into_View(testConfig, maker_element);
        WaitHelper.waitForElement(testConfig, maker_element);
        doClick(testConfig, maker_element, "Choosing the maker -->" + maker);

        WebElement model_element = testConfig.driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", model)));
        WaitHelper.waitForElement(testConfig, model_element);
        check_the_checkBox(testConfig, model_element, "Choosing the model -->" + maker);

        return get_chip_content();
    }

    public void Sort_by()
    {
        Actions actions = new Actions(testConfig.driver);
        actions.moveToElement(this.findBy_button);
        doClick(testConfig, this.findBy_button);
    }

    public void choose_sort_by_option(String option)
    {
        WebElement option_element = testConfig.driver.findElement(By.xpath(String.format("//div[contains(text() , '%s')]", option)));
        doClick(testConfig, option_element , "Choosing the filter option as --> " + option);
        WaitHelper.waitForSeconds(testConfig,0.5);
    }

    public void click_favorite_icon(String count){
        List<WebElement> fav_icons = this.fav_icon;
        int count_of_fav = Integer.parseInt(count);
        WaitHelper.waitForSeconds(testConfig,1);
        for (int i = 0 ; i < count_of_fav ; i++)
        {
            WaitHelper.waitForElementToBeClickable(testConfig, fav_icons.get(i));
            doClick(testConfig, fav_icons.get(i), "marking fav car --> " + vehicle_name.get(i).getText().trim());
            WaitHelper.waitForSeconds(testConfig,1);
        }

    }

    public void pick_vechile(){
        WaitHelper.waitForSeconds(testConfig,1);
        testConfig.selectedCar = get_Text(testConfig, this.vehicle_name.get(0)).trim();
        doClick(testConfig, vehicle_card.get(0), "Choosing available vechile as --> "+ testConfig.selectedCar);
    }


    public String getTextOfSelectedProvince()
    {
       return get_Text(testConfig, this.selected_province).trim();
    }

    public String get_province_code(String province) {
        HashMap<String, String> province_code = new HashMap<>();
        province_code.put("ontario", "ON");
        province_code.put("british columbia", "BC");
        province_code.put("alberta", "AB");

        return province_code.get(province.toLowerCase());
    }

    public int get_count_of_drawer_cards() {
        doClick(testConfig, this.fav_icon_button);
        WaitHelper.waitForElement(testConfig, this.drawer_vehicle_cards.get(1));
        int vehicle_count = this.drawer_vehicle_cards.size();
        doClick(testConfig, this.fav_icon_button);
        return vehicle_count;
    }

    public HashMap<String, String> get_chip_content(){
        String[] maker_and_model = get_Text(testConfig, this.chipContent).trim().split(":");
        String maker = maker_and_model[0].trim();
        String model = maker_and_model[1].trim();

        HashMap<String, String> maker_and_model_hashMap = new HashMap<>();
        maker_and_model_hashMap.put("maker" , maker);
        maker_and_model_hashMap.put("model" , model);
        return maker_and_model_hashMap;
    }







}
