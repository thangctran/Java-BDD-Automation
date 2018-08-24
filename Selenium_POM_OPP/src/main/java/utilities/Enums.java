package utilities;

public class Enums {
    public enum CLICK_TYPE {doubleClick, click, rightClick, mouseOver;}
    public enum OPERATOR {equal, notEqual, greaterThan, greaterThanOrEqual, lessThan, lessThanOrEqual, contains, notContains;}
    public enum METHOD_DRIVER {acceptAlert,close,deleteAllCookies,dismissAlert,get,getAlertMessage,getCurrentUrl,quit,switchToDefaultContent,
        switchToFrame,switchToWindow,refresh,forward,back;}
    public enum METHOD_ELEMENT {click,getHeight,getWidth,isDisplayed,sendKeys;}
}