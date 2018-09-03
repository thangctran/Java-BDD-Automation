package pages.be;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import commons.Commons;
import keywords.WebUI;
import constants.GolobalVariables;
import utilities.Utility;
import utilities.Variables;

public class LoginBEPage {
    final static String lblErrorMessage = "//div[starts-with(@class,'alert')]/p";
    final static String lblWaringMessage = "//*[contains(text(),'Please fill out this field.']";

    @Given("^User login BE page with valid email and password$")
    public void login() {
        Utility.logInfo("STEP", "User login BE page with valid email and password", 1);
        Commons.login(GolobalVariables.urlBE, GolobalVariables.emailBE, GolobalVariables.passwordBE);
    }

    @Given("^User login BE page with blank email and password$")
    public void loginBlankEmailAndPassword() {
        Utility.logInfo("STEP", "User login BE page with blank email and password", 1);
        Commons.login(GolobalVariables.urlBE, "", "");
    }

    @Given("^User login BE page with invalid format email$")
    public void loginInvalidFormatEmail() {
        Utility.logInfo("STEP", "User login BE page with invalid format email", 1);
        Commons.login(GolobalVariables.urlBE, Variables.testData.get("email"), GolobalVariables.passwordBE);
    }

    @Then("^Verify Error message is displayed$")
    public void verifyErrorMessage() {
        Utility.logInfo("STEP", "Verify Error message is displayed", 1);
        WebUI.verifyAttribute(lblErrorMessage, null,"textContent", Variables.testData.get("errorMessage"));
    }

    @Then("^Verify Warning message is displayed$")
    public void verifyWarningMessage() {
        Utility.logInfo("STEP", "Verify Warning message is displayed", 1);
        WebUI.verifyElementPresent(lblWaringMessage, null);
    }
}
