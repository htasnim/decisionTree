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
import com.ashraf.course.machinelearning.id3decisiontree.utilities.Defs.NitrogenBase;
import com.ashraf.course.machinelearning.id3decisiontree.utilities.StatsUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Ashraf
 */
public class Id3Training {

    final static Logger logger = Logger.getLogger(Id3Training.class);

    public TreeNode buildTree() {
        Set<Integer> breakingPosList = new HashSet<Integer>();
        List<Entry> entryList = new ArrayList<Entry>();

        try {
            entryList = CsvUtils.getEntryListFromTrainingFile(Defs.TRAINNING_FILE_NAME);
        } catch (FileNotFoundException fileEx) {
            logger.error(fileEx.getMessage());
            logger.info("Failed to create Decision Tree...");
            return null;
        } catch (IOException ioEx) {
            logger.error(ioEx.getMessage());
            logger.info("Failed to create Decision Tree...");
            return null;
        }

        logger.info("Total size of training data: " + entryList.size());

        logger.info("Start: create Decision Tree...");
        long startTime = System.nanoTime();
        TreeNode root = createNode(entryList, breakingPosList, NitrogenBase.UNKNOWN);
        long endTime = System.nanoTime();
        logger.info("End: create Decision Tree...");
        logger.info("Training time: " + (endTime - startTime) / 1000000 + " miliseconds.");
        return root;
    }

    private TreeNode createNode(List<Entry> currEntryList, Set<Integer> breakingPosList, NitrogenBase nBase) {
        if (currEntryList == null || currEntryList.size() < 1) {
            return null;
        }

        TreeNode node = new TreeNode();

        // All entries are from same category. So this is a leaf node.
        if (isSameCategory(currEntryList)) {
            node.setLeafNode(Boolean.TRUE);
            node.setCategory(currEntryList.get(0).getCategory());
            node.setnBase(nBase);
            return node;
        }

        // Otherwise, this is not a leaf node. This node will have children
        // Calculate information gain.
        Double maxIG = Double.MIN_VALUE;
        Integer maxIgPos = -1;
        List<Entry> finalEntryListA = new ArrayList<Entry>();
        List<Entry> finalEntryListC = new ArrayList<Entry>();
        List<Entry> finalEntryListG = new ArrayList<Entry>();
        List<Entry> finalEntryListT = new ArrayList<Entry>();
        for (int i = 0; i < Defs.SEQ_LENGTH; i++) {

            if (breakingPosList.contains(i)) {
                continue;
            }
            List<Entry> currEntryListA = new ArrayList<Entry>();
            List<Entry> currEntryListC = new ArrayList<Entry>();
            List<Entry> currEntryListG = new ArrayList<Entry>();
            List<Entry> currEntryListT = new ArrayList<Entry>();

            for (Entry currEntry : currEntryList) {
                if (currEntry.getSequence().charAt(i) == 'A') {
                    currEntryListA.add(currEntry);
                } else if (currEntry.getSequence().charAt(i) == 'C') {
                    currEntryListC.add(currEntry);
                } else if (currEntry.getSequence().charAt(i) == 'G') {
                    currEntryListG.add(currEntry);
                } else if (currEntry.getSequence().charAt(i) == 'T') {
                    currEntryListT.add(currEntry);
                }
            }

            Double informationGain = StatsUtil.calculateInformationGain(currEntryList, currEntryListA, currEntryListC, currEntryListG, currEntryListT);
            if (informationGain >= maxIG) {
                maxIG = informationGain;
                maxIgPos = i;
                finalEntryListA = new ArrayList<Entry>(currEntryListA);
                finalEntryListC = new ArrayList<Entry>(currEntryListC);
                finalEntryListG = new ArrayList<Entry>(currEntryListG);
                finalEntryListT = new ArrayList<Entry>(currEntryListT);
            }
        }

        // Create children
        breakingPosList.add(maxIgPos);
        TreeNode childA = createNode(finalEntryListA, breakingPosList, NitrogenBase.A);
        TreeNode childC = createNode(finalEntryListC, breakingPosList, NitrogenBase.C);
        TreeNode childG = createNode(finalEntryListG, breakingPosList, NitrogenBase.G);
        TreeNode childT = createNode(finalEntryListT, breakingPosList, NitrogenBase.T);

        node.getChildren().add(childA);
        node.getChildren().add(childC);
        node.getChildren().add(childG);
        node.getChildren().add(childT);
        node.setBreakingPos(maxIgPos);
        node.setnBase(nBase);

        breakingPosList.remove(new Integer(maxIgPos));
        return node;
    }

    private Boolean isSameCategory(List<Entry> entryList) {
        if (entryList.size() < 2) {
            return Boolean.TRUE;
        }
        Defs.Category category = entryList.get(0).getCategory();
        for (int i = 1; i < entryList.size(); i++) {
            if (category != entryList.get(i).getCategory()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
