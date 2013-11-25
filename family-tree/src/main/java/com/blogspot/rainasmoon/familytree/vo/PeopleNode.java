package com.blogspot.rainasmoon.familytree.vo;

import com.blogspot.rainasmoon.familytree.entity.people.People;

public class PeopleNode extends Node {

    private People people;

    public PeopleNode() {
        super(NodeModel.person);
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}