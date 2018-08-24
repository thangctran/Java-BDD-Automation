package drivers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import utilities.Enums;
import utilities.Utility;
import constants.Environemnts;
import helpers.FileHelper;

public class KWDriver{
    static WebDriver driver;

    public static void setDriver(WebDriver driverTest) {
        if (driver == null) driver = driverTest;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver action(Enums.METHOD_DRIVER actionName, String description){
        String tempDescription = "";
        if(description != null) tempDescription = " [" + description + "] ";
        Utility.logInfo("INFO", String.format("Driver: %s ::%s", actionName.toString(), tempDescription), 1);
        return driver;
    }

    public static WebDriver setSeleniumDrivers() {
        String browser = FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);
        int waitTime = Integer.parseInt(FileHelper.getXmlNodeValue("//Configuration/LoadTimeOut/text()",0));
        // open a new driver instance to our application URL
        WebDriver driverTest;
        switch (browser.toLowerCase()) { // check our browser
            case "firefox": {
                System.setProperty("webdriver.firefox.marionette", Environemnts.DRIVER_PATH + "\\geckodriver.exe");
                driverTest = new FirefoxDriver();
                break;
            }
            case "edge": {
                System.setProperty("webdriver.edge.driver", Environemnts.DRIVER_PATH + "\\/MicrosoftWebDriver.exe");
                driverTest = new EdgeDriver();
                break;
            }
            case "ie": {
                System.setProperty("webdriver.ie.driver", Environemnts.DRIVER_PATH + "\\IEDriverServer.exe");
                driverTest = new InternetExplorerDriver();
                break;
            }
            case "safari": {
                driverTest = new SafariDriver();
                break;
            }
            // if our browser is not listed, throw an error
            default: {
                System.setProperty("webdriver.chrome.driver", Environemnts.DRIVER_PATH + "\\chromedriver.exe");
                driverTest = new ChromeDriver();
            }
        }
        driverTest.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        driverTest.manage().window().maximize();
        return driverTest;
    }

    public static void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Utility.verifyValues("KWDriver: verifyTitle", actualTitle, expectedTitle, Enums.OPERATOR.equal);
    }

    public static void verifyURL(String expectedURL) {
        String actualURL = driver.getCurrentUrl();
        Utility.verifyValues("KWDriver: verifyURL", actualURL, expectedURL, Enums.OPERATOR.equal);
    }

    public static void switchToWindowTitle(String title) {
        Utility.logInfo("INFO", "KWDriver: switchToWindowTitle :: [" + title + "]", 1);
        try{
            boolean bFind = false;
            for(int i=driver.getWindowHandles().size() - 1; i>=0 ; i--){
                driver.switchTo().window(driver.getWindowHandles().toArray()[i].toString());
                if (title.equals(driver.getTitle())){
                    bFind = true;
                    break;
                }
            }
            if(!bFind) driver.switchTo().window(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeDriver() {
        Utility.logInfo("INFO", "KWDriver: closeDriver", 1);
        driver.close();
        driver.quit();
        driver = null;
    }
}
