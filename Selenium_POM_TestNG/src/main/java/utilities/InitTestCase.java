package utilities;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import constants.Environemnts;
import keywords.WebUI;
import helpers.FileHelper;
import google.GoogleSheets;

public class InitTestCase extends TestListenerAdapter{
    static String startDate;
    // Override to custom onTestStart function of TestNG
    @Override
    public void onTestStart(ITestResult itr) {
        startDate = Utility.getUnique("yyyy/MM/dd HH:mm:ss.SSS");
        Utility.logInfo("TESTCASE","*** Execute TestCase: " + itr.getMethod().getDescription() + " ***", 1);
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
        String testResult;
        switch(itr.getStatus()){
            case 1:
                testResult = "passed";
                break;
            case 2:
                testResult = "failed";
                break;
            default:
                testResult = "skipped";
        }
        try {
            //Update to google sheet: https://docs.google.com/spreadsheets/d/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/edit#gid=0
            GoogleSheets.updateTestCaseStatus(itr.getMethod().getDescription(), startDate, testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebUI.deleteAllCookies();
        Utility.logInfo("TESTCASE","*** End TestCase: " + itr.getMethod().getDescription() + " ***", 1);
    }
}
