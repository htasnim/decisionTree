/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashraf.course.machinelearning.id3decisiontree.utilities;

/**
 * Java class mathUtil
 * The class contains small mathematical methods (i.e. log base 2 calculation) which we will need   
 * to calculate the statistical analysis
 *
 * @author Ashraf
 * @version 1.0.0, 09 Sept 2018
 */
public class MathUtils {

    public static Double log2(Double x) {
        if (x <= 0.0) {
            return 0.0;
        }
        return Math.log(x) / Math.log(2.0);
    }
}
