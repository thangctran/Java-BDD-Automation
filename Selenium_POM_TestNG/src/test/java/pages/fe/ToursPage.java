package pages.fe;

import commons.Commons;
import keywords.WebUI;

public class ToursPage {
    static String lstTourType = "//*[./strong[text()='Tour Type']]/a[1]";

    public static void filerSearch(String startGrade, String tourType){
        Commons.filerSearch(startGrade, null, null, tourType, null );
    }

    public static void setPriceRange(String from, String to) {
        Commons.setPriceRange(from, to);
    }

    public static void verifyTourType(String expectedTourType) {
        WebUI.verifyAttributeOnList(lstTourType, null, "textContent", expectedTourType);
    }
}
