package com.blogspot.rainasmoon.familytree.unit.service.people;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blogspot.rainasmoon.familytree.dao.people.ChildrenDao;
import com.blogspot.rainasmoon.familytree.dao.people.MarriageDao;
import com.blogspot.rainasmoon.familytree.dao.people.PeopleDao;
import com.blogspot.rainasmoon.familytree.data.FamilyData;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.people.PeopleManager;

public class PeopleManagerTest {
	private IMocksControl control = EasyMock.createControl();

	private PeopleManager peopleManager;
	private PeopleDao mockPeopleDao;
	private MarriageDao mockMarriageDao;
	private ChildrenDao mockChildrenDao;

	private People givenPeople;

	@Before
	public void setUp() {
		peopleManager = new PeopleManager();
		mockPeopleDao = control.createMock(PeopleDao.class);
		mockChildrenDao = control.createMock(ChildrenDao.class);
		mockMarriageDao = control.createMock(MarriageDao.class);
		peopleManager.setPeopleDao(mockPeopleDao);
		peopleManager.setMarriageDao(mockMarriageDao);
		peopleManager.setChildrenDao(mockChildrenDao);

		givenPeople = FamilyData.givenPeople();
	}

	@After
	public void tearDown() {
		control.verify();
	}

	@Test
	public void shouldGetMarriageList() {

		givenPeopleWithMarriage();

		List<Marriage> marriageList = peopleManager
				.getMarriageList(givenPeople);

        assertNotNull(marriageList);
        assertEquals(marriageList.size(), 1);
	}

	@Test
	public void shouldGetGrandPa() {
		givenPeopleWithGrandPa();

		People grandPa = peopleManager.getGrandPa(givenPeople);

        assertNotNull(grandPa);
	}

	@Test
	public void shouldGetGrandMa() {
		givenPeopleWithGrandMa();

		People grandMa = peopleManager.getGrandMa(givenPeople);

        assertNotNull(grandMa);
	}

	private void givenPeopleWithMarriage() {
        List<Marriage> list = new ArrayList<Marriage>();
        list.add(new Marriage());

        EasyMock.expect(mockMarriageDao.findMarriageByHusband(givenPeople)).andReturn(list);
		control.replay();
	}

	private void givenPeopleWithGrandPa() {
        Marriage marriage = FamilyData.givenMarriage();
        EasyMock.expect(mockChildrenDao.findParents(givenPeople)).andReturn(marriage);
        EasyMock.expect(mockChildrenDao.findParents(marriage.getHusband())).andReturn(marriage);
        control.replay();
	}

	private void givenPeopleWithGrandMa() {
        Marriage marriage = FamilyData.givenMarriage();
        EasyMock.expect(mockChildrenDao.findParents(givenPeople)).andReturn(marriage);
        EasyMock.expect(mockChildrenDao.findParents(marriage.getHusband())).andReturn(marriage);
        control.replay();
	}
}
