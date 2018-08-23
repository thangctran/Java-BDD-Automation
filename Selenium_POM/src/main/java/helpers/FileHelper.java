package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import constants.Environemnts;
import utilities.Utility;

public class FileHelper {
    public final static String LOG_FILE = Environemnts.TEST_PATH + "\\reports\\LogDetail_" + Utility.getUnique("yyMMddHHmmss") + ".txt";

    public static void writeTextFile(String strFileLocation, String strTextContent){
        try {
            // Define charset to format text content before setting to txt file
            Charset utf8 = StandardCharsets.UTF_8;

            // Define text file location path
            Path path = Paths.get(strFileLocation);

            // Writing to the expected txt file
            Files.write(path,strTextContent.getBytes(utf8),StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(Utility.getUnique("yyyy/MM/dd HH:mm:ss.SSS") + " [ERROR] " + e.toString());
        }
    }

    public static void writeLogFile(String strTextContent){
        writeTextFile(LOG_FILE, strTextContent);
    }

    public static String getTextFile(String strFileLocation, Integer rowIndex) {
        String strReturnText = "";
        try {
            // Define charset to format text content before setting to txt file
            Charset utf8 = StandardCharsets.UTF_8;

            // Define text file location path
            Path path = Paths.get(strFileLocation);

            // Read all lines in text file
            List<String> strLines = Files.readAllLines(path, utf8);
            if (rowIndex != null) strReturnText = strLines.get(rowIndex);
            else {
                // Convert List item to string
                for (String line : strLines) {
                    strReturnText += line + "\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strReturnText;
    }

    public static String getXmlNodeValue(String strXMLNodeXpath, int intIndex){
        // Generate DocumentBuilderFactory to build XML document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Generate List to contain the returned xml node
        List<String> list = new ArrayList();

        // Optimize the input XML file location to the correct syntax
        String xmlFilePath = Environemnts.CONFIG_FILE;

        // Initiate returnedValue is null
        String returnedValue = null;
        try {

            // Build xml node from xml file
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFilePath);

            // Print out the xml string
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            // Complie expected xpath to get
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(strXMLNodeXpath);

            // Get list of xpath
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            // Get value of the expected xpath
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());

            // Print out the returned value
            returnedValue = list.get(intIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedValue;
    }

    public static Map<String,String> getTestDataRow(String strTestDataName, String strSheetName, int iRowIndex) {
        // Initiate dictionary to contain returned values
        Map<String,String> dictTestDataRow = new HashMap<String, String>();
        int iRow = iRowIndex;
        try{
            if (iRow == 0) {
                throw new Exception("Row 0 is used as header name, row index must start from 1");
            } else {
                // Initiate test data location path
                String strTestDataPath = Environemnts.DATA_PATH  + "\\" + strTestDataName;

                // Initiate column count variable
                Integer iCol = 0;

                // Initiate file input stream to read excel file
                FileInputStream fis = new FileInputStream(strTestDataPath);

                // Initiate excel workbook which contains the sheet
                Workbook wb = new XSSFWorkbook(fis);

                // Initiate sheet which contains the expected cell
                Sheet sheet = wb.getSheet(strSheetName);

                // Initiate Formular Evaluator to evaluate if the expected cell contains a formular
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                Row rHeaderRows = sheet.getRow(0);
                Cell cHeaderCell = rHeaderRows.getCell(0);
                String strHeader = cHeaderCell.getStringCellValue();

                String strCellValue = "";

                while (cHeaderCell != null) {
                    strHeader = cHeaderCell.getStringCellValue();
                    // Get value of corresponding cell
                    Row rExpectedRows = sheet.getRow(iRow);
                    Cell cExpectedCell = rExpectedRows.getCell(iCol);
                    switch (cExpectedCell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            Boolean bCellvaLue = cExpectedCell.getBooleanCellValue();
                            strCellValue = bCellvaLue.toString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            Number nCellValue = cExpectedCell.getNumericCellValue();
                            strCellValue = nCellValue.toString();
                            break;
                        case Cell.CELL_TYPE_STRING:
                            strCellValue = cExpectedCell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            switch (evaluator.evaluateFormulaCell(cExpectedCell)) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    Boolean bCellvaLueResult = cExpectedCell.getBooleanCellValue();
                                    strCellValue = bCellvaLueResult.toString();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    Number nCellValueResult = cExpectedCell.getNumericCellValue();
                                    strCellValue = nCellValueResult.toString();
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    strCellValue = cExpectedCell.getStringCellValue();
                                    break;
                            }
                    }
                    dictTestDataRow.put(strHeader, strCellValue);
                    iCol++;
                    rHeaderRows = sheet.getRow(0);
                    cHeaderCell = rHeaderRows.getCell(iCol);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictTestDataRow;
    }

    public static Map<String,String> getTestDataCSV(String strCSVName, String delimiter,  int iRowIndex) {
        // Initiate dictionary to contain returned values
        Map<String,String> dictTestDataRow = new HashMap<String, String>();
        try{
            if (iRowIndex <= 0) {
                throw new Exception("Row 0 is used as header name, row index must start from 1");
            } else {
                // -define .csv file in app
                String fileNameDefined = Environemnts.DATA_PATH  + "\\" + strCSVName;

                //put data to map
                String[] arrayHeader = getTextFile(fileNameDefined, 0).split(delimiter);
                String[] arrayCellValue = getTextFile(fileNameDefined, iRowIndex).split(delimiter);
                for(int i=0; i< arrayHeader.length; i++){
                    dictTestDataRow.put(arrayHeader[i], arrayCellValue[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictTestDataRow;
    }
}
