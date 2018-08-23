package pages;

public class MainPage extends BasePages {
    final static String mnuMenuContent = "(//ul[contains(@*,'sidebar')])[last()]/li[.//*[contains(//text(),'')]]/a";
    final static String mnuSubMenu = "//*[./*[@aria-expanded='true']]//li/a[//text()]";

    public static void goToFuction(String mainMenu, String subMenu) {
        click(mnuMenuContent, mainMenu);
        if(subMenu != null) click(mnuSubMenu, subMenu);
    }
}
