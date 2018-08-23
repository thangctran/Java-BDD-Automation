package pages.be;

import java.util.List;
import commons.Commons;
import constants.Controls;
import utilities.Enums;
import utilities.Utility;

public class HotelsManagementPage {
    public static String deleteHotelByButton(String rowIndex) {
        return Commons.deleteRowByButton(rowIndex, "Name");
    }

    public static String deleteHotelByIcon(String rowIndex) {
        return Commons.deleteRowByIcon(rowIndex, "Name");
    }

    public static void verifyHotelNameDeleted(String rowIndex, String hotelName) {
        List<String> listName = Commons.getCellValuesOnTable(Controls.table, null, "Name", rowIndex);
        Utility.verifyValues("verifyHotelNameDeleted", listName.get(0), hotelName, Enums.OPERATOR.notEqual);
    }

    public static int getImageNumberUpload(String rowIndex) {
        List<String> listGallery = Commons.getCellValuesOnTable(Controls.table, null,"Gallery", rowIndex);
        return Utility.getNumericInString(listGallery.get(0));
    }

    public static void verifyImageNumberUploaded(String rowIndex, int expectedImageNumber) {
        int currentNumber = getImageNumberUpload(rowIndex);
        Utility.verifyValues("verifyImageNumberUploaded", String.valueOf(currentNumber), String.valueOf(expectedImageNumber), Enums.OPERATOR.equal);
    }

    public static void uploadGallery(String rowIndex, String imageUpload) {
        Commons.uploadGallery(rowIndex, imageUpload);
    }

    public static void searchRecordsOnTable(String text, String field) {
        Commons.searchRecordsOnTable (text, field);
    }
}
