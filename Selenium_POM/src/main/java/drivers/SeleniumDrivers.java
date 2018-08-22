package drivers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.WebDriver;
import constains.Environemnts;
import helpers.FileHelper;

public class SeleniumDrivers {

    // this is our driver that will be used for all selenium actions
    public static WebDriver setSeleniumDrivers() {
        String browser = FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);
        int waitTime = Integer.parseInt(FileHelper.getXmlNodeValue("//Configuration/LoadTimeOut/text()",0));
        // open a new driver instance to our application URL
        WebDriver driver;
        switch (browser.toLowerCase()) { // check our browser
            case "firefox": {
                System.setProperty("webdriver.firefox.marionette", Environemnts.DRIVER_PATH + "\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            }
            case "edge": {
                System.setProperty("webdriver.edge.driver", Environemnts.DRIVER_PATH + "\\/MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            }
            case "ie": {
                System.setProperty("webdriver.ie.driver", Environemnts.DRIVER_PATH + "\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            }
            case "safari": {
                driver = new SafariDriver();
                break;
            }
            // if our browser is not listed, throw an error
            default: {
                System.setProperty("webdriver.chrome.driver", Environemnts.DRIVER_PATH + "\\chromedriver.exe");
                driver = new ChromeDriver();
            }
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

}
