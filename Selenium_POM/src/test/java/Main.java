import helpers.FileHelper;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<String,String> a = FileHelper.getTestDataCSV("login\\BE002.csv", "," , 1);
//        Map<String,String> b = FileHelper.getTestDataCSV("login\\BE002.csv", ",", 2);
        System.out.println(a.get("email"));
        System.out.println(a.get("error_message"));
//        System.out.println(b.get("email"));
//        System.out.println(b.get("password"));
    }
}
