package com.blogspot.rainasmoon.familytree.vo;

import java.util.List;

public class FamilyNode extends Node {

    private MarriageNode spousNode;

    private List<PeopleNode> childrenNode;

    public FamilyNode() {
        super(NodeModel.family);
    }

    public MarriageNode getSpousNode() {
        return spousNode;
    }

    public void setSpousNode(MarriageNode spousNode) {
        this.spousNode = spousNode;
    }

    public List<PeopleNode> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<PeopleNode> childrenNode) {
        this.childrenNode = childrenNode;
    }
}
