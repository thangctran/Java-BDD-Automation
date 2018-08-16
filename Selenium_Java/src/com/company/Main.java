package com.company;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.text.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    static WebDriver driver;
    static String strOpenOption = "create";
    static Integer logSetting = 1; // 1 : info ; 0 : debug

    ////////////// Files ///////////////
    static void writeLogFile(String strTextContent){
        try {
            // Define charset to format text content before setting to txt file
            Charset utf8 = StandardCharsets.UTF_8;

            // Define text file location path
            String strTextFileLocation = System.getProperty("user.dir") + "\\out\\LogDetails.txt";
            Path FILEPATH = Paths.get(strTextFileLocation);

            //Writing to the expected txt file with "create" or "append" Option
            if (strOpenOption.equals("create")) {
                Files.write(FILEPATH,strTextContent.getBytes(utf8), StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
                strOpenOption = "append";
            }else {
                Files.write(FILEPATH,strTextContent.getBytes(utf8),StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.out.println(getUnique("yyyy/MM/dd HH:mm:ss.SSS") + " [ERROR] " + e.toString());
        }
    }

    ////////////// Driver ///////////////
    static WebDriver getDriver(String browser, Integer waitTime) {
        String currentDriver = System.getProperty("user.dir") + "\\driver\\";
        WebDriver newDriver = null;
        switch(browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.firefox.marionette", currentDriver + "geckodriver.exe");
                newDriver = new FirefoxDriver();
                break;
            case "ie":
                break;
            default:
                System.setProperty("webdriver.chrome.driver", currentDriver + "chromedriver.exe");
                newDriver = new ChromeDriver();
        }
        newDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        return newDriver;
    }

    ////////////// Utility ///////////////
    static String getUnique(String formatDate) {
        // "E yyyy.MM.dd 'at' HH:mm:ss a zzz" => Sat 2018.08.11 at 05:09:21 PM UTC
        SimpleDateFormat ft = new SimpleDateFormat (formatDate);
        return ft.format(new Date( )).toString();
    }

    static Integer getRandomInt(Integer min, Integer max){
        Integer x = (int)((Math.random()*((max-min)+1)) + min);
        return x;
    }

    static Integer getNumericInString(String string) {
        return Integer.parseInt(string.replaceAll("[^0-9]", ""));
    }

    static void typeKeysByRobot(String keys) {
        logInfo("INFO", "typeKeysByRobot :: [" + keys + "]", 1);
        try {
            StringSelection stringSelection = new StringSelection(keys);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
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

    static void logInfo(String logType, String logs, Integer logMode) {
        if (logMode >= logSetting) {
            String logText = getUnique("yyyy/MM/dd HH:mm:ss.SSS") + " [" + logType + "] " + logs + "\n";
            System.out.print(logText);
            writeLogFile(logText);
        }
    }
    static void delay(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
        }
    }

    static boolean isNumeric(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    static String parseString(String string, String startSub, String endSub) {
        int index1 = 0;
        int index11 = 0;
        int index2 = -1;
        String strReturnResult = "";
        // Check if sub String exists or not
        if (startSub != null) {
            index1 = string.indexOf(startSub);
            if (index1 >= 0) index11 = index1 + startSub.length();
        }
        if (endSub != null) {
            index2 = string.indexOf(endSub, index11);
        }
        if (index2 > -1) strReturnResult = string.substring(index1, index2);
        else strReturnResult = string.substring(index11);
        return strReturnResult;
    }

    static String convertXpath(String strXpath, String dynamicValue) {
        String resultXpath = strXpath;
        String strTempXpath = strXpath;
        if (dynamicValue != null) {
            String findKey = "";
            String newTemp = "";
            if (isNumeric(dynamicValue)) resultXpath = "(" + strTempXpath + ")[" + dynamicValue + "]";
            else {
                if (strTempXpath.indexOf("//@") > -1) findKey = parseString(strTempXpath, "//@", "]");
                if (strTempXpath.indexOf("//text()") > -1) findKey = parseString(strTempXpath, "//text()", "]");
                if (strTempXpath.indexOf("//.") > -1) findKey = parseString(strTempXpath, "//.", "]");
                // edit //@attributeName,'' to @attributeName,'dynamicName'
                if (findKey.indexOf(",") > -1) {
                    newTemp = parseString(findKey, null, ",").replace("//", "") + ",'" + dynamicValue + "')";
                    resultXpath = strTempXpath.replace(findKey, newTemp);
                } else {
                    // edit //@attributeName] to @attributeName='dynamicName']
                    newTemp = findKey.replace("//", "") + "='" + dynamicValue + "'";
                    resultXpath = strTempXpath.replace(findKey, newTemp);
                }
            }
        }
        return resultXpath;
    }

    static void verifyValues(String actionName, String value1, String value2, String operator) {
        boolean bCurrentStatus = false;
        switch(operator) {
            case "!=":
                bCurrentStatus = (value1 != value2);
                break;
            case ">":
                bCurrentStatus = (Double.parseDouble(value1) > Double.parseDouble(value2));
                break;
            case ">=":
                bCurrentStatus = (Double.parseDouble(value1) >= Double.parseDouble(value2));
                break;
            case "<":
                bCurrentStatus = (Double.parseDouble(value1) < Double.parseDouble(value2));
                break;
            case "<=":
                bCurrentStatus = (Double.parseDouble(value1) <= Double.parseDouble(value2));
                break;
            case "contains":
                bCurrentStatus = (value1.contains(value2));
                break;
            case "not contains":
                bCurrentStatus = (value1.indexOf(value2) < 0);
                break;
            default:
                bCurrentStatus = (value1.contentEquals(value2));
        }
        //Log to file
        if(bCurrentStatus) logInfo("PASSED", actionName + " :: [" + value1 + "] " + operator + " [" + value2 + "]", 1);
        else logInfo("FAILED", actionName + " :: [" + value1 + "] " + operator + " [" + value2 + "]", 1);
    }

    ////////////// WebElement ///////////////
    static void waitForPresent(String strXpath, Integer iTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            WebElement a = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
            logInfo("DEBUG", "waitForPresent :: appear [" + strXpath + "]", 0);
        } catch (Exception e) {
            logInfo("DEBUG", "waitForPresent :: disappear [" + strXpath + "]", 0);
        }
    }

    static void scrollToElement(String strXpath, String dynamicValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "scrollToElement :: [" + newXpath + "]", 0);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(newXpath)));
    }

    static void click(String strXpath, String dynamicValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "click :: [" + newXpath + "]", 1);
        driver.findElement(By.xpath(newXpath)).click();
    }

    static void clickMousePosition(String strXpath, String dynamicValue, Integer x, Integer y, String clickType) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        WebElement element = driver.findElement(By.xpath(newXpath));
        Integer eleWidth = Math.round(element.getSize().getWidth()/2);
        if(x != null) eleWidth = x;
        Integer eleHeight = Math.round(element.getSize().getHeight()/2);
        if(y != null) eleHeight = y;
        logInfo("INFO", "clickMousePosition :: [" + eleWidth + "," + eleHeight + "] ::[" + newXpath + "]", 1);
        Actions builder = new Actions(driver);
