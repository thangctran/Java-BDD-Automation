package google;
// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// [START sheets_quickstart]
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import static utilities.Utility.getUnique;
import static drivers.Driver.googelSheetName;

public class GoogleSheets {
    private static final String APPLICATION_NAME = "Google Sheets API Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String SPREADSHEET_ID = "1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void setCellValue(String sheetName, String columnName, int rowIndex, String value) throws IOException, GeneralSecurityException{
        String range = sheetName + "!" + columnName + rowIndex;//"UI-Report-Firefox!A2";
        ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(value)));
        UpdateValuesResponse result = getSheetsService().spreadsheets().values()
                .update(SPREADSHEET_ID, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

    public static String getCellValue(String sheetName, String columnName, int rowIndex) throws IOException, GeneralSecurityException{
        String range = sheetName + "!" + columnName + rowIndex;//"UI-Report-Firefox!A2";
        ValueRange response = getSheetsService().spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();
        if(response.getValues() != null) return String.valueOf(response.getValues().get(0).get(0));
        else return null;
    }

    //Update to google sheet: https://docs.google.com/spreadsheets/d/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/edit#gid=0
    public static void updateTestCaseStatus(String testCaseName, String startDate, String status) throws IOException, GeneralSecurityException{
        String range = googelSheetName + "!A:A";
        ValueRange response = getSheetsService().spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();
        List<String> testCase = Arrays.asList(testCaseName);
        int findRowIndex = response.getValues().indexOf(testCase);
        if(findRowIndex <= 0) {
            findRowIndex = response.getValues().size() + 1;
            setCellValue(googelSheetName, "A", findRowIndex, testCaseName);
        }
        setCellValue(googelSheetName, "C", findRowIndex, startDate);
        setCellValue(googelSheetName, "E", findRowIndex, status);
    }

    public static void insertColumnTestStatus() throws IOException, GeneralSecurityException{
        String range = "UI-Report-Chrome!E1:E";//googelSheetName + "!E1:E";
        String startDate = getUnique("yyyy/MM/dd HH:mm:ss");
        ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(startDate)));
        AppendValuesResponse appendResult = getSheetsService().spreadsheets().values()
                .append(SPREADSHEET_ID, range, body)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }
//    public static void main(String... args) throws IOException, GeneralSecurityException {
//        insertColumnTestStatus();
//    }
}
// [END sheets_quickstart]
