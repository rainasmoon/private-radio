package com.blogspot.rainasmoon.familytree.dao.people;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

@Component
public class ChildrenDao extends HibernateDao<Children, Long> {

	/**
	 * find the marriage's all childrens.
	 * @param marriage
	 * @return
	 */
	public List<Children> findChildren(Marriage marriage) {


		if (marriage == null) {
			return null;
		}
		String hql = "from Children c where c.marriage.id = :marriageId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("marriageId", marriage.getId());
        @SuppressWarnings("unchecked")
        List<Children> l = createQuery(hql, values).list();
		return l;
	}

	
	/**
	 * find if the people is the marriage's child.
	 * @param marriage
	 * @param peopleId
	 * @return
	 */
	public Children findChildIfExist(Marriage marriage, People peopleId) {
		String hql = "from Children c where c.marriage.id = :marriageId and c.people.id = :childId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("marriageId", marriage.getId());
		values.put("childId", peopleId.getId());

		return findUnique(hql, values);

	}

	/**
	 * find the person's parents.
	 * @param child
	 * @return
	 */
    public Marriage findParents(People child) {
        String hql = "select c.marriage from Children c where c.people.id = ?";
        Marriage m = findUnique(hql, child.getId());

        return m;
    }

}
