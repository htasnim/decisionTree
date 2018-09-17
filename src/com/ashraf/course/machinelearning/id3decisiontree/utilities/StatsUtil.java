/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.utilities;

import com.ashraf.course.machinelearning.id3decisiontree.dataStructures.Entry;
import java.util.ArrayList;
import java.util.List;

/**
 * StatsUtil class contains all the statistical utility methods (i.e.
 * information gain, entropy, gini index) we need to calculate
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class StatsUtil {

    /**
     * This function is used to calculate chai square value from a parent data
     * entry set and four subset of data entry
     *
     * @param entryList parent data entry list
     * @param aEntryList subset of data entry list based on Nitrogen base A
     * @param cEntryList subset of data entry list based on Nitrogen base C
     * @param gEntryList subset of data entry list based on Nitrogen base G
     * @param tEntryList subset of data entry list based on Nitrogen base T
     * @return calculated chai square value
     * @since version 1.0.0
     */
    public static Double calculateChiSquare(List<Entry> entryList, List<Entry> aEntryList, List<Entry> cEntryList, List<Entry> gEntryList, List<Entry> tEntryList) {

        // Calculate the table to calculate chai square
        Integer observed_EI_A = getSubSetOfEntry(aEntryList, Defs.Category.EI).size();
        Integer observed_EI_C = getSubSetOfEntry(cEntryList, Defs.Category.EI).size();
        Integer observed_EI_G = getSubSetOfEntry(gEntryList, Defs.Category.EI).size();
        Integer observed_EI_T = getSubSetOfEntry(tEntryList, Defs.Category.EI).size();

        Integer observed_IE_A = getSubSetOfEntry(aEntryList, Defs.Category.IE).size();
        Integer observed_IE_C = getSubSetOfEntry(cEntryList, Defs.Category.IE).size();
        Integer observed_IE_G = getSubSetOfEntry(gEntryList, Defs.Category.IE).size();
        Integer observed_IE_T = getSubSetOfEntry(tEntryList, Defs.Category.IE).size();

        Integer observed_N_A = getSubSetOfEntry(aEntryList, Defs.Category.N).size();
        Integer observed_N_C = getSubSetOfEntry(cEntryList, Defs.Category.N).size();
        Integer observed_N_G = getSubSetOfEntry(gEntryList, Defs.Category.N).size();
        Integer observed_N_T = getSubSetOfEntry(tEntryList, Defs.Category.N).size();

        Integer P = aEntryList.size();
        Integer Q = cEntryList.size();
        Integer R = gEntryList.size();
        Integer S = tEntryList.size();

        Integer A = getSubSetOfEntry(entryList, Defs.Category.EI).size();
        Integer B = getSubSetOfEntry(entryList, Defs.Category.IE).size();
        Integer C = getSubSetOfEntry(entryList, Defs.Category.N).size();
        Integer TOTAL = entryList.size();

        // Calculate chai square based on the equation
        Double chaiSq = calculateExpectedProb(observed_EI_A, A, P, TOTAL)
                + calculateExpectedProb(observed_EI_C, A, Q, TOTAL)
                + calculateExpectedProb(observed_EI_G, A, R, TOTAL)
                + calculateExpectedProb(observed_EI_T, A, S, TOTAL)
                + calculateExpectedProb(observed_IE_A, B, P, TOTAL)
                + calculateExpectedProb(observed_IE_C, B, Q, TOTAL)
                + calculateExpectedProb(observed_IE_G, B, R, TOTAL)
                + calculateExpectedProb(observed_IE_T, B, S, TOTAL)
                + calculateExpectedProb(observed_N_A, C, P, TOTAL)
                + calculateExpectedProb(observed_N_C, C, Q, TOTAL)
                + calculateExpectedProb(observed_N_G, C, R, TOTAL)
                + calculateExpectedProb(observed_N_T, C, S, TOTAL);

        return chaiSq;
    }

    /**
     * This function is used to calculate Gini index information gain value from
     * a parent data entry set and four subset of data entry
     *
     * @param entryList parent data entry list
     * @param aEntryList subset of data entry list based on Nitrogen base A
     * @param cEntryList subset of data entry list based on Nitrogen base C
     * @param gEntryList subset of data entry list based on Nitrogen base G
     * @param tEntryList subset of data entry list based on Nitrogen base T
     * @return calculated Gini index value
     * @since version 1.0.0
     */
    public static Double calculateGiniIndex(List<Entry> entryList, List<Entry> aEntryList, List<Entry> cEntryList, List<Entry> gEntryList, List<Entry> tEntryList) {

        Integer sizeParent = entryList.size();
        Integer sizeA = aEntryList.size();
        Integer sizeC = cEntryList.size();
        Integer sizeG = gEntryList.size();
        Integer sizeT = tEntryList.size();

        Double giniParent = 1.0 - calculateProbOfFeature(entryList, Defs.Category.EI) - calculateProbOfFeature(entryList, Defs.Category.IE) - calculateProbOfFeature(entryList, Defs.Category.N);

        Double giniA = 1.0 - calculateProbOfFeature(aEntryList, Defs.Category.EI) - calculateProbOfFeature(aEntryList, Defs.Category.IE) - calculateProbOfFeature(aEntryList, Defs.Category.N);
        Double giniC = 1.0 - calculateProbOfFeature(cEntryList, Defs.Category.EI) - calculateProbOfFeature(cEntryList, Defs.Category.IE) - calculateProbOfFeature(cEntryList, Defs.Category.N);
        Double giniG = 1.0 - calculateProbOfFeature(gEntryList, Defs.Category.EI) - calculateProbOfFeature(gEntryList, Defs.Category.IE) - calculateProbOfFeature(gEntryList, Defs.Category.N);
        Double giniT = 1.0 - calculateProbOfFeature(tEntryList, Defs.Category.EI) - calculateProbOfFeature(tEntryList, Defs.Category.IE) - calculateProbOfFeature(tEntryList, Defs.Category.N);

        Double gSplit = (double) sizeA / (double) sizeParent * giniA
                + (double) sizeC / (double) sizeParent * giniC
                + (double) sizeG / (double) sizeParent * giniG
                + (double) sizeT / (double) sizeParent * giniT;
        return giniParent - gSplit;
    }

    /**
     * The method calculateInformationGain calculates information gain for a
     * parent DNA sequence to four subsets of DNA sequences divided based in
     * different nitrogen bases
     *
     * @param entryList set of DNA sequences for parent node in the decision
     * tree
     * @param aEntryList set of DNA sequences with nitrogen base A in a given
     * position
     * @param cEntryList set of DNA sequences with nitrogen base C in a given
     * position
     * @param gEntryList set of DNA sequences with nitrogen base G in a given
     * position
     * @param tEntryList set of DNA sequences with nitrogen base T in a given
     * position
     * @return information gain based on the DNA sequences
     * @since version 1.0.0
     */
    public static Double calculateInformationGain(List<Entry> entryList, List<Entry> aEntryList, List<Entry> cEntryList, List<Entry> gEntryList, List<Entry> tEntryList) {

        Double pA = (double) aEntryList.size() / (double) entryList.size();
        Double pC = (double) cEntryList.size() / (double) entryList.size();
        Double pG = (double) gEntryList.size() / (double) entryList.size();
        Double pT = (double) tEntryList.size() / (double) entryList.size();

        Double entropyTarget = Double.isNaN(calculateEntrory(entryList)) ? 0.0 : calculateEntrory(entryList);
        Double entropyA = Double.isNaN(calculateEntrory(aEntryList)) ? 0.0 : calculateEntrory(aEntryList);
        Double entropyC = Double.isNaN(calculateEntrory(cEntryList)) ? 0.0 : calculateEntrory(cEntryList);
        Double entropyG = Double.isNaN(calculateEntrory(gEntryList)) ? 0.0 : calculateEntrory(gEntryList);
        Double entropyT = Double.isNaN(calculateEntrory(tEntryList)) ? 0.0 : calculateEntrory(tEntryList);

        Double entropyAttributes = pA * entropyA + pC * entropyC + pG * entropyG + pT * entropyT;
        Double informationGain = entropyTarget - entropyAttributes;

        return informationGain;
    }

    /**
     * The method calculateEntrory is subroutine to calculate the information
     * gain
     *
     * @param entryList set of DNA sequences of three classes (i.e. EI, IE, N)
     * @return entropy based on given set of DNA sequences
     * @since version 1.0.0
     */
    private static Double calculateEntrory(List<Entry> entryList) {
        int dataSize = entryList.size();
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

        Double probOfEi = (double) eiCount / (double) dataSize;
        Double probOfIe = (double) ieCount / (double) dataSize;
        Double probOfN = (double) nCount / (double) dataSize;
        Double entropy = (-1.0) * ((probOfEi * MathUtils.log2(probOfEi)) + (probOfIe * MathUtils.log2(probOfIe)) + (probOfN * MathUtils.log2(probOfN)));

        return entropy;
    }

    // This private method is a subroutine to calculate Gini index information gain value
    private static Double calculateProbOfFeature(List<Entry> entryList, Defs.Category currentCategory) {
        int dataSize = entryList.size();
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
        if (currentCategory == Defs.Category.EI) {
            return Math.pow((double) eiCount / (double) dataSize, 2.0);
        } else if (currentCategory == Defs.Category.IE) {
            return Math.pow((double) ieCount / (double) dataSize, 2.0);
        } else {
            return Math.pow((double) nCount / (double) dataSize, 2.0);
        }
    }

    // This private method is a subroutine to calculate different statistical analysis. This function
    // creates a ubset of data entry list based on a given class from a data entry list
    private static List<Entry> getSubSetOfEntry(List<Entry> entryList, Defs.Category category) {
        List<Entry> returnEntryList = new ArrayList<Entry>();
        for (Entry currEntry : entryList) {
            if (currEntry.getCategory() == category) {
                returnEntryList.add(currEntry);
            }
        }
        return returnEntryList;
    }

    // This private methid is a subroutine to calculate chai square value
    private static Double calculateExpectedProb(Integer observedVal, Integer rowSum, Integer colSum, Integer total) {
        Double expectedVal = (rowSum.doubleValue() * colSum.doubleValue()) / total.doubleValue();
        return Math.pow((observedVal.doubleValue() - expectedVal), 2.0) / expectedVal;
    }
}
