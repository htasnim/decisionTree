/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.utilities;

import com.ashraf.course.machinelearning.id3decisiontree.dataStructures.Entry;
import java.util.List;

/**
 * StatsUtil class contains all the statistical utility methods (i.e.
 * information gain, entropy, gini index) we need to calculate
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class StatsUtil {

    public static Double calculateChiSquare() {
        return Math.random();
    }

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
}
