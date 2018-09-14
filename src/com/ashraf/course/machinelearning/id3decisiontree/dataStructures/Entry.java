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
 *
 * @author Ashraf
 */
public class Entry {

    private Integer id;
    private String sequence;
    private Integer lengthOfSeq;
    private Category category;
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
