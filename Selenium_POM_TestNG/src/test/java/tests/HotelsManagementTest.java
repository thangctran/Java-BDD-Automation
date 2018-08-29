package tests;

import drivers.Driver;
import keywords.WebUI;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import constants.Environemnts;
import constants.GolobalVariabes;
import pages.be.HotelsManagementPage;
import pages.be.LoginBEPage;
import pages.be.MainBEPage;
import utilities.Utility;

public class HotelsManagementTest {

    @BeforeSuite
    public void startTestSuite(){
        Driver.setDriver(Driver.setSeleniumDrivers());
    }

    @AfterSuite
    public void endTestSuite(){
        WebUI.closeDriver();
    }

    @Test(priority = 0, description = "BE006-Hotels-Upload gallery")
    public void BE006_Hotels() {
          //set variables
        String rowIndex = String.valueOf(Utility.getRandomInt(1,9));
        String imageUpload = Environemnts.DATA_PATH + "Image.PNG";

        Utility.logInfo("STEP", "Navigate and login as Admin to page https://www.phptravels.net/admin", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, GolobalVariabes.emailBE, GolobalVariabes.passwordBE);

        Utility.logInfo("STEP", "Select HOTELS -> HOTELS", 1);
        MainBEPage.goToFunction("Hotels", "Hotels");

        Utility.logInfo("STEP", "Upload image to a Hotel record", 1);
        int imageNumberBefore = HotelsManagementPage.getImageNumberUpload(rowIndex);
        HotelsManagementPage.uploadGallery(rowIndex, imageUpload);

        Utility.logInfo("STEP", "Go to Hotels Management again", 1);
        MainBEPage.goToFunction("Hotels", "Hotels");

        Utility.logInfo("STEP", "Verify image is uploaded for this hotel", 1);
        HotelsManagementPage.verifyImageNumberUploaded(rowIndex, imageNumberBefore + 1);
    }

    @Test(priority = 0, description = "BE007-Hotels-Delete Hotels by icon")
    public void BE007_Hotels() {
        //set variables
        String rowIndex = String.valueOf(Utility.getRandomInt(1, 9));

        Utility.logInfo("STEP", "Navigate and login as Admin to page https://www.phptravels.net/admin", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, GolobalVariabes.emailBE, GolobalVariabes.passwordBE);

        Utility.logInfo("STEP", "Select HOTELS -> HOTELS", 1);
        MainBEPage.goToFunction("Hotels", "Hotels");

        Utility.logInfo("STEP", "Delete Hotel by Delete Icon", 1);
        String hotelNameDelete = HotelsManagementPage.deleteHotelByIcon(rowIndex);

        Utility.logInfo("STEP", "Verify the Hotel is deleted on list.", 1);
        HotelsManagementPage.verifyHotelNameDeleted(rowIndex, hotelNameDelete);
    }

    @Test(priority = 0, description = "BE008-Hotels-Delete Hotels by Delete Selected button")
    public void BE008_Hotels() {
        //set variables
        String rowIndex = String.valueOf(Utility.getRandomInt(1, 9));

        Utility.logInfo("STEP", "Navigate and login as Admin to page https://www.phptravels.net/admin", 1);
        LoginBEPage.login(GolobalVariabes.urlBE, GolobalVariabes.emailBE, GolobalVariabes.passwordBE);

        Utility.logInfo("STEP", "Select HOTELS -> HOTELS", 1);
        MainBEPage.goToFunction("Hotels", "Hotels");

        Utility.logInfo("STEP", "Delete Hotel by Delete Selected button", 1);
        String hotelNameDelete = HotelsManagementPage.deleteHotelByButton(rowIndex);

        Utility.logInfo("STEP", "Verify the Hotel is deleted on list.", 1);
        HotelsManagementPage.verifyHotelNameDeleted(rowIndex, hotelNameDelete);
    }
}
