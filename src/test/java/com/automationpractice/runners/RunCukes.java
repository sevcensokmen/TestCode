package com.automationpractice.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.Test;

@CucumberOptions(
        features="src/test/resources/features",
        glue="com/automationpractice/steps",
        tags = {"@AddSummerDress"}
)

public class RunCukes {

    @Test
    public void runCukes() {
        new TestNGCucumberRunner(getClass()).runCukes();
    }
}

