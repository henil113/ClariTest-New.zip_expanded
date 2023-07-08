package com.test.csv;

import com.test.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    private final String SAMPLE_CSV_FILE_PATH = "/raw_fees.csv";

    public List<Data> parseCSV() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(SAMPLE_CSV_FILE_PATH);
        Reader reader = null;
		reader = new InputStreamReader(inputStream);
        CSVParser csvParser = new CSVParser(
            reader,
            CSVFormat.DEFAULT
                .withHeader(
                    "Id", "Name", "Description__c", "Department__c",
                    "Category__c", "Sub_Category__c", "Type__c",
                    "Quantity__c","Unit_Price__c"
                )
                .withIgnoreHeaderCase()
                .withTrim()
        );

        // Reading all records at once into memory
        List<CSVRecord> csvRecords = csvParser.getRecords();
        System.out.println("Size ::: " + csvRecords.size());
        List<Data> records = csvRecords.stream().skip(1).map(record -> buildingData(record)).collect(Collectors.toList());

        

        return records;
    }

    private Data buildingData(CSVRecord record){
        Data storedData = new Data();
        storedData.setId(record.get("Id"));
        storedData.setName(record.get("Name"));
        storedData.setDescription(record.get("Description__c"));
        storedData.setDepartment(new Department(record.get("Department__c")));
        storedData.setCategory(new Category(record.get("Category__c")));
        storedData.setSubCategory(new SubCategory(record.get("Sub_Category__c")));
        storedData.setType(new Type(record.get("Type__c")));
        storedData.setQuantity(Integer.parseInt(record.get("Quantity__c")));
        storedData.setUnitPrice(new BigDecimal(record.get("Unit_Price__c")));
        return storedData;
    }
}
