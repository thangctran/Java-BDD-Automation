package steps.fe;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import commons.Commons;
import constants.GolobalVariables;
import keywords.WebUI;
import utilities.Utility;

public class LoginFEPage {
    @Then("^Verify Login FE page is displayed$")
    public void verifyLoginPage() {
        Utility.logInfo("STEP", "Verify Login FE page is displayed", 1);
        WebUI.verifyTitle("Login");
        WebUI.verifyURL(GolobalVariables.urlLogin);
    }

    @Given("^User login FE page with valid email and password$")
    public void loginFE() {
        Utility.logInfo("STEP", "User login FE page with valid email and password$", 1);
        Commons.login(GolobalVariables.urlLogin, GolobalVariables.emailFE, GolobalVariables.passwordFE);
    }
}
