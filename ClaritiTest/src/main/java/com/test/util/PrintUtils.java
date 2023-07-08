package com.test.util;

import com.test.Constants;
import com.test.hierarchy.TreeNode;

public class PrintUtils {

    public static void printTreeNode(String stringValue, TreeNode<?> Node){
        Node.getChildren().forEach( childNode -> {
            System.out.println(stringValue +  childNode.getName() + " (" + childNode.getType() + ") / " + childNode.getParentNode().getName());
            printTreeNode(stringValue + Constants.SPACE, childNode);
        });
    }

    public static void println(Object msg){
        System.out.println(msg);
    }

    public static void print(Object msg){
        System.out.print(msg);
    }
}
