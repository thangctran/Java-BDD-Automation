package pages.fe;

import pages.BasePages;
import static utilities.Utility.getUnique;

public class MyAccountPage extends BasePages {
    static String lblUserName = "//h3[@class='RTL']";
    static String lblCurrentDay = "//span[@class='h4']";
    static String imgMenuBarIcon = "//ul[@class='nav profile-tabs' or @id='social-sidebar-menu']/li/a/*[//@class]";
    static String lblMenuBar = "//ul[@class='nav profile-tabs']/li[./a[contains(//.,'')]]";
    static String btnInvoice = "//div[@class='row'][.//span[@class='grey'][contains(//.,'')]]//a[text()='Invoice']";

    public static void verifyMyAccountPage(String userName) {
        verifyAttribute(lblUserName, null, "textContent", "Hi, " + userName);
        verifyAttribute(lblCurrentDay, null, "innerText", getUnique("d MMMM yyyy"));
        // verify Bookings; My Profile; Wishlist; Newsletter label
        verifyElementPresent(lblMenuBar, "Bookings");
        verifyElementPresent(lblMenuBar, "My Profile");
        verifyElementPresent(lblMenuBar, "Wishlist");
        verifyElementPresent(lblMenuBar, "Newsletter");
        // verify Bookings; My Profile; Wishlist; Newsletter icon
        verifyElementPresent(imgMenuBarIcon, "bookings-icon");
        verifyElementPresent(imgMenuBarIcon, "profile-icon");
        verifyElementPresent(imgMenuBarIcon, "wishlist-icon");
        verifyElementPresent(imgMenuBarIcon, "newsletter-icon");
        // verify Bookings menu is selected as default
        verifyAttribute(lblMenuBar, "Bookings", "class", "active");;
    }

    public static void clickInvoiveButton(String dynamicValue) {
        click(btnInvoice, dynamicValue);
    }
}
