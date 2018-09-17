/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.dataStructures;

import com.ashraf.course.machinelearning.id3decisiontree.utilities.Defs.Category;
import java.util.HashSet;
import java.util.Set;

/**
 * Entry class is a data structure class which is used to store both training
 * and testing data entries in the project. Entry class stores ID, DNA sequence
 * and class of each data entry.
 *
 * @since version 1.0.0
 */
public class Entry {

    // ID of a data entry
    private Integer id;

    // DNA sequence of a data entry
    private String sequence;

    // DNA sequence length
    private Integer lengthOfSeq;

    // Data class (i.e doro, acceptor, neutral)
    private Category category;

    // Previously used attribute set
    private Set<Integer> divPositionSet;

    public Entry() {

    }

    public Entry(Integer id, String sequence) {
        this.id = id;
        this.sequence = sequence;
        this.lengthOfSeq = sequence.length();
        this.category = Category.UNKNOWN;
        this.divPositionSet = new HashSet<Integer>();
    }

    public Entry(Integer id, String sequence, String category) {
        this.id = id;
        this.sequence = sequence;
        this.lengthOfSeq = sequence.length();
        if (category.equalsIgnoreCase("EI")) {
            this.category = Category.EI;
        } else if (category.equalsIgnoreCase("IE")) {
            this.category = Category.IE;
        } else {
            this.category = Category.N;
        }
        this.divPositionSet = new HashSet<Integer>();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the divPositionSet
     */
    public Set<Integer> getDivPositionSet() {
        return divPositionSet;
    }

    /**
     * @param divPositionSet the divPositionSet to set
     */
    public void setDivPositionSet(Set<Integer> divPositionSet) {
        this.divPositionSet = divPositionSet;
    }

    /**
     * @return the lengthOfSeq
     */
    public Integer getLengthOfSeq() {
        return lengthOfSeq;
    }

    /**
     * @param lengthOfSeq the lengthOfSeq to set
     */
    public void setLengthOfSeq(Integer lengthOfSeq) {
        this.lengthOfSeq = lengthOfSeq;
    }
}
