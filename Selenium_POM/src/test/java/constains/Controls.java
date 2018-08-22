package constains;

public class Controls {

    public final static String link = "//a[//text()]";
    public final static String checkbox = "//*[./input[@type='checkbox'][//@*]]/ins";
    public final static String textbox = "//input[@*]";
    public final static String radio = "//*[./input[@type='radio'][//@*]]/ins";
    public final static String dropdown = "//select[//@*]";
    public final static String table = "//table[//@*]";
    public final static String button = "//button[//text()]";
}
