# ID3 Decision Tree

Machine Learning project 1

File description:
The files and folder(s) in this directory are:
1. lib: a directory contains dependency java libraries
2. ID3DecisionTree.jar: the executable jar file for the project
3. training.csv: a csv file containing training data
4. testing.csv: a csv file containing test data
5. results.csv: a csv file containing classified test data (will be generated after execution of the project)
6. ID3DecisionTree.log: a log file for the project (will be generated after first execution of the project)
7. README.md: this file

Preparation for project execution:
1. First we have to make sure Java Runtime Environment (JRE) is properly installed in the workstation (we used java 8 to develop the project)
2. "lib" directory and the files "training.csv" nad "testing.csv" should be in the same folder as the "ID3DecisionTree.jar" file

Running the project:
We can run the project using terminal command. The format of the command is:

    java -jar ID3DecisionTree.jar <FLAG_VAL> <CRITICAL_VAL>

        where FLAG_VAL is used to fix information gain measure. It can be either 0 or 1.
        FLAG_VAL = 0 means use Information Gain by Entropy
        FLAG_VAL = 1 means use Information Gain by GiniIndex

        CRITICAL_VAL can be a floating point real number which denotes the critical value that will  be used in chi-square tesing

Therefore, a sample execution command is:
    java -jar ID3DecisionTree.jar 1 8.72

However, the command line parameters are optional. Which means, we can run the project using the command:
    java -jar ID3DecisionTree.jar
In this case, the default params are:
    USE_GINI_INDEX
    CRITICAL_VAL = 12.59

Thank you and good luck!!