//        builder.moveToElement(element, eleWidth, eleHeight);
        switch(clickType) {
            case "doubleClick":
                builder.moveToElement(element, eleWidth, eleHeight).doubleClick().build().perform();
                break;
            case "mouseOver":
                builder.moveToElement(element, eleWidth, eleHeight).build().perform();
                break;
            default:
                builder.moveToElement(element, eleWidth, eleHeight).click().build().perform();
        }
    }

    static void dragAndDrop(String sourceXpath, String sourceDynamic, String targetXpath, String targetDynamic, Integer x, Integer y) {
        String newSourceXpath = convertXpath(sourceXpath, sourceDynamic);
        WebElement source = driver.findElement(By.xpath(newSourceXpath));
        String newTargetXpath = convertXpath(targetXpath, targetDynamic);
        WebElement target = driver.findElement(By.xpath(newTargetXpath));
        Integer eleWidth = Math.round(target.getSize().getWidth()/2);
        if(x != null) eleWidth = x;
        Integer eleHeight = Math.round(target.getSize().getHeight()/2);
        if(y != null) eleHeight = y;
        logInfo("INFO", "dragAndDrop ::[" + newSourceXpath + "] to [" + newTargetXpath + "]:[" + eleWidth + "," + eleHeight + "]", 1);
        Actions builder = new Actions(driver);
        builder.clickAndHold(source).moveToElement(target, eleWidth, eleHeight).click().release(target).build().perform();
    }

    static void selectCheckbox(String strXpath, String dynamicValue, boolean status) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "selectCheckbox :: [" + status + "] :: [" + newXpath + "]", 1);
        WebElement element = driver.findElement(By.xpath(newXpath));
        String currentStatus = element.getAttribute("class");
        if(currentStatus.contains("checked") != status) element.click();
    }

    static void selectMultipleCheckboxes(String strXpath, List<String> listValues, boolean status) {
        for(String item : listValues){
            selectCheckbox(strXpath, item, status);
        }
    }

    static void setText(String strXpath, String dynamicValue, String value) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "setText :: [" + value + "] :: [" + newXpath + "]", 1);
        driver.findElement(By.xpath(newXpath)).sendKeys(value);
    }

    static void pressKeys(String keys) {
        logInfo("INFO", "pressKeys :: [" + keys + "]", 1);
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(keys).build().perform();
    }

    static void pressControlV() {
        Actions pressKey = new Actions(driver);
        pressKey.sendKeys(Keys.CONTROL + "v").build().perform();
    }

    static void selectOptionByText(String strXpath, String dynamicValue, String value) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "selectOptionByText :: [" + value + "] :: [" + newXpath + "]", 1);
        Select dropdown = new Select(driver.findElement(By.xpath(newXpath)));
        dropdown.selectByVisibleText(value);
    }

    static void verifyElementPresent(String strXpath, String dynamicValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        logInfo("INFO", "verifyElementPresent :: [" + newXpath + "]", 1);
        boolean bCurrentStatus = driver.findElement(By.xpath(newXpath)).isDisplayed();
        verifyValues("verifyElementPresent :: [" + newXpath + "]", String.valueOf(bCurrentStatus), "true", "=");
    }

    static void verifyAttributeElement(String strXpath, String dynamicValue, String attribute, String expectedValue) {
        String newXpath = convertXpath(strXpath, dynamicValue);
        String currentValue = driver.findElement(By.xpath(newXpath)).getAttribute(attribute);
        verifyValues("verifyAttributeElement :: [" + attribute + "]:[" + newXpath + "]", currentValue, expectedValue, "=");
    }

    static Integer countItemsOnList(String strListXpath, String dynamicValue) {
        String newXpath = convertXpath(strListXpath, dynamicValue);
        return driver.findElements(By.xpath(newXpath)).size();
    }

    static Integer countRows(String strTableXpath) {
        String newXpath = strTableXpath + "//tr";
        return driver.findElements(By.xpath(newXpath)).size();
    }

    static String getColumnXpath(String columnName){
        String column = columnName;
        if(!isNumeric(columnName)) column = "count(//tr/th[text()='" + columnName + "']/preceding-sibling::th)+1";
        return column;
    }

    static void selectCheckboxOnTable(String strTableXpath, String column, String rowIndex, boolean status) {
        String columnName = getColumnXpath(column);
        String newXpath = strTableXpath + "//tr[" + rowIndex + "]/td[" + columnName + "]//*[input]";
        selectCheckbox(newXpath, null, status);
    }

    static void clickCellOnTable(String strTableXpath, String column, String rowIndex, String clickOn) {
        String tagName = "a"; //clickOn : View; Edit; Remove; DELETE
        if(clickOn.toLowerCase() != "link") tagName = "a[@title='" + clickOn + "']";
        String columnName = getColumnXpath(column);
        String newXpath = strTableXpath + "//tr[" + rowIndex + "]/td[" + columnName + "]//" + tagName;
        logInfo("INFO", "clickCellOnTable :: [" + rowIndex + "," + column + "] :: [" + newXpath + "]", 1);
        driver.findElement(By.xpath(newXpath)).click();
    }

    static List<String> getCellValuesOnTable(String strTableXpath, String column, String rowIndex) {
        List<String> returnValues = new ArrayList<String>();
        String columnName = getColumnXpath(column);
        String newXpath;
        if (rowIndex != null) {
            newXpath = strTableXpath + "//tr[" + rowIndex + "]/td[" + columnName + "]";
            returnValues.add(driver.findElement(By.xpath(newXpath)).getAttribute("innerText").toString());
        } else {
            newXpath = strTableXpath + "//tr/td[" + columnName + "]";
            List<WebElement> wListRows = driver.findElements(By.xpath(newXpath));
            for(WebElement webElement : wListRows){
                returnValues.add(webElement.getAttribute("innerText").toString());
            }
        }
        return returnValues;
    }

    static void verifyStartOnTable(String strTableXpath, String column, String rowIndex, String expectedStart) {
        String newXpath;
        Integer countStart;
        String columnName = getColumnXpath(column);
        if (rowIndex != null) {
            newXpath = strTableXpath + "//tr[" + rowIndex + "]/td[" + columnName + "]//i[@class='star fa fa-star']";
            countStart = countItemsOnList(newXpath, null);
            verifyValues("verifyStartOnTable :: [" + newXpath + "]", String.valueOf(countStart), String.valueOf(expectedStart), ">=");
        } else {
            Integer rows = countRows(strTableXpath);
            for(int n=1; n<= rows; n++){
                newXpath = strTableXpath + "//tr[" + n + "]/td[" + columnName + "]//i[@class='star fa fa-star']";
                countStart = countItemsOnList(newXpath, null);
                verifyValues("verifyStartOnTable :: [" + newXpath + "]", String.valueOf(countStart), String.valueOf(expectedStart), ">=");
            }
        }
    }

    static void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        verifyValues("verifyTitle", actualTitle, expectedTitle, "=");
    }

    static void navigateURL(String URL) {
        logInfo("INFO", "navigateURL :: [" + URL + "]", 1);
        driver.get(URL);
        driver.manage().window().maximize();
    }

    static void verifyURL(String expectedURL) {
        String actualURL = driver.getCurrentUrl();
        verifyValues("verifyURL", actualURL, expectedURL, "=");
    }

    static void alertDialog(String action){
        logInfo("INFO", "alertDialog :: [" + action + "]", 1);
        if(action == "dismiss") driver.switchTo().alert().dismiss();
        else {
            driver.switchTo().alert().accept();
            delay(2);
        }
    }

    ////////////////////////////COMMON/////////////////////////////
    static void login(String url, String email, String password) {
        String txtEmail = "//input[@placeholder='Email'][not(@id)]";
        String txtPassword = "//input[@name='password']";
        String btnLogin = "//button[@type='submit'][contains(@class,'btn-block l')]";

        if(url != null) navigateURL(url);
        if(email != null)setText(txtEmail, null, email);
        if(password != null) setText(txtPassword, null, password);
        click(btnLogin, null);
    }

    static void verifyMyAccountPage(String userName) {
        String lblUserName = "//h3[@class='RTL']";
        String lblCurrentDay = "//span[@class='h4']";
        String imgMenuBarIcon = "//ul[@class='nav profile-tabs' or @id='social-sidebar-menu']/li/a/*[//@class]";
        String lblMenuBar = "//ul[@class='nav profile-tabs']/li[./a[contains(//.,'')]]";
        verifyAttributeElement(lblUserName, null, "textContent", userName);
        verifyAttributeElement(lblCurrentDay, null, "innerText", getUnique("d MMMM yyyy"));
        // verify Bookings; My Profile; Wishlist; Newsletter label
        verifyElementPresent(lblMenuBar, "Bookings");
        verifyElementPresent(lblMenuBar, "My Profile");
        verifyElementPresent(lblMenuBar, "Wishlist");
        verifyElementPresent(lblMenuBar, "Newsletter");
        // verify Bookings; My Profile; Wishlist; Newsletter icon
        verifyElementPresent(imgMenuBarIcon, "bookings-icon");
        verifyElementPresent(imgMenuBarIcon, "profile-icon");
        verifyElementPresent(imgMenuBarIcon, "wishlist-icon");
        verifyElementPresent(imgMenuBarIcon, "newsletter-icon");
        // verify Bookings menu is selected as default
        verifyAttributeElement(lblMenuBar, "Bookings", "class", "active");;
    }

    static void goToFuction(String menuMain, String subMenu) {
        String mnuMenuContent = "(//ul[contains(@*,'sidebar')])[last()]/li[.//*[contains(//text(),'')]]/a";
        String mnuSubMenu = "//*[./*[@aria-expanded='true']]//li/a[//text()]";
        click(mnuMenuContent, menuMain);
        if(subMenu != null) click(mnuSubMenu, subMenu);
    }

    static void deleteRowByButton(String rowIndex) {
        String tblBE = "//table";
        String lnkLink = "//a[//text()]";
        selectCheckboxOnTable(tblBE, "1", rowIndex, true);
        click(lnkLink, " Delete Selected");
        alertDialog("accept");
    }

    static void uploadGallery(String rowIndex, String imageUpload) {
        String tblBE = "//table";
        String btnAddPhotos = "//a[@aria-controls='UploadPhotos']";
        String lblDropFiles = "//span[@class='drop']";
        clickCellOnTable(tblBE, "Gallery", rowIndex, "link");
        click(btnAddPhotos, null);
        click(lblDropFiles, null);
        typeKeysByRobot(imageUpload);
    }

    static void searchOnBE(String text, String field) {
        String lstSearch = "//div[@class='xcrud-nav']//*[//text()][@class]";
        String txtSearch = "//input[@name='phrase']";
        String ddlSearch = "//select[@name='column']";
        driver.switchTo().defaultContent();
        scrollToElement(lstSearch, "Search");
        click(lstSearch, "Search");
        setText(txtSearch, null, text);
        selectOptionByText(ddlSearch, null, field);
        click(lstSearch, "Go");
    }

    static void setPriceRange(Integer from, Integer to) {
        String sldPriceRange = "//div[@class='slider-track']";
        String sldRound = "//div[@class='slider-handle round']";
        Integer sliderWitdth = driver.findElement(By.xpath(sldPriceRange)).getSize().getWidth();
        Integer posi = 0;
        if(from != null){
            posi = (int)(sliderWitdth * from / 100);
            dragAndDrop(sldRound, "1", sldPriceRange, null, posi, 2);
        }
        if(to != null){
            posi = (int)(sliderWitdth * to / 100);
            dragAndDrop(sldRound, "2", sldPriceRange, null, posi, 2);
        }
    }

    static void filerSearch(String startGrade, List<String> listPropertyTypes, List<String> listAmenities, String tour_Car_Type, String airportPickup ) {
        String rdoStartGrade = "//*[./input[@name='stars']]";
        String chkCheckbox = "//*[starts-with(@class,'icheckbox')][./input[@type='checkbox'][//@*]]";
        String rdoRadio = "//*[starts-with(@class,'iradio')][./input[@type='radio'][//@*]]";
        String btnButton = "//button[//text()]";
        String ddlDropDownList = "//select[//@*]";
        click(rdoStartGrade, startGrade);
        if(listPropertyTypes != null) selectMultipleCheckboxes(chkCheckbox, listPropertyTypes, true);
        if(listAmenities != null) selectMultipleCheckboxes(chkCheckbox, listAmenities, true);
        if(tour_Car_Type != null) click(rdoRadio, tour_Car_Type);
        if(airportPickup != null) selectOptionByText(ddlDropDownList, "pickup" , airportPickup);
        click(btnButton, "Search");
    }

    //////////////////////// MAIN /////////////////////////////////
    public static void main(String[] args) throws InterruptedException {
        //set Driver
        driver = getDriver("chrome", 10);

        //define controls
        String btnButton = "//button[//text()]";
        String tblSearchResult = "//*[@class='itemscontainer' or @class='panel panel-default']/table[contains(@class,'table-striped')]";
        String tblBE = "//table";

        logInfo("TESTCASE", "Start ##### FE001-Login - Login successful #####", 1);
        String baseUrlFE = "https://www.phptravels.net";
        String emailFE = "user@phptravels.com";
        String passwordFE = "demouser";
        String expectedTitle = "Login";
        String expectedURL = "https://www.phptravels.net/login";

        logInfo("STEP", "Go to https://www.phptravels.net", 1);
        navigateURL(baseUrlFE);

        logInfo("STEP", "Select MY ACCOUNT -> Login", 1);
        goToFuction("My Account", " Login");

        logInfo("STEP", "Verify Login page is displayed", 1);
        verifyTitle(expectedTitle);
        verifyURL(expectedURL);

        logInfo("STEP", "Login with valid Email and Password", 1);
        login(null, emailFE, passwordFE);

        logInfo("STEP", "Verify My Account main page is displayed", 1);
        verifyMyAccountPage("Hi, Johny Smith");
        logInfo("TESTCASE", "End ##### FE001-Login - Login successful #####", 1);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        logInfo("TESTCASE", "Start ##### FE005-Hotels - Verify Hotel Filter #####", 1);
        String startNumber = "4";
        List<String> listTypes = Arrays.asList("Hotel", "Villa");
        List<String> listAmenities = Arrays.asList("Night Club", "Restaurant");
        String urlHotel = "https://www.phptravels.net/hotels";

        logInfo("STEP", "Go to https://www.phptravels.net/hotels", 1);
        navigateURL(urlHotel);

        logInfo("STEP", "Filter Search: Star grade; Price range; Property Types and Amenities", 1);
        setPriceRange(0, 60);
        filerSearch(startNumber, listTypes, listAmenities, null, null);

        logInfo("STEP", "Number Start of each Hotel is from 4 to 5 starts", 1);
        verifyStartOnTable(tblSearchResult, "1",null, startNumber);
        logInfo("TESTCASE", "End ##### FE005-Hotels - Verify Hotel Filter #####", 1);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        logInfo("TESTCASE", "Start ##### BE006-Hotels-Upload gallery #####", 1);
        String baseUrlBE = "https://www.phptravels.net/admin";
        String emailBE = "admin@phptravels.com";
        String passwordBE = "demoadmin";
        String rowIndex = getRandomInt(1,9).toString();
        String imageUpload = System.getProperty("user.dir") + "\\data\\Image.PNG";
        logInfo("STEP", "Navigate and login as Admin to page https://www.phptravels.net/admin", 1);
        login(baseUrlBE, emailBE, passwordBE);

        logInfo("STEP", "Go to Hotels Management", 1);
        goToFuction("Hotels", "Hotels");

        logInfo("STEP", "Upload image to a Hotel record", 1);
        scrollToElement(btnButton, "All");
        List<String> listGallery = getCellValuesOnTable(tblBE, "Gallery", rowIndex);
        Integer imageNumberBefore = getNumericInString(listGallery.get(0)) + 1;
        uploadGallery(rowIndex, imageUpload);

        logInfo("STEP", "Go to Hotels Management again", 1);
        goToFuction("Hotels", "Hotels");

        logInfo("STEP", "Verify image is uploaded for this hotel", 1);
        scrollToElement(btnButton, "All");
        listGallery = getCellValuesOnTable(tblBE, "Gallery", rowIndex);
        Integer imageNumberAfter = getNumericInString(listGallery.get(0));
        verifyValues("verifyUpload", imageNumberAfter.toString(), imageNumberBefore.toString(), "=");

        logInfo("TESTCASE", "End ##### BE006-Hotels-Upload gallery #####", 1);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        logInfo("TESTCASE", "Start ##### BE008-Hotels-Delete Hotels by Delete Selected button #####", 1);

        logInfo("STEP", "Delete Hotel by by Delete Selected button", 1);
        List<String> listHotelNameBefore = getCellValuesOnTable(tblBE, "Name", rowIndex);
        deleteRowByButton(rowIndex);

        logInfo("STEP", "Verify the Hotel is deleted on list.", 1);
        List<String> listHotelNameAfter = getCellValuesOnTable(tblBE, "Name", rowIndex);
        verifyValues("verifyHotelDeleted", listHotelNameAfter.get(0), listHotelNameBefore.get(0), "!=");

        logInfo("TESTCASE", "End ##### BE008-Hotels-Delete Hotels by Delete Selected button #####", 1);

        //close Chrome
        driver.close();
    }
}