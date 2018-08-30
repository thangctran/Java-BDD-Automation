package pages.fe;

import keywords.WebUI;
import utilities.Utility;

public class MyAccountPage {
    final static String lblUserName = "//h3[@class='RTL']";
    final static String lblCurrentDay = "//span[@class='h4']";
    final static String imgMenuBarIcon = "//ul[@class='nav profile-tabs' or @id='social-sidebar-menu']/li/a/*[//@class]";
    final static String lblMenuBar = "//ul[@class='nav profile-tabs']/li[./a[contains(//.,'')]]";
    final static String btnInvoice = "//div[@class='row'][.//span[@class='grey'][contains(//.,'')]]//a[text()='Invoice']";

    public static void verifyMyAccountPage(String userName) {
        WebUI.verifyAttribute(lblUserName, null, "textContent", "Hi, " + userName);
        WebUI.verifyAttribute(lblCurrentDay, null, "innerText", Utility.getUnique("d MMMM yyyy"));
        // verify Bookings; My Profile; Wishlist; Newsletter label
        WebUI.verifyElementPresent(lblMenuBar, "Bookings");
        WebUI.verifyElementPresent(lblMenuBar, "My Profile");
        WebUI.verifyElementPresent(lblMenuBar, "Wishlist");
        WebUI.verifyElementPresent(lblMenuBar, "Newsletter");
        // verify Bookings; My Profile; Wishlist; Newsletter icon
        WebUI.verifyElementPresent(imgMenuBarIcon, "bookings-icon");
        WebUI.verifyElementPresent(imgMenuBarIcon, "profile-icon");
        WebUI.verifyElementPresent(imgMenuBarIcon, "wishlist-icon");
        WebUI.verifyElementPresent(imgMenuBarIcon, "newsletter-icon");
        // verify Bookings menu is selected as default
        WebUI.verifyAttribute(lblMenuBar, "Bookings", "class", "active");;
    }

    public static void clickInvoiveButton(String dynamicValue) {
        WebUI.click(btnInvoice, dynamicValue);
    }
}
