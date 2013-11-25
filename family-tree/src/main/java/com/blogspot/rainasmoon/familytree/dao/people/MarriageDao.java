package com.blogspot.rainasmoon.familytree.dao.people;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

@Component
public class MarriageDao extends HibernateDao<Marriage, Long> {


	public Marriage findMarriage(People husband, People wife) {

		String hql = "from Marriage m where m.husband.id = :husbandId and m.wife.id = :wifeId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("husbandId", husband.getId());
		values.put("wifeId", wife.getId());

		Marriage marriage = findUnique(hql, values);

		return marriage;
	}

    public List<Marriage> findMarriageByHusband(People husband) {

        String hql = "from Marriage m where m.husband.id = ?";

        return find(hql, husband.getId());
    }

    public List<Marriage> findMarriageByWife(People wife) {

        String hql = "from Marriage m where m.wife.id = ?";

        return find(hql, wife.getId());
    }

}
