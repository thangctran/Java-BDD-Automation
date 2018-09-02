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
        List<String> listPropertyTypes = Utility.convertStringToList(Variables.testData.get("propertyTypes"), ",");
        List<String> listAmenities = Utility.convertStringToList(Variables.testData.get("amenities"), ",");
        Commons.filerSearch(Variables.testData.get("star"), listPropertyTypes, listAmenities, null, null );
    }

    @When("^User set Price Range to filter Hotels$")
    public void setPriceRange() {
        Commons.setPriceRange(Variables.testData.get("priceFrom"), Variables.testData.get("priceTo"));
    }

    @Then("^Verify Number Star of each Hotels displayed correctly$")
    public void verifyNumberStartHotels() {
        CustomWebUI.verifyStartOnTable(Controls.table, "bgwhite table table-striped", "1",null, Variables.testData.get("star"));
    }
}