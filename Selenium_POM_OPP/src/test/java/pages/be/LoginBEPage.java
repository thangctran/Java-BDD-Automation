package pages.be;

import commons.Commons;
import pages.BasePages;

public class LoginBEPage extends BasePages {
    final static String lblErrorMessage = "//div[starts-with(@class,'alert')]/p";
    final static String lblWaringMessage = "//*[contains(text(),'Please fill out this field.']";

    public static void login(String url, String email, String password) {
        Commons.login(url, email, password);
    }

    public static void verifyErrorMessage(String expectedMessage) {
        verifyAttribute(lblErrorMessage, null,"textContent", expectedMessage);
    }

    public static void verifyWarningMessage() {
        verifyElementPresent(lblWaringMessage, null);
    }
}
