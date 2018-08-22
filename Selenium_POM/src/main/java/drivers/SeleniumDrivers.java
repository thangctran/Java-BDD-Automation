package drivers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.WebDriver;

public class SeleniumDrivers {

    // this is our driver that will be used for all selenium actions
    public static WebDriver setSeleniumDrivers(String browser, int waitTime) {

        // open a new driver instance to our application URL
        WebDriver driver;
        switch (browser.toLowerCase()) { // check our browser
            case "firefox": {
                System.setProperty("webdriver.firefox.marionette", "drivers//geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            }
            case "edge": {
                System.setProperty("webdriver.edge.driver", "drivers//MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            }
            case "ie": {
                System.setProperty("webdriver.ie.driver", "drivers//IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            }
            case "safari": {
                driver = new SafariDriver();
                break;
            }
            // if our browser is not listed, throw an error
            default: {
                System.setProperty("webdriver.chrome.driver","drivers//chromedriver.exe");
                driver = new ChromeDriver();
            }
        }
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}
