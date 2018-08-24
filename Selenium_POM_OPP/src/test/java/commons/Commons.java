package commons;

import java.util.List;
import pages.BasePages;
import constants.Controls;
import drivers.KWDriver;
import utilities.Enums.METHOD_ELEMENT;
import utilities.Enums.METHOD_DRIVER;
import utilities.Utility;

public class Commons extends BasePages {
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
        if(url != null) KWDriver.action(METHOD_DRIVER.get, url).get(url);
        if(email != null)setText(txtEmail, null, email);
        if(password != null) setText(txtPassword, null, password);
        click(btnLogin, null);
    }

    public static String deleteRowByButton(String rowIndex, String columnName) {
        selectCheckboxOnTable(Controls.table, null,"1", rowIndex, true);
        List<String> listName = getCellValuesOnTable(Controls.table, null, columnName, rowIndex);
        click(Controls.link, " Delete Selected");
        KWDriver.action(METHOD_DRIVER.acceptAlert, null).switchTo().alert().accept();
        Utility.delay(2);
        return listName.get(0);
    }

    public static String deleteRowByIcon(String rowIndex, String columnName) {
        List<String> listName = getCellValuesOnTable(Controls.table, null, columnName, rowIndex);
        clickIconOnTable(Controls.table, null, rowIndex, "DELETE");
        KWDriver.action(METHOD_DRIVER.acceptAlert, null).switchTo().alert().accept();
        Utility.delay(2);
        return listName.get(0);
    }

    public static void uploadGallery(String rowIndex, String imageUpload) {
        clickCellOnTable(Controls.table, null, "Gallery", rowIndex);
        click(btnAddPhotos, null);
        click(lblDropFiles, null);
        typeKeysByRobot(imageUpload);
    }

    public static void searchRecordsOnTable(String text, String field) {
        scrollToElement(lstSearch, "Search");
        click(lstSearch, "Search");
        setText(Controls.textbox, "phrase", text);
        selectOptionByText(Controls.dropdown, "column", field);
        click(lstSearch, "Go");
    }

    public static void setPriceRange(Integer from, Integer to) {
        int sliderWidth = action(METHOD_ELEMENT.getWidth, null, sldPriceRange, null).getSize().getWidth();
        int posi = 0;
        if(from != null){
            posi = (int)(sliderWidth * from / 100);
            dragAndDrop(sldRound, "1", sldPriceRange, null, posi, 2);
        }
        if(to != null){
            posi = (int)(sliderWidth * to / 100);
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
