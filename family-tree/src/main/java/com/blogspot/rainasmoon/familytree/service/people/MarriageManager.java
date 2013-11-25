package com.blogspot.rainasmoon.familytree.service.people;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;

import com.blogspot.rainasmoon.familytree.dao.people.MarriageDao;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

@Component
@Transactional
public class MarriageManager {

	@Autowired
	private MarriageDao marriageDao;
	
	@Autowired
	private PeopleManager peopleManager;

	/**
	 * the imagic moment, they really get married.
	 * @param husband
	 * @param wife
	 * @return
	 */
    public Marriage marry(People husband, People wife) {
		Marriage marriage = new Marriage(husband, wife);

		marriageDao.save(marriage);

        return marriage;
	}

	public boolean isMarried(People husband, People wife) {

		if (marriageDao.findMarriage(husband, wife) != null) {
			return true;
		}

		return false;
	}

	public List<Marriage> search(List<PropertyFilter> filters) {
		return marriageDao.find(filters);
	}

	public People getHusband(People wife) {
		List<Marriage> m = marriageDao.findMarriageByWife(wife);
		if (m == null || m.isEmpty()) {
			return null;
		}
		if (m.size() > 1) {
			throw new RuntimeException("What a crazy family...");
		}
		return m.get(0).getHusband();
	}
	
	public People getWife(People husband) {
		List<Marriage> m = marriageDao.findMarriageByHusband(husband);
		if (m == null || m.isEmpty()) {
			return null;
		}
		if (m.size() > 1) {
			throw new RuntimeException("What a crazy family...");
		}
		return m.get(0).getWife();
	}
	
	public People getSpouse(People people) {
		List<People> s = peopleManager.getSpouseList(people);
		
		if (s.size() > 1) {
			throw new RuntimeException("What a crazy people with a lot of ML story...");
		}
		return s.get(0);
	}
	
	public Marriage getMarriage(People people) {
		if(people == null ) {
			return null;
		} 
		if (people.isMale()) {
			List<Marriage> m = marriageDao.findMarriageByHusband(people);
			if (m == null || m.isEmpty()) {
				return null;
			}
			if (m.size() > 1) {
				throw new RuntimeException("What a crazy family...");
			}
            return m.get(0);
        } else {
        	List<Marriage> m = marriageDao.findMarriageByWife(people);
        	if (m == null || m.isEmpty()) {
    			return null;
    		}
    		if (m.size() > 1) {
    			throw new RuntimeException("What a crazy family...");
    		}
            return m.get(0);
        }
	}
	
	public Marriage getMarriage(Long id) {
		return marriageDao.get(id);
	}

}
