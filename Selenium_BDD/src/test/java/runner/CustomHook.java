package runner;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import keywords.WebUI;
import utilities.Utility;

public class CustomHook {
    @Before
    public void beforeScenario() {
        Utility.logInfo("TESTCASE","*** Execute TestCase:  ***", 1);
    }

    @After
    public void afterScenario() {
        WebUI.deleteAllCookies();
        Utility.logInfo("TESTCASE","*** End TestCase:  ***", 1);
    }
}
