package steps.fe;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import utilities.Utility;
import utilities.Variables;
import keywords.WebUI;

public class ToursPage {
    static String lstTourType = "//*[./strong[text()='Tour Type']]/a[1]";

    @When("^User filter search Tours$")
    public void filerSearch(){
        Utility.logInfo("STEP", "User filter search Tours", 1);
        Commons.filerSearch(Variables.testData.get("star"), null, null, Variables.testData.get("tourType"), null );
    }

    @Then("^Verify Tour Type each Tours displayed correctly$")
    public void verifyTourType() {
        Utility.logInfo("STEP", "Tour Type each Tours displayed correctly", 1);
        WebUI.verifyAttributeOnList(lstTourType, null, "textContent", Variables.testData.get("tourType"));
    }
}
