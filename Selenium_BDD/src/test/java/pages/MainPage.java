package pages;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import commons.Commons;
import constants.GolobalVariables;
import helpers.FileHelper;
import keywords.WebUI;
import utilities.Variables;

public class MainPage {
    @Given("^User uses test data: CSV files \"(.*)\", delimiter \"(.*)\", at row \"(.*)\"$")
    public void useTestData(String csvFile, String delimiter, String rowIndex) {
        Variables.testData = FileHelper.getTestDataCSV(csvFile, delimiter, Integer.valueOf(rowIndex));
    }

    @Given("^User navigates to \"(.*)\" page$")
    public void navigateURL(String pageName) {
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

    @When("^User select main-menu \"(.*)\" -> sub-menu \"(.*)\"$")
    public void goToFunction(String mainMenu, String subMenu) {
        Commons.goToFunction(mainMenu, subMenu);
    }
}
