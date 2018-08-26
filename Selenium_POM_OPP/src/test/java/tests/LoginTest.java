package tests;

import java.util.Map;


import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import constants.GolobalVariabes;
import drivers.Driver;
import keywords.WebUI;
import helpers.FileHelper;
import pages.be.MainBEPage;
import pages.be.LoginBEPage;
import pages.fe.MyAccountPage;
import utilities.Utility;

public class LoginTest {

    @BeforeSuite
    public void startTestSuite(){
        Driver.setDriver(Driver.setSeleniumDrivers());
    }

    @AfterSuite
    public void endTestSuite(){
        WebUI.closeDriver();
    }

    @Test(priority = 0, description = "FE001-Login - Login successful")
    public void FE001_LoginFE() {
        Utility.logInfo("STEP","Navigate to url 'https://www.phptravels.net/'", 1);
        WebUI.navigateURL(GolobalVariabes.urlFE);

        Utility.logInfo("STEP", "Select MY ACCOUNT -> Login", 1);
        MainBEPage.goToFunction("My Account", " Login");

        Utility.logInfo("STEP", "Verify Login page is displayed", 1);
        WebUI.verifyTitle("Login");
        WebUI.verifyURL(GolobalVariabes.urlLogin);

        Utility.logInfo("STEP","Click on 'LOGIN' button with valid email and password", 1);
        LoginBEPage.login(null, GolobalVariabes.emailFE, GolobalVariabes.passwordFE);

        Utility.logInfo("STEP","Main page is displayed", 1);
        MyAccountPage.verifyMyAccountPage(GolobalVariabes.userName);
    }

    @Test(priority = 0, description = "BE001-Login-Login to page successful")
    public void BE001_LoginBE() {
        //Define test data
//        Map<String, String> data = FileHelper.getTestDataCSV("login\\BE001.csv", ",", 1);

        Utility.logInfo("STEP","Login to BE with admin role", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, GolobalVariabes.emailBE, GolobalVariabes.passwordBE);

        Utility.logInfo("STEP","Verify Main BE page is displayed", 1);
        MainBEPage.verifyMainBEPage(GolobalVariabes.userBEName);
    }

    @Test(priority = 0, description = "BE002-Login-Login to page unsuccessful")
    public void BE002_LoginBE() {
        //Define test data
        Map<String, String> data = FileHelper.getTestDataRow("testData.xlsx", "login", 1);

        Utility.logInfo("STEP","Login with blank 'Email' and 'Password'", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, "", "");
        LoginBEPage.verifyWarningMessage();

        Utility.logInfo("STEP","Click on 'LOGIN' button with invalid format email and password", 1);
        LoginBEPage.login(null, data.get("email"), data.get("password"));;
        LoginBEPage.verifyErrorMessage(data.get("error_message"));
    }
}
