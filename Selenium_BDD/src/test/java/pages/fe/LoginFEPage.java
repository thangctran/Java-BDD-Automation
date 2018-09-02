package pages.fe;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import commons.Commons;
import constants.GolobalVariables;
import keywords.WebUI;

public class LoginFEPage {
    @Then("^Verify Login FE page is displayed$")
    public void verifyLoginPage() {
        WebUI.verifyTitle("Login");
        WebUI.verifyURL(GolobalVariables.urlLogin);
    }

    @Given("^User login FE page with valid email and password$")
    public void loginFE() {
        Commons.login(GolobalVariables.urlFE, GolobalVariables.emailFE, GolobalVariables.passwordFE);
    }
}
