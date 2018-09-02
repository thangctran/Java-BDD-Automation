import helpers.FileHelper;

import constants.GolobalVariables;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GolobalVariables.testData = FileHelper.getTestDataCSV("be\\BE002.csv", "," , 1);
//        Map<String,String> b = FileHelper.getTestDataCSV("login\\BE002.csv", ",", 2);
        System.out.println(GolobalVariables.testData.get("email"));
        System.out.println(GolobalVariables.testData.get("error_message"));
//        System.out.println(b.get("email"));
//        System.out.println(b.get("password"));
//        UIElements.convertToElement("adasds", "dasd", "dad", 1)
    }
}
