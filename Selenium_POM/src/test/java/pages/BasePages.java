package pages;

import elements.UIElements;
import org.openqa.selenium.WebElement;
import utilities.Enums;
import java.util.ArrayList;
import java.util.List;
import static utilities.Utility.isNumeric;
import static utilities.Utility.verifyValues;

public class BasePages extends UIElements {
    static String mnuMenuContent = "(//ul[contains(@*,'sidebar')])[last()]/li[.//*[contains(//text(),'')]]/a";
    static String mnuSubMenu = "//*[./*[@aria-expanded='true']]//li/a[//text()]";

    public static void goToFuction(String mainMenu, String subMenu) {
        click(mnuMenuContent, mainMenu);
        if(subMenu != null) click(mnuSubMenu, subMenu);
    }

    public static int countRows(String strTableXpath, String dynamicValue) {
        String newXpath = strTableXpath + "//tr";
        return countItemsOnList(newXpath, dynamicValue);
    }

    static String getColumnXpath(String columnName){
        String column = columnName;
        if(!isNumeric(columnName)) column = "count(//tr/th[text()='" + columnName + "']/preceding-sibling::th)+1";
        return column;
    }

    public static void selectCheckboxOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex, boolean status) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//*[input]", strTableXpath, rowIndex, columnName);
        selectCheckbox(newXpath, dynamicValue, status);
    }

    public static void clickIconOnTable(String strTableXpath, String dynamicValue, String rowIndex, String icon) {
        String newXpath = String.format("%s//tr[%s]/td//a[@title='%s']", strTableXpath, rowIndex, icon);
        click(newXpath, dynamicValue);
    }

    public static void clickCellOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//a]", strTableXpath, rowIndex, columnName);
        WebElement element = convertToElement("clickCellOnTable", newXpath, dynamicValue, 1);
        element.click();
    }

    public static List<String> getCellValuesOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        List<String> returnValues = new ArrayList<String>();
        String columnName = getColumnXpath(column);
        String newXpath;
        if (rowIndex != null) {
            newXpath = String.format("%s//tr[%s]/td[%s]", strTableXpath, rowIndex, columnName);
            returnValues.add(getAttribute(newXpath, dynamicValue, "innerText"));
        } else {
            newXpath = String.format("%s//tr/td[%s]", strTableXpath, columnName);
            returnValues = getAttributeOnList(newXpath, dynamicValue, "innerText");
        }
        return returnValues;
    }

    public static void verifyStartOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex, String expectedStart) {
        String newXpath;
        int countStart;
        String columnName = getColumnXpath(column);
        if (rowIndex != null) {
            newXpath = String.format("%s//tr[%s]/td[%s]//i[@class='star fa fa-star']", strTableXpath, rowIndex, columnName);
            countStart = countItemsOnList(newXpath, dynamicValue);
            verifyValues("verifyStartOnTable :: [" + newXpath + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.equal);
        } else {
            int rows = countRows(strTableXpath, dynamicValue);
            for(int n=1; n<= rows; n++){
                newXpath = String.format("%s//tr[%d]/td[%s]//i[@class='star fa fa-star']", strTableXpath, n, columnName);
                countStart = countItemsOnList(newXpath, dynamicValue);
                verifyValues("verifyStartOnTable :: at row [" + n + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.greaterThanOrEqual);
            }
        }
    }
}