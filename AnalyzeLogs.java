/**
 * Created by khuffak on 10/9/2018.
 * AnalyzeLogs
 */
package com.devops;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.io.BufferedReader;

import com.devops.kbhtest;

public class AnalyzeLogs {

    //final File folder = new File("\\\\nasv0052\\icp_devops_share\\Karl\\UserStories\\PB7\\US465333 - 5.4 SP2 Longevity testing - CMOracle-Round2\\US465333_CM54SP2_Ora_101718_0436pm_Q4B_v2_logs4/");
    //final File folder = new File("C:\\Dev\\LogAnalyzer\\TestLogs\\");
    final File folder = new File("C:\\Dev\\LogAnalyzer\\CTC_CM5.3.1 SP2_CU12_1.17.1.5373.logs");


    //String logPath = "//nasv0052/icp_devops_share/Karl/UserStories/PB3/US471275 - 5.3.1 CU12 CM performance testing/CTC_CM5.3.1 SP2_CU12_1.17.1.5370.logs/";
    String logPath = "C:\\Dev\\LogAnalyzer\\TestLogs\\";
    String logErrorFileName = "LogError.log";

    // Constructor
    public AnalyzeLogs() {
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(logPath + logErrorFileName)));
            StringBuffer stringBuffer = new StringBuffer();
            listFilesForFolder(folder, bwr, stringBuffer);

            //flush the stream
            bwr.flush();

            //close the stream
            bwr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            AnalyzeLogs readFile = new AnalyzeLogs();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void listFilesForFolder(final File folder, BufferedWriter bwr, StringBuffer stringBuffer) {
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.getName().contains("connector")) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry, bwr, stringBuffer);
                } else {
                    System.out.println(fileEntry.getName());
                    processLogFile(fileEntry, bwr, stringBuffer);
                }
            }
        }
    }

    public static void processLogFile(File fileEntry, BufferedWriter bwr, StringBuffer stringBuffer) {
        try {
//            File file = new File( fileEntry.toString());
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileEntry.toString()));

            String line;
            String failType = "ERROR";

            boolean errorLine = false;
            String threadNum = "";
            String lastThreadNum = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);

                if (!line.isEmpty()) {


                    if (line.indexOf("Thread") >= 0) {
                        threadNum = line.substring(31, 40).toString();
                        lastThreadNum = threadNum;
                    }

                    int errorType = line.indexOf(" ERROR ");
                    if (line.indexOf(" ERROR ") >= 0) {
                        //errorLine = true;
                        bwr.write(fileEntry.getParent() + ": ");
                        bwr.write(fileEntry.getName() + ": ");
                        bwr.newLine();
                        bwr.write(line.toString());
                        bwr.newLine();

                    }

                    if (line.indexOf("Caused by:") >= 0) {
                        bwr.write(line.toString());
                        bwr.newLine();
                        bwr.newLine();
                    }
                }
            }

            bufferedReader.close();
            //fileReader.close();
            //System.out.println("Contents of file:");
            //System.out.println(stringBuffer.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
   }

    /* https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html */
    public static void writeToFileZipFileContents(String zipFileName,
                                                  String outputFileName )
            throws java.io.IOException {

        java.nio.charset.Charset charset = java.nio.charset.StandardCharsets.US_ASCII;
        java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);

        // Open zip file and create output file with
        // try-with-resources statement

        try (
                java.util.zip.ZipFile zf =
                        new java.util.zip.ZipFile(zipFileName);
                java.io.BufferedWriter writer =
                        java.nio.file.Files.newBufferedWriter(outputFilePath, charset)
        ) {
            // Enumerate each entry
            for (java.util.Enumeration entries =
                 zf.entries(); entries.hasMoreElements();) {
                // Get the entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                String zipEntryName =
                        ((java.util.zip.ZipEntry)entries.nextElement()).getName() +
                                newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }
}