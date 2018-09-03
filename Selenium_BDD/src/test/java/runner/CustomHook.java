package runner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import keywords.WebUI;
import utilities.Utility;

public class CustomHook {
    @Before
    public void beforeScenario(Scenario scenario) {
        Utility.logInfo("TESTCASE","*** Execute TestCase: " + scenario.getName() + " ***", 1);
    }

    @After
    public void afterScenario(Scenario scenario) {
        WebUI.deleteAllCookies();
        Utility.logInfo("TESTCASE","*** End TestCase: " + scenario.getName() + " ***", 1);
    }
}
