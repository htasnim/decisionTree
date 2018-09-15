/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.utilities;

/**
 * Defs class contains all the constants, enums, and project mode values that we
 * will use throughout the project
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class Defs {

    // Length for each DNA sequence: hard coded for this experiment
    public static final Integer SEQ_LENGTH = 60;

    // Input file name for training data
    public static final String TRAINNING_FILE_NAME = "training.csv";

    // Input file name for testing data
    public static final String TESTING_FILE_NAME = "testing.csv";

    // Output file name to store the results
    public static final String RESULT_FILE_NAME = "results.csv";

    // Set of different DNA classes
    public static enum Category {
        N, EI, IE, UNKNOWN
    }

    // Set of different Nitrogen bases in DNA sequence
    public static enum NitrogenBase {
        A, G, C, T, INVALID, UNKNOWN
    }

    // Set of statistical analysis to split the tree
    public static enum MethodOfSplittingAttribute {
        INFORMATION_GAIN, GINI_INDEX
    }

    // Different modes for program cimpilation
    public static enum RunningModes {
        DEBUG, RELEASE
    }

    // Current ststistical analysis mode
    public static final MethodOfSplittingAttribute currSplittingMethod = MethodOfSplittingAttribute.INFORMATION_GAIN;

    // Current compilation mode
    public static final RunningModes currentRunningMode = RunningModes.RELEASE;

    // Critical values for Chi square testing. Uncomment any one of three
    public static final Double CTITICAL_VALUE = 16.81; // For ALPHA = 0.01
//    public static final Double CTITICAL_VALUE = 12.59; // For ALPHA = 0.05
//    public static final Double CTITICAL_VALUE = 0.872; // For ALPHA = 0.99
}
