package com.test.hierarchy;

import static com.test.util.PrintUtils.printTreeNode;
//import static com.test.util.PrintUtils.System.out.println;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.test.Constants;
import com.test.model.Data;;

public class FeesHierarchyService {
	TreeNode<String> firstNode = null;
	TreeNode<String> secondNode = null;
	TreeNode<String> thirdNode = null;
	TreeNode<String> fourthNode = null;

	public TreeNode<String> feeStructured(List<Data> dataHierarchy) {
		//System.out.println("======= Building Hierarchy : START =======");
		TreeNode<String> rootNode = new TreeNode<>(Constants.ROOT, null);
		dataHierarchy.forEach(item -> {
			TreeNode<String> departmentNode = rootNode.addChild(item.getDepartment().getName(),
					Constants.TYPE_DEPARTMENT);
			TreeNode<String> categoryNode = departmentNode.addChild(item.getCategory().getName(),
					Constants.TYPE_CATEGORY);
			TreeNode<String> subCategoryNode = categoryNode.addChild(item.getSubCategory().getName(),
					Constants.TYPE_SUBCATEGORY);
			subCategoryNode.addChild(item.getType().getName(), Constants.TYPE_TYPE);
		});
		//System.out.println("======= Building Hierarchy : END =======\n\n\n");
		return rootNode;
	}

	public TreeNode<String> searchNode(TreeNode<String> rootNode, String departmentName, String categoryName,
			String subCategoryName, String typeName) {
		if (departmentName != null) {
			firstNode = search(rootNode, departmentName, null);
		}
		if (categoryName != null && firstNode != null) {
			secondNode = search(firstNode, categoryName, null);
		}
		if (subCategoryName != null  && secondNode != null) {
			thirdNode = search(secondNode, subCategoryName, null);
		}
		if (typeName != null  && thirdNode != null) {
			fourthNode = search(thirdNode, typeName, null);
		}

		//System.out.println("First Node >>> " + firstNode.getName());
		return fourthNode !=null ? fourthNode : thirdNode != null ? thirdNode : secondNode != null ? secondNode : firstNode;
	}

	private TreeNode<String> search(TreeNode<String> Node, String name, TreeNode<String> foundedNode) {
		if (!Objects.isNull(foundedNode))
			return foundedNode;

		if (Node.getName().equals(name)) {
			foundedNode = Node;
			return foundedNode;
		}

		for (TreeNode<String> childNode : Node.getChildren()) {
			//System.out.println(">>> " + childNode.getName() + " | " + childNode.getType());
			foundedNode = search(childNode, name, foundedNode);
		}

		return foundedNode;
	}

	public BigDecimal getTotalFees(TreeNode<String> Node, List<Data> dataHierarchy) {
		List<String> hierarchyList = getHierarchyList(Node, "", new ArrayList<>());
		final BigDecimal[] totalFees = { BigDecimal.ZERO };
		final String feesHierarchyRelations = Node.getType().equals(Constants.TYPE_SUBCATEGORY) || Node.getType().equals(Constants.TYPE_TYPE)
				? Node.getParentNode().getParentNode().getName() + "/" + Node.getParentNode().getName() + "/"
				: "";

		hierarchyList.forEach(feesHierarchy -> {
			//System.out.println(">>>>>>> " + (feesHierarchyRelations + feesHierarchy));
			totalFees[0] = totalFees[0]
					.add(dataHierarchy.stream().filter(e -> e.getFeesHierarchy().endsWith(feesHierarchyRelations + feesHierarchy))
							.map(Data::getTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add));
		});
		System.out.println("Total Fees --- " + totalFees[0]);
		Integer surCharge = 0;
		String departmentName = "";
		if (Node.getType().equals(Constants.TYPE_DEPARTMENT)) {
			departmentName = Node.getName();
		} else if (Node.getType().equals(Constants.TYPE_CATEGORY)) {
			departmentName = Node.getParentNode().getName();
		} else if (Node.getType().equals(Constants.TYPE_SUBCATEGORY)) {
			departmentName = Node.getParentNode().getParentNode().getName();
		} else if (Node.getType().equals(Constants.TYPE_TYPE)) {
			departmentName = Node.getParentNode().getParentNode().getParentNode().getName();
		}
		surCharge = Constants.DEPARTMENT_SURCHARGE.get(departmentName);
		System.out.println(departmentName + " SurCharge Percentage % --- " + surCharge);
		BigDecimal surchargeOnFees = totalFees[0].multiply(BigDecimal.valueOf(surCharge))
				.divide(BigDecimal.valueOf(100));
		System.out.println(departmentName + " Surcharge Calculated On TotalFees ---" + surchargeOnFees);
		BigDecimal finalAmount = totalFees[0].add(surchargeOnFees);
		System.out.println(departmentName + " Final Fee --- " + finalAmount);

		return finalAmount;
	}

	private List<String> getHierarchyList(TreeNode<String> Node, String value,
			List<String> feesHierarchyList) {
		if (value.isEmpty()) {
			value = Node.getName();
		}
		for (TreeNode<String> treeNode : Node.getChildren()) {
			getHierarchyList(treeNode, value + "/" + treeNode.getName(), feesHierarchyList);
		}
		if (Node.isLeafPresent())
			feesHierarchyList.add(value);
		return feesHierarchyList;
	}

	public void printHierarchy(TreeNode<?> Node) {
		printTreeNode(Constants.SPACE, Node);
	}

}
