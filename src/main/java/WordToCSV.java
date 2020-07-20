import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.util.*;




class WordToCSV {


    public static boolean isTBUExist;
    public static boolean isTBCExist;

    public static boolean isExists;
    public static String manualimportlabel = "";
    public static String summarytrimmedlabel = "";
    static String notification = "";
    static boolean isOleNeeded[];

    public static String generateCsv(String fileName, String charToReplace, String saveFileName, String issueKeyPrefix,String language) {
        //setting console output to errorLog.txt
        try{
            PrintStream fileOut = new PrintStream("./errorLog.txt");
            System.setOut(fileOut);
        }
        catch (Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
        ArrayList<Test> tests = null;


        if(language.equals("Turkish"))
            tests = readTurkish(fileName,issueKeyPrefix);
        else if(language.equals("English"))
            tests = readEnglish(fileName,issueKeyPrefix);


        if(fileName.equals("")){
            notification = "Docx file must be declared.";
            return notification;
        }
        write(tests,saveFileName,charToReplace);

        return notification;
    }

    public static ArrayList<Test> replaceSemicolons(ArrayList<Test> tests){
        ArrayList<Test> newTests = new ArrayList<>();
        for(Test test : tests){
            String issueKey = test.getIssueKey().replaceAll(";",":");
            String issueID = test.getIssueID().replaceAll(";",":");
            String summary = test.getSummary().replaceAll(";",":");
            String description = test.getDescription().replaceAll(";",":");
            String manualTestSteps = test.getManualTestSteps().replaceAll(";",":");
            String assumptionsAndConstraints = test.getAssumptionsAndConstraints().replaceAll(";",":");
            String testInputs = test.getTestInputs().replaceAll(";",":");
            String conditions = test.getConditions().replaceAll(";",":");
            Test newTest = new Test();
            newTest.setIssueKey(issueKey);
            newTest.setIssueID(issueID);
            newTest.setSummary(summary);
            newTest.setDescription(description);
            newTest.setManualTestSteps(manualTestSteps);
            newTest.setAssumptionsAndConstraints(assumptionsAndConstraints);
            newTest.setTestInputs(testInputs);
            newTest.setConditions(conditions);
            newTests.add(newTest);
        }
        return newTests;
    }

    public static ArrayList<Test> replaceBackslashN(ArrayList<Test> tests,String charToReplace){
        ArrayList<Test> newTests = new ArrayList<>();
        for(Test test : tests){
            String issueKey = test.getIssueKey().replaceAll("\n", charToReplace);
            String issueID = test.getIssueID().replaceAll("\n", charToReplace);
            String summary = test.getSummary().replaceAll("\n", charToReplace);
            String description = test.getDescription().replaceAll("\n", charToReplace);
            String manualTestSteps = test.getManualTestSteps().replaceAll("\n", charToReplace);
            String assumptionsAndConstraints = test.getAssumptionsAndConstraints().replaceAll("\n", charToReplace);
            String testInputs = test.getTestInputs().replaceAll("\n", charToReplace);
            String conditions = test.getConditions().replaceAll("\n", charToReplace);
            Test newTest = new Test();
            newTest.setIssueKey(issueKey);
            newTest.setIssueID(issueID);
            newTest.setSummary(summary);
            newTest.setDescription(description);
            newTest.setManualTestSteps(manualTestSteps);
            newTest.setAssumptionsAndConstraints(assumptionsAndConstraints);
            newTest.setTestInputs(testInputs);
            newTest.setConditions(conditions);
            newTests.add(newTest);
        }
        return newTests;
    }


    public static void write(ArrayList<Test> tests,String filename,String charToReplace){
        String filenameTBC;
        String filenameTBU;
        if(filename.contains("_")){
            filenameTBC = filename.substring(0,filename.lastIndexOf("_"))+"_TBC.csv";
            filenameTBU = filename.substring(0,filename.lastIndexOf("_"))+"_TBU.csv";
        }
        else{
            filenameTBC = filename.substring(0,filename.lastIndexOf("."))+"_TBC.csv";
            filenameTBU = filename.substring(0,filename.lastIndexOf("."))+"_TBU.csv";
        }
        tests = replaceSemicolons(tests);
        tests = replaceBackslashN(tests,charToReplace);

        File tbu = new File(filenameTBU);
        File tbc = new File(filenameTBC);

        if(tbu.exists())
            tbu.delete();
        if(tbc.exists())
            tbc.delete();

        if(!isExists){
            notification =  "Belirtilen Dosya Bulunamadı.";
        }
        else{
            try {
                FileWriter myWriter2 = null;
                for(Test test: tests){
                    if(test.getIssueKey().equals("")){
                        //TBC writing
                        myWriter2 = new FileWriter(filenameTBC);
                        myWriter2.write("Issue key;");
                        myWriter2.write("Issue Type;");
                        myWriter2.write("Issue id;");
                        myWriter2.write("Summary;");
                        myWriter2.write("Description;");
                        myWriter2.write("Custom field (Manual Test Steps);");
                        myWriter2.write("Custom field (Assumptions & Constraints);");
                        myWriter2.write("Custom field (Test Inputs);");
                        myWriter2.write("Custom field (Conditions);");
                        myWriter2.write("Manuel_Test_Step_Import_Needed_Label;");
                        myWriter2.write("Summary_Trimmed_Label;");
                        myWriter2.write("OLE_Objects_Needs_Manuel_Processing_Label;");
                        myWriter2.write("\n");
                        break;
                    }
                }

                //TBU writing
                FileWriter myWriter = new FileWriter(filenameTBU);
                myWriter.write("Issue key;");
                myWriter.write("Issue id;");
                myWriter.write("Summary;");
                myWriter.write("Description;");
                myWriter.write("Custom field (Manual Test Steps);");
                myWriter.write("Custom field (Assumptions & Constraints);");
                myWriter.write("Custom field (Test Inputs);");
                myWriter.write("Custom field (Conditions);");
                myWriter.write("Manuel_Test_Step_Import_Needed_Label;");
                myWriter.write("Summary_Trimmed_Label;");
                myWriter.write("OLE_Objects_Needs_Manuel_Processing_Label;");
                myWriter.write("\n");

                for(Test test : tests){

                    manualimportlabel = "";
                    summarytrimmedlabel = "";
                    if(!test.getIssueKey().equals("")){ // UPDATED
                        try{
                            myWriter.write("\""+checkStringLength(test.getIssueKey())+"\""+";");
                            myWriter.write("\""+checkStringLength(test.getIssueID())+"\""+";");
                           // myWriter.write("\""+checkStringLength(test.getSummary())+"\""+";");
                            if(test.getSummary().length() < 250){
                                myWriter.write("\""+test.getSummary()+"\""+";");
                            }
                            else{
                                myWriter.write("\""+test.getSummary().substring(0,249) +"\""+";");
                                summarytrimmedlabel = "Summary_Trimmed";
                            }
                            myWriter.write("\""+checkStringLength(test.getDescription())+"\""+";");
                            myWriter.write(""+checkStringLength(test.getManualTestSteps())+""+";");
                            myWriter.write("\""+checkStringLength(test.getAssumptionsAndConstraints())+"\""+";");
                            myWriter.write("\""+checkStringLength(test.getTestInputs())+"\""+";");
                            myWriter.write("\""+checkStringLength(test.getConditions())+"\""+";");
                            myWriter.write("\""+manualimportlabel+"\""+";");
                            myWriter.write("\""+summarytrimmedlabel+"\""+";");
                            if(isOleNeeded[tests.indexOf(test)])
                                myWriter.write("\""+"OLE_Objects_Needs_Manuel_Processing_Needed"+"\""+";");
                            else
                                myWriter.write("\""+""+"\""+";");
                            myWriter.write("\n");
                        }
                        catch (Exception e){
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            String sStackTrace = sw.toString(); // stack trace as a string
                            System.out.println(sStackTrace);
                        }
                    }
                    else{ // CREATED
                        try{
                            myWriter2.write("\""+test.getIssueKey()+"\""+";");
                            myWriter2.write("\""+"TEST"+"\""+";");
                            myWriter2.write("\""+test.getIssueID()+"\""+";");
                            if(test.getSummary().length() < 235){
                                myWriter2.write("\""+test.getSummary()+"\""+";");
                            }
                            else{
                                myWriter2.write("\""+test.getSummary().substring(0,249) +"\""+";");
                                summarytrimmedlabel = "Summary_Trimmed";
                            }
                            myWriter2.write("\""+test.getDescription()+"\""+";");
                            myWriter2.write(""+checkStringLength(test.getManualTestSteps())+""+";");
                            myWriter2.write("\""+test.getAssumptionsAndConstraints()+"\""+";");
                            myWriter2.write("\""+test.getTestInputs()+"\""+";");
                            myWriter2.write("\""+test.getConditions()+"\""+";");
                            myWriter2.write("\""+manualimportlabel+"\""+";");
                            myWriter2.write("\""+summarytrimmedlabel+"\""+";");
                            if(isOleNeeded[tests.indexOf(test)])
                                myWriter2.write("\""+"OLE_Objects_Needs_Manuel_Processing_Needed"+"\""+";");
                            else
                                myWriter2.write("\""+""+"\""+";");
                            myWriter2.write("\n");
                        }
                        catch (Exception e){
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            String sStackTrace = sw.toString(); // stack trace as a string
                            System.out.println(sStackTrace);
                        }

                    }


                }
                if(myWriter2 != null)
                    myWriter2.close();
                myWriter.close();
                notification =  "Done! csv file generated.";
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                System.out.println(sStackTrace);
            }
        }

    }
    public static String checkStringLength(String s){
        if(s.length() > 32760){
            manualimportlabel = "Manuel_test_steps_import_needed";
            return s.substring(0,30000);
        }
        else{
            return s;
        }
    }


    public static String removeEqualsSymbol(String s){
        if(s.length() > 1 && s.charAt(0) == '-') return s.substring(1);
        else return s;
    }


    public static ArrayList<Test> readEnglish(String fileName,String issueKeyPrefix){
        int testCount=0;
        ArrayList<Test> tests = new ArrayList<>();
        try {
            File checkIsExist = new File(fileName);
            isExists = checkIsExist.exists();
            if(!isExists){
                notification = "File Not Found";
                return null;
            }

            FileInputStream fis = new FileInputStream(fileName);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFTable> tables = xdoc.getTables();
            int size = tables.size();
            ArrayList<ArrayList<String>> infos = new ArrayList<ArrayList<String>>(size);




            //finding start point of tests
            int k=0;
            for(int i=0; i<tables.size(); i++){
                //System.out.println(i+tables.get(i).getRow(0).getTable().getText());
                if(!tables.get(i).getRow(0).getTable().getText().contains("P/F/NA")){
                    k++;
                }
                else{
                    break;
                }
            }


            //counting test numbers
            int m=0;
            for(int i=k; i<tables.size(); i++){
                //System.out.println(i+tables.get(i).getRow(0).getTable().getText());
                if(tables.get(i).getRow(0).getTable().getText().contains("P/F/NA")){
                    testCount++;
                }
            }



            isOleNeeded = new boolean[testCount];
            //System.out.println(k);
            int a = 0;
            for(; k<size; k++){
                XWPFTable s = tables.get(k);
                //System.out.println(s.getText());
                if(s.getText().contains("P/F/NA")){
                    infos.add(new ArrayList<String>());
                    a++;
                }
                else{
                    if(a<testCount)
                        isOleNeeded[a] = true;
                    continue;
                }
                for(int i=2; i<s.getNumberOfRows(); i++){
                    /*if(!s.getRow(i).getTable().getText().contains("Adım No\tTest Adımı\tBeklenen Test Sonuçları\tAçıklamalar\tSonuç\tGereksinim No\n" +
                            "G/K/UD *")){
                        infos.remove(k);
                        continue;
                    }*/
                    for(int j=0; j<s.getRow(i).getTableCells().size(); j++){
                        //infos.get(k).add(s.getRow(i).getCell(j).getText());

                        infos.get(a-1).add(s.getRow(i).getCell(j).getText());
                        //System.out.println("i:" + i + " j:" + j + " " +s.getRow(i).getCell(j).getText());
                    }
                }
            }

            tests = fillManuelSteps(infos);
            //System.out.println(isOleNeeded[0]);


            List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

            ArrayList<XWPFParagraph> neww = new ArrayList<>();
            for(XWPFParagraph p : paragraphList){
                if(!p.getText().equals("") || !p.getText().equals("\n"))
                    neww.add(p);
            }

            paragraphList = neww;

            int prev=-1;
            int[] checkpoints = new int[tests.size()+1];



            int testStepsCount = 0;
            boolean isStartPointFound = false;
            int count = 1;
            String issueKeyPrefixWithChar = "|" + issueKeyPrefix;
            for(int i=0; i<paragraphList.size(); i++){
                if((paragraphList.get(i).getText().startsWith(issueKeyPrefix) && !isStartPointFound) || (paragraphList.get(i).getText().startsWith(issueKeyPrefixWithChar) && !isStartPointFound)/*paragraphList.get(i).getText().contains("TEST AÇIKLAMALARI")*/){
                    checkpoints[0] = i;
                    isStartPointFound = true;
                    //checkpoints[0] = i+2;
                }
                if(paragraphList.get(i).getText().contains("Test Steps")){
                    testStepsCount++;
                    if(prev == -1){
                        prev = i;
                    }
                    else{

                        String str = paragraphList.get(i-(i-prev)+3).getText();
                        if(!paragraphList.get(i-(i-prev)+3).getText().startsWith(issueKeyPrefix) && !paragraphList.get(i-(i-prev)+3).getText().startsWith(issueKeyPrefixWithChar)){
                            int s=1;
                            str = paragraphList.get(i-(i-prev)+s).getText();
                            while(!paragraphList.get(i-(i-prev)+s).getText().startsWith(issueKeyPrefix) && !paragraphList.get(i-(i-prev)+s).getText().startsWith(issueKeyPrefixWithChar)){
                                s++;
                            }
                            checkpoints[count] = i-(i-prev)+s;
                            count++;
                            prev = i;
                        }
                        else{
                            checkpoints[count] = i-(i-prev)+3;
                            count++;
                            prev = i;
                        }

                    }

                }
            }


            for (int i=0; i<checkpoints.length-1; i++){

                int checkpoint = checkpoints[i];
                int length = checkpoints[i+1] - checkpoints[i];
                String issueKeyAndID = "";
                String issueKey = "";
                String issueId = "";
                String description = "";
                String summary = "";
                String assumptionsAndConstraints = "";
                String testInput = "";
                String conditions = "";


                if(length < 0)
                    length = checkpoints[i] - checkpoints[i-1];


                /*ArrayList<String> list = new ArrayList<>();
                for(int h=0; h<length;h++){
                    list.add(paragraphList.get(checkpoint+h).getText());
                    System.out.println(h+ " " +paragraphList.get(checkpoint+h).getText());
                }*/



                issueKeyAndID = paragraphList.get(checkpoint).getText();
                if(!issueKeyAndID.equals("") && issueKeyAndID.contains("|")){
                    String temp = issueKeyAndID;
                    temp = temp.substring(temp.indexOf("|")+1);
                    temp = temp.substring(0,temp.indexOf("|"));
                    String summaryTemp = issueKeyAndID.substring(issueKeyAndID.lastIndexOf("|")+4);
                    issueKey = temp;
                    summary = summaryTemp;


                }
                else{
                    issueKey = "";
                    summary = issueKeyAndID;
                }


                issueId = "124900";

                int descriptionIndex = 0;
                int conditionsIndex = 0;
                int testInputIndex = 0;
                int assumptionsAndConstraintsIndex = 0;
                int testStepsIndex = 0;


                for(int b=0; b<length; b++){
                    String paragraph = "";
                    try{
                        paragraph= paragraphList.get(checkpoint+b).getText().replaceAll(" ","");
                    }
                    catch (Exception e){
                        // continue no problem;
                    }

                    if(paragraph.equals("PrerequisiteConditions"))
                        conditionsIndex = b;
                    if(paragraph.equals("TestInputs"))
                        testInputIndex = b;
                    if(paragraph.equals("AssumptionsandConstraints"))
                        assumptionsAndConstraintsIndex = b;
                    if(paragraph.equals("TestSteps"))
                        testStepsIndex = b;
                }

                for(int z=descriptionIndex+1; z<conditionsIndex; z++){
                    description += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=conditionsIndex+1; z<testInputIndex; z++){
                    conditions += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=testInputIndex+1; z<assumptionsAndConstraintsIndex; z++){
                    testInput += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=assumptionsAndConstraintsIndex+1; z<testStepsIndex; z++){
                    assumptionsAndConstraints += paragraphList.get(checkpoint+z).getText();
                }


                tests.get(i).setIssueKey(removeEqualsSymbol(issueKey));
                tests.get(i).setIssueID(removeEqualsSymbol(issueId));
                tests.get(i).setDescription(removeEqualsSymbol(description));
                tests.get(i).setSummary(removeEqualsSymbol(summary));
                tests.get(i).setAssumptionsAndConstraints(removeEqualsSymbol(assumptionsAndConstraints));
                tests.get(i).setTestInputs(removeEqualsSymbol(testInput));
                tests.get(i).setConditions(removeEqualsSymbol(conditions));

            }



        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }

        int testSize = tests.size();
        for(int i=testCount; i<testSize; i++){
            tests.remove(testCount);
        }

        return tests;
    }

    public static ArrayList<Test> readTurkish(String fileName,String issueKeyPrefix){
        int testCount=0;
        ArrayList<Test> tests = new ArrayList<>();
        try {
            File checkIsExist = new File(fileName);
            isExists = checkIsExist.exists();
            if(!isExists){
                notification = "File Not Found";
                return null;
            }

            FileInputStream fis = new FileInputStream(fileName);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFTable> tables = xdoc.getTables();
            int size = tables.size();
            ArrayList<ArrayList<String>> infos = new ArrayList<ArrayList<String>>(size);




            int k=0;
            for(int i=0; i<tables.size(); i++){
                if(!tables.get(i).getRow(0).getTable().getText().contains("G/K/UD")){
                    k++;
                }
                else{
                    break;
                }
            }



            int m=0;
            for(int i=k; i<tables.size(); i++){
                //System.out.println(i+tables.get(i).getRow(0).getTable().getText());
                if(tables.get(i).getRow(0).getTable().getText().contains("G/K/UD")){
                    testCount++;
                }
            }



            isOleNeeded = new boolean[testCount];
            //System.out.println(k);
            int a = 0;
            for(; k<size; k++){
                XWPFTable s = tables.get(k);
                //System.out.println(s.getText());
                if(s.getText().contains("G/K/UD")){
                    infos.add(new ArrayList<String>());
                    a++;
                }
                else{
                    if(a<testCount)
                        isOleNeeded[a] = true;
                    continue;
                }
                for(int i=2; i<s.getNumberOfRows(); i++){
                    /*if(!s.getRow(i).getTable().getText().contains("Adım No\tTest Adımı\tBeklenen Test Sonuçları\tAçıklamalar\tSonuç\tGereksinim No\n" +
                            "G/K/UD *")){
                        infos.remove(k);
                        continue;
                    }*/
                    for(int j=0; j<s.getRow(i).getTableCells().size(); j++){
                        //infos.get(k).add(s.getRow(i).getCell(j).getText());

                        infos.get(a-1).add(s.getRow(i).getCell(j).getText());
                        //System.out.println("i:" + i + " j:" + j + " " +s.getRow(i).getCell(j).getText());
                    }
                }
            }

            tests = fillManuelSteps(infos);
            //System.out.println(isOleNeeded[0]);


            List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

            ArrayList<XWPFParagraph> neww = new ArrayList<>();
            for(XWPFParagraph p : paragraphList){
                if(!p.getText().equals("") || !p.getText().equals("\n"))
                    neww.add(p);
            }

            paragraphList = neww;

            int prev=-1;
            int[] checkpoints = new int[tests.size()+1];



            int testStepsCount = 0;
            boolean isStartPointFound = false;
            int count = 1;
            String issueKeyPrefixWithChar = "|" + issueKeyPrefix;
            for(int i=0; i<paragraphList.size(); i++){
                if((paragraphList.get(i).getText().startsWith(issueKeyPrefix) && !isStartPointFound) || (paragraphList.get(i).getText().startsWith(issueKeyPrefixWithChar) && !isStartPointFound)/*paragraphList.get(i).getText().contains("TEST AÇIKLAMALARI")*/){
                    checkpoints[0] = i;
                    isStartPointFound = true;
                    //checkpoints[0] = i+2;
                }
                if(paragraphList.get(i).getText().contains("Test Adımları")){
                    testStepsCount++;
                    if(prev == -1){
                        prev = i;
                    }
                    else{

                        String str = paragraphList.get(i-(i-prev)+3).getText();
                        if(!paragraphList.get(i-(i-prev)+3).getText().startsWith(issueKeyPrefix) && !paragraphList.get(i-(i-prev)+3).getText().startsWith(issueKeyPrefixWithChar)){
                            int s=1;
                            str = paragraphList.get(i-(i-prev)+s).getText();
                            while(!paragraphList.get(i-(i-prev)+s).getText().startsWith(issueKeyPrefix) && !paragraphList.get(i-(i-prev)+s).getText().startsWith(issueKeyPrefixWithChar)){
                                s++;
                            }
                            checkpoints[count] = i-(i-prev)+s;
                            count++;
                            prev = i;
                        }
                        else{
                            checkpoints[count] = i-(i-prev)+3;
                            count++;
                            prev = i;
                        }

                    }

                }
            }


            for (int i=0; i<checkpoints.length-1; i++){

                int checkpoint = checkpoints[i];
                int length = checkpoints[i+1] - checkpoints[i];
                String issueKeyAndID = "";
                String issueKey = "";
                String issueId = "";
                String description = "";
                String summary = "";
                String assumptionsAndConstraints = "";
                String testInput = "";
                String conditions = "";


                if(length < 0)
                    try{
                        length = checkpoints[i] - checkpoints[i-1];
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        length = checkpoints[i];
                    }


                //unlock this when debugging
                /*ArrayList<String> list = new ArrayList<>();
                for(int h=0; h<length;h++){
                    list.add(paragraphList.get(checkpoint+h).getText());
                    System.out.println(h+ " " +paragraphList.get(checkpoint+h).getText());
                }*/



                issueKeyAndID = paragraphList.get(checkpoint).getText();
                if(!issueKeyAndID.equals("") && issueKeyAndID.contains("|")){
                    String temp = issueKeyAndID;
                    temp = temp.substring(temp.indexOf("|")+1);
                    temp = temp.substring(0,temp.indexOf("|"));
                    String summaryTemp = issueKeyAndID.substring(issueKeyAndID.lastIndexOf("|")+4);
                    issueKey = temp;
                    summary = summaryTemp;


                }
                else{
                    issueKey = "";
                    summary = issueKeyAndID;
                }


                issueId = "124900";

                int descriptionIndex = 0;
                int conditionsIndex = 0;
                int testInputIndex = 0;
                int assumptionsAndConstraintsIndex = 0;
                int testStepsIndex = 0;


                for(int b=0; b<length; b++){
                    String paragraph = "";
                    try {
                        paragraph= paragraphList.get(checkpoint+b).getText().replaceAll(" ","");
                    }
                    catch (Exception e){
                        // no problem continue
                    }
                    if(paragraph.equals("ÖnKoşullar"))
                        conditionsIndex = b;
                    if(paragraph.equals("TestGirdileri"))
                        testInputIndex = b;
                    if(paragraph.equals("VarsayımlarveKısıtlamalar"))
                        assumptionsAndConstraintsIndex = b;
                    if(paragraph.equals("TestAdımları"))
                        testStepsIndex = b;
                }

                for(int z=descriptionIndex+1; z<conditionsIndex; z++){
                    description += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=conditionsIndex+1; z<testInputIndex; z++){
                    conditions += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=testInputIndex+1; z<assumptionsAndConstraintsIndex; z++){
                    testInput += paragraphList.get(checkpoint+z).getText();
                }
                for(int z=assumptionsAndConstraintsIndex+1; z<testStepsIndex; z++){
                    assumptionsAndConstraints += paragraphList.get(checkpoint+z).getText();
                }


                tests.get(i).setIssueKey(removeEqualsSymbol(issueKey));
                tests.get(i).setIssueID(removeEqualsSymbol(issueId));
                tests.get(i).setDescription(removeEqualsSymbol(description));
                tests.get(i).setSummary(removeEqualsSymbol(summary));
                tests.get(i).setAssumptionsAndConstraints(removeEqualsSymbol(assumptionsAndConstraints));
                tests.get(i).setTestInputs(removeEqualsSymbol(testInput));
                tests.get(i).setConditions(removeEqualsSymbol(conditions));

           }



        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }

        int testSize = tests.size();
        for(int i=testCount; i<testSize; i++){
            tests.remove(testCount);
        }

        return tests;
    }
    public static ArrayList<Test> fillManuelSteps(ArrayList<ArrayList<String>> infos){
        for(ArrayList<String> info : infos){
            String s = info.get(info.size()-1);
            String s2  = info.get(info.size()-1).replaceAll(" ","");
            if(info.get(info.size()-1).replaceAll(" ","").contains("*:G:GeçtiK:KaldıUD:UygulanabilirDeğil"))
                info.remove(info.size()-1);
        }

        ArrayList<Test> tests = new ArrayList<Test>(infos.size());
        int idNumber = 1000;
        for(int i=0; i<infos.size(); i++){
            Test test = new Test();
            Info info = new Info();
            Gson gsonBuilder = new GsonBuilder().create();
            String manualTestStep = "[";
            for(int j=0; j<infos.get(i).size(); j++){
                try{
                    switch (j%6) {
                        case 0:
                            info.setIndex(Integer.parseInt(infos.get(i).get(j)));
                            break;
                        case 1:
                            info.setStep(infos.get(i).get(j));
                            break;
                        case 2:
                            info.setResult(infos.get(i).get(j));
                            break;
                        case 3:
                            info.setAttachments(new String[0]);
                            break;
                        case 4:
                            info.setId(idNumber);
                            idNumber++;
                            break;
                        case 5:
                            info.setData(infos.get(i).get(j));
                            manualTestStep += gsonBuilder.toJson(info);
                            if(j+1 != infos.get(i).size())
                                manualTestStep += ",";
                            break;
                    }
                }
                catch(Exception e){
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    System.out.println(sStackTrace);
                }
            }
            manualTestStep += "]";
            test.setManualTestSteps(manualTestStep);
            tests.add(test);
        }
        return tests;
    }

}