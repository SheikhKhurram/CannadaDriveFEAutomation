package com.qa.Utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.Base.BasePage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;

public class Config {

    private Properties runtimeProperties;
    public WebDriver driver = null;
    public WebDriverWait wait = null;
    public String testcaseName;
    public String selectedCar;
    public ExtentSparkReporter spark;
    public ExtentReports extent ;
    public ExtentTest testcase;
    public String screenShotPath;
    public String browserName;



    public Config(){

        runtimeProperties = new Properties();
        Properties prop = new Properties();
        String properties_path = System.getProperty("user.dir") + File.separator + "Environment_Properties" ;
        spark = new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"Report/" + BasePage.timestamp()+"_ExtentReport.html");
        extent = new ExtentReports();

        try {
            FileInputStream fileInputStream = new FileInputStream(properties_path + File.separator + "Config.properties");
            prop.load(fileInputStream);
            fileInputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<Object> enumeration = prop.keys();

        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            putRunTimeProperty(key, (String) prop.get(key));
        }



        extent.setSystemInfo("os", "Windows");


    }

    private void putRunTimeProperty(String key, String value) {
        runtimeProperties.put(key.toLowerCase(), value);
    }

    public String getRuntimeProperty(String key) {
        String keyName = key.toLowerCase();
        try {
           return runtimeProperties.getProperty(keyName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRuntimeJsonProperty(String fileName, String key) {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = null;
        JSONObject jsonObject = null;
        String value = "";

        try {
           fileReader = new FileReader(System.getProperty("user.dir") + "/src/main/java/com/qa/Data/"+fileName+".json");
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            return (String) jsonObject.get(key);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;


    }


}
