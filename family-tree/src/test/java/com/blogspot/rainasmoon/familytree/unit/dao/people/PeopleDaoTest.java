package com.blogspot.rainasmoon.familytree.unit.dao.people;

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

import com.blogspot.rainasmoon.familytree.dao.people.PeopleDao;
import com.blogspot.rainasmoon.familytree.data.FamilyData;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.unit.dao.BaseUnitTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class PeopleDaoTest extends BaseUnitTestCase {

	private static DataSource dataSourceHolder = null;

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
	public void shouldSelectPeople() {
        List<People> l = peopleDao.getAll();
		assertNotNull(l);
		logger.info(l.size());
	}

	@Test
	public void shouldCreatePeople() {
		People people = FamilyData.givenPeople();
		int before = countRowsInTable("PEOPLE");
		peopleDao.save(people);
		int after = countRowsInTable("PEOPLE");
		assertEquals(1, after - before);
	}



}
