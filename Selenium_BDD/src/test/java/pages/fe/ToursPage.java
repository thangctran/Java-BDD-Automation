package pages.fe;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import utilities.Variables;
import keywords.WebUI;

public class ToursPage {
    static String lstTourType = "//*[./strong[text()='Tour Type']]/a[1]";

    @When("^User filter search Tours$")
    public void filerSearch(){
        Commons.filerSearch(Variables.testData.get("star"), null, null, Variables.testData.get("tourType"), null );
    }

    @When("^User set Price Range to filter Tours$")
    public void setPriceRange() {
        Commons.setPriceRange(Variables.testData.get("priceFrom"), Variables.testData.get("priceTo"));
    }

    @Then("^Tour Type each Tours displayed correctly$")
    public void verifyTourType() {
        WebUI.verifyAttributeOnList(lstTourType, null, "textContent", Variables.testData.get("tourType"));
    }
}
