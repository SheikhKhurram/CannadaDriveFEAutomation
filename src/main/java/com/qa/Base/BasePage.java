package com.qa.Base;

import com.qa.Utility.Config;
import com.qa.Utility.WaitHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class BasePage {

    private WebDriver driver;
    /**
     *
     * @param testConfig
     * @param url
     */
    public void OpenBrowser_And_navigate_To_Url(Config testConfig, String url) {
        if (testConfig.driver == null)
            initializeBrowser(testConfig);

        testConfig.driver.get(url);
        testConfig.testcase.info(String.format("Navigated to URL " + "\"%s\"", url));

    }

    /**
     *
     * @param testConfig
     * This method will initialize the browser and launch the passed browserName
     */
    private void initializeBrowser(Config testConfig){
        String browserName = testConfig.browserName.toLowerCase();

        switch (browserName) {
            case "chrome" : {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("start-fullscreen");
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "firefox" : {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                this.driver = new FirefoxDriver(options);
                break;
            }
            case "opera" : {
                WebDriverManager.operadriver().setup();
                this.driver = new OperaDriver();
                break;
            }
        }
        testConfig.testcase.info("Launching "+browserName + " browser");

        testConfig.driver = this.driver;
        String time = testConfig.getRuntimeProperty("Wait");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(time)));
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(testConfig.getRuntimeProperty("pageLoadTimeout"))));
        driver.manage().window().fullscreen();

    }

    /**
     *
     * @param testConfig
     * @param element
     *  This method will scorll into view till passed element is focused
     */
    public void scroll_Into_View(Config testConfig, WebElement element){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JavascriptExecutor js = (JavascriptExecutor) testConfig.driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * @param testConfig
     * @param element
     * @param description
     *
     * This method is wrapper for click() which will wait for an element and click on passed element
     *
     */
    public void doClick(Config testConfig , WebElement element , String... description ) {
        WaitHelper.waitForElementToBeClickable(testConfig, element);
        element.click();
        if(description.length > 0) {
            testConfig.testcase.info(description[0]);
        }

    }

    public void doClick_and_scroll(Config testConfig , WebElement element , String description , boolean... scroll) {
        WaitHelper.waitForElementToBeClickable(testConfig, element);
        element.click();
        if (scroll.length > 0 && scroll[0]) {
            scroll_Into_View(testConfig, element);
        }
    }

    /**
     *
     * @param element
     * @param value
     * @param description
     * This method is wrapper for click() method of selenium , which clear the input section, wait for element and enter the passed argument
     */

    public void enterData(WebElement element, String value, String description)
    {
        element.clear();
        element.sendKeys(value);
    }


    /**
     *
     * @param testConfig
     * @param element
     * @param value
     * @param description
     * This method is wrapper for click() method of selenium , which clear the input section, wait for element and enter the passed argument
     */
    public void typeData(Config testConfig, WebElement element, String value, String... description) {
        element.clear();
        String[] values = value.split("");
        for (String val : values) {
            element.sendKeys(val);
            WaitHelper.waitForSeconds(testConfig, 0.15);
        }
        if(description.length > 0) {
            testConfig.testcase.info(description[0]);
        }

    }

    /**
     *
     * @param testConfig
     * @param element
     * @param description
     * @return
     */
    public String get_Text(Config testConfig , WebElement element, String... description)
    {
        WaitHelper.waitForElement(testConfig, element);
        if(description.length > 0) {
            testConfig.testcase.info(description[0]);
        }
        return element.getText();

    }

    /**
     *
     * @param testConfig
     * @param element
     * @param description
     */
    public void check_the_checkBox(Config testConfig, WebElement element, String... description){
        WaitHelper.waitForElement(testConfig, element);
        String disc = description.length > 0 ? "Checking the checkbox -->"+ description[0] : "" ;
        try {
            if (!element.isSelected()){
                try{
                    doClick(testConfig,element,disc);
                }
                catch (StaleElementReferenceException e) {
                    doClick(testConfig,element);
                }
            }
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        if(description.length > 0) {
            testConfig.testcase.info(description[0]);
        }
    }

    public String getTitle(Config testConfig){
       return testConfig.driver.getTitle().trim();
    }


    public static void clearWithBackspace(WebElement input) {
        while(input.getAttribute("value").length() > 0) {
            input.sendKeys(Keys.BACK_SPACE);
        }
    }

    /**
     *
     * @param testConfig
     * @param screenShotName
     * This methosd will takesScreenshot and save the img file in ScreenShot folder
     */
    public static void take_screenShot(Config testConfig, String screenShotName) {
        File file = ((TakesScreenshot) testConfig.driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/target/" + screenShotName+ "_"+ timestamp() + ".png";
        try {
            FileHandler.copy(file , new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        testConfig.screenShotPath = path;
    }


    /**
     * @return
     * This method will return the currentTimeStamp
     */
    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }









}
