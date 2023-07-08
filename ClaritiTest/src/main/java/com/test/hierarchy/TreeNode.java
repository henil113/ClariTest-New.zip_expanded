package com.test.hierarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreeNode<T> {

    private T name;
    private TreeNode<T> parentNode;
    private String type;
    private List<TreeNode<T>> children = new ArrayList<>();

    public TreeNode(T name, TreeNode<T> parent){
        this(name, parent, null);
    }

    public TreeNode(T name, TreeNode<T> parent, String type){
        this.name = name;
        this.parentNode = parent;
        this.type = type;
    }
    
    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public TreeNode<T> getParentNode() {
        return parentNode;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public String getType() {
        return type;
    }

    public boolean isRootPresent(){
        return parentNode == null;
    }

    public boolean isLeafPresent(){
        return children == null || children.isEmpty();
    }
    public TreeNode addChild(T nodeName, String type){
        Optional<TreeNode<T>> optionalTreeNode = getChildIfExists(nodeName);
        if(optionalTreeNode.isPresent()){
            return optionalTreeNode.get();
        }
        TreeNode Node = new TreeNode(nodeName, this, type);
        this.children.add(Node);
        return Node;
    }
    public List<TreeNode> getAllParents(){
        List<TreeNode> parents = new ArrayList<>();
        TreeNode parent = this.parentNode;
        while (parent != null){
            parents.add(parent);
            parent = parent.getParentNode();
        }
        return parents;
    }

    

    private Optional<TreeNode<T>> getChildIfExists(T name){
        return this.children.stream().filter( childName-> childName.getName().equals(name)).findFirst();
    }

    
}
