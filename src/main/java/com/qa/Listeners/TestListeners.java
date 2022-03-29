package com.qa.Listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.Base.BaseTest;
import com.qa.Utility.Config;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static com.qa.Base.BasePage.take_screenShot;


public class TestListeners implements ITestListener{

    @Override
    public void onTestFailure(ITestResult result) {
        Config[] testConfigs = BaseTest.threadLocalConfig.get();
        for (Config testConfig : testConfigs) {
            if (testConfig != null) {
                testConfig.testcase.info("********Execution of testcase Ends Here ****************");
                take_screenShot(testConfig, result.getName());
                testConfig.testcase.fail(MediaEntityBuilder.createScreenCaptureFromPath(testConfig.screenShotPath).build());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }


    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
