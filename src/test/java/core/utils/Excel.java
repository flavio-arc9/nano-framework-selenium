package core.utils;

import java.io.FileReader;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Excel {
    
    public static List<Map<String, String>> getData(String file) {
        List<Map<String, String>> mydata = new ArrayList<>();
    
        try {
            String environment = System.getProperty("cucumber.env");
            String ruta = System.getProperty("user.dir") + "/src/test/java/resources/fixtures/" + environment +"."+file;
            FileReader filereader = new FileReader(ruta);
            CSVReader csvReader = new CSVReaderBuilder(filereader).build();
    
            String[] headers = csvReader.readNext();
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                Map<String, String> TestDataInMap = new TreeMap<>();
                for (int i = 0; i < row.length; i++) {
                    TestDataInMap.put(headers[i], row[i]);
                }
                mydata.add(TestDataInMap);
            }
        } catch (Exception e) {}
        return mydata;
    }
}
