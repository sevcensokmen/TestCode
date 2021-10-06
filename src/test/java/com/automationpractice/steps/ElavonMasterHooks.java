package com.automationpractice.steps;

import com.automationpractice.DriverBase;
import com.automationpractice.extentreport.ExtentManager;
import com.automationpractice.extentreport.ExtentTestManager;
import com.aventstack.extentreports.Status;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.Date;

public class ElavonMasterHooks extends DriverBase {

    private static int scenarioNumber = 0;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println( "SCENARIO BEFORE:" + scenario.getName() + " TIME:" + new Date());
        String caseId = "";
        for (String s : scenario.getSourceTagNames()) {
            if (s.contains("TestRail")) {
                String[] res = s.split("(\\(.*?)");
                caseId = res[1].substring(0, res[1].length() - 1); // Removing the last parenthesis
                scenarioNumber = Integer.parseInt(caseId);
            }
        }
        instantiateDriverObject("elavon");
        ExtentTestManager.startTest("Scenario No : " + scenarioNumber + " : " + scenario.getName());
        ExtentTestManager.getTest().log(Status.INFO, "Scenario started : - " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) throws Throwable {
        if (getDriver() != null && scenario.isFailed()) {
            scenario.embed(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
            ExtentTestManager.logFail("Scenario Failed");
            ExtentTestManager.addScreenShotsOnFailure();
            Thread.sleep(3000);
        } else {
            ExtentTestManager.scenarioPass();
        }
        ExtentManager.getReporter().flush();
        closeDriverObjects();
    }
}