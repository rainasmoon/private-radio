package com.blogspot.rainasmoon.familytree.unit.dao.account;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.DbUnitUtils;

import com.blogspot.rainasmoon.familytree.dao.account.RoleDao;
import com.blogspot.rainasmoon.familytree.dao.account.UserDao;
import com.blogspot.rainasmoon.familytree.entity.account.Role;
import com.blogspot.rainasmoon.familytree.entity.account.User;
import com.blogspot.rainasmoon.familytree.unit.dao.BaseUnitTestCase;

/**
 * RoleDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class RoleDaoTest extends BaseUnitTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

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

	/**
	 * 测试删除角色时删除用户-角色的中间表.
	 */
	@Test
	public void deleteRole() {
		//新增测试角色并与admin用户绑定.
		Role role = new Role();
		role.setName(DataUtils.randomName("Role"));
		roleDao.save(role);

		User user = userDao.get(1L);
		user.getRoleList().add(role);
		userDao.save(user);
		userDao.flush();

		int oldJoinTableCount = countRowsInTable("ACCT_USER_ROLE");
		int oldUserTableCount = countRowsInTable("ACCT_USER");

		//删除用户角色, 中间表将减少1条记录,而用户表应该不受影响.
		roleDao.delete(role.getId());
		roleDao.flush();

		int newJoinTableCount = countRowsInTable("ACCT_USER_ROLE");
		int newUserTableCount = countRowsInTable("ACCT_USER");
		assertEquals(1, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}
}
