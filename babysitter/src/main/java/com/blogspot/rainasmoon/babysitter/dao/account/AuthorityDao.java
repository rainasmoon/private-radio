package com.blogspot.rainasmoon.babysitter.dao.account;

import org.springframework.stereotype.Component;
import com.blogspot.rainasmoon.babysitter.entity.account.Authority;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
