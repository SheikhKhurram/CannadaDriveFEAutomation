package com.qa.Testcases;

import com.aventstack.extentreports.Status;
import com.qa.Base.BaseTest;
import com.qa.Pages.HomePage;
import com.qa.Pages.PaymentPage;
import com.qa.Pages.VehicleDetailsPage;
import com.qa.Utility.Config;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class EndToEndTest extends BaseTest {

    private HomePage homePage;
    private VehicleDetailsPage vechileDetailsPage;
    private PaymentPage paymentPage;



    @Test(dataProvider = "getTestConfig", description = "This testcase will book a car")
    public void EndToEndTest(Config testConfig, String province ,String filter, String maker,String model, String sortByOption, String warranty)
    {
        homePage = new HomePage(testConfig);
        vechileDetailsPage = new VehicleDetailsPage(testConfig);
        paymentPage = new PaymentPage(testConfig);

        Assert.assertEquals(homePage.getTitle(testConfig), testConfig.getRuntimeJsonProperty("HomePageData" , "homePageTitle"));
        testConfig.testcase.log(Status.PASS, String.format("Validated the homePage title as -->  \"%s\" ", testConfig.getRuntimeJsonProperty("HomePageData" , "homePageTitle")));

        homePage.select_Province(province);
        Assert.assertEquals(homePage.get_province_code(province), homePage.getTextOfSelectedProvince());
        testConfig.testcase.log(Status.PASS, String.format("Validated the selected  province as -->  \"%s\" ", homePage.get_province_code(province)));


        homePage.chooseFilter(filter);

        HashMap<String, String> maker_and_model = homePage.choose_Maker_And_Model(maker, model);
        Assert.assertEquals(maker, maker_and_model.get("maker"));
        Assert.assertEquals(model, maker_and_model.get("model"));

        homePage.Sort_by();
        homePage.choose_sort_by_option(sortByOption);

        homePage.click_favorite_icon(testConfig.getRuntimeProperty("fav_count"));
        Assert.assertEquals(Integer.parseInt(testConfig.getRuntimeProperty("fav_count")), homePage.get_count_of_drawer_cards());
       //Assert.assertEquals(2, homePage.get_count_of_drawer_cards(), "Fav icon count is mismatched");
        testConfig.testcase.log(Status.PASS, "Validated marked fav count as --> "+testConfig.getRuntimeProperty("fav_count"));

        homePage.pick_vechile();
        Assert.assertTrue(StringUtils.containsIgnoreCase(vechileDetailsPage.getSelectedVehicle(), testConfig.selectedCar));
        testConfig.testcase.log(Status.PASS, String.format("Validated selected car as --> \"%s\" ", testConfig.selectedCar));


        vechileDetailsPage.click_on_purchaseButton();
        Assert.assertEquals(paymentPage.get_payment_Page_title() , testConfig.getRuntimeJsonProperty("VehicleDetailsPageData" , "title"));
        testConfig.testcase.log(Status.PASS, String.format("Validated paymentPage title as -->  \"%s\" ", testConfig.getRuntimeJsonProperty("VehicleDetailsPageData" , "title")));


        paymentPage.click_on_calculated_delivery();
        paymentPage.fill_the_address(testConfig.getRuntimeProperty("address"));
        paymentPage.Choose_warranty_and_save(warranty);

    }


}
