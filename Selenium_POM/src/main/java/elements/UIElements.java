package elements;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import drivers.SeleniumDrivers;
import static utilities.Utility.*;
import utilities.Enums;

public class UIElements {
    final static WebDriver driver = SeleniumDrivers.setSeleniumDrivers("chrome", 20);

    public static WebElement convertToElement(String actionName, String strXpath, String dynamicValue, int logMode){
        WebElement element;
        String newXpath = convertXpath(strXpath, dynamicValue);

        try {
            logInfo("INFO", String.format("%s :: [%s]", actionName, newXpath), logMode);
            element = driver.findElement(By.xpath(newXpath));
        } catch (Exception e){
            logInfo("WARN", String.format("%s :: Not Found[%s]", actionName, newXpath), 1);
            return null;
        }
        return element;
    }

    public static void waitForPresent(String strXpath, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            WebElement a = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
            logInfo("DEBUG", "waitForPresent :: appear [" + strXpath + "]", 0);
        } catch (Exception e) {
            logInfo("DEBUG", "waitForPresent :: disappear [" + strXpath + "]", 0);
        }
    }

    public static Integer getHeight(String strXpath, String dynamicValue) {
        WebElement element = convertToElement("getHeight", strXpath, dynamicValue, 0);
        return element.getSize().getHeight();
    }

    public static Integer getWidth(String strXpath, String dynamicValue) {
        WebElement element = convertToElement("getWidth", strXpath, dynamicValue, 0);
        return element.getSize().getWidth();
    }

    public static void scrollToElement(String strXpath, String dynamicValue) {
        WebElement element = convertToElement("scrollToElement", strXpath, dynamicValue, 0);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void click(String strXpath, String dynamicValue) {
        WebElement element = convertToElement("click", strXpath, dynamicValue, 1);
        element.click();
    }

    public static void clickMousePosition(String strXpath, String dynamicValue, Integer x, Integer y, Enums.CLICK_TYPE clickType) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        WebElement element = driver.findElement(By.xpath(newXpath));

        int eleWidth = Math.round(element.getSize().getWidth()/2);
        if(x != null) eleWidth = x;

        int eleHeight = Math.round(element.getSize().getHeight()/2);
        if(y != null) eleHeight = y;

        logInfo("INFO", "clickMousePosition :: [" + eleWidth + "," + eleHeight + "] ::[" + newXpath + "]", 1);
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
        String newSourceXpath = convertXpath(sourceXpath, sourceDynamic);
        WebElement source = driver.findElement(By.xpath(newSourceXpath));

        String newTargetXpath = convertXpath(targetXpath, targetDynamic);
        WebElement target = driver.findElement(By.xpath(newTargetXpath));

        int eleWidth = Math.round(target.getSize().getWidth()/2);
        if(x != null) eleWidth = x;

        int eleHeight = Math.round(target.getSize().getHeight()/2);
        if(y != null) eleHeight = y;

        logInfo("INFO", "dragAndDrop ::[" + newSourceXpath + "] to [" + newTargetXpath + "]:[" + eleWidth + "," + eleHeight + "]", 1);
        Actions builder = new Actions(driver);
        builder.clickAndHold(source).moveToElement(target, eleWidth, eleHeight).click().release(target).build().perform();
    }

    public static void selectCheckbox(String strXpath, String dynamicValue, boolean status) {
        WebElement element = convertToElement("selectCheckbox :: [" + status + "]", strXpath, dynamicValue, 1);
        boolean currentStatus = element.isSelected();
        if(currentStatus != status) element.click();
    }

    public static void setText(String strXpath, String dynamicValue, String value) {
        WebElement element = convertToElement("setText :: [" + value + "]", strXpath, dynamicValue, 1);
        element.sendKeys(value);
    }

    public static void pressKeys(String keys) {
        logInfo("INFO", "pressKeys :: [" + keys + "]", 1);
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(keys).build().perform();
    }

    public static void pressControlV() {
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(Keys.CONTROL + "v").build().perform();
    }

    public static void typeKeysByRobot(String keys) {
        logInfo("INFO", "typeKeysByRobot :: [" + keys + "]", 1);
        try {
            //copy keys to clipboard
            StringSelection stringSelection = new StringSelection(keys);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            //paste keys and enter
            Thread.sleep(2000);
            Robot robot = new Robot();
            robot.setAutoDelay(300);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        } catch (Exception e){
            logInfo("ERROR", "typeKeysByRobot :: " + e.getMessage(), 1);
        }
    }

    public static void selectOptionByText(String strXpath, String dynamicValue, String text) {
        WebElement element = convertToElement("selectOptionByText :: [" + text + "]", strXpath, dynamicValue, 1);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(text);
    }

    public static void selectOptionByValue(String strXpath, String dynamicValue, String value) {
        WebElement element = convertToElement("selectOptionByValue :: [" + value + "]", strXpath, dynamicValue, 1);
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
    }
    
    public static void selectOptionByIndex(String strXpath, String dynamicValue, int index) {
        WebElement element = convertToElement("selectOptionByIndex :: [" + index + "]", strXpath, dynamicValue, 1);
        Select dropdown = new Select(element);
        dropdown.selectByIndex(index);
    }
    
    public static boolean isDisplayed(String strXpath, String dynamicValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        boolean currentStatus = false;
        try {
            currentStatus = driver.findElement(By.xpath(newXpath)).isDisplayed();
        } catch (Exception e){
            currentStatus = false;
        }
        return currentStatus;
    }

    public static void verifyElementPresent(String strXpath, String dynamicValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        boolean bCurrentStatus = isDisplayed(strXpath, dynamicValue);
        verifyValues("verifyElementPresent :: [" + newXpath + "]", String.valueOf(bCurrentStatus), "true", Enums.OPERATOR.equal);
    }

    public static String getAttribute(String strXpath, String dynamicValue, String attribute) {
        WebElement element = convertToElement("getAttribute", strXpath, dynamicValue, 0);
        return element.getAttribute(attribute);
    }

    public static void verifyAttribute(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        String currentValue = getAttribute(strXpath, dynamicValue, attribute);
        verifyValues("verifyAttributeElement :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static List<String> getAttributeOnList(String strListXpath, String dynamicValue, String attribute) {
        List<String> returnValues = new ArrayList<String>();
        String newXpath = convertXpath(strListXpath, dynamicValue);
        List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        for(WebElement webElement : wListRows) {
            returnValues.add(webElement.getAttribute(attribute));
        }
        return returnValues;
    }

    public static String getCssValue(String strXpath, String dynamicValue, String attribute) {
        WebElement element = convertToElement("getCssValue", strXpath, dynamicValue, 0);
        return element.getCssValue(attribute);
    }

    public static void verifyCssValue(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        String currentValue = getCssValue(strXpath, dynamicValue, attribute);
        verifyValues("verifyCssValue :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static int countItemsOnList(String strListXpath, String dynamicValue) {
        String newXpath = convertXpath(strListXpath, dynamicValue);
        return driver.findElements(By.xpath(newXpath)).size();
    }

    public static void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        verifyValues("verifyTitle", actualTitle, expectedTitle, Enums.OPERATOR.equal);
    }

    public static void navigateURL(String URL) {
        logInfo("INFO", "navigateURL :: [" + URL + "]", 1);
        driver.get(URL);
        driver.manage().window().maximize();
    }

    public static void verifyURL(String expectedURL) {
        String actualURL = driver.getCurrentUrl();
        verifyValues("verifyURL", actualURL, expectedURL, Enums.OPERATOR.equal);
    }

    public static void alertDialog(String action){
        logInfo("INFO", "alertDialog :: [" + action + "]", 1);
        if(action == "dismiss") driver.switchTo().alert().dismiss();
        else {
            driver.switchTo().alert().accept();
            delay(2);
        }
    }

    public static String getAlertMessage(){
        String message = driver.switchTo().alert().getText();
        logInfo("INFO", "getAlertMessage :: [" + message + "]", 0);
        return message;
    }

    public static void closeDriver() {
        driver.close();
        driver.quit();
    }
}