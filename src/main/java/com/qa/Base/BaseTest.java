package com.qa.Base;

import com.qa.Base.BasePage;
import com.qa.Utility.Config;
import com.qa.Utility.ExcelUtility;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Listeners(com.qa.Listeners.TestListeners.class)
public class BaseTest extends BasePage
{

    public static ThreadLocal<Config[]> threadLocalConfig = new ThreadLocal<>();
    private Config testConfig;

    @DataProvider(name = "getTestConfig")
    public Object[][] getTestConfiguration(Method method){
        testConfig = new Config();
        testConfig.testcaseName = method.getName();
        testConfig.testcase = testConfig.extent.createTest(testConfig.testcaseName);

        threadLocalConfig.set(new Config[] {testConfig});

        String path = System.getProperty("user.dir") + File.separator + "src/main/resources/CarSelectionDetails.xlsx";
        ExcelUtility excelUtility = new ExcelUtility(testConfig, path, "CanadaDriveData");
        return excelUtility.getAllDetails();

     }


    @BeforeMethod
    @Parameters("browser")
    public void setup(String browserName) {
        testConfig.browserName = browserName;
        OpenBrowser_And_navigate_To_Url(testConfig, testConfig.getRuntimeProperty("URL"));
    }

    @AfterMethod
    public void tearDown() {
        testConfig.extent.attachReporter(testConfig.spark);
        testConfig.extent.flush();
    }

    @AfterTest
    public void quittingBrowser() {
        testConfig.driver.quit();
    }



}
