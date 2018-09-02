package pages.be;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import commons.Commons;
import cucumber.api.java.en.When;
import keywords.WebUI;
import constants.GolobalVariables;
import utilities.Variables;

public class LoginBEPage {
    final static String lblErrorMessage = "//div[starts-with(@class,'alert')]/p";
    final static String lblWaringMessage = "//*[contains(text(),'Please fill out this field.']";

    @Given("^User login BE page with valid email and password$")
    public void login() {
        Commons.login(GolobalVariables.urlBE, GolobalVariables.emailBE, GolobalVariables.passwordBE);
    }

    @Given("^User login BE page with blank email and password$")
    public void loginBlankEmailAndPassword() {
        Commons.login(GolobalVariables.urlBE, "", "");
    }

    @Given("^User login BE page with invalid format email$")
    public void loginInvalidFormatEmail() {
        Commons.login(GolobalVariables.urlBE, Variables.testData.get("email"), GolobalVariables.passwordBE);
    }

    @Then("^Error message is displayed$")
    public void verifyErrorMessage(String expectedMessage) {
        WebUI.verifyAttribute(lblErrorMessage, null,"textContent", Variables.testData.get("error_message"));
    }

    @Then("^Warning message is displayed$")
    public void verifyWarningMessage() {
        WebUI.verifyElementPresent(lblWaringMessage, null);
    }
}
