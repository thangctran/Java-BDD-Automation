package pages;

import common.Common;
import utilities.Enums;
import java.util.List;

public class BasePages extends Common {
    static String mnuMenuContent = "(//ul[contains(@*,'sidebar')])[last()]/li[.//*[contains(//text(),'')]]/a";
    static String mnuSubMenu = "//*[./*[@aria-expanded='true']]//li/a[//text()]";
    static String sldPriceRange = "//div[@class='slider-track']";
    static String sldRound = "//div[@class='slider-handle round']";
    static String btnAddPhotos = "//a[@aria-controls='UploadPhotos']";
    static String lblDropFiles = "//span[@class='drop']";
    static String lstSearch = "//div[@class='xcrud-nav']//*[//text()][@class]";
    static String rdoStartGrade = "//*[./input[@name='stars']]";
    static String tblTable = getXpathElement(Enums.ELEMENT_TYPE.table);
    static String lnkLink = getXpathElement(Enums.ELEMENT_TYPE.link);
    static String txtTextbox = getXpathElement(Enums.ELEMENT_TYPE.textbox);
    static String ddlDropDownList = getXpathElement(Enums.ELEMENT_TYPE.dropdown);
    static String chkCheckbox = getXpathElement(Enums.ELEMENT_TYPE.checkbox);
    static String rdoRadio = getXpathElement(Enums.ELEMENT_TYPE.radio);
    static String btnButton = getXpathElement(Enums.ELEMENT_TYPE.button);

    public static void goToFuction(String menuMain, String subMenu) {
        click(mnuMenuContent, menuMain);
        if(subMenu != null) click(mnuSubMenu, subMenu);
    }

    public static void deleteRowByButton(String rowIndex) {
        selectCheckboxOnTable(tblTable, null,"1", rowIndex, true);
        click(lnkLink, " Delete Selected");
        alertDialog("accept");
    }

    public static void uploadGallery(String rowIndex, String imageUpload) {
        clickCellOnTable(tblTable, null, "Gallery", rowIndex);
        click(btnAddPhotos, null);
        click(lblDropFiles, null);
        typeKeysByRobot(imageUpload);
    }

    public static void searchOnRecordsOnTable(String text, String field) {
        scrollToElement(lstSearch, "Search");
        click(lstSearch, "Search");
        setText(txtTextbox, "phrase", text);
        selectOptionByText(ddlDropDownList, "column", field);
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

    static void filerSearch(String startGrade, List<String> listPropertyTypes, List<String> listAmenities, String tour_Car_Type, String airportPickup ) {
        click(rdoStartGrade, startGrade);
        if(listPropertyTypes != null) {
            for(String itemType : listPropertyTypes) {
                selectCheckbox(chkCheckbox, itemType, true);
            }
        }
        if(listAmenities != null) {
            for(String itemAmenities : listAmenities) {
                selectCheckbox(chkCheckbox, itemAmenities, true);
            }
        }
        if(tour_Car_Type != null) click(rdoRadio, tour_Car_Type);
        if(airportPickup != null) selectOptionByText(ddlDropDownList, "pickup" , airportPickup);
        click(btnButton, "Search");
    }
}