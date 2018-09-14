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

    // Different modes for program cimpilation
    public static enum RunningModes {
        DEBUG, RELEASE
    }

    // Current compilation mode
    public static final RunningModes currentRunningMode = RunningModes.RELEASE;
}
