package com.blogspot.rainasmoon.familytree.functional.children;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.utils.SeleniumUtils;

import com.blogspot.rainasmoon.familytree.functional.BaseFunctionalTestCase;
import com.blogspot.rainasmoon.familytree.functional.Gui;

public class ChildrenManagerTest extends BaseFunctionalTestCase {

    @Test
    @Groups(BaseFunctionalTestCase.DAILY)
    public void shouldCreateChild() {
        driver.findElement(By.linkText(Gui.MENU_MARRIAGE)).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "view it children"));
        driver.findElement(By.linkText("view it children")).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "children list"));

        SeleniumUtils.type(driver.findElement(By.name("filter_LIKES_name")), "bruce");
        driver.findElement(By.xpath(Gui.BUTTON_FAMILY_SEARCH)).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "make a child"));
        driver.findElement(By.linkText("make a child")).click();

        assertTrue(SeleniumUtils.isTextPresent(driver, "bruce"));

    }

    @Test
    @Groups(BaseFunctionalTestCase.DAILY)
    public void shouldAbandonChild() {

        driver.findElement(By.linkText(Gui.MENU_MARRIAGE)).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "view it children"));
        driver.findElement(By.linkText("view it children")).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "children list"));

        assertTrue(SeleniumUtils.isTextPresent(driver, "abandon"));
        assertTrue(SeleniumUtils.isTextPresent(driver, "leo"));

        driver.findElement(By.linkText("abandon")).click();
        assertFalse(SeleniumUtils.isTextPresent(driver, "leo"));

    }
}
