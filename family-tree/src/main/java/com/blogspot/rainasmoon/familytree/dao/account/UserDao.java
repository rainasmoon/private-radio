package com.blogspot.rainasmoon.familytree.dao.account;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.blogspot.rainasmoon.familytree.entity.account.User;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class UserDao extends HibernateDao<User, Long> {
}
