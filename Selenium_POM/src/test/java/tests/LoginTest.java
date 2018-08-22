package tests;

import java.util.Map;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import constains.GolobalVariabes;
import drivers.SeleniumDrivers;
import elements.UIElements;
import helpers.FileHelper;
import pages.BasePages;
import pages.fe.MyAccountPage;
import pages.LoginPage;
import utilities.Utility;

public class LoginTest {

    @BeforeSuite
    public void startTestSuite(){
        UIElements.setDriver(SeleniumDrivers.setSeleniumDrivers());
    }

    @AfterSuite
    public void endTestSuite(){
        UIElements.closeDriver();
    }

    @Test(priority = 0, description = "FE001-Login - Login successful")
    public void FE001_LoginFE() {
        Utility.logInfo("TESTCASE","FE001-Login - Login successful", 1);
        Utility.logInfo("STEP","Navigate to url 'https://www.phptravels.net/'", 1);
        BasePages.navigateURL(GolobalVariabes.urlFE);

        Utility.logInfo("STEP", "Select MY ACCOUNT -> Login", 1);
        BasePages.goToFuction("My Account", " Login");

        Utility.logInfo("STEP", "Verify Login page is displayed", 1);
        BasePages.verifyTitle("Login");
        BasePages.verifyURL(GolobalVariabes.urlLogin);

        Utility.logInfo("STEP","Click on 'LOGIN' button with valid email and password", 1);
        LoginPage.login(null, GolobalVariabes.emailFE, GolobalVariabes.passwordFE);

        Utility.logInfo("STEP","Main page is displayed", 1);
        MyAccountPage.verifyMyAccountPage(GolobalVariabes.userName);
    }

    @Test(priority = 0, description = "BE002-Login-Login to page unsuccessful")
    public void BE002_LoginBE() {
        Utility.logInfo("TESTCASE","BE002-Login-Login to page unsuccessful", 1);
        //Define test data
        Map<String, String> data = FileHelper.getTestDataRow("testData.xlsx", "login", 1);

        Utility.logInfo("STEP","Click on 'LOGIN' button with invalid format email and password", 1);
        LoginPage.login(GolobalVariabes.urlBE, data.get("email"), data.get("password"));;
        LoginPage.verifyErrorMessage(data.get("error_message"));

        Utility.logInfo("STEP","Click on 'LOGIN' button with blank 'Email' and 'Password'", 1);
        LoginPage.login(null, "", "");
        LoginPage.verifyWarningMessage();
    }
}
