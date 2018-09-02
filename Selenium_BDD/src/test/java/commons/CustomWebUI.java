package commons;

import java.util.ArrayList;
import java.util.List;
import constants.Controls;
import keywords.WebUI;
import utilities.Enums;
import utilities.Utility;
import utilities.Variables;

public class CustomWebUI {
    public static int countRows(String strTableXpath, String dynamicValue) {
        String newXpath = Utility.convertXpath(strTableXpath, dynamicValue) + "//tr/td[1]";
        return WebUI.countItemsOnList(newXpath, null);
    }

    static String getColumnXpath(String columnName){
        String column = columnName;
        if(!Utility.isNumeric(columnName)) column = "count(//tr/th[text()='" + columnName + "']/preceding-sibling::th)+1";
        return column;
    }

    public static void selectCheckboxOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex, boolean status) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//*[input]", strTableXpath, rowIndex, columnName);
        WebUI.selectCheckbox(newXpath, dynamicValue, status);
    }

    public static void clickIconOnTable(String strTableXpath, String dynamicValue, String rowIndex, String icon) {
        String newXpath = String.format("%s//tr[%s]/td//a[@title='%s']", strTableXpath, rowIndex, icon);
        WebUI.findElement("clickIconOnTable", newXpath, dynamicValue).click();
    }

    public static void clickCellOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        String columnName = getColumnXpath(column);
        String newXpath = String.format("%s//tr[%s]/td[%s]//a", strTableXpath, rowIndex, columnName);
        WebUI.findElement("clickCellOnTable", newXpath, dynamicValue).click();
    }

    public static List<String> getCellValuesOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex) {
        List<String> returnValues = new ArrayList<String>();
        String columnName = getColumnXpath(column);
        String newXpath;
        if (rowIndex != null) {
            newXpath = String.format("%s//tr[%s]/td[%s]", strTableXpath, rowIndex, columnName);
            Utility.logInfo("INFO", "getCellValuesOnTable :: [" + newXpath + "]", 1);
            returnValues.add(WebUI.getAttribute(newXpath, dynamicValue, "innerText"));
        } else {
            newXpath = String.format("%s//tr/td[%s]", strTableXpath, columnName);
            returnValues = WebUI.getAttributeOnList(newXpath, dynamicValue, "innerText");
        }
        return returnValues;
    }

    public static void verifyStartOnTable(String strTableXpath, String dynamicValue, String column, String rowIndex, String expectedStart) {
        String newXpath;
        int countStart;
        String columnName = getColumnXpath(column);
        if (rowIndex != null) {
            newXpath = String.format("%s//tr[%s]/td[%s]//i[@class='star fa fa-star']", strTableXpath, rowIndex, columnName);
            countStart = WebUI.countItemsOnList(newXpath, dynamicValue);
            Utility.verifyValues("verifyStartOnTable :: [" + newXpath + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.equal);
        } else {
            int rows = countRows(strTableXpath, dynamicValue);
            for(int n=1; n<= rows; n++){
                newXpath = String.format("%s//tr[%d]/td[%s]//i[@class='star fa fa-star']", strTableXpath, n, columnName);
                countStart = WebUI.countItemsOnList(newXpath, dynamicValue);
                Utility.verifyValues("verifyStartOnTable :: at row [" + n + "]", String.valueOf(countStart), String.valueOf(expectedStart), Enums.OPERATOR.greaterThanOrEqual);
            }
        }
    }

    public static int getImageNumberUpload(String rowIndex) {
        List<String> listGallery = getCellValuesOnTable(Controls.table, null,"Gallery", rowIndex);
        return Utility.getNumericInString(listGallery.get(0));
    }
}