package pages.be;

import utilities.Utility;
import keywords.WebUI;

public class MainBEPage {
    final static String btnButtons = "//button[//@class]";
    final static String lblHeadSessions = "//div[@class='panel-heading'][//.='Booking Summary ']";
    final static String lblVisitStatistics = "//div[@class='pull-left']";
    final static String lblUser = "//div[@class='user']/span";
    final static String mnuMenuContent = "(//ul[contains(@*,'sidebar')])[last()]/li[.//*[contains(//text(),'')]]/a";
    final static String mnuSubMenu = "//*[./*[@aria-expanded='true']]//li/a[//text()]";

    public static void goToFunction(String mainMenu, String subMenu) {
        WebUI.click(mnuMenuContent, mainMenu);
        if(subMenu != null) WebUI.click(mnuSubMenu, subMenu);
    }

    public static void verifyMainBEPage(String userName){
        // 'First name + Last name'of Account displays on the left menu bar. Ex: 'Super Admin Admin'.
        WebUI.verifyAttribute(lblUser, null, "outerText", userName);

        //6 buttons
        // 'QUICK BOOKING' has red color
        WebUI.verifyAttribute(btnButtons, "btn btn-danger btn-block", "outerText", "QUICK BOOKING");
        WebUI.verifyCssValue(btnButtons, "btn btn-danger btn-block", "background-color", "rgba(238, 95, 91, 1)");
        //'BOOKINGS' has blue color
        WebUI.verifyAttribute(btnButtons, "btn btn-primary btn-block", "outerText", "BOOKINGS");
        WebUI.verifyCssValue(btnButtons, "btn btn-primary btn-block", "background-color", "rgba(70, 109, 241, 1)");
        //'CMS PAGES' has light-blue color
        WebUI.verifyAttribute(btnButtons, "btn btn-info btn-block", "outerText", "  CMS PAGES");
        WebUI.verifyCssValue(btnButtons, "btn btn-info btn-block", "background-color", "rgba(91, 192, 222, 1)");
         //'BLOG' has green color
        WebUI.verifyAttribute(btnButtons, "btn btn-success btn-block", "outerText", "BLOG");
        WebUI.verifyCssValue(btnButtons, "btn btn-success btn-block", "background-color", "rgba(98, 196, 98, 1)");
        //'SEND NEWSLETTER' has yellow color
        WebUI.verifyAttribute(btnButtons, "btn btn-warning btn-block", "outerText", "SEND NEWSLETTER");
        WebUI.verifyCssValue(btnButtons, "btn btn-warning btn-block", "background-color", "rgba(251, 180, 80, 1)");
        //'BACKUP DATABASE' has white color
        WebUI.verifyAttribute(btnButtons, "btn btn-default btn-block", "outerText", "BACKUP DATABSE");
        WebUI.verifyCssValue(btnButtons, "btn btn-default btn-block", "background-color", "rgba(255, 255, 255, 1)");

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
