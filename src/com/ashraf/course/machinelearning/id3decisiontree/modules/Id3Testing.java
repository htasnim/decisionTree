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
 * @author Ashraf
 */
public class Id3Testing {

    final static Logger logger = Logger.getLogger(Id3Testing.class);

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
        long endTime = System.nanoTime();
        logger.info("End: run ID3 decition tree on test data...");
        logger.info("Testing time: " + (endTime - startTime) / 1000000 + " miliseconds.");
        
        return returnEntryList;
    }

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

        // System.out.println("Breaking pos: " + node.getBreakingPos());
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
