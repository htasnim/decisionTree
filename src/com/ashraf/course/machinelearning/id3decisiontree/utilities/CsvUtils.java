/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.utilities;

import com.ashraf.course.machinelearning.id3decisiontree.dataStructures.Entry;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CsvUtils class contains all the utility methods we need to work on the csv
 * files (i.e. read, write and parse from the csv files)
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class CsvUtils {

    /**
     * The method getEntryListFromTrainingFile is used to parse all the training
     * DNA sequences from a given file
     *
     * @param fileName full file name with path of the csv file
     * @return list of DNA sequences
     * @since version 1.0.0
     */
    public static List<Entry> getEntryListFromTrainingFile(String fileName) throws FileNotFoundException, IOException {
        List<Entry> entryList = new ArrayList<Entry>();

        // Create an object of filereader 
        // class with CSV file as a parameter. 
        FileReader fileReader = new FileReader(fileName);

        // create csvReader object passing 
        // file reader as a parameter 
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(0).build();
        String[] lineEntry;

        // we are going to read data line by line 
        while ((lineEntry = csvReader.readNext()) != null) {
            Entry entry = new Entry(Integer.parseInt(lineEntry[0]), lineEntry[1], lineEntry[2]);
            entryList.add(entry);
        }

        return filterInvalidEntries(entryList);
    }

    /**
     * The method getEntryListFromTestFile is used to parse all the testing DNA
     * sequences from a given file
     *
     * @param fileName full file name with path of the csv file
     * @return list of DNA sequences
     * @since version 1.0.0
     */
    public static List<Entry> getEntryListFromTestFile(String fileName) throws FileNotFoundException, IOException {
        List<Entry> entryList = new ArrayList<Entry>();

        // Create an object of filereader 
        // class with CSV file as a parameter. 
        FileReader fileReader = new FileReader(fileName);

        // create csvReader object passing 
        // file reader as a parameter 
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(0).build();
        String[] lineEntry;

        // we are going to read data line by line 
        while ((lineEntry = csvReader.readNext()) != null) {
            Entry entry = new Entry(Integer.parseInt(lineEntry[0]), lineEntry[1]);
            entryList.add(entry);
        }

        return entryList;
    }

    /**
     * The method writeEntriesToCsvFile is used to write the results (i.e.
     * classified test DNA sequences) to a csv file
     *
     * @param fileName full file name with path of the csv file to be written
     * @since version 1.0.0
     */
    public static void writeEntriesToCsvFile(List<Entry> entryList, String fileName) throws FileNotFoundException, IOException {

        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter osw = new OutputStreamWriter(fos,
                        StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw)) {

            List<String[]> entries = new ArrayList<String[]>();
            String[] header = {"ID", "Class"};
            entries.add(header);

            for (Entry entry : entryList) {
                String[] entryArr = new String[2];
                entryArr[0] = entry.getId().toString();
                entryArr[1] = entry.getCategory().name();
                entries.add(entryArr);
            }

            writer.writeAll(entries);
        }
    }

    public static List<Entry> filterInvalidEntries(List<Entry> entryList) {
        List<Entry> filteredEntryList = new ArrayList<Entry>();
        for (Entry entry : entryList) {
            Boolean isValid = Boolean.TRUE;

            for (int i = 0; i < entry.getSequence().length(); i++) {
                if (entry.getSequence().charAt(i) != 'A'
                        && entry.getSequence().charAt(i) != 'C'
                        && entry.getSequence().charAt(i) != 'G'
                        && entry.getSequence().charAt(i) != 'T') {
                    isValid = Boolean.FALSE;
                    break;
                }
            }

            if (isValid) {
                filteredEntryList.add(entry);
            }
        }
        return filteredEntryList;
    }

    public static void main(String args[]) {
        try {
            List<Entry> entryList = getEntryListFromTrainingFile("training.csv");
        } catch (IOException ex) {
            Logger.getLogger(CsvUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
