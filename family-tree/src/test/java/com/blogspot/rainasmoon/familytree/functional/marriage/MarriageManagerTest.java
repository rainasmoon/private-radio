package com.blogspot.rainasmoon.familytree.functional.marriage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.utils.SeleniumUtils;

import com.blogspot.rainasmoon.familytree.functional.BaseFunctionalTestCase;
import com.blogspot.rainasmoon.familytree.functional.Gui;

public class MarriageManagerTest extends BaseFunctionalTestCase {

    @Test
    @Groups(BaseFunctionalTestCase.DAILY)
    public void shouldGetMarried() {
        driver.findElement(By.linkText(Gui.MENU_PEOPLE)).click();
        driver.findElement(By.linkText("marry")).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "pls select a husband."));

        SeleniumUtils.type(driver.findElement(By.name("filter_LIKES_name")), "Glen");
        driver.findElement(By.xpath(Gui.BUTTON_FAMILY_SEARCH)).click();

        driver.findElement(By.linkText("love each other")).click();
        driver.findElement(By.linkText("marry")).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "pls select a wife."));

        SeleniumUtils.type(driver.findElement(By.name("filter_LIKES_name")), "leo");
        driver.findElement(By.xpath(Gui.BUTTON_FAMILY_SEARCH)).click();

        driver.findElement(By.linkText("love each other")).click();
        driver.findElement(By.linkText("marry")).click();
        assertTrue(SeleniumUtils.isTextPresent(driver, "marriage success."));

    }
}
