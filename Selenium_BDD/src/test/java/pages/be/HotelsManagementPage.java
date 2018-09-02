package pages.be;

import java.util.List;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import commons.CustomWebUI;
import constants.Controls;
import utilities.Enums;
import utilities.Utility;
import utilities.Variables;

public class HotelsManagementPage {

    @When("^User deletes Hotel by Button$")
    public void deleteHotelByButton() {
        Variables.testData.put("hotelName", Commons.deleteRowByButton(Variables.testData.get("rowIndex"), "Name"));
    }

    @When("^User deletes Hotel by Icon$")
    public void deleteHotelByIcon() {
        Variables.testData.put("hotelName", Commons.deleteRowByButton(Variables.testData.get("rowIndex"), "Name"));
    }

    @Then("^Verify User deleted a Hotel$")
    public void verifyHotelNameDeleted() {
        List<String> listName = CustomWebUI.getCellValuesOnTable(Controls.table, null, "Name", Variables.testData.get("rowIndex"));
        Utility.verifyValues("verifyHotelNameDeleted", listName.get(0), Variables.testData.get("hotelName"), Enums.OPERATOR.notEqual);
    }

    @Then("^Verify Image Number is uploaded$")
    public void verifyImageNumberUploaded() {
        int currentNumber = CustomWebUI.getImageNumberUpload(Variables.testData.get("rowIndex"));
        int expectedImageNumber = Integer.valueOf(Variables.testData.get("imageNumber")) + 1;
        Utility.verifyValues("verifyImageNumberUploaded", String.valueOf(currentNumber), String.valueOf(expectedImageNumber), Enums.OPERATOR.equal);
    }

    @When("^User upload a image to Hotel$")
    public void uploadGallery() {
        int currentNumber = CustomWebUI.getImageNumberUpload(Variables.testData.get("rowIndex"));
        Variables.testData.put("imageNumber", String.valueOf(currentNumber));
        Commons.uploadGallery(Variables.testData.get("rowIndex"), Variables.testData.get("imageUpload"));
    }
}
