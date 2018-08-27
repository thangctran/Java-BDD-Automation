package keywords;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import drivers.Driver;
import utilities.Enums;
import utilities.Utility;

public class WebUI extends Driver {
    static WebDriver driver = Driver.getDriver();

    ///////////////////////////Driver keywords/////////////////////////////////
    public static void navigateURL(String URL) {
        Utility.logInfo("INFO", "navigateURL :: [" + URL + "]", 1);
        driver.get(URL);
    }
    
    public static void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Utility.verifyValues("verifyTitle", actualTitle, expectedTitle, Enums.OPERATOR.equal);
    }

    public static void verifyURL(String expectedURL) {
        String actualURL = driver.getCurrentUrl();
        Utility.verifyValues("verifyURL", actualURL, expectedURL, Enums.OPERATOR.equal);
    }

    public static void switchToWindowTitle(String title) {
        Utility.logInfo("INFO", "switchToWindowTitle :: [" + title + "]", 1);
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

    public static void acceptAlert(){
        Utility.logInfo("INFO", "alertDialog", 1);
        driver.switchTo().alert().dismiss();
        Utility.delay(2);
    }

    public static void dismissAlert(){
        Utility.logInfo("INFO", "dismissAlert ", 1);
        driver.switchTo().alert().dismiss();
        Utility.delay(2);
    }

    public static String getAlertMessage(){
        String message = driver.switchTo().alert().getText();
        Utility.logInfo("INFO", "getAlertMessage :: [" + message + "]", 0);
        return message;
    }

    public static void verifyAlertMessage(String expectedMessage) {
        String actualMessage = driver.getCurrentUrl();
        Utility.verifyValues("verifyAlertMessage", actualMessage, expectedMessage, Enums.OPERATOR.equal);
    }

    public static void closeDriver() {
        Utility.logInfo("INFO", "closeDriver", 1);
        driver.close();
        driver.quit();
        driver = null;
    }

    public static void switchToDefaultContent() {
        Utility.logInfo("INFO", "switchToDefaultContent ", 0);
        driver.switchTo().defaultContent();
    }

    public static void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public static void captureScreen(String pathFileName) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(pathFileName));
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }
    ///////////////////////////Element keywords/////////////////////////////////
    public static void waitForPresent(String strXpath, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            WebElement a = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
            Utility.logInfo("DEBUG", "waitForPresent :: appear [" + strXpath + "]", 0);
        } catch (Exception e) {
            Utility.logInfo("DEBUG", "waitForPresent :: disappear [" + strXpath + "]", 0);
        }
    }

    public static int getHeight(String strXpath, String dynamicValue) { ;
        return webElement("getHeight", strXpath, dynamicValue).getSize().getHeight();
    }

    public static int getWidth(String strXpath, String dynamicValue) {
        return webElement("getWidth", strXpath, dynamicValue).getSize().getWidth();
    }

    public static void scrollToElement(String strXpath, String dynamicValue) {
        WebElement element = webElement("scrollToElement", strXpath, dynamicValue);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void click(String strXpath, String dynamicValue) {
        webElement("click", strXpath, dynamicValue).click();
    }

    public static void clickAndWait(String strXpath, String dynamicValue, int waitSeconds) {
        webElement("click", strXpath, dynamicValue).click();
        Utility.delay(waitSeconds);
    }

    public static void clickMousePosition(String strXpath, String dynamicValue, Integer x, Integer y, Enums.CLICK_TYPE clickType) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        WebElement element = driver.findElement(By.xpath(newXpath));

        int eleWidth = Math.round(element.getSize().getWidth()/2);
        if(x != null) eleWidth = x;

        int eleHeight = Math.round(element.getSize().getHeight()/2);
        if(y != null) eleHeight = y;

        Utility.logInfo("INFO", "clickMousePosition :: [" + eleWidth + "," + eleHeight + "] ::[" + newXpath + "]", 1);
        Actions builder = new Actions(driver);
        //click on element
        switch(clickType) {
            case doubleClick:
                builder.moveToElement(element, eleWidth, eleHeight).doubleClick().build().perform();
                break;
            case mouseOver:
                builder.moveToElement(element, eleWidth, eleHeight).click().build().perform();
                break;
            case rightClick:
                builder.moveToElement(element, eleWidth, eleHeight).contextClick().build().perform();
                break;
            default:
                builder.moveToElement(element, eleWidth, eleHeight).build().perform();
        }
    }

    public static void dragAndDrop(String sourceXpath, String sourceDynamic, String targetXpath, String targetDynamic, Integer x, Integer y) {
        WebElement source = webElement("drag", sourceXpath, sourceDynamic);
        WebElement target = webElement("drop", targetXpath, targetDynamic);

        int eleWidth = Math.round(target.getSize().getWidth()/2);
        if(x != null) eleWidth = x;

        int eleHeight = Math.round(target.getSize().getHeight()/2);
        if(y != null) eleHeight = y;

        Actions builder = new Actions(driver);
        builder.clickAndHold(source).moveToElement(target, eleWidth, eleHeight).click().release(target).build().perform();
    }

    public static void selectCheckbox(String strXpath, String dynamicValue, boolean status) {
        WebElement element = webElement("selectCheckbox :: [" + status + "]", strXpath, dynamicValue);
        boolean currentStatus = element.isSelected();
        if(currentStatus != status) element.click();
    }

    public static void setText(String strXpath, String dynamicValue, String value) {
        webElement("setText :: [" + value + "]", strXpath, dynamicValue).sendKeys(value);
    }

    public static void pressKeys(String keys) {
        Utility.logInfo("INFO", "pressKeys :: [" + keys + "]", 1);
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(keys).build().perform();
    }

    public static void pressKeyByRobot(int keycode) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(300);
            robot.keyPress(keycode); // keycode = KeyEvent.VK_CONTROL
            robot.keyRelease(keycode);
            Thread.sleep(2000);
        } catch (Exception e){
            Utility.logInfo("ERROR", "pressKeyByRobot :: " + e.getMessage(), 1);
        }
    }

    public static void typeKeysByRobot(String keys) {
        Utility.logInfo("INFO", "typeKeysByRobot :: [" + keys + "]", 1);
        try {
            //copy keys to clipboard
            StringSelection stringSelection = new StringSelection(keys);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            //paste keys and enter
            Thread.sleep(2000);
            Robot robot = new Robot();
            robot.setAutoDelay(500);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        } catch (Exception e){
            Utility.logInfo("ERROR", "typeKeysByRobot :: " + e.getMessage(), 1);
        }
    }

    public static void upLoadFile(String strXpath, String dynamicValue, String imagePath) {
        webElement("upLoadFile", strXpath, dynamicValue).click();
        typeKeysByRobot(imagePath);
    }

    public static void selectOptionByText(String strXpath, String dynamicValue, String text) {
        WebElement element = webElement("selectOptionByText :: [" + text + "]", strXpath, dynamicValue);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(text);
    }

    public static void selectOptionByValue(String strXpath, String dynamicValue, String value) {
        WebElement element = webElement("selectOptionByValue :: [" + value + "]", strXpath, dynamicValue);
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
    }

    public static void selectOptionByIndex(String strXpath, String dynamicValue, int index) {
        WebElement element = webElement("selectOptionByIndex :: [" + index + "]", strXpath, dynamicValue);
        Select dropdown = new Select(element);
        dropdown.selectByIndex(index);
    }

    public static boolean isDisplayed(String strXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        boolean currentStatus = false;
        try {
            currentStatus = driver.findElement(By.xpath(newXpath)).isDisplayed();
        } catch (Exception e){
            currentStatus = false;
        }
        return currentStatus;
    }

    public static void verifyElementPresent(String strXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        boolean bCurrentStatus = isDisplayed(strXpath, dynamicValue);
        Utility.verifyValues("verifyElementPresent :: [" + newXpath + "]", String.valueOf(bCurrentStatus), "true", Enums.OPERATOR.equal);
    }

    public static String getAttribute(String strXpath, String dynamicValue, String attribute) {
        return webElement("getAttribute :: [" + attribute + "]", strXpath, dynamicValue).getAttribute(attribute).trim();
    }

    public static void verifyAttribute(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        String currentValue = driver.findElement(By.xpath(newXpath)).getAttribute(attribute).trim();
        Utility.verifyValues("verifyAttributeElement :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static List<String> getAttributeOnList(String strListXpath, String dynamicValue, String attribute) {
        List<String> returnValues = new ArrayList<String>();
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        for(WebElement webElement : wListRows) {
            returnValues.add(webElement.getAttribute(attribute).trim());
        }
        return returnValues;
    }

    public static void verifyAttributeOnList(String strListXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        String currentValue = "";
        for(WebElement webElement : wListRows) {
            currentValue = webElement.getAttribute(attribute).trim();
            if(!currentValue.contentEquals(expectedValue)) {
                break;
            }
        }
        Utility.verifyValues("verifyAttributeOnList :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static String getCssValue(String strXpath, String dynamicValue, String attribute) {
        return webElement("getCssValue :: [" + attribute + "]", strXpath, dynamicValue).getCssValue(attribute).trim();
    }

    public static void verifyCssValue(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        String currentValue = driver.findElement(By.xpath(newXpath)).getCssValue(attribute).trim();
        Utility.verifyValues("verifyCssValue :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static void verifyCssValueOnList(String strListXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        String currentValue = "";
        for(WebElement webElement : wListRows) {
            currentValue = webElement.getCssValue(attribute).trim();
            if(!currentValue.contentEquals(expectedValue)) {
                break;
            }
        }
        Utility.verifyValues("verifyCssValueOnList :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static int countItemsOnList(String strListXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        return driver.findElements(By.xpath(newXpath)).size();
    }
}
