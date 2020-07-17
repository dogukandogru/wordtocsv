import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class Converts Excel files to CSV files
 */
class ExcelToJira {

    private static String notification = ""; // This string returns errors and infos to GUI
    private static String language; // language of TPR File
    public static String  generateCsv(String fileName, String charToReplace, String saveFileName, String issueKeyPrefix,String languageFromGUI ){
        language = languageFromGUI;
        read(fileName,charToReplace,saveFileName);
        return notification;
    }


    /**
     * This method read xls or xlsx file and parse Test class and call write method.
     * @param fileName The name of the file to be read
     * @param replaceChar The char to put between steps
     * @param saveFilename The name of the file to be saved
     */
    private static void read(String fileName, String replaceChar,String saveFilename) {
        ArrayList<Test> tests = new ArrayList<>();
        ArrayList<String> sheetNames = new ArrayList<>();
        try{
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(fileName));

            // Getting sheet names
            for(Sheet sheet: workbook) {
                sheetNames.add(sheet.getSheetName());
            }

            // Getting the Sheet at index zero
            Sheet sheet = workbook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            for(int k=0; k<sheetNames.size(); k++){
                sheet = workbook.getSheetAt(k);
                int lastCheckpoint = sheet.getLastRowNum()+1;
                int id = 1000;
                Gson gsonBuilder = new GsonBuilder().create();
                ArrayList<Integer> checkpoints = new ArrayList<>();

                // Finding the starting and ending points of the tests.
                for(int i=1; i<=sheet.getLastRowNum()-1; i++){
                    String testDurumuNo = "";
                    if(sheet.getRow(i) != null) {
                        testDurumuNo = sheet.getRow(i).getCell(2).toString();
                        if (testDurumuNo.equals("1.0")) {
                            checkpoints.add(i);
                        }
                    }
                    else
                        break;
                }

                // Reading excel file from start point to end point.
                for(int m=0; m<checkpoints.size(); m++){
                    String issueKey = "";
                    String summary = "";
                    String description = "";
                    String onKosul = "";
                    String testGirdileri = "";
                    String varsayimlarVeKisitlamalar = "";
                    String manualTestStep = "[";
                    Test test = new Test();
                    int checkpoint = checkpoints.get(m);
                    int nextCheckpoint = -1;
                    ArrayList<Info> infos = new ArrayList<>();

                    // Fixing checkpoints
                    if(m+1 >= checkpoints.size()) nextCheckpoint = lastCheckpoint-1;
                    else nextCheckpoint = checkpoints.get(m+1);


                    // Getting infos from excel file.
                    for(int i=checkpoint; i<nextCheckpoint; i++){
                        if(sheet.getRow(i) == null || sheet.getRow(i).toString().equals(""))
                            break;
                        issueKey = sheet.getRow(i).getCell(0).toString();
                        summary = sheet.getRow(i).getCell(0).toString() + " - " + sheet.getRow(i).getCell(1).toString();
                        if(!sheet.getRow(i).getCell(5).toString().equals("")){
                            if(language.equals("English")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                description += "For_Step-"+num+":"+sheet.getRow(i).getCell(5).toString()+replaceChar;
                            }
                            else if(language.equals("Turkish")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                description += num+" numaralı Test Adımı için"+":"+sheet.getRow(i).getCell(5).toString()+replaceChar;
                            }

                        }
                        if(!sheet.getRow(i).getCell(8).toString().equals("")){
                            if(language.equals("English")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                onKosul += "For_Step-"+num+":"+sheet.getRow(i).getCell(8).toString()+replaceChar;
                            }
                            else if(language.equals("Turkish")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                onKosul += num+" numaralı Test Adımı için"+":"+sheet.getRow(i).getCell(8).toString()+replaceChar;
                            }
                        }
                        if(!sheet.getRow(i).getCell(9).toString().equals("")){
                            if(language.equals("English")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                testGirdileri += "For_Step-"+num+":"+sheet.getRow(i).getCell(9).toString()+replaceChar;
                            }
                            else if(language.equals("Turkish")) {
                                String num = ("0000" + i);
                                num = num.substring(num.length() - 4, num.length());
                                testGirdileri += num + " numaralı Test Adımı için" + ":" + sheet.getRow(i).getCell(9).toString() + replaceChar;
                            }
                        }
                        if(!sheet.getRow(i).getCell(10).toString().equals("")){
                            if(language.equals("English")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                varsayimlarVeKisitlamalar += "For_Step-"+num+":"+sheet.getRow(i).getCell(10).toString()+replaceChar;

                            }
                            else if(language.equals("Turkish")){
                                String num = ("0000"+i);
                                num = num.substring(num.length()-4,num.length());
                                varsayimlarVeKisitlamalar += num+" numaralı Test Adımı için"+":"+sheet.getRow(i).getCell(10).toString()+replaceChar;

                            }
                        }

                        // Filling ManualTestSteps
                        Info info = new Info();
                        info.setId(id++);
                        try{
                            info.setIndex(Integer.parseInt(sheet.getRow(i).getCell(2).toString().substring(0,sheet.getRow(i).getCell(2).toString().indexOf("."))));
                        }
                        catch (StringIndexOutOfBoundsException e){
                            //e.printStackTrace();
                        }
                        info.setStep(sheet.getRow(i).getCell(3).toString());
                        info.setResult(sheet.getRow(i).getCell(4).toString());
                        info.setData(sheet.getRow(i).getCell(7).toString());
                        info.setAttachments(new String[0]);
                        manualTestStep += gsonBuilder.toJson(info);
                        if(i+1 != nextCheckpoint)
                            manualTestStep += ",";


                        // Filling in the appropriate classes
                        infos.add(info);
                        test.setSummary(summary);
                        test.setIssueID("124900");
                        test.setConditions(onKosul);
                        test.setTestInputs(testGirdileri);
                        test.setAssumptionsAndConstraints(varsayimlarVeKisitlamalar);
                        test.setDescription(description);
                        test.setIssueKey(issueKey);
                        test.setManualTestSteps(manualTestStep);
                    }

                    // Adding manualTestSteps
                    test.setManualTestSteps(test.getManualTestSteps()+"]");


                    if(test.getDescription().equals(""))
                        test.setDescription("YOKTUR");
                    if(test.getConditions().equals(""))
                        test.setConditions("YOKTUR");
                    if(test.getTestInputs().equals(""))
                        test.setTestInputs("YOKTUR");
                    if(test.getAssumptionsAndConstraints().equals(""))
                        test.setAssumptionsAndConstraints("YOKTUR");
                    tests.add(test);
                }
            }
        }
        catch (Exception e){ // If there is an exception this code part will be print the exception to errorLog.file
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            System.out.println(sStackTrace);
        }
        // Calling write method
        try{
            write(tests,saveFilename,replaceChar,sheetNames);
        }
        catch (Exception e){ // If there is an exception this code part will be print the exception to errorLog.file
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            System.out.println(sStackTrace);
        }
    }

    /**
     * This method gets informations from read method and writing to csv file.
     * @param tests Tests Arraylist
     * @param filename The name of file to be saved
     * @param charToReplace \n replacement character
     * @param sheetNames Sheet names
     */
    private static void write(ArrayList<Test> tests,String filename,String charToReplace,ArrayList<String> sheetNames){
        String filenameTBC;
        String filenameTBU;

        // Setting file names
        filenameTBC = filename.substring(0,filename.lastIndexOf("_"))+"_Test_Sets_TBC.csv";
        filenameTBU = filename;


        tests = WordToCSV.replaceSemicolons(tests); // Replacing semicolons, because semicolon made exceptions
        tests = WordToCSV.replaceBackslashN(tests,charToReplace); // Replacing  \n, because \n made exceptions


        try {
            //_Test_Sets_TBC writing
            FileWriter myWriter2 = null;
            myWriter2 = new FileWriter(filenameTBC);
            myWriter2.write("Issue Type;");
            myWriter2.write("Summary;");
            myWriter2.write("Description;");
            myWriter2.write("\n");

            //TBC writing
            FileWriter myWriter = new FileWriter(filenameTBU);
            myWriter.write("Issue Type;");
            myWriter.write("Summary;");
            myWriter.write("Description;");
            myWriter.write("Custom field (Manual Test Steps);");
            myWriter.write("Custom field (Assumptions & Constraints);");
            myWriter.write("Custom field (Test Inputs);");
            myWriter.write("Custom field (Conditions);");
            myWriter.write("Custom Integer-1;");
            myWriter.write("\n");
            int customInt = 10;

            // Writing all individual tests to TBC file.
            for(Test test : tests){
                    try{
                        myWriter.write("\""+"Test"+"\""+";");
                        // String lengths are checked because csv cells cannot receive more than 32760 characters.
                        myWriter.write("\""+WordToCSV.checkStringLength(test.getSummary())+"\""+";");
                        myWriter.write(""+ WordToCSV.checkStringLength(test.getDescription())+""+";");
                        myWriter.write(""+WordToCSV.checkStringLength(test.getManualTestSteps())+""+";");
                        myWriter.write(""+WordToCSV.checkStringLength(test.getAssumptionsAndConstraints())+""+";");
                        myWriter.write(""+WordToCSV.checkStringLength(test.getTestInputs())+""+";");
                        myWriter.write(""+WordToCSV.checkStringLength(test.getConditions())+""+";");
                        myWriter.write(""+(customInt)+""+";");
                        customInt+=10;
                        myWriter.write("\n");
                    }
                    catch (Exception e){ // If there is an exception this code part will be print the exception to errorLog.file
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String sStackTrace = sw.toString();
                        System.out.println(sStackTrace);
                    }

            }

            // Writing all individual sheets to Test_Steps_TBC file
            for(String sheet : sheetNames){
                try{
                    myWriter2.write("\""+"Test Set"+"\""+";");
                    myWriter2.write("\""+sheet+"\""+";");
                    myWriter2.write("\""+sheet+"\""+";");
                    myWriter2.write("\n");
                }
                catch (Exception e){ // If there is an exception this code part will be print the exception to errorLog.file
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String sStackTrace = sw.toString();
                    System.out.println(sStackTrace);
                }
            }

            // Closing writers
            myWriter2.close();
            myWriter.close();

            notification =  "Done! csv file generated.";
        }
        catch (Exception e) { // If there is an exception this code part will be print the exception to errorLog.file
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
    }
}
