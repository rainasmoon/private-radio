package com.blogspot.rainasmoon.familytree.service.people;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;

import com.blogspot.rainasmoon.familytree.dao.people.ChildrenDao;
import com.blogspot.rainasmoon.familytree.dao.people.MarriageDao;
import com.blogspot.rainasmoon.familytree.dao.people.PeopleDao;
import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.ServiceException;

@Component
@Transactional
public class PeopleManager {

	private static Logger logger = LoggerFactory.getLogger(PeopleManager.class);

    private PeopleDao peopleDao;

    private MarriageDao marriageDao;

    private ChildrenDao childrenDao;

	@Transactional(readOnly = true)
    public People getPeople(Long id) {
        return peopleDao.get(id);
    }
	
	/**
	 * get the people by its name.
	 * @param name
	 * @return
	 */
	@Transactional(readOnly = true)
	public People getPeople(String name) {
		People people = new People();
		people.setName(name);
		return peopleDao.findPeople(people );
	}

	@Transactional(readOnly = true)
    public List<People> search(List<PropertyFilter> filters) {
        if (filters == null || filters.size() == 0) {
			return Collections.emptyList();
        }
        return peopleDao.find(filters);
    }

    public void savePeople(People entity) {
        peopleDao.save(entity);
    }

    /**
     * find the people's direct siblings.
     * @param people
     * @return
     */
	@Transactional(readOnly = true)
    public List<People> getSibling(People people) {
		Marriage family = childrenDao.findParents(people);
		if (family == null) {
			return Collections.emptyList();
		}

		List<Children> childrenList = family.getChildrenList();

        List<People> sibling = new ArrayList<People>();
        for (Children children : childrenList) {
            sibling.add(children.getPeople());
        }
        sibling.remove(people);

        return sibling;
    }

	@Transactional(readOnly = true)
    public List<Marriage> getMarriageList(People people) {
        if (people.isMale()) {
            return marriageDao.findMarriageByHusband(people);
        } else {
            return marriageDao.findMarriageByWife(people);
        }
    }

	@Transactional(readOnly = true)    
    public List<People> getSpouseList(People people) {
        if (people.isMale()) {
            return getWifeList(people);
        } else {
            return getHusbandList(people);
        }
    }
	
	

	@Transactional(readOnly = true)    
    public List<People> getHusbandList(People people) {
        if (people.isFemale()) {
            List<People> husbandList = new ArrayList<People>();
            for (Marriage marriage : marriageDao.findMarriageByWife(people)) {
                husbandList.add(marriage.getWife());
            }
            return husbandList;
        }
		return Collections.emptyList();
    }

	@Transactional(readOnly = true)   
    public List<People> getWifeList(People people) {
        if (people.isMale()) {
            List<People> wifeList = new ArrayList<People>();
            for (Marriage marriage : marriageDao.findMarriageByHusband(people)) {
                wifeList.add(marriage.getWife());
            }
            return wifeList;
        }
		return Collections.emptyList();
    }

	@Transactional(readOnly = true)
    public List<People> getParent(People people) {
		logger.warn("not implyment is called. you must be crazy...");
		throw new ServiceException("not implyment method.");

    }

	@Transactional(readOnly = true)
	public People getFather(People people) {
		Marriage marriage = childrenDao.findParents(people);
		if (marriage == null) {
			return null;
		}
		return marriage.getHusband();
    }

	@Transactional(readOnly = true)
    public People getMother(People people) {
		Marriage marriage = childrenDao.findParents(people);
		if (marriage == null) {
			return null;
		}
		return marriage.getWife();
    }

	@Transactional(readOnly = true)
    public People getGrandPa(People people) {
		People father = getFather(people);
		if (father == null) {
			return null;
		}
		return getFather(father);
    }

	@Transactional(readOnly = true)
    public People getGrandMa(People people) {
		People father = getFather(people);
		if (father == null) {
			return null;
		}
		return getMother(father);
    }

	@Transactional(readOnly = true)
    @Deprecated
    // TODO: here has some problem. because each family has its children.
    public List<People> getChildren(People people) {
		logger.warn("not implyment is called. you must be crazy...");
		throw new ServiceException("not implyment method.");
    }

	@Autowired
	public void setPeopleDao(PeopleDao peopleDao) {
		this.peopleDao = peopleDao;
	}

	@Autowired
	public void setMarriageDao(MarriageDao marriageDao) {
		this.marriageDao = marriageDao;
	}

	@Autowired
	public void setChildrenDao(ChildrenDao childrenDao) {
		this.childrenDao = childrenDao;
		
	}

}
