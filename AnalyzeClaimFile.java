package com.devops;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khuffak on 11/1/2018.
 */




public class AnalyzeClaimFile {

    private static final Map<Integer, String> MY_MAP = createMap();
    private static Map<Integer, String> createMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        result.put(1, "one");
        result.put(2, "two");
        return Collections.unmodifiableMap(result);
    }

    final File claimFile = new File("C:\\Dev\\ClaimFileAnalyzer\\XML_CombinedBusinessTest.xml.csv");
    // Constructor
    public AnalyzeClaimFile() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(claimFile.toString()));

            BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("C:/Dev/ClaimFileAnalyzer/TypeOfBill.log")));

            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                if (line.contains("<type-of-bill>")) {
                    //System.out.println(line.substring(line.indexOf("<type-of-bill>")+14, line.indexOf("<type-of-bill>")+17));
                    //stringBuffer.append(line.substring(line.indexOf("<type-of-bill>")+14, line.indexOf("<type-of-bill>")+17));

                    //stringBuffer.append(line);

                    bwr.newLine();
                    bwr.write(line.substring(line.indexOf("<type-of-bill>")+14, line.indexOf("<type-of-bill>")+17));

                    System.out.println(line.substring(line.indexOf("<type-of-bill>")+14, line.indexOf("<type-of-bill>")+17));
                }



//                if (!line.isEmpty()) {
//                    System.out.println(line);
//                }
            }

            //flush the stream
            bwr.flush();

            //close the stream
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            AnalyzeClaimFile acf = new AnalyzeClaimFile();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



}
