package runner;

import constants.Environemnts;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import helpers.FileHelper;
import keywords.WebUI;
import utilities.Utility;
import utilities.Variables;

public class CustomHook {
    @Before
    public void beforeScenario(Scenario scenario) {
        Utility.logInfo("TESTCASE","*** Execute TestCase: " + scenario.getName() + " ***", 1);
    }

    @After
    public void afterScenario(Scenario scenario) {
        if(scenario.getStatus() != "passed"){
            String captureImage = FileHelper.getXmlNodeValue("//Configuration/CaptureImage/text()",0);
            if(captureImage.toLowerCase() != "false") {
                WebUI.captureScreen(Environemnts.REPORTS_PATH + "images\\" + scenario.getName().split("-")[0] + "_" + Utility.getUnique("yyMMdd_HHmmss") + ".png");
            }
        }
        WebUI.deleteAllCookies();
        if(Variables.testData.size() > 0) Variables.testData.clear();
        Utility.logInfo("TESTCASE","*** End TestCase: " + scenario.getName() + " ***", 1);
    }
}
