package utilities;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import constants.Environemnts;
import keywords.WebUI;
import helpers.FileHelper;

public class InitTestSuite extends TestListenerAdapter{
    // Override to custom onTestStart function of TestNG
    @Override
    public void onTestStart(ITestResult itr) {
        Utility.logInfo("TESTCASE","*** Execute TestCase: " + itr.getName() + " ***", 1);
    }

    // Override to custom onTestFailure function of TestNG
    @Override
    public void onTestFailure(ITestResult itr) {
        String captureImage = FileHelper.getXmlNodeValue("//Configuration/CaptureImage/text()",0);
        if(captureImage.toLowerCase() != "false") {
            WebUI.captureScreen(Environemnts.REPORTS_PATH + "images\\" + itr.getName() + "_" + Utility.getUnique("yyMMdd_HHmmss") + ".png");
        }
        onTestEnd(itr);
    }

    // Override to custom onTestSkipped function of TestNG
    @Override
    public void onTestSkipped(ITestResult itr) {
        String captureImage = FileHelper.getXmlNodeValue("//Configuration/CaptureImage/text()",0);
        if(captureImage.toLowerCase() != "false") {
            WebUI.captureScreen(Environemnts.REPORTS_PATH + "images\\" + itr.getName() + "_" + Utility.getUnique("yyMMdd_HHmmss") + ".png");
        }
        onTestEnd(itr);
    }

    // Override to custom onTestSuccess function of TestNG
    @Override
    public void onTestSuccess(ITestResult itr) {
        onTestEnd(itr);
    }

    // Override to custom onTestEnd function of TestNG
    public void onTestEnd(ITestResult itr) {
        WebUI.deleteAllCookies();
        Utility.logInfo("TESTCASE","*** End TestCase: " + itr.getName() + " ***", 1);
    }
}
