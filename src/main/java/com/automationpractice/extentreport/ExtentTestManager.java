package com.automationpractice.extentreport;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.IOException;

public class ExtentTestManager {

    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

    public static synchronized ExtentTest getTest() {

        return testReport.get();
    }

    public static void logInfo(String message) {

        testReport.get().info(message);
    }

    public static void logPass(String message) {

        testReport.get().pass(message);
    }

    public static void scenarioPass() {

        String passLogg = "SCENARIO PASSED";
        Markup m = MarkupHelper.createLabel(passLogg, ExtentColor.GREEN);
        testReport.get().log(Status.PASS, m);
    }

    public static void logFail(String message) {

        testReport.get().fail(message);
    }

    public static synchronized boolean addScreenShotsOnFailure() throws Exception {

        ExtentManager.captureScreenshot();
        try {

            testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.lastCapturedScreenName).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String failureLogg = "SCENARIO FAILED";
        Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
        testReport.get().log(Status.FAIL, m);
        return true;
    }

    public static synchronized boolean addScreenShots() throws Exception {

        ExtentManager.captureScreenshot();
        try {
            testReport.get().info(("<b>" + "<font color=" + "green>" + "Screenshot" + "</font>" + "</b>"),
                    MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.lastCapturedScreenName).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = ExtentManager.getReporter().createTest(testName, "");
        testReport.set(test);
        return test;
    }

}
