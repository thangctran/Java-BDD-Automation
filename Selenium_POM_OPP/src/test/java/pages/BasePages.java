package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import elements.KWElement;
import utilities.Enums;
import utilities.Utility;

public class BasePages extends KWElement {
    public static void click(String strXpath, String dynamicValue) {
        action(Enums.METHOD_ELEMENT.click, null, strXpath, dynamicValue).click();
    }

    public static void setText(String strXpath, String dynamicValue, String value) {
        action(Enums.METHOD_ELEMENT.sendKeys, value, strXpath, dynamicValue).sendKeys(value);
    }

    public static int countRows(String strTableXpath, String dynamicValue) {
        String newXpath = strTableXpath + "//tr";
        return countItemsOnList(newXpath, dynamicValue);
    }

    static String getColumnXpath(String columnName){
        String column = columnName;
        if(!Utility.isNumeric(columnName)) column = "count(//tr/th[text()='" + columnName + "']/preceding-sibling::th)+1";
        return column;
    }

    public static void selectCheckboxOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex, boolean status) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//*[input]", strTableXpath, rowIndex, columnName);
        selectCheckbox(newXpath, dynamicValue, status);
    }

    public static void clickIconOnTable(String strTableXpath, String dynamicValue, String rowIndex, String icon) {
        String newXpath = String.format("%s//tr[%s]/td//a[@title='%s']", strTableXpath, rowIndex, icon);
        WebElement element = convertToElement("clickIconOnTable", newXpath, dynamicValue, 1);
        element.click();
    }

    public static void clickCellOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//a", strTableXpath, rowIndex, columnName);
        WebElement element = convertToElement("clickCellOnTable", newXpath, dynamicValue, 1);
        element.click();
    }

    public static List<String> getCellValuesOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        List<String> returnValues = new ArrayList<String>();
        String columnName = getColumnXpath(column);
        String newXpath;
        if (rowIndex != null) {
            newXpath = String.format("%s//tr[%s]/td[%s]", strTableXpath, rowIndex, columnName);
            Utility.logInfo("INFO", "getCellValuesOnTable :: [" + newXpath + "]", 1);
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
            Utility.verifyValues("verifyStartOnTable :: [" + newXpath + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.equal);
        } else {
            int rows = countRows(strTableXpath, dynamicValue);
            for(int n=1; n<= rows; n++){
                newXpath = String.format("%s//tr[%d]/td[%s]//i[@class='star fa fa-star']", strTableXpath, n, columnName);
                countStart = countItemsOnList(newXpath, dynamicValue);
                Utility.verifyValues("verifyStartOnTable :: at row [" + n + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.greaterThanOrEqual);
            }
        }
    }
}