package constains;

import static utilities.Utility.getUnique;

public class Environemnts {
    public final static String PROJECT_PATH = System.getProperty("user.dir");
    public final static String DRIVER_PATH = PROJECT_PATH + "\\src\\main\\resources\\drivers\\";
    public final static String CONFIG_FILE = PROJECT_PATH + "\\src\\main\\resources\\configuration\\config.xml";
    public final static String TEST_PATH = PROJECT_PATH + "\\src\\test\\resources";
}
