package com.blogspot.rainasmoon.familytree.unit.dao.children;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.utils.DbUnitUtils;

import com.blogspot.rainasmoon.familytree.dao.people.ChildrenDao;
import com.blogspot.rainasmoon.familytree.dao.people.MarriageDao;
import com.blogspot.rainasmoon.familytree.dao.people.PeopleDao;
import com.blogspot.rainasmoon.familytree.data.FamilyData;
import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.unit.dao.BaseUnitTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class ChildrenDaoTest extends BaseUnitTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private ChildrenDao childrenDao;

	@Autowired
	private PeopleDao peopleDao;

	@Autowired
	private MarriageDao marriageDao;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, BaseUnitTestCase.path);
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, BaseUnitTestCase.path);
	}

	@Test
	public void shouldCreateChild() {
		Children child = FamilyData.givenChildren();

		int before = countRowsInTable("CHILDREN");

		peopleDao.save(child.getMarriage().getHusband());
		peopleDao.save(child.getMarriage().getWife());
		marriageDao.save(child.getMarriage());
		peopleDao.save(child.getPeople());
		childrenDao.save(child);
		int after = countRowsInTable("CHILDREN");
		assertEquals(1, after - before);

	}

	@Test
	public void shouldFindChildren() {
		Marriage m = marriageDao.get(FamilyData.marriageId);
        List<Children> l = childrenDao.findChildren(m);
		assertEquals(1, l.size());
	}

	@Test
	public void shouldFindWetherChildrenExist() {
		Marriage marriage = marriageDao.get(FamilyData.marriageId);
		People child = peopleDao.get(FamilyData.childId);
		Children children = childrenDao.findChildIfExist(marriage, child);
		logger.info("find the child: " + children);

        assertNotNull(children);

	}

    @Test
    public void shouldFindParent() {
        People child = peopleDao.get(FamilyData.childId);
        Marriage marriage = childrenDao.findParents(child);

        assertNotNull(marriage);
        assertNotNull(marriage.getHusband());
        assertNotNull(marriage.getWife());
        logger.info("child : " + child.getName() + " 's father is : " + marriage.getHusband().getName());
        logger.info("child : " + child.getName() + " 's mother is : " + marriage.getWife().getName());

    }
}
