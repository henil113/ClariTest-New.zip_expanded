package com.test;

import com.test.csv.CSVReader;
import com.test.hierarchy.FeesHierarchyService;
import com.test.hierarchy.TreeNode;
import com.test.model.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FeesHierarchyTest {

    List<Data> dataValues;
    FeesHierarchyService hierarchyService;

    @BeforeEach
    void setUp() throws IOException {
        dataValues = new CSVReader().parseCSV();
        hierarchyService = new FeesHierarchyService();
    }

    @RepeatedTest(1)
    public void buildFeeHierarchy() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        Assertions.assertNotNull(feesHierarchyTree);
        Assertions.assertEquals(feesHierarchyTree.getName(), "ROOT");
        String[] expectedDepartments = {"Support", "Development","Sales","Operations","Marketing"};
        String[] actualDepartments = feesHierarchyTree.getChildren().stream()
                .map( e-> e.getName()).collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedDepartments, actualDepartments);
    }

    @RepeatedTest(1)
    public void searchNode_Support() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Support", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Support");
        String[] expectedCategories = {"Tier 1", "Tier 2","Tier 3"};
        String[] actualCategories = departmentTreeNode.getChildren().stream()
                .map( e-> e.getName()).sorted().collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedCategories, actualCategories);
    }

    @RepeatedTest(1)
    public void searchNode_Marketing() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Marketing", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Marketing");
        String[] expectedCategories = {"ABM"};
        String[] actualCategories = departmentTreeNode.getChildren().stream()
                .map( e-> e.getName()).sorted().collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedCategories, actualCategories);
    }

    @RepeatedTest(1)
    public void searchNode_Sales() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Sales", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Sales");
        String[] expectedCategories = {"Pre Sales", "Sales Engineering"};
        String[] actualCategories = departmentTreeNode.getChildren().stream()
                .map( e-> e.getName()).sorted().collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedCategories, actualCategories);
    }

    @RepeatedTest(1)
    public void searchNode_Development() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Development", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Development");
        String[] expectedCategories = {"Coding", "Quality Assurance"};
        String[] actualCategories = departmentTreeNode.getChildren().stream()
                .map( e-> e.getName()).sorted().collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedCategories, actualCategories);
    }

    @RepeatedTest(1)
    public void searchNode_Operations() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Operations", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Operations");
        String[] expectedCategories = {"Human Resources", "Performance Management"};
        String[] actualCategories = departmentTreeNode.getChildren().stream()
                .map( e-> e.getName()).sorted().collect(Collectors.toList()).toArray(new String[]{});
        Assertions.assertArrayEquals(expectedCategories, actualCategories);

    }

    @RepeatedTest(1)
    public void getTotalFees_Marketing() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Marketing", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Marketing");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("619524.202"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Sales() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Sales", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Sales");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("643947.652"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Development() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Development", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Development");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("666826.26"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Operations() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Operations", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Operations");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("467370.7745"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Support() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Support", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "Support");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("516008.9445"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Marketing_ABM() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> departmentTreeNode = hierarchyService.searchNode(feesHierarchyTree,"ABM", null, null, null);
        Assertions.assertNotNull(departmentTreeNode);
        Assertions.assertEquals(departmentTreeNode.getName(), "ABM");
        BigDecimal totalFees = hierarchyService.getTotalFees(departmentTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("619524.202"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Sales_PreSales() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Pre Sales", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Pre Sales");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("329646.672"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Sales_SalesEngineering() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Sales Engineering", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Sales Engineering");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("314300.98"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Development_Coding() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Coding", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Coding");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("327203.748"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Development_QualityAssurance() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Quality Assurance", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Quality Assurance");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("339622.512"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Operations_HumanResources() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Human Resources", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Human Resources");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("229041.0255"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Operations_PerformanceManagement() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("238329.749"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Support_Tier1() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Tier 1", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Tier 1");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("179710.17"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Support_Tier2() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Tier 2", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Tier 2");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("167030.463"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Support_Tier3() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Tier 3", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Tier 3");
        BigDecimal totalFees = hierarchyService.getTotalFees(categoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("169268.3115"));
    }

    @RepeatedTest(1)
    public void getTotalFees_Marketing_ABM_Cat1() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"ABM", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "ABM");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat1", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat1");
        BigDecimal totalFees = hierarchyService.getTotalFees(subCategoryTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("218216.262"));
    }
    @RepeatedTest(1)
    public void getTotalFees_Marketing_ABM_Cat1_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"ABM", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "ABM");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat1", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat1");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeA", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeA");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("65766.151"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Marketing_ABM_Cat2_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"ABM", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "ABM");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat2", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat2");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeA", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeA");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("69204.652"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Development_Coding_Cat1_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Coding", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Coding");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat1", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat1");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeA", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeA");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("37097.976"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Development_Coding_Cat2_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Coding", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Coding");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat2", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat2");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeA", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeA");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("31304.244"));
    }
    @RepeatedTest(1)
    public void getTotalFees_Development_QualityAssurance_Cat2_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Quality Assurance", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Quality Assurance");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat2", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat2");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeA", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeA");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("32674.50"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Development_QualityAssurance_Cat3_TypeB() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Quality Assurance", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Quality Assurance");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat3", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat3");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeB", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeB");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("35944.056"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Operations_PerformanceManagement_Cat1_TypeB() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat1", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat1");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeB", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeB");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("26838.546"));
    }
    
    @RepeatedTest(1)
    public void getTotalFees_Operations_PerformanceManagement_Cat3_TypeC() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat3", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat3");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeC", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeC");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("28304.643"));
    }
    @RepeatedTest(1)
    public void getTotalFees_Support_Tier1_Cat2_TypeB() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat3", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat3");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeC", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeC");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("28304.643"));
    }
    @RepeatedTest(1)
    public void getTotalFees_Support_Tier2_Cat1_TypeC() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat3", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat3");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeC", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeC");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("28304.643"));
    }
    @RepeatedTest(1)
    public void getTotalFees_Support_Tier3_Cat3_TypeA() {
        TreeNode<String> feesHierarchyTree = hierarchyService.feeStructured(dataValues);
        TreeNode<String> categoryTreeNode = hierarchyService.searchNode(feesHierarchyTree,"Performance Management", null, null, null);
        Assertions.assertNotNull(categoryTreeNode);
        Assertions.assertEquals(categoryTreeNode.getName(), "Performance Management");
        TreeNode<String> subCategoryTreeNode = hierarchyService.searchNode(categoryTreeNode,"Cat3", null, null, null);
        Assertions.assertNotNull(subCategoryTreeNode);
        Assertions.assertEquals(subCategoryTreeNode.getName(), "Cat3");
        TreeNode<String> typeTreeNode = hierarchyService.searchNode(subCategoryTreeNode,"TypeC", null, null, null);
        Assertions.assertNotNull(typeTreeNode);
        Assertions.assertEquals(typeTreeNode.getName(), "TypeC");
        BigDecimal totalFees = hierarchyService.getTotalFees(typeTreeNode, dataValues);
        Assertions.assertEquals(totalFees, new BigDecimal("28304.643"));
    }
}
