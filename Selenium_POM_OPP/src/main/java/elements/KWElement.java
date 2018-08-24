package elements;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import drivers.KWDriver;
import utilities.Enums;
import utilities.Utility;

public class KWElement {
    public static WebDriver driver = KWDriver.getDriver();

    public static WebElement action(Enums.METHOD_ELEMENT actionName, String description, String strXpath, String dynamicValue){
        WebElement element;
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        try {
            String tempDescription = "";
            if(description != null) tempDescription = "[" + description + "] ";
            Utility.logInfo("INFO", String.format("%s :: %s[%s]", actionName.toString(), tempDescription, newXpath), 1);
            element = driver.findElement(By.xpath(newXpath));
        } catch (Exception e){
            Utility.logInfo("ERROR", String.format("%s :: Not Found[%s]", actionName.toString(), newXpath), 1);
            return null;
        }
        return element;
    }
    
    public static WebElement convertToElement(String actionName, String strXpath, String dynamicValue, int logMode){
        WebElement element;
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);

        try {
            Utility.logInfo("INFO", String.format("%s :: [%s]", actionName, newXpath), logMode);
            element = driver.findElement(By.xpath(newXpath));
        } catch (Exception e){
            Utility.logInfo("ERROR", String.format("%s :: Not Found[%s]", actionName, newXpath), 1);
            return null;
        }
        return element;
    }

    public static void waitForPresent(String strXpath, int iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            WebElement a = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
            Utility.logInfo("DEBUG", "waitForPresent :: appear [" + strXpath + "]", 0);
        } catch (Exception e) {
            Utility.logInfo("DEBUG", "waitForPresent :: disappear [" + strXpath + "]", 0);
        }
    }

    public static void scrollToElement(String strXpath, String dynamicValue) {
        WebElement element = convertToElement("scrollToElement", strXpath, dynamicValue, 0);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
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
        String newSourceXpath = Utility.convertXpath(sourceXpath, sourceDynamic);
        WebElement source = driver.findElement(By.xpath(newSourceXpath));

        String newTargetXpath = Utility.convertXpath(targetXpath, targetDynamic);
        WebElement target = driver.findElement(By.xpath(newTargetXpath));

        int eleWidth = Math.round(target.getSize().getWidth()/2);
        if(x != null) eleWidth = x;

        int eleHeight = Math.round(target.getSize().getHeight()/2);
        if(y != null) eleHeight = y;

        Utility.logInfo("INFO", "dragAndDrop ::[" + newSourceXpath + "] to [" + newTargetXpath + "]:[" + eleWidth + "," + eleHeight + "]", 1);
        Actions builder = new Actions(driver);
        builder.clickAndHold(source).moveToElement(target, eleWidth, eleHeight).click().release(target).build().perform();
    }

    public static void selectCheckbox(String strXpath, String dynamicValue, boolean status) {
        WebElement element = convertToElement("selectCheckbox :: [" + status + "]", strXpath, dynamicValue, 1);
        boolean currentStatus = element.isSelected();
        if(currentStatus != status) element.click();
    }

    public static void pressKeys(String keys) {
        Utility.logInfo("INFO", "pressKeys :: [" + keys + "]", 1);
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(keys).build().perform();
    }

    public static void pressControlV() {
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(Keys.CONTROL + "v").build().perform();
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
            robot.setAutoDelay(300);
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

    public static void verifyElementPresent(String strXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        boolean bCurrentStatus = driver.findElement(By.xpath(newXpath)).isDisplayed();
        Utility.verifyValues("verifyElementPresent :: [" + newXpath + "]", String.valueOf(bCurrentStatus), "true", Enums.OPERATOR.equal);
    }

    public static String getAttribute(String strXpath, String dynamicValue, String attribute) {
        WebElement element = convertToElement("getAttribute", strXpath, dynamicValue, 0);
        return element.getAttribute(attribute).trim();
    }

    public static void verifyAttribute(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        String currentValue = driver.findElement(By.xpath(newXpath)).getAttribute(attribute).trim();
        Utility.verifyValues("verifyAttributeElement :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static java.util.List<String> getAttributeOnList(String strListXpath, String dynamicValue, String attribute) {
        java.util.List<String> returnValues = new ArrayList<String>();
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        java.util.List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        for(WebElement webElement : wListRows) {
            returnValues.add(webElement.getAttribute(attribute));
        }
        return returnValues;
    }

    public static void verifyAttributeOnList(String strListXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        java.util.List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        String checkValue = "match";
        for(WebElement webElement : wListRows) {
            if(webElement.getAttribute(attribute).trim() != expectedValue) {
                checkValue = "Not match";
                break;
            }
        }
        Utility.verifyValues("verifyAttributeOnList :: [" + attribute + "]:[" + newXpath + "]", checkValue, "match", Enums.OPERATOR.equal);
    }

    public static String getCssValue(String strXpath, String dynamicValue, String attribute) {
        WebElement element = convertToElement("getCssValue", strXpath, dynamicValue, 0);
        return element.getCssValue(attribute).trim();
    }

    public static void verifyCssValue(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        String currentValue = driver.findElement(By.xpath(newXpath)).getCssValue(attribute).trim();
        Utility.verifyValues("verifyCssValue :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, Enums.OPERATOR.equal);
    }

    public static void verifyCssValueOnList(String strListXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
        String checkValue = "match";
        for(WebElement webElement : wListRows) {
            if(webElement.getCssValue(attribute) != expectedValue) {
                checkValue = "Not match";;
                break;
            }
        }
        Utility.verifyValues("verifyCssValueOnList :: [" + attribute + "]:[" + newXpath + "]", checkValue, "match", Enums.OPERATOR.equal);
    }

    public static int countItemsOnList(String strListXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strListXpath, dynamicValue);
        return driver.findElements(By.xpath(newXpath)).size();
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
}
