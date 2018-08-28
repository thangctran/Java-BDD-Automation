package commons;

import java.util.List;
import keywords.WebUI;
import constants.Controls;

public class Commons {
    final static String txtEmail = "//input[@placeholder='Email'][not(@id)]";
    final static String txtPassword = "//input[@name='password']";
    final static String btnLogin = "//button[@type='submit'][contains(@class,'btn-block l')]";
    final static String sldPriceRange = "//div[@class='slider-track']";
    final static String sldRound = "//div[@class='slider-handle round']";
    final static String btnAddPhotos = "//a[@aria-controls='UploadPhotos']";
    final static String lblDropFiles = "//span[@class='drop']";
    final static String lstSearch = "//div[@class='xcrud-nav']//*[//text()][@class]";
    final static String rdoStartGrade = "//*[./input[@name='stars']]";

    public static void login(String url, String email, String password) {
        if(url != null) WebUI.navigateURL(url);
        if(email != null) WebUI.setText(txtEmail, null, email);
        if(password != null) WebUI.setText(txtPassword, null, password);
        WebUI.click(btnLogin, null);
    }

    public static String deleteRowByButton(String rowIndex, String columnName) {
        CustomWebUI.selectCheckboxOnTable(Controls.table, null,"1", rowIndex, true);
        List<String> listName = CustomWebUI.getCellValuesOnTable(Controls.table, null, columnName, rowIndex);
        WebUI.click(Controls.link, " Delete Selected");
        WebUI.acceptAlert();
        return listName.get(0);
    }

    public static String deleteRowByIcon(String rowIndex, String columnName) {
        List<String> listName = CustomWebUI.getCellValuesOnTable(Controls.table, null, columnName, rowIndex);
        CustomWebUI.clickIconOnTable(Controls.table, null, rowIndex, "DELETE");
        WebUI.acceptAlert();
        return listName.get(0);
    }

    public static void uploadGallery(String rowIndex, String imageUpload) {
        CustomWebUI.clickCellOnTable(Controls.table, null, "Gallery", rowIndex);
        WebUI.click(btnAddPhotos, null);
        WebUI.upLoadFile(lblDropFiles, null, imageUpload);
    }

    public static void searchRecordsOnTable(String text, String field) {
        WebUI.scrollToElement(lstSearch, "Search");
        WebUI.click(lstSearch, "Search");
        WebUI.setText(Controls.textbox, "phrase", text);
        WebUI.selectOptionByText(Controls.dropdown, "column", field);
        WebUI.click(lstSearch, "Go");
    }

    public static void setPriceRange(Integer from, Integer to) {
        int sliderWidth = WebUI.getWidth(sldPriceRange, null);
        int posi = 0;
        if(from != null){
            posi = (int)(sliderWidth * from / 100);
            WebUI.dragAndDrop(sldRound, "1", sldPriceRange, null, posi, 2);
        }
        if(to != null){
            posi = (int)(sliderWidth * to / 100);
            WebUI.dragAndDrop(sldRound, "2", sldPriceRange, null, posi, 2);
        }
    }

    public static void filerSearch(String startGrade, List<String> listPropertyTypes, List<String> listAmenities, String tour_Car_Type, String airportPickup ) {
        WebUI.click(rdoStartGrade, startGrade);
        if(listPropertyTypes != null) {
            for(String itemType : listPropertyTypes) {
                WebUI.selectCheckbox(Controls.checkbox, itemType, true);
            }
        }
        if(listAmenities != null) {
            for(String itemAmenities : listAmenities) {
                WebUI.selectCheckbox(Controls.checkbox, itemAmenities, true);
            }
        }
        if(tour_Car_Type != null) WebUI.click(Controls.radio, tour_Car_Type);
        if(airportPickup != null) WebUI.selectOptionByText(Controls.dropdown, "pickup" , airportPickup);
        WebUI.click(Controls.button, "Search");
    }
}