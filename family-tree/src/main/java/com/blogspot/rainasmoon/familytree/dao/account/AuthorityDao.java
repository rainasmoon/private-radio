package com.blogspot.rainasmoon.familytree.dao.account;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.blogspot.rainasmoon.familytree.entity.account.Authority;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
