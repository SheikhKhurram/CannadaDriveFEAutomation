package com.qa.Utility;

import com.qa.Base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private static WebDriverWait wait;


    public static void waitForElement(Config testConfig, WebElement element, String... description) {

        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(Long.parseLong(testConfig.getRuntimeProperty("WebdriverTimeout"))));

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        }
        catch (StaleElementReferenceException e) {
            try {
                wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }


    public static boolean waitForElement(Config testConfig, WebElement element, long time, String... description) {

        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(time));

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch (StaleElementReferenceException e) {
            try {
                wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
                return true;
            }
            catch (NoSuchElementException ee) {
                return false;
            }
            catch (Exception ee) {
                ee.printStackTrace();
                return false;
            }
        }
    }


    public static void waitForElementToBeClickable(Config testConfig, WebElement element, String... description) {

        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(Long.parseLong(testConfig.getRuntimeProperty("WebdriverTimeout"))));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        catch (StaleElementReferenceException e) {
            try {
                wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }


    public static void waitForSeconds(Config testConfig , double seconds) {
        int milliseconds =(int) (seconds * 1000);
        try{
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double pauseLow() {
        return 1;
    }

    public static double pauseMedium() {
        return 3;
    }

    public static double pauseHigh() {
        return 5;
    }






}
