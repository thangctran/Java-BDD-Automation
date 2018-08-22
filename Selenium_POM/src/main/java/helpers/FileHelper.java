package helpers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static constains.Environemnts.LOG_FILE;
import static utilities.Utility.getUnique;

public class FileHelper {
    public static void writeLogFile(String strTextContent){
        try {
            // Define charset to format text content before setting to txt file
            Charset utf8 = StandardCharsets.UTF_8;

            // Define text file location path
            String strTextFileLocation = LOG_FILE;
            Path FILEPATH = Paths.get(strTextFileLocation);

            //Writing to the expected txt file
            Files.write(FILEPATH,strTextContent.getBytes(utf8),StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(getUnique("yyyy/MM/dd HH:mm:ss.SSS") + " [ERROR] " + e.toString());
        }
    }
}
