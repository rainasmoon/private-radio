package com.blogspot.rainasmoon.familytree.vo;

public class MarriageNode extends Node {
    private PeopleNode husbandNode;
    private PeopleNode wifeNode;

    public MarriageNode() {
        super(NodeModel.marriage);
    }

    public PeopleNode getHusbandNode() {
        return husbandNode;
    }

    public void setHusbandNode(PeopleNode husbandNode) {
        this.husbandNode = husbandNode;
    }

    public PeopleNode getWifeNode() {
        return wifeNode;
    }

    public void setWifeNode(PeopleNode wifeNode) {
        this.wifeNode = wifeNode;
    }
}
