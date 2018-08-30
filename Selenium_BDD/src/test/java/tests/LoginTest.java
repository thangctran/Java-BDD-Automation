package tests;

import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import constants.GolobalVariabes;
import keywords.WebUI;
import helpers.FileHelper;
import pages.be.MainBEPage;
import pages.be.LoginBEPage;
import pages.fe.MyAccountPage;
import utilities.Utility;

public class LoginTest {
    @Given("^I navigate to Front-End page $")
    public void user_is_on_homepage() {
        WebUI.navigateURL(GolobalVariabes.urlFE);
    }

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

    public void BE001_LoginBE() {
        //Define test data
//        Map<String, String> data = FileHelper.getTestDataCSV("login\\BE001.csv", ",", 1);

        Utility.logInfo("STEP","Login to BE with admin role", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, GolobalVariabes.emailBE, GolobalVariabes.passwordBE);

        Utility.logInfo("STEP","Verify Main BE page is displayed", 1);
        MainBEPage.verifyMainBEPage(GolobalVariabes.userBEName);
    }

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
