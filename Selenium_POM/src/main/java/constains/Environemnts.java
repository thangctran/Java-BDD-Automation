package constains;

import static utilities.Utility.getUnique;

public class Environemnts {
    public final static String PROJECT_PATH = System.getProperty("user.dir");
    public final static String REPORTS_PATH = PROJECT_PATH + "\\src\\main\\resources\\reports";
    public final static String DRIVERS_PATH = PROJECT_PATH + "\\src\\main\\resources\\drivers";
    public final static String LOG_FILE = REPORTS_PATH + "\\LogDetail" + getUnique("yyMMddHHmmss")+ ".txt";
}
