package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import commons.Commons;
import constants.GolobalVariables;
import helpers.FileHelper;
import keywords.WebUI;
import utilities.Utility;
import utilities.Variables;

public class MainPage {

    @Given("^User uses test data: CSV files \"(.*)\", delimiter \"(.*)\", at row \"(.*)\"$")
    public void useTestData(String csvFile, String delimiter, String rowIndex) {
        Variables.testData = FileHelper.getTestDataCSV(csvFile, delimiter, Integer.valueOf(rowIndex));
    }

    @Given("^User navigates to \"(.*)\" page$")
    public void navigateURL(String pageName) {
        Utility.logInfo("STEP", "User navigates to " + pageName + " page", 1);
        String url ;
        switch(pageName.toLowerCase()) {
            case "cars" :
                url = GolobalVariables.urlCars;
                break;
            case "blog" :
                url = GolobalVariables.urlBlog;
                break;
            case "flights" :
                url = GolobalVariables.urlFlights;
                break;
            case "hotels" :
                url = GolobalVariables.urlHotels;
                break;
            case "login" :
                url = GolobalVariables.urlLogin;
                break;
            case "tours" :
                url = GolobalVariables.urlTours;
                break;
            case "visa" :
                url = GolobalVariables.urlVisa;
                break;
            case "front-end" :
                url = GolobalVariables.urlFE;
                break;
            default:
                url = GolobalVariables.urlBE;
        }
        WebUI.navigateURL(url);
    }

    @When("^User set Price Range to filter$")
    public void setPriceRange() {
        String from = Variables.testData.get("priceFrom");
        String to = Variables.testData.get("priceTo");
        Utility.logInfo("STEP", "User set Price Range (" + from + " , " + to + ") to filter", 1);
        Commons.setPriceRange(from, to);
    }

    @When("^User select main-menu \"(.*)\" -> sub-menu \"(.*)\"$")
    public void goToFunction(String mainMenu, String subMenu) {
        Utility.logInfo("STEP", "User select main-menu " + mainMenu + " -> sub-menu " + subMenu, 1);
        Commons.goToFunction(mainMenu, subMenu);
    }
}
