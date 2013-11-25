package com.blogspot.rainasmoon.familytree.unit.dao.marriage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.utils.DbUnitUtils;

import com.blogspot.rainasmoon.familytree.dao.people.MarriageDao;
import com.blogspot.rainasmoon.familytree.dao.people.PeopleDao;
import com.blogspot.rainasmoon.familytree.data.FamilyData;
import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.unit.dao.BaseUnitTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class MarriageDaoTest extends BaseUnitTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private MarriageDao marriageDao;

	@Autowired
	private PeopleDao peopleDao;

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
	public void shouldMarryEachOther() {

		Marriage marriage = FamilyData.givenMarriage();
		int before = countRowsInTable("MARRIAGE");

		peopleDao.save(marriage.getHusband());
		peopleDao.save(marriage.getWife());
		marriageDao.save(marriage);
		int after = countRowsInTable("MARRIAGE");
		assertEquals(1, after - before);

	}

	@Test
	public void shouldFindMarriage() {
		Marriage marriage = marriageDao.get(FamilyData.marriageId);

		logger.info(marriage);
		assertNotNull(marriage);
		assertNotNull(marriage.getHusband());
		assertNotNull(marriage.getWife());

	}

	@Test
	public void shouldGetChildrenList() {
		List<Children> l = marriageDao.get(FamilyData.marriageId)
				.getChildrenList();

		assertNotNull(l);

		assertTrue(l.size() > 0);
	}

	@Test
	public void shouldFindMarriageIfExist() {

		People husband = peopleDao.get(FamilyData.husbandId);
		People wife = peopleDao.get(FamilyData.wifeId);
		Marriage m = marriageDao.findMarriage(husband, wife);
		logger.info("the marriage is:" + m);

	}

}
