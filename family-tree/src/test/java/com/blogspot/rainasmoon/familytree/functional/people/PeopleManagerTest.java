package com.blogspot.rainasmoon.familytree.functional.people;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.utils.SeleniumUtils;

import com.blogspot.rainasmoon.familytree.data.FamilyData;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.functional.BaseFunctionalTestCase;
import com.blogspot.rainasmoon.familytree.functional.Gui;

public class PeopleManagerTest extends BaseFunctionalTestCase {

	@Test
	@Groups(BaseFunctionalTestCase.DAILY)
	public void shouldCreatePeople() {
		driver.findElement(By.linkText(Gui.MENU_ADD_PEOPLE)).click();

		People people = FamilyData.givenPeople();

		driver.findElement(By.id("name")).sendKeys(people.getName());
		if ("Male".equalsIgnoreCase(people.getSex())) {
			driver.findElement(By.id("sex_male")).click();
		} else {
			driver.findElement(By.id("sex_female")).click();
		}

		driver.findElement(By.xpath(Gui.BUTTON_FAMILY_SUBMIT)).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, "save people success."));
		thenVerifyPeople(people);
	}


	private void thenVerifyPeople(People people) {
		driver.findElement(By.linkText(Gui.MENU_PEOPLE)).click();
		SeleniumUtils.type(driver.findElement(By.name("filter_LIKES_name")),
				people.getName());
		driver.findElement(By.xpath(Gui.BUTTON_FAMILY_SEARCH)).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, people.getName()));
		assertTrue(SeleniumUtils.isTextPresent(driver, people.getSex()));
	}
}
