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
 * Id3Training represents the training module of this project. This module is
 * called each time we need to build a decision tree based on some training data
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class Id3Training {

    final static Logger logger = Logger.getLogger(Id3Training.class);

    /**
     * buildTree method needs to be called each time we need to create a tree
     * using some training data. This method reads a csv file to read training
     * entries then uses createNode function to create the decision tree.
     *
     *
     * @return root node of the decision tree
     * @since version 1.0.0
     */
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
        logger.info("Selected evaluation criteria: " + Defs.currSplittingMethod);

        logger.info("Start: create Decision Tree...");
        logger.info("Using Chai sqaure test...");
        logger.info("Critical value: " + Defs.CRITICAL_VALUE);
        long startTime = System.nanoTime();
        TreeNode root = createNode(entryList, breakingPosList, NitrogenBase.UNKNOWN);
        long endTime = System.nanoTime();
        logger.info("End: create Decision Tree...");
        logger.info("Training time: " + (endTime - startTime) / 1000000 + " miliseconds.");
        return root;
    }

    /**
     * createTree is a recursive function which is used by buildTree function
     * and it is used to create the decision tree. From each call of createTree
     * function we get one tree node which are linked togather in the tree. To
     * create the tree using the training data createTree uses some statistical
     * analysis (i.e. information gain, gini index). The Methods to those
     * statistical analysis can be found in statsUti class.
     *
     * @param currEntryList a set of data entries which will be used to create
     * current node and it's children of the tree.
     * @param breakingPosList list of attributes where a decision have already
     * been made
     * @param nBase nitrogen base which is used to create surrent subset to
     * entries.
     * @return current node of the tree with it's children
     * @since version 1.0.0
     */
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
        // Calculate information gain for each unused attribute:
        Double maxIG = Double.MIN_VALUE;
        double maxGI = Double.MIN_VALUE;
        Integer breakingAttributePos = -1;
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

            // Create 4 subset for 4 different features of an attribute
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

            if (Defs.currSplittingMethod == Defs.MethodOfSplittingAttribute.INFORMATION_GAIN) {
                // Get information gain
                Double informationGain = StatsUtil.calculateInformationGain(currEntryList, currEntryListA, currEntryListC, currEntryListG, currEntryListT);
                if (informationGain >= maxIG) {
                    maxIG = informationGain;
                    breakingAttributePos = i;
                    finalEntryListA = new ArrayList<Entry>(currEntryListA);
                    finalEntryListC = new ArrayList<Entry>(currEntryListC);
                    finalEntryListG = new ArrayList<Entry>(currEntryListG);
                    finalEntryListT = new ArrayList<Entry>(currEntryListT);
                }
            } else if (Defs.currSplittingMethod == Defs.MethodOfSplittingAttribute.GINI_INDEX) {
                Double giniIndex = StatsUtil.calculateGiniIndex(currEntryList, currEntryListA, currEntryListC, currEntryListG, currEntryListT);
                if (giniIndex >= maxGI) {
                    maxGI = giniIndex;
                    breakingAttributePos = i;
                    finalEntryListA = new ArrayList<Entry>(currEntryListA);
                    finalEntryListC = new ArrayList<Entry>(currEntryListC);
                    finalEntryListG = new ArrayList<Entry>(currEntryListG);
                    finalEntryListT = new ArrayList<Entry>(currEntryListT);
                }
            }
        }

        // Run chai-square test
        if (Defs.USE_CHAI_SQUARE == Boolean.TRUE) {
            Double chaiSquareVal = StatsUtil.calculateChiSquare(currEntryList, finalEntryListA, finalEntryListC, finalEntryListG, finalEntryListT);
            if (Double.isNaN(chaiSquareVal) != Boolean.TRUE && chaiSquareVal < Defs.CRITICAL_VALUE) {
                node.setLeafNode(Boolean.TRUE);
                node.setCategory(getMaxOccuredCategory(currEntryList));
                node.setnBase(nBase);
                return node;
            }
        }

        // Create children
        breakingPosList.add(breakingAttributePos);
        TreeNode childA = createNode(finalEntryListA, breakingPosList, NitrogenBase.A);
        TreeNode childC = createNode(finalEntryListC, breakingPosList, NitrogenBase.C);
        TreeNode childG = createNode(finalEntryListG, breakingPosList, NitrogenBase.G);
        TreeNode childT = createNode(finalEntryListT, breakingPosList, NitrogenBase.T);

        node.getChildren().add(childA);
        node.getChildren().add(childC);
        node.getChildren().add(childG);
        node.getChildren().add(childT);
        node.setBreakingPos(breakingAttributePos);
        node.setnBase(nBase);

        breakingPosList.remove(new Integer(breakingAttributePos));
        return node;
    }

    /**
     * isSameCategory function is used by createTree function to check if all
     * the entries of a list are of same class or not
     *
     * @param entryList a set of data entries
     * @return true if all the data entries are of same class, false otherwise
     * @since version 1.0.0
     */
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

    private Defs.Category getMaxOccuredCategory(List<Entry> entryList) {
        Integer eiCount, ieCount, nCount;
        eiCount = ieCount = nCount = 0;
        for (Entry currEntry : entryList) {
            switch (currEntry.getCategory()) {
                case EI:
                    eiCount++;
                    break;
                case IE:
                    ieCount++;
                    break;
                case N:
                    nCount++;
                    break;
                default:
                    break;
            }
        }
        if (eiCount >= ieCount && eiCount >= nCount) {
            return Defs.Category.EI;
        } else if (ieCount >= eiCount && eiCount >= nCount) {
            return Defs.Category.IE;
        }
        return Defs.Category.N;
    }
}
