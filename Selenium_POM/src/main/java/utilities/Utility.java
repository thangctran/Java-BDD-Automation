package utilities;

import org.testng.Assert;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utility {
    final static int logSetting = 1 ; // 1:info; 0:debug
    public static String getUnique(String formatDate) {
        // "E yyyy.MM.dd 'at' HH:mm:ss a zzz" => Sat 2018.08.11 at 05:09:21 PM UTC
        SimpleDateFormat ft = new SimpleDateFormat (formatDate);
        return ft.format(new Date( ));
    }

    public static int getRandomInt(int min, int max){
        return (int)((Math.random()*((max-min)+1)) + min);
    }

    public static int getNumericInString(String string) {
        return Integer.parseInt(string.replaceAll("[^0-9]", ""));
    }

    public static void logInfo(String logType, String logs, int logMode) {
        if (logMode >= logSetting) {
            String logText = getUnique("yyyy/MM/dd HH:mm:ss.SSS") + " [" + logType + "] " + logs + "\n";
            System.out.print(logText);
//            writeLogFile(logText);
        }
    }
    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
        }
    }

    public static boolean isNumeric(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    public static String parseString(String string, String startSub, String endSub) {
        int findStartIndex = 0;
        int startIndex = 0;
        int findEndIndex = -1;
        // Check if sub String exists or not
        if (startSub != null) {
            findStartIndex = string.indexOf(startSub);
            if (findStartIndex >= 0) startIndex = findStartIndex + startSub.length();
        }
        if (endSub != null) {
            findEndIndex = string.indexOf(endSub, startIndex);
        }
        if (findEndIndex > -1) return string.substring(findStartIndex, findEndIndex);
        else return string.substring(startIndex);
    }

    public static String convertXpath(String strXpath, String dynamicValue) {
        String resultXpath = strXpath;
        String strTempXpath = strXpath;
        if (dynamicValue != null) {
            String findKey = "";
            String newTemp = "";
            if (isNumeric(dynamicValue)) resultXpath = "(" + strTempXpath + ")[" + dynamicValue + "]";
            else {
                //find dynamic keys to replace
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

    public static void verifyValues(String actionName, String actualValue, String expectedValue, Enums.OPERATOR operators) {
        boolean verifyStatus = false;
        String operator = "=";//equal, notEqual, greaterThan, greaterThanOrEqual, lessThan, lessThanOrEqual, contains, notContains
        switch(operators) {
            case notEqual:
                operator = "!=";
                verifyStatus = (actualValue != expectedValue);
                break;
            case greaterThan:
                operator = ">";
                verifyStatus = (Double.parseDouble(actualValue) > Double.parseDouble(expectedValue));
                break;
            case greaterThanOrEqual:
                operator = ">=";
                verifyStatus = (Double.parseDouble(actualValue) >= Double.parseDouble(expectedValue));
                break;
            case lessThan:
                operator = "<";
                verifyStatus = (Double.parseDouble(actualValue) < Double.parseDouble(expectedValue));
                break;
            case lessThanOrEqual:
                operator = "<=";
                verifyStatus = (Double.parseDouble(actualValue) <= Double.parseDouble(expectedValue));
                break;
            case contains:
                operator = "contains";
                verifyStatus = (actualValue.contains(expectedValue));
                break;
            case notContains:
                operator = "not contains";
                verifyStatus = (!actualValue.contains(expectedValue)); //(actualValue.indexOf(expectedValue) < 0);
                break;
            default:
                verifyStatus = (actualValue.contentEquals(expectedValue));
        }
        //Log to file
        if(verifyStatus) logInfo("PASSED", actionName + " :: [" + actualValue + "] " + operator + " [" + expectedValue + "]", 1);
        else logInfo("FAILED", actionName + " :: [" + actualValue + "] " + operator + " [" + expectedValue + "]", 1);
        Assert.assertEquals(verifyStatus, true);
    }
}
