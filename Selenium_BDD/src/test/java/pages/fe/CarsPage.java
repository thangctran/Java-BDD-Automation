package pages.fe;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import constants.Controls;
import keywords.WebUI;
import utilities.Utility;
import utilities.Variables;

public class CarsPage {
    static String btnAirportPickup = "//button[.=' Airport Pickup']";

    @When("^User filter search Cars")
    public void filerSearch(String startGrade, String carType, String airportPickup){
        Commons.filerSearch(Variables.testData.get("star"), null, null, Variables.testData.get("carType"), Variables.testData.get("airportPickup") );
    }

    @When("^User set Price Range to filter Cars$")
    public void setPriceRange() {
        Commons.setPriceRange(Variables.testData.get("priceFrom"), Variables.testData.get("priceTo"));
    }

    @Then("^Verify green Airport Pickup button for each Cars$")
    public static void verifyGreenAirportPickupButton() {
        String xpathTableRow = Utility.convertXpath(Controls.table, "bgwhite table table-striped") + "/*/tr";
        int rows = WebUI.countItemsOnList(xpathTableRow, null);
        for(int n=1; n<= rows; n++){
            String newXpath = String.format("%s[%d]%s", xpathTableRow, n, btnAirportPickup);
            WebUI.verifyCssValue(newXpath, null, "background-color", "rgba(92, 184, 92, 1)");
        }
    }
}
