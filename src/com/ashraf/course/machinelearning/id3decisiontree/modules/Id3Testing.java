/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.modules;

import com.ashraf.course.machinelearning.id3decisiontree.dataStructures.Entry;
import com.ashraf.course.machinelearning.id3decisiontree.dataStructures.TreeNode;
import com.ashraf.course.machinelearning.id3decisiontree.utilities.CsvUtils;
import com.ashraf.course.machinelearning.id3decisiontree.utilities.Defs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Id3Testing class represents the testing module of this project. This module
 * is called each time we need to run test on a given set of data
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class Id3Testing {

    final static Logger logger = Logger.getLogger(Id3Testing.class);

    /**
     * getTestResults is public function which is called each time we need to
     * runs tests on testing data. A decision tree must be given as a parameter
     * to run the tests. This function reads testing data based on a csv
     * filename define in Defs class. For each data entry, this method analyze
     * the decision tree and puts each entry in either of EI, IE, N class.
     *
     *
     * @param root node of the decision tree
     * @return classified List of Entry
     * @since version 1.0.0
     */
    public List<Entry> getTestResults(TreeNode root) {
        List<Entry> testEntryList = new ArrayList<Entry>();

        try {
            testEntryList = CsvUtils.getEntryListFromTestFile(Defs.TESTING_FILE_NAME);
        } catch (FileNotFoundException fileEx) {
            logger.error(fileEx.getMessage());
            logger.info("Could not run tests...");
            return null;
        } catch (IOException ioEx) {
            logger.error(ioEx.getMessage());
            logger.info("Could not run tests...");
            return null;
        }

        logger.info("Total size of test data: " + testEntryList.size());

        List<Entry> returnEntryList = new ArrayList<Entry>();

        logger.info("Start: run ID3 decition tree on test data...");
        long startTime = System.nanoTime();
        for (Entry currEntry : testEntryList) {
            returnEntryList.add(getEntryCategory(currEntry, root));
        }

        // If the resulting class is unknown, we assume that the DNA sequence does not contain any exxon or intron marker. Therefore it falls into N class
        for (Entry currEntry : returnEntryList) {
            if (currEntry.getCategory() == Defs.Category.UNKNOWN) {
                currEntry.setCategory(Defs.Category.N);
            }
        }
        long endTime = System.nanoTime();
        logger.info("End: run ID3 decition tree on test data...");
        logger.info("Testing time: " + (endTime - startTime) / 1000000 + " miliseconds.");

        return returnEntryList;
    }

    /**
     * getEntryCategory is a private function which is called to classify each
     * of the test data. This function traverses the decision tree by calling
     * itself recursively to find the best fit for an Entry.
     *
     *
     * @param entry single data entry
     * @param ndoe current node of the tree
     * @return root node of the decision tree
     * @since version 1.0.0
     */
    private Entry getEntryCategory(Entry entry, TreeNode node) {
        if (node == null) {
            return entry;
        }

        if (node.isLeafNode() == Boolean.TRUE) {
            entry.setCategory(node.getCategory());
            return entry;
        }

        if (node.getBreakingPos() < 0 || node.getBreakingPos() >= Defs.SEQ_LENGTH) {
            return entry;
        }

        Character nextFeature = entry.getSequence().charAt(node.getBreakingPos());
        Defs.NitrogenBase nBase;

        switch (nextFeature) {
            case 'A':
                nBase = Defs.NitrogenBase.A;
                break;
            case 'C':
                nBase = Defs.NitrogenBase.C;
                break;
            case 'G':
                nBase = Defs.NitrogenBase.G;
                break;
            case 'T':
                nBase = Defs.NitrogenBase.T;
                break;
            default:
                nBase = Defs.NitrogenBase.INVALID;
                break;
        }

        // Recursively call for any of the children nodes
        for (TreeNode child : node.getChildren()) {
            if (child != null) {
                if (child.getnBase() == nBase) {
                    return getEntryCategory(entry, child);
                }
            }
        }
        return entry;
    }
}
