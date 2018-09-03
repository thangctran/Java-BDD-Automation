package pages.fe;

import java.util.List;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import commons.CustomWebUI;
import constants.Controls;
import utilities.Utility;
import utilities.Variables;

public class HotelsPage {
    @When("^User filter search Hotels$")
    public void filerSearch(){
        Utility.logInfo("STEP", "User filter search Hotels", 1);
        List<String> listPropertyTypes = Utility.convertStringToList(Variables.testData.get("propertyTypes"), ",");
        List<String> listAmenities = Utility.convertStringToList(Variables.testData.get("amenities"), ",");
        Commons.filerSearch(Variables.testData.get("star"), listPropertyTypes, listAmenities, null, null );
    }

    @Then("^Verify Number Star of each Hotels displayed correctly$")
    public void verifyNumberStartHotels() {
        Utility.logInfo("STEP", "Verify Number Star of each Hotels displayed correctly", 1);
        CustomWebUI.verifyStartOnTable(Controls.table, "bgwhite table table-striped", "1",null, Variables.testData.get("star"));
    }
}