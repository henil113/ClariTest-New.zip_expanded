package com.test;

import com.test.csv.CSVReader;
import com.test.hierarchy.FeesHierarchyService;
import com.test.hierarchy.TreeNode;
import com.test.model.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static com.test.util.PrintUtils.println;

public class Application {

    public static void main(String[] args) {
             try {
            List<Data> records = new CSVReader().parseCSV();
            FeesHierarchyService hierarchy = new FeesHierarchyService();
            TreeNode<String> feeStructure = hierarchy.feeStructured(records);
            //hierarchy.printHierarchy(feeStructure);
            Scanner scannerObject = new Scanner(System.in);
            System.out.println("Enter Department Name: ");
            String departmentName = scannerObject.nextLine();
            System.out.println("Enter Category Name: ");
            String categoryName = scannerObject.nextLine();
            System.out.println("Enter SubCategory Name: ");
            String subCategoryName = scannerObject.nextLine();
            System.out.println("Enter Type Name: ");
            String typeName = scannerObject.nextLine();
            TreeNode<String> treeNode = hierarchy.searchNode(feeStructure,departmentName,categoryName,subCategoryName,typeName);
            System.out.println("Last Node which is found : " + treeNode.getName());
            hierarchy.printHierarchy(treeNode);
            BigDecimal totalFees = hierarchy.getTotalFees(treeNode, records);
          
           System.out.println("Total Fees ---" + totalFees);
           
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
