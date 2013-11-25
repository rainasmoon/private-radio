package com.blogspot.rainasmoon.familytree.service.people;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blogspot.rainasmoon.familytree.dao.people.ChildrenDao;
import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;

/**
 * implement all the method related with child.
 * sach as create a child, find one's children
 * delete a children
 * @author bsnpb6p
 *
 */
@Component
@Transactional
public class ChildrenManager {

	@Autowired
	private ChildrenDao childrenDao;

	public void createChild(Marriage marriage, People child) {
		Children children = new Children(marriage, child);
		childrenDao.save(children);

	}

	public boolean hasThisChild(Marriage marriage, People child) {

		if (childrenDao.findChildIfExist(marriage, child) != null) {
			return true;
		}

		return false;

	}

	public List<Children> findChildren(Marriage marriage) {

		return childrenDao.findChildren(marriage);
	}

	public void deleteChildren(Long childrenId) {
		childrenDao.delete(childrenId);
	}

}
