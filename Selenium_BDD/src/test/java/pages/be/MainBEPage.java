package pages.be;

import cucumber.api.java.en.Then;
import constants.GolobalVariables;
import utilities.Utility;
import keywords.WebUI;

public class MainBEPage {
    final static String btnButtons = "//button[//@class]";
    final static String lblHeadSessions = "//div[@class='panel-heading'][//.='Booking Summary ']";
    final static String lblVisitStatistics = "//div[@class='pull-left']";
    final static String lblUser = "//div[@class='user']/span";

    @Then("^Verify BE page is displayed correctly$")
    public static void verifyMainBEPage(){
        Utility.logInfo("STEP", "Verify BE page is displayed correctly", 1);
        // 'First name + Last name'of Account displays on the left menu bar. Ex: 'Super Admin Admin'.
        WebUI.verifyAttribute(lblUser, null, "outerText", GolobalVariables.userBEName);

        //6 buttons
        // 'QUICK BOOKING' has red color
        WebUI.verifyAttribute(btnButtons, "btn btn-danger btn-block", "outerText", "QUICK BOOKING");
        WebUI.verifyColorElement(btnButtons, "btn btn-danger btn-block", "background-color", "#ee5f5b");
        //'BOOKINGS' has blue color
        WebUI.verifyAttribute(btnButtons, "btn btn-primary btn-block", "outerText", "BOOKINGS");
        WebUI.verifyColorElement(btnButtons, "btn btn-primary btn-block", "background-color", "#466df1");
        //'CMS PAGES' has light-blue color
        WebUI.verifyAttribute(btnButtons, "btn btn-info btn-block", "outerText", "  CMS PAGES");
        WebUI.verifyColorElement(btnButtons, "btn btn-info btn-block", "background-color", "#5bc0de");
         //'BLOG' has green color
        WebUI.verifyAttribute(btnButtons, "btn btn-success btn-block", "outerText", "BLOG");
        WebUI.verifyColorElement(btnButtons, "btn btn-success btn-block", "background-color", "#62c462");
        //'SEND NEWSLETTER' has yellow color
        WebUI.verifyAttribute(btnButtons, "btn btn-warning btn-block", "outerText", "SEND NEWSLETTER");
        WebUI.verifyColorElement(btnButtons, "btn btn-warning btn-block", "background-color", "#fbb450");
        //'BACKUP DATABASE' has white color
        WebUI.verifyAttribute(btnButtons, "btn btn-default btn-block", "outerText", "BACKUP DATABSE");
        WebUI.verifyColorElement(btnButtons, "btn btn-default btn-block", "background-color", "#ffffff");

        // 4 Sessions:
        //'BOOKING SUMMARY'
        WebUI.verifyElementPresent(lblHeadSessions, "Booking Summary ");
        //'REVENUE CHART'
        WebUI.verifyElementPresent(lblHeadSessions, "Revenue Chart");
        //'RECENT BOOKINGS'
        WebUI.verifyElementPresent(lblHeadSessions, "Recent Bookings");
        //'VISIT STATISTICS OF <MONTH>'
        WebUI.verifyAttribute(lblVisitStatistics, null, "outerText", "VISIT STATISTICS OF " + Utility.getUnique("MMMM").toUpperCase());
    }
}
