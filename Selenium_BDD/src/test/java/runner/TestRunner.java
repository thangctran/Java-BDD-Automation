package runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import drivers.Driver;
import keywords.WebUI;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"pages", "runner"},
        plugin = { "pretty", "html:src/test/resources/reports/cucumber-html-reports"}
)

public class TestRunner {
    @BeforeClass
    public static void beforeSuite() {
        Driver.setDriver(Driver.setSeleniumDrivers());
    }

    @AfterClass
    public static void afterSuite() {
        WebUI.closeDriver();
    }

}
