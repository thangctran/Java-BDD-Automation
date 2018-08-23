package tests;

import java.util.Arrays;
import java.util.List;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import constants.GolobalVariabes;
import drivers.SeleniumDrivers;
import elements.UIElements;
import pages.MainPage;
import pages.fe.HotelsPage;
import utilities.Utility;

public class HotelsTest {
    @BeforeSuite
    public void startTestSuite(){
        UIElements.setDriver(SeleniumDrivers.setSeleniumDrivers());
    }

    @AfterSuite
    public void endTestSuite(){
        UIElements.closeDriver();
    }

    @Test(priority = 0, description = "FE005-Hotels - Verify Hotel Filter")
    public void FE005_Hotels() {
        Utility.logInfo("TESTCASE", "FE005-Hotels - Verify Hotel Filter", 1);
        //set variable
        String startNumber = "4";
        List<String> listPropertyTypes = Arrays.asList("Hotel", "Villa");
        List<String> listAmenities = Arrays.asList("Night Club", "Restaurant");

        Utility.logInfo("STEP", "Navigate to url 'https://www.phptravels.net/hotels'", 1);
        MainPage.navigateURL(GolobalVariabes.urlHotels);

        Utility.logInfo("STEP", " Price range: From 0 to 40", 1);
        HotelsPage.setPriceRange(0, 40);

        Utility.logInfo("STEP", "Filter Search: Star grade; Property Types and Amenities", 1);
        HotelsPage.filerSearch(startNumber, listPropertyTypes, listAmenities);

        Utility.logInfo("STEP", "Number Start of each Hotel is from 4 to 5 starts", 1);
        HotelsPage.verifyNumberStartHotels(startNumber);
    }
}
