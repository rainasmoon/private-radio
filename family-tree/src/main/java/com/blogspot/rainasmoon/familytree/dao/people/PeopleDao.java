package com.blogspot.rainasmoon.familytree.dao.people;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

@Component
public class PeopleDao extends HibernateDao<People, Long> {

	public People findPeople(People people) {
        String hql = "select p from People p where p.name = ?";
        People p = findUnique(hql, people.getName());

        return p;
    }
}
