package tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import constants.GolobalVariabes;
import helpers.FileHelper;
import pages.fe.CarsPage;
import pages.fe.HotelsPage;
import pages.fe.ToursPage;
import drivers.Driver;
import keywords.WebUI;
import utilities.Utility;

public class FilterSearchTest {
    @BeforeSuite
    public void startTestSuite(){
        Driver.setDriver(Driver.setSeleniumDrivers());
    }

    @AfterSuite
    public void endTestSuite(){
        WebUI.closeDriver();
    }

    @Test(priority = 0, description = "FE002-Tours - Verify Tours Filter")
    public void FE002_Tours() {
        //set variable
        Map<String, String> data = FileHelper.getTestDataCSV("fe\\FE002.csv", ",", 1);

        Utility.logInfo("STEP", "Navigate to url 'https://www.phptravels.net/tours'", 1);
        WebUI.navigateURL(GolobalVariabes.urlTours);

        Utility.logInfo("STEP", " Price range: From 0 to 60", 1);
        ToursPage.setPriceRange(data.get("priceFrom"), data.get("priceTo"));

        Utility.logInfo("STEP", "Filter Search: Star grade: 3; Tour Types: Holidays", 1);
        ToursPage.filerSearch(data.get("star"), data.get("tourType"));

        Utility.logInfo("STEP", "Tour Type of each Hotels is 'Holidays", 1);
        ToursPage.verifyTourType(data.get("tourType"));
    }


    @Test(priority = 0, description = "FE003-Cars - Verify Cars Filter")
    public void FE003_Cars() {
        //set variable
        Map<String, String> data = FileHelper.getTestDataCSV("fe\\FE003.csv", ",", 1);

        Utility.logInfo("STEP", "Navigate to url 'https://www.phptravels.net/cars'", 1);
        WebUI.navigateURL(GolobalVariabes.urlCars);

        Utility.logInfo("STEP", "Filter Search: Star grade: 4; Car Types: Standard; Airport Pickup: Yes", 1);
        CarsPage.filerSearch(data.get("star"), data.get("carType"), data.get("airportPickup"));

        Utility.logInfo("STEP", "Airport Pickup displays for each Casr with green backgourd-color", 1);
        CarsPage.verifyGreenAirportPickupButton();
    }

    @Test(priority = 0, description = "FE005-Hotels - Verify Hotel Filter")
    public void FE005_Hotels() {
        //set variable
        Map<String, String> data = FileHelper.getTestDataCSV("fe\\FE005.csv", "\t", 1);
        List<String> listPropertyTypes = Utility.convertStringToList(data.get("propertyTypes"), ",");
        List<String> listAmenities = Utility.convertStringToList(data.get("amenities"), ",");

        Utility.logInfo("STEP", "Navigate to url 'https://www.phptravels.net/hotels'", 1);
        WebUI.navigateURL(GolobalVariabes.urlHotels);

        Utility.logInfo("STEP", " Price range: From 0 to 40", 1);
        HotelsPage.setPriceRange(data.get("priceFrom"), data.get("priceTo"));

        Utility.logInfo("STEP", "Filter Search: Star grade; Property Types and Amenities", 1);
        HotelsPage.filerSearch(data.get("star"), listPropertyTypes, listAmenities);

        Utility.logInfo("STEP", "Number Start of each Hotel is from 4 to 5 starts", 1);
        HotelsPage.verifyNumberStartHotels(data.get("star"));
    }
}
