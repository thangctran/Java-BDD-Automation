package pages;

import elements.UIElements;

public class LoginPage extends UIElements {
    final static String txtEmail = "//input[@placeholder='Email'][not(@id)]";
    final static String txtPassword = "//input[@name='password']";
    final static String btnLogin = "//button[@type='submit'][contains(@class,'btn-block l')]";
    final static String lblErrorMessage = "//div[starts-with(@class,'alert')]/p";
    final static String lblWaringMessage = "//*[contains(text(),'Please fill out this field.']";

    public static void login(String url, String email, String password) {
        if(url != null) navigateURL(url);
        if(email != null)setText(txtEmail, null, email);
        if(password != null) setText(txtPassword, null, password);
        click(btnLogin, null);
    }

    public static void verifyErrorMessage(String expectedMessage) {
        verifyAttribute(lblErrorMessage, null,"textContent", expectedMessage);
    }

    public static void verifyWarningMessage() {
        verifyElementPresent(lblWaringMessage, null);
    }
}
