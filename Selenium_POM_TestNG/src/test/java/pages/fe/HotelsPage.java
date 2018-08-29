package pages.fe;

import java.util.List;
import commons.Commons;
import commons.CustomWebUI;
import constants.Controls;

public class HotelsPage {

    public static void filerSearch(String startGrade, List<String> listPropertyTypes, List<String> listAmenities){
        Commons.filerSearch(startGrade, listPropertyTypes, listAmenities, null, null );
    }

    public static void setPriceRange(Integer from, Integer to) {
        Commons.setPriceRange(from, to);
    }

    public static void verifyNumberStartHotels(String expectedStart) {
        CustomWebUI.verifyStartOnTable(Controls.table, "bgwhite table table-striped", "1",null, expectedStart);
    }
}