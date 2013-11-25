package com.blogspot.rainasmoon.familytree.data;

import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

public class FamilyData {

	public static final Long husbandId = new Long(1);
	public static final Long wifeId = new Long(1);
	public static final Long marriageId = new Long(1);
    public static final Long childId = new Long(3);

	public static Children givenChildren() {
		Marriage marriage = givenMarriage();
		People child = givenPeople("Child");
		return new Children(marriage, child);
	}

	public static Marriage givenMarriage() {

		People husband = givenPeople("Husband");
		People wife = givenPeople("Wife");

		return new Marriage(husband, wife);

	}

	public static People givenPeople() {
		return givenPeople("Text");
	}

	public static People givenPeople(String name) {
		return givenPeople(name, "Male");
	}

	public static People givenPeople(String name, String sex) {
		People people = new People();
		people.setName(name);
		people.setSex(sex);

		return people;
	}
}
