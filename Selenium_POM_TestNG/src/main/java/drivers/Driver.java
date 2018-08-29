package drivers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import constants.Environemnts;
import helpers.FileHelper;
import utilities.Utility;

public class Driver {
    static WebDriver driver;

    public static void setDriver(WebDriver driverTest) {
        if (driver == null) driver = driverTest;
    }

    public static WebDriver getDriver() {
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

    public static WebElement findElement(String description, String strXpath, String dynamicValue){
        WebElement element;
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        try {
            Utility.logInfo("INFO", String.format("%s :: [%s]", description, newXpath), 1);
            element = driver.findElement(By.xpath(newXpath));
        } catch (Exception e){
            Utility.logInfo("ERROR", String.format("%s :: Not Found[%s]", description, newXpath), 1);
            return null;
        }
        return element;
    }
}
