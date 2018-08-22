package constains;

import utilities.Enums;

public class GolobalVariabes {

    public final static String urlBE = "https://www.phptravels.net/admin";
    public final static String emailBE = "admin@phptravels.com";
    public final static String passwordBE = "demoadmin";
    public final static String urlFE = "https://www.phptravels.net";
    public final static String urlLogin = "https://www.phptravels.net/login";
    public final static String emailFE = "user@phptravels.com";
    public final static String passwordFE = "demouser";
    public final static String userName = "Johny Smith";
    public final static String urlHotels = urlFE + "/hotels";
    public final static String urlFlights = urlFE + "/flights";
    public final static String urlCars = urlFE + "/cars";
    public final static String urlBlog = urlFE + "/blog";
    public final static String urlVisa = urlFE + "/ivisa";

    public static String getXpathElement(Enums.ELEMENT_TYPE elementName) {
        String returnXpath;
        switch(elementName) {
            case link:
                returnXpath = "//a[//text()]";
                break;
            case checkbox:
                returnXpath = "//*[./input[@type='checkbox'][//@*]]/ins";
                break;
            case textbox:
                returnXpath = "//input[@*]";
                break;
            case radio:
                returnXpath = "//*[./input[@type='radio'][//@*]]/ins";
                break;
            case dropdown:
                returnXpath = "//select[//@*]";
                break;
            case table:
                returnXpath = "//table[//@*]";
                break;
            default:
                returnXpath = "//button[//text()]";
        }
        return returnXpath;
    }
}
