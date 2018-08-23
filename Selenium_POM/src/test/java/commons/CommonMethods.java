package commons;

import java.util.List;
import pages.BasePages;
import constants.Controls;

public class CommonMethods extends BasePages {
    
    static String sldPriceRange = "//div[@class='slider-track']";
    static String sldRound = "//div[@class='slider-handle round']";
    static String btnAddPhotos = "//a[@aria-controls='UploadPhotos']";
    static String lblDropFiles = "//span[@class='drop']";
    static String lstSearch = "//div[@class='xcrud-nav']//*[//text()][@class]";
    static String rdoStartGrade = "//*[./input[@name='stars']]";

    public void deleteRowByButton(String rowIndex) {
        selectCheckboxOnTable(Controls.table, null,"1", rowIndex, true);
        click(Controls.link, " Delete Selected");
        alertDialog("accept");
    }

    public static void deleteRowByIcon(String rowIndex) {
        clickIconOnTable(Controls.table, null, rowIndex, "DELETE");
        click(Controls.link, " Delete Selected");
        alertDialog("accept");
    }

    public static void uploadGallery(String rowIndex, String imageUpload) {
        clickCellOnTable(Controls.table, null, "Gallery", rowIndex);
        click(btnAddPhotos, null);
        click(lblDropFiles, null);
        typeKeysByRobot(imageUpload);
    }

    public static void searchOnRecordsOnTable(String text, String field) {
        scrollToElement(lstSearch, "Search");
        click(lstSearch, "Search");
        setText(Controls.textbox, "phrase", text);
        selectOptionByText(Controls.dropdown, "column", field);
        click(lstSearch, "Go");
    }

    public static void setPriceRange(Integer from, Integer to) {
        int sliderWitdth = getWidth(sldPriceRange, null);
        int posi = 0;
        if(from != null){
            posi = (int)(sliderWitdth * from / 100);
            dragAndDrop(sldRound, "1", sldPriceRange, null, posi, 2);
        }
        if(to != null){
            posi = (int)(sliderWitdth * to / 100);
            dragAndDrop(sldRound, "2", sldPriceRange, null, posi, 2);
        }
    }

    public static void filerSearch(String startGrade, List<String> listPropertyTypes, List<String> listAmenities, String tour_Car_Type, String airportPickup ) {
        click(rdoStartGrade, startGrade);
        if(listPropertyTypes != null) {
            for(String itemType : listPropertyTypes) {
                selectCheckbox(Controls.checkbox, itemType, true);
            }
        }
        if(listAmenities != null) {
            for(String itemAmenities : listAmenities) {
                selectCheckbox(Controls.checkbox, itemAmenities, true);
            }
        }
        if(tour_Car_Type != null) click(Controls.radio, tour_Car_Type);
        if(airportPickup != null) selectOptionByText(Controls.dropdown, "pickup" , airportPickup);
        click(Controls.button, "Search");
    }
}
