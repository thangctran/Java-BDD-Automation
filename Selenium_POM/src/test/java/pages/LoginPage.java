package pages;

import elements.UIElements;

public class LoginPage extends UIElements {
    static String txtEmail = "//input[@placeholder='Email'][not(@id)]";
    static String txtPassword = "//input[@name='password']";
    static String btnLogin = "//button[@type='submit'][contains(@class,'btn-block l')]";

    protected static void login(String url, String email, String password) {
        if(url != null) navigateURL(url);
        if(email != null)setText(txtEmail, null, email);
        if(password != null) setText(txtPassword, null, password);
        click(btnLogin, null);
    }
}
